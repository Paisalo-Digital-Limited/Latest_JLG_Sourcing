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
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.ViewHolders.ManagerViewHolder;

import java.util.List;

/**
 * Created by sachindra on 2016-10-08.
 */
public class AdapterListManager extends ArrayAdapter<Manager> {
    Context context;
    int resourecId;
    List<Manager> managers = null;

    public AdapterListManager(Context context, @LayoutRes int resource, @NonNull List<Manager> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourecId = resource;
        this.managers = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ManagerViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);

            holder = new ManagerViewHolder();
            holder.tvManagerName = (TextView) v.findViewById(R.id.designamtionName);
            holder.tvManagerCode = (TextView) v.findViewById(R.id.placeAndGroup);
            holder.tvManagerCreator = (TextView) v.findViewById(R.id.branccodeAndCreator);


            v.setTag(holder);
        } else {
            holder = (ManagerViewHolder) v.getTag();
        }

        Manager manager = managers.get(position);
        holder.tvManagerName.setText(manager.FOName);
        holder.tvManagerCode.setText(manager.AreaName+"/"+manager.AreaCd);
        holder.tvManagerCreator.setText(manager.FOCode+"/"+manager.Creator);


        return v;
    }

}
