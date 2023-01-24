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
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.ViewHolders.BorrowerViewHolder;

import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterListBorrower extends ArrayAdapter<Borrower> {
    Context context;
    int resourecId;
    List<Borrower> borrowers = null;

    public AdapterListBorrower(Context context, @LayoutRes int resource, @NonNull List<Borrower> borrowers) {
        super(context, resource, borrowers);
        this.context = context;
        this.resourecId = resource;
        this.borrowers = borrowers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        BorrowerViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);

            holder = new BorrowerViewHolder();
            holder.tvName = (TextView) v.findViewById(R.id.itemLayoutCustomerName);
            holder.tvGurName = (TextView) v.findViewById(R.id.itemLayoutCustomerFather);
            holder.tvAadhar = (TextView) v.findViewById(R.id.itemLayoutCustomerAadhar);
            holder.tvMobile = (TextView) v.findViewById(R.id.itemLayoutCustomerMobile);
            holder.tvFiCode = (TextView) v.findViewById(R.id.itemLayoutCustomerCreator);

            v.setTag(holder);
        } else {
            holder = (BorrowerViewHolder) v.getTag();
        }

        Borrower borrower = borrowers.get(position);
        holder.tvName.setText(borrower.getBorrowerName());
        holder.tvGurName.setText(borrower.getGurName());
        holder.tvAadhar.setText(borrower.aadharid);
        holder.tvMobile.setText(borrower.P_ph3);
        holder.tvFiCode.setText(String.valueOf(borrower.Code) + " / " + borrower.Creator);
        return v;
    }

}
