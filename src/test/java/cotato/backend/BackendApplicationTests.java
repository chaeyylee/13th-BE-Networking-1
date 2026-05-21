package cotato.backend;

import cotato.backend.common.exception.AppException;
import cotato.backend.dto.request.ApplicationCreateRequest;
import cotato.backend.dto.request.StaffCreateRequest;
import cotato.backend.entity.Applicant;
import cotato.backend.entity.Application;
import cotato.backend.repository.ApplicantRepository;
import cotato.backend.repository.ApplicationRepository;
import cotato.backend.service.ApplicationLikeService;
import cotato.backend.service.ApplicationService;
import cotato.backend.service.StaffService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	private StaffService staffService;

	@Autowired
	private ApplicationLikeService applicationLikeService;

	@Test
	@DisplayName("서류 등록 시 같은 전화번호는 동일 지원자로 식별되고, 서류는 여러 개 등록된다")
	void applicantReuseTest() {
		ApplicationCreateRequest req1 = createApplicationRequest("이채영", "01011112222", 13);
		ApplicationCreateRequest req2 = createApplicationRequest("이채영", "01011112222", 14);

		Long applicationId1 = applicationService.create(req1);
		Long applicationId2 = applicationService.create(req2);

		List<Applicant> applicants = applicantRepository.findAll();
		List<Application> applications = applicationRepository.findAll();

		assertThat(applicants).hasSize(1);
		assertThat(applications).hasSize(2);

		Application application1 = applicationRepository.findById(applicationId1).orElseThrow();
		Application application2 = applicationRepository.findById(applicationId2).orElseThrow();

		assertThat(application1.getApplicant().getId())
				.isEqualTo(application2.getApplicant().getId());
	}

	@Test
	@DisplayName("서류 단건 조회 시 상세 정보와 좋아요 운영진 목록을 조회할 수 있다")
	void applicationDetailTest() {
		Long applicationId = applicationService.create(
				createApplicationRequest("이채영", "01022223333", 13)
		);

		Long staffId = staffService.create(
				createStaffRequest("정찬민", "01033334444", "파트장")
		);

		applicationLikeService.like(applicationId, staffId);

		entityManager.flush();
		entityManager.clear();

		var response = applicationService.getById(applicationId);

		assertThat(response.getName()).isEqualTo("이채영");
		assertThat(response.getPeriod()).isEqualTo(13);
		assertThat(response.getAge()).isEqualTo(24);
		assertThat(response.getPart()).isEqualTo("백엔드");
		assertThat(response.getAbility()).isEqualTo(8);
		assertThat(response.getPassion()).isEqualTo(9);
		assertThat(response.getPhoneNumber()).isEqualTo("01022223333");
		assertThat(response.getLikeCount()).isEqualTo(1);
		assertThat(response.getLikes()).hasSize(1);
		assertThat(response.getLikes().get(0).getName()).isEqualTo("정찬민");
		assertThat(response.getLikes().get(0).getRole()).isEqualTo("파트장");
	}

	@Test
	@DisplayName("서류 리스트는 likes, gisu, gisu+likes 기준으로 10건씩 조회된다")
	void applicationListFilterTest() {
		Long applicationId1 = applicationService.create(
				createApplicationRequest("이채영", "01044445555", 13)
		);
		applicationService.create(
				createApplicationRequest("김지원", "01055556666", 13)
		);
		applicationService.create(
				createApplicationRequest("박서연", "01066667777", 14)
		);

		Long staffId = staffService.create(
				createStaffRequest("정찬민", "01077778888", "파트장")
		);

		applicationLikeService.like(applicationId1, staffId);

		var likesList = applicationService.getList("likes", 1, null);
		var gisuList = applicationService.getList("gisu", 1, 13);
		var gisuLikesList = applicationService.getList("gisu+likes", 1, 13);

		assertThat(likesList).hasSizeLessThanOrEqualTo(10);
		assertThat(gisuList).hasSizeLessThanOrEqualTo(10);
		assertThat(gisuLikesList).hasSizeLessThanOrEqualTo(10);

		assertThat(gisuList)
				.allMatch(response -> response.getPeriod().equals(13));

		assertThat(gisuLikesList)
				.allMatch(response -> response.getPeriod().equals(13));

		assertThat(likesList.get(0).getId()).isEqualTo(applicationId1);
	}

	@Test
	@DisplayName("같은 운영진이 같은 서류에 중복 좋아요를 누르면 예외가 발생한다")
	void duplicateLikeTest() {
		Long applicationId = applicationService.create(
				createApplicationRequest("이채영", "01088889999", 13)
		);

		Long staffId = staffService.create(
				createStaffRequest("정찬민", "01099990000", "파트장")
		);

		applicationLikeService.like(applicationId, staffId);

		assertThatThrownBy(() -> applicationLikeService.like(applicationId, staffId))
				.isInstanceOf(AppException.class);
	}

	@Test
	@DisplayName("서류 등록 시 유효하지 않은 값이면 400 Bad Request가 발생한다")
	void applicationValidationFailTest() throws Exception {
		String invalidRequest = """
                {
                  "name": "a",
                  "period": 0,
                  "age": 10,
                  "part": "AI",
                  "ability": 20,
                  "passion": -1,
                  "phoneNumber": "123",
                  "applicationTime": "2025-02-28 23:30"
                }
                """;

		mockMvc.perform(post("/applications")
						.contentType(APPLICATION_JSON)
						.content(invalidRequest))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("운영진 등록 시 유효하지 않은 role이면 400 Bad Request가 발생한다")
	void staffValidationFailTest() throws Exception {
		String invalidRequest = """
                {
                  "name": "정찬민",
                  "age": 30,
                  "phoneNumber": "01098765432",
                  "role": "개발자"
                }
                """;

		mockMvc.perform(post("/staffs")
						.contentType(APPLICATION_JSON)
						.content(invalidRequest))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("존재하지 않는 서류 ID를 조회하면 404 Not Found가 발생한다")
	void applicationNotFoundTest() throws Exception {
		mockMvc.perform(get("/applications/999999"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("잘못된 filterBy 값으로 서류 리스트를 조회하면 400 Bad Request가 발생한다")
	void invalidFilterByTest() throws Exception {
		mockMvc.perform(get("/applications")
						.param("filterBy", "wrong")
						.param("page", "1"))
				.andExpect(status().isBadRequest());
	}

	private ApplicationCreateRequest createApplicationRequest(String name, String phoneNumber, int period) {
		ApplicationCreateRequest request = new ApplicationCreateRequest();

		setField(request, "name", name);
		setField(request, "period", period);
		setField(request, "age", 24);
		setField(request, "part", "백엔드");
		setField(request, "ability", 8);
		setField(request, "passion", 9);
		setField(request, "phoneNumber", phoneNumber);
		setField(request, "applicationTime", LocalDateTime.of(2025, 2, 28, 23, 30));

		return request;
	}

	private StaffCreateRequest createStaffRequest(String name, String phoneNumber, String role) {
		StaffCreateRequest request = new StaffCreateRequest();

		setField(request, "name", name);
		setField(request, "age", 30);
		setField(request, "phoneNumber", phoneNumber);
		setField(request, "role", role);

		return request;
	}

	private void setField(Object target, String fieldName, Object value) {
		try {
			var field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}