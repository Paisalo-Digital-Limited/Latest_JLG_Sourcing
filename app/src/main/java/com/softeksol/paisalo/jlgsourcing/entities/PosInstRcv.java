package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;

import java.util.Comparator;
import java.util.Date;

public class PosInstRcv {

    @Expose
    private int InstRcvID;
    @Expose
    private String IMEI;
    @Expose
    private String CaseCode;
    @Expose
    private int RcptNo;
    @Expose
    private int InstRcvAmt;
    @Expose
    private Date InstRcvDateTimeUTC;
    @Expose
    private String Flag;
    @Expose
    private Date CreationDate;
    @Expose
    private byte BatchNo;
    @Expose
    private Date BatchDate;
    @Expose
    private String FoCode;
    @Expose
    private String DataBaseName;
    @Expose
    private String Creator;
    @Expose
    private String CustName;
    @Expose
    private String PartyCd;
    @Expose
    private String PayFlag;
    @Expose
    private String SmsMobNo;
    @Expose
    private int InterestAmt;

    public static Comparator<PosInstRcv> InstRcvName = new Comparator<PosInstRcv>() {
        public int compare(PosInstRcv dueData1, PosInstRcv dueData2) {

            int compareName = dueData1.CustName.compareTo(dueData2.CustName);

            return compareName;
        }

    };


    public int getInstRcvID() {
        return InstRcvID;
    }

    public void setInstRcvID(int instRcvID) {
        InstRcvID = instRcvID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getCaseCode() {
        return CaseCode;
    }

    public void setCaseCode(String caseCode) {
        CaseCode = caseCode;
    }

    public int getRcptNo() {
        return RcptNo;
    }

    public void setRcptNo(int rcptNo) {
        RcptNo = rcptNo;
    }

    public int getInstRcvAmt() {
        return InstRcvAmt;
    }

    public void setInstRcvAmt(int instRcvAmt) {
        InstRcvAmt = instRcvAmt;
    }

    public Date getInstRcvDateTimeUTC() {
        return InstRcvDateTimeUTC;
    }

    public void setInstRcvDateTimeUTC(Date instRcvDateTimeUTC) {
        InstRcvDateTimeUTC = instRcvDateTimeUTC;
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

    public byte getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(byte batchNo) {
        BatchNo = batchNo;
    }

    public Date getBatchDate() {
        return BatchDate;
    }

    public void setBatchDate(Date batchDate) {
        BatchDate = batchDate;
    }

    public String getFoCode() {
        return FoCode;
    }

    public void setFoCode(String foCode) {
        FoCode = foCode;
    }

    public String getDataBaseName() {
        return DataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        DataBaseName = dataBaseName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getPartyCd() {
        return PartyCd;
    }

    public void setPartyCd(String partyCd) {
        PartyCd = partyCd;
    }

    public String getPayFlag() {
        return PayFlag;
    }

    public void setPayFlag(String payFlag) {
        PayFlag = payFlag;
    }

    public String getSmsMobNo() {
        return SmsMobNo;
    }

    public void setSmsMobNo(String smsMobNo) {
        SmsMobNo = smsMobNo;
    }

    public int getInterestAmt() {
        return InterestAmt;
    }

    public void setInterestAmt(int interestAmt) {
        InterestAmt = interestAmt;
    }

    @Override
    public String toString() {
        return "PosInstRcv{" +
                "InstRcvID=" + InstRcvID +
                ", IMEI='" + IMEI + '\'' +
                ", CaseCode='" + CaseCode + '\'' +
                ", RcptNo=" + RcptNo +
                ", InstRcvAmt=" + InstRcvAmt +
                ", InstRcvDateTimeUTC=" + InstRcvDateTimeUTC +
                ", Flag='" + Flag + '\'' +
                ", CreationDate=" + CreationDate +
                ", BatchNo=" + BatchNo +
                ", BatchDate=" + BatchDate +
                ", FoCode='" + FoCode + '\'' +
                ", DataBaseName='" + DataBaseName + '\'' +
                ", Creator='" + Creator + '\'' +
                ", CustName='" + CustName + '\'' +
                ", PartyCd='" + PartyCd + '\'' +
                ", PayFlag='" + PayFlag + '\'' +
                ", SmsMobNo='" + SmsMobNo + '\'' +
                ", InterestAmt='" + InterestAmt + '\'' +
                '}';
    }
}


