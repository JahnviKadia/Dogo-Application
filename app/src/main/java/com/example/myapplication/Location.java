package com.example.myapplication;

public class Location {
    String Address;
    String Email;
    String Contact;

    public Location() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public Location(String address, String email, String contact) {
        Address = address;
        Email = email;
        Contact = contact;
    }
}
