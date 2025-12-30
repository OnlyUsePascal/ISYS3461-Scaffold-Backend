package isys3461.lab_test_2.user_service.common.http;

import org.springframework.http.ResponseEntity;

public record GenericResponseDto<T>(T data) {
  static public <T> ResponseEntity<GenericResponseDto<T>> getGenericResponseDto(T result) {
    return ResponseEntity.ok(new GenericResponseDto<T>(result));
  }
}
