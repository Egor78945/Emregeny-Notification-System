package org.user_api_service.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.user_api_service.app.exceptions.RequestCancelledException;
import org.user_api_service.app.exceptions.WrongDataException;
import org.user_api_service.app.models.requestModels.MailRequestModel;
import org.user_api_service.app.services.converters.MailConverter;
import org.user_api_service.app.services.grpc.phone_number_service.MailGRPCService;
import org.user_api_service.app.services.redis.RedisService;
import org.user_api_service.app.services.validators.MailValidationService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailGRPCService mailGRPCService;
    private final RedisService redisService;
    private final MailMessageService mailMessageService;

    public Long save(MailRequestModel requestModel) throws JsonProcessingException, WrongDataException {
        Long currentUserId = redisService.getCurrentUser().getId();
        if (MailValidationService.isValidEmail(requestModel.getMail()) && !mailGRPCService.getMailExistsRequest(requestModel.getMail(), currentUserId)) {
            return mailGRPCService.addMailRequest(requestModel, currentUserId);
        } else
            throw new WrongDataException("Mail format is invalid");
    }

    public CompletableFuture<List<String>> getAll() {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return redisService.getCurrentUser().getId();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenApplyAsync(mailGRPCService::getMailsRequest);
    }

    public void delete(String mail) throws JsonProcessingException, RequestCancelledException {
        Long currentUserId = redisService.getCurrentUser().getId();
        if (mailGRPCService.getMailExistsRequest(mail, currentUserId)) {
            mailGRPCService.deleteMail(mail, currentUserId);
        } else
            throw new RequestCancelledException(String.format("Mail %s is not found", mail));
    }

    public CompletableFuture<Void> send(String message) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return redisService.getCurrentUser().getId();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenApplyAsync(mailGRPCService::getMailsRequest)
                .thenApplyAsync(mails ->
                        MailConverter.convertToMailMessageRequestModel(mails, message))
                .thenAcceptAsync(mailMessageService::sendMessageToMail);
    }
}
