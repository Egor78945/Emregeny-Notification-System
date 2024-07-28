package org.user_api_service.app.service.validator;

public class MailValidationService {
    public static boolean isValidEmail(String email){
        if(email.endsWith("@gmail.com") || email.endsWith("@mail.ru")){
            String[] arr = email.split("@");
            if(arr.length == 2 && arr[0].length() > 5){
                for(char c : arr[0].toCharArray()){
                    if(!Character.isDigit(c) && !Character.isLetter(c))
                        return false;
                }
                return true;
            }
        }
        return false;
    }
}
