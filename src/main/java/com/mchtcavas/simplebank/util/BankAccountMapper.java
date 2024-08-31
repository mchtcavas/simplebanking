package com.mchtcavas.simplebank.util;

import com.mchtcavas.simplebank.domain.BankAccount;
import com.mchtcavas.simplebank.dto.BankAccountDto;

import java.util.stream.Collectors;

public class BankAccountMapper {
    public static BankAccount toBankAccount(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankAccountDto.getId());
        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
        bankAccount.setBalance(bankAccountDto.getBalance());
        bankAccount.setMail(bankAccountDto.getMail());
        bankAccount.setOwner(bankAccountDto.getOwner());
        bankAccount.setTransactions(bankAccountDto.getTransactionDtoList().stream().map(transactionDto -> TransactionMapper.toTransaction(transactionDto, bankAccount)).collect(Collectors.toList()));
        return bankAccount;
    }

    public static BankAccountDto toBankAccountDto(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(bankAccount.getId());
        bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setBalance(bankAccount.getBalance());
        bankAccountDto.setMail(bankAccount.getMail());
        bankAccountDto.setOwner(bankAccount.getOwner());
        bankAccountDto.setTransactionDtoList(bankAccount.getTransactions().stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList()));
        return bankAccountDto;
    }
}
