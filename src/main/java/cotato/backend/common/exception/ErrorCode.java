package cotato.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "COMMON-001"),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못되었습니다.", "COMMON-002"),
	DUPLICATE_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 서류입니다.", "LIKE-001"),

	// 404
	NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없습니다.", "COMMON-003"),
	APPLICANT_NOT_FOUND(HttpStatus.NOT_FOUND, "지원자를 찾을 수 없습니다.", "APPLICANT-001"),
	APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "서류를 찾을 수 없습니다.", "APPLICATION-001"),
	STAFF_NOT_FOUND(HttpStatus.NOT_FOUND, "운영진을 찾을 수 없습니다.", "STAFF-001"),
	LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요 내역을 찾을 수 없습니다.", "LIKE-002"),

	// 500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 에러가 발생하였습니다.", "COMMON-004"),
	;

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
