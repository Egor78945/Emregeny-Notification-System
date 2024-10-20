package org.user_api_service.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "UserApiService", description = "Сервис, выполняющий аутентификацию и регистрацию пользователей, а так-же принимающий от них запросы"))
public class UserApiServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApiServiceApplication.class, args);
    }
}
