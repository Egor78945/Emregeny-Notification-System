package org.database_service.app.service;

import lombok.RequiredArgsConstructor;
import org.database_service.app.model.entity.Role;
import org.database_service.app.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void saveRole(List<Role> roleList) {
        roleRepository.saveAll(roleList);
    }

    public List<Role> getByUserId(Long id){
        return roleRepository.findAllByUserId(id);
    }
}
