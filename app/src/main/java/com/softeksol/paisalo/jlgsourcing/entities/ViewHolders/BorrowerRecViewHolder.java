package com.softeksol.paisalo.jlgsourcing.entities.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;

/**
 * Created by sachindra on 5/24/2017.
 */
public class BorrowerRecViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mBorrowerName;
    public final TextView mBorrowerGuardian;
    public final TextView mAddress;
    public final TextView mFiId;
    public final TextView mMobile;
    public final TextView mCreator;
    public final ConstraintLayout mainCardBG;

    public Borrower mItem;

    public BorrowerRecViewHolder(View view) {
        super(view);
        mView = view;
        mBorrowerName = (TextView) view.findViewById(R.id.itemLayoutCustomerName);
        mBorrowerGuardian = (TextView) view.findViewById(R.id.itemLayoutCustomerFather);
        mAddress = (TextView) view.findViewById(R.id.itemLayoutCustomerAddress);
        mFiId = (TextView) view.findViewById(R.id.itemLayoutCustomerFiId);
        mMobile = (TextView) view.findViewById(R.id.itemLayoutCustomerMobile);
        mCreator = (TextView) view.findViewById(R.id.itemLayoutCustomerCreator);
        mainCardBG =  view.findViewById(R.id.mainCardBG);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mBorrowerName.getText() + " " + mBorrowerGuardian.getText() + "'";
    }
}