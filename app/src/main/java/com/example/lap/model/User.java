package com.example.lap.model;

public class User {
    private int id;
    private String name;
    private String code;
    private byte gender;

    public User(int id, String name, String code, byte gender) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
