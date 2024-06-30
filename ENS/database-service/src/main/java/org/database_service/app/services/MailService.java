package org.database_service.app.services;

import lombok.RequiredArgsConstructor;
import org.database_service.app.model.entities.Mail;
import org.database_service.app.repositories.MailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailRepository mailRepository;

    public List<Mail> getByUserId(Long id) {
        return mailRepository.findAllByUserId(id);
    }

    public Long saveMail(String email, Long userId) {
        return mailRepository.save(new Mail(email, userId)).getId();
    }

    public boolean existsByMailAndUserId(String mail, Long id) {
        return mailRepository.findMailByMailAndUser_id(mail, id) != null;
    }

    public void deleteByMailAndUserId(String number, Long id){
        mailRepository.deleteMail(number,id);
    }
}
