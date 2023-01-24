package com.softeksol.paisalo.jlgsourcing.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.ViewHolders.BorrowerRecViewHolder;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentLoanAppList;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentLoanAppSubmitList;

import java.util.List;

/**
 * Created by sachindra on 2016-10-01.
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link Borrower} and makes a call to the
 * specified {@link FragmentLoanAppList.OnListFragmentLoanAppInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

public class AdapterRecViewLoanApplication extends RecyclerView.Adapter<BorrowerRecViewHolder> {

    private List<Borrower> mValues;
    private final FragmentLoanAppList.OnListFragmentLoanAppInteractionListener mListener;
    private final FragmentLoanAppSubmitList.OnListFragmentLoanAppSubmitInteractionListener mListener1;

    public AdapterRecViewLoanApplication(List<Borrower> items, FragmentLoanAppList.OnListFragmentLoanAppInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mListener1 = null;
    }

    public AdapterRecViewLoanApplication(List<Borrower> items, FragmentLoanAppSubmitList.OnListFragmentLoanAppSubmitInteractionListener listener) {
        mValues = items;
        mListener = null;
        mListener1 = listener;
    }

    @Override
    public BorrowerRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_loan_app_info, parent, false);
        return new BorrowerRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BorrowerRecViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.mItem = mValues.get(position);
        holder.mFiId.setText(String.valueOf(mValues.get(position).Code) + "(" + String.valueOf(mValues.get(position).FiID) + ")");
        holder.mCreator.setText(mValues.get(position).Creator);
        holder.mAddress.setText(mValues.get(position).getPerAddress());
        holder.mMobile.setText(mValues.get(position).P_ph3);
        holder.mBorrowerName.setText(mValues.get(position).getBorrowerName());
        holder.mBorrowerGuardian.setText(mValues.get(position).getGurName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an documentStore has been selected.
                    mListener.onListFragmentLoanAppInteraction(holder.mItem, position);
                }
                if (null != mListener1) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an documentStore has been selected.
                    mListener1.onListFragmentLoanAppSubmitInteraction(holder.mItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateList(List<Borrower> data) {
        mValues = data;
        notifyDataSetChanged();
    }

}
