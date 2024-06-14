package org.user_api_service.app.services.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.user_api_service.app.configurations.jwt.JWTCore;
import org.user_api_service.app.models.requestModels.AuthenticationRequestModel;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTCore jwtCore;

    public String authenticate(AuthenticationRequestModel authModel) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtCore.generateToken(authentication);
    }
}
