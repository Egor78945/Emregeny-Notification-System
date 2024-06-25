package org.database_service.app.services.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.database_service.app.model.entities.Role;
import org.database_service.app.model.entities.User;
import org.database_service.app.services.RoleService;
import org.database_service.app.services.UserService;
import org.database_service.app.services.converters.RoleConverter;
import org.database_service.app.services.converters.UserConverter;

import java.util.List;


@RequiredArgsConstructor
@GrpcService
public class UserServiceGRPCImplementation extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void saveUser(DatabaseService.SaveUserRequest request, StreamObserver<DatabaseService.SaveUserResponse> responseObserver) {
        Long savedUserId = userService.saveUser(UserConverter.convertSaveRequestToUser(request)).getId();
        roleService
                .saveRole(RoleConverter
                        .convertStringToRole(request.getRolesList())
                        .stream()
                        .peek(r -> r.setUser_id(savedUserId))
                        .toList());
        DatabaseService.SaveUserResponse saveUserResponse = DatabaseService.SaveUserResponse
                .newBuilder()
                .setId(savedUserId)
                .build();
        responseObserver.onNext(saveUserResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getDetails(DatabaseService.GetUserDetailsRequest request, StreamObserver<DatabaseService.GetUserDetailsResponse> responseObserver) {
        User user = userService.getUserByEmail(request.getEmail());
        List<Role> roleList = roleService.getByUserId(user.getId());

        responseObserver.onNext(UserConverter.convertUserDetailsToGetUserDetailsResponse(user, roleList));
        responseObserver.onCompleted();
    }

    @Override
    public void userExists(DatabaseService.GetUserExistsRequest request, StreamObserver<DatabaseService.GetUserExistsResponse> responseObserver){
        responseObserver.onNext(UserConverter.buildGetUserExistsResponse(userService.existsUserByEmail(request.getEmail())));
        responseObserver.onCompleted();
    }
}
