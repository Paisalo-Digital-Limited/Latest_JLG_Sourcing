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
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyMember;

import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterListBorrowerFamilyMembers extends ArrayAdapter<BorrowerFamilyMember> {
    Context context;
    int resourecId;
    List<BorrowerFamilyMember> familyList = null;

    public AdapterListBorrowerFamilyMembers(Context context, @LayoutRes int resource, @NonNull List<BorrowerFamilyMember> familyList) {
        super(context, resource, familyList);
        this.context = context;
        this.resourecId = resource;
        this.familyList = familyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        BorrowerFamilyMemberViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);

            holder = new BorrowerFamilyMemberViewHolder();
            holder.tvMemberName = (TextView) v.findViewById(R.id.tvFamilyName);
            holder.tvMemberGenderAge = (TextView) v.findViewById(R.id.tvFamilyGenderAge);
            holder.tvMemberHealth = (TextView) v.findViewById(R.id.tvFamilyHealth);
            holder.tvMemberEducation = (TextView) v.findViewById(R.id.tvFamilyEducation);
            holder.tvMemberSchoolType = (TextView) v.findViewById(R.id.tvFamilySchoolType);
            holder.tvMemberBusiness = (TextView) v.findViewById(R.id.tvFamilyBusiness);
            holder.tvMemberBusinessType = (TextView) v.findViewById(R.id.tvFamilyBusinessType);
            holder.tvMemberRelation = (TextView) v.findViewById(R.id.tvFamilyRelationship);
            holder.tvMemberIncome = (TextView) v.findViewById(R.id.tvFamilyIncome);
            holder.tvMemberIncomeType = (TextView) v.findViewById(R.id.tvFamilyIncomeType);

            v.setTag(holder);
        } else {
            holder = (BorrowerFamilyMemberViewHolder) v.getTag();
        }

        BorrowerFamilyMember family = familyList.get(position);
        holder.tvMemberName.setText(family.getMemName());
        holder.tvMemberGenderAge.setText(family.getGender() + "  " + String.valueOf(family.getAge()));
        holder.tvMemberHealth.setText(family.getHealth());
        holder.tvMemberEducation.setText(family.getEducatioin());
        holder.tvMemberSchoolType.setText(family.getSchoolType());
        holder.tvMemberBusiness.setText(family.getBusiness());
        holder.tvMemberBusinessType.setText(family.getBusinessType());
        holder.tvMemberRelation.setText(family.getRelationWBorrower());
        holder.tvMemberIncome.setText(String.valueOf(family.getIncome()));
        holder.tvMemberIncomeType.setText(family.getIncomeType());
        return v;
    }

    public class BorrowerFamilyMemberViewHolder {
        public TextView tvMemberName;
        public TextView tvMemberGenderAge;
        public TextView tvMemberHealth;
        public TextView tvMemberEducation;
        public TextView tvMemberSchoolType;
        public TextView tvMemberBusiness;
        public TextView tvMemberBusinessType;
        public TextView tvMemberRelation;
        public TextView tvMemberIncome;
        public TextView tvMemberIncomeType;
    }

}
