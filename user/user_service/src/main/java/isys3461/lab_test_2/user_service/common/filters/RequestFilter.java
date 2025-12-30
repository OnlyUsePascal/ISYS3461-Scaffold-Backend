package isys3461.lab_test_2.user_service.common.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import isys3461.lab_test_2.user_service.common.http.ExceptionDto;
import isys3461.lab_test_2.user_service.common.util.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestFilter extends OncePerRequestFilter {
  @Autowired
  private JwtService jwtService;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      try {
        // parse token
        String token = authHeader.substring(7);
        var claims = jwtService.parseJwsToken(token);

        log.info("Parsed claims {}", claims);

        // attach to context
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + claims.role()));
        var authentication = new UsernamePasswordAuthenticationToken(
            claims,
            null,
            authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        log.error("Failed to authenticate token: {}", e.getMessage(), e);
        sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Failed to authenticate token: " + e.getMessage());
        return;
      }
    } else {
      log.warn("no auth header found");
    }

    filterChain.doFilter(request, response);
  }

  private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
    response.setStatus(status.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    ExceptionDto errorDto = new ExceptionDto(message, status.value());
    String jsonResponse = objectMapper.writeValueAsString(errorDto);
    response.getWriter().write(jsonResponse);
  }
}