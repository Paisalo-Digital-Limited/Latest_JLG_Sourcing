package com.softeksol.paisalo.jlgsourcing.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.DueData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterDueData extends ArrayAdapter<DueData> implements Filterable {
    Context context;
    int resourecId;
    List<DueData> originalData;
    List<DueData> filteredData = null;
    ValueFilter filter = new ValueFilter();


    public AdapterDueData(Context context, @LayoutRes int resource, @NonNull List<DueData> dueDataList) {
        super(context, resource, dueDataList);
        this.context = context;
        this.resourecId = resource;
        this.originalData = dueDataList;
        this.filteredData = dueDataList;
    }

    public int getCount() {
        return filteredData.size();
    }

    public DueData getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return filteredData.get(position).isEnabled();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        BorrowerViewHolder holder;
        Drawable bgColor = null;
        final DueData dueData = this.filteredData.get(position);
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);
            bgColor = v.getBackground();
            holder = new BorrowerViewHolder();
            holder.tvName = (TextView) v.findViewById(R.id.itemLayoutCustomerName);
            holder.tvGurName = (TextView) v.findViewById(R.id.itemLayoutCustomerFather);
            holder.tvMobile = (TextView) v.findViewById(R.id.itemLayoutCustomerMobile);
            holder.tvCreator = (TextView) v.findViewById(R.id.itemLayoutCustomerCreator);
            holder.tvAddress = (TextView) v.findViewById(R.id.itemLayoutCustomerAddress);
            holder.tvFiCode = (TextView) v.findViewById(R.id.itemLayoutCustomerFiId);
            holder.imgBtnEkyc = (ImageButton) v.findViewById(R.id.itemLayoutCustomerEkyc);
            holder.mainCardBG = (ConstraintLayout) v.findViewById(R.id.mainCardBG);
            v.setTag(holder);
        } else {
            holder = (BorrowerViewHolder) v.getTag();
        }
        holder.tvName.setText(dueData.getCustName());
        holder.tvGurName.setText(dueData.getFHName());
        holder.tvMobile.setText(dueData.getMobile());
        holder.tvCreator.setText(dueData.getCaseCode() + " / " + dueData.getCreator());
        holder.tvAddress.setText(dueData.getAddress());
        holder.tvFiCode.setText("Tot:" + dueData.getInstallmentSum(false));
        //holder.imgBtnEkyc.setVisibility(dueData.hasAadhar() ? View.VISIBLE : View.GONE);
        holder.imgBtnEkyc.setVisibility(View.GONE);
        /*if (dueData.hasAadhar()) {
            holder.imgBtnEkyc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityCollection) context).performEKyc(dueData);
                }
            });
        }*/
        if (dueData.getIsNachReg().equals("Y")) {
           holder.mainCardBG.setBackgroundColor(context.getResources().getColor(R.color.colorNachBg));
        } else {
            holder.mainCardBG.setBackground(bgColor);
        }
        holder.mainCardBG.setEnabled(dueData.isEnabled());
        return v;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String filterString = constraint.toString().toUpperCase();

            final List<DueData> list = originalData;
            if (constraint != null && constraint.length() > 0) {
                List<DueData> filterList = new ArrayList<>();
                for (DueData dueData : list) {
                    if (dueData.getCustName().toUpperCase().contains(filterString)
                            || dueData.getCaseCode().toUpperCase().contains(filterString)) {
                        filterList.add(dueData);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = list.size();
                results.values = list;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<DueData>) results.values;
            notifyDataSetChanged();
        }


    }

    public class BorrowerViewHolder {
        public TextView tvName;
        public TextView tvGurName;
        public TextView tvMobile;
        public TextView tvFiCode;
        public TextView tvAddress;
        public TextView tvCreator;
        public ImageButton imgBtnEkyc;
        public ConstraintLayout mainCardBG;
    }

}
