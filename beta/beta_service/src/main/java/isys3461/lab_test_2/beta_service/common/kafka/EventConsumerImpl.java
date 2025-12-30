package isys3461.lab_test_2.beta_service.common.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import isys3461.lab_test_2.beta_api.external.dto.alpha.AlphaTopicRegistry;
import isys3461.lab_test_2.beta_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test_2.beta_api.external.dto.beta.BetaTopicRegistry;
import isys3461.lab_test_2.beta_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test_2.beta_api.external.service.BetaExternalService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventConsumerImpl {
  @Autowired
  private BetaExternalService betaExternalService;

  @Autowired
  private ObjectMapper objectMapper;

  @KafkaListener(topics = BetaTopicRegistry.TEST_REQUEST_REPLY_REQ)
  @SendTo(BetaTopicRegistry.TEST_REQUEST_REPLY_RES)
  public byte[] testKafkaRequestReply(byte[] reqBytes) {
    try {
      TestKafkaRequestReplyReq req = objectMapper.readValue(reqBytes, TestKafkaRequestReplyReq.class);
      log.info("testKafkaRequestReply: {}", req);
      var res = betaExternalService.testKafkaRequestReply(req);
      return objectMapper.writeValueAsBytes(res);
    } catch (Exception e) {
      log.error("testKafkaRequestReply error", e);
      return new byte[0];
    }
  }

  @KafkaListener(topics = AlphaTopicRegistry.TEST_NOTIFY)
  public void testKafkaNotify(byte[] reqBytes) {
    try {
      TestKafkaNotifyReq req = objectMapper.readValue(reqBytes, TestKafkaNotifyReq.class);
      log.info("testKafkaNotify: {}", req);
      betaExternalService.testKafkaNotify(req);
    } catch (Exception e) {
      log.error("testKafkaNotify error", e);
    }
  }
}
