package cotato.backend.service;

import cotato.backend.common.exception.AppException;
import cotato.backend.common.exception.ErrorCode;
import cotato.backend.dto.request.StaffCreateRequest;
import cotato.backend.dto.request.StaffUpdateRequest;
import cotato.backend.dto.response.StaffResponse;
import cotato.backend.entity.Staff;
import cotato.backend.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffService {

    private final StaffRepository staffRepository;

    @Transactional
    public Long create(StaffCreateRequest request) {
        Staff staff = Staff.builder()
                .name(request.getName())
                .age(request.getAge())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();
        return staffRepository.save(staff).getId();
    }

    public StaffResponse getById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        return new StaffResponse(staff);
    }

    @Transactional
    public StaffResponse update(Long id, StaffUpdateRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        staff.update(request.getName(), request.getAge(), request.getPhoneNumber(), request.getRole());
        return new StaffResponse(staff);
    }
}
