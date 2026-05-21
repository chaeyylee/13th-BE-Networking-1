package cotato.backend.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.dto.request.ApplicantUpdateRequest;
import cotato.backend.dto.response.ApplicantResponse;
import cotato.backend.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지원자", description = "지원자 관련 API")
@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @Operation(summary = "지원자 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ApplicantResponse>> getById(
            @Parameter(description = "지원자 ID", example = "1")
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(DataResponse.from(applicantService.getById(id)));
    }

    @Operation(summary = "지원자 정보 수정")
    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<ApplicantResponse>> update(
            @Parameter(description = "지원자 ID", example = "1")
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid ApplicantUpdateRequest request
    ) {
        return ResponseEntity.ok(DataResponse.from(applicantService.update(id, request)));
    }
}
