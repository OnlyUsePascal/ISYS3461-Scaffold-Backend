package isys3461.lab_test_2.user_service.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isys3461.lab_test_2.user_api.external.service.AuthExternalService;
import isys3461.lab_test_2.user_service.auth.repo.AuthRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserExternalServiceImpl implements AuthExternalService {
  @Autowired
  private AuthRepo alphaRepo;
}
