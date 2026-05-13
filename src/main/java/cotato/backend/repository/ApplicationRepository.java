package cotato.backend.repository;

import cotato.backend.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // 기수별 조회
    Page<Application> findAllByPeriod(Integer period, Pageable pageable);

    // 좋아요 순 조회 -> Pageable에 Sort 포함해서 넘김
    Page<Application> findAll(Pageable pageable);
}
