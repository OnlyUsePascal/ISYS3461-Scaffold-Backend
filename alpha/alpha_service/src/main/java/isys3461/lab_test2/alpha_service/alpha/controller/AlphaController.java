package isys3461.lab_test2.alpha_service.alpha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isys3461.lab_test2.alpha_api.internal.service.AlphaInternalService;
import isys3461.lab_test2.alpha_service.common.http.GenericResponseDto;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AlphaController {
  @Autowired
  private AlphaInternalService alphaInternalService;

  @Value("${spring.application.name}")
  private String appName;

  @GetMapping("/greeting")
  public ResponseEntity<GenericResponseDto<String>> greeting() {
    return GenericResponseDto.getGenericResponseDto("hello from service + " + appName);
  }

  @GetMapping("/test/err")
  public ResponseEntity<String> throwErr() {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bruh no");
  }

}
