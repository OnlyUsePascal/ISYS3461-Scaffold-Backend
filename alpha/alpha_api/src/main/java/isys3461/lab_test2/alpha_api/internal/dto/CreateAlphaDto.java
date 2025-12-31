package isys3461.lab_test2.alpha_api.internal.dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;

public class CreateAlphaDto {
  static public record CreateAlphaReq (
    @NonNull
    String name,

    @NonNull
    Double price
  ){}
}
