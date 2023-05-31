package com.example.lap.model;

public class User {
    private String name;
    private String code;
    private byte gender; // 1 : nam , 2: nữ

    public User(String name, String code, byte gender) {
        this.name = name;
        this.code = code;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }
}
