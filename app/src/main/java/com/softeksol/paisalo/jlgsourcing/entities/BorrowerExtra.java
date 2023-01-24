package com.softeksol.paisalo.jlgsourcing.entities;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerExtraDTO;

import java.io.Serializable;

/**
 * Created by sachindra on 2016-10-04.
 */
@ModelContainer
@Table(database = DbIGL.class)
public class BorrowerExtra extends BaseModel implements Serializable {
    @Expose
    @Column
    public long Code;

    @Expose
    @Column
    public String Tag;

    @Expose
    @Column
    public String Creator;

    @Expose
    @Column
    public int FutureIncome;

    @Expose
    @Column
    public int OtherDependents;

    @Expose
    @Column
    public int NoOfChildren;

    @Expose
    @Column
    public int SchoolingChildren;

    @Expose
    @Column
    public int SpendOnChildren;

    @Expose
    @Column
    public String FamIncomeSource;

    @Expose
    @Column
    public int FamMonthlyIncome;

    @Expose
    @Column
    public String FamOccupation;

    @Expose
    @Column
    public String FamJobCompType;

    @Expose
    @Column
    public String FamJobCompName;

    @Expose
    @Column
    public String FamBusinessType;

    @Expose
    @Column
    public String FamBusinessShopType;

    @Expose
    @Column
    public String FamAgriLandOwner;

    @Expose
    @Column
    public String FamAgriLandType;

    @Expose
    @Column
    public int FamAgriLandArea;

    @Expose
    @Column
    public String FamOtherIncomeType;

    @Expose
    @Column
    public String OtherContactPerson;

    @Expose
    @Column
    public String OtherContactMob;

    @Expose
    @Column
    public String IMEINO;

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    long FiID;


//////////////////////////////////////////////////////////

    @Expose
    @Column
    public String AGRICULTURAL_INCOME;

    @Expose
    @Column
    public String SOC_ATTR_2_INCOME;

    @Expose
    @Column
    public String SOC_ATTR_3_LAND_HOLD;

    @Expose
    @Column
    public String SOC_ATTR_4_SPL_ABLD;

    @Expose
    @Column
    public String SOC_ATTR_5_SPL_SOC_CTG;

    @Expose
    @Column
    public String EDUCATION_CODE;

    @Expose
    @Column
    public String ANNUAL_INCOME;

    @Expose
    @Column
    public String PLACE_OF_BIRTH;

    @Expose
    @Column
    public String EMAIL_ID;

    @Expose
    @Column
    public String VISUALLY_IMPAIRED_YN;

    @Expose
    @Column
    public String FORM60_TNX_DT;

    @Expose
    @Column
    public String FORM60_SUBMISSIONDATE;

    @Expose
    @Column
    public String FORM60_PAN_APPLIED_YN;

    @Expose
    @Column
    public String MOTHER_TITLE;

     @Expose
    @Column
    public String MOTHER_FIRST_NAME;

      @Expose
    @Column
    public String MOTHER_MIDDLE_NAME;

       @Expose
    @Column
    public String MOTHER_LAST_NAME;

      @Expose
    @Column
    public String MOTHER_MAIDEN_NAME;

 @Expose
    @Column
    public String SPOUSE_TITLE;


      @Expose
    @Column
    public String SPOUSE_FIRST_NAME;


      @Expose
    @Column
    public String SPOUSE_MIDDLE_NAME;

     @Expose
    @Column
    public String SPOUSE_LAST_NAME;

     @Expose
    @Column
    public String FORM60SUBMISSIONDATE;


     @Expose
    @Column
    public String PAN_APPLIED_FLAG;

     @Expose
    @Column
    public String OTHER_THAN_AGRICULTURAL_INCOME;

    @Expose
    @Column
    public String APPLICNT_TITLE;


     @Expose
    @Column
    public String MARITAL_STATUS;


      @Expose
    @Column
    public String OCCUPATION_TYPE;


       @Expose
    @Column
    public String RESERVATN_CATEGORY;



      @Expose
    @Column
    public String FATHER_TITLE;


     @Expose
    @Column
    public String FATHER_FIRST_NAME;


