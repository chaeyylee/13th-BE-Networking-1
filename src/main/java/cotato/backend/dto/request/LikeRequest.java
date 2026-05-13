package cotato.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequest {

    @NotNull(message = "운영진 ID는 필수입니다.")
    private Long staffId;
}
