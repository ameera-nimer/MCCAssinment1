package com.example.mcc1;

public class User {


    private String id ;
    private String  Name;

    private String  number;

    private String  address;

    public User() {
    }

    public User(String id, String name, String number, String address) {
        this.id = id;
        Name = name;
        this.number = number;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    }
