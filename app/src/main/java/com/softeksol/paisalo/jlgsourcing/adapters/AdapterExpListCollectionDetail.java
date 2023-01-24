package com.softeksol.paisalo.jlgsourcing.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.dto.CollectionGroup;
import com.softeksol.paisalo.jlgsourcing.entities.dto.CollectionItem;

import java.util.List;

public class AdapterExpListCollectionDetail extends BaseExpandableListAdapter {

    private Context _context;
    private List<CollectionGroup> _listDataHeader; // header titles

    public AdapterExpListCollectionDetail(Context context, List<CollectionGroup> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CollectionGroup group = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_item_collection_group, null);
        }
        ((TextView) convertView.findViewById(R.id.tvCollectionGroup)).setText(group.getName());
        ((TextView) convertView.findViewById(R.id.tvCollectionGroupTotal)).setText(group.getTotal() + "");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CollectionItem collectionItem = getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_item_collection_detail, null);
        }

        ((TextView) convertView.findViewById(R.id.tvCollectionParty)).setText(collectionItem.getPartyName());
        ((TextView) convertView.findViewById(R.id.tvCollectionCase)).setText(collectionItem.getCaseCode());
        ((TextView) convertView.findViewById(R.id.tvCollectionAmount)).setText(collectionItem.getDr() + "");
        return convertView;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public CollectionItem getChild(int groupPosition, int childPosititon) {
        return this._listDataHeader.get(groupPosition).getChildItemList().get(childPosititon);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getChildItemList().size();
    }

    @Override
    public CollectionGroup getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void updaateData(List<CollectionGroup> groupList) {
        this._listDataHeader = groupList;
        this.notifyDataSetChanged();
    }

    public void clearData() {
        this._listDataHeader.clear();
        this.notifyDataSetChanged();
    }
}