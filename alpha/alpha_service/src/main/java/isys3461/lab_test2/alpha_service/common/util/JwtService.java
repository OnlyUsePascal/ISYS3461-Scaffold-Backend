package isys3461.lab_test2.alpha_service.common.util;

import isys3461.lab_test2.alpha_service.common.util.dto.CreateTokenDto.CreateJwsTokenReq;
import isys3461.lab_test2.alpha_service.common.util.dto.ParseTokenDto.ParseTokenRes;

public interface JwtService {
  ParseTokenRes parseJwsToken(String token) throws Exception;
}
