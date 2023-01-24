package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtra;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtraBank;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyExpenses;

import java.util.Date;
import java.util.List;

public class BorrowerDTO {

    @Expose
    public long Code;

    @Expose
    public String Tag;

    @Expose
    public String FI_NO;

    @Expose
    public String DelCode;

    @Expose
    public String A_Code;

    @Expose
    public String Case_City;

    @Expose
    public Date DT;

    @Expose
    public String Approved;

    @Expose
    public String Saved;

    @Expose
    public int Loan_Amt;

    @Expose
    public String Loan_Duration;

    @Expose
    public String Loan_Reason;

    @Expose
    public String Ini;

    @Expose
    public String Fname;

    @Expose
    public String Mname = "";

    @Expose
    public String Lname = "";

    @Expose
    public String F_Ini;

    @Expose
    public String F_Fname;

    @Expose
    public String F_Mname = "";

    @Expose
    public String F_Lname = "";

    @Expose
    public String Business_Detail = "";

    @Expose
    public int Income;

    @Expose
    public Date DOB;

    @Expose
    public String Cast = "";

    @Expose
    public String P_Add1 = "";

    @Expose
    public String P_Add2 = "";

    @Expose
    public String P_Add3 = "";

    @Expose
    public String P_City = "";

    @Expose
    public String P_Ph1;

    @Expose
    public String P_Ph2;

    @Expose
    public String P_Ph3;

    @Expose
    public String T_Add1;

    @Expose
    public String T_Add2;

    @Expose
    public String T_Add3;

    @Expose
    public String T_City;

    @Expose
    public String T_Ph1;

    @Expose
    public String T_Ph2;

    @Expose
    public String T_Ph3;

    @Expose
    public String O_Add1;

    @Expose
    public String O_Add2;

    @Expose
    public String O_Add3;

    @Expose
    public String O_City;

    @Expose
    public String O_Ph1;

    @Expose
    public String O_Ph2;

    @Expose
    public String O_Ph3;

    @Expose
    public String Identity;

    @Expose
    public String Identity_No;

    @Expose
    public String Bank_Ac_No;

    @Expose
    public String Bank_Address;

    @Expose
    public String Res_type;

    @Expose
    public String House_Owner;

    @Expose
    public int Rent_of_House;

    @Expose
    public String Property_Det;

    @Expose
    public int FAmily_member;

    @Expose
    public String Enc_Property;

    @Expose
    public String Main_Title_Deed;

    @Expose
    public String Lit_Det;

    @Expose
    public String Sec_Details;

    @Expose
    public String FI_REPORT;

    @Expose
    public String Flat_Typ;

    @Expose
    public String Near_House;

    @Expose
    public String House_Loan;

    @Expose
    public int Loan_EMi;

    @Expose
    public String House_Identity;

    @Expose
    public String Live_IN_City;

    @Expose
    public String Live_In_Present_Place;

    @Expose
    public int Area_Of_House;

    @Expose
    public String House_Locality;

    @Expose
    public String House_Interior;

    @Expose
    public String Two_Wh_From;

    @Expose
    public String Two_Wh_Modal;

    @Expose
    public String Two_Wh_Make;

    @Expose
    public String Two_Wh_Regdno;

    @Expose
    public String Four_Wh_From;

    @Expose
    public String four_Wh_Modal;

    @Expose
    public String four_Wh_Make;

    @Expose
    public String four_Wh_Regdno;

    @Expose
    public String Vehicle_USe_By;

    @Expose
    public String Oth_Prop_Det;

    @Expose
    public String person_Contact_place;

    @Expose
    public String Oth_Contact_Place;

    @Expose
    public String Verified_phone;

    @Expose
    public String SEL;

    @Expose
    public String CASE_NO;

    @Expose
    public String Creator;

    @Expose
    public String UserID;

    @Expose
    public String Auth_UserID;

    @Expose
    public Date Auth_Date;

    @Expose
    public Date Creation_Date;

    @Expose
    public String Mod_Type;

    @Expose
    public String Last_Mod_UserID;

    @Expose
    public Date Last_Mod_Date;

    @Expose
    public String GroupCode;

    @Expose
    public String CityCode;

    @Expose
    public String Gender;

    @Expose
    public String Religion;

