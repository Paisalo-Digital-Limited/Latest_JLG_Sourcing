package com.softeksol.paisalo.jlgsourcing.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.entities.ReceiptData;

import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterReceiptData extends ArrayAdapter<ReceiptData> {
    Context context;
    int resourecId;
    List<ReceiptData> receiptDataList = null;

    public AdapterReceiptData(Context context, @LayoutRes int resource, @NonNull List<ReceiptData> receiptDataList) {
        super(context, resource, receiptDataList);
        this.context = context;
        this.resourecId = resource;
        this.receiptDataList = receiptDataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ReceiptDataViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);

            holder = new ReceiptDataViewHolder();
            holder.caseCode = (TextView) v.findViewById(R.id.tvLyRecCaseCode);
            holder.receiptDate = (TextView) v.findViewById(R.id.tvLyRecReceiptDate);
            holder.receiptAmount = (TextView) v.findViewById(R.id.tvLyRecReceiptAmount);

            v.setTag(holder);
        } else {
            holder = (ReceiptDataViewHolder) v.getTag();
        }

        ReceiptData receiptData = this.receiptDataList.get(position);
        holder.caseCode.setText(receiptData.getCaseCode());
        holder.receiptDate.setText(DateUtils.getFormatedDate(receiptData.getCreationDate(), "dd-MM-yyyy"));
        holder.receiptAmount.setText(String.valueOf(receiptData.getInstRcvAmt()));
        return v;
    }

    public int getReceiptSum() {
        int receiptSum = 0;
        for (ReceiptData receiptData : receiptDataList) {
            receiptSum += receiptData.getInstRcvAmt();
        }
        return receiptSum;
    }

    class ReceiptDataViewHolder {
        private TextView caseCode;
        private TextView receiptDate;
        private TextView receiptAmount;

        public TextView getCaseCode() {
            return caseCode;
        }

        public void setCaseCode(TextView caseCode) {
            this.caseCode = caseCode;
        }

        public TextView getReceiptDate() {
            return receiptDate;
        }

        public void setReceiptDate(TextView receiptDate) {
            this.receiptDate = receiptDate;
        }

        public TextView getReceiptAmount() {
            return receiptAmount;
        }

        public void setReceiptAmount(TextView receiptAmount) {
            this.receiptAmount = receiptAmount;
        }
    }
}
