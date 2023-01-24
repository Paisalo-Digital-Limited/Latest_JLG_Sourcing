package com.softeksol.paisalo.jlgsourcing.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.PendingFi;
import com.softeksol.paisalo.jlgsourcing.entities.dto.PreclosureCaseDTO;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentLoanAppList;

import java.util.List;

/**
 * Created by sachindra on 2016-10-01.
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link PendingFi} and makes a call to the
 * specified {@link FragmentLoanAppList.OnListFragmentLoanAppInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

public class AdapterRecViewPreclosureCases extends RecyclerView.Adapter<AdapterRecViewPreclosureCases.PreclosureCaseRecViewHolder> {

    private List<PreclosureCaseDTO> mValues;
    private final OnPreclosureCaseClickListener mListener;

    public AdapterRecViewPreclosureCases(List<PreclosureCaseDTO> items, OnPreclosureCaseClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public PreclosureCaseRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_loan_app_info, parent, false);
        return new PreclosureCaseRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PreclosureCaseRecViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mFiId.setText(String.valueOf(mValues.get(position).getCaseCode()));
        holder.mCreator.setText(mValues.get(position).getCreator());
        holder.mAddress.setText(mValues.get(position).getAddress());
        holder.mMobile.setText(mValues.get(position).getMobile());
        holder.mBorrowerName.setText(mValues.get(position).getCustName());
        holder.mBorrowerGuardian.setText(mValues.get(position).getFHName());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateList(List<PreclosureCaseDTO> data) {
        mValues = data;
        notifyDataSetChanged();
    }

    class PreclosureCaseRecViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mBorrowerName;
        public final TextView mBorrowerGuardian;
        public final TextView mAddress;
        public final TextView mFiId;
        public final TextView mMobile;
        public final TextView mCreator;

        public PreclosureCaseDTO mItem;

        public PreclosureCaseRecViewHolder(View view) {
            super(view);
            mView = view;
            view.setOnClickListener(this);
            mBorrowerName = (TextView) view.findViewById(R.id.itemLayoutCustomerName);
            mBorrowerGuardian = (TextView) view.findViewById(R.id.itemLayoutCustomerFather);
            mAddress = (TextView) view.findViewById(R.id.itemLayoutCustomerAddress);
            mFiId = (TextView) view.findViewById(R.id.itemLayoutCustomerFiId);
            mMobile = (TextView) view.findViewById(R.id.itemLayoutCustomerMobile);
            mCreator = (TextView) view.findViewById(R.id.itemLayoutCustomerCreator);
            view.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBorrowerName.getText() + " " + mBorrowerGuardian.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            mListener.onPreclosureCaseInteraction(this.mItem);
        }
    }

    public interface OnPreclosureCaseClickListener {
        void onPreclosureCaseInteraction(PreclosureCaseDTO borrower);
    }

}
