package isys3461.lab_test_2.user_service.common.util;

import isys3461.lab_test_2.user_service.common.util.dto.CreateTokenDto.CreateJwsTokenReq;
import isys3461.lab_test_2.user_service.common.util.dto.ParseTokenDto.ParseTokenRes;

public interface JwtService {
  String createJwsToken(CreateJwsTokenReq req) throws Exception;

  ParseTokenRes parseJwsToken(String token) throws Exception;
}
