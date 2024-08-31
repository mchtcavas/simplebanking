package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.dto.BankAccountDto;
import org.springframework.stereotype.Component;

@Component
public interface BankAccountManagement {
    BankAccountDto create(String owner, String mail);
    BankAccountDto check(String accountNumber);
    void update(BankAccountDto bankAccountDto);
}
