package org.user_api_service.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user_api_service.app.models.requestModels.AuthenticationRequestModel;
import org.user_api_service.app.models.requestModels.RegistrationRequestModel;
import org.user_api_service.app.services.authentication.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ens/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> login(@RequestBody AuthenticationRequestModel authModel) {
        return ResponseEntity.ok(authenticationService.authenticate(authModel));
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequestModel requestModel) {
        Long id = authenticationService.register(requestModel);
        return ResponseEntity.ok(String.format("User has been registered by id %s", id));
    }
}
