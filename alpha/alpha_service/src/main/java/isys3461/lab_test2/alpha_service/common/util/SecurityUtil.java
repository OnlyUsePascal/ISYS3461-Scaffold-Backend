package isys3461.lab_test2.alpha_service.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import isys3461.lab_test2.alpha_service.common.util.dto.ParseTokenDto.ParseTokenRes;

@Component
public class SecurityUtil {
  public static ParseTokenRes getUserClaims() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "failed to fetch user claims");
    }

    return (ParseTokenRes) auth.getPrincipal();
  }
}
