package org.database_service.app.repositories;

import org.database_service.app.model.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    @Query("SELECT n FROM PhoneNumber n JOIN User u ON n.user_id=u WHERE n.user_id=?1")
    List<PhoneNumber> findAllByUserId(Long id);
}
