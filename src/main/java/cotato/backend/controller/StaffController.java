package cotato.backend.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.dto.request.StaffCreateRequest;
import cotato.backend.dto.request.StaffUpdateRequest;
import cotato.backend.dto.response.StaffResponse;
import cotato.backend.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "운영진", description = "운영진 관련 API")
@RestController
@RequestMapping("/staffs")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @Operation(summary = "운영진 등록")
    @ApiResponse(responseCode = "201", description = "운영진 등록 성공")
    @PostMapping
    public ResponseEntity<DataResponse<Long>> create(
            @RequestBody @Valid StaffCreateRequest request
    ) {
        Long id = staffService.create(request);
        return ResponseEntity.status(201).body(DataResponse.created(id));
    }

    @Operation(summary = "운영진 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<StaffResponse>> getById(
            @Parameter(description = "운영진 ID", example = "1")
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(DataResponse.from(staffService.getById(id)));
    }

    @Operation(summary = "운영진 정보 수정")
    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<StaffResponse>> update(
            @Parameter(description = "운영진 ID", example = "1")
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid StaffUpdateRequest request
    ) {
        return ResponseEntity.ok(DataResponse.from(staffService.update(id, request)));
    }
}
