package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import javassist.NotFoundException;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
@ResponseBody
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<?> createAccountHandler(@RequestBody Account newAccount) {
        try {
            Account created = accountService.createAccount(newAccount);
            if (created == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                return ResponseEntity.ok(created);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginToAccountHandler(@RequestBody Account account) {
        Account existingAccount = accountService.loginToAccount(account);
        if (existingAccount == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.ok(existingAccount);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessageHandler(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountHandler(@PathVariable int accountId) {
        List<Message> messages = messageService.getAllMessagesByAccount(accountId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageHandler(@PathVariable int messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageHandler(@PathVariable int messageId, @RequestBody Message newMessage) {
        try {
            int result = messageService.updateMessageById(messageId, newMessage);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/messages/{messageId}")
    private ResponseEntity<?> deleteMessageHandler(@PathVariable int messageId) {
        try {
            int result = messageService.deleteMessageById(messageId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
