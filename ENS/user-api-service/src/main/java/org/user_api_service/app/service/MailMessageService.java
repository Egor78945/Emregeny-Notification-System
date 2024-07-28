package org.user_api_service.app.service;

import org.springframework.stereotype.Service;
import org.user_api_service.app.model.request_model.MailMessageRequestModel;
import org.user_api_service.app.service.rabbitmq.producer.MailMessageProducer;

import java.util.List;

@Service
public class MailMessageService {
    private final MailMessageProducer mailMessageProducer;

    public MailMessageService(MailMessageProducer mailMessageProducer) {
        this.mailMessageProducer = mailMessageProducer;
    }

    public void sendMessageToMail(MailMessageRequestModel requestModel) {
        mailMessageProducer.sendMessage(requestModel);
    }

    public void sendMessageToMail(List<MailMessageRequestModel> requestModel) {
        requestModel.forEach(this::sendMessageToMail);
    }
}
