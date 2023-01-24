package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

public class PosBankDeposit implements Serializable {
    @Expose
    private int ADepRecptNo;
    @Expose
    private String IMEI;
    @Expose
    private String Ahead;
    @Expose
    private int DepositAmt;
    @Expose
    private Date DepositDateTimeUTC;
    @Expose
    private String Flag;
    @Expose
    private Date CreationDate;
    @Expose
    private String ReconcileFlag;
    @Expose
    private String FoCode;
    @Expose
    private String Creator;
    @Expose
    private String DataBaseName;
    @Expose
    private int BatchNo;
    @Expose
    private Date BatchDate;

    public int getADepRecptNo() {
        return ADepRecptNo;
    }

    public void setADepRecptNo(int ADepRecptNo) {
        this.ADepRecptNo = ADepRecptNo;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getAhead() {
        return Ahead;
    }

    public void setAhead(String ahead) {
        Ahead = ahead;
    }

    public int getDepositAmt() {
        return DepositAmt;
    }

    public void setDepositAmt(int depositAmt) {
        DepositAmt = depositAmt;
    }

    public Date getDepositDateTimeUTC() {
        return DepositDateTimeUTC;
    }

    public void setDepositDateTimeUTC(Date depositDateTimeUTC) {
        DepositDateTimeUTC = depositDateTimeUTC;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public String getReconcileFlag() {
        return ReconcileFlag;
    }

    public void setReconcileFlag(String reconcileFlag) {
        ReconcileFlag = reconcileFlag;
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

    public int getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(int batchNo) {
        BatchNo = batchNo;
    }

    public Date getBatchDate() {
        return BatchDate;
    }

    public void setBatchDate(Date batchDate) {
        BatchDate = batchDate;
    }

    @Override
    public String toString() {
        return "PosBankDeposit{" +
                "ADepRecptNo=" + ADepRecptNo +
                ", IMEI='" + IMEI + '\'' +
                ", Ahead='" + Ahead + '\'' +
                ", DepositAmt=" + DepositAmt +
                ", DepositDateTimeUTC=" + DepositDateTimeUTC +
                ", Flag='" + Flag + '\'' +
                ", CreationDate=" + CreationDate +
                ", ReconcileFlag='" + ReconcileFlag + '\'' +
                ", FoCode='" + FoCode + '\'' +
                ", Creator='" + Creator + '\'' +
                ", DataBaseName='" + DataBaseName + '\'' +
                ", BatchNo=" + BatchNo +
                ", BatchDate=" + BatchDate +
                '}';
    }
}
