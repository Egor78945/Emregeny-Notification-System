package org.user_api_service.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.user_api_service.app.exception.RequestCancelledException;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.model.request_model.MailRequestModel;
import org.user_api_service.app.model.respone_model.User;
import org.user_api_service.app.service.converter.MailConverter;
import org.user_api_service.app.service.grpc.phone_number_service.MailGRPCService;
import org.user_api_service.app.service.redis.RedisService;
import org.user_api_service.app.service.validator.MailValidationService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailGRPCService mailGRPCService;
    private final RedisService redisService;
    private final MailMessageService mailMessageService;

    public Long save(MailRequestModel requestModel) throws JsonProcessingException, WrongDataException {
        Long currentUserId = redisService.getObject("current", User.class).getId();
        if (MailValidationService.isValidEmail(requestModel.getMail()) && !mailGRPCService.getMailExistsRequest(requestModel.getMail(), currentUserId)) {
            redisService.saveToList("mails", requestModel.getMail());
            return mailGRPCService.addMailRequest(requestModel, currentUserId);
        } else
            throw new WrongDataException("Mail format is invalid or the mail is already in your list");
    }

    public CompletableFuture<List<String>> getAll() {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return redisService.getObject("current", User.class).getId();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenApplyAsync(this::getMails);
    }

    public void delete(String mail) throws JsonProcessingException, RequestCancelledException {
        Long currentUserId = redisService.getObject("current", User.class).getId();
        if (mailGRPCService.getMailExistsRequest(mail, currentUserId)) {
            redisService.removeFromList("mails", mail);
            mailGRPCService.deleteMail(mail, currentUserId);
        } else
            throw new RequestCancelledException(String.format("Mail %s is not found", mail));
    }

    public CompletableFuture<Void> send(String message) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return redisService.getObject("current", User.class).getId();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenApplyAsync(this::getMails)
                .thenApplyAsync(mails ->
                        MailConverter.convertToMailMessageRequestModel(mails, message))
                .thenAcceptAsync(mailMessageService::sendMessageToMail);
    }

    private List<String> getMails(Long id) {
        List<String> numbers = redisService.getFromList("mails", String.class);
        if (numbers.isEmpty()) {
            numbers = mailGRPCService.getMailsRequest(id);
        }
        return numbers;
    }
}
