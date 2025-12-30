package isys3461.lab_test2.alpha_service.common.util.dto;

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
