package com.softeksol.paisalo.jlgsourcing.entities;

public class Aadhar {
    private String CustomerID;
    private String UUID;
    private String Poiname;
    private String Poidob;
    private String Poigender;
    private String Poiphone;
    private String Poiemail;
    private String Poaco;
    private String Poadist;
    private String Poahouse;
    private String Poaloc;
    private String Poapc;
    private String Poastate;
    private String Poavtc;
    private String PoavtcCode;
    private String Poastreet;
    private String Poalm;
    private String Poasubdist;
    private String Poapo;
    private String Pht;
    private String CreatedDate;
    private String UserID;
    private String TxnID;
    private String RCode;
    private String RTimestamp;
    private String Photo;

    public Aadhar() {
    }

    public Aadhar(String customerID, String UUID, String poiname, String poidob, String poigender, String poiphone, String poiemail, String poaco, String poadist, String poahouse, String poaloc, String poapc, String poastate, String poavtc, String poavtcCode, String poastreet, String poalm, String poasubdist, String poapo, String pht, String createdDate, String userID, String txnID, String RCode, String RTimestamp, String photo) {
        CustomerID = customerID;
        this.UUID = UUID;
        Poiname = poiname;
        Poidob = poidob;
        Poigender = poigender;
        Poiphone = poiphone;
        Poiemail = poiemail;
        Poaco = poaco;
        Poadist = poadist;
        Poahouse = poahouse;
        Poaloc = poaloc;
        Poapc = poapc;
        Poastate = poastate;
        Poavtc = poavtc;
        PoavtcCode = poavtcCode;
        Poastreet = poastreet;
        Poalm = poalm;
        Poasubdist = poasubdist;
        Poapo = poapo;
        Pht = pht;
        CreatedDate = createdDate;
        UserID = userID;
        TxnID = txnID;
        this.RCode = RCode;
        this.RTimestamp = RTimestamp;
        Photo = photo;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPoiname() {
        return Poiname;
    }

    public void setPoiname(String poiname) {
        Poiname = poiname;
    }

    public String getPoidob() {
        return Poidob;
    }

    public void setPoidob(String poidob) {
        Poidob = poidob;
    }

    public String getPoigender() {
        return Poigender;
    }

    public void setPoigender(String poigender) {
        Poigender = poigender;
    }

    public String getPoiphone() {
        return Poiphone;
    }

    public void setPoiphone(String poiphone) {
        Poiphone = poiphone;
    }

    public String getPoiemail() {
        return Poiemail;
    }

    public void setPoiemail(String poiemail) {
        Poiemail = poiemail;
    }

    public String getPoaco() {
        return Poaco;
    }

    public void setPoaco(String poaco) {
        Poaco = poaco;
    }

    public String getPoadist() {
        return Poadist;
    }

    public void setPoadist(String poadist) {
        Poadist = poadist;
    }

    public String getPoahouse() {
        return Poahouse;
    }

    public void setPoahouse(String poahouse) {
        Poahouse = poahouse;
    }

    public String getPoaloc() {
        return Poaloc;
    }

    public void setPoaloc(String poaloc) {
        Poaloc = poaloc;
    }

    public String getPoapc() {
        return Poapc;
    }

    public void setPoapc(String poapc) {
        Poapc = poapc;
    }

    public String getPoastate() {
        return Poastate;
    }

    public void setPoastate(String poastate) {
        Poastate = poastate;
    }

    public String getPoavtc() {
        return Poavtc;
    }

    public void setPoavtc(String poavtc) {
        Poavtc = poavtc;
    }

    public String getPoavtcCode() {
        return PoavtcCode;
    }

    public void setPoavtcCode(String poavtcCode) {
        PoavtcCode = poavtcCode;
    }

    public String getPoastreet() {
        return Poastreet;
    }

    public void setPoastreet(String poastreet) {
        Poastreet = poastreet;
    }

    public String getPoalm() {
        return Poalm;
    }

    public void setPoalm(String poalm) {
        Poalm = poalm;
    }

    public String getPoasubdist() {
        return Poasubdist;
    }

    public void setPoasubdist(String poasubdist) {
        Poasubdist = poasubdist;
    }

    public String getPoapo() {
        return Poapo;
    }

    public void setPoapo(String poapo) {
        Poapo = poapo;
    }

    public String getPht() {
        return Pht;
    }

    public void setPht(String pht) {
        Pht = pht;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getTxnID() {
        return TxnID;
    }

    public void setTxnID(String txnID) {
        TxnID = txnID;
    }

    public String getRCode() {
        return RCode;
    }

    public void setRCode(String RCode) {
        this.RCode = RCode;
    }

    public String getRTimestamp() {
        return RTimestamp;
    }

    public void setRTimestamp(String RTimestamp) {
        this.RTimestamp = RTimestamp;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    @Override
    public String toString() {
        return "Aadhar{" +
                "CustomerID='" + CustomerID + '\'' +
                ", UUID='" + UUID + '\'' +
                ", Poiname='" + Poiname + '\'' +
                ", Poidob='" + Poidob + '\'' +
                ", Poigender='" + Poigender + '\'' +
                ", Poiphone='" + Poiphone + '\'' +
                ", Poiemail='" + Poiemail + '\'' +
                ", Poaco='" + Poaco + '\'' +
                ", Poadist='" + Poadist + '\'' +
                ", Poahouse='" + Poahouse + '\'' +
                ", Poaloc='" + Poaloc + '\'' +
                ", Poapc='" + Poapc + '\'' +
                ", Poastate='" + Poastate + '\'' +
                ", Poavtc='" + Poavtc + '\'' +
                ", PoavtcCode='" + PoavtcCode + '\'' +
                ", Poastreet='" + Poastreet + '\'' +
                ", Poalm='" + Poalm + '\'' +
                ", Poasubdist='" + Poasubdist + '\'' +
                ", Poapo='" + Poapo + '\'' +
                ", Pht='" + Pht + '\'' +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", UserID='" + UserID + '\'' +
                ", TxnID='" + TxnID + '\'' +
                ", RCode='" + RCode + '\'' +
                ", RTimestamp='" + RTimestamp + '\'' +
                ", Photo='" + Photo + '\'' +
                '}';
    }
}
