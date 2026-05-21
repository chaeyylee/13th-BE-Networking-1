package cotato.backend.service;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.entity.Application;
import cotato.backend.entity.ApplicationLike;
import cotato.backend.entity.Staff;
import cotato.backend.repository.ApplicationLikeRepository;
import cotato.backend.repository.ApplicationRepository;
import cotato.backend.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationLikeService {

    private final ApplicationRepository applicationRepository;
    private final StaffRepository staffRepository;
    private final ApplicationLikeRepository applicationLikeRepository;

    @Transactional
    public void like(Long applicationId, Long staffId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        if (applicationLikeRepository.existsByApplicationAndStaff(application, staff)) {
            throw new AppException(ErrorCode.DUPLICATE_LIKE);
        }

        applicationLikeRepository.save(
                ApplicationLike.builder()
                        .application(application)
                        .staff(staff)
                        .build()
        );
        application.incrementLikeCount();
    }

    @Transactional
    public void cancelLike(Long applicationId, Long staffId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICATION_NOT_FOUND));
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

        ApplicationLike like = applicationLikeRepository.findByApplicationAndStaff(application, staff)
                .orElseThrow(() -> new AppException(ErrorCode.LIKE_NOT_FOUND));

        applicationLikeRepository.delete(like);
        application.decrementLikeCount();
    }
}
