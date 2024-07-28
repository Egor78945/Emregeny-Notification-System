package org.user_api_service.app.service.grpc.user_service;

import com.example.grpc.DatabaseService;
import com.example.grpc.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.model.request_model.SaveUserRequestModel;
import org.user_api_service.app.service.UserDetailsImpl;
import org.user_api_service.app.service.converter.UserConverter;

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

    public boolean userExistsRequest(String email){
        DatabaseService.GetUserExistsRequest request = DatabaseService.GetUserExistsRequest
                .newBuilder()
                .setEmail(email)
                .build();
        return blockingStub.userExists(request).getExists();
    }

    public UserDetailsImpl getUserDetails(String email) throws WrongDataException {
        DatabaseService.GetUserDetailsRequest request = DatabaseService.GetUserDetailsRequest
                .newBuilder()
                .setEmail(email)
                .build();
        return UserConverter.convertGetUserDetailsResponseToUserDetailsImpl(blockingStub.getDetails(request));
    }

}
