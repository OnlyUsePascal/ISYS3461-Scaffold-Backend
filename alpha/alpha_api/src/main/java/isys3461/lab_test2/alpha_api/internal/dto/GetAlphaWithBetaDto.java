package isys3461.lab_test2.alpha_api.internal.dto;

import java.util.List;
import java.util.UUID;

public class GetAlphaWithBetaDto {
  static public record GetAlphaWithBetaResBeta(
      UUID id,
      String name) {
  }

  static public record GetAlphaWithBetaRes(
      UUID id,
      String name,
      Double price,
      List<GetAlphaWithBetaResBeta> betas) {
  }
}
