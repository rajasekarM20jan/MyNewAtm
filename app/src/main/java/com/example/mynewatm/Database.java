package com.example.mynewatm;

public class Database {
    String name;
    String dateOfBirth;
    String debitCardNumber;
    String typeOfAccount;
    String pin;
    String balance;

    public Database(){

    }
    public Database(String name,String dateOfBirth,String debitCardNumber,String typeOfAccount,String pin,String balance){
        this.name=name;
        this.dateOfBirth=dateOfBirth;
        this.typeOfAccount=typeOfAccount;
        this.pin=pin;
        this.balance=balance;
        this.debitCardNumber=debitCardNumber;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public String getPin() {
        return pin;
    }

    public String getBalance() {
        return balance;
    }
    public String getDebitCardNumber() {
        return debitCardNumber;
    }
}
