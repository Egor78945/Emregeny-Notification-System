package org.database_service.app.service.grpc;

import com.example.grpc.DatabaseService;
import com.example.grpc.MailServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.database_service.app.service.MailService;
import org.database_service.app.service.converter.MailConverter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RequiredArgsConstructor
@GrpcService
public class MailServiceGRPCImplementation extends MailServiceGrpc.MailServiceImplBase {
    private final MailService mailService;

    @Override
    public void getMails(DatabaseService.GetMailRequest request, StreamObserver<DatabaseService.GetMailResponse> responseObserver) {
        CompletableFuture<DatabaseService.GetMailResponse> mailsResponse = CompletableFuture
                .supplyAsync(() ->
                        mailService.getByUserId(request.getId()))
                .thenApplyAsync(MailConverter::convertMailToString)
                .thenApplyAsync(MailConverter::convertMailsToMailResponse);

        try {
            responseObserver.onNext(mailsResponse.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void saveMail(DatabaseService.AddMailRequest request, StreamObserver<DatabaseService.AddMailResponse> responseObserver) {
        CompletableFuture<DatabaseService.AddMailResponse> mailResponse = CompletableFuture
                .supplyAsync(() ->
                        mailService.saveMail(request.getMail(), request.getId()))
                .thenApplyAsync(MailConverter::convertMailIdToSaveMailResponse);

        try {
            responseObserver.onNext(mailResponse.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void mailExists(DatabaseService.GetMailExistsRequest request, StreamObserver<DatabaseService.GetMailExistsResponse> responseObserver) {
        CompletableFuture<DatabaseService.GetMailExistsResponse> existsResponse = CompletableFuture.supplyAsync(() -> mailService.existsByMailAndUserId(request.getMail(), request.getId())).thenApplyAsync(MailConverter::convertExistsToMailExistsResponse);

        try {
            responseObserver.onNext(existsResponse.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deleteMail(DatabaseService.DeleteMailRequest request, StreamObserver<DatabaseService.DeleteMailResponse> responseObserver) {
        CompletableFuture<DatabaseService.DeleteMailResponse> deleteResponse = CompletableFuture.supplyAsync(() -> {
            mailService.deleteByMailAndUserId(request.getMail(), request.getId());
            return request.getId();
        }).thenApplyAsync(MailConverter::convertMailIdToDeleteMailResponse);

        try {
            responseObserver.onNext(deleteResponse.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onCompleted();
    }
}
