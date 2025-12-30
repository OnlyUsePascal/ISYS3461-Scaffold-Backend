package isys3461.lab_test_2.user_service.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import isys3461.lab_test_2.user_api.external.service.EventProducer;
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
}
