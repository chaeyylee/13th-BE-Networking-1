package cotato.backend.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.dto.request.LikeRequest;
import cotato.backend.service.ApplicationLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "좋아요", description = "서류 좋아요 관련 API")
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationLikeController {

    private final ApplicationLikeService applicationLikeService;

    @Operation(summary = "서류 좋아요")
    @PostMapping("/{applicationId}/likes")
    public ResponseEntity<DataResponse<Void>> like(
            @Parameter(description = "서류 ID", example = "1")
            @PathVariable(name = "applicationId") Long applicationId,
            @RequestBody @Valid LikeRequest request
    ) {
        applicationLikeService.like(applicationId, request.getStaffId());
        return ResponseEntity.ok(DataResponse.ok());
    }

    @Operation(summary = "서류 좋아요 취소")
    @DeleteMapping("/{applicationId}/likes")
    public ResponseEntity<DataResponse<Void>> cancelLike(
            @Parameter(description = "서류 ID", example = "1")
            @PathVariable(name = "applicationId") Long applicationId,
            @RequestBody @Valid LikeRequest request
    ) {
        applicationLikeService.cancelLike(applicationId, request.getStaffId());
        return ResponseEntity.ok(DataResponse.ok());
    }
}
