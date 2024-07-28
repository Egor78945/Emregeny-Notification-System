package org.user_api_service.app.controller.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user_api_service.app.annotation.AuthControllerExceptionHandler;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.model.request_model.AuthenticationRequestModel;
import org.user_api_service.app.model.request_model.RegistrationRequestModel;
import org.user_api_service.app.service.authentication.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ens/auth")
@AuthControllerExceptionHandler
public class AuthController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> login(@RequestBody AuthenticationRequestModel authModel) throws JsonProcessingException {
        return ResponseEntity.ok(authenticationService.authenticate(authModel));
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequestModel requestModel) throws WrongDataException {
        Long id = authenticationService.register(requestModel);
        return ResponseEntity.ok(String.format("User has been registered by id %s", id));
    }
}