      @Expose
    @Column
    public String FATHER_MIDDLE_NAME;


     @Expose
    @Column
    public String FATHER_LAST_NAME;



    @Override
    public String toString() {
        return "BorrowerExtra{" +
                "Code=" + Code +
                ", Tag='" + Tag + '\'' +
                ", Creator='" + Creator + '\'' +
                ", FutureIncome=" + FutureIncome +
                ", OtherDependents=" + OtherDependents +
                ", NoOfChildren=" + NoOfChildren +
                ", SchoolingChildren=" + SchoolingChildren +
                ", SpendOnChildren=" + SpendOnChildren +
                ", FamIncomeSource='" + FamIncomeSource + '\'' +
                ", FamMonthlyIncome=" + FamMonthlyIncome +
                ", FamOccupation='" + FamOccupation + '\'' +
                ", FamJobCompType='" + FamJobCompType + '\'' +
                ", FamJobCompName='" + FamJobCompName + '\'' +
                ", FamBusinessType='" + FamBusinessType + '\'' +
                ", FamBusinessShopType='" + FamBusinessShopType + '\'' +
                ", FamAgriLandOwner='" + FamAgriLandOwner + '\'' +
                ", FamAgriLandType='" + FamAgriLandType + '\'' +
                ", FamAgriLandArea=" + FamAgriLandArea +
                ", FamOtherIncomeType='" + FamOtherIncomeType + '\'' +
                ", OtherContactPerson='" + OtherContactPerson + '\'' +
                ", OtherContactMob='" + OtherContactMob + '\'' +
                ", IMEINO='" + IMEINO + '\'' +
                ", id=" + id +
                ", FiID=" + FiID +
        ", AGRICULTURAL_INCOME='"+AGRICULTURAL_INCOME+'\''+
                ", SOC_ATTR_2_INCOME='"+SOC_ATTR_2_INCOME+'\''+
        ", SOC_ATTR_3_LAND_HOLD='"+SOC_ATTR_3_LAND_HOLD+'\''+
                ", SOC_ATTR_4_SPL_ABLD='"+SOC_ATTR_4_SPL_ABLD+'\''+
        ", SOC_ATTR_5_SPL_SOC_CTG='"+SOC_ATTR_5_SPL_SOC_CTG+'\''+
                ", EDUCATION_CODE='"+EDUCATION_CODE+'\''+
        ", ANNUAL_INCOME='"+ANNUAL_INCOME+'\''+
                ", PLACE_OF_BIRTH='"+PLACE_OF_BIRTH+'\''+
            ", EMAIL_ID='"+EMAIL_ID+'\''+
                ", VISUALLY_IMPAIRED_YN='"+VISUALLY_IMPAIRED_YN+'\''+
        ", FORM60_TNX_DT='"+FORM60_TNX_DT+'\''+
                ", FORM60_SUBMISSIONDATE='"+FORM60_SUBMISSIONDATE+'\''+
        ", FORM60_PAN_APPLIED_YN='"+FORM60_PAN_APPLIED_YN+'\''+
                ", MOTHER_TITLE='"+MOTHER_TITLE+'\''+
        ", MOTHER_FIRST_NAME='"+MOTHER_FIRST_NAME+'\''+
                ", MOTHER_MIDDLE_NAME='"+MOTHER_MIDDLE_NAME+'\''+
        ", MOTHER_LAST_NAME='"+MOTHER_LAST_NAME+'\''+
                ", MOTHER_MAIDEN_NAME='"+MOTHER_MAIDEN_NAME+'\''+
        ", SPOUSE_TITLE='"+SPOUSE_TITLE+'\''+
                ", SPOUSE_FIRST_NAME='"+SPOUSE_FIRST_NAME+'\''+
        ", SPOUSE_MIDDLE_NAME='"+SPOUSE_MIDDLE_NAME+'\''+
                ", SPOUSE_LAST_NAME='"+SPOUSE_LAST_NAME+'\''+
        ", FORM60SUBMISSIONDATE='"+FORM60SUBMISSIONDATE+'\''+
                ", PAN_APPLIED_FLAG='"+PAN_APPLIED_FLAG+'\''+
        ", OTHER_THAN_AGRICULTURAL_INCOME='"+OTHER_THAN_AGRICULTURAL_INCOME+'\''+
                ", APPLICNT_TITLE='"+APPLICNT_TITLE+'\''+
        ", MARITAL_STATUS='"+MARITAL_STATUS+'\''+
                ", OCCUPATION_TYPE='"+OCCUPATION_TYPE+'\''+
        ", RESERVATN_CATEGORY='"+RESERVATN_CATEGORY+'\''+
                ", FATHER_TITLE='"+FATHER_TITLE+'\''+
        ", FATHER_FIRST_NAME='"+FATHER_FIRST_NAME+'\''+
                ", FATHER_MIDDLE_NAME='"+FATHER_MIDDLE_NAME+'\''+
        ", FATHER_LAST_NAME='"+FATHER_LAST_NAME+'\''+
        '}';
    }

