package org.database_service.app.services;

import lombok.RequiredArgsConstructor;
import org.database_service.app.model.entities.User;
import org.database_service.app.repositories.UserRepository;
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
}
