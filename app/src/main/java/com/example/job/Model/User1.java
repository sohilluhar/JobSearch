package com.example.job.Model;

import java.util.List;

public class User1 {

    String phonenumber, email, password, name, type, gender, dob, location;

    public User1() {
    }

    public User1(String phonenumber, String email, String password, String name, String type, String gender, String dob, String location) {
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.dob = dob;
        this.location = location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
