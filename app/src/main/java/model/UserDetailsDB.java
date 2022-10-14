package model;

public class UserDetailsDB {
    String name;
    String userName;
    String MPin;
    String balance;
    String login;

    UserDetailsDB(){

    }
    public UserDetailsDB(String name,String userName,String MPin,String balance,String login){
        this.name=name;
        this.userName=userName;
        this.MPin=MPin;
        this.balance=balance;
        this.login=login;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getMPin() {
        return MPin;
    }

    public String getBalance() {
        return balance;
    }

    public String getLogin() {
        return login;
    }


}
