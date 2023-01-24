package com.softeksol.paisalo.jlgsourcing.entities.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionData {
    private String Creator;
    private String CaseCode;
    private long Dr;
    private String PartyName;
    private String GroupCode;
    private String BranchCode;

    public static Comparator<CollectionData> sortByCreatorBranchGroup = new Comparator<CollectionData>() {
        public int compare(CollectionData collectionData1, CollectionData collectionData2) {

            int compareCreator = collectionData1.Creator.compareTo(collectionData2.Creator);
            int compareBranch = collectionData1.BranchCode.compareTo(collectionData2.BranchCode);
            int compareGroup = collectionData1.GroupCode.compareTo(collectionData2.GroupCode);
            int compareParty = collectionData1.PartyName.compareTo(collectionData2.PartyName);

            return (compareCreator == 0) ? ((compareBranch == 0) ? ((compareGroup == 0) ? compareParty : compareGroup) : compareBranch) : compareCreator;
        }
    };

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
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

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getGroupName() {
        return Creator + " / " + BranchCode + " / " + GroupCode;
    }


    private CollectionItem getCollectionItem() {
        return new CollectionItem(this.CaseCode, this.Dr, this.PartyName);
    }

    public static ArrayList<CollectionGroup> getCollectionGroups(List<CollectionData> collectionData) {
        ArrayList<CollectionData> dataArrayList = new ArrayList<>(collectionData);
        Collections.sort(dataArrayList, sortByCreatorBranchGroup);
        ArrayList<CollectionGroup> collectionGroups = new ArrayList<>();
        String grpName = "";
        long grpTotal = 0;
        CollectionGroup group = null;
        for (CollectionData collectionData1 : dataArrayList) {
            if (!grpName.equals(collectionData1.getGroupName())) {
                grpName = collectionData1.getGroupName();
                grpTotal = 0;
                group = new CollectionGroup(grpName, new ArrayList<CollectionItem>());
                collectionGroups.add(group);
            }
            CollectionItem collectionItem = collectionData1.getCollectionItem();
            grpTotal += collectionItem.getDr();
            group.addCollectionItem(collectionItem);
            group.setTotal(grpTotal);
        }
        return collectionGroups;
    }

    @Override
    public String toString() {
        return "CollectionData{" +
                "Creator='" + Creator + '\'' +
                ", CaseCode='" + CaseCode + '\'' +
                ", Dr=" + Dr +
                ", PartyName='" + PartyName + '\'' +
                ", GroupCode='" + GroupCode + '\'' +
                ", BranchCode='" + BranchCode + '\'' +
                '}';
    }
}
