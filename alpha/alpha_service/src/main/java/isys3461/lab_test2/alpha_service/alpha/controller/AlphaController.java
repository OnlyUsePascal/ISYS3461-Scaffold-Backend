package isys3461.lab_test2.alpha_service.alpha.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;
import isys3461.lab_test2.alpha_api.internal.dto.CreateAlphaDto.CreateAlphaReq;
import isys3461.lab_test2.alpha_api.internal.dto.GetAlphaDto.GetAlphaRes;
import isys3461.lab_test2.alpha_api.internal.dto.GetAlphaWithBetaDto.GetAlphaWithBetaRes;
import isys3461.lab_test2.alpha_api.internal.dto.ListAlphasDto.ListAlphasReq;
import isys3461.lab_test2.alpha_api.internal.dto.ListAlphasDto.ListAlphasRes;
import isys3461.lab_test2.alpha_api.internal.dto.UpdateAlphaDto.UpdateAlphaReq;
import isys3461.lab_test2.alpha_api.internal.service.AlphaInternalService;
import isys3461.lab_test2.alpha_service.common.http.GenericResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AlphaController {
  @Autowired
  private AlphaInternalService alphaInternalService;

  @Value("${spring.application.name}")
  private String appName;

  @GetMapping("/test/greeting")
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

  @GetMapping("/")
  public ResponseEntity<GenericResponseDto<Page<ListAlphasRes>>> listAlphas(
      @RequestParam(defaultValue = "1") Integer pageNo,
      @RequestParam(defaultValue = "5") Integer pageSz) {
    var res = alphaInternalService.listAlphas(new ListAlphasReq(pageNo, pageSz));
    return GenericResponseDto.getGenericResponseDto(res);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponseDto<GetAlphaRes>> getAlpha(@PathVariable UUID id) {
    var res = alphaInternalService.getAlpha(id);
    return GenericResponseDto.getGenericResponseDto(res);
  }
  
  @GetMapping("/{id}/with-beta")
  public ResponseEntity<GenericResponseDto<GetAlphaWithBetaRes>> getAlphaWithBeta(@PathVariable UUID id) {
    var res = alphaInternalService.getAlphaWithBeta(id);
    return GenericResponseDto.getGenericResponseDto(res);
  }

  @PostMapping("/")
  public ResponseEntity<GenericResponseDto<UUID>> createAlpha(@RequestBody @Valid CreateAlphaReq req) {
    var res = alphaInternalService.createAlpha(req);
    return GenericResponseDto.getGenericResponseDto(res);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<GenericResponseDto<String>> updateAlpha(@PathVariable UUID id, UpdateAlphaReq req) {
    alphaInternalService.updateAlpha(id, req);
    return GenericResponseDto.getGenericResponseDto("alpha updated successfully");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponseDto<String>> deleteAlpha(@PathVariable UUID id) {
    alphaInternalService.deleteAlpha(id);
    return GenericResponseDto.getGenericResponseDto("alpha deleted successfully");
  }
}
