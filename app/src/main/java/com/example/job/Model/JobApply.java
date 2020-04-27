package com.example.job.Model;

public class JobApply {
    String phone;
    String status;

    public JobApply() {
    }

    public JobApply(String phone, String status) {
        this.phone = phone;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
