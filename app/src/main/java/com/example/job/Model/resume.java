package com.example.job.Model;
import java.util.List;

public class resume {
    private String personal_fullname,personal_address,personal_contact,personal_mail,personal_careerobjective,profileurl;
    public resume(String personal_fullname,String personal_address,String personal_contact,String personal_mail ,String personal_careerobjective,String profileurl){
        this.personal_fullname=personal_fullname;
        this.personal_address=personal_address;
        this.personal_contact=personal_contact;
        this.personal_mail=personal_mail;
        this.personal_careerobjective=personal_careerobjective;
        this.profileurl = profileurl;
    }

    public resume() {
    }

    public String getPersonal_fullname() {
        return personal_fullname;
    }
    public String getPersonal_address() {
        return personal_address;
    }
    public String getPersonal_contact() {
        return personal_contact;
    }
    public String getPersonal_mail() {
        return personal_mail;
    }
    public String getPersonal_careerobjective() {
        return personal_careerobjective;
    }
    public String getProfileurl() {
        return profileurl;
    }
    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

}
