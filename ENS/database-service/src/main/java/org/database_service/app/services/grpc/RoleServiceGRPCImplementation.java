package org.database_service.app.services.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.RoleServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.database_service.app.model.entities.Role;
import org.database_service.app.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceGRPCImplementation extends RoleServiceGrpc.RoleServiceImplBase {
    private final RoleService roleService;

    @Override
    public void getRoles(DatabaseService.GetUserRolesRequest request, StreamObserver<DatabaseService.GetUserRolesResponse> rolesObserver) {
        List<Role> roles = roleService.getByUserId(request.getId());

        DatabaseService.GetUserRolesResponse response = DatabaseService.GetUserRolesResponse
                .newBuilder()
                .addAllRoles(roles
                        .stream()
                        .map(r -> r.getRole())
                        .toList())
                .build();

        rolesObserver.onNext(response);
        rolesObserver.onCompleted();
    }
}
