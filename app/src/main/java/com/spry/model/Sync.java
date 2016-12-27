package com.spry.model;

/**
 * Created by sprydev5 on 11/6/15.
 */
public class Sync {
    private String Name;
    private String Profile_Url;
    private String gender;
    private String age;
    private String email;
    private String DOB;
    private String AndroidID;
    private String DeviceID;
    private String SERIALID;
    private String TIMEELAPSED;
    private String ACTIVITYNAME;
    private String VIEWNAME;
    private String CLICKCOUNT;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfile_Url() {
        return Profile_Url;
    }

    public void setProfile_Url(String profile_Url) {
        Profile_Url = profile_Url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dOB) {
        DOB = dOB;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getSERIALID() {
        return SERIALID;
    }

    public void setSERIALID(String sERIALID) {
        SERIALID = sERIALID;
    }

    public String getTIMEELAPSED() {
        return TIMEELAPSED;
    }

    public void setTIMEELAPSED(String TIMEELAPSED) {
        this.TIMEELAPSED = TIMEELAPSED;
    }

    public String getACTIVITYNAME() {
        return ACTIVITYNAME;
    }

    public void setACTIVITYNAME(String ACTIVITYNAME) {
        this.ACTIVITYNAME = ACTIVITYNAME;
    }

    public String getVIEWNAME() {
        return VIEWNAME;
    }

    public void setVIEWNAME(String VIEWNAME) {
        this.VIEWNAME = VIEWNAME;
    }

    public String getCLICKCOUNT() {
        return CLICKCOUNT;
    }

    public void setCLICKCOUNT(String CLICKCOUNT) {
        this.CLICKCOUNT = CLICKCOUNT;
    }
}
