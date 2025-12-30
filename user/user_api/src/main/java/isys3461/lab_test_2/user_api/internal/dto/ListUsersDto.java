package isys3461.lab_test_2.user_api.internal.dto;

import java.util.UUID;

public class ListUsersDto {
  public static record ListUsersRes(
      UUID id,
      String name,
      String address) {
  }
}
