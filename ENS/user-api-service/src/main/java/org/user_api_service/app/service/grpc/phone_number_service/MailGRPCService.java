package org.user_api_service.app.service.grpc.phone_number_service;

import com.example.grpc.DatabaseService;
import com.example.grpc.MailServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.user_api_service.app.model.request_model.MailRequestModel;

import java.util.List;

@Service
public class MailGRPCService {
    @GrpcClient("grpc-service")
    private MailServiceGrpc.MailServiceBlockingStub blockingStub;

    public Long addMailRequest(MailRequestModel mailRequestModel, Long id) {
        DatabaseService.AddMailRequest request = DatabaseService.AddMailRequest
                .newBuilder()
                .setId(id)
                .setMail(mailRequestModel.getMail())
                .build();
        return blockingStub.saveMail(request).getId();
    }

    public List<String> getMailsRequest(Long id) {
        DatabaseService.GetMailRequest request = DatabaseService.GetMailRequest
                .newBuilder()
                .setId(id)
                .build();
        return blockingStub.getMails(request).getMailList();
    }

    public boolean getMailExistsRequest(String mail, Long userId) {
        DatabaseService.GetMailExistsRequest request = DatabaseService.GetMailExistsRequest
                .newBuilder()
                .setMail(mail)
                .setId(userId)
                .build();
        return blockingStub.mailExists(request).getExists();
    }

    public Long deleteMail(String mail, Long userId) {
        DatabaseService.DeleteMailRequest request = DatabaseService.DeleteMailRequest
                .newBuilder()
                .setMail(mail)
                .setId(userId)
                .build();
        return blockingStub.deleteMail(request).getId();
    }
}
