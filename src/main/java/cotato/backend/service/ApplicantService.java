package cotato.backend.service;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.dto.request.ApplicantUpdateRequest;
import cotato.backend.dto.response.ApplicantResponse;
import cotato.backend.entity.Applicant;
import cotato.backend.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantResponse getById(Long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICANT_NOT_FOUND));
        return new ApplicantResponse(applicant);
    }

    @Transactional
    public ApplicantResponse update(Long id, ApplicantUpdateRequest request) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPLICANT_NOT_FOUND));
        applicant.update(request.getName(), request.getAge(), request.getPhoneNumber());
        return new ApplicantResponse(applicant);
    }
}
