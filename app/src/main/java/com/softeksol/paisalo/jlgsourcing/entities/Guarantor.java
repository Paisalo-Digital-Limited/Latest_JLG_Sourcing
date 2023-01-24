package com.softeksol.paisalo.jlgsourcing.entities;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import com.softeksol.paisalo.jlgsourcing.DbIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.enums.EnumApiPath;
import com.softeksol.paisalo.jlgsourcing.enums.EnumFieldName;
import com.softeksol.paisalo.jlgsourcing.enums.EnumImageTags;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sachindra on 2015-10-01.
 */
@ModelContainer
@Table(database = DbIGL.class)
public class Guarantor extends BaseModel implements Serializable {

    @Expose
    @Column
    private String Code;

    @Expose
    @Column
    private long fi_Code;

    @Expose
    @Column
    private int GrNo = 0;

    @Expose
    @Column
    private String Initials;

    @Expose
    @Column
    private String Name;

    @Expose
    @Column
    private String GurInitials;

    @Expose
    @Column
    private String GurName;

    @Expose
    @Column
    private int CorrAddr;

    @Expose
    @Column
    private String FirmName;

    @Expose
    @Column
    private String OffAdd1;

    @Expose
    @Column
    private String OffAdd2;

    @Expose
    @Column
    private String OffAdd3;

    @Expose
    @Column
    private String OffCity;

    @Expose
    @Column
    private String OffPh1;

    @Expose
    @Column
    private String OffPh2;

    @Expose
    @Column
    private String OffPh3;

    @Expose
    @Column
    private String OffFax;

    @Expose
    @Column
    private String OffMob1;

    @Expose
    @Column
    private String OffMob2;

    @Expose
    @Column
    private String ResAdd1;

    @Expose
    @Column
    private String ResAdd2;

    @Expose
    @Column
    private String ResAdd3;

    @Expose
    @Column
    private String ResCity;

    @Expose
    @Column
    private String ResPh1;

    @Expose
    @Column
    private String ResPh2;

    @Expose
    @Column
    private String ResPh3;

    @Expose
    @Column
    private String ResFax;

    @Expose
    @Column
    private String ResMob1;

    @Expose
    @Column
    private String ResMob2;

    @Expose
    @Column
    private String PerAdd1;

    @Expose
    @Column
    private String PerAdd2;

    @Expose
    @Column
    private String PerAdd3;

    @Expose
    @Column
    private String PerCity;

    @Expose
    @Column
    private String PerPh1;

    @Expose
    @Column
    private String PerPh2;

    @Expose
    @Column
    private String PerPh3;

    @Expose
    @Column
    private String PerFax;

    @Expose
    @Column
    private String PerMob1;

    @Expose
    @Column
    private String PerMob2;

    @Expose
    @Column
    private String Occupation;

    @Expose
    @Column
    private String OccupTypeDesig;

    @Expose
    @Column
    private Date DOB;

    @Expose
    @Column
    private int Age;

    @Expose
    @Column
    private String Location;

    @Expose
    @Column
    private String PANNo;

    @Expose
    @Column
    private String BankAcNo;

    @Expose
    @Column
    private String BankName;

    @Expose
    @Column
    private String BankBranch;

    @Expose
    @Column
    private String OtherCase;

    @Expose
    @Column
    private String Remarks;

    @Expose
    @Column
    private String RecoveryAuth;

    @Expose
    @Column
    private String RecoveryExec;

    @Expose
    @Column
    private String Type;

    @Expose
    @Column
    private String FDflag;

    @Expose
    @Column
    private String Relation;

    @Expose
    @Column
    private String IncomeTax;

    @Expose
    @Column
    private int Minor;

    @Expose
    @Column
    private String Creator;

    @Expose
    @Column
    private String UserID;

    @Expose
    @Column
    private String Auth_UserID;

    @Expose
    @Column
    private Date Auth_Date;

    @Expose
    @Column
    private Date Creation_Date;

    @Expose
    @Column
    private String Mod_Type;

    @Expose
    @Column
    private String Last_Mod_UserID;

    @Expose
    @Column
    private Date Last_Mod_Date;

    @Expose
    @Column
    private String GroupCode;

    @Expose
    @Column
    private String CityCode;

    @Expose
    @Column
    private String Gender;

