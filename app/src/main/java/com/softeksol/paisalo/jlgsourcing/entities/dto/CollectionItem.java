package com.softeksol.paisalo.jlgsourcing.entities.dto;

public class CollectionItem {
    private String CaseCode;
    private long Dr;
    private String PartyName;

    public CollectionItem() {
    }

    public CollectionItem(String caseCode, long dr, String partyName) {
        CaseCode = caseCode;
        Dr = dr;
        PartyName = partyName;
    }

    public String getCaseCode() {
        return CaseCode;
    }

    public void setCaseCode(String caseCode) {
        CaseCode = caseCode;
    }

    public long getDr() {
        return Dr;
    }

    public void setDr(long dr) {
        Dr = dr;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }
}
