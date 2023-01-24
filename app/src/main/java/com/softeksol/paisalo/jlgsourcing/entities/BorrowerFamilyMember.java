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
public class BorrowerFamilyMember extends BaseModel implements Serializable {

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
    private String MemName;
    @Expose
    @Column
    private int Age;
    @Expose
    @Column
    private String Gender;
    @Expose
    @Column
    private String RelationWBorrower;
    @Expose
    @Column
    private String Health;
    @Expose
    @Column
    private String Educatioin;
    @Expose
    @Column
    private String SchoolType;
    @Expose
    @Column
    private String Business;
    @Expose
    @Column
    private String BusinessType;
    @Expose
    @Column
    private int Income;
    @Expose
    @Column
    private String IncomeType;
    @Column
    @PrimaryKey(autoincrement = true)
    private long id;
    @Column
    private long FiID;
    @Expose
    @Column
    private long AutoID;

    public BorrowerFamilyMember() {
    }

    public BorrowerFamilyMember(long code, String tag, String creator, String memName, int age, String gender, String relationWBorrower, String health, String educatioin, String schoolType, String business, String businessType, int income, String incomeType, long id, long fiID, long autoId) {
        Code = code;
        Tag = tag;
        Creator = creator;
        MemName = memName;
        Age = age;
        Gender = gender;
        RelationWBorrower = relationWBorrower;
        Health = health;
        Educatioin = educatioin;
        SchoolType = schoolType;
        Business = business;
        BusinessType = businessType;
        Income = income;
        IncomeType = incomeType;
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

    public String getMemName() {
        return MemName;
    }

    public void setMemName(String memName) {
        MemName = memName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getRelationWBorrower() {
        return RelationWBorrower;
    }

    public void setRelationWBorrower(String relationWBorrower) {
        RelationWBorrower = relationWBorrower;
    }

    public String getHealth() {
        return Health;
    }

    public void setHealth(String health) {
        Health = health;
    }

    public String getEducatioin() {
        return Educatioin;
    }

    public void setEducatioin(String educatioin) {
        Educatioin = educatioin;
    }

    public String getSchoolType() {
        return SchoolType;
    }

    public void setSchoolType(String schoolType) {
        SchoolType = schoolType;
    }

    public String getBusiness() {
        return Business;
    }

    public void setBusiness(String business) {
        Business = business;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public int getIncome() {
        return Income;
    }

    public void setIncome(int income) {
        Income = income;
    }

    public String getIncomeType() {
        return IncomeType;
    }

    public void setIncomeType(String incomeType) {
        IncomeType = incomeType;
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
        return "BorrowerFamilyMember{" +
                "Code=" + Code +
                ", Tag='" + Tag + '\'' +
                ", Creator='" + Creator + '\'' +
                ", MemName='" + MemName + '\'' +
                ", Age=" + Age +
                ", Gender='" + Gender + '\'' +
                ", RelationWBorrower='" + RelationWBorrower + '\'' +
                ", Health='" + Health + '\'' +
                ", Educatioin='" + Educatioin + '\'' +
                ", SchoolType='" + SchoolType + '\'' +
                ", Business='" + Business + '\'' +
                ", BusinessType='" + BusinessType + '\'' +
                ", Income=" + Income +
                ", IncomeType='" + IncomeType + '\'' +
                ", id=" + id +
                ", FiID=" + FiID +
                '}';
    }
}
