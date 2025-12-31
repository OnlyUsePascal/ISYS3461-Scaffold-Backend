package isys3461.lab_test_2.beta_api.external.service;

import isys3461.lab_test_2.beta_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test_2.beta_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsReq;
import isys3461.lab_test_2.beta_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsRes;
import isys3461.lab_test_2.beta_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test_2.beta_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;

public interface BetaExternalService {
  TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req);
  
  void testKafkaNotify(TestKafkaNotifyReq req);

  ListBetaByIdsRes listBetaByIds(ListBetaByIdsReq req);
}
