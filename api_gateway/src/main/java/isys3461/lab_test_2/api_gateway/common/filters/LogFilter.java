package isys3461.lab_test_2.api_gateway.common.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Order(-5)
public class LogFilter implements GlobalFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    log.info("--> Request: {} {}",
        exchange.getRequest().getMethod(),
        exchange.getRequest().getURI());

    return chain
        .filter(exchange)
        .then(Mono.fromRunnable(() -> {
          log.info("<-- Response: {}", exchange.getResponse().getStatusCode());
        }));
  }
}
