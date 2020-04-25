package com.example.job.Model;

public class Sponser {

    String category;
    String description;
    String emailid;
    String key;
    String name;
    String type;
    String img;

    public Sponser() {
    }

    public Sponser(String category, String description, String emailid, String key, String name, String type, String img) {
        this.category = category;
        this.description = description;
        this.emailid = emailid;
        this.key = key;
        this.name = name;
        this.type = type;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
