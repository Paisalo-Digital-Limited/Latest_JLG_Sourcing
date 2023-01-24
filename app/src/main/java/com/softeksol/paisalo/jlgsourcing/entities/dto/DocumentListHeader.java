package com.softeksol.paisalo.jlgsourcing.entities.dto;

/**
 * Created by sachindra on 2/18/2017.
 */
public class DocumentListHeader {
    private String borrowerName;
    private String fiCode;

    public DocumentListHeader() {
    }

    public DocumentListHeader(String borrowerName, String fiCode) {
        this.borrowerName = borrowerName;
        this.fiCode = fiCode;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getFiCode() {
        return fiCode;
    }

    public void setFiCode(String fiCode) {
        this.fiCode = fiCode;
    }

}
