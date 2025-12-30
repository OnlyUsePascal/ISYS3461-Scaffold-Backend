package isys3461.lab_test_2.user_api.internal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SignUpDto {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SignUpReq {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    private String address;
  }

  public static record SignUpRes(String token) {
  };
}