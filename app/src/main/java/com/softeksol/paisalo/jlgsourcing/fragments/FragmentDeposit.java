package com.softeksol.paisalo.jlgsourcing.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.reflect.TypeToken;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityBankDeposit;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDeposit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDeposit extends AbsFragment {
    private ArrayList<PendigBatch> pendigBatches = new ArrayList<>();
    private ListView lvc;

    public FragmentDeposit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentDeposit.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDeposit newInstance() {
        FragmentDeposit fragment = new FragmentDeposit();
        /*Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_deposit, container, false);
        lvc = (ListView) v.findViewById(R.id.lvcPendingBatches);

        lvc.setAdapter(new AdapterPendigBatch(getContext(), R.layout.layout_item_pending_batch, new ArrayList<PendigBatch>()));
        lvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((PendigBatch) parent.getItemAtPosition(position)).Balance >= 100) {
                    Intent intent = new Intent(getActivity(), ActivityBankDeposit.class);
                    intent.putExtra("PENDING_BATCH", (PendigBatch) parent.getAdapter().getItem(position));
                    startActivity(intent);
                } else {
                    Utils.alert(getContext(), "Please select a Pending Batch having amount more than 100");
                }
            }
        });
        return v;
    }

    public void refreshData() {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Loan Collection Deposit", "Fetching Unprocessed Collections") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Type listType = new TypeToken<List<PendigBatch>>() {
                    }.getType();
                    pendigBatches.clear();
                    String jsonString = (new String(responseBody));
                    List<PendigBatch> pendigBatches1 = WebOperations.convertToObjectArray(jsonString, listType);
                    pendigBatches.addAll(pendigBatches1);
                    //Log.d("ReceiptData",pendigBatches.toString());
                    showCollections();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("DueData Error", new String(responseBody));
            }
        };
        (new WebOperations()).getEntity(getContext(), "POSDATA", "instcollection", "getpendingbatches", null, asyncResponseHandler);
    }

    private void showCollections() {
        AdapterPendigBatch adapterPendigBatch = (AdapterPendigBatch) lvc.getAdapter();
        adapterPendigBatch.clear();
        adapterPendigBatch.addAll(this.pendigBatches);
        adapterPendigBatch.notifyDataSetChanged();
        ((TextView) getView().findViewById(R.id.tvPendingBatchesCount)).setText(String.valueOf(adapterPendigBatch.getCount()));
        ((TextView) getView().findViewById(R.id.tvPendingBatchesAmt)).setText(String.valueOf(adapterPendigBatch.getPendingBatchesSum()));
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    @Override
    public String getName() {
        return "Deposit";
    }

    public class PendigBatch implements Serializable {
        private String databasename;
        private String creator;
        private String focode;
        private Date BatchDate;
        private int batchno;
        private int Balance;

        public String getDatabasename() {
            return databasename;
        }

        public void setDatabasename(String databasename) {
            this.databasename = databasename;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getFocode() {
            return focode;
        }

        public void setFocode(String focode) {
            this.focode = focode;
        }

        public Date getBatchDate() {
            return BatchDate;
        }

        public void setBatchDate(Date batchDate) {
            BatchDate = batchDate;
        }

        public int getBatchno() {
            return batchno;
        }

        public void setBatchno(int batchno) {
            this.batchno = batchno;
        }

        public int getBalance() {
            return Balance;
        }

        public void setBalance(int balance) {
            Balance = balance;
        }
    }

    public class AdapterPendigBatch extends ArrayAdapter<PendigBatch> {
        Context context;
        int resourecId;
        List<PendigBatch> pendigBatchList = null;

        public AdapterPendigBatch(Context context, @LayoutRes int resource, @NonNull List<PendigBatch> pendigBatches) {
            super(context, resource, pendigBatches);
            this.context = context;
            this.resourecId = resource;
            this.pendigBatchList = pendigBatches;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            PendingBatchViewHolder holder;
            if (v == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                v = inflater.inflate(resourecId, parent, false);

                holder = new PendingBatchViewHolder();
                holder.tvPendingBatchDate = (TextView) v.findViewById(R.id.tvLlPendingBatchDate);
                holder.tvPendingBatchAmount = (TextView) v.findViewById(R.id.tvLlPendingBatchAmount);

                v.setTag(holder);
            } else {
                holder = (PendingBatchViewHolder) v.getTag();
            }

            PendigBatch pendigBatch = this.pendigBatchList.get(position);
            holder.tvPendingBatchDate.setText(DateUtils.getFormatedDate(pendigBatch.BatchDate, "dd-MM-yyyy"));
            holder.tvPendingBatchAmount.setText(String.valueOf(pendigBatch.Balance));
            return v;

        }

        public int getPendingBatchesSum() {
            int tot = 0;
            for (PendigBatch pendigBatch : this.pendigBatchList) {
                tot += pendigBatch.Balance;
            }
            return tot;
        }

        class PendingBatchViewHolder {
            TextView tvPendingBatchDate;
            TextView tvPendingBatchAmount;
        }
    }


}
