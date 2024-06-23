package org.user_api_service.app.services.grpc.phone_number_service;

import com.example.grpc.DatabaseService;
import com.example.grpc.PhoneNumberServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.user_api_service.app.models.requestModels.PhoneNumberRequestModel;

import java.util.List;

@Service
public class PhoneNumberGRPCService {
    @GrpcClient("grpc-service")
    private PhoneNumberServiceGrpc.PhoneNumberServiceBlockingStub blockingStub;

    public Long addNumberRequest(PhoneNumberRequestModel phoneNumberRequestModel, Long id) {
        DatabaseService.AddNumberRequest request = DatabaseService.AddNumberRequest
                .newBuilder()
                .setId(id)
                .setNumber(phoneNumberRequestModel.getNumber())
                .build();
        return blockingStub.saveNumber(request).getId();
    }

    public List<String> getNumbersRequest(Long id) {
        DatabaseService.GetNumberRequest request = DatabaseService.GetNumberRequest
                .newBuilder()
                .setId(id)
                .build();
        return blockingStub.getNumbers(request).getNumberList();
    }

    public boolean getNumberExistsRequest(String number, Long userId) {
        DatabaseService.GetNumberExistsRequest request = DatabaseService.GetNumberExistsRequest
                .newBuilder()
                .setNumber(number)
                .setId(userId)
                .build();
        return blockingStub.numberExists(request).getExists();
    }

    public Long deleteNumber(String number, Long userId) {
        DatabaseService.DeletePhoneNumberRequest request = DatabaseService.DeletePhoneNumberRequest
                .newBuilder()
                .setNumber(number)
                .setId(userId)
                .build();
        return blockingStub.deleteNumber(request).getId();
    }
}
