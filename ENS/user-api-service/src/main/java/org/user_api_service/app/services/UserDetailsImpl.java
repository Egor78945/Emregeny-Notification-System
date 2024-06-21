package org.user_api_service.app.services;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.user_api_service.app.enums.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private List<UserRole> roles;
    private String registrationDate;

    public UserDetailsImpl(UserDetailsImpl userDetails){
        this.id = userDetails.getId();
        this.email = userDetails.getEmail();
        this.password = userDetails.getPassword();
        this.roles = userDetails.getRoles();
        this.registrationDate = userDetails.getRegistrationDate();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
