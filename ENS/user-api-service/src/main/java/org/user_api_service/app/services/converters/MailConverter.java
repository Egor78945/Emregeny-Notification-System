package org.user_api_service.app.services.converters;

import org.user_api_service.app.models.requestModels.MailMessageRequestModel;

import java.util.List;

public class MailConverter {
    public static MailMessageRequestModel convertToMailMessageRequestModel(String mail, String message) {
        return new MailMessageRequestModel(mail, message);
    }

    public static List<MailMessageRequestModel> convertToMailMessageRequestModel(List<String> mails, String message) {
        return mails
                .stream()
                .map(m -> convertToMailMessageRequestModel(m, message))
                .toList();
    }
}