    @Expose
    @Column
    private String Religion;

    @Expose
    @Column
    private int LandHolding;

    @Expose
    @Column
    private String ExServiceMan;

    @Expose
    @Column
    private int p_pin;

    @Expose
    @Column
    private int t_pin;

    @Expose
    @Column
    private int o_pin;

    @Expose
    @Column
    private String IdentityType;

    @Expose
    @Column
    private String Identity_no;

    @Expose
    @Column
    private String p_StateID;

    @Expose
    @Column
    private String aadharid;

    @Expose
    @Column
    private String voterid;

    @Expose
    @Column
    private String drivinglic;

    @Expose
    @Column
    private String ESignSucceed;

    @Column
    private String isAadharVerified;

    @Expose
    @Column
    private String KYCUUID;

    @Column
    @PrimaryKey(autoincrement = true)
    private long id;

    @Expose
    private long Concent;


    @ForeignKey
    ForeignKeyContainer<Borrower> borrowerForeignKeyContainer;

    @Column
    private long FiID;

    public void associateBorrower(Borrower borrower) {
        this.FiID = borrower.FiID;
        this.Creator = borrower.Creator;
        this.GroupCode = borrower.GroupCode;
        this.CityCode = borrower.CityCode;
        this.UserID = borrower.UserID;
        this.fi_Code = borrower.Code;
        if (this.GrNo == 0) this.GrNo = getNextGrNo(borrower.FiID);
        this.borrowerForeignKeyContainer = FlowManager.getContainerAdapter(Borrower.class)
                .toForeignKeyContainer(borrower); // convenience conversion
    }

    public String getPerAddress() {
        return TextUtils.join(" ", new String[]{Utils.NullIf(PerAdd1, ""), Utils.NullIf(PerAdd2, ""), Utils.NullIf(PerAdd3, ""), Utils.NullIf(PerCity, "")}).replace("  ", " ").trim();
    }

    public int getNextGrNo(long fiId) {
        int nextGrNo = 1;
        Cursor cursor = SQLite.select(Method.max(Guarantor_Table.GrNo)).from(Guarantor.class).as("MaxGrNo").where(Guarantor_Table.FiID.eq(fiId)).query();
        if (cursor != null)
            if (cursor.getCount() > 0)
                cursor.moveToFirst();
        if (!cursor.isNull(0))
            if (cursor.getInt(0) > 0) nextGrNo = cursor.getInt(0) + 1;
        return nextGrNo;
    }

    public void setPicture(String imagePath) {
        DocumentStore documentStore = SQLite.select()
                .from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(this.FiID))
                .and(DocumentStore_Table.GuarantorSerial.eq(GrNo))
                .and(DocumentStore_Table.checklistid.eq(0))
                .querySingle();
        if (documentStore == null) {
            documentStore = new DocumentStore();
            documentStore.FiID = this.FiID;
            documentStore.Creator = this.Creator;
            documentStore.ficode = this.fi_Code;
            documentStore.fitag = this.getFiTag();
            documentStore.userid = this.UserID;
            documentStore.checklistid = 0;
            documentStore.remarks = "Picture";
            documentStore.imageTag = EnumImageTags.Guarantor.getImageTag();
            documentStore.fieldname = EnumFieldName.Guarantor.getFieldName();
            documentStore.apiRelativePath = EnumApiPath.GuarantorApi.getApiPath();
            documentStore.GuarantorSerial = GrNo;
        }
        documentStore.imagePath = imagePath;
        documentStore.save();
    }

