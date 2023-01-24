package com.softeksol.paisalo.jlgsourcing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.dto.OperationItem;

import java.util.List;


public class AdapterOperation extends ArrayAdapter<OperationItem> {
    private final Context context;
    private String defaultState = "";
    private List<OperationItem> StateArrayList;

    public AdapterOperation(Context context, List<OperationItem> values) {
        super(context, R.layout.operation_item_card, values);
        this.context = context;
        this.StateArrayList = values;
    }

    public View getCustomView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        OperationViewHolder holder;

        if (view == null) {
            holder = new OperationViewHolder();
            view = inflater.inflate(R.layout.operation_item_card, null);

            holder.textView = (TextView) view.findViewById(R.id.module_name);
            holder.imageView = (ImageView) view.findViewById(R.id.imageModeules);
            view.setTag(holder);
        } else {
            holder = (OperationViewHolder) view.getTag();
        }

        OperationItem operationItem = getItem(position);
        if (operationItem != null) {
            if (holder.textView != null) {
                // set the documentStore name on the TextView
                holder.textView.setText(operationItem.getOprationName());
                holder.operationItem = operationItem;
                if (operationItem.getOprationName().equals("KYC")){
                    holder.imageView.setImageResource(R.drawable.kyc_ic);

                }else if (operationItem.getOprationName().equals("Application Form")){
                    holder.imageView.setImageResource(R.drawable.application_form_ic);

                }else if (operationItem.getOprationName().equals("Collection")){
                    holder.imageView.setImageResource(R.drawable.collection_ic);

                }
            }
        }

        return view;
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    class OperationViewHolder {
        private TextView textView;
        private ImageView imageView;
        private OperationItem operationItem;
    }
}