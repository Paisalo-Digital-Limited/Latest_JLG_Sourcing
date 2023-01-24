package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.Expose;

public class BorrowerExtraBankDTO {
    @Expose
    private long Code;

    @Expose
    private String Creator;

    @Expose
    private String Tag;

    @Expose
    private String nbfcBorrowerTransKey;

    @Expose
    private String bankBorrowerTransKey;

    @Expose
    private String loanType;

    @Expose
    private int tenureOfBusinessResidential;

    @Expose
    private int tradeExperince;

    @Expose
    private String skillCertified;

    @Expose
    private String establishmentCertificate;

    @Expose
    private String activityType;

    @Expose
    private String ITRfiling;

    @Expose
    private String bankCif;

    @Expose
    private int bankAssessedLoanAmount;

    @Expose
    private int bankTenure;

    @Expose
    private double bankRateOfInterest;

    @Expose
    private String educationStatus;

    @Expose
    private String signatureThumb;

    @Expose
    private String workStatus;

    @Expose
    private String workLocation;

    @Expose
    private String motherName;

    @Expose
    private String fatherName;

    @Expose
    private String ckycNumber;

    @Expose
    private String ckycOccupationCode;

    public long getCode() {
        return Code;
    }

    public void setCode(long code) {
        Code = code;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
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
}
