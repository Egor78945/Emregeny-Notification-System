package org.user_api_service.app.exceptions;

public class WrongDataException extends Exception{
    public WrongDataException(String message){
        super(message);
    }
}
