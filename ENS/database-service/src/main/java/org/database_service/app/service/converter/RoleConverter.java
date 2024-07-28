package org.database_service.app.service.converter;

import com.example.grpc.DatabaseService;
import org.database_service.app.model.entity.Role;

import java.util.List;

public class RoleConverter {
    public static Role convertStringToRole(String roleString) {
        return new Role(roleString);
    }

    public static List<Role> convertStringToRole(List<String> stringRoles) {
        return stringRoles
                .stream()
                .map(r -> convertStringToRole(r))
                .toList();
    }

    public static DatabaseService.GetUserRolesResponse buildUserRolesResponse(List<String> roles){
        return DatabaseService.GetUserRolesResponse
                .newBuilder()
                .addAllRoles(roles)
                .build();
    }
}
