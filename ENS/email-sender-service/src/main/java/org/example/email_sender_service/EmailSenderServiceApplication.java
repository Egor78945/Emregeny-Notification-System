package org.example.email_sender_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "EmailSenderService", description = "Сервис, выполняюший рассылки сообщений по email"))
public class EmailSenderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderServiceApplication.class, args);
    }

}
