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
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyLoan;

import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterListBorrowerFamilyLoans extends ArrayAdapter<BorrowerFamilyLoan> {
    Context context;
    int resourecId;
    List<BorrowerFamilyLoan> familyLoans = null;

    public AdapterListBorrowerFamilyLoans(Context context, @LayoutRes int resource, @NonNull List<BorrowerFamilyLoan> familyLoans) {
        super(context, resource, familyLoans);
        this.context = context;
        this.resourecId = resource;
        this.familyLoans = familyLoans;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        BorrowerFamilyMemberViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);

            holder = new BorrowerFamilyMemberViewHolder();
            holder.tvLenderName = (TextView) v.findViewById(R.id.tvRunningLoanLenderName);
            holder.tvLenderType = (TextView) v.findViewById(R.id.tvRunningLoanLenderType);
            holder.tvIsMFI = (TextView) v.findViewById(R.id.tvRunningLoanLenderIsMFI);
            holder.tvLonee = (TextView) v.findViewById(R.id.tvRunningLoanLonee);
            holder.tvLoanReason = (TextView) v.findViewById(R.id.tvRunningLoanReason);
            holder.tvLoanAmount = (TextView) v.findViewById(R.id.tvRunningLoanAmount);
            holder.tvLoanEmi = (TextView) v.findViewById(R.id.tvRunningLoanEMI);
            holder.tvBalanceAmount = (TextView) v.findViewById(R.id.tvRunningLoanBalance);

            v.setTag(holder);
        } else {
            holder = (BorrowerFamilyMemberViewHolder) v.getTag();
        }

        BorrowerFamilyLoan loan = familyLoans.get(position);
        holder.tvLenderName.setText(loan.getLenderName());
        holder.tvLenderType.setText(loan.getLenderType());
        holder.tvIsMFI.setText(loan.getIsMFI().equals("Y") ? "MFI" : "Not MFI");
        holder.tvLonee.setText(loan.getLoneeName());
        holder.tvLoanReason.setText(loan.getLoanReason());
        holder.tvLoanAmount.setText("Amt: " + String.valueOf(loan.getLoanAmount()));
        holder.tvLoanEmi.setText("EMI: " + String.valueOf(loan.getLoanEMIAmount()));
        holder.tvBalanceAmount.setText("Bal: " + String.valueOf(loan.getLoanBalanceAmount()));
        return v;
    }

    public class BorrowerFamilyMemberViewHolder {
        public TextView tvLenderName;
        public TextView tvLenderType;
        public TextView tvIsMFI;
        public TextView tvLonee;
        public TextView tvLoanReason;
        public TextView tvLoanAmount;
        public TextView tvLoanEmi;
        public TextView tvBalanceAmount;
    }

}