    public BorrowerExtraDTO getExtraDTO() {
        BorrowerExtraDTO borrowerExtraDTO = new BorrowerExtraDTO();
        borrowerExtraDTO.setCode(this.Code);
        borrowerExtraDTO.setCreator(this.Creator);
        borrowerExtraDTO.setFamAgriLandArea(this.FamAgriLandArea);
        borrowerExtraDTO.setFamAgriLandOwner(this.FamAgriLandOwner);
        borrowerExtraDTO.setFamAgriLandType(this.FamAgriLandType);
        borrowerExtraDTO.setFamBusinessShopType(this.FamBusinessShopType);
        borrowerExtraDTO.setFamBusinessType(this.FamBusinessType);
        borrowerExtraDTO.setFamIncomeSource(this.FamIncomeSource);
        borrowerExtraDTO.setFamJobCompName(this.FamJobCompName);
        borrowerExtraDTO.setFamJobCompType(this.FamJobCompType);
        borrowerExtraDTO.setFamMonthlyIncome(this.FamMonthlyIncome);
        borrowerExtraDTO.setFamOccupation(this.FamOccupation);
        borrowerExtraDTO.setFamOtherIncomeType(this.FamOtherIncomeType);
        borrowerExtraDTO.setFutureIncome(this.FutureIncome);
        borrowerExtraDTO.setIMEINO(this.IMEINO);
        borrowerExtraDTO.setNoOfChildren(this.NoOfChildren);
        borrowerExtraDTO.setOtherContactMob(this.OtherContactMob);
        borrowerExtraDTO.setOtherContactPerson(this.OtherContactPerson);
        borrowerExtraDTO.setOtherDependents(this.OtherDependents);
        borrowerExtraDTO.setSchoolingChildren(this.SchoolingChildren);
        borrowerExtraDTO.setSpendOnChildren(this.SpendOnChildren);
        borrowerExtraDTO.setTag(this.Tag);
               borrowerExtraDTO.setAGRICULTURAL_INCOME(this.AGRICULTURAL_INCOME);
       borrowerExtraDTO.setSOC_ATTR_2_INCOME(this.SOC_ATTR_2_INCOME);
               borrowerExtraDTO.setSOC_ATTR_3_LAND_HOLD(this.SOC_ATTR_3_LAND_HOLD);
       borrowerExtraDTO.setSOC_ATTR_4_SPL_ABLD(this.SOC_ATTR_4_SPL_ABLD);
               borrowerExtraDTO.setSOC_ATTR_5_SPL_SOC_CTG(this.SOC_ATTR_5_SPL_SOC_CTG);
       borrowerExtraDTO.setEDUCATION_CODE(this.EDUCATION_CODE);
               borrowerExtraDTO.setANNUAL_INCOME(this.ANNUAL_INCOME);
       borrowerExtraDTO.setPLACE_OF_BIRTH(this.PLACE_OF_BIRTH);
               borrowerExtraDTO.setEMAIL_ID(this.EMAIL_ID);
       borrowerExtraDTO.setVISUALLY_IMPAIRED_YN(this.VISUALLY_IMPAIRED_YN);
               borrowerExtraDTO.setFORM60_TNX_DT(this.FORM60_TNX_DT);
       borrowerExtraDTO.setFORM60_SUBMISSIONDATE(this.FORM60_SUBMISSIONDATE);
               borrowerExtraDTO.setFORM60_PAN_APPLIED_YN(this.FORM60_PAN_APPLIED_YN);
       borrowerExtraDTO.setMOTHER_TITLE(this.MOTHER_TITLE);
               borrowerExtraDTO.setMOTHER_FIRST_NAME(this.MOTHER_FIRST_NAME);
       borrowerExtraDTO.setMOTHER_MIDDLE_NAME(this.MOTHER_MIDDLE_NAME);
               borrowerExtraDTO.setMOTHER_LAST_NAME(this.MOTHER_LAST_NAME);
       borrowerExtraDTO.setMOTHER_MAIDEN_NAME(this.MOTHER_MAIDEN_NAME);
               borrowerExtraDTO.setSPOUSE_TITLE(this.SPOUSE_TITLE);
       borrowerExtraDTO.setSPOUSE_FIRST_NAME(this.SPOUSE_FIRST_NAME);
               borrowerExtraDTO.setSPOUSE_MIDDLE_NAME(this.SPOUSE_MIDDLE_NAME);
       borrowerExtraDTO.setSPOUSE_LAST_NAME(this.SPOUSE_LAST_NAME);
               borrowerExtraDTO.setFORM60SUBMISSIONDATE(this.FORM60SUBMISSIONDATE);
       borrowerExtraDTO.setPAN_APPLIED_FLAG(this.PAN_APPLIED_FLAG);
               borrowerExtraDTO.setOTHER_THAN_AGRICULTURAL_INCOME(this.OTHER_THAN_AGRICULTURAL_INCOME);
       borrowerExtraDTO.setAPPLICNT_TITLE(this.APPLICNT_TITLE);
               borrowerExtraDTO.setMARITAL_STATUS(this.MARITAL_STATUS);
       borrowerExtraDTO.setOCCUPATION_TYPE(this.OCCUPATION_TYPE);
               borrowerExtraDTO.setRESERVATN_CATEGORY(this.RESERVATN_CATEGORY);
       borrowerExtraDTO.setFATHER_TITLE(this.FATHER_TITLE);
               borrowerExtraDTO.setFATHER_FIRST_NAME(this.FATHER_FIRST_NAME);
       borrowerExtraDTO.setFATHER_MIDDLE_NAME(this.FATHER_MIDDLE_NAME);
               borrowerExtraDTO.setFATHER_LAST_NAME(this.FATHER_LAST_NAME);


        return borrowerExtraDTO;
    }

