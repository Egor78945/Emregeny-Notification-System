package org.database_service.app.services.converters;

import com.example.grpc.DatabaseService;
import org.database_service.app.model.entities.Mail;

import java.util.List;

public class MailConverter {
    public static List<String> convertMailToString(List<Mail> mail) {
        return mail
                .stream()
                .map(Mail::getMail)
                .toList();
    }

    public static DatabaseService.GetMailResponse convertMailsToMailResponse(List<String> mails) {
        return DatabaseService.GetMailResponse
                .newBuilder()
                .addAllMail(mails)
                .build();
    }

    public static DatabaseService.AddMailResponse convertMailIdToSaveMailResponse(Long id) {
        return DatabaseService.AddMailResponse
                .newBuilder()
                .setId(id)
                .build();
    }

    public static DatabaseService.GetMailExistsResponse convertExistsToMailExistsResponse(boolean exists) {
        return DatabaseService.GetMailExistsResponse
                .newBuilder()
                .setExists(exists)
                .build();
    }

    public static DatabaseService.DeleteMailResponse convertMailIdToDeleteMailResponse(Long id) {
        return DatabaseService.DeleteMailResponse
                .newBuilder()
                .setId(id)
                .build();
    }
}
