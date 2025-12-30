package isys3461.lab_test_2.user_api.internal.dto;

import java.util.UUID;

public class GetUserDto {
  public static record GetUserRes(
      UUID id,
      String name,
      String address) {
  }
}