    public BorrowerExtra() {
    }

    public BorrowerExtra(BorrowerExtraDTO borrowerExtraDTO) {
        this.Code = borrowerExtraDTO.getCode();
        this.Creator = borrowerExtraDTO.getCreator();
        this.FamAgriLandArea = borrowerExtraDTO.getFamAgriLandArea();
        this.FamAgriLandOwner = borrowerExtraDTO.getFamAgriLandOwner();
        this.FamAgriLandType = borrowerExtraDTO.getFamAgriLandType();
        this.FamBusinessShopType = borrowerExtraDTO.getFamBusinessShopType();
        this.FamBusinessType = borrowerExtraDTO.getFamBusinessType();
        this.FamIncomeSource = borrowerExtraDTO.getFamIncomeSource();
        this.FamJobCompName = borrowerExtraDTO.getFamJobCompName();
        this.FamJobCompType = borrowerExtraDTO.getFamJobCompType();
        this.FamMonthlyIncome = borrowerExtraDTO.getFamMonthlyIncome();
        this.FamOccupation = borrowerExtraDTO.getFamOccupation();
        this.FamOtherIncomeType = borrowerExtraDTO.getFamOtherIncomeType();
        this.FutureIncome = borrowerExtraDTO.getFutureIncome();
        this.IMEINO = borrowerExtraDTO.getIMEINO();
        this.NoOfChildren = borrowerExtraDTO.getNoOfChildren();
        this.OtherContactMob = borrowerExtraDTO.getOtherContactMob();
        this.OtherContactPerson = borrowerExtraDTO.getOtherContactPerson();
        this.OtherDependents = borrowerExtraDTO.getOtherDependents();
        this.SchoolingChildren = borrowerExtraDTO.getSchoolingChildren();
        this.SpendOnChildren = borrowerExtraDTO.getSpendOnChildren();
        this.Tag = borrowerExtraDTO.getTag();
        this.AGRICULTURAL_INCOME=borrowerExtraDTO.getAGRICULTURAL_INCOME();
                this.SOC_ATTR_2_INCOME=borrowerExtraDTO.getSOC_ATTR_2_INCOME();
        this.SOC_ATTR_3_LAND_HOLD=borrowerExtraDTO.getSOC_ATTR_3_LAND_HOLD();
                this.SOC_ATTR_4_SPL_ABLD=borrowerExtraDTO.getSOC_ATTR_4_SPL_ABLD();
        this.SOC_ATTR_5_SPL_SOC_CTG=borrowerExtraDTO.getSOC_ATTR_5_SPL_SOC_CTG();
                this.EDUCATION_CODE=borrowerExtraDTO.getEDUCATION_CODE();
        this.ANNUAL_INCOME=borrowerExtraDTO.getANNUAL_INCOME();
                this.PLACE_OF_BIRTH=borrowerExtraDTO.getPLACE_OF_BIRTH();
            this.EMAIL_ID=borrowerExtraDTO.getEMAIL_ID();
                this.VISUALLY_IMPAIRED_YN=borrowerExtraDTO.getVISUALLY_IMPAIRED_YN();
        this.FORM60_TNX_DT=borrowerExtraDTO.getFORM60_TNX_DT();
                this.FORM60_SUBMISSIONDATE=borrowerExtraDTO.getFORM60_SUBMISSIONDATE();
        this.FORM60_PAN_APPLIED_YN=borrowerExtraDTO.getFORM60_PAN_APPLIED_YN();
                this.MOTHER_TITLE=borrowerExtraDTO.getMOTHER_TITLE();
        this.MOTHER_FIRST_NAME=borrowerExtraDTO.getMOTHER_FIRST_NAME();
                this.MOTHER_MIDDLE_NAME=borrowerExtraDTO.getMOTHER_MIDDLE_NAME();
        this.MOTHER_LAST_NAME=borrowerExtraDTO.getMOTHER_LAST_NAME();
                this.MOTHER_MAIDEN_NAME=borrowerExtraDTO.getMOTHER_MAIDEN_NAME();
        this.SPOUSE_TITLE=borrowerExtraDTO.getSPOUSE_TITLE();
                this.SPOUSE_FIRST_NAME=borrowerExtraDTO.getSPOUSE_FIRST_NAME();
        this.SPOUSE_MIDDLE_NAME=borrowerExtraDTO.getSPOUSE_MIDDLE_NAME();
                this.SPOUSE_LAST_NAME=borrowerExtraDTO.getSPOUSE_LAST_NAME();
        this.FORM60SUBMISSIONDATE=borrowerExtraDTO.getFORM60SUBMISSIONDATE();
                this.PAN_APPLIED_FLAG=borrowerExtraDTO.getPAN_APPLIED_FLAG();
        this.OTHER_THAN_AGRICULTURAL_INCOME=borrowerExtraDTO.getOTHER_THAN_AGRICULTURAL_INCOME();
                this.APPLICNT_TITLE=borrowerExtraDTO.getAPPLICNT_TITLE();
        this.MARITAL_STATUS=borrowerExtraDTO.getMARITAL_STATUS();
                this.OCCUPATION_TYPE=borrowerExtraDTO.getOCCUPATION_TYPE();
        this.RESERVATN_CATEGORY=borrowerExtraDTO.getRESERVATN_CATEGORY();
                this.FATHER_TITLE=borrowerExtraDTO.getFATHER_TITLE();
        this.FATHER_FIRST_NAME=borrowerExtraDTO.getFATHER_FIRST_NAME();
                this.FATHER_MIDDLE_NAME=borrowerExtraDTO.getFATHER_MIDDLE_NAME();
        this.FATHER_LAST_NAME=borrowerExtraDTO.getFATHER_LAST_NAME();
    }
}
