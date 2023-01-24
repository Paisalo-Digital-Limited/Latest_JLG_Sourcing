package com.softeksol.paisalo.jlgsourcing.entities;

import java.util.Date;

public class ReceiptData {
    private int InstRcvID;
    private String CaseCode;
    private int InstRcvAmt;
    private Date CreationDate;
    private String FoCode;
    private String Creator;
    private String DataBaseName;

    public int getInstRcvID() {
        return InstRcvID;
    }

    public void setInstRcvID(int instRcvID) {
        InstRcvID = instRcvID;
    }

    public String getCaseCode() {
        return CaseCode;
    }

    public void setCaseCode(String caseCode) {
        CaseCode = caseCode;
    }

    public int getInstRcvAmt() {
        return InstRcvAmt;
    }

    public void setInstRcvAmt(int instRcvAmt) {
        InstRcvAmt = instRcvAmt;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public String getFoCode() {
        return FoCode;
    }

    public void setFoCode(String foCode) {
        FoCode = foCode;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getDataBaseName() {
        return DataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        DataBaseName = dataBaseName;
    }

    @Override
    public String toString() {
        return "ReceiptData{" +
                "InstRcvID='" + InstRcvID + '\'' +
                ", CaseCode='" + CaseCode + '\'' +
                ", InstRcvAmt=" + InstRcvAmt +
                ", CreationDate=" + CreationDate +
                ", FoCode='" + FoCode + '\'' +
                ", Creator='" + Creator + '\'' +
                ", DataBaseName='" + DataBaseName + '\'' +
                '}';
    }
}
