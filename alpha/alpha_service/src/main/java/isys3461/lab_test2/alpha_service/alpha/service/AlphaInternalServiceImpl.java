package isys3461.lab_test2.alpha_service.alpha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;
import isys3461.lab_test2.alpha_api.external.service.EventProducer;
import isys3461.lab_test2.alpha_api.internal.service.AlphaInternalService;
import isys3461.lab_test2.alpha_service.alpha.repo.AlphaRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class AlphaInternalServiceImpl implements AlphaInternalService {
  @Autowired
  private AlphaRepo alphaRepo;

  @Autowired
  private EventProducer eventProducer;

  @Override
  public void testKafkaNotify(TestKafkaNotifyReq req) {
    eventProducer.testKafkaNotify(req);
  }

  @Override
  public TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req) {
    var res = eventProducer.testKafkaRequestReply(req);
    return res;
  }
}
