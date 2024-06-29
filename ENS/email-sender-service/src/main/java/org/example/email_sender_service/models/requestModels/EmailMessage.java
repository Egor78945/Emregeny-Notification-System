package org.example.email_sender_service.models.requestModels;

public class EmailMessage {
    private String to;
    private String message;

    public EmailMessage(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public EmailMessage() {
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
