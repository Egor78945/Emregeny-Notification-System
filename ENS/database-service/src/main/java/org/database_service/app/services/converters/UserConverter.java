package org.database_service.app.services.converters;

import com.example.grpc.DatabaseService;
import org.database_service.app.model.entities.Role;
import org.database_service.app.model.entities.User;

import java.util.List;

public class UserConverter {

    public static User convert(DatabaseService.SaveUserRequest request) {
        return User
                .builder()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setRegisterDate(request.getRegDate())
                .build();
    }

    public static DatabaseService.GetUserDetailsResponse convertToGetUserDetailsResponse(User user, List<Role> roles) {
        return DatabaseService.GetUserDetailsResponse
                .newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .addAllRoles(roles
                        .stream()
                        .map(r -> r.getRole())
                        .toList())
                .setRegDate(user.getRegisterDate())
                .build();
    }
}
