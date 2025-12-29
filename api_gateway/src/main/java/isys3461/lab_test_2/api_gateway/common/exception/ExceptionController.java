package isys3461.lab_test_2.api_gateway.common.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Order(-1)
public class ExceptionController extends AbstractErrorWebExceptionHandler {
  public ExceptionController(ErrorAttributes errorAttributes, ApplicationContext applicationContext,
      ServerCodecConfigurer serverCodecConfigurer) {
    super(errorAttributes, new WebProperties.Resources(), applicationContext);
    super.setMessageWriters(serverCodecConfigurer.getWriters());
    super.setMessageReaders(serverCodecConfigurer.getReaders());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), (request) -> {
      // error
      Throwable err = getError(request);
      var statusCode = (err instanceof ResponseStatusException) ? ((ResponseStatusException) err).getStatusCode()
          : HttpStatus.INTERNAL_SERVER_ERROR;
      // err.printStackTrace();

      // response
      var errProperties = getErrorAttributes(request, ErrorAttributeOptions.of(
          ErrorAttributeOptions.Include.PATH,
          ErrorAttributeOptions.Include.STATUS,
          ErrorAttributeOptions.Include.ERROR,
          ErrorAttributeOptions.Include.MESSAGE));

      return ServerResponse
          .status(statusCode)
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(errProperties));
    });
  }
}
