package cotato.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationListRequest {

    private String filterBy; // "likes", "gisu", "gisu+likes"
    private Integer page;    // 1부터 시작
    private Integer period;  // filterBy가 "gisu" 또는 "gisu+likes"일 때 사용
}
