package org.database_service.app.services.converters;

import com.example.grpc.DatabaseService;
import org.database_service.app.model.entities.Role;

import java.util.List;

public class RoleConverter {
    public static List<Role> convert(DatabaseService.SaveUserRequest request) {
        return request
                .getRolesList()
                .stream()
                .map(r -> new Role(r))
                .toList();
    }
}
