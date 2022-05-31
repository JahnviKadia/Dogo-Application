package com.example.myapplication;

public class Userinformation2 {

    public String name;
    public String surname;
    public String phoneno;

    public Userinformation2(){
    }

    public Userinformation2(String name,String surname, String phoneno){
        this.name = name;
        this.surname = surname;
        this.phoneno = phoneno;
    }
    public String getUserName() {
        return name;
    }
    public String getUserSurname() {
        return surname;
    }
    public String getUserPhoneno() {
        return phoneno;
    }
}