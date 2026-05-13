package cotato.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StaffCreateRequest {

    @Schema(example = "정찬민")
    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 10)
    private String name;

    @Schema(example = "30")
    @NotNull
    @Min(1)
    private Integer age;

    @Schema(example = "01098765432")
    @NotBlank
    @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하는 11자리여야 합니다.")
    private String phoneNumber;

    @Schema(example = "파트장")
    @NotBlank(message = "역할은 필수입니다.")
    @Pattern(
            regexp = "^(파트장|기획팀장|홍보팀장|부회장|회장|교육팀장)$",
            message = "역할은 파트장, 기획팀장, 홍보팀장, 부회장, 회장, 교육팀장 중 하나여야 합니다."
    )
    private String role;
}
