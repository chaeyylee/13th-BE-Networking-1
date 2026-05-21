package cotato.backend.dto.response;

import cotato.backend.entity.Staff;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class StaffResponse {

    @Schema(example = "정찬민")
    private final String name;

    @Schema(example = "30")
    private final Integer age;

    @Schema(example = "01098765432")
    private final String phoneNumber;

    @Schema(example = "파트장")
    private final String role;

    public StaffResponse(Staff staff) {
        this.name = staff.getName();
        this.age = staff.getAge();
        this.phoneNumber = staff.getPhoneNumber();
        this.role = staff.getRole();
    }
}
