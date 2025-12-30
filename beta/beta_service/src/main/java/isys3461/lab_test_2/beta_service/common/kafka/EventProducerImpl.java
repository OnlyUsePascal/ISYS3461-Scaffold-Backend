package isys3461.lab_test_2.beta_service.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import isys3461.lab_test_2.beta_api.external.service.EventProducer;
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

  private byte[] sendAndReceive(String reqTopic, String resTopic, Object data) throws Exception {
    byte[] reqBytes = objectMapper.writeValueAsBytes(data);
    ProducerRecord<String, Object> req = new ProducerRecord<>(reqTopic, reqBytes);
    req.headers().add(KafkaHeaders.REPLY_TOPIC, resTopic.getBytes());

    RequestReplyFuture<String, Object, Object> replyFuture = replyingKafkaTemplate.sendAndReceive(req);
    ConsumerRecord<String, Object> res = replyFuture.get();
    return (byte[]) res.value();
  }


}
