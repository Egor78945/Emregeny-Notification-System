package org.user_api_service.app.models.requestModels;

import lombok.Data;
import java.util.List;

@Data
public class SaveUserRequestModel {
    private String email;
    private String password;
    private List<String> roles;
    private String registrationDate;

    public SaveUserRequestModel(String email, String password, String registrationDate, List<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.registrationDate = registrationDate;
    }
}
