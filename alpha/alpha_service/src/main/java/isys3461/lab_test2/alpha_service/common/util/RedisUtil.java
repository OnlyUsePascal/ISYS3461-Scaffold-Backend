package isys3461.lab_test2.alpha_service.common.util;

import java.util.UUID;

public class RedisUtil {
  static private final String ALPHA_GET_SINGLE_PREFIX = "alpha:get_single:";

  static public String buildGetAlphaKey(UUID id) {
    return ALPHA_GET_SINGLE_PREFIX + id.toString();
  } 

  
}
