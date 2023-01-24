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
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.entities.PosInstRcv;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterInstSettlementData extends ArrayAdapter<PosInstRcv> implements Filterable {
    Context context;
    int resourecId;
    List<PosInstRcv> originalData;
    List<PosInstRcv> filteredData = null;
    ValueFilter filter = new ValueFilter();


    public AdapterInstSettlementData(Context context, @LayoutRes int resource, @NonNull List<PosInstRcv> posInstSettlements) {
        super(context, resource, posInstSettlements);
        this.context = context;
        this.resourecId = resource;
        this.originalData = posInstSettlements;
        this.filteredData = posInstSettlements;
    }

    public int getCount() {
        return filteredData.size();
    }

    public PosInstRcv getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        BorrowerViewHolder holder;
        Drawable bgColor = null;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);
            bgColor = v.getBackground();
            holder = new BorrowerViewHolder();
            holder.tvName = (TextView) v.findViewById(R.id.itemLayoutSettleName);
            holder.tvMobile = (TextView) v.findViewById(R.id.itemLayoutSettleMobile);
            holder.tvCaseCode = (TextView) v.findViewById(R.id.itemLayoutSettleCaseCode);
            holder.tvReceiptDate = (TextView) v.findViewById(R.id.itemLayoutSettleDate);
            holder.tvReceiptAmt = (TextView) v.findViewById(R.id.itemLayoutSettleAmount);
            v.setTag(holder);
        } else {
            holder = (BorrowerViewHolder) v.getTag();
        }

        final PosInstRcv posInstSettlement = this.filteredData.get(position);
        holder.tvName.setText(posInstSettlement.getCustName());
        holder.tvCaseCode.setText(posInstSettlement.getCaseCode());
        holder.tvMobile.setText(posInstSettlement.getSmsMobNo());
        holder.tvReceiptAmt.setText(Utils.getNotNullString(posInstSettlement.getInstRcvAmt()));
        holder.tvReceiptDate.setText(DateUtils.getFormatedDate(posInstSettlement.getCreationDate(), "dd-MM-yyyy"));
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

            final List<PosInstRcv> list = originalData;
            if (constraint != null && constraint.length() > 0) {
                List<PosInstRcv> filterList = new ArrayList<>();
                for (PosInstRcv dueData : list) {
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
            filteredData = (ArrayList<PosInstRcv>) results.values;
            notifyDataSetChanged();
        }
    }

    public class BorrowerViewHolder {
        public TextView tvName;
        public TextView tvMobile;
        public TextView tvCaseCode;
        public TextView tvReceiptDate;
        public TextView tvReceiptAmt;
    }

}
