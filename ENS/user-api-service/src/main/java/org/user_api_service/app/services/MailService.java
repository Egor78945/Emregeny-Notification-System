package org.user_api_service.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.user_api_service.app.exceptions.RequestCancelledException;
import org.user_api_service.app.exceptions.WrongDataException;
import org.user_api_service.app.models.requestModels.MailRequestModel;
import org.user_api_service.app.services.grpc.phone_number_service.MailGRPCService;
import org.user_api_service.app.services.redis.RedisService;
import org.user_api_service.app.services.validators.MailValidationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailGRPCService mailGRPCService;
    private final RedisService redisService;

    public Long save(MailRequestModel requestModel) throws JsonProcessingException, WrongDataException {
        Long currentUserId = redisService.getCurrentUser().getId();
        if (MailValidationService.isValidEmail(requestModel.getMail()) && !mailGRPCService.getMailExistsRequest(requestModel.getMail(), currentUserId)) {
            return mailGRPCService.addMailRequest(requestModel, currentUserId);
        } else
            throw new WrongDataException("Mail format is invalid");
    }

    public List<String> getAll() throws JsonProcessingException, RequestCancelledException {
        Long currentUserId = redisService.getCurrentUser().getId();
        List<String> mails = mailGRPCService.getMailsRequest(currentUserId);
        if (mails.isEmpty())
            throw new RequestCancelledException("You have not any mails");
        return mails;
    }

    public Long delete(String mail) throws JsonProcessingException, RequestCancelledException {
        Long currentUserId = redisService.getCurrentUser().getId();
        if (mailGRPCService.getMailExistsRequest(mail, currentUserId)) {
            return mailGRPCService.deleteMail(mail, currentUserId);
        } else
            throw new RequestCancelledException(String.format("Mail %s is not found", mail));
    }
}
