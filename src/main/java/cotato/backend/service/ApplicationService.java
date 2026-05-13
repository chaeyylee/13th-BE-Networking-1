package cotato.backend.service;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.dto.request.ApplicationCreateRequest;
import cotato.backend.dto.response.ApplicationDetailResponse;
import cotato.backend.dto.response.ApplicationSummaryResponse;
import cotato.backend.entity.Applicant;
import cotato.backend.entity.Application;
import cotato.backend.repository.ApplicantRepository;
import cotato.backend.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;

    @Transactional
    public Long create(ApplicationCreateRequest request) {
        Applicant applicant = applicantRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseGet(() -> applicantRepository.save(
                        Applicant.builder()
                                .name(request.getName())
                                .age(request.getAge())
                                .phoneNumber(request.getPhoneNumber())
                                .build()
                ));

        Application application = Application.builder()
                .applicant(applicant)
                .period(request.getPeriod())
                .age(request.getAge())
                .part(request.getPart())
                .ability(request.getAbility())
                .passion(request.getPassion())
                .applicationTime(request.getApplicationTime())
                .build();

        return applicationRepository.save(application).getId();
    }

    public ApplicationDetailResponse getById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));
        return new ApplicationDetailResponse(application);
    }

    public List<ApplicationSummaryResponse> getList(String filterBy, Integer page, Integer period) {
        int pageIndex = (page == null ? 1 : page) - 1; // 0-based 변환
        int pageSize = 10;

        Pageable pageable;
        Page<Application> result;

        switch (filterBy) {
            case "likes" -> {
                pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "likeCount"));
                result = applicationRepository.findAll(pageable);
            }
            case "gisu" -> {
                if (period == null) throw new AppException(ErrorCode.INVALID_PARAMETER);
                pageable = PageRequest.of(pageIndex, pageSize);
                result = applicationRepository.findAllByPeriod(period, pageable);
            }
            case "gisu+likes" -> {
                if (period == null) throw new AppException(ErrorCode.INVALID_PARAMETER);
                pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "likeCount"));
                result = applicationRepository.findAllByPeriod(period, pageable);
            }
            default -> throw new AppException(ErrorCode.INVALID_PARAMETER);
        }

        return result.getContent()
                .stream()
                .map(ApplicationSummaryResponse::new)
                .toList();
    }
}
