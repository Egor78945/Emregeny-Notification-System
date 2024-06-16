package org.database_service.app.services.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.UserServiceGrpc;
import io.grpc.stub.StreamObserver;

public class UserServiceGRPCImplementation extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void saveUser(DatabaseService.SaveUserRequest request, StreamObserver<DatabaseService.SaveUserResponse> responseObserver) {

        DatabaseService.SaveUserResponse saveUserResponse = DatabaseService.SaveUserResponse
                .newBuilder()
                .setId()
                .build();
        responseObserver.onNext(saveUserResponse);
        responseObserver.onCompleted();
    }
}
