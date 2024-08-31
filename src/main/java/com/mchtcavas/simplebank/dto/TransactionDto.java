package com.mchtcavas.simplebank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mchtcavas.simplebank.domain.TransactionType;

import java.time.LocalDateTime;

public class TransactionDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private LocalDateTime date;
    private double amount;
    private TransactionType type;
    private String approvalCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", type=" + type +
                ", approvalCode='" + approvalCode + '\'' +
                '}';
    }
}
