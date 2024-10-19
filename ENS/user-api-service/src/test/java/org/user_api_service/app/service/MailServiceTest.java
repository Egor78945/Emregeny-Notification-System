package org.user_api_service.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.model.request_model.MailRequestModel;
import org.user_api_service.app.model.respone_model.User;
import org.user_api_service.app.service.grpc.phone_number_service.MailGRPCService;
import org.user_api_service.app.service.redis.RedisService;
import org.user_api_service.app.service.validator.MailValidationService;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {
    @InjectMocks
    private MailService mailService;
    @Mock
    private MailGRPCService mailGRPCService;
    @Mock
    private RedisService redisService;
    @Mock
    private MailMessageService mailMessageService;

    @Test
    public void save_wrongDataException() throws JsonProcessingException {
        Mockito.when(redisService.getObject("current", User.class)).thenReturn(new User());
        Assertions.assertThrows(WrongDataException.class, () -> mailService.save(new MailRequestModel("abc")));
    }

    @Test
    public void save_validSave() throws JsonProcessingException, WrongDataException {
        MailRequestModel requestModel = new MailRequestModel("Egor78396@gmail.com");

        User user = new User();
        user.setId(1L);
        Mockito.when(redisService.getObject("current", User.class)).thenReturn(user);

        Mockito.when(mailGRPCService.getMailExistsRequest(requestModel.getMail(), 1L)).thenReturn(false);
        Mockito.when(mailGRPCService.addMailRequest(requestModel, 1L)).thenReturn(1L);

        Assertions.assertEquals(1L, mailService.save(requestModel));
    }
}
