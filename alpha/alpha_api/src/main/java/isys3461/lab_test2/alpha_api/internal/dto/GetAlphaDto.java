package isys3461.lab_test2.alpha_api.internal.dto;

import java.util.UUID;

public class GetAlphaDto {
  

  static public record GetAlphaRes(
      UUID id,
      String name,
      Double price) {
  }
}
