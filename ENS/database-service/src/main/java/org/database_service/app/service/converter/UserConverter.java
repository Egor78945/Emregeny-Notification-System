package org.database_service.app.service.converter;

import com.example.grpc.DatabaseService;
import org.database_service.app.model.entity.Role;
import org.database_service.app.model.entity.User;

import java.util.List;

public class UserConverter {

    public static User convertSaveRequestToUser(DatabaseService.SaveUserRequest request) {
        return User
                .builder()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setRegisterDate(request.getRegDate())
                .build();
    }

    public static DatabaseService.GetUserDetailsResponse convertUserDetailsToGetUserDetailsResponse(User user, List<Role> roles) {
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

    public static DatabaseService.GetUserExistsResponse buildGetUserExistsResponse(boolean answer) {
        return DatabaseService.GetUserExistsResponse
                .newBuilder()
                .setExists(answer)
                .build();
    }

    public static DatabaseService.SaveUserResponse buildSaveUserResponse(Long id) {
        return DatabaseService.SaveUserResponse
                .newBuilder()
                .setId(id)
                .build();
    }
}
