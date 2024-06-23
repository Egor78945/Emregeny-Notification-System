package org.database_service.app.repositories;

import org.database_service.app.model.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    @Query("SELECT n FROM PhoneNumber n JOIN User u ON n.user_id=u.id WHERE n.user_id=?1")
    List<PhoneNumber> findAllByUserId(Long id);
    @Query("SELECT n.id FROM PhoneNumber n JOIN User u ON n.user_id=u.id WHERE n.user_id=?2 AND n.number=?1")
    Long findPhoneNumberByNumberAndUser_id(String number, Long id);
    @Transactional
    @Modifying
    @Query("DELETE FROM PhoneNumber n WHERE n.number=?1 AND n.user_id=?2")
    void deletePhoneNumber(String number, Long id);
}
