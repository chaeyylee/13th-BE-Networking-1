package cotato.backend.dto.response;

import cotato.backend.entity.Applicant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApplicantResponse {

    @Schema(example = "이채영")
    private final String name;

    @Schema(example = "24")
    private final Integer age;

    @Schema(example = "01012345678")
    private final String phoneNumber;

    public ApplicantResponse(Applicant applicant) {
        this.name = applicant.getName();
        this.age = applicant.getAge();
        this.phoneNumber = applicant.getPhoneNumber();
    }
}
