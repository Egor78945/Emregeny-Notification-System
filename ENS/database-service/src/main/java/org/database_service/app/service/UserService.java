package org.database_service.app.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.database_service.app.exception.NotFoundException;
import org.database_service.app.model.entity.User;
import org.database_service.app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void saveUser(List<User> userList) {
        userRepository.saveAll(userList);
    }

    @Transactional
    public User getUserById(Long id) throws NotFoundException {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format("User with id %s is not found", id)));
    }

    @Transactional
    public boolean existsUserByEmail(String email){
        return userRepository.existsUserByEmail(email);
    }

    @Transactional
    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
