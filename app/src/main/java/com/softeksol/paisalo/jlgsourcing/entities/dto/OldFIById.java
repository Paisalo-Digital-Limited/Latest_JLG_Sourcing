
package com.softeksol.paisalo.jlgsourcing.entities.dto;


import com.google.gson.annotations.SerializedName;

import java.util.Comparator;


public class OldFIById {

    @SerializedName("AadharID")
    private String aadharID;
    @SerializedName("AllowToInput")
    private Boolean allowToInput;
    @SerializedName("Approved")
    private String approved;
    @SerializedName("CaseCode")
    private String caseCode;
    @SerializedName("Creator")
    private String creator;
    @SerializedName("CustName")
    private String custName;
    @SerializedName("DueAmt")
    private Double dueAmt;
    @SerializedName("FiCode")
    private Long fiCode;
    @SerializedName("FiInputDays")
    private Long fiInputDays;
    @SerializedName("FiInputDt")
    private String fiInputDt;
    @SerializedName("GuarantyInCases")
    private Long guarantyInCases;
    @SerializedName("GuardianName")
    private String guardianName;
    @SerializedName("InstPending")
    private Long instPending;
    @SerializedName("Invest")
    private Double invest;
    @SerializedName("RejectReason")
    private String rejectReason;

    public static Comparator<OldFIById> sortByInputDaysDesc = new Comparator<OldFIById>() {
        public int compare(OldFIById oldFI1, OldFIById oldFI2) {
            if (oldFI1.fiInputDays == oldFI2.fiInputDays)
                return 0;
            else if (oldFI1.fiInputDays > oldFI2.fiInputDays)
                return 1;
            else
                return -1;
        }

    };


    public String getAadharID() {
        return aadharID;
    }

    public Boolean getAllowToInput() {
        return allowToInput;
    }

    public String getApproved() {
        return approved;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public String getCreator() {
        return creator;
    }

    public String getCustName() {
        return custName;
    }

    public Double getDueAmt() {
        return dueAmt;
    }

    public Long getFiCode() {
        return fiCode;
    }

    public Long getFiInputDays() {
        return fiInputDays;
    }

    public String getFiInputDt() {
        return fiInputDt;
    }

    public Long getGuarantyInCases() {
        return guarantyInCases;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public Long getInstPending() {
        return instPending;
    }

    public Double getInvest() {
        return invest;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setAadharID(String aadharID) {
        this.aadharID = aadharID;
    }

    public void setAllowToInput(Boolean allowToInput) {
        this.allowToInput = allowToInput;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setDueAmt(Double dueAmt) {
        this.dueAmt = dueAmt;
    }

    public void setFiCode(Long fiCode) {
        this.fiCode = fiCode;
    }

    public void setFiInputDays(Long fiInputDays) {
        this.fiInputDays = fiInputDays;
    }

    public void setFiInputDt(String fiInputDt) {
        this.fiInputDt = fiInputDt;
    }

    public void setGuarantyInCases(Long guarantyInCases) {
        this.guarantyInCases = guarantyInCases;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public void setInstPending(Long instPending) {
        this.instPending = instPending;
    }

    public void setInvest(Double invest) {
        this.invest = invest;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
