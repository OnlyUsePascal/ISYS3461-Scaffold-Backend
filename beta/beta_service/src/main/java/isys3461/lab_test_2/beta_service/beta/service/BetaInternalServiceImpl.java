package isys3461.lab_test_2.beta_service.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isys3461.lab_test_2.beta_api.external.service.EventProducer;
import isys3461.lab_test_2.beta_api.internal.service.BetaInternalService;
import isys3461.lab_test_2.beta_service.beta.repo.BetaRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class BetaInternalServiceImpl implements BetaInternalService {
  @Autowired
  private BetaRepo betaRepo;

  @Autowired
  private EventProducer eventProducer;
}
