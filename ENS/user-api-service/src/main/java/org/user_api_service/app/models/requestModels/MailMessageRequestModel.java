package org.user_api_service.app.models.requestModels;

public class MailMessageRequestModel {
    private String to;
    private String message;

    public MailMessageRequestModel(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public MailMessageRequestModel() {
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
