package org.database_service.app.services.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.database_service.app.model.entities.User;
import org.database_service.app.services.RoleService;
import org.database_service.app.services.UserService;
import org.database_service.app.services.converters.RoleConverter;
import org.database_service.app.services.converters.UserConverter;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceGRPCImplementation extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void saveUser(DatabaseService.SaveUserRequest request, StreamObserver<DatabaseService.SaveUserResponse> responseObserver) {
        User saved = userService.saveUser(UserConverter.convert(request));
        roleService
                .saveRole(RoleConverter
                        .convert(request)
                        .stream()
                        .peek(r -> r.setUser_id(saved.getId()))
                        .toList());
        DatabaseService.SaveUserResponse saveUserResponse = DatabaseService.SaveUserResponse
                .newBuilder()
                .setId(saved.getId())
                .build();
        responseObserver.onNext(saveUserResponse);
        responseObserver.onCompleted();
    }
}
