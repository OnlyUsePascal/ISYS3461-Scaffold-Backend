package isys3461.lab_test2.alpha_api.internal.service;

import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;

public interface AlphaInternalService {
  void testKafkaNotify(TestKafkaNotifyReq req);

  TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req);
}
