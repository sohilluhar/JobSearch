package com.example.job.Model;

public class Group {
    String category;
    String file;
    String groupdescription;
    String groupkey;
    String grpimg;
    String location;
    String name;
    String orgid;

    public Group(String category, String file, String groupdescription, String groupkey, String grpimg, String location, String name, String orgid) {
        this.category = category;
        this.file = file;
        this.groupdescription = groupdescription;
        this.groupkey = groupkey;
        this.grpimg = grpimg;
        this.location = location;
        this.name = name;
        this.orgid = orgid;
    }

    public Group() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getGroupdescription() {
        return groupdescription;
    }

    public void setGroupdescription(String groupdescription) {
        this.groupdescription = groupdescription;
    }

    public String getGroupkey() {
        return groupkey;
    }

    public void setGroupkey(String groupkey) {
        this.groupkey = groupkey;
    }

    public String getGrpimg() {
        return grpimg;
    }

    public void setGrpimg(String grpimg) {
        this.grpimg = grpimg;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }
}
