package isys3461.lab_test_2.beta_service.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isys3461.lab_test_2.beta_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test_2.beta_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsReq;
import isys3461.lab_test_2.beta_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsRes;
import isys3461.lab_test_2.beta_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsResItem;
import isys3461.lab_test_2.beta_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test_2.beta_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;
import isys3461.lab_test_2.beta_api.external.service.BetaExternalService;
import isys3461.lab_test_2.beta_service.beta.repo.BetaRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BetaExternalServiceImpl implements BetaExternalService {
  @Autowired
  private BetaRepo betaRepo;

  @Override
  public void testKafkaNotify(TestKafkaNotifyReq req) {
    log.info("receive alpha noti {}", req);
  }

  @Override
  public TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req) {
    return new TestKafkaRequestReplyRes(req.num1() + req.num2());
  }

  @Override
  public ListBetaByIdsRes listBetaByIds(ListBetaByIdsReq req) {
    var betas = betaRepo.findAll();

    var resItems = betas.stream().map(b -> {
      return new ListBetaByIdsResItem(b.getId(), b.getName());
    }).toList();
    return new ListBetaByIdsRes(resItems);
  }
}
