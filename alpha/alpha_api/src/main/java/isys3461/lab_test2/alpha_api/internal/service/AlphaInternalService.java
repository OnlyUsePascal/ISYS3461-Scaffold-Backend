package isys3461.lab_test2.alpha_api.internal.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;
import isys3461.lab_test2.alpha_api.internal.dto.CreateAlphaDto.CreateAlphaReq;
import isys3461.lab_test2.alpha_api.internal.dto.GetAlphaDto.GetAlphaRes;
import isys3461.lab_test2.alpha_api.internal.dto.GetAlphaWithBetaDto.GetAlphaWithBetaRes;
import isys3461.lab_test2.alpha_api.internal.dto.ListAlphasDto.ListAlphasReq;
import isys3461.lab_test2.alpha_api.internal.dto.ListAlphasDto.ListAlphasRes;
import isys3461.lab_test2.alpha_api.internal.dto.UpdateAlphaDto.UpdateAlphaReq;

public interface AlphaInternalService {
  void testKafkaNotify(TestKafkaNotifyReq req);

  TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req);

  Page<ListAlphasRes> listAlphas(ListAlphasReq req);

  GetAlphaRes getAlpha(UUID id);

  GetAlphaWithBetaRes getAlphaWithBeta(UUID id);

  UUID createAlpha(CreateAlphaReq req);

  void updateAlpha(UUID id, UpdateAlphaReq req);

  void deleteAlpha(UUID id);
}
