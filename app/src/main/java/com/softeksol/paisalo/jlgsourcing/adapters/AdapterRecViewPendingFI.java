package com.softeksol.paisalo.jlgsourcing.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.entities.PendingFi;
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

public class AdapterRecViewPendingFI extends RecyclerView.Adapter<AdapterRecViewPendingFI.PendingFiRecViewHolder> {

    private List<PendingFi> mValues;
    private final FragmentLoanAppList.OnListFragmentLoanAppInteractionListener mListener;
    Context context;

    public AdapterRecViewPendingFI(List<PendingFi> items, FragmentLoanAppList.OnListFragmentLoanAppInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context=context;
    }

    @Override
    public PendingFiRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_loan_app_info, parent, false);
        return new PendingFiRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PendingFiRecViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.mItem = mValues.get(position);
        holder.mFiId.setText(String.valueOf(mValues.get(position).Code));
        holder.mCreator.setText(mValues.get(position).Creator);
        holder.mAddress.setText(mValues.get(position).Addr);
        holder.mMobile.setText(mValues.get(position).P_ph3);
        holder.mBorrowerName.setText(mValues.get(position).getBorrowerName());
        holder.mBorrowerGuardian.setText(mValues.get(position).getGurName());

        /*holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.mItem.
                return false;
            }
        });*/
        if (Integer.parseInt(Utils.NullIf(mValues.get(position).SEL, "0")) > 0) {
            holder.mainCardBG.setBackgroundColor(context.getResources().getColor(R.color.colorLightGreen));
        } else {
            holder.mainCardBG.setBackgroundColor(context.getResources().getColor(R.color.zxing_transparent));
        }
//        Log.d("checkStatus",mValues.get(position).BorrLoanAppSignStatus + "");
//        Log.d("checkApproved",holder.mItem.Approved + "");
        if (Utils.NullIf(mValues.get(position).BorrLoanAppSignStatus, "").equals("Y") && Utils.NullIf(holder.mItem.Approved, "NPR").equals("NPR")) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onListFragmentLoanAppInteraction(holder.mItem, position);
                    }
                }
            });
        } else {
            holder.mView.setOnClickListener(null);
            holder.mainCardBG.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateList(List<PendingFi> data) {
        mValues = data;
        notifyDataSetChanged();
    }

    class PendingFiRecViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mBorrowerName;
        public final TextView mBorrowerGuardian;
        public final TextView mAddress;
        public final TextView mFiId;
        public final TextView mMobile;
        public final TextView mCreator;
        public final ConstraintLayout mainCardBG;

        public PendingFi mItem;

        public PendingFiRecViewHolder(View view) {
            super(view);
            mView = view;
            mBorrowerName = (TextView) view.findViewById(R.id.itemLayoutCustomerName);
            mBorrowerGuardian = (TextView) view.findViewById(R.id.itemLayoutCustomerFather);
            mAddress = (TextView) view.findViewById(R.id.itemLayoutCustomerAddress);
            mFiId = (TextView) view.findViewById(R.id.itemLayoutCustomerFiId);
            mMobile = (TextView) view.findViewById(R.id.itemLayoutCustomerMobile);
            mCreator = (TextView) view.findViewById(R.id.itemLayoutCustomerCreator);
            mainCardBG = (ConstraintLayout) view.findViewById(R.id.mainCardBG);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBorrowerName.getText() + " " + mBorrowerGuardian.getText() + "'";
        }

    }
}
