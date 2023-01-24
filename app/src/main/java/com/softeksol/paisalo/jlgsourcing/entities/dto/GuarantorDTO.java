package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.Expose;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;

import java.util.Date;

public class GuarantorDTO {

    @Expose
    private String Code;

    @Expose
    private long fi_Code;

    @Expose
    private int GrNo = 0;

    @Expose
    private String Initials;

    @Expose
    private String Name;

    @Expose
    private String GurInitials;

    @Expose
    private String GurName;

    @Expose
    private int CorrAddr;

    @Expose
    private String FirmName;

    @Expose
    private String OffAdd1;

    @Expose
    private String OffAdd2;

    @Expose
    private String OffAdd3;

    @Expose
    private String OffCity;

    @Expose
    private String OffPh1;

    @Expose
    private String OffPh2;

    @Expose
    private String OffPh3;

    @Expose
    private String OffFax;

    @Expose
    private String OffMob1;

    @Expose
    private String OffMob2;

    @Expose
    private String ResAdd1;

    @Expose
    private String ResAdd2;

    @Expose
    private String ResAdd3;

    @Expose
    private String ResCity;

    @Expose
    private String ResPh1;

    @Expose
    private String ResPh2;

    @Expose
    private String ResPh3;

    @Expose
    private String ResFax;

    @Expose
    private String ResMob1;

    @Expose
    private String ResMob2;

    @Expose
    private String PerAdd1;

    @Expose
    private String PerAdd2;

    @Expose
    private String PerAdd3;

    @Expose
    private String PerCity;

    @Expose
    private String PerPh1;

    @Expose
    private String PerPh2;

    @Expose
    private String PerPh3;

    @Expose
    private String PerFax;

    @Expose
    private String PerMob1;

    @Expose
    private String PerMob2;

    @Expose
    private String Occupation;

    @Expose
    private String OccupTypeDesig;

    @Expose
    private Date DOB;

    @Expose
    private int Age;

    @Expose
    private String Location;

    @Expose
    private String PANNo;

    @Expose
    private String BankAcNo;

    @Expose
    private String BankName;

    @Expose
    private String BankBranch;

    @Expose
    private String OtherCase;

    @Expose
    private String Remarks;

    @Expose
    private String RecoveryAuth;

    @Expose
    private String RecoveryExec;

    @Expose
    private String Type;

    @Expose
    private String FDflag;

    @Expose
    private String Relation;

    @Expose
    private String IncomeTax;

    @Expose
    private boolean Minor;

    @Expose
    private String Creator;

    @Expose
    private String UserID;

    @Expose
    private String Auth_UserID;

    @Expose
    private Date Auth_Date;

    @Expose
    private Date Creation_Date;

    @Expose
    private String Mod_Type;

    @Expose
    private String Last_Mod_UserID;

    @Expose
    private Date Last_Mod_Date;

    @Expose
    private String GroupCode;

    @Expose
    private String CityCode;

    @Expose
    private String Gender;

    @Expose
    private String Religion;

    @Expose
    private int LandHolding;

    @Expose
    private String ExServiceMan;

    @Expose
    private int p_pin;

    @Expose
    private int t_pin;

    @Expose
    private int o_pin;

    @Expose
    private String IdentityType;

    @Expose
    private String Identity_no;

    @Expose
    private String p_StateID;

    @Expose
    private String aadharid;

    @Expose
    private String voterid;

    @Expose
    private String drivinglic;

    @Expose
    private String ESignSucceed;

    @Expose
    private String KYCUUID;

