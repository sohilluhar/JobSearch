package com.example.job.Model;

public class Job {

    private String name;
    private String details;
    private String location;
    private String salary;
    private String vacancy;
    private String designation;
    private String jobtype;
    private String skill;
    private String workexperience;
    private String criteriahsc;
    private String criteriagraduate;
    private String criteriapostgraduat;
    private String companyname;
    private String contactname;
    private String contactnumber;
    private String id;
    private String img;

    public Job(String name, String details, String location, String salary, String vacancy, String designation, String jobtype, String skill, String workexperience, String criteriahsc, String criteriagraduate, String criteriapostgraduat, String companyname, String contactname, String contactnumber, String id, String img) {
        this.name = name;
        this.details = details;
        this.location = location;
        this.salary = salary;
        this.vacancy = vacancy;
        this.designation = designation;
        this.jobtype = jobtype;
        this.skill = skill;
        this.workexperience = workexperience;
        this.criteriahsc = criteriahsc;
        this.criteriagraduate = criteriagraduate;
        this.criteriapostgraduat = criteriapostgraduat;
        this.companyname = companyname;
        this.contactname = contactname;
        this.contactnumber = contactnumber;
        this.id = id;
        this.img = img;
    }

    public Job() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getWorkexperience() {
        return workexperience;
    }

    public void setWorkexperience(String workexperience) {
        this.workexperience = workexperience;
    }

    public String getCriteriahsc() {
        return criteriahsc;
    }

    public void setCriteriahsc(String criteriahsc) {
        this.criteriahsc = criteriahsc;
    }

    public String getCriteriagraduate() {
        return criteriagraduate;
    }

    public void setCriteriagraduate(String criteriagraduate) {
        this.criteriagraduate = criteriagraduate;
    }

    public String getCriteriapostgraduat() {
        return criteriapostgraduat;
    }

    public void setCriteriapostgraduat(String criteriapostgraduat) {
        this.criteriapostgraduat = criteriapostgraduat;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
