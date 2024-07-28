package org.example.email_sender_service.model.request_model;

public class EmailMessageRequestModel {
    private String to;
    private String message;

    public EmailMessageRequestModel(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public EmailMessageRequestModel() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