//    public void setPictureGuarantor(String imagePath,String imageShow) {
//        DocumentStore documentStore = SQLite.select()
//                .from(DocumentStore.class)
//                .where(DocumentStore_Table.FiID.eq(this.FiID))
//                .and(DocumentStore_Table.GuarantorSerial.eq(GrNo))
//                .and(DocumentStore_Table.checklistid.eq(0))
//                .querySingle();
//        if (documentStore == null) {
//            documentStore = new DocumentStore();
//            documentStore.FiID = this.FiID;
//            documentStore.Creator = this.Creator;
//            documentStore.ficode = this.fi_Code;
//            documentStore.fitag = this.getFiTag();
//            documentStore.userid = this.UserID;
//            documentStore.checklistid = 0;
//            documentStore.remarks = "Picture";
//            documentStore.imageTag = EnumImageTags.Guarantor.getImageTag();
//            documentStore.fieldname = EnumFieldName.Guarantor.getFieldName();
//            documentStore.apiRelativePath = EnumApiPath.GuarantorApi.getApiPath();
//            documentStore.GuarantorSerial = GrNo;
//        }
//        documentStore.imagePath = imagePath;
//        documentStore.imageshow = imageShow;
//        documentStore.save();
//    }

    public void setPicture(Uri imagePath) {
        setPicture(imagePath.getPath());
    }

    public Uri getPicture() {
        DocumentStore documentStore = getPictureStore();
        return documentStore == null ? null : (documentStore.imagePath == null ? null : Uri.parse(documentStore.imagePath));
    }

