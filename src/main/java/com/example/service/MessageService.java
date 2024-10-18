package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.MessageRepository;

import javassist.NotFoundException;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    private void throwOnInvalid(Message message) throws InvalidMessageException {
        if (message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            throw new InvalidMessageException();
        }
    }

    private Message getByIdOrThrow(int id) throws NotFoundException {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty()) {
            throw new NotFoundException("Message not found");
        }
        return message.get();
    }

    public Message createMessage(Message message) throws Exception {
        throwOnInvalid(message);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByAccount(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    public Message getMessageById(int id) {
        try {
            return getByIdOrThrow(id);
        } catch (Exception e) {
            return null;
        }
    }

    public int updateMessageById(int id, Message newMsg) throws Exception {
        Message message = getByIdOrThrow(id);
        throwOnInvalid(newMsg);

        message.setMessageId(id);
        message.setMessageText(newMsg.getMessageText());
        messageRepository.save(message);
        return 1;
    }

    public int deleteMessageById(int id) throws NotFoundException {
        Message message = getByIdOrThrow(id);

        messageRepository.delete(message);
        return 1;
    }
}
