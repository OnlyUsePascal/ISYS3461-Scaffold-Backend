package isys3461.lab_test_2.user_service.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import isys3461.lab_test_2.user_api.internal.dto.SignInDto.SignInReq;
import isys3461.lab_test_2.user_api.internal.dto.SignInDto.SignInRes;
import isys3461.lab_test_2.user_api.internal.dto.SignUpDto.SignUpReq;
import isys3461.lab_test_2.user_api.internal.dto.SignUpDto.SignUpRes;
import isys3461.lab_test_2.user_api.internal.service.AuthInternalService;
import isys3461.lab_test_2.user_service.common.http.GenericResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/auth")
@Slf4j
public class AuthController {
  @Autowired
  private AuthInternalService authInternalService;

  @Value("${spring.application.name}")
  private String appName;

  @GetMapping("/greeting")
  public ResponseEntity<GenericResponseDto<String>> greeting() {
    return GenericResponseDto.getGenericResponseDto("hello from service + " + appName);
  }

  @GetMapping("/test/err")
  public ResponseEntity<String> testErr() {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bruh no");
  }

  @GetMapping("/test/header")
  @SecurityRequirement(name = "bearer-auth")
  public ResponseEntity<String> testHeader() {
    return ResponseEntity.ok("test auth header");
  }

  @GetMapping("/test/header-role")
  @SecurityRequirement(name = "bearer-auth")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<String> testHeaderWithRole() {
    return ResponseEntity.ok("test auth header with role");
  }

  @PostMapping("/sign-in")
  public ResponseEntity<GenericResponseDto<SignInRes>> signIn(@RequestBody SignInReq req) {
    var res = authInternalService.signIn(req);
    return GenericResponseDto.getGenericResponseDto(res);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<GenericResponseDto<SignUpRes>> signUp(@RequestBody SignUpReq req) {
    var res = authInternalService.signUp(req);
    return GenericResponseDto.getGenericResponseDto(res);
  }

}
