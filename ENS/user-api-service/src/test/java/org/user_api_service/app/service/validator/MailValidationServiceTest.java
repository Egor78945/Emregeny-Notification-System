package org.user_api_service.app.service.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MailValidationServiceTest {
    @Test
    public void isValidEmail_validEmail(){
        Assertions.assertTrue(MailValidationService.isValidEmail("Egor78396@gmail.com"));
    }

    @Test
    public void isValidEmail_emptyUserName(){
        Assertions.assertFalse(MailValidationService.isValidEmail("@gmail.com"));
    }

    @Test
    public void isValidEmail_longUserName(){
        Assertions.assertFalse(MailValidationService.isValidEmail("Ashdfhkshekuhkfsehfsuefhsdhhfsdfwhefhsjhdjfhjshejfjhhsjkejkfhjskdmfbsmdmfbmsbmebfmsmefse87f67se78f87sd8f76s7d67fsdfsdf7s7df7s7d77sd7f7s7df@gmail.com"));
    }

    @Test
    public void isValidEmail_doubleDogCharacter(){
        Assertions.assertFalse(MailValidationService.isValidEmail("egor24@@gmail.com"));
    }

    @Test
    public void isValidEmail_emptyDomain(){
        Assertions.assertFalse(MailValidationService.isValidEmail("egor24@"));
    }
    @Test
    public void isValidEmail_withoutDog(){
        Assertions.assertFalse(MailValidationService.isValidEmail("egor24gmail.com"));
    }
    @Test
    public void isValidEmail_unknownDomain(){
        Assertions.assertFalse(MailValidationService.isValidEmail("egor24abc.ru"));
    }
}
