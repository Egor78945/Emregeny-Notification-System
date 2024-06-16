package org.database_service.app.services.converters;

import com.example.grpc.DatabaseService;
import org.database_service.app.model.entities.User;

public class UserConverter {

    public static User convert(DatabaseService.SaveUserRequest request){
        return User
                .builder()
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setRegisterDate(request.getRegDate())
                .build();
    }
}
