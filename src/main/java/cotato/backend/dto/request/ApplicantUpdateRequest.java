package cotato.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicantUpdateRequest {

    @Schema(example = "이채영")
    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
    private String name;

    @Schema(example = "25")
    @NotNull
    @Min(22) @Max(30)
    private Integer age;

    @Schema(example = "01012345678")
    @NotBlank
    @Pattern(regexp = "^010\\d{8}$")
    private String phoneNumber;
}
