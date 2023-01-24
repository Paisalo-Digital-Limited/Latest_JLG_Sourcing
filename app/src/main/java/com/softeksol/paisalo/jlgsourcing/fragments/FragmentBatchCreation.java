package com.softeksol.paisalo.jlgsourcing.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterReceiptData;
import com.softeksol.paisalo.jlgsourcing.entities.ReceiptData;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBatchCreation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBatchCreation extends AbsCollectionFragment {

    private ArrayList<ReceiptData> receiptDataList = new ArrayList<>();
    private ListView lvc;
    private OnFragmentBatchCreationInteractionListener mListener;

    public FragmentBatchCreation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * // @param groupCode Parameter 1.
     *
     * @return A new instance of fragment FragmentBatchCreation.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBatchCreation newInstance() {
        FragmentBatchCreation fragment = new FragmentBatchCreation();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_batch_creation, container, false);
        lvc = (ListView) v.findViewById(R.id.lvcReceiptList);
        lvc.setAdapter(new AdapterReceiptData(getContext(), R.layout.layout_item_receipt_data, new ArrayList<ReceiptData>()));
        AppCompatButton btn = (AppCompatButton) v.findViewById(R.id.btnDepositeLeft);
        btn.setText("Create Deposit Batch");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLastBatch(new Date());
            }
        });
        return v;
    }

    @Override
    public void refreshData() {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Loan Collection Deposit", "Fetching Unprocessed Collections") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Type listType = new TypeToken<List<ReceiptData>>() {
                    }.getType();
                    receiptDataList.clear();
                    String jsonString = (new String(responseBody));
                    List<ReceiptData> receiptDataList1 = WebOperations.convertToObjectArray(jsonString, listType);
                    receiptDataList.addAll(receiptDataList1);
                    //Log.d("ReceiptData",receiptDataList.toString());
                    showCollections();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.d("DueData Error", new String(responseBody));
            }
        };
        (new WebOperations()).getEntity(getContext(), "POSDATA", "instcollection", "getunbatcheddata", null, asyncResponseHandler);
    }

    private void showCollections() {
        AdapterReceiptData adapterReceiptData = (AdapterReceiptData) lvc.getAdapter();
        adapterReceiptData.clear();
        adapterReceiptData.addAll(this.receiptDataList);
        adapterReceiptData.notifyDataSetChanged();
        ((TextView) getView().findViewById(R.id.tvReceiptCount)).setText(String.valueOf(adapterReceiptData.getCount()));
        ((TextView) getView().findViewById(R.id.tvReceiptTotalAmt)).setText(String.valueOf(adapterReceiptData.getReceiptSum()));
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    private void requestLastBatch(final Date today) {

        final Date lastDate = DateUtils.getDatePart(today);
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Deposite Closing", "Saving Deposit for Selected Receipts") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    //Utils.alert(getContext(), new String(responseBody));
                    Type listType = new TypeToken<List<LastBatch>>() {
                    }.getType();
                    String jsonString = (new String(responseBody));
                    List<LastBatch> lastBatch = WebOperations.convertToObjectArray(jsonString, listType);
                    if (lastBatch.get(0).BatchDate == null) {
                        updateBatch(today);
                    } else {
                        if (lastBatch.get(0).BatchDate.before(lastDate)) {
                            updateBatch(today);
                        } else {
                            Utils.alert(getContext(), "You cannot create a new DayEnd today, try tomorrow");
                        }
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utils.alert(getContext(), new String(responseBody));
                //Log.d("Batch Creation Error", new String(responseBody));
            }
        };
        (new WebOperations()).getEntity(getContext(), "POSDATA", "instcollection", "getlastbatch", null, asyncResponseHandler);

    }

    private void updateBatch(Date batchDate) {
        ArrayList<BatchData> batchDataArrayList = new ArrayList<>();
        for (ReceiptData receiptData : this.receiptDataList) {
            BatchData batchData = new BatchData();
            batchData.setInstRcvID(receiptData.getInstRcvID());
            batchData.setBatchNo(1);
            batchData.setBatchDate(batchDate);
            batchDataArrayList.add(batchData);
        }

        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Deposite Closing", "Saving Deposit for Selected Receipts") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Utils.alert(getContext(), new String(responseBody));
                    refreshData();
                    mListener.OnFragmentBatchCreationInteraction();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utils.alert(getContext(), new String(responseBody));
                //Log.d("Batch Creation Error", new String(responseBody));
            }
        };
        (new WebOperations()).postEntity(getContext(), "POSDATA", "instcollection", "newbatchupdate", WebOperations.convertToJson(batchDataArrayList), asyncResponseHandler);
    }

    @Override
    public String getName() {
        return "Day End";
    }

    class BatchData {
        @Expose
        private int InstRcvID;
        @Expose
        private int BatchNo;
        @Expose
        private Date BatchDate;

        public int getInstRcvID() {
            return InstRcvID;
        }

        public void setInstRcvID(int instRcvID) {
            InstRcvID = instRcvID;
        }

        public int getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(int batchNo) {
            BatchNo = batchNo;
        }

        public Date getBatchDate() {
            return BatchDate;
        }

        public void setBatchDate(Date batchDate) {
            BatchDate = batchDate;
        }

        @Override
        public String toString() {
            return "BatchData{" +
                    "InstRcvID=" + InstRcvID +
                    ", BatchNo=" + BatchNo +
                    ", BatchDate=" + BatchDate +
                    '}';
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBatchCreationInteractionListener) {
            mListener = (OnFragmentBatchCreationInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        //Log.d("onDetach", "");
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentBatchCreationInteractionListener {
        void OnFragmentBatchCreationInteraction();
    }

    private class LastBatch implements Serializable {
        private Date BatchDate;
        private int batchno;

        @Override
        public String toString() {
            return "LastBatch{" +
                    "BatchDate=" + BatchDate +
                    ", batchno=" + batchno +
                    '}';
        }
    }

}
