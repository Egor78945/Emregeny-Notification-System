package org.user_api_service.app.model.request_model;

import lombok.Data;

@Data
public class RegistrationRequestModel {
    private String email;
    private String password;
}
