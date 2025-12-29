package isys3461.lab_test2.alpha_service.alpha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isys3461.lab_test2.alpha_api.external.service.AlphaExternalService;
import isys3461.lab_test2.alpha_service.alpha.repo.AlphaRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlphaExternalServiceImpl implements AlphaExternalService {
  @Autowired
  private AlphaRepo alphaRepo;
}
