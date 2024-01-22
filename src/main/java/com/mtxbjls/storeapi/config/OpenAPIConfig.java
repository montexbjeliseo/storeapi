package com.mtxbjls.storeapi.config;

import com.mtxbjls.storeapi.utils.Constants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = Constants.Docs.TITLE, version = Constants.Docs.VERSION, description = Constants.Docs.DESCRIPTION))
@SecurityScheme(
    name = Constants.Docs.BEARER_AUTH,
    type = SecuritySchemeType.HTTP,
    bearerFormat = Constants.Docs.BEARER_FORMAT,
    scheme = Constants.Docs.BEARER_SCHEME
)
public class OpenAPIConfig {
}
