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
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;

import java.util.List;

/**
 * Created by sachindra on 2016-10-19.
 */
public class AdapterListRange extends ArrayAdapter<RangeCategory> {
    Context context;
    int resourecId;
    List<RangeCategory> rangeCategoryList = null;
    Boolean showDetail = false;

    public AdapterListRange(Context context, @LayoutRes int resource, @NonNull List<RangeCategory> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourecId = resource;
        this.rangeCategoryList = objects;
    }

    public AdapterListRange(Context context, @NonNull List<RangeCategory> objects, Boolean withSubDetail) {
        super(context, R.layout.spinner_card_orange, objects);
        this.context = context;
        this.resourecId = R.layout.spinner_card_orange;
        this.rangeCategoryList = objects;
        this.showDetail = withSubDetail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(R.layout.spinner_card_orange, parent, false);
        } else {
            v = convertView;
        }

        ((TextView) v.findViewById(R.id.text_cname)).setText(rangeCategoryList.get(position).DescriptionEn);
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            if (showDetail)
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_two_line, parent, false);
            else
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_card_orange, parent, false);

        } else {
            v = convertView;
        }
        if (showDetail) {
            ((TextView) v.findViewById(R.id.tvListItemDouble1)).setText(rangeCategoryList.get(position).DescriptionEn);
            ((TextView) v.findViewById(R.id.tvListItemDouble2)).setText(rangeCategoryList.get(position).GroupDescriptionEn);
        } else {
            ((TextView) v.findViewById(R.id.text_cname)).setText(rangeCategoryList.get(position).DescriptionEn);
        }

        return v;
    }
}
