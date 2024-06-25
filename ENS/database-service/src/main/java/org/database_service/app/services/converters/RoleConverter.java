package org.database_service.app.services.converters;

import org.database_service.app.model.entities.Role;

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
}