//    public String getPictureGuarantor(){
//        DocumentStore documentStore = getPictureStore();
//
//        return documentStore == null ? null : (documentStore.imageshow == null ? null : documentStore.imageshow);
//    }

    public DocumentStore getPictureStore() {
        DocumentStore documentStore = SQLite.select()
                .from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(this.FiID))
                .and(DocumentStore_Table.GuarantorSerial.eq(this.GrNo))
                .and(DocumentStore_Table.checklistid.eq(0))
                .querySingle();
        return documentStore;
    }


    @Override
    public void delete() {
        SQLite.delete().from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(this.FiID))
                .and(DocumentStore_Table.GuarantorSerial.eq(this.GrNo))
                .execute();
        super.delete();
    }

    public String getFiTag() {
        return SQLite.select()
                .from(Borrower.class)
                .where(Borrower_Table.FiID.eq(this.FiID))
                .querySingle().Tag;
    }

    @Override
    public String toString() {
        return "Guarantor{" +
                "Code='" + Code + '\'' +
                ", fi_Code=" + fi_Code +
                ", GrNo=" + GrNo +
                ", Initials='" + Initials + '\'' +
                ", Name='" + Name + '\'' +
                ", GurInitials='" + GurInitials + '\'' +
                ", GurName='" + GurName + '\'' +
                ", CorrAddr=" + CorrAddr +
                ", FirmName='" + FirmName + '\'' +
                ", OffAdd1='" + OffAdd1 + '\'' +
                ", OffAdd2='" + OffAdd2 + '\'' +
                ", OffAdd3='" + OffAdd3 + '\'' +
                ", OffCity='" + OffCity + '\'' +
                ", OffPh1='" + OffPh1 + '\'' +
                ", OffPh2='" + OffPh2 + '\'' +
                ", OffPh3='" + OffPh3 + '\'' +
                ", OffFax='" + OffFax + '\'' +
                ", OffMob1='" + OffMob1 + '\'' +
                ", OffMob2='" + OffMob2 + '\'' +
                ", ResAdd1='" + ResAdd1 + '\'' +
                ", ResAdd2='" + ResAdd2 + '\'' +
                ", ResAdd3='" + ResAdd3 + '\'' +
                ", ResCity='" + ResCity + '\'' +
                ", ResPh1='" + ResPh1 + '\'' +
                ", ResPh2='" + ResPh2 + '\'' +
                ", ResPh3='" + ResPh3 + '\'' +
                ", ResFax='" + ResFax + '\'' +
                ", ResMob1='" + ResMob1 + '\'' +
                ", ResMob2='" + ResMob2 + '\'' +
                ", PerAdd1='" + PerAdd1 + '\'' +
                ", PerAdd2='" + PerAdd2 + '\'' +
                ", PerAdd3='" + PerAdd3 + '\'' +
                ", PerCity='" + PerCity + '\'' +
                ", PerPh1='" + PerPh1 + '\'' +
                ", PerPh2='" + PerPh2 + '\'' +
                ", PerPh3='" + PerPh3 + '\'' +
                ", PerFax='" + PerFax + '\'' +
                ", PerMob1='" + PerMob1 + '\'' +
                ", PerMob2='" + PerMob2 + '\'' +
                ", Occupation='" + Occupation + '\'' +
                ", OccupTypeDesig='" + OccupTypeDesig + '\'' +
                ", DOB=" + DOB +
                ", Age=" + Age +
                ", Location='" + Location + '\'' +
                ", PANNo='" + PANNo + '\'' +
                ", BankAcNo='" + BankAcNo + '\'' +
                ", BankName='" + BankName + '\'' +
                ", BankBranch='" + BankBranch + '\'' +
                ", OtherCase='" + OtherCase + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", RecoveryAuth='" + RecoveryAuth + '\'' +
                ", RecoveryExec='" + RecoveryExec + '\'' +
                ", Type='" + Type + '\'' +
                ", FDflag='" + FDflag + '\'' +
                ", Relation='" + Relation + '\'' +
                ", IncomeTax='" + IncomeTax + '\'' +
                ", Minor=" + Minor +
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
                ", P_Pin=" + p_pin +
                ", T_Pin=" + t_pin +
                ", O_Pin=" + o_pin +
                ", IdentityType='" + IdentityType + '\'' +
                ", Identity_No='" + Identity_no + '\'' +
                ", p_StateID='" + p_StateID + '\'' +
                ", AadharID='" + aadharid + '\'' +
                ", VoterID='" + voterid + '\'' +
                ", DrivingLic='" + drivinglic + '\'' +
                ", ESignSucceed='" + ESignSucceed + '\'' +
                ", isAadharVerified='" + isAadharVerified + '\'' +
                ", KYCUUID='" + KYCUUID + '\'' +
                ", id=" + id +
                ", Concent=" + Concent +
                ", borrowerForeignKeyContainer=" + borrowerForeignKeyContainer +
                ", FiID=" + FiID +
                '}';
    }

    public String getCode() {
        return Code;
    }

    public long getFi_Code() {
        return fi_Code;
    }

    public int getGrNo() {
        return GrNo;
    }

    public String getInitials() {
        return Initials;
    }

    public String getName() {
        return Name;
    }

    public String getGurInitials() {
        return GurInitials;
    }

    public String getGurName() {
        return GurName;
    }

    public int getCorrAddr() {
        return CorrAddr;
    }

    public String getFirmName() {
        return FirmName;
    }

    public String getOffAdd1() {
        return OffAdd1;
    }

    public String getOffAdd2() {
        return OffAdd2;
    }

    public String getOffAdd3() {
        return OffAdd3;
    }

    public String getOffCity() {
        return OffCity;
    }

    public String getOffPh1() {
        return OffPh1;
    }

    public String getOffPh2() {
        return OffPh2;
    }

    public String getOffPh3() {
        return OffPh3;
    }

    public String getOffFax() {
        return OffFax;
    }

    public String getOffMob1() {
        return OffMob1;
    }

    public String getOffMob2() {
        return OffMob2;
    }

    public String getResAdd1() {
        return ResAdd1;
    }

    public String getResAdd2() {
        return ResAdd2;
    }

    public String getResAdd3() {
        return ResAdd3;
    }

    public String getResCity() {
        return ResCity;
    }

    public String getResPh1() {
        return ResPh1;
    }

    public String getResPh2() {
        return ResPh2;
    }

    public String getResPh3() {
        return ResPh3;
    }

    public String getResFax() {
        return ResFax;
    }

    public String getResMob1() {
        return ResMob1;
    }

    public String getResMob2() {
        return ResMob2;
    }

    public String getPerAdd1() {
        return PerAdd1;
    }

    public String getPerAdd2() {
        return PerAdd2;
    }

    public String getPerAdd3() {
        return PerAdd3;
    }

    public String getPerCity() {
        return PerCity;
    }

    public String getPerPh1() {
        return PerPh1;
    }

    public String getPerPh2() {
        return PerPh2;
    }

    public String getPerPh3() {
        return PerPh3;
    }

    public String getPerFax() {
        return PerFax;
    }

    public String getPerMob1() {
        return PerMob1;
    }

    public String getPerMob2() {
        return PerMob2;
    }

    public String getOccupation() {
        return Occupation;
    }

    public String getOccupTypeDesig() {
        return OccupTypeDesig;
    }

    public Date getDOB() {
        return DOB;
    }

    public int getAge() {
        return Age;
    }

    public String getLocation() {
        return Location;
    }

    public String getPANNo() {
        return PANNo;
    }

    public String getBankAcNo() {
        return BankAcNo;
    }

    public String getBankName() {
        return BankName;
    }

    public String getBankBranch() {
        return BankBranch;
    }

    public String getOtherCase() {
        return OtherCase;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getRecoveryAuth() {
        return RecoveryAuth;
    }

    public String getRecoveryExec() {
        return RecoveryExec;
    }

    public String getType() {
        return Type;
    }

    public String getFDflag() {
        return FDflag;
    }

    public String getRelation() {
        return Relation;
    }

    public String getIncomeTax() {
        return IncomeTax;
    }

    public int getMinor() {
        return Minor;
    }

    public String getCreator() {
        return Creator;
    }

    public String getUserID() {
        return UserID;
    }

    public String getAuth_UserID() {
        return Auth_UserID;
    }

    public Date getAuth_Date() {
        return Auth_Date;
    }

    public Date getCreation_Date() {
        return Creation_Date;
    }

    public String getMod_Type() {
        return Mod_Type;
    }

    public String getLast_Mod_UserID() {
        return Last_Mod_UserID;
    }

    public Date getLast_Mod_Date() {
        return Last_Mod_Date;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public String getCityCode() {
        return CityCode;
    }

    public String getGender() {
        return Gender;
    }

    public String getReligion() {
        return Religion;
    }

    public int getLandHolding() {
        return LandHolding;
    }

    public String getExServiceMan() {
        return ExServiceMan;
    }

    public int getP_pin() {
        return p_pin;
    }

    public int getT_pin() {
        return t_pin;
    }

    public int getO_pin() {
        return o_pin;
    }

    public String getIdentityType() {
        return IdentityType;
    }

    public String getIdentity_no() {
        return Identity_no;
    }

    public String getP_StateID() {
        return p_StateID;
    }

    public String getAadharid() {
        return aadharid;
    }

    public String getVoterid() {
        return voterid;
    }

    public String getDrivinglic() {
        return drivinglic;
    }

    public String getESignSucceed() {
        return ESignSucceed;
    }

    public String getIsAadharVerified() {
        return isAadharVerified;
    }

    public String getKYCUUID() {
        return KYCUUID;
    }

    public long getId() {
        return id;
    }

    public long getConcent() {
        return Concent;
    }

    public ForeignKeyContainer<Borrower> getBorrowerForeignKeyContainer() {
        return borrowerForeignKeyContainer;
    }

    public long getFiID() {
        return FiID;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setFi_Code(long fi_Code) {
        this.fi_Code = fi_Code;
    }

    public void setGrNo(int grNo) {
        GrNo = grNo;
    }

    public void setPerMob1(String perMob1) {
        PerMob1 = perMob1;
    }

    public void setVoterid(String voterid) {
        this.voterid = voterid;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setGurName(String gurName) {
        GurName = gurName;
    }

    public void setPerAdd1(String perAdd1) {
        PerAdd1 = perAdd1;
    }

    public void setPerAdd2(String perAdd2) {
        PerAdd2 = perAdd2;
    }

    public void setPerAdd3(String perAdd3) {
        PerAdd3 = perAdd3;
    }

    public void setPerCity(String perCity) {
        PerCity = perCity;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setP_StateID(String p_StateID) {
        this.p_StateID = p_StateID;
    }

    public void setP_pin(int p_pin) {
        this.p_pin = p_pin;
    }

    public void setAadharid(String aadharid) {
        this.aadharid = aadharid;
    }

    public void setIsAadharVerified(String isAadharVerified) {
        this.isAadharVerified = isAadharVerified;
    }

    public void setKYCUUID(String KYCUUID) {
        this.KYCUUID = KYCUUID;
    }

    public void setInitials(String initials) {
        Initials = initials;
    }

    public void setGurInitials(String gurInitials) {
        GurInitials = gurInitials;
    }

    public void setCorrAddr(int corrAddr) {
        CorrAddr = corrAddr;
    }

    public void setFirmName(String firmName) {
        FirmName = firmName;
    }

    public void setOffAdd1(String offAdd1) {
        OffAdd1 = offAdd1;
    }

    public void setOffAdd2(String offAdd2) {
        OffAdd2 = offAdd2;
    }

    public void setOffAdd3(String offAdd3) {
        OffAdd3 = offAdd3;
    }

    public void setOffCity(String offCity) {
        OffCity = offCity;
    }

    public void setOffPh1(String offPh1) {
        OffPh1 = offPh1;
    }

    public void setOffPh2(String offPh2) {
        OffPh2 = offPh2;
    }

    public void setOffPh3(String offPh3) {
        OffPh3 = offPh3;
    }

    public void setOffFax(String offFax) {
        OffFax = offFax;
    }

    public void setOffMob1(String offMob1) {
        OffMob1 = offMob1;
    }

    public void setOffMob2(String offMob2) {
        OffMob2 = offMob2;
    }

    public void setResAdd1(String resAdd1) {
        ResAdd1 = resAdd1;
    }

    public void setResAdd2(String resAdd2) {
        ResAdd2 = resAdd2;
    }

    public void setResAdd3(String resAdd3) {
        ResAdd3 = resAdd3;
    }

    public void setResCity(String resCity) {
        ResCity = resCity;
    }

    public void setResPh1(String resPh1) {
        ResPh1 = resPh1;
    }

    public void setResPh2(String resPh2) {
        ResPh2 = resPh2;
    }

    public void setResPh3(String resPh3) {
        ResPh3 = resPh3;
    }

    public void setResFax(String resFax) {
        ResFax = resFax;
    }

    public void setResMob1(String resMob1) {
        ResMob1 = resMob1;
    }

    public void setResMob2(String resMob2) {
        ResMob2 = resMob2;
    }

    public void setPerPh1(String perPh1) {
        PerPh1 = perPh1;
    }

    public void setPerPh2(String perPh2) {
        PerPh2 = perPh2;
    }

    public void setPerPh3(String perPh3) {
        PerPh3 = perPh3;
    }

    public void setPerFax(String perFax) {
        PerFax = perFax;
    }

    public void setPerMob2(String perMob2) {
        PerMob2 = perMob2;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public void setOccupTypeDesig(String occupTypeDesig) {
        OccupTypeDesig = occupTypeDesig;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setPANNo(String PANNo) {
        this.PANNo = PANNo;
    }

    public void setBankAcNo(String bankAcNo) {
        BankAcNo = bankAcNo;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public void setBankBranch(String bankBranch) {
        BankBranch = bankBranch;
    }

    public void setOtherCase(String otherCase) {
        OtherCase = otherCase;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setRecoveryAuth(String recoveryAuth) {
        RecoveryAuth = recoveryAuth;
    }

    public void setRecoveryExec(String recoveryExec) {
        RecoveryExec = recoveryExec;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setFDflag(String FDflag) {
        this.FDflag = FDflag;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public void setIncomeTax(String incomeTax) {
        IncomeTax = incomeTax;
    }

    public void setMinor(int minor) {
        Minor = minor;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public void setAuth_UserID(String auth_UserID) {
        Auth_UserID = auth_UserID;
    }

    public void setAuth_Date(Date auth_Date) {
        Auth_Date = auth_Date;
    }

    public void setCreation_Date(Date creation_Date) {
        Creation_Date = creation_Date;
    }

    public void setMod_Type(String mod_Type) {
        Mod_Type = mod_Type;
    }

    public void setLast_Mod_UserID(String last_Mod_UserID) {
        Last_Mod_UserID = last_Mod_UserID;
    }

    public void setLast_Mod_Date(Date last_Mod_Date) {
        Last_Mod_Date = last_Mod_Date;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public void setLandHolding(int landHolding) {
        LandHolding = landHolding;
    }

    public void setExServiceMan(String exServiceMan) {
        ExServiceMan = exServiceMan;
    }

    public void setT_pin(int t_pin) {
        this.t_pin = t_pin;
    }

    public void setO_pin(int o_pin) {
        this.o_pin = o_pin;
    }

    public void setIdentityType(String identityType) {
        IdentityType = identityType;
    }

    public void setIdentity_no(String identity_no) {
        Identity_no = identity_no;
    }

    public void setDrivinglic(String drivinglic) {
        this.drivinglic = drivinglic;
    }

    public void setESignSucceed(String ESignSucceed) {
        this.ESignSucceed = ESignSucceed;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConcent(long concent) {
        Concent = concent;
    }

    public void setFiID(long fiID) {
        FiID = fiID;
    }

    public Guarantor() {
    }

    public Guarantor(String code, long fi_Code, int grNo, String initials, String name, String gurInitials, String gurName, int corrAddr, String firmName, String offAdd1, String offAdd2, String offAdd3, String offCity, String offPh1, String offPh2, String offPh3, String offFax, String offMob1, String offMob2, String resAdd1, String resAdd2, String resAdd3, String resCity, String resPh1, String resPh2, String resPh3, String resFax, String resMob1, String resMob2, String perAdd1, String perAdd2, String perAdd3, String perCity, String perPh1, String perPh2, String perPh3, String perFax, String perMob1, String perMob2, String occupation, String occupTypeDesig, Date DOB, int age, String location, String PANNo, String bankAcNo, String bankName, String bankBranch, String otherCase, String remarks, String recoveryAuth, String recoveryExec, String type, String FDflag, String relation, String incomeTax, int minor, String creator, String userID, String auth_UserID, Date auth_Date, Date creation_Date, String mod_Type, String last_Mod_UserID, Date last_Mod_Date, String groupCode, String cityCode, String gender, String religion, int landHolding, String exServiceMan, int p_pin, int t_pin, int o_pin, String identityType, String identity_no, String p_StateID, String aadharid, String voterid, String drivinglic, String ESignSucceed, String isAadharVerified, String KYCUUID, long id, long concent, long fiID) {
        Code = code;
        this.fi_Code = fi_Code;
        GrNo = grNo;
        Initials = initials;
        Name = name;
        GurInitials = gurInitials;
        GurName = gurName;
        CorrAddr = corrAddr;
        FirmName = firmName;
        OffAdd1 = offAdd1;
        OffAdd2 = offAdd2;
        OffAdd3 = offAdd3;
        OffCity = offCity;
        OffPh1 = offPh1;
        OffPh2 = offPh2;
        OffPh3 = offPh3;
        OffFax = offFax;
        OffMob1 = offMob1;
        OffMob2 = offMob2;
        ResAdd1 = resAdd1;
        ResAdd2 = resAdd2;
        ResAdd3 = resAdd3;
        ResCity = resCity;
        ResPh1 = resPh1;
        ResPh2 = resPh2;
        ResPh3 = resPh3;
        ResFax = resFax;
        ResMob1 = resMob1;
        ResMob2 = resMob2;
        PerAdd1 = perAdd1;
        PerAdd2 = perAdd2;
        PerAdd3 = perAdd3;
        PerCity = perCity;
        PerPh1 = perPh1;
        PerPh2 = perPh2;
        PerPh3 = perPh3;
        PerFax = perFax;
        PerMob1 = perMob1;
        PerMob2 = perMob2;
        Occupation = occupation;
        OccupTypeDesig = occupTypeDesig;
        this.DOB = DOB;
        Age = age;
        Location = location;
        this.PANNo = PANNo;
        BankAcNo = bankAcNo;
        BankName = bankName;
        BankBranch = bankBranch;
        OtherCase = otherCase;
        Remarks = remarks;
        RecoveryAuth = recoveryAuth;
        RecoveryExec = recoveryExec;
        Type = type;
        this.FDflag = FDflag;
        Relation = relation;
        IncomeTax = incomeTax;
        Minor = minor;
        Creator = creator;
        UserID = userID;
        Auth_UserID = auth_UserID;
        Auth_Date = auth_Date;
        Creation_Date = creation_Date;
        Mod_Type = mod_Type;
        Last_Mod_UserID = last_Mod_UserID;
        Last_Mod_Date = last_Mod_Date;
        GroupCode = groupCode;
        CityCode = cityCode;
        Gender = gender;
        Religion = religion;
        LandHolding = landHolding;
        ExServiceMan = exServiceMan;
        this.p_pin = p_pin;
        this.t_pin = t_pin;
        this.o_pin = o_pin;
        IdentityType = identityType;
        Identity_no = identity_no;
        this.p_StateID = p_StateID;
        this.aadharid = aadharid;
        this.voterid = voterid;
        this.drivinglic = drivinglic;
        this.ESignSucceed = ESignSucceed;
        this.isAadharVerified = isAadharVerified;
        this.KYCUUID = KYCUUID;
        this.id = id;
        Concent = concent;
        FiID = fiID;
    }
}
