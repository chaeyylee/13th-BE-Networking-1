package cotato.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import cotato.backend.entity.Application;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ApplicationDetailResponse {

    @Schema(example = "이채영")
    private final String name;

    @Schema(example = "13")
    private final Integer period;

    @Schema(example = "24")
    private final Integer age;

    @Schema(example = "백엔드")
    private final String part;

    @Schema(example = "8")
    private final Integer ability;

    @Schema(example = "9")
    private final Integer passion;

    @Schema(example = "01012345678")
    private final String phoneNumber;

    @Schema(example = "2025-02-28 23:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime applicationTime;

    @Schema(example = "1")
    private final Integer likeCount;

    private final List<LikeInfoResponse> likes;

    public ApplicationDetailResponse(Application application) {
        this.name = application.getApplicant().getName();
        this.period = application.getPeriod();
        this.age = application.getAge();
        this.part = application.getPart();
        this.ability = application.getAbility();
        this.passion = application.getPassion();
        this.phoneNumber = application.getApplicant().getPhoneNumber();
        this.applicationTime = application.getApplicationTime();

        this.likeCount = application.getLikeCount();

        this.likes = application.getLikes() == null
                ? List.of()
                : application.getLikes().stream()
                .map(like -> new LikeInfoResponse(like.getStaff()))
                .toList();
    }
}
