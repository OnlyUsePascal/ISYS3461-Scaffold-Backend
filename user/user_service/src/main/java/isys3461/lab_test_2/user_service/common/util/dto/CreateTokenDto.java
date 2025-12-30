package isys3461.lab_test_2.user_service.common.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CreateTokenDto {
  @Data
  @AllArgsConstructor
  static public class CreateJwsTokenReq {
    private String id;
    private String email;
    private String role;
  }
}
