package com.delkappa.manos.myapplication;

public class User {

    public String login;
    public String password;
    public String status;
    public String userFirstName;
    public String userName;

    public User() {
        // ...
    }

    public String toString() {
        return(login + " " + password + " " + status + " " + userFirstName + " " + userName + "\n");
    }

}