package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.domain.BankAccount;
import com.mchtcavas.simplebank.dto.BankAccountDto;
import com.mchtcavas.simplebank.exception.BusinessException;
import com.mchtcavas.simplebank.exception.BusinessExceptionKey;
import com.mchtcavas.simplebank.repository.BankAccountRepository;
import com.mchtcavas.simplebank.util.BankAccountMapper;
import com.mchtcavas.simplebank.util.RandomAccountNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BankAccountManagementImpl implements BankAccountManagement {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BankAccountRepository bankAccountRepository;

    public BankAccountManagementImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccountDto create(String owner, String mail) {
        log.debug("Entering create method with owner={} and mail={}", owner, mail);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(owner);
        bankAccount.setMail(mail);
        bankAccount.setAccountNumber(RandomAccountNumberGenerator.create());
        bankAccount.setBalance(0);

        bankAccount = this.bankAccountRepository.save(bankAccount);

        log.debug("Created BankAccount with accountNumber={} and initial balance={}", bankAccount.getAccountNumber(), bankAccount.getBalance());

        return BankAccountMapper.toBankAccountDto(bankAccount);
    }

    @Override
    public BankAccountDto check(String accountNumber) {
        log.debug("Entering checkBankAccount method with accountNumber={}", accountNumber);

        Optional<BankAccount> bankAccount = this.bankAccountRepository.findBankAccountByAccountNumber(accountNumber);

        if (bankAccount.isEmpty()) {
            log.error("Account not found for accountNumber={}", accountNumber);
            throw new BusinessException(BusinessExceptionKey.ACCOUNT_NOT_FOUND);
        }

        BankAccountDto bankAccountDto = BankAccountMapper.toBankAccountDto(bankAccount.get());
        log.debug("Successfully found and mapped BankAccountDto: {}", bankAccountDto);

        return bankAccountDto;
    }

    @Override
    public void update(BankAccountDto bankAccountDto) {
        log.debug("Entering updateBankAccount method with Bank Account Number={}", bankAccountDto.getAccountNumber());

        BankAccount bankAccount = BankAccountMapper.toBankAccount(bankAccountDto);
        log.debug("Mapped BankAccountDto to Bank Account Number: {}", bankAccount.getAccountNumber());

        this.bankAccountRepository.save(bankAccount);
        log.debug("Bank account saved to repository: {}", bankAccount.getAccountNumber());
    }
}
