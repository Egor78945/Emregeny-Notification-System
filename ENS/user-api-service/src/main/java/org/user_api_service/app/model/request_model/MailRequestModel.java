package org.user_api_service.app.model.request_model;

import lombok.Data;

@Data
public class MailRequestModel {
    private String mail;

    public MailRequestModel(String mail) {
        this.mail = mail;
    }

    public MailRequestModel() {
    }
}
