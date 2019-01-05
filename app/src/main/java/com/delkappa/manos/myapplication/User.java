package com.delkappa.manos.myapplication;

public class User {
    public String email;
    public String login;
    public String password;
    public String phoneNumber;
    public String status;
    public String userFirstName;
    public String userName;

    public User(String pEmail, String pLogin, String pPassword, String pPhoneNumber, String pStatus, String pUserFirstName, String pUserName ) {
        this.email = pEmail;
        this.login = pLogin;
        this.password = pPassword;
        this.phoneNumber = pPhoneNumber;
        this.status = pStatus;
        this.userFirstName = pUserFirstName;
        this.userName = pUserName;
    }

    public User() {

    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String toString() {
        return(email + " " + login + " " + password + " " + phoneNumber + " " + status + " " + userFirstName + " " + userName + "\n");
    }

}