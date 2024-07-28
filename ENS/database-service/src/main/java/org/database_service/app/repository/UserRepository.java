package org.database_service.app.repository;

import org.database_service.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsUserByEmail(String email);
    User findUserByEmail(String email);
}
