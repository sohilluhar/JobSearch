package com.example.job.Model;

import java.util.List;

public class Event {
    private String address;
    private String description;
    private String category;
    private String city;


    private String pincode;
    private String event_name;
    private String ngo_name;
    private String ngo_logo;
    private String imgurl;
    private String date;
    private String time;
    private String location;
    private String organize_by;
    private String mail;
    private String vol_req;
    private String ngo_id;
    private String approval_req;
    private String event_key;
    private String event_Vol_No_Req;
    private List<String> photos;
    private List<Annoucement> annoucement;
    private List<String> comment;
    private List<String> speakers;
    private List<String> sponsor;

    public Event(String address, String description, String city, String pincode, String event_name,
                 String ngo_name, String ngo_logo, String date, String time, String location, String organize_by,
                 String vol_req, String ngo_id, String approval_req, List<String> photos, List<Annoucement> annoucement, List<String> comment, String event_key,
                 String event_limit, String imgurl, String mail, List<String> speakers, List<String> sponsor, String category
    ) {
        this.address = address;
        this.description = description;
        this.city = city;
        this.pincode = pincode;
        this.event_name = event_name;
        this.ngo_name = ngo_name;
        this.ngo_logo = ngo_logo;
        this.date = date;
        this.time = time;
        this.location = location;
        this.organize_by = organize_by;
        this.vol_req = vol_req;
        this.ngo_id = ngo_id;
        this.approval_req = approval_req;
        this.photos = photos;
        this.annoucement = annoucement;
        this.comment = comment;
        this.event_key = event_key;
        this.event_Vol_No_Req = event_limit;
        this.imgurl = imgurl;
        this.mail = mail;
        this.speakers = speakers;
        this.sponsor = sponsor;
        this.category = category;
    }

    public Event() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getSponsor() {
        return sponsor;
    }

    public void setSponsor(List<String> sponsor) {
        this.sponsor = sponsor;
    }

    public String getEvent_Vol_No_Req() {
        return event_Vol_No_Req;
    }

    public void setEvent_Vol_No_Req(String event_Vol_No_Req) {
        this.event_Vol_No_Req = event_Vol_No_Req;
    }

    public List<String> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<String> speakers) {
        this.speakers = speakers;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEvent_Limit() {
        return event_Vol_No_Req;
    }

    public void setEvent_Limit(String event_Limit) {
        this.event_Vol_No_Req = event_Limit;
    }

    public String getEvent_key() {
        return event_key;
    }

    public void setEvent_key(String event_key) {
        this.event_key = event_key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(String ngo_id) {
        this.ngo_id = ngo_id;
    }

    public String getApproval_req() {
        return approval_req;
    }

    public void setApproval_req(String approval_req) {
        this.approval_req = approval_req;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getNgo_name() {
        return ngo_name;
    }

    public void setNgo_name(String ngo_name) {
        this.ngo_name = ngo_name;
    }

    public String getNgo_logo() {
        return ngo_logo;
    }

    public void setNgo_logo(String ngo_logo) {
        this.ngo_logo = ngo_logo;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganize_by() {
        return organize_by;
    }

    public void setOrganize_by(String organize_by) {
        this.organize_by = organize_by;
    }

    public String getVol_req() {
        return vol_req;
    }

    public void setVol_req(String vol_req) {
        this.vol_req = vol_req;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public List<Annoucement> getAnnoucement() {
        return annoucement;
    }

    public void setAnnoucement(List<Annoucement> annoucement) {
        this.annoucement = annoucement;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

}