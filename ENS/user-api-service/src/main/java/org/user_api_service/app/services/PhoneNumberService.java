package org.user_api_service.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.user_api_service.app.exceptions.RequestCancelledException;
import org.user_api_service.app.exceptions.WrongDataException;
import org.user_api_service.app.models.requestModels.PhoneNumberRequestModel;
import org.user_api_service.app.models.responeModels.User;
import org.user_api_service.app.services.grpc.phone_number_service.PhoneNumberGRPCService;
import org.user_api_service.app.services.redis.RedisService;
import org.user_api_service.app.services.validators.PhoneNumberValidationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberService {
    private final PhoneNumberGRPCService phoneNumberGRPCService;
    private final RedisService redisService;
    private final JsonMapper jsonMapper;

    public Long save(PhoneNumberRequestModel requestModel) throws JsonProcessingException, WrongDataException {
        Long currentUserId = redisService.getCurrentUser().getId();
        if (PhoneNumberValidationService.isValidNumber(requestModel.getNumber()) && !phoneNumberGRPCService.getNumberExistsRequest(requestModel.getNumber(), currentUserId)) {
            return phoneNumberGRPCService.addNumberRequest(requestModel, currentUserId);
        } else
            throw new WrongDataException("Phone number format is invalid");
    }

    public List<String> getAll() throws JsonProcessingException, RequestCancelledException {
        Long currentUserId = redisService.getCurrentUser().getId();
        List<String> numbers = phoneNumberGRPCService.getNumbersRequest(currentUserId);
        if (numbers.isEmpty())
            throw new RequestCancelledException("You have not any phone numbers");
        return numbers;
    }

    public Long delete(String number) throws JsonProcessingException, RequestCancelledException {
        Long currentUserId = redisService.getCurrentUser().getId();
        if (phoneNumberGRPCService.getNumberExistsRequest(number, currentUserId)) {
            return phoneNumberGRPCService.deleteNumber(number, currentUserId);
        } else
            throw new RequestCancelledException(String.format("Phone number %s is not found", number));
    }
}
