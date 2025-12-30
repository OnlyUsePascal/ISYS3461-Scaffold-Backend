package isys3461.lab_test_2.user_service.common.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {
  @ExceptionHandler({ Exception.class, RuntimeException.class })
  public ResponseEntity<?> defaultException(Exception e) {
    e.printStackTrace();

    var code = (e instanceof AccessDeniedException)
        ? HttpStatus.FORBIDDEN
        : HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(
        new ExceptionDto(e.getMessage(), code.value()),
        code);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> handleNotFound(ResponseStatusException e) {
    e.printStackTrace();
    return new ResponseEntity<>(new ExceptionDto(
        e.getReason(),
        e.getStatusCode().value()),
        e.getStatusCode());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException err) {
    err.printStackTrace();

    var sanitizedErrMsg = err.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getObjectName() + "." + error.getField() + ": " + error.getDefaultMessage())
        .reduce((error1, error2) -> error1 + ",\n" + error2)
        .orElse("Validation failed");
    return new ResponseEntity<>(
        new ExceptionDto(sanitizedErrMsg,
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }
}
