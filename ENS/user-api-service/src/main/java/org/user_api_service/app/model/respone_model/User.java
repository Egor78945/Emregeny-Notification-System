package org.user_api_service.app.model.respone_model;

import lombok.Data;
import org.user_api_service.app.enums.UserRole;
import org.user_api_service.app.service.UserDetailsImpl;

import java.util.List;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private List<UserRole> roles;
    private String registrationDate;

    public User(UserDetailsImpl userDetails) {
        this.id = userDetails.getId();
        this.email = userDetails.getEmail();
        this.password = userDetails.getPassword();
        this.roles = userDetails.getRoles();
        this.registrationDate = userDetails.getRegistrationDate();
    }

    public User() {
    }
}
