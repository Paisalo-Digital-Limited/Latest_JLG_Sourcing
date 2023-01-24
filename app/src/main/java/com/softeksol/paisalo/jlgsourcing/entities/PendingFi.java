package com.softeksol.paisalo.jlgsourcing.entities;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by sachindra on 2015-10-01.
 */

public class PendingFi implements Serializable {

    @Expose
    public long Code;

    @Expose
    public String Creator;

    @Expose
    public int SanctionedAmt;

    @Expose
    public String Remarks;

    @Expose
    public Date Dt_Fin;

    @Expose
    public Date Dt_Start;

    @Expose
    public String SchCode;

    @Expose
    public String Fname;

    @Expose
    public String Mname = "";

    @Expose
    public String Lname = "";

    @Expose
    public String F_fname;

    @Expose
    public String F_mname = "";

    @Expose
    public String F_lname = "";

    @Expose
    public String Description;

    @Expose
    public int Period;

    @Expose
    public double Rate;

    @Expose
    public String aadharid;

    @Expose
    public String Addr;

    @Expose
    public String P_ph3;

    @Expose
    public String GroupCode;

    @Expose
    public String CityCode;

    @Expose
    public String BorrLoanAppSignStatus;

    @Expose
    public String Approved;

    @Expose
    public String SEL;

    public long FiID;


    public PendingFi() {
    }


    public String getBorrowerName() {
        return TextUtils.join(" ", new String[]{Utils.NullIf(Fname, ""), Utils.NullIf(Mname, ""), Utils.NullIf(Lname, "")}).replace("  ", " ").trim();
    }

    public String getGurName() {
        return TextUtils.join(" ", new String[]{Utils.NullIf(F_fname, ""), Utils.NullIf(F_mname, ""), Utils.NullIf(F_lname, "")}).replace("  ", " ").trim();
    }

    @Override
    public String toString() {
        return "PendingFi{" +
                "Code=" + Code +
                ", Creator='" + Creator + '\'' +
                ", SanctionedAmt=" + SanctionedAmt +
                ", Remarks='" + Remarks + '\'' +
                ", Dt_Fin=" + Dt_Fin +
                ", Dt_Start=" + Dt_Start +
                ", SchCode='" + SchCode + '\'' +
                ", Fname='" + Fname + '\'' +
                ", Mname='" + Mname + '\'' +
                ", Lname='" + Lname + '\'' +
                ", F_Fname='" + F_fname + '\'' +
                ", F_Mname='" + F_mname + '\'' +
                ", F_Lname='" + F_lname + '\'' +
                ", Description='" + Description + '\'' +
                ", Period=" + Period +
                ", Rate=" + Rate +
                ", AadharID='" + aadharid + '\'' +
                ", Addr='" + Addr + '\'' +
                ", P_ph3='" + P_ph3 + '\'' +
                ", GroupCode='" + GroupCode + '\'' +
                ", CityCode='" + CityCode + '\'' +
                ", BorrLoanAppSignStatus='" + BorrLoanAppSignStatus + '\'' +
                ", Approved='" + Approved + '\'' +
                ", Last_Mod_UserID='" + SEL + '\'' +
                ", FiID=" + FiID +
                '}';
    }
}
