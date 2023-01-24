package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.Expose;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyMember;

public class BorrowerFamilyMemberDTO {

    @Expose
    private long Code;
    @Expose
    private String Tag;
    @Expose
    private String Creator;
    @Expose
    private String MemName;
    @Expose
    private int Age;
    @Expose
    private String Gender;
    @Expose
    private String RelationWBorrower;
    @Expose
    private String Health;
    @Expose
    private String Educatioin;
    @Expose
    private String SchoolType;
    @Expose
    private String Business;
    @Expose
    private String BusinessType;
    @Expose
    private int Income;
    @Expose
    private String IncomeType;
    @Expose
    private long AutoID;

    public BorrowerFamilyMember getBorrowerFamilyMember() {
        BorrowerFamilyMember familyMember = new BorrowerFamilyMember();
        familyMember.setAge(this.Age);
        familyMember.setAutoID(this.AutoID);
        familyMember.setBusiness(this.Business);
        familyMember.setCode(this.Code);
        familyMember.setBusinessType(this.BusinessType);
        familyMember.setCreator(this.Creator);
        familyMember.setEducatioin(this.Educatioin);
        familyMember.setGender(this.Gender);
        familyMember.setHealth(this.Health);
        familyMember.setIncome(this.Income);
        familyMember.setIncomeType(this.IncomeType);
        familyMember.setMemName(this.MemName);
        familyMember.setRelationWBorrower(this.RelationWBorrower);
        familyMember.setSchoolType(this.SchoolType);
        familyMember.setTag(this.Tag);
        return familyMember;
    }

    @Override
    public String toString() {
        return "BorrowerFamilyMemberDTO{" +
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
                ", AutoID=" + AutoID +
                '}';
    }
}
