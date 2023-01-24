package com.softeksol.paisalo.jlgsourcing.entities;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DueData implements Serializable {
    private String db;
    private String creator;
    private String FoCode;
    private String CaseCode;
    private String PartyCd;
    private String CustName;
    private String FHName;
    private String Address;
    private String Mobile;
    private Date FirstInstDate;
    private int NofInstDue;
    private int InstsAmtDue;
    private Date InstDueAsOn;
    private int ToBeDueAmt;
    private Date ToBeDueDate;
    private Date DataAsOn;
    private List<Installment> InstData;
    private String dbName;
    private String aadhar;
    private String CityCode;
    private int FutureDue = 0;
    private int InterestAmt = 0;

    private boolean isEnabled = true;

    public static Comparator<DueData> DueDataNachName = new Comparator<DueData>() {
        public int compare(DueData dueData1, DueData dueData2) {

            int compareNach = dueData1.isNachReg.compareTo(dueData2.isNachReg);
            int compareName = dueData1.CustName.compareTo(dueData2.CustName);

            return ((compareNach == 0) ? compareName : compareNach);

            //return StudentName1.compareTo(StudentName2); //ascending order
            //return StudentName2.compareTo(StudentName1); //descending order

        }

    };

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getFoCode() {
        return FoCode;
    }

    public void setFoCode(String foCode) {
        FoCode = foCode;
    }

    public String getCaseCode() {
        return CaseCode;
    }

    public void setCaseCode(String caseCode) {
        CaseCode = caseCode;
    }

    public String getPartyCd() {
        return PartyCd;
    }

    public void setPartyCd(String partyCd) {
        PartyCd = partyCd;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getFHName() {
        return FHName;
    }

    public void setFHName(String FHName) {
        this.FHName = FHName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public Date getFirstInstDate() {
        return FirstInstDate;
    }

    public void setFirstInstDate(Date firstInstDate) {
        FirstInstDate = firstInstDate;
    }

    public int getNofInstDue() {
        return NofInstDue;
    }

    public void setNofInstDue(int nofInstDue) {
        NofInstDue = nofInstDue;
    }

    public int getInstsAmtDue() {
        return InstsAmtDue;
    }

    public void setInstsAmtDue(int instsAmtDue) {
        InstsAmtDue = instsAmtDue;
    }

    public Date getInstDueAsOn() {
        return InstDueAsOn;
    }

    public void setInstDueAsOn(Date instDueAsOn) {
        InstDueAsOn = instDueAsOn;
    }

    public int getToBeDueAmt() {
        return ToBeDueAmt;
    }

    public void setToBeDueAmt(int toBeDueAmt) {
        ToBeDueAmt = toBeDueAmt;
    }

    public int getFutureDue() {
        return FutureDue;
    }

    public void setFutureDue(int futureDue) {
        FutureDue = futureDue;
    }

    public Date getToBeDueDate() {
        return ToBeDueDate;
    }

    public void setToBeDueDate(Date toBeDueDate) {
        ToBeDueDate = toBeDueDate;
    }

    public Date getDataAsOn() {
        return DataAsOn;
    }

    public void setDataAsOn(Date dataAsOn) {
        DataAsOn = dataAsOn;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public boolean hasAadhar() {
        return aadhar != null && aadhar.length() == 12;
    }

    private String isNachReg;

    public String getIsNachReg() {
        return isNachReg;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public static int getInstallmentSum(List<Installment> installments, Boolean selectedOnly) {
        int tot = 0;
        for (Installment installment : installments) {
            if (selectedOnly) {
                if (installment.isSelected()) {
                    tot += installment.getAmount();
                }
            } else {
                tot += installment.getAmount();
            }
        }
        return tot;
    }

    public void setInstData(List<Installment> instData) {
        InstData = instData;
    }

    public int getInterestAmt() {
        return InterestAmt;
    }

    public void setInterestAmt(int interestAmt) {
        InterestAmt = interestAmt;
    }

    @Override
    public String toString() {
        return "DueData{" +
                "db='" + db + '\'' +
                ", creator='" + creator + '\'' +
                ", FoCode='" + FoCode + '\'' +
                ", CityCode='" + CityCode + '\'' +
                ", CaseCode='" + CaseCode + '\'' +
                ", PartyCd='" + PartyCd + '\'' +
                ", CustName='" + CustName + '\'' +
                ", FHName='" + FHName + '\'' +
                ", Address='" + Address + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", FirstInstDate=" + FirstInstDate +
                ", NofInstDue=" + NofInstDue +
                ", InstsAmtDue=" + InstsAmtDue +
                ", InstDueAsOn=" + InstDueAsOn +
                ", ToBeDueAmt=" + ToBeDueAmt +
                ", ToBeDueDate=" + ToBeDueDate +
                ", DataAsOn=" + DataAsOn +
                ", InstData=" + InstData +
                ", dbName='" + dbName + '\'' +
                ", aadhar='" + aadhar + '\'' +
                ", isNachReg='" + isNachReg + '\'' +
                ", InterestAmt=" + InterestAmt +
                '}';
    }

    public static int getSelectedCount(List<Installment> installments) {
        int tot = 0;
        for (Installment installment : installments) {
            if (installment.isSelected()) {
                tot++;
            }
        }
        return tot;
    }

    public List<Installment> getInstData() {
        List<Installment> installments = new ArrayList<>();
        boolean isFirst = true;
        //installments.addAll(InstData);
        int sunBal = getSundryBalance();
        for (Installment inst : InstData) {
            Installment cloneInst = new Installment(inst);
            if (isFirst) {
                cloneInst.setAmount(cloneInst.getAmount() - sunBal);
                isFirst = false;
            }
            installments.add(cloneInst);

        }
        if (ToBeDueAmt > 0) {
            Installment installment = new Installment();
            installment.setAmount(ToBeDueAmt);
            installment.setDueDate((new SimpleDateFormat("dd-MM-yyyy")).format(ToBeDueDate));
            installment.setSelected(false);
            installments.add(installment);
        }

        return installments;
    }

    public int getInstallmentSum(Boolean selectedOnly) {
        return getInstallmentSum(this.InstData, selectedOnly);
    }

    public int getSelectedCount() {
        return getSelectedCount(this.InstData);
    }

    public void setIsNachReg(String isNachReg) {
        this.isNachReg = isNachReg;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public void clearSection(List<Installment> installments) {
        int tot = 0;
        for (Installment installment : installments) {
            installment.setSelected(false);
        }
    }

    public int getSundryBalance() {
        return getInstallmentSum(false) - getInstsAmtDue();
    }

    public int getMaxDueAmount() {
        return InstsAmtDue + ToBeDueAmt + FutureDue + InterestAmt;
    }
}
