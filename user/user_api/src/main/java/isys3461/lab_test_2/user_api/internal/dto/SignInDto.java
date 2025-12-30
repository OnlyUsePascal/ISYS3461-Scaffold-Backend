package isys3461.lab_test_2.user_api.internal.dto;

public class SignInDto {
  public static record SignInReq(
      String username,
      String password) {
  }

  public static record SignInRes(String token) {
  };
}
