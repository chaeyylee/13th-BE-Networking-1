package cotato.backend.controller;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.dto.request.ApplicationCreateRequest;
import cotato.backend.dto.response.ApplicationDetailResponse;
import cotato.backend.dto.response.ApplicationSummaryResponse;
import cotato.backend.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "지원 서류", description = "지원 서류 관련 API")
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "서류 등록")
    @ApiResponse(responseCode = "201", description = "서류 등록 성공")
    @PostMapping
    public ResponseEntity<DataResponse<Long>> create(
            @RequestBody @Valid ApplicationCreateRequest request
    ) {
        Long id = applicationService.create(request);
        return ResponseEntity.status(201).body(DataResponse.created(id));
    }

    @Operation(summary = "서류 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ApplicationDetailResponse>> getById(
            @Parameter(description = "서류 ID", example = "1")
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok(DataResponse.from(applicationService.getById(id)));
    }

    @Operation(
            summary = "서류 리스트 조회 (필터링)",
            description = "filterBy: `likes`(좋아요순) / `gisu`(기수별) / `gisu+likes`(기수+좋아요순)"
    )
    @GetMapping
    public ResponseEntity<DataResponse<List<ApplicationSummaryResponse>>> getList(
            @Parameter(description = "필터 기준 (likes / gisu / gisu+likes)", example = "likes")
            @RequestParam(name = "filterBy") String filterBy,

            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(name = "page", defaultValue = "1") Integer page,

            @Parameter(description = "기수 (filterBy가 gisu 또는 gisu+likes일 때만 입력)", example = "13")
            @RequestParam(name = "period", required = false) Integer period
    ) {
        return ResponseEntity.ok(DataResponse.from(applicationService.getList(filterBy, page, period)));
    }
}
