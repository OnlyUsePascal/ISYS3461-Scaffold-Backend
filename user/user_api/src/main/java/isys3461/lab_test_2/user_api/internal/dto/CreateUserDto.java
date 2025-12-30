package isys3461.lab_test_2.user_api.internal.dto;

import java.util.UUID;

public class CreateUserDto {
  public static record CreateUserReq(
      UUID id,
      String name,
      String address
    ) {
  }

  public static record CreateUserRes(
      UUID id,
      String name,
      String address
    ) {
  }
}
