package isys3461.lab_test2.alpha_service.common.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import isys3461.lab_test2.alpha_service.common.util.dto.ParseTokenDto.ParseTokenRes;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtServiceImpl implements JwtService {
  @Autowired
  private KeyService keyService;

  private final SignatureAlgorithm signatureAlgorithm = Jwts.SIG.RS256;
  private final long jwsExpiration = 1000L * 60 * 60 * 24 * 1; // 1 days

  private final KeyAlgorithm<PublicKey, PrivateKey> encryptionKeyAlgorithm = Jwts.KEY.RSA_OAEP_256;
  private final AeadAlgorithm encryptionAlgorithm = Jwts.ENC.A256GCM;

  @Override
  public ParseTokenRes parseJwsToken(String token) throws Exception {
    Claims claims = Jwts.parser()
        .verifyWith(keyService.getJwsPublickey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    var id = claims.getSubject();
    var email = claims.get("email", String.class);
    var role = claims.get("role", String.class);
    log.info("id {}, email {}, role {}", id, email, role);

    var expiration = claims.getExpiration();
    if (expiration.before(new Date())) {
      log.warn("Token is expired for user email {}", email);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token expired");
    }

    return new ParseTokenRes(id, email, role);
  }
}
