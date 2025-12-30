package isys3461.lab_test2.alpha_service.common.util;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyService {
  PublicKey getJwsPublickey() throws Exception;
}
