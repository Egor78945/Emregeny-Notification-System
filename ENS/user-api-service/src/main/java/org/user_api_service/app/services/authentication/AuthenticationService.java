package org.user_api_service.app.services.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.user_api_service.app.configurations.jwt.JWTCore;
import org.user_api_service.app.exceptions.WrongDataException;
import org.user_api_service.app.models.requestModels.AuthenticationRequestModel;
import org.user_api_service.app.models.requestModels.RegistrationRequestModel;
import org.user_api_service.app.models.requestModels.SaveUserRequestModel;
import org.user_api_service.app.models.responeModels.User;
import org.user_api_service.app.services.UserDetailsImpl;
import org.user_api_service.app.services.UserService;
import org.user_api_service.app.services.converters.UserConverter;
import org.user_api_service.app.services.grpc.user_service.UserGRPCService;
import org.user_api_service.app.services.redis.RedisService;
import org.user_api_service.app.services.validators.UserValidationService;

import java.util.Arrays;
import java.util.Date;

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
        redisService.save("current", new User((UserDetailsImpl) userService.loadUserByUsername(authModel.getEmail())));
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
