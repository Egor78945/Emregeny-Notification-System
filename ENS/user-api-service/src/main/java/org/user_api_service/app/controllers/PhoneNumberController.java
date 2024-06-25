package org.user_api_service.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user_api_service.app.annotations.PhoneNumberControllerExceptionHandler;
import org.user_api_service.app.exceptions.RequestCancelledException;
import org.user_api_service.app.exceptions.WrongDataException;
import org.user_api_service.app.models.requestModels.PhoneNumberRequestModel;
import org.user_api_service.app.services.PhoneNumberService;

import java.util.List;

@RestController
@RequestMapping("/ens/number")
@RequiredArgsConstructor
@PhoneNumberControllerExceptionHandler
public class PhoneNumberController {
    private final PhoneNumberService phoneNumberService;

    @PostMapping
    public ResponseEntity<String> addPhoneNumber(@RequestBody PhoneNumberRequestModel requestModel) throws JsonProcessingException, WrongDataException {
        Long id = phoneNumberService.save(requestModel);
        return ResponseEntity.ok(String.format("Phone number %s have been saved by id %s", requestModel.getNumber(), id));
    }

    @GetMapping
    public ResponseEntity<List<String>> getMyNumbers() throws RequestCancelledException, JsonProcessingException {
        return ResponseEntity.ok(phoneNumberService.getAll());
    }

    @DeleteMapping
    public ResponseEntity<String> deletePhoneNumber(@RequestParam("number") String number) {
        return ResponseEntity.ok(String.format("Phone number %s has been deleted", number));
    }
}
