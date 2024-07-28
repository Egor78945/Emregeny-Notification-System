package org.user_api_service.app.exception;

public class RequestCancelledException extends Exception{
    public RequestCancelledException(String message){
        super(message);
    }
}
