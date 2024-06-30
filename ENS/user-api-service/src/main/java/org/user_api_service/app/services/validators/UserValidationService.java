package org.user_api_service.app.services.validators;

import org.springframework.stereotype.Service;
import org.user_api_service.app.models.requestModels.RegistrationRequestModel;

@Service
public class UserValidationService {
    public static boolean isValidRegistrationRequestModel(RegistrationRequestModel requestModel){
        return MailValidationService.isValidEmail(requestModel.getEmail()) && isValidPassword(requestModel.getPassword());
    }
    private static boolean isValidPassword(String password){
        if(password.length() >= 8){
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