    @Expose
    public int LandHolding;

    @Expose
    public String ExServiceMan;

    @Expose
    public int Age;

    @Expose
    public int P_Pin;

    @Expose
    public int T_Pin;

    @Expose
    public int O_Pin;

    @Expose
    public String P_State;

    @Expose
    public String O_State;

    @Expose
    public String T_State;

    @Expose
    public String PanNO;

    @Expose
    public String AadharID;

    @Expose
    public String VoterID;

    @Expose
    public String DrivingLic;

    @Expose
    public String isCustvisited;

    @Expose
    public String SmCode;

    @Expose
    public String OldCaseCode;

    @Expose
    public String isAadharVerified;

    @Expose
    public String isMarried;

    @Expose
    public String BankName;

    @Expose
    public String BankAcType;

    @Expose
    public Date BankAcOpenDt;

    @Expose
    public float Latitude;

    @Expose
    public float Longitude;

    @Expose
    public Date GeoDateTime;

    @Expose
    public String RelationWBorrower;

    @Expose
    public String AMApproval;

    @Expose
    public String AMRemarks;

    @Expose
    public String KYCUUID;

    @Expose
    public BorrowerExtraDTO fiExtra;

    @Expose
    @SerializedName("FiExtraBankBo")
    public BorrowerExtraBankDTO fiExtraBank;

    @Expose
    public BorrowerFamilyExpenses fiFamExpenses;

    @Expose
    public List<BorrowerFamilyLoanDTO> fiFamLoans;

    @Expose
    public List<BorrowerFamilyMemberDTO> fiFamMems;


    public BorrowerDTO() {
    }

