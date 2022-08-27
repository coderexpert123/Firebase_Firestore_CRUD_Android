package com.singh.fl;
//Model class for user
public class UserModel {


    String name;
    String email;
    String uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserModel(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;

    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
