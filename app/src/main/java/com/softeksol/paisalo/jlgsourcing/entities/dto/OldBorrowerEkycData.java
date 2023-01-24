package com.softeksol.paisalo.jlgsourcing.entities.dto;

import java.util.Comparator;

public class OldBorrowerEkycData {
    public static Comparator<OldBorrowerEkycData> OldBorrowerEkycDataName = new Comparator<OldBorrowerEkycData>() {
        public int compare(OldBorrowerEkycData dueData1, OldBorrowerEkycData dueData2) {
            int compareName = dueData1.Name.compareTo(dueData2.Name);
            int compareCase = dueData1.SmCode.compareTo(dueData2.SmCode);
            return ((compareName == 0) ? compareCase : compareName);
        }
    };
    private String Code;
    private String Creator;
    private String Name;
    private String GurName;
    private String UniqID;
    private String Addr;
    private String MobileNo;
    private String GroupCo;
    private String PartyCd;
    private String SmCode;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGurName() {
        return GurName;
    }

    public void setGurName(String gurName) {
        GurName = gurName;
    }

    public String getUniqID() {
        return UniqID;
    }

    public void setUniqID(String uniqID) {
        UniqID = uniqID;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getGroupCo() {
        return GroupCo;
    }

    public void setGroupCo(String groupCo) {
        GroupCo = groupCo;
    }

    public String getPartyCd() {
        return PartyCd;
    }

    public void setPartyCd(String partyCd) {
        PartyCd = partyCd;
    }

    public String getSmCode() {
        return SmCode;
    }

    public void setSmCode(String smCode) {
        SmCode = smCode;
    }
}
