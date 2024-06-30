package org.database_service.app.repositories;

import org.database_service.app.model.entities.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    @Query("SELECT m FROM Mail m JOIN User u ON m.user_id=u.id WHERE m.user_id=?1")
    List<Mail> findAllByUserId(Long id);
    @Query("SELECT m.id FROM Mail m JOIN User u ON m.user_id=u.id WHERE m.user_id=?2 AND m.mail=?1")
    Long findMailByMailAndUser_id(String mail, Long id);
    @Transactional
    @Modifying
    @Query("DELETE FROM Mail m WHERE m.mail=?1 AND m.user_id=?2")
    void deleteMail(String mail, Long id);
}
