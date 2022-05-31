package com.example.myapplication;

public class UserFav {
    String email;
    String result;

    public String getEmail ( ) {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getResult ( ) {
        return result;
    }

    public void setResult (String result) {
        this.result = result;
    }

    public UserFav (String email, String result) {
        this.email = email;
        this.result = result;
    }

    public UserFav ( ) {
    }
}
