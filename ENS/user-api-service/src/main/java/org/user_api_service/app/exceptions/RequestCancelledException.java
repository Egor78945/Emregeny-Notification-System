package org.user_api_service.app.exceptions;

public class RequestCancelledException extends Exception{
    public RequestCancelledException(String message){
        super(message);
    }
}
