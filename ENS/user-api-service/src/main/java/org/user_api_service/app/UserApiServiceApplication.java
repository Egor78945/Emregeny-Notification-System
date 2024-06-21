package org.user_api_service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class UserApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiServiceApplication.class, args);
    }

}
