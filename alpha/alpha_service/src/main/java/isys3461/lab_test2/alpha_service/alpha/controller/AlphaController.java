package isys3461.lab_test2.alpha_service.alpha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import isys3461.lab_test2.alpha_api.internal.service.AlphaInternalService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AlphaController {
  @Autowired
  private AlphaInternalService alphaInternalService;

  @Value("${spring.application.name}")
  private String appName;

  @GetMapping("/greeting")
  public ResponseEntity<String> greeting() {
    return ResponseEntity.ok("hello from service + " + appName);
  }

}
