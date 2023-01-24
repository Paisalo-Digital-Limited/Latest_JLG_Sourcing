package com.softeksol.paisalo.jlgsourcing.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.dto.DocumentListHeader;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sachindra on 1/2/2017.
 */
public class AdapterExpandableListDocuments extends BaseExpandableListAdapter {

    private Context _context;
    private List<DocumentListHeader> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<DocumentListHeader, List<DocumentStore>> _listDataChild;

    public AdapterExpandableListDocuments(Context context, List<DocumentListHeader> listDataHeader,
                                          HashMap<DocumentListHeader, List<DocumentStore>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_item_loan_app_kyc_upload_head, null);
        }

        TextView lbBorrowername = (TextView) convertView.findViewById(R.id.tvKycItemLayoutName);
        //lbBorrowername.setTypeface(null, Typeface.BOLD);
        lbBorrowername.setText(headerTitle);
        TextView lbFiCode = (TextView) convertView.findViewById(R.id.tvKycItemLayoutFiCode);
        lbFiCode.setTypeface(null, Typeface.BOLD);
        lbFiCode.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_item_loan_app_kyc_upload_child, null);
        }

        //TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        //txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


}
