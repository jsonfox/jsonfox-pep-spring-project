package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.InvalidAccountDetailsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) throws InvalidAccountDetailsException {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return null;
        }

        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            throw new InvalidAccountDetailsException();
        }

        return accountRepository.save(account);
    }

    public Account loginToAccount(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
