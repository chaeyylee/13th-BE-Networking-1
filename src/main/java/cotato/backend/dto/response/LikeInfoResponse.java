package cotato.backend.dto.response;

import cotato.backend.entity.Staff;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LikeInfoResponse {

    @Schema(example = "1")
    private final Long staffId;

    @Schema(example = "정찬민")
    private final String name;

    @Schema(example = "파트장")
    private final String role;

    public LikeInfoResponse(Staff staff) {
        this.staffId = staff.getId();
        this.name = staff.getName();
        this.role = staff.getRole();
    }
}
