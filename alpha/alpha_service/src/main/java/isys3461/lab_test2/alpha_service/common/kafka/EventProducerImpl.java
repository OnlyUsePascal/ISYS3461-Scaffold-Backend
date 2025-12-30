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

  private Object sendAndReceive(String reqTopic, String resTopic, Object data) throws Exception {
    ProducerRecord<String, Object> req = new ProducerRecord<>(reqTopic, data);
    req.headers().add(KafkaHeaders.REPLY_TOPIC, resTopic.getBytes());

    RequestReplyFuture<String, Object, Object> replyFuture = replyingKafkaTemplate.sendAndReceive(req);
    ConsumerRecord<String, Object> res = replyFuture.get();
    return res.value();
  }

  @Override
  public void testKafkaNotify(TestKafkaNotifyReq req) {
    log.info("testKafkaNotify {}", req);
    kafkaTemplate.send(AlphaTopicRegistry.TEST_NOTIFY, req);
  }

  @Override
  public TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req) {
    log.info("testKafkaRequestReply {}", req);
    try {
      var res = (TestKafkaRequestReplyRes) sendAndReceive(
          BetaTopicRegistry.TEST_REQUEST_REPLY_REQ, BetaTopicRegistry.TEST_REQUEST_REPLY_RES, req);
      log.info("testKafkaRequestReply {}", res);
      return res;

    } catch (Exception e) {
      log.error("testKafkaRequestReply: {}", e.getMessage());
      return new TestKafkaRequestReplyRes(0);
    }
  }
}
