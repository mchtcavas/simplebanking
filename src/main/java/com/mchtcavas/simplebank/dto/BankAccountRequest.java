package com.mchtcavas.simplebank.dto;

public class BankAccountRequest {
    private String owner;
    private String mail;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "BankAccountRequest{" +
                "owner='" + owner + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
