package cotato.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApplicationCreateRequest {

    @Schema(example = "이채영")
    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, max = 10, message = "이름은 2글자 이상 10글자 이하여야 합니다.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
    private String name;

    @Schema(example = "13")
    @NotNull(message = "지원 기수는 필수입니다.")
    @Min(value = 1, message = "지원 기수는 1 이상이어야 합니다.")
    private Integer period;

    @Schema(example = "24")
    @NotNull(message = "나이는 필수입니다.")
    @Min(value = 22, message = "나이는 22살 이상이어야 합니다.")
    @Max(value = 30, message = "나이는 30살 이하여야 합니다.")
    private Integer age;

    @Schema(example = "백엔드")
    @NotBlank(message = "파트는 필수입니다.")
    @Pattern(
            regexp = "^(기획|디자이너|프론트엔드|백엔드)$",
            message = "파트는 기획, 디자이너, 프론트엔드, 백엔드 중 하나여야 합니다."
    )
    private String part;

    @Schema(example = "8")
    @NotNull(message = "실력은 필수입니다.")
    @Min(value = 0, message = "실력은 0 이상이어야 합니다.")
    @Max(value = 10, message = "실력은 10 이하여야 합니다.")
    private Integer ability;

    @Schema(example = "9")
    @NotNull(message = "열정은 필수입니다.")
    @Min(value = 0, message = "열정은 0 이상이어야 합니다.")
    @Max(value = 10, message = "열정은 10 이하여야 합니다.")
    private Integer passion;

    @Schema(example = "01012345678")
    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Pattern(
            regexp = "^010\\d{8}$",
            message = "휴대폰 번호는 010으로 시작하는 11자리여야 합니다."
    )
    private String phoneNumber;

    @Schema(example = "2025-02-28 23:30")
    @NotNull(message = "서류 제출 시간은 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime applicationTime;
}
