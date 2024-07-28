package org.user_api_service.app.service.converter;

import com.example.grpc.DatabaseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.user_api_service.app.enums.UserRole;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.model.request_model.RegistrationRequestModel;
import org.user_api_service.app.model.request_model.SaveUserRequestModel;
import org.user_api_service.app.model.respone_model.User;
import org.user_api_service.app.service.UserDetailsImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserConverter {
    public static SaveUserRequestModel convertRegisterModelToSaveRequestModel(RegistrationRequestModel requestModel, PasswordEncoder passwordEncoder) {
        return new SaveUserRequestModel(requestModel.getEmail(), passwordEncoder.encode(requestModel.getPassword()), new Date(System.currentTimeMillis()).toString(), Arrays.asList("USER"));
    }

    public static UserDetailsImpl convertGetUserDetailsResponseToUserDetailsImpl(DatabaseService.GetUserDetailsResponse response) throws WrongDataException {
        return new UserDetailsImpl(response.getId(), response.getEmail(), response.getPassword(), convertStringToRole(response.getRolesList()), response.getRegDate());
    }

    private static UserRole convertStringToRole(String role) throws WrongDataException {
        switch (role) {
            case "USER":
                return UserRole.USER;
            case "ADMIN":
                return UserRole.ADMIN;
            default:
                throw new WrongDataException("Role is unknown");
        }
    }

    private static List<UserRole> convertStringToRole(List<String> strings) throws WrongDataException {
        List<UserRole> roles = new ArrayList<>();
        for (String r : strings) {
            roles.add(convertStringToRole(r));
        }
        return roles;
    }

    public User convertUserDetailsToUserCache(UserDetailsImpl userDetails){
        return new User(userDetails);
    }
}