    public BorrowerDTO(Borrower borrower) {
        this.Auth_Date = borrower.Auth_Date;
        this.BankAcOpenDt = borrower.BankAcOpenDt;
        this.Creation_Date = borrower.Creation_Date;
        this.DOB = borrower.DOB;
        this.DT = borrower.DT;
        this.GeoDateTime = borrower.GeoDateTime;
        this.Last_Mod_Date = borrower.Last_Mod_Date;
        this.Latitude = borrower.Latitude;
        this.Longitude = borrower.Longitude;
        this.Age = borrower.Age;
        this.Area_Of_House = borrower.Area_Of_House;
        this.FAmily_member = borrower.FAmily_member;
        this.Income = borrower.Income;
        this.LandHolding = borrower.LandHolding;
        this.Loan_Amt = borrower.Loan_Amt;
        this.Loan_EMi = borrower.Loan_EMi;
        this.O_Pin = borrower.o_pin;
        this.P_Pin = borrower.p_pin;
        this.Rent_of_House = borrower.Rent_of_House;
        this.T_Pin = borrower.t_pin;
        this.Code = borrower.Code;
        this.A_Code = borrower.A_Code;
        this.AadharID = borrower.aadharid;
        this.AMApproval = borrower.AMApproval;
        this.AMRemarks = borrower.AMRemarks;
        this.Approved = borrower.Approved;
        this.Auth_UserID = borrower.Auth_UserID;
        this.Bank_Ac_No = borrower.bank_ac_no;
        this.Bank_Address = borrower.Bank_Address;
        this.BankAcType = borrower.BankAcType;
        this.BankName = borrower.BankName;
        this.Business_Detail = borrower.Business_Detail;
        this.Case_City = borrower.Case_City;
        this.CASE_NO = borrower.CASE_NO;
        this.Cast = borrower.Cast;
        this.CityCode = borrower.CityCode;
        this.Creator = borrower.Creator;
        this.DelCode = borrower.DelCode;
        this.DrivingLic = borrower.drivinglic;
        this.Enc_Property = borrower.Enc_Property;
        this.ExServiceMan = borrower.ExServiceMan;
        this.F_Fname = borrower.F_fname;
        this.F_Ini = borrower.F_ini;
        this.F_Lname = borrower.F_lname;
        this.F_Mname = borrower.F_mname;
        this.FI_NO = borrower.FI_NO;
        this.FI_REPORT = borrower.FI_REPORT;
        this.Flat_Typ = borrower.Flat_Typ;
        this.Fname = borrower.Fname;
        this.Four_Wh_From = borrower.Four_Wh_From;
        this.four_Wh_Make = borrower.four_Wh_Make;
        this.four_Wh_Modal = borrower.four_Wh_Modal;
        this.four_Wh_Regdno = borrower.four_Wh_Regdno;
        this.Gender = borrower.Gender;
        this.GroupCode = borrower.GroupCode;
        this.House_Identity = borrower.House_Identity;
        this.House_Interior = borrower.House_Interior;
        this.House_Loan = borrower.House_Loan;
        this.House_Locality = borrower.House_Locality;
        this.House_Owner = borrower.House_Owner;
        this.Identity = borrower.Identity;
        this.Identity_No = borrower.Identity_no;
        this.Ini = borrower.ini;
        this.isAadharVerified = borrower.isAadharVerified;
        this.isCustvisited = borrower.isCustvisited;
        this.isMarried = borrower.isMarried;
        this.KYCUUID = borrower.KYCUUID;
        this.Last_Mod_UserID = borrower.Last_Mod_UserID;
        this.Lit_Det = borrower.Lit_Det;
        this.Live_IN_City = borrower.Live_IN_City;
        this.Live_In_Present_Place = borrower.Live_In_Present_Place;
        this.Lname = borrower.Lname;
        this.Loan_Duration = borrower.Loan_Duration;
        this.Loan_Reason = borrower.Loan_Reason;
        this.Main_Title_Deed = borrower.Main_Title_Deed;
        this.Mname = borrower.Mname;
        this.Mod_Type = borrower.Mod_Type;
        this.Near_House = borrower.Near_House;
        this.O_Add1 = borrower.O_Add1;
        this.O_Add2 = borrower.O_Add2;
        this.O_Add3 = borrower.O_Add3;
        this.O_City = borrower.O_City;
        this.O_Ph1 = borrower.O_Ph1;
        this.O_Ph2 = borrower.O_Ph2;
        this.O_Ph3 = borrower.O_Ph3;
        this.O_State = borrower.o_state;
        this.OldCaseCode = borrower.OldCaseCode;
        this.Oth_Contact_Place = borrower.Oth_Contact_Place;
        this.Oth_Prop_Det = borrower.Oth_Prop_Det;
        this.P_Add1 = borrower.P_Add1;
        this.P_Add2 = borrower.P_add2;
        this.P_Add3 = borrower.P_add3;
        this.P_City = borrower.P_city;
        this.P_Ph1 = borrower.P_ph1;
        this.P_Ph2 = borrower.P_ph2;
        this.P_Ph3 = borrower.P_ph3;

        this.P_State = borrower.p_state;
        this.PanNO = borrower.PanNO;
        this.person_Contact_place = borrower.person_Contact_place;
        this.Property_Det = borrower.Property_Det;
        this.RelationWBorrower = borrower.RelationWBorrower;
        this.Religion = borrower.Religion;
        this.Res_type = borrower.Res_type;
        this.Saved = borrower.Saved;
        this.Sec_Details = borrower.Sec_Details;
        this.SEL = borrower.SEL;
        this.SmCode = borrower.SmCode;
        this.T_Add1 = borrower.T_Add1;
        this.T_Add2 = borrower.T_add2;
        this.T_Add3 = borrower.T_add3;
        this.T_City = borrower.T_city;
        this.T_Ph1 = borrower.T_ph1;
        this.T_Ph2 = borrower.T_ph2;
        this.T_Ph3 = borrower.T_ph3;
        this.T_State = borrower.t_state;
        this.Tag = borrower.Tag;
        this.Two_Wh_From = borrower.Two_Wh_From;
        this.Two_Wh_Make = borrower.Two_Wh_Make;
        this.Two_Wh_Modal = borrower.Two_Wh_Modal;
        this.Two_Wh_Regdno = borrower.Two_Wh_Regdno;
        this.UserID = borrower.UserID;
        this.Vehicle_USe_By = borrower.Vehicle_USe_By;
        this.Verified_phone = borrower.Verified_phone;
        this.VoterID = borrower.voterid;

        BorrowerExtraBank borrowerExtraBank = borrower.getBorrowerExtraBank();
        if (borrowerExtraBank == null) {
            this.fiExtraBank = null;
        } else {
            this.fiExtraBank = borrowerExtraBank.getExtraBankDTO();
            if (this.fiExtraBank.getBankCif() == null || this.fiExtraBank.getBankCif().equals(""))
                this.fiExtraBank.setBankCif("Pend");
        }

        BorrowerExtra borrowerExtra = borrower.getBorrowerExtra();
        if (borrowerExtra == null) {
            this.fiExtra = null;
        } else {
            this.fiExtra = borrowerExtra.getExtraDTO();
        }

        BorrowerFamilyExpenses borrowerFamilyExpenses = borrower.getFiFamilyExpenses();
        if (borrowerFamilyExpenses == null) {
            this.fiFamExpenses = null;
        } else {
            this.fiFamExpenses = borrowerFamilyExpenses;
        }

        /*List<BorrowerFamilyLoan> familyLoans=borrower.getFiFamilyLoans();
        if(familyLoans==null || familyLoans.size()==0) {
            this.fiFamLoans = null;
        }else {
            this.fiFamLoans=familyLoans;
        }*/
        this.fiFamLoans = null;
        this.fiFamMems = null;
    }

