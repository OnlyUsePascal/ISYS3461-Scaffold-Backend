package isys3461.lab_test2.alpha_service.common.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@SecurityScheme(name = "bearer-auth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@OpenAPIDefinition(servers = {
		@Server(url = "/api/${spring.application.name}", description = "Gateway URL"),
		@Server(url = "http://localhost:${server.port}", description = "Direct Service URL") // Optional
})
public class OpenApiConfig {

}
