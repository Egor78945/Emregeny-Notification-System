package org.database_service.app.repository;

import org.database_service.app.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r JOIN User u ON r.user_id=u.id WHERE r.user_id=?1")
    List<Role> findAllByUserId(Long id);
}
