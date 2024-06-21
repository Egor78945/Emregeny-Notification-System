package org.user_api_service.app.services.grpc.user_service;

import com.example.grpc.DatabaseService;
import com.example.grpc.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.user_api_service.app.models.requestModels.SaveUserRequestModel;

@Service
public class UserGRPCService {
    @GrpcClient("grpc-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public Long saveUserRequest(SaveUserRequestModel requestModel){
        DatabaseService.SaveUserRequest request = DatabaseService.SaveUserRequest
                .newBuilder()
                .setEmail(requestModel.getEmail())
                .setPassword(requestModel.getPassword())
                .addAllRoles(requestModel.getRoles())
                .setRegDate(requestModel.getRegistrationDate())
                .build();
        return blockingStub.saveUser(request).getId();
    }
}
