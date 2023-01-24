package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import com.softeksol.paisalo.jlgsourcing.DbIGL;

import java.io.Serializable;


/**
 * Created by sachindra on 2015-10-01.
 */
@ModelContainer
@Table(database = DbIGL.class)
public class BorrowerFamilyLoan extends BaseModel implements Serializable {

    @ForeignKey
    ForeignKeyContainer<Borrower> borrowerForeignKeyContainer;
    @Expose
    @Column
    private long Code;
    @Expose
    @Column
    private String Tag;
    @Expose
    @Column
    private String Creator;
    @Expose
    @Column
    private String LoneeName;
    @Expose
    @Column
    private String LenderName;
    @Expose
    @Column
    private String LenderType;
    @Expose
    @Column
    private String isMFI;
    @Expose
    @Column
    private String LoanReason;

    @Expose
    @Column
    private int LoanAmount;
    @Expose
    @Column
    private int LoanEMIAmount;
    @Expose
    @Column
    private int LoanBalanceAmount;

    @Column
    @PrimaryKey(autoincrement = true)
    private long id;

    @Column
    private long FiID;

    @Expose
    @Column
    private long AutoID;

    public BorrowerFamilyLoan() {
    }

    public BorrowerFamilyLoan(long code, String tag, String creator, String loneeName, String lenderName, String lenderType, String isMFI, String loanReason, int loanAmount, int loanEMIAmount, int loanBalanceAmount, long id, long fiID, long autoId) {
        Code = code;
        Tag = tag;
        Creator = creator;
        LoneeName = loneeName;
        LenderName = lenderName;
        LenderType = lenderType;
        this.isMFI = isMFI;
        LoanReason = loanReason;
        LoanAmount = loanAmount;
        LoanEMIAmount = loanEMIAmount;
        LoanBalanceAmount = loanBalanceAmount;
        this.id = id;
        FiID = fiID;
        this.AutoID = autoId;
    }

    public void associateBorrower(Borrower borrower) {
        this.FiID = borrower.FiID;
        this.Creator = borrower.Creator;
        this.borrowerForeignKeyContainer = FlowManager.getContainerAdapter(Borrower.class)
                .toForeignKeyContainer(borrower); // convenience conversion
    }

    public long getCode() {
        return Code;
    }

    public void setCode(long code) {
        Code = code;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getLoneeName() {
        return LoneeName;
    }

    public void setLoneeName(String loneeName) {
        LoneeName = loneeName;
    }

    public String getLenderName() {
        return LenderName;
    }

    public void setLenderName(String lenderName) {
        LenderName = lenderName;
    }

    public String getLenderType() {
        return LenderType;
    }

    public void setLenderType(String lenderType) {
        LenderType = lenderType;
    }

    public String getIsMFI() {
        return isMFI;
    }

    public void setIsMFI(String isMFI) {
        this.isMFI = isMFI;
    }

    public String getLoanReason() {
        return LoanReason;
    }

    public void setLoanReason(String loanReason) {
        LoanReason = loanReason;
    }

    public int getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        LoanAmount = loanAmount;
    }

    public int getLoanEMIAmount() {
        return LoanEMIAmount;
    }

    public void setLoanEMIAmount(int loanEMIAmount) {
        LoanEMIAmount = loanEMIAmount;
    }

    public int getLoanBalanceAmount() {
        return LoanBalanceAmount;
    }

    public void setLoanBalanceAmount(int loanBalanceAmount) {
        LoanBalanceAmount = loanBalanceAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFiID() {
        return FiID;
    }

    public void setFiID(long fiID) {
        FiID = fiID;
    }

    public long getAutoID() {
        return AutoID;
    }

    public void setAutoID(long autoID) {
        AutoID = autoID;
    }

    @Override
    public String toString() {
        return "BorrowerFamilyLoan{" +
                "Code=" + Code +
                ", Tag='" + Tag + '\'' +
                ", Creator='" + Creator + '\'' +
                ", LoneeName='" + LoneeName + '\'' +
                ", LenderName='" + LenderName + '\'' +
                ", LenderType='" + LenderType + '\'' +
                ", isMFI=" + isMFI +
                ", LoanReason='" + LoanReason + '\'' +
                ", LoanAmount=" + LoanAmount +
                ", LoanEMIAmount=" + LoanEMIAmount +
                ", LoanBalanceAmount=" + LoanBalanceAmount +
                ", id=" + id +
                ", FiID=" + FiID +
                '}';
    }
}
