package com.mchtcavas.simplebank.repository;

import com.mchtcavas.simplebank.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findBankAccountByAccountNumber(String accountNumber);
    Optional<BankAccount> findBankAccountByMail(String mail);
}
