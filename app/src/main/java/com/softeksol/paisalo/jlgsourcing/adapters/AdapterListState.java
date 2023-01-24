package com.softeksol.paisalo.jlgsourcing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.StateData;
import com.softeksol.paisalo.jlgsourcing.entities.ViewHolders.StateViewHolder;

import java.util.List;


public class AdapterListState extends ArrayAdapter<StateData> {
    private final Context context;
    private String defaultState = "";
    private List<StateData> StateArrayList;

    public AdapterListState(Context context, List<StateData> values) {
        super(context, R.layout.layout_item_states, values);
        this.context = context;
        this.StateArrayList = values;
    }

    public AdapterListState(Context context, List<StateData> values, String defaultState) {
        super(context, R.layout.layout_item_states, values);
        this.context = context;
        this.defaultState = defaultState;
    }

    public View getCustomView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // create a ViewHolder reference
        StateViewHolder holder;

        // check to see if the reused view is null or not, if is not null then
        // reuse it
        if (view == null) {
            holder = new StateViewHolder();
            view = inflater.inflate(R.layout.layout_item_states, null);
            holder.StateName = (TextView) view
                    .findViewById(R.id.layoutListStateName);
            holder.StateCode = (TextView) view
                    .findViewById(R.id.layoutListStateCode);
            holder.StatePinPrefix = (TextView) view
                    .findViewById(R.id.layoutListStatePinPrefix);
            holder.StatePinSuffix = (TextView) view
                    .findViewById(R.id.layoutListStatePinSuffix);
            // the setTag is used to store the data within this view
            view.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (StateViewHolder) view.getTag();
        }

        StateData stateItem = getItem(position);
        if (stateItem != null) {
            if (holder.StateCode != null) {
                view.setSelected(stateItem.StateName.equals(defaultState));
                // set the documentStore name on the TextView
                holder.StateName.setText(stateItem.StateName);
                holder.StateCode.setText(stateItem.StateCode);
                holder.StatePinPrefix.setText(String.valueOf(stateItem.PinPrefix));
                holder.StatePinSuffix.setText(String.valueOf(stateItem.PinSuffix));
            }
        }

        // this method must return the view corresponding to the data at the
        // specified position.
        return view;
    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


}