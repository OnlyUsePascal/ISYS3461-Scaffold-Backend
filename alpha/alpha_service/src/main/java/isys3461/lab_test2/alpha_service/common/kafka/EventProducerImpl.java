package isys3461.lab_test2.alpha_service.common.kafka;

import java.util.ArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import isys3461.lab_test2.alpha_api.external.dto.alpha.AlphaTopicRegistry;
import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.BetaTopicRegistry;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;
import isys3461.lab_test2.alpha_api.external.service.EventProducer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventProducerImpl implements EventProducer {
  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  private ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private void send(String reqTopic, Object reqData) throws Exception {
    var reqBytes = objectMapper.writeValueAsBytes(reqData);
    kafkaTemplate.send(reqTopic, reqBytes);
  }

  private <T> T sendAndReceive(String reqTopic, String resTopic,
      Object reqData, Class<T> resType) throws Exception {

    byte[] reqBytes = objectMapper.writeValueAsBytes(reqData);
    ProducerRecord<String, Object> req = new ProducerRecord<>(reqTopic, reqBytes);
    req.headers().add(KafkaHeaders.REPLY_TOPIC, resTopic.getBytes());

    RequestReplyFuture<String, Object, Object> replyFuture = replyingKafkaTemplate.sendAndReceive(req);
    ConsumerRecord<String, Object> res = replyFuture.get();
    var resBytes = (byte[]) res.value();
    return objectMapper.readValue(resBytes, resType);
  }

  @Override
  public void testKafkaNotify(TestKafkaNotifyReq req) {
    log.info("testKafkaNotify {}", req);
    try {
      send(AlphaTopicRegistry.TEST_NOTIFY, req);
    } catch (Exception e) {
      log.error("testKafkaNotify error", e);
    }
  }

  @Override
  public TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req) {
    log.info("testKafkaRequestReply {}", req);
    try {
      var res = sendAndReceive(BetaTopicRegistry.TEST_REQUEST_REPLY_REQ, BetaTopicRegistry.TEST_REQUEST_REPLY_RES,
          req, TestKafkaRequestReplyRes.class);
      log.info("testKafkaRequestReply {}", res);
      return res;

    } catch (Exception e) {
      log.error("testKafkaRequestReply: {}", e.getMessage());
      return new TestKafkaRequestReplyRes(0);
    }
  }
}
