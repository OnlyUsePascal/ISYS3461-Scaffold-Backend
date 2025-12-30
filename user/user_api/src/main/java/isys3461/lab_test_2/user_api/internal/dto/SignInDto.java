package isys3461.lab_test_2.user_api.internal.dto;

import jakarta.validation.constraints.NotNull;

public class SignInDto {
  public static record SignInReq(
      @NotNull String username,

      @NotNull String password) {
  }

  public static record SignInRes(String token, String refreshToken) {
  };
}
