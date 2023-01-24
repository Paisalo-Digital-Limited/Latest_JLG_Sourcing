package com.softeksol.paisalo.jlgsourcing.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerExtraBankDTO;

import java.io.Serializable;

/**
 * Created by sachindra on 2016-10-04.
 */
@ModelContainer
@Table(database = DbIGL.class)
public class BorrowerExtraBank extends BaseModel implements Serializable {
    @Column
    long Code;

    @Column
    String Tag;

    @Column
    String Creator;

    @Column
    String nbfcBorrowerTransKey = "Pend";

    @Column
    String bankBorrowerTransKey = "Pend";

    @Column
    String loanType = "AGRI";

    @Column
    int tenureOfBusinessResidential = -1;

    @Column
    int tradeExperince = -1;

    @Column
    String skillCertified = "Pend";

    @Column
    String establishmentCertificate = "Pend";

    @Column
    String activityType = "Others";

    @Column
    String ITRfiling = "Pend";

    @Column
    String bankCif;

    @Column
    int bankAssessedLoanAmount = -1;

    @Column
    int bankTenure = -1;

    @Column
    double bankRateOfInterest = -1.0;

    @Column
    String educationStatus = "Pend";

    @Column
    String signatureThumb = "Pend";

    @Column
    String workStatus = "Pend";

    @Column
    String workLocation = "Pend";

    @Column
    String motherName;

    @Column
    String fatherName;

    @Column
    String ckycNumber = "Pend";

    @Column
    String ckycOccupationCode;

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    long FiID;

    public BorrowerExtraBank() {
    }

