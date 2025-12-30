package isys3461.lab_test_2.user_service.common.util;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyService {
  PrivateKey getJwsPrivateKey() throws Exception;

  PublicKey getJwsPublickey() throws Exception;
}
