package com.softeksol.paisalo.jlgsourcing.entities.RequestParameters;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by sachindra on 5/23/2017.
 */
public class ESingAuthRequestParms implements Serializable {
    @Expose
    public int FICode;

    @Expose
    public String Creator;

    @Expose
    public String CustName;

    @Expose
    public String Reason;

    @Expose
    public long AadhorNo;

    @Expose
    public long GAadhorNo;

    @Expose
    public int GrNo;

    @Expose
    public String OTPBioMetricData;

    @Expose
    public String AuthMethod;    // FP / BIO / OTP

    @Expose
    public String UserID;

    @Expose
    public String Concent;

    @Expose
    public String Uniquedevicecode;

    @Expose
    public String TerminalID;

    @Expose
    public String PdfToSign;
}

