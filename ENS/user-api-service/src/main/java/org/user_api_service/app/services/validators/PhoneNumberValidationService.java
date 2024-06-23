package org.user_api_service.app.services.validators;

public class PhoneNumberValidationService {

    public static boolean isValidNumber(String number){
        return number.length() == 11 && (number.startsWith("7") || number.startsWith("8"));
    }
}