    @Override
    public String toString() {
        return "BorrowerDTO{" +
                "Code=" + Code +
                ", Tag='" + Tag + '\'' +
                ", FI_NO='" + FI_NO + '\'' +
                ", DelCode='" + DelCode + '\'' +
                ", A_Code='" + A_Code + '\'' +
                ", Case_City='" + Case_City + '\'' +
                ", DT=" + DT +
                ", Approved='" + Approved + '\'' +
                ", Saved='" + Saved + '\'' +
                ", Loan_Amt=" + Loan_Amt +
                ", Loan_Duration='" + Loan_Duration + '\'' +
                ", Loan_Reason='" + Loan_Reason + '\'' +
                ", Ini='" + Ini + '\'' +
                ", Fname='" + Fname + '\'' +
                ", Mname='" + Mname + '\'' +
                ", Lname='" + Lname + '\'' +
                ", F_Ini='" + F_Ini + '\'' +
                ", F_Fname='" + F_Fname + '\'' +
                ", F_Mname='" + F_Mname + '\'' +
                ", F_Lname='" + F_Lname + '\'' +
                ", Business_Detail='" + Business_Detail + '\'' +
                ", Income=" + Income +
                ", DOB=" + DOB +
                ", Cast='" + Cast + '\'' +
                ", P_Add1='" + P_Add1 + '\'' +
                ", P_Add2='" + P_Add2 + '\'' +
                ", P_Add3='" + P_Add3 + '\'' +
                ", P_City='" + P_City + '\'' +
                ", P_Ph1='" + P_Ph1 + '\'' +
                ", P_Ph2='" + P_Ph2 + '\'' +
                ", P_ph3='" + P_Ph3 + '\'' +
                ", T_Add1='" + T_Add1 + '\'' +
                ", T_Add2='" + T_Add2 + '\'' +
                ", T_Add3='" + T_Add3 + '\'' +
                ", T_City='" + T_City + '\'' +
                ", T_Ph1='" + T_Ph1 + '\'' +
                ", T_Ph2='" + T_Ph2 + '\'' +
                ", T_Ph3='" + T_Ph3 + '\'' +
                ", O_Add1='" + O_Add1 + '\'' +
                ", O_Add2='" + O_Add2 + '\'' +
                ", O_Add3='" + O_Add3 + '\'' +
                ", O_City='" + O_City + '\'' +
                ", O_Ph1='" + O_Ph1 + '\'' +
                ", O_Ph2='" + O_Ph2 + '\'' +
                ", O_Ph3='" + O_Ph3 + '\'' +
                ", Identity='" + Identity + '\'' +
                ", Identity_No='" + Identity_No + '\'' +
                ", Bank_Ac_No='" + Bank_Ac_No + '\'' +
                ", Bank_Address='" + Bank_Address + '\'' +
                ", Res_type='" + Res_type + '\'' +
                ", House_Owner='" + House_Owner + '\'' +
                ", Rent_of_House=" + Rent_of_House +
                ", Property_Det='" + Property_Det + '\'' +
                ", FAmily_member=" + FAmily_member +
                ", Enc_Property='" + Enc_Property + '\'' +
                ", Main_Title_Deed='" + Main_Title_Deed + '\'' +
                ", Lit_Det='" + Lit_Det + '\'' +
                ", Sec_Details='" + Sec_Details + '\'' +
                ", FI_REPORT='" + FI_REPORT + '\'' +
                ", Flat_Typ='" + Flat_Typ + '\'' +
                ", Near_House='" + Near_House + '\'' +
                ", House_Loan='" + House_Loan + '\'' +
                ", Loan_EMi=" + Loan_EMi +
                ", House_Identity='" + House_Identity + '\'' +
                ", Live_IN_City='" + Live_IN_City + '\'' +
                ", Live_In_Present_Place='" + Live_In_Present_Place + '\'' +
                ", Area_Of_House=" + Area_Of_House +
                ", House_Locality='" + House_Locality + '\'' +
                ", House_Interior='" + House_Interior + '\'' +
                ", Two_Wh_From='" + Two_Wh_From + '\'' +
                ", Two_Wh_Modal='" + Two_Wh_Modal + '\'' +
                ", Two_Wh_Make='" + Two_Wh_Make + '\'' +
                ", Two_Wh_Regdno='" + Two_Wh_Regdno + '\'' +
                ", Four_Wh_From='" + Four_Wh_From + '\'' +
                ", four_Wh_Modal='" + four_Wh_Modal + '\'' +
                ", four_Wh_Make='" + four_Wh_Make + '\'' +
                ", four_Wh_Regdno='" + four_Wh_Regdno + '\'' +
                ", Vehicle_USe_By='" + Vehicle_USe_By + '\'' +
                ", Oth_Prop_Det='" + Oth_Prop_Det + '\'' +
                ", person_Contact_place='" + person_Contact_place + '\'' +
                ", Oth_Contact_Place='" + Oth_Contact_Place + '\'' +
                ", Verified_phone='" + Verified_phone + '\'' +
                ", SEL='" + SEL + '\'' +
                ", CASE_NO='" + CASE_NO + '\'' +
                ", Creator='" + Creator + '\'' +
                ", UserID='" + UserID + '\'' +
                ", Auth_UserID='" + Auth_UserID + '\'' +
                ", Auth_Date=" + Auth_Date +
                ", Creation_Date=" + Creation_Date +
                ", Mod_Type='" + Mod_Type + '\'' +
                ", Last_Mod_UserID='" + Last_Mod_UserID + '\'' +
                ", Last_Mod_Date=" + Last_Mod_Date +
                ", GroupCode='" + GroupCode + '\'' +
                ", CityCode='" + CityCode + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Religion='" + Religion + '\'' +
                ", LandHolding=" + LandHolding +
                ", ExServiceMan='" + ExServiceMan + '\'' +
                ", Age=" + Age +
                ", P_Pin=" + P_Pin +
                ", T_Pin=" + T_Pin +
                ", O_Pin=" + O_Pin +
                ", P_State='" + P_State + '\'' +
                ", O_State='" + O_State + '\'' +
                ", T_State='" + T_State + '\'' +
                ", PanNO='" + PanNO + '\'' +
                ", AadharID='" + AadharID + '\'' +
                ", VoterID='" + VoterID + '\'' +
                ", DrivingLic='" + DrivingLic + '\'' +
                ", isCustvisited='" + isCustvisited + '\'' +
                ", SmCode='" + SmCode + '\'' +
                ", OldCaseCode='" + OldCaseCode + '\'' +
                ", isAadharVerified='" + isAadharVerified + '\'' +
                ", isMarried='" + isMarried + '\'' +
                ", BankName='" + BankName + '\'' +
                ", BankAcType='" + BankAcType + '\'' +
                ", BankAcOpenDt=" + BankAcOpenDt +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", GeoDateTime=" + GeoDateTime +
                ", RelationWBorrower='" + RelationWBorrower + '\'' +
                ", AMApproval='" + AMApproval + '\'' +
                ", AMRemarks='" + AMRemarks + '\'' +
                ", KYCUUID='" + KYCUUID + '\'' +
                ", fiExtra=" + fiExtra +
                ", fiFamExpenses=" + fiFamExpenses +
                ", fiFamLoans=" + fiFamLoans +
                '}';
    }
}
