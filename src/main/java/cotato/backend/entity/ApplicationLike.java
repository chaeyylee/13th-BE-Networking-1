package cotato.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "application_like",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"application_id", "staff_id"})
                // 같은 운영진이 같은 서류에 중복 좋아요 방지
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Builder
    public ApplicationLike(Application application, Staff staff) {
        this.application = application;
        this.staff = staff;
    }
}
