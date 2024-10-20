package org.user_api_service.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.service.grpc.user.UserGRPCService;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserGRPCService userGRPCService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userGRPCService.getUserDetails(username);
        } catch (WrongDataException e) {
            throw new RuntimeException(e);
        }
    }
}
