package cotato.backend.repository;

import cotato.backend.entity.Application;
import cotato.backend.entity.ApplicationLike;
import cotato.backend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationLikeRepository extends JpaRepository<ApplicationLike, Long> {

    Optional<ApplicationLike> findByApplicationAndStaff(Application application, Staff staff);

    boolean existsByApplicationAndStaff(Application application, Staff staff);
}
