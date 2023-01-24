package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.SerializedName;

public class PreclosureCaseDTO {

    @SerializedName("CityCode")
    private String cityCode;

    @SerializedName("InstsAmtDue")
    private double instsAmtDue;

    @SerializedName("creator")
    private String creator;

    @SerializedName("FHName")
    private String fHName;

    @SerializedName("NofInstDue")
    private int nofInstDue;

    @SerializedName("Address")
    private String address;

    @SerializedName("dbName")
    private String dbName;

    @SerializedName("CaseCode")
    private String caseCode;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("FirstInstDate")
    private String firstInstDate;

    @SerializedName("DataAsOn")
    private String dataAsOn;

    @SerializedName("PartyCd")
    private String partyCd;

    @SerializedName("CustName")
    private String custName;

    @SerializedName("TotalRecdAmt")
    private double totalRecdAmt;

    @SerializedName("TotalRecdCnt")
    private int totalRecdCnt;

    @SerializedName("InstDueAsOn")
    private String instDueAsOn;

    @SerializedName("TotalDueCnt")
    private int totalDueCnt;

    @SerializedName("FoCode")
    private String foCode;

    @SerializedName("TotalDueAmt")
    private double totalDueAmt;

    @SerializedName("NoOfInsts")
    private int noOfInsts;

    @SerializedName("db")
    private String db;

    @SerializedName("FutureDue")
    private double futureDue;

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setInstsAmtDue(double instsAmtDue) {
        this.instsAmtDue = instsAmtDue;
    }

    public double getInstsAmtDue() {
        return instsAmtDue;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setFHName(String fHName) {
        this.fHName = fHName;
    }

    public String getFHName() {
        return fHName;
    }

    public void setNofInstDue(int nofInstDue) {
        this.nofInstDue = nofInstDue;
    }

    public int getNofInstDue() {
        return nofInstDue;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setFirstInstDate(String firstInstDate) {
        this.firstInstDate = firstInstDate;
    }

    public String getFirstInstDate() {
        return firstInstDate;
    }

    public void setDataAsOn(String dataAsOn) {
        this.dataAsOn = dataAsOn;
    }

    public String getDataAsOn() {
        return dataAsOn;
    }

    public void setPartyCd(String partyCd) {
        this.partyCd = partyCd;
    }

    public String getPartyCd() {
        return partyCd;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setTotalRecdAmt(double totalRecdAmt) {
        this.totalRecdAmt = totalRecdAmt;
    }

    public double getTotalRecdAmt() {
        return totalRecdAmt;
    }

    public void setTotalRecdCnt(int totalRecdCnt) {
        this.totalRecdCnt = totalRecdCnt;
    }

    public int getTotalRecdCnt() {
        return totalRecdCnt;
    }

    public void setInstDueAsOn(String instDueAsOn) {
        this.instDueAsOn = instDueAsOn;
    }

    public String getInstDueAsOn() {
        return instDueAsOn;
    }

    public void setTotalDueCnt(int totalDueCnt) {
        this.totalDueCnt = totalDueCnt;
    }

    public int getTotalDueCnt() {
        return totalDueCnt;
    }

    public void setFoCode(String foCode) {
        this.foCode = foCode;
    }

    public String getFoCode() {
        return foCode;
    }

    public void setTotalDueAmt(double totalDueAmt) {
        this.totalDueAmt = totalDueAmt;
    }

    public double getTotalDueAmt() {
        return totalDueAmt;
    }

    public void setNoOfInsts(int noOfInsts) {
        this.noOfInsts = noOfInsts;
    }

    public int getNoOfInsts() {
        return noOfInsts;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getDb() {
        return db;
    }

    public void setFutureDue(double futureDue) {
        this.futureDue = futureDue;
    }

    public double getFutureDue() {
        return futureDue;
    }

    @Override
    public String toString() {
        return
                "PreclosureDTO{" +
                        "cityCode = '" + cityCode + '\'' +
                        ",instsAmtDue = '" + instsAmtDue + '\'' +
                        ",creator = '" + creator + '\'' +
                        ",fHName = '" + fHName + '\'' +
                        ",nofInstDue = '" + nofInstDue + '\'' +
                        ",address = '" + address + '\'' +
                        ",dbName = '" + dbName + '\'' +
                        ",caseCode = '" + caseCode + '\'' +
                        ",mobile = '" + mobile + '\'' +
                        ",firstInstDate = '" + firstInstDate + '\'' +
                        ",dataAsOn = '" + dataAsOn + '\'' +
                        ",partyCd = '" + partyCd + '\'' +
                        ",custName = '" + custName + '\'' +
                        ",totalRecdAmt = '" + totalRecdAmt + '\'' +
                        ",totalRecdCnt = '" + totalRecdCnt + '\'' +
                        ",instDueAsOn = '" + instDueAsOn + '\'' +
                        ",totalDueCnt = '" + totalDueCnt + '\'' +
                        ",foCode = '" + foCode + '\'' +
                        ",totalDueAmt = '" + totalDueAmt + '\'' +
                        ",noOfInsts = '" + noOfInsts + '\'' +
                        ",db = '" + db + '\'' +
                        ",futureDue = '" + futureDue + '\'' +
                        "}";
    }
}