    public Guarantor getGuarantor(Borrower borrower) {
        Guarantor guarantor = new Guarantor();
        guarantor.setGrNo(this.GrNo);
        guarantor.associateBorrower(borrower);

        guarantor.setAuth_Date(this.Auth_Date);
        guarantor.setCreation_Date(this.Creation_Date);
        guarantor.setDOB(this.DOB);
        guarantor.setLast_Mod_Date(this.Last_Mod_Date);
        guarantor.setAge(this.Age);
        guarantor.setCorrAddr(this.CorrAddr);
        guarantor.setLandHolding(this.LandHolding);
        guarantor.setMinor(this.Minor ? 1 : 0);
        guarantor.setO_pin(this.o_pin);
        guarantor.setP_pin(this.p_pin);
        guarantor.setT_pin(this.t_pin);
        guarantor.setFi_Code(this.fi_Code);
        guarantor.setAadharid(this.aadharid);
        guarantor.setAuth_UserID(this.Auth_UserID);
        guarantor.setBankAcNo(this.BankAcNo);
        guarantor.setBankBranch(this.BankBranch);
        guarantor.setBankName(this.BankName);
        guarantor.setCityCode(this.CityCode);
        guarantor.setCode(this.Code);
        guarantor.setCreator(this.Creator);
        guarantor.setDrivinglic(this.drivinglic);
        guarantor.setESignSucceed(this.ESignSucceed);
        guarantor.setExServiceMan(this.ExServiceMan);
        guarantor.setFDflag(this.FDflag);
        guarantor.setFirmName(this.FirmName);
        guarantor.setGender(this.Gender);
        guarantor.setGroupCode(this.GroupCode);
        guarantor.setGurInitials(this.GurInitials);
        guarantor.setGurName(this.GurName);
        guarantor.setIdentity_no(this.Identity_no);
        guarantor.setIdentityType(this.IdentityType);
        guarantor.setIncomeTax(this.IncomeTax);
        guarantor.setInitials(this.Initials);
        guarantor.setKYCUUID(this.KYCUUID);
        guarantor.setLast_Mod_UserID(this.Last_Mod_UserID);
        guarantor.setLocation(this.Location);
        guarantor.setMod_Type(this.Mod_Type);
        guarantor.setName(this.Name);
        guarantor.setOccupation(this.Occupation);
        guarantor.setOccupTypeDesig(this.OccupTypeDesig);
        guarantor.setOffAdd1(this.OffAdd1);
        guarantor.setOffAdd2(this.OffAdd2);
        guarantor.setOffAdd3(this.OffAdd3);
        guarantor.setOffCity(this.OffCity);
        guarantor.setOffFax(this.OffFax);
        guarantor.setOffMob1(this.OffMob1);
        guarantor.setOffMob2(this.OffMob2);
        guarantor.setOffPh1(this.OffPh1);
        guarantor.setOffPh2(this.OffPh2);
        guarantor.setOffPh3(this.OffPh3);
        guarantor.setOtherCase(this.OtherCase);
        guarantor.setP_StateID(this.p_StateID);
        guarantor.setPANNo(this.PANNo);
        guarantor.setPerAdd1(this.PerAdd1);
        guarantor.setPerAdd2(this.PerAdd2);
        guarantor.setPerAdd3(this.PerAdd3);
        guarantor.setPerCity(this.PerCity);
        guarantor.setPerFax(this.PerFax);
        guarantor.setPerMob1(this.PerMob1);
        guarantor.setPerMob2(this.PerMob2);
        guarantor.setPerPh1(this.PerPh1);
        guarantor.setPerPh2(this.PerPh2);
        guarantor.setPerPh3(this.PerPh3);
        guarantor.setRecoveryAuth(this.RecoveryAuth);
        guarantor.setRecoveryExec(this.RecoveryExec);
        guarantor.setRelation(this.Relation);
        guarantor.setReligion(this.Religion);
        guarantor.setRemarks(this.Remarks);
        guarantor.setResAdd1(this.ResAdd1);
        guarantor.setResAdd2(this.ResAdd2);
        guarantor.setResAdd3(this.ResAdd3);
        guarantor.setResCity(this.ResCity);
        guarantor.setResFax(this.ResFax);
        guarantor.setResMob1(this.ResMob1);
        guarantor.setResMob2(this.ResMob2);
        guarantor.setResPh1(this.ResPh1);
        guarantor.setResPh2(this.ResPh2);
        guarantor.setResPh3(this.ResPh3);
        guarantor.setType(this.Type);
        guarantor.setUserID(this.UserID);
        guarantor.setVoterid(this.voterid);

        return guarantor;
    }


}
