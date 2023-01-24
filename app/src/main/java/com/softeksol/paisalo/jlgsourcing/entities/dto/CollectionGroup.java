package com.softeksol.paisalo.jlgsourcing.entities.dto;

import com.softeksol.paisalo.jlgsourcing.interfaces.ParentListItem;

import java.util.ArrayList;
import java.util.List;

public class CollectionGroup implements ParentListItem {
    private String name;
    private long total;
    private ArrayList<CollectionItem> collectionItems;

    public CollectionGroup() {
    }

    public CollectionGroup(String name, ArrayList<CollectionItem> collectionItems) {
        this.name = name;
        this.collectionItems = collectionItems;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CollectionItem> getCollectionItems() {
        return collectionItems;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean addCollectionItem(CollectionItem collectionItem) {
        boolean retVal = false;
        if (collectionItems != null)
            retVal = collectionItems.add(collectionItem);
        return retVal;
    }

    public boolean clearCollectionItems() {
        boolean retVal = false;
        if (collectionItems != null) {
            collectionItems.clear();
            retVal = true;
        }
        return retVal;
    }

    public static long getGroupsTotal(List<CollectionGroup> collectionGroups) {
        long retVal = 0;
        for (CollectionGroup collectionGroup : collectionGroups) {
            retVal += collectionGroup.getTotal();
        }
        return retVal;
    }

    @Override
    public List<CollectionItem> getChildItemList() {
        return collectionItems;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
