package org.user_api_service.app.controller.exception_handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.user_api_service.app.annotation.MailControllerExceptionHandler;
import org.user_api_service.app.exception.RequestCancelledException;
import org.user_api_service.app.exception.WrongDataException;

import java.util.concurrent.ExecutionException;

@ControllerAdvice(annotations = MailControllerExceptionHandler.class)
@Slf4j
public class MailControllerAdvice {
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> jsonProcessingExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(505));
    }

    @ExceptionHandler(WrongDataException.class)
    public ResponseEntity<String> wrongDataExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(RequestCancelledException.class)
    public ResponseEntity<String> requestCancelledExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<String> executionExceptionHandler(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(505));
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<String> interruptedExceptionHandler(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(505));
    }
}
