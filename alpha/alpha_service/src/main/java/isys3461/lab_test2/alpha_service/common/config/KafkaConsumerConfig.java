package isys3461.lab_test2.alpha_service.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import isys3461.lab_test2.alpha_api.external.dto.beta.BetaTopicRegistry;

@Configuration
public class KafkaConsumerConfig {
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
      KafkaTemplate<String, Object> kafkaTemplate,
      ConsumerFactory<String, Object> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setReplyTemplate(kafkaTemplate);
    return factory;
  }

  // request-reply
  // listener
  @Bean
  public ConcurrentMessageListenerContainer<String, Object> replyListenerContainer(
      ConsumerFactory<String, Object> consumerFactory) {

    ContainerProperties containerProperties = new ContainerProperties(
        BetaTopicRegistry.TEST_REQUEST_REPLY_RES,
        BetaTopicRegistry.LIST_RES,
        "what");

    // Important: Optimize for single-reply usage
    // containerProperties.setGroupId("reply-group");

    return new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
  }
}
