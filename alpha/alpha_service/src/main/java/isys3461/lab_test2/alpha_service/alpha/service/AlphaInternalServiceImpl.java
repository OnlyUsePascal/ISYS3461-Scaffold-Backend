package isys3461.lab_test2.alpha_service.alpha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isys3461.lab_test2.alpha_api.internal.service.AlphaInternalService;
import isys3461.lab_test2.alpha_service.alpha.repo.AlphaRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class AlphaInternalServiceImpl implements AlphaInternalService {
  @Autowired
  private AlphaRepo alphaRepo;
}
