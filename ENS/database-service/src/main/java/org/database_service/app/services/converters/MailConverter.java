package org.database_service.app.services.converters;

import org.database_service.app.model.entities.Mail;

import java.util.List;

public class MailConverter {
    public static List<String> convertToString(List<Mail> mail) {
        return mail
                .stream()
                .map(n -> n.getMail())
                .toList();
    }
}