    public BorrowerExtraBank(String creator, String tag) {
        this.Tag = tag;
        this.Creator = creator;
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

    public String getNbfcBorrowerTransKey() {
        return nbfcBorrowerTransKey;
    }

    public void setNbfcBorrowerTransKey(String nbfcBorrowerTransKey) {
        this.nbfcBorrowerTransKey = nbfcBorrowerTransKey;
    }

    public String getBankBorrowerTransKey() {
        return bankBorrowerTransKey;
    }

    public void setBankBorrowerTransKey(String bankBorrowerTransKey) {
        this.bankBorrowerTransKey = bankBorrowerTransKey;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public int getTenureOfBusinessResidential() {
        return tenureOfBusinessResidential;
    }

    public void setTenureOfBusinessResidential(int tenureOfBusinessResidential) {
        this.tenureOfBusinessResidential = tenureOfBusinessResidential;
    }

    public int getTradeExperince() {
        return tradeExperince;
    }

    public void setTradeExperince(int tradeExperince) {
        this.tradeExperince = tradeExperince;
    }

    public String getSkillCertified() {
        return skillCertified;
    }

    public void setSkillCertified(String skillCertified) {
        this.skillCertified = skillCertified;
    }

    public String getEstablishmentCertificate() {
        return establishmentCertificate;
    }

    public void setEstablishmentCertificate(String establishmentCertificate) {
        this.establishmentCertificate = establishmentCertificate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getITRfiling() {
        return ITRfiling;
    }

    public void setITRfiling(String ITRfiling) {
        this.ITRfiling = ITRfiling;
    }

    public String getBankCif() {
        return bankCif;
    }

    public void setBankCif(String bankCif) {
        this.bankCif = bankCif;
    }

    public int getBankAssessedLoanAmount() {
        return bankAssessedLoanAmount;
    }

    public void setBankAssessedLoanAmount(int bankAssessedLoanAmount) {
        this.bankAssessedLoanAmount = bankAssessedLoanAmount;
    }

    public int getBankTenure() {
        return bankTenure;
    }

    public void setBankTenure(int bankTenure) {
        this.bankTenure = bankTenure;
    }

    public double getBankRateOfInterest() {
        return bankRateOfInterest;
    }

    public void setBankRateOfInterest(double bankRateOfInterest) {
        this.bankRateOfInterest = bankRateOfInterest;
    }

    public String getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(String educationStatus) {
        this.educationStatus = educationStatus;
    }

    public String getSignatureThumb() {
        return signatureThumb;
    }

    public void setSignatureThumb(String signatureThumb) {
        this.signatureThumb = signatureThumb;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getCkycNumber() {
        return ckycNumber;
    }

    public void setCkycNumber(String ckycNumber) {
        this.ckycNumber = ckycNumber;
    }

    public String getCkycOccupationCode() {
        return ckycOccupationCode;
    }

    public void setCkycOccupationCode(String ckycOccupationCode) {
        this.ckycOccupationCode = ckycOccupationCode;
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

    public BorrowerExtraBankDTO getExtraBankDTO() {
        BorrowerExtraBankDTO bankDTO = new BorrowerExtraBankDTO();
        bankDTO.setActivityType(this.activityType);
        bankDTO.setBankAssessedLoanAmount(this.bankAssessedLoanAmount);
        bankDTO.setBankBorrowerTransKey(this.bankBorrowerTransKey);
        bankDTO.setBankCif(this.bankCif);
        bankDTO.setBankRateOfInterest(this.bankRateOfInterest);
        bankDTO.setBankTenure(this.bankTenure);
        bankDTO.setCkycNumber(this.ckycNumber);
        bankDTO.setCkycOccupationCode(this.ckycOccupationCode);
        bankDTO.setCode(this.Code);
        bankDTO.setCreator(this.Creator);
        bankDTO.setEducationStatus(this.educationStatus);
        bankDTO.setEstablishmentCertificate(this.establishmentCertificate);
        bankDTO.setFatherName(this.fatherName);
        bankDTO.setITRfiling(this.ITRfiling);
        bankDTO.setLoanType(this.loanType);
        bankDTO.setMotherName(this.motherName);
        bankDTO.setNbfcBorrowerTransKey(this.nbfcBorrowerTransKey);
        bankDTO.setSignatureThumb(this.signatureThumb);
        bankDTO.setSkillCertified(this.skillCertified);
        bankDTO.setTag(this.Tag);
        bankDTO.setTenureOfBusinessResidential(this.tenureOfBusinessResidential);
        bankDTO.setTradeExperince(this.tradeExperince);
        bankDTO.setWorkLocation(this.workLocation);
        bankDTO.setWorkStatus(this.workStatus);
        return bankDTO;
    }

    public BorrowerExtraBank(BorrowerExtraBankDTO extraBankDTO) {
        this.setActivityType(extraBankDTO.getActivityType());
        this.setBankAssessedLoanAmount(extraBankDTO.getBankAssessedLoanAmount());
        this.setBankBorrowerTransKey(extraBankDTO.getBankBorrowerTransKey());
        this.setBankCif(extraBankDTO.getBankCif());
        this.setBankRateOfInterest(extraBankDTO.getBankRateOfInterest());
        this.setBankTenure(extraBankDTO.getBankTenure());
        this.setCkycNumber(extraBankDTO.getCkycNumber());
        this.setCkycOccupationCode(extraBankDTO.getCkycOccupationCode());
        this.setCode(extraBankDTO.getCode());
        this.setCreator(extraBankDTO.getCreator());
        this.setEducationStatus(extraBankDTO.getEducationStatus());
        this.setEstablishmentCertificate(extraBankDTO.getEstablishmentCertificate());
        this.setFatherName(extraBankDTO.getFatherName());
        this.setITRfiling(extraBankDTO.getITRfiling());
        this.setLoanType(extraBankDTO.getLoanType());
        this.setMotherName(extraBankDTO.getMotherName());
        this.setNbfcBorrowerTransKey(extraBankDTO.getNbfcBorrowerTransKey());
        this.setSignatureThumb(extraBankDTO.getSignatureThumb());
        this.setSkillCertified(extraBankDTO.getSkillCertified());
        this.setTag(extraBankDTO.getTag());
        this.setTenureOfBusinessResidential(extraBankDTO.getTenureOfBusinessResidential());
        this.setTradeExperince(extraBankDTO.getTradeExperince());
        this.setWorkLocation(extraBankDTO.getWorkLocation());
        this.setWorkStatus(extraBankDTO.getWorkStatus());
    }
}
