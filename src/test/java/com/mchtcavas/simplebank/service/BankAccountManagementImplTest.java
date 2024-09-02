package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.domain.BankAccount;
import com.mchtcavas.simplebank.dto.BankAccountDto;
import com.mchtcavas.simplebank.exception.BusinessException;
import com.mchtcavas.simplebank.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankAccountManagementImplTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountManagementImpl bankAccountManagement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccountSuccessfully() {
        // Arrange
        String owner = "John Doe";
        String mail = "john.doe@example.com";

        when(bankAccountRepository.findBankAccountByMail(mail)).thenReturn(Optional.empty());

        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(owner);
        bankAccount.setMail(mail);
        bankAccount.setAccountNumber("123456");
        bankAccount.setBalance(0);

        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        BankAccountDto expectedDto = new BankAccountDto();
        expectedDto.setAccountNumber("123456");
        expectedDto.setOwner(owner);
        expectedDto.setMail(mail);
        expectedDto.setBalance(0);

        // Act
        BankAccountDto result = bankAccountManagement.create(owner, mail);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getAccountNumber(), result.getAccountNumber());
        assertEquals(expectedDto.getOwner(), result.getOwner());
        assertEquals(expectedDto.getMail(), result.getMail());
        assertEquals(expectedDto.getBalance(), result.getBalance());

        verify(bankAccountRepository).findBankAccountByMail(mail);
        verify(bankAccountRepository).save(any(BankAccount.class));
    }

    @Test
    public void testCreateAccountWhenAccountAlreadyExists() {
        // Arrange
        String owner = "John Doe";
        String mail = "john.doe@example.com";

        when(bankAccountRepository.findBankAccountByMail(mail)).thenReturn(Optional.of(new BankAccount()));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bankAccountManagement.create(owner, mail);
        });

        assertEquals("An account with the given details already exists.", exception.getMessage());

        verify(bankAccountRepository).findBankAccountByMail(mail);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    public void testCheckAccountSuccessfully() {
        // Arrange
        String accountNumber = "123456";

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setOwner("John Doe");
        bankAccount.setMail("john.doe@example.com");
        bankAccount.setBalance(100);

        when(bankAccountRepository.findBankAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(bankAccount));

        BankAccountDto expectedDto = new BankAccountDto();
        expectedDto.setAccountNumber(accountNumber);
        expectedDto.setOwner("John Doe");
        expectedDto.setMail("john.doe@example.com");
        expectedDto.setBalance(100);

        // Act
        BankAccountDto result = bankAccountManagement.check(accountNumber);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getAccountNumber(), result.getAccountNumber());
        assertEquals(expectedDto.getOwner(), result.getOwner());
        assertEquals(expectedDto.getMail(), result.getMail());
        assertEquals(expectedDto.getBalance(), result.getBalance());

        verify(bankAccountRepository).findBankAccountByAccountNumber(accountNumber);
    }

    @Test
    public void testCheckAccountNotFound() {
        // Arrange
        String accountNumber = "123456";

        when(bankAccountRepository.findBankAccountByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bankAccountManagement.check(accountNumber);
        });

        assertEquals("The specified account was not found.", exception.getMessage());

        verify(bankAccountRepository).findBankAccountByAccountNumber(accountNumber);
    }

    @Test
    public void testUpdateAccount() {
        // Arrange
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountNumber("123456");
        bankAccountDto.setOwner("John Doe");
        bankAccountDto.setMail("john.doe@example.com");
        bankAccountDto.setBalance(200);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber("123456");
        bankAccount.setOwner("John Doe");
        bankAccount.setMail("john.doe@example.com");
        bankAccount.setBalance(200);

        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);

        // Act
        bankAccountManagement.update(bankAccountDto);

        // Assert
        verify(bankAccountRepository).save(any(BankAccount.class));
    }
}
