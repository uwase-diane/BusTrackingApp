package com.example.bustrackingapp.entities;

public class User {

    private int id;
    private String fullnames;
    private String password;
    private  String email;

    public User()
    {

    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail()
    {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullnames() {
        return fullnames;
    }

    public void setFullnames(String fullnames) {
        this.fullnames = fullnames;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
