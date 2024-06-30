package org.database_service.app.services.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.MailServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.database_service.app.model.entities.Mail;
import org.database_service.app.services.MailService;
import org.database_service.app.services.converters.MailConverter;

import java.util.List;


@RequiredArgsConstructor
@GrpcService
public class MailServiceGRPCImplementation extends MailServiceGrpc.MailServiceImplBase {
    private final MailService mailService;

    @Override
    public void getMails(DatabaseService.GetMailRequest request, StreamObserver<DatabaseService.GetMailResponse> responseObserver) {
        List<Mail> mails = mailService.getByUserId(request.getId());

        DatabaseService.GetMailResponse response = DatabaseService.GetMailResponse
                .newBuilder()
                .addAllMail(MailConverter.convertToString(mails))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveMail(DatabaseService.AddMailRequest request, StreamObserver<DatabaseService.AddMailResponse> responseObserver) {
        Long mailId = mailService.saveMail(request.getMail(), request.getId());

        DatabaseService.AddMailResponse response = DatabaseService.AddMailResponse
                .newBuilder()
                .setId(mailId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void mailExists(DatabaseService.GetMailExistsRequest request, StreamObserver<DatabaseService.GetMailExistsResponse> responseObserver) {
        boolean exists = mailService.existsByMailAndUserId(request.getMail(), request.getId());

        DatabaseService.GetMailExistsResponse response = DatabaseService.GetMailExistsResponse
                .newBuilder()
                .setExists(exists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteMail(DatabaseService.DeleteMailRequest request, StreamObserver<DatabaseService.DeleteMailResponse> responseObserver){
        mailService.deleteByMailAndUserId(request.getMail(), request.getId());

        DatabaseService.DeleteMailResponse response = DatabaseService.DeleteMailResponse
                .newBuilder()
                .setId(request.getId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
