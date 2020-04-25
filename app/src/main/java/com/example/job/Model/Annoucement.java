package com.example.job.Model;

public class Annoucement {

    String title;
    String decsription;
    String date;
    String time;

    public Annoucement(String title, String decsription, String date, String time) {
        this.title = title;
        this.decsription = decsription;
        this.date = date;
        this.time = time;
    }

    public Annoucement() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecsription() {
        return decsription;
    }

    public void setDecsription(String decsription) {
        this.decsription = decsription;
    }
}
