package com.softeksol.paisalo.jlgsourcing.entities.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;

/**
 * Created by sachindra on 5/24/2017.
 */
public class GuarantorRecViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mGuarantorName;
    public final TextView mGuarantorGuardian;
    public final TextView mAddress;
    public final TextView mFiId;
    public final TextView mMobile;
    public final TextView mCreator;

    public Guarantor mItem;

    public GuarantorRecViewHolder(View view) {
        super(view);
        mView = view;
        mGuarantorName = (TextView) view.findViewById(R.id.itemLayoutCustomerName);
        mGuarantorGuardian = (TextView) view.findViewById(R.id.itemLayoutCustomerFather);
        mAddress = (TextView) view.findViewById(R.id.itemLayoutCustomerAddress);
        mFiId = (TextView) view.findViewById(R.id.itemLayoutCustomerFiId);
        mMobile = (TextView) view.findViewById(R.id.itemLayoutCustomerMobile);
        mCreator = (TextView) view.findViewById(R.id.itemLayoutCustomerCreator);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mGuarantorName.getText() + " " + mGuarantorGuardian.getText() + "'";
    }
}