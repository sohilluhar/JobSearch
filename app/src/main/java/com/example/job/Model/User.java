package com.example.job.Model;

import java.util.List;

public class User {
    private String email, name, pass, type, userphone, about, github, linkedin, work, bussinessemail, bussinessphone, bussinessaddress, profileurl;
    private String personal_fullname, personal_address, personal_contact, personal_mail, personal_careerobjective;
    private List<String> goingevent, connection, pendingconnection, requestedconnection;
    private String extraType;

    public User(String email, String name, String pass, String type, String extraType, String userphone, String about, String github, String linkedin, String work, String bussinessemail, String bussinessphone, String bussinessaddress, List<String> goingevent, List<String> connection, List<String> pendingconnection, List<String> requestedconnectoin, String profileurl) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.type = type;
        this.extraType = extraType;
        this.userphone = userphone;
        this.about = about;
        this.github = github;
        this.linkedin = linkedin;
        this.work = work;
        this.bussinessemail = bussinessemail;
        this.bussinessphone = bussinessphone;
        this.bussinessaddress = bussinessaddress;
        this.goingevent = goingevent;
        this.connection = connection;
        this.pendingconnection = pendingconnection;
        this.requestedconnection = requestedconnectoin;
        this.profileurl = profileurl;
        /*this.personal_fullname = personal_fullname;
        this.personal_address=personal_address;
        this.personal_contact=personal_contact;
        this.personal_mail=personal_mail;
        this.personal_careerobjective=personal_careerobjective;*/
    }

    User() {
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

//    public User(String email, String name, String pass, String type, String userphone, List<String> goingevent
//
//    ) {
//        this.email = email;
//        this.name = name;
//        this.pass = pass;
//        this.type = type;
//        this.userphone = userphone;
//        this.goingevent = goingevent;
//    }

    public List<String> getConnection() {
        return connection;
    }

    public void setConnection(List<String> connection) {
        this.connection = connection;
    }

    public List<String> getPendingconnection() {
        return pendingconnection;
    }

    public void setPendingconnection(List<String> pendingconnection) {
        this.pendingconnection = pendingconnection;
    }

    public String getExtraType() {
        return extraType;
    }

    public void setExtraType(String extraType) {
        this.extraType = extraType;
    }

    public List<String> getRequestedconnection() {
        return requestedconnection;
    }

    public void setRequestedconnection(List<String> requestedconnection) {
        this.requestedconnection = requestedconnection;
    }

    public String getBussinessaddress() {
        return bussinessaddress;
    }

    public void setBussinessaddress(String bussinessaddress) {
        this.bussinessaddress = bussinessaddress;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getBussinessemail() {
        return bussinessemail;
    }

    public void setBussinessemail(String bussinessemail) {
        this.bussinessemail = bussinessemail;
    }

    public String getBussinessphone() {
        return bussinessphone;
    }

    public void setBussinessphone(String bussinessphone) {
        this.bussinessphone = bussinessphone;
    }

    public List<String> getGoingevent() {
        return goingevent;
    }

    public void setGoingevent(List<String> goingevent) {
        this.goingevent = goingevent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPersonal_fullname() {
        return personal_fullname;
    }

    public void setPersonal_fullname(String personal_fullname) {
        this.personal_fullname = personal_fullname;
    }

    public String getPersonal_address() {
        return personal_address;
    }

    public void setPersonal_address(String personal_address) {
        this.personal_address = personal_address;
    }

    public String getPersonal_contact() {
        return personal_contact;
    }

    public void setPersonal_contact(String personal_contact) {
        this.personal_contact = personal_contact;
    }

    public String getPersonal_mail() {
        return personal_mail;
    }

    public void setPersonal_mail(String personal_mail) {
        this.personal_mail = personal_mail;
    }

    public String getPersonal_careerobjective() {
        return personal_careerobjective;
    }

    public void setPersonal_careerobjective(String personal_careerobjective) {
        this.personal_careerobjective = personal_careerobjective;
    }


}
