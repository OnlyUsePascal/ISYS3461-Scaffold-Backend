package isys3461.lab_test2.alpha_api.external.service;

import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsRes;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;

public interface EventProducer {
  void testKafkaNotify(TestKafkaNotifyReq req);

  TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req);

  ListBetaByIdsRes listBetaByIds(ListBetaByIdsReq req);
}
