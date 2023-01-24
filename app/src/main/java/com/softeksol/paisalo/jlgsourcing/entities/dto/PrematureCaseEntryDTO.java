package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.SerializedName;

public class PrematureCaseEntryDTO {

    @SerializedName("FcBrkupCr")
    private double fcBrkupCr;

    @SerializedName("FoCode_BrCode")
    private String foCodeBrCode;

    @SerializedName("Party_cd")
    private String partyCd;

    @SerializedName("ahead")
    private String ahead;

    @SerializedName("Narr")
    private String narr;

    @SerializedName("vdate")
    private String vdate;

    @SerializedName("CaseCode")
    private String caseCode;

    @SerializedName("id")
    private int id;

    @SerializedName("Creator")
    private String creator;

    @SerializedName("FcBrkupDesc")
    private String fcBrkupDesc;

    @SerializedName("FcBrkupDr")
    private double fcBrkupDr;

    public void setFcBrkupCr(double fcBrkupCr) {
        this.fcBrkupCr = fcBrkupCr;
    }

    public double getFcBrkupCr() {
        return fcBrkupCr;
    }

    public void setFoCodeBrCode(String foCodeBrCode) {
        this.foCodeBrCode = foCodeBrCode;
    }

    public String getFoCodeBrCode() {
        return foCodeBrCode;
    }

    public void setPartyCd(String partyCd) {
        this.partyCd = partyCd;
    }

    public String getPartyCd() {
        return partyCd;
    }

    public void setAhead(String ahead) {
        this.ahead = ahead;
    }

    public String getAhead() {
        return ahead;
    }

    public void setNarr(String narr) {
        this.narr = narr;
    }

    public String getNarr() {
        return narr;
    }

    public void setVdate(String vdate) {
        this.vdate = vdate;
    }

    public String getVdate() {
        return vdate;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setFcBrkupDesc(String fcBrkupDesc) {
        this.fcBrkupDesc = fcBrkupDesc;
    }

    public String getFcBrkupDesc() {
        return fcBrkupDesc;
    }

    public void setFcBrkupDr(double fcBrkupDr) {
        this.fcBrkupDr = fcBrkupDr;
    }

    public double getFcBrkupDr() {
        return fcBrkupDr;
    }

    @Override
    public String toString() {
        return
                "PrematureCaseDataDTO{" +
                        "fcBrkupCr = '" + fcBrkupCr + '\'' +
                        ",foCode_BrCode = '" + foCodeBrCode + '\'' +
                        ",party_cd = '" + partyCd + '\'' +
                        ",ahead = '" + ahead + '\'' +
                        ",narr = '" + narr + '\'' +
                        ",vdate = '" + vdate + '\'' +
                        ",caseCode = '" + caseCode + '\'' +
                        ",id = '" + id + '\'' +
                        ",creator = '" + creator + '\'' +
                        ",fcBrkupDesc = '" + fcBrkupDesc + '\'' +
                        ",fcBrkupDr = '" + fcBrkupDr + '\'' +
                        "}";
    }
}