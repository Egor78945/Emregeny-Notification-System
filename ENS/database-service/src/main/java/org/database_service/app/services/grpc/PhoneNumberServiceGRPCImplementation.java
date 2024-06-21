package org.database_service.app.services.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.PhoneNumberServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.database_service.app.model.entities.PhoneNumber;
import org.database_service.app.services.PhoneNumberService;
import org.database_service.app.services.converters.PhoneNumberConverter;

import java.util.List;


@RequiredArgsConstructor
@GrpcService
public class PhoneNumberServiceGRPCImplementation extends PhoneNumberServiceGrpc.PhoneNumberServiceImplBase {
    private final PhoneNumberService phoneNumberService;

    @Override
    public void getNumbers(DatabaseService.GetNumberRequest request, StreamObserver<DatabaseService.GetNumberResponse> responseObserver) {
        List<PhoneNumber> numbers = phoneNumberService.getByUserId(request.getId());

        DatabaseService.GetNumberResponse response = DatabaseService.GetNumberResponse
                .newBuilder()
                .addAllNumber(PhoneNumberConverter.convertToString(numbers))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
