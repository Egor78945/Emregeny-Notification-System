package org.user_api_service.app.services.converters;

import org.user_api_service.app.models.requestModels.RegistrationRequestModel;
import org.user_api_service.app.models.requestModels.SaveUserRequestModel;

import java.util.Arrays;
import java.util.Date;

public class UserConverter {
    public static SaveUserRequestModel convertRegisterModelToSaveRequestModel(RegistrationRequestModel requestModel) {
        return new SaveUserRequestModel(requestModel.getEmail(), requestModel.getPassword(), new Date(System.currentTimeMillis()).toString(), Arrays.asList("USER"));
    }
}
