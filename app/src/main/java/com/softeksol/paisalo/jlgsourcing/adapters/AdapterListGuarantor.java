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
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.softeksol.paisalo.jlgsourcing.entities.ViewHolders.GuarantorViewHolder;

import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterListGuarantor extends ArrayAdapter<Guarantor> {
    Context context;
    int resourecId;
    List<Guarantor> guarantors = null;

    public AdapterListGuarantor(Context context, @LayoutRes int resource, @NonNull List<Guarantor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourecId = resource;
        this.guarantors = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        GuarantorViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);

            holder = new GuarantorViewHolder();
            holder.tvName = (TextView) v.findViewById(R.id.tvItemLayoutName);
            holder.tvGurName = (TextView) v.findViewById(R.id.tvItemLayoutFather);
            holder.tvAddress = (TextView) v.findViewById(R.id.tvItemLayoutAddress);
            holder.tvAadhar = (TextView) v.findViewById(R.id.tvItemLayoutAadhar);
            holder.tvMobile = (TextView) v.findViewById(R.id.tvItemLayoutMobile);
            holder.tvId = (TextView) v.findViewById(R.id.tvItemLayoutExtra);

            v.setTag(holder);
        } else {
            holder = (GuarantorViewHolder) v.getTag();
        }

        Guarantor guarantor = guarantors.get(position);
        holder.tvName.setText(guarantor.getName());
        holder.tvGurName.setText(guarantor.getGurName());
        holder.tvAadhar.setText(guarantor.getAadharid());
        holder.tvAddress.setText(guarantor.getPerAddress());
        holder.tvMobile.setText(guarantor.getPerMob1());
        holder.tvId.setText("GrNo: " + String.valueOf(guarantor.getGrNo()) + "     id: " + String.valueOf(guarantor.getId()));
        return v;
    }

}
