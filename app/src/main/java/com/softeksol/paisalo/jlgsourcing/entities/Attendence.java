package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;

public class Attendence {

    @Expose
    private String MobileImei;

    public String getMobileImei() {
        return MobileImei;
    }

    public void setMobileImei(String mobileImei) {
        MobileImei = mobileImei;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getGpsLatInOut() {
        return GpsLatInOut;
    }

    public void setGpsLatInOut(String gpsLatInOut) {
        GpsLatInOut = gpsLatInOut;
    }

    public String getGpsLongInOut() {
        return GpsLongInOut;
    }

    public void setGpsLongInOut(String gpsLongInOut) {
        GpsLongInOut = gpsLongInOut;
    }

    public String getAddrInOut() {
        return AddrInOut;
    }

    public void setAddrInOut(String addrInOut) {
        AddrInOut = addrInOut;
    }

    public String getMobileDateTime() {
        return MobileDateTime;
    }

    public void setMobileDateTime(String mobileDateTime) {
        MobileDateTime = mobileDateTime;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getInOutTime() {
        return InOutTime;
    }

    public void setInOutTime(String inOutTime) {
        InOutTime = inOutTime;
    }

    public String getInOutFlag() {
        return InOutFlag;
    }

    public void setInOutFlag(String inOutFlag) {
        InOutFlag = inOutFlag;
    }

    @Expose
    private String UserID;
    @Expose
    private String Creator;
    @Expose
    private String BranchCode;
    @Expose
    private String GpsLatInOut;
    @Expose
    private String GpsLongInOut;
    @Expose
    private String AddrInOut;
    @Expose
    private String MobileDateTime;
    @Expose
    private String CreationDate;
    @Expose
    private String InOutTime;
    @Expose
    private String InOutFlag;

}
