package isys3461.lab_test2.alpha_api.internal.dto;

public class UpdateAlphaDto {
  static public record UpdateAlphaReq(
      String name,
      Double price) {
  }
}
