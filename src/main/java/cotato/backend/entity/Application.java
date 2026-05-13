package cotato.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    @OneToMany(mappedBy = "application")
    private List<ApplicationLike> likes = new ArrayList<>();

    @Column(nullable = false)
    private Integer period; // 지원 기수

    @Column(nullable = false)
    private Integer age; // 나이

    @Column(nullable = false)
    private String part; // 기획, 디자이너, 프론트엔드, 백엔드

    @Column(nullable = false)
    private Integer ability; // 0~10

    @Column(nullable = false)
    private Integer passion; // 0~10

    @Column(nullable = false)
    private LocalDateTime applicationTime; // 서류 제출 시간

    @Column(nullable = false)
    private int likeCount = 0; // 좋아요 수 (캐싱)

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    @Builder
    public Application(Applicant applicant, Integer period, Integer age, String part,
                       Integer ability, Integer passion, LocalDateTime applicationTime) {
        this.applicant = applicant;
        this.period = period;
        this.age = age;
        this.part = part;
        this.ability = ability;
        this.passion = passion;
        this.applicationTime = applicationTime;
    }
}
