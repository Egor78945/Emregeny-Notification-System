package org.user_api_service.app.models.requestModels;

import lombok.Data;

@Data
public class RegistrationRequestModel {
    private String email;
    private String password;
}
