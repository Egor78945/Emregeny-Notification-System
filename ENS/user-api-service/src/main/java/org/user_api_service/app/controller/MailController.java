package org.user_api_service.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.user_api_service.app.annotation.MailControllerExceptionHandler;
import org.user_api_service.app.exception.RequestCancelledException;
import org.user_api_service.app.exception.WrongDataException;
import org.user_api_service.app.model.request_model.MailRequestModel;
import org.user_api_service.app.service.MailService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/ens/mail")
@RequiredArgsConstructor
@MailControllerExceptionHandler
public class MailController {
    private final MailService mailService;

    @PostMapping("/add")
    public ResponseEntity<String> addMail(@RequestBody MailRequestModel requestModel) throws JsonProcessingException, WrongDataException {
        Long id = mailService.save(requestModel);
        return ResponseEntity.ok(String.format("Mail %s have been saved by id %s", requestModel.getMail(), id));
    }

    @GetMapping("/get")
    public ResponseEntity<List<String>> getMyMails() throws RequestCancelledException, ExecutionException, InterruptedException {
        List<String> mails = mailService.getAll().get();
        if(!mails.isEmpty()){
            return ResponseEntity.ok(mails);
        } else {
            throw new RequestCancelledException("You have not any mails");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMail(@RequestParam("mail") String mail) throws RequestCancelledException, JsonProcessingException {
        mailService.delete(mail);
        return ResponseEntity.ok(String.format("Mail %s has been deleted", mail));
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam("message") String message) throws ExecutionException, InterruptedException {
        mailService.send(message).get();
        return ResponseEntity.ok("Message has been sent to all your mails");
    }
}
