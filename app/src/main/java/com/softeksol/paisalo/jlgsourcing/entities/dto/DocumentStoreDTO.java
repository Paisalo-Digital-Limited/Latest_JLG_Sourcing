package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by sachindra on 2/20/2017.
 */
public class DocumentStoreDTO {
    @Expose
    public
    String Creator;

    @Expose
    public
    long FICode;

    @Expose
    public
    String Tag;

    @Expose
    public
    String ImageTag;

    @Expose
    public
    int GrNo;

    @Expose
    public
    String DocRemark;

    @Expose
    public
    long ChecklistID;

    @Expose
    public
    String UserID;

    @Expose
    public
    String timestamp;

    @Expose
    public
    float latitude;

    @Expose
    public
    float longitude;

    @Expose
    public
    String Document;

    @Override
    public String toString() {
        return "DocumentStoreDTO{" +
                "Creator='" + Creator + '\'' +
                ", FICode=" + FICode +
                ", Tag='" + Tag + '\'' +
                ", ImageTag='" + ImageTag + '\'' +
                ", GrNo=" + GrNo +
                ", DocRemark='" + DocRemark + '\'' +
                ", ChecklistID=" + ChecklistID +
                ", UserID='" + UserID + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", Document='" + Document + '\'' +
                '}';
    }
}
