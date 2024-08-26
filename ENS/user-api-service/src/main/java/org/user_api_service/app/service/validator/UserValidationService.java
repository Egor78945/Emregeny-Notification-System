package org.user_api_service.app.service.validator;

import org.springframework.stereotype.Service;
import org.user_api_service.app.model.request_model.RegistrationRequestModel;

@Service
public class UserValidationService {
    public static boolean isValidRegistrationRequestModel(RegistrationRequestModel requestModel){
        return MailValidationService.isValidEmail(requestModel.getEmail()) && isValidPassword(requestModel.getPassword());
    }
    public static boolean isValidPassword(String password){
        if(password.length() >= 8 && password.length() <= 150 && !password.isEmpty() && !password.isBlank()){
            int letCount = 0;
            int digCount = 0;
            for(char c : password.toCharArray()){
                if(Character.isLetter(c))
                    letCount++;
                else if (Character.isDigit(c))
                    digCount++;
                else
                    return false;
            }
            return letCount>=4 && digCount >=4;
        }
        return false;
    }
}
