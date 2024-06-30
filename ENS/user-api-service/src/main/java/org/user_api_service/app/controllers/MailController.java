package org.user_api_service.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user_api_service.app.annotations.MailControllerExceptionHandler;
import org.user_api_service.app.exceptions.RequestCancelledException;
import org.user_api_service.app.exceptions.WrongDataException;
import org.user_api_service.app.models.requestModels.MailRequestModel;
import org.user_api_service.app.services.MailService;

import java.util.List;

@RestController
@RequestMapping("/ens/mail")
@RequiredArgsConstructor
@MailControllerExceptionHandler
public class MailController {
    private final MailService mailService;

    @PostMapping
    public ResponseEntity<String> addMail(@RequestBody MailRequestModel requestModel) throws JsonProcessingException, WrongDataException {
        Long id = mailService.save(requestModel);
        return ResponseEntity.ok(String.format("Mail %s have been saved by id %s", requestModel.getMail(), id));
    }

    @GetMapping
    public ResponseEntity<List<String>> getMyMails() throws RequestCancelledException, JsonProcessingException {
        return ResponseEntity.ok(mailService.getAll());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMail(@RequestParam("mail") String mail) throws RequestCancelledException, JsonProcessingException {
        mailService.delete(mail);
        return ResponseEntity.ok(String.format("Mail %s has been deleted", mail));
    }
}
