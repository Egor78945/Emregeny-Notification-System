package org.user_api_service.app.service.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.user_api_service.app.configuration.jwt.JWTCore;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.model.request_model.AuthenticationRequestModel;
import org.user_api_service.app.model.request_model.RegistrationRequestModel;
import org.user_api_service.app.model.respone_model.User;
import org.user_api_service.app.service.UserDetailsImpl;
import org.user_api_service.app.service.UserService;
import org.user_api_service.app.service.converter.UserConverter;
import org.user_api_service.app.service.grpc.user_service.UserGRPCService;
import org.user_api_service.app.service.redis.RedisService;
import org.user_api_service.app.service.validator.UserValidationService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTCore jwtCore;
    private final UserGRPCService userGRPCService;
    private final UserService userService;
    private final RedisService redisService;

    public String authenticate(AuthenticationRequestModel authModel) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getEmail(), authModel.getPassword()));
        redisService.saveObject("current", new User((UserDetailsImpl) userService.loadUserByUsername(authModel.getEmail())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtCore.generateToken(authentication);
    }

    public Long register(RegistrationRequestModel requestModel) throws WrongDataException {
        if (UserValidationService.isValidRegistrationRequestModel(requestModel) && !userGRPCService.userExistsRequest(requestModel.getEmail()))
            return userGRPCService.saveUserRequest(UserConverter.convertRegisterModelToSaveRequestModel(requestModel, passwordEncoder));
        else
            throw new WrongDataException("Email or password is invalid");
    }

}
