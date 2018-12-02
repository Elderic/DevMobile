package com.delkappa.manos.myapplication;

public class Post {

    public String deptID;
    public String userFirstName;
    public String userID;
    public String userName;

    public Post() {
        // ...
    }

    public String toString() {
        return(deptID + " " + userFirstName + " " + userID + " " + userName + "\n");
    }

}