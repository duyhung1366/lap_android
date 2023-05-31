package com.example.lap.model;

public class Employee extends User{

    private byte role;
    /*
    *   1 : trưởng phòng
    *   2 : phó phòng
    *   3 : nhân viên
    * */
    private String codeDepartment;

    public Employee(String name, String code, byte gender, byte role, String codeDepartment) {
        super(name, code, gender);
        this.role = role;
        this.codeDepartment = codeDepartment;
    }

    public byte getRole() {
        return role;
    }

    public void setRole(byte role) {
        this.role = role;
    }

    public String getCodeDepartment() {
        return codeDepartment;
    }

    public void setCodeDepartment(String codeDepartment) {
        this.codeDepartment = codeDepartment;
    }
}
