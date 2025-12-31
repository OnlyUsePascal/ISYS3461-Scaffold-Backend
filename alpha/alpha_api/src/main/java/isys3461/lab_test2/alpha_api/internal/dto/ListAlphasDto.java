package isys3461.lab_test2.alpha_api.internal.dto;

import java.util.UUID;

public class ListAlphasDto {
  static public record ListAlphasReq(
    Integer pageNo,
    Integer pageSz
  ){}

  static public record ListAlphasRes(
    UUID id,
    String name,
    Double price
  ){}
  
}
