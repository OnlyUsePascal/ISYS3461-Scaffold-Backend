package isys3461.lab_test_2.user_service.common.util.dto;

public class ParseTokenDto {
  static public record ParseTokenRes(String id, String email, String role) {
  }
}
