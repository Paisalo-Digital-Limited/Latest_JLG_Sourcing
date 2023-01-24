package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.Expose;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyLoan;

public class BorrowerFamilyLoanDTO {
    @Expose
    private long Code;
    @Expose
    private String Tag;
    @Expose
    private String Creator;
    @Expose
    private String LoneeName;
    @Expose
    private String LenderName;
    @Expose
    private String LenderType;
    @Expose
    private String isMFI;
    @Expose
    private String LoanReason;

    @Expose
    private int LoanAmount;
    @Expose
    private int LoanEMIAmount;
    @Expose
    private int LoanBalanceAmount;

    @Expose
    private long AutoID;

    public BorrowerFamilyLoan getFamilyLoan() {
        BorrowerFamilyLoan borrowerFamilyLoan = new BorrowerFamilyLoan();
        borrowerFamilyLoan.setAutoID(this.AutoID);
        borrowerFamilyLoan.setCode(this.Code);
        borrowerFamilyLoan.setCreator(this.Creator);
        borrowerFamilyLoan.setIsMFI(this.isMFI);
        borrowerFamilyLoan.setLenderName(this.LenderName);
        borrowerFamilyLoan.setLenderType(this.LenderType);
        borrowerFamilyLoan.setLoanAmount(this.LoanAmount);
        borrowerFamilyLoan.setLoanBalanceAmount(this.LoanBalanceAmount);
        borrowerFamilyLoan.setLoanEMIAmount(this.LoanEMIAmount);
        borrowerFamilyLoan.setLoanReason(this.LoanReason);
        borrowerFamilyLoan.setLoneeName(this.LoneeName);
        borrowerFamilyLoan.setTag(this.Tag);
        return borrowerFamilyLoan;
    }

    @Override
    public String toString() {
        return "BorrowerFamilyLoanDTO{" +
                "Code=" + Code +
                ", Tag='" + Tag + '\'' +
                ", Creator='" + Creator + '\'' +
                ", LoneeName='" + LoneeName + '\'' +
                ", LenderName='" + LenderName + '\'' +
                ", LenderType='" + LenderType + '\'' +
                ", isMFI='" + isMFI + '\'' +
                ", LoanReason='" + LoanReason + '\'' +
                ", LoanAmount=" + LoanAmount +
                ", LoanEMIAmount=" + LoanEMIAmount +
                ", LoanBalanceAmount=" + LoanBalanceAmount +
                ", AutoID=" + AutoID +
                '}';
    }
}
