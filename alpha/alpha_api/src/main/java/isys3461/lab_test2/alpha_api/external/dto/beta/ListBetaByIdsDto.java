package isys3461.lab_test2.alpha_api.external.dto.beta;

import java.util.List;
import java.util.UUID;

public class ListBetaByIdsDto {
  static public record ListBetaByIdsReq(
      List<UUID> ids) {
  };

  static public record ListBetaByIdsResItem(
      UUID id,
      String name) {
  };

  static public record ListBetaByIdsRes(
      List<ListBetaByIdsResItem> items) {
  };
}
