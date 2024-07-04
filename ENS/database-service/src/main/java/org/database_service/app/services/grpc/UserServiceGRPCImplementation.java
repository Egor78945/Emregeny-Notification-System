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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RequiredArgsConstructor
@GrpcService
public class UserServiceGRPCImplementation extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void saveUser(DatabaseService.SaveUserRequest request, StreamObserver<DatabaseService.SaveUserResponse> responseObserver) {
        CompletableFuture<DatabaseService.SaveUserResponse> saveUserResponse = CompletableFuture
                .supplyAsync(() ->
                        userService.saveUser(UserConverter
                                .convertSaveRequestToUser(request)))
                .thenApplyAsync(u -> {
                    roleService.saveRole(RoleConverter
                            .convertStringToRole(request
                                    .getRolesList())
                            .stream()
                            .peek(r -> r.setUser_id(u.getId()))
                            .toList());
                    return u.getId();
                })
                .thenApplyAsync(UserConverter::buildSaveUserResponse);

        try {
            responseObserver.onNext(saveUserResponse.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getDetails(DatabaseService.GetUserDetailsRequest request, StreamObserver<DatabaseService.GetUserDetailsResponse> responseObserver) {
        User user;
        List<Role> roles;

        try {
            user = CompletableFuture.supplyAsync(() -> userService.getUserByEmail(request.getEmail())).get();
            roles = CompletableFuture.supplyAsync(() -> roleService.getByUserId(user.getId())).get();
            responseObserver.onNext(UserConverter.convertUserDetailsToGetUserDetailsResponse(user, roles));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void userExists(DatabaseService.GetUserExistsRequest request, StreamObserver<DatabaseService.GetUserExistsResponse> responseObserver) {
        CompletableFuture<DatabaseService.GetUserExistsResponse> responseFuture = CompletableFuture
                .supplyAsync(() ->
                        userService.existsUserByEmail(request.getEmail()))
                .thenApplyAsync(UserConverter::buildGetUserExistsResponse);

        try {
            responseObserver.onNext(responseFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }
}
