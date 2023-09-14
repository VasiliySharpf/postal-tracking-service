package net.example.postaltrackingservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.stereotype.Component;

@Component
@SecuritySchemes({
        @SecurityScheme(
                name = "admin",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                in = SecuritySchemeIn.HEADER,
                bearerFormat = "JWT")
})
public class SwaggerConfig {
}
