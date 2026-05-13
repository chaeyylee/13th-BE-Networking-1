package cotato.backend.dto.response;

import cotato.backend.entity.Application;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApplicationSummaryResponse {

    @Schema(example = "1")
    private final Long id;

    @Schema(example = "이채영")
    private final String name;

    @Schema(example = "13")
    private final Integer period;

    @Schema(example = "백엔드")
    private final String part;

    @Schema(example = "5")
    private final int likeCount;

    public ApplicationSummaryResponse(Application application) {
        this.id = application.getId();
        this.name = application.getApplicant().getName();
        this.period = application.getPeriod();
        this.part = application.getPart();
        this.likeCount = application.getLikeCount();
    }
}
