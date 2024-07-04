package org.database_service.app.services.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.RoleServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.database_service.app.model.entities.Role;
import org.database_service.app.services.RoleService;
import org.database_service.app.services.converters.RoleConverter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RequiredArgsConstructor
@GrpcService
public class RoleServiceGRPCImplementation extends RoleServiceGrpc.RoleServiceImplBase {
    private final RoleService roleService;

    @Override
    public void getRoles(DatabaseService.GetUserRolesRequest request, StreamObserver<DatabaseService.GetUserRolesResponse> rolesObserver) {
        CompletableFuture<DatabaseService.GetUserRolesResponse> getRolesResponse = CompletableFuture
                .supplyAsync(() ->
                        roleService.getByUserId(request.getId()))
                .thenApplyAsync(r ->
                        r.stream()
                                .map(Role::getRole)
                                .toList())
                .thenApplyAsync(RoleConverter::buildUserRolesResponse);

        try {
            rolesObserver.onNext(getRolesResponse.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        rolesObserver.onCompleted();
    }
}
