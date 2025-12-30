package isys3461.lab_test2.alpha_service.alpha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;
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
  public ResponseEntity<String> testErr() {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bruh no");
  }

  @GetMapping("/test/kafka-request-reply")
  public ResponseEntity<GenericResponseDto<TestKafkaRequestReplyRes>> testKafkaReqRes() {
    var res = alphaInternalService.testKafkaRequestReply(
        new TestKafkaRequestReplyReq(1, 2));
    return GenericResponseDto.getGenericResponseDto(res);
  }

  @GetMapping("/test/kafka-notify")
  public ResponseEntity<GenericResponseDto<String>> testKafkaNotify() {
    alphaInternalService.testKafkaNotify(
        new TestKafkaNotifyReq("hello there!"));
    return GenericResponseDto.getGenericResponseDto("test kafka notify");
  }

}
