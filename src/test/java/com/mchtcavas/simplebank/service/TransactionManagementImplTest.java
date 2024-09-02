package com.mchtcavas.simplebank.service;

import com.mchtcavas.simplebank.config.ApplicationProperties;
import com.mchtcavas.simplebank.domain.TransactionType;
import com.mchtcavas.simplebank.dto.BankAccountDto;
import com.mchtcavas.simplebank.dto.TransactionDto;
import com.mchtcavas.simplebank.validation.ValidationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionManagementImplTest {
    @Mock
    private BankAccountManagement bankAccountManagement;

    @Mock
    private ValidationManager validationManager;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private Logger log;

    @InjectMocks
    private TransactionManagementImpl transactionManagement;

    @Test
    public void testDebit() {
        // Arrange
        String accountNumber = "123456";
        double amount = 100.0;
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountNumber(accountNumber);
        bankAccountDto.setBalance(200.0);

        when(bankAccountManagement.check(anyString())).thenReturn(bankAccountDto);
        doNothing().when(bankAccountManagement).update(any(BankAccountDto.class));

        // Act
        TransactionDto transactionDto = transactionManagement.debit(amount, accountNumber);

        // Assert
        verify(bankAccountManagement).check(accountNumber);
        verify(bankAccountManagement).update(bankAccountDto);
        assertEquals(amount, transactionDto.getAmount());
        assertEquals(TransactionType.WITHDRAWAL_TRANSACTION, transactionDto.getType());
        assertNotNull(transactionDto.getApprovalCode());
    }

    @Test
    public void testCredit() {
        // Arrange
        String accountNumber = "123456";
        double amount = 100.0;
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountNumber(accountNumber);
        bankAccountDto.setBalance(200.0);

        when(bankAccountManagement.check(anyString())).thenReturn(bankAccountDto);
        doNothing().when(bankAccountManagement).update(any(BankAccountDto.class));

        // Act
        TransactionDto transactionDto = transactionManagement.credit(amount, accountNumber);

        // Assert
        verify(bankAccountManagement).check(accountNumber);
        verify(bankAccountManagement).update(bankAccountDto);
        assertEquals(amount, transactionDto.getAmount());
        assertEquals(TransactionType.DEPOSIT_TRANSACTION, transactionDto.getType());
        assertNotNull(transactionDto.getApprovalCode());
    }

    @Test
    public void testPhoneBillPayment() {
        // Arrange
        String accountNumber = "123456";
        double amount = 100.0;
        String providerName = "Provider";
        String billNumber = "Bill123";
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountNumber(accountNumber);
        bankAccountDto.setBalance(200.0);

        when(bankAccountManagement.check(anyString())).thenReturn(bankAccountDto);
        doNothing().when(bankAccountManagement).update(any(BankAccountDto.class));

        // Act
        TransactionDto transactionDto = transactionManagement.phoneBillPayment(providerName, billNumber, amount, accountNumber);

        // Assert
        verify(bankAccountManagement).check(accountNumber);
        verify(bankAccountManagement).update(bankAccountDto);
        assertEquals(amount, transactionDto.getAmount());
        assertEquals(TransactionType.PHONE_BILL_PAYMENT_TRANSACTION, transactionDto.getType());
        assertNotNull(transactionDto.getApprovalCode());
    }
}
