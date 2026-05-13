package cotato.backend.common.exception;

import cotato.backend.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 처리되지 않은 모든 예외를 잡는 핸들러
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllException(Exception e, HttpServletRequest request) {
		log.error("처리되지 않은 예외 발생: ", e);
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, request);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> handleAppCustomException(AppException e, HttpServletRequest request) {
		log.error("AppException 발생: {}", e.getErrorCode().getMessage());
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode(), request);
		return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
	}

	// @Valid 유효성 검사 실패 시
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(
			MethodArgumentNotValidException e, HttpServletRequest request) {
		log.error("유효성 검사 실패: {}", e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_PARAMETER, request);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
