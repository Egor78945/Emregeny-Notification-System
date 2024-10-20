package org.user_api_service.app.controller.exception_handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.user_api_service.app.annotation.AuthControllerExceptionHandler;
import org.user_api_service.app.exception.WrongDataException;

@ControllerAdvice(annotations = AuthControllerExceptionHandler.class)
@Slf4j
public class AuthControllerAdvice {
    @ExceptionHandler(WrongDataException.class)
    public ResponseEntity<String> wrongDataExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> jsonProcessingExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }
}
