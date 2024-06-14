package org.user_api_service.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.user_api_service.app.models.requestModels.AuthenticationRequestModel;
import org.user_api_service.app.services.UserService;
import org.user_api_service.app.services.authentication.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> login(@RequestBody AuthenticationRequestModel authModel) {
        return ResponseEntity.ok(authenticationService.authenticate(authModel));
    }
}
