package org.user_api_service.app.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.user_api_service.app.model.request_model.RegistrationRequestModel;

public class UserValidationServiceTest {
    @Test
    public void isValidRegistrationRequestModel_invalidEmail(){
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel("@gmail.com","12345abcde");
        Assertions.assertFalse(UserValidationService.isValidRegistrationRequestModel(registrationRequestModel));
    }
    @Test
    public void isValidRegistrationRequestModel_invalidPassword(){
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel("Egor78396@gmail.com","12345ab");
        Assertions.assertFalse(UserValidationService.isValidRegistrationRequestModel(registrationRequestModel));
    }
    @Test
    public void isValidRegistrationRequestModel_validRequestModel(){
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel("Egor78396@gmail.com","12345abcde");
        Assertions.assertTrue(UserValidationService.isValidRegistrationRequestModel(registrationRequestModel));
    }
    @Test
    public void isValidPassword_emptyPassword(){
        Assertions.assertFalse(UserValidationService.isValidPassword(""));
    }
    @Test
    public void isValidPassword_blankPassword(){
        Assertions.assertFalse(UserValidationService.isValidPassword("                    "));
    }
    @Test
    public void isValidPassword_withoutLetters(){
        Assertions.assertFalse(UserValidationService.isValidPassword("12345"));
    }
    @Test
    public void isValidPassword_withoutDigits(){
        Assertions.assertFalse(UserValidationService.isValidPassword("abcde"));
    }
    @Test
    public void isValidPassword_longPassword(){
        Assertions.assertFalse(UserValidationService.isValidPassword("abcdeasfasfsdfsdfsdfs6dfs7d7f8s8df6f656f5566s6d7fsdfsdfs76df7866s7d687f76s87df87f87f6f6fssdfsdfsdfsdfs67f7s7d87fsd87fsd87f87s7d87fs6fs6d78f68asdau7s6f7asfasdfsdf"));
    }
    @Test
    public void isValidPassword_validPassword(){
        Assertions.assertTrue(UserValidationService.isValidPassword("abcde12356"));
    }
}
