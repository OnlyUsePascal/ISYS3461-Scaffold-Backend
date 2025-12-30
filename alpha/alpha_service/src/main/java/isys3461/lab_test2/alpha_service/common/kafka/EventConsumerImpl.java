package isys3461.lab_test2.alpha_service.common.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import isys3461.lab_test2.alpha_api.external.service.AlphaExternalService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventConsumerImpl {
  @Autowired
  private AlphaExternalService alphaExternalService;

}
