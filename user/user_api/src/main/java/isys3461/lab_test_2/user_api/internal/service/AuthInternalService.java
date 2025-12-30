package isys3461.lab_test_2.user_api.internal.service;

import isys3461.lab_test_2.user_api.internal.dto.SignInDto.SignInReq;
import isys3461.lab_test_2.user_api.internal.dto.SignInDto.SignInRes;
import isys3461.lab_test_2.user_api.internal.dto.SignUpDto.SignUpReq;
import isys3461.lab_test_2.user_api.internal.dto.SignUpDto.SignUpRes;

public interface AuthInternalService {
  SignInRes signIn(SignInReq req);

  SignUpRes signUp(SignUpReq req);
}
