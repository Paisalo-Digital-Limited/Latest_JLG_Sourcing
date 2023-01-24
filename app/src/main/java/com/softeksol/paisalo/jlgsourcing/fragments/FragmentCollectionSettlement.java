package com.softeksol.paisalo.jlgsourcing.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityCollection;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterInstSettlementData;
import com.softeksol.paisalo.jlgsourcing.entities.PosInstRcv;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCollectionSettlement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCollectionSettlement extends AbsCollectionFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DB_NAME = "param1";
    private static final String ARG_DB_DESC = "param2";

    // TODO: Rename and change types of parameters
    private String mDbName;
    private String mDbDesc;
    private ListView lv;
    private SearchView searchView;
    private boolean isDialogActive;

    public FragmentCollectionSettlement() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dbName Parameter 1.
     * @return A new instance of fragment FragmentCollection.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCollectionSettlement newInstance(String dbName, String dbDesc) {
        FragmentCollectionSettlement fragment = new FragmentCollectionSettlement();

        Bundle args = new Bundle();
        args.putString(ARG_DB_NAME, dbName);
        args.putString(ARG_DB_DESC, dbDesc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDbName = getArguments().getString(ARG_DB_NAME);
            mDbDesc = getArguments().getString(ARG_DB_DESC);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        lv = (ListView) view.findViewById(R.id.lvDueData);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setEnabled(false);
                view.setClickable(false);
                final PosInstRcv dueData = (PosInstRcv) parent.getAdapter().getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Receipt Settlement");
                builder.setMessage("Are you sure to settle this receipt ?");
                builder.setPositiveButton("Settle Receipt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PosInstRcv submitData = dueData;
                        submitData.setPayFlag("S");
                        saveDeposit(submitData);
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        lv.setAdapter(new AdapterInstSettlementData(getContext(), R.layout.layout_item_receipt_settlement, ((ActivityCollection) getActivity()).getRecSettlementData()));
        searchView = (SearchView) view.findViewById(R.id.searchViewDueData);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((AdapterInstSettlementData) lv.getAdapter()).getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    public String getName() {
        return getArguments().getString(ARG_DB_DESC);
    }


    private void saveDeposit(PosInstRcv instRcv) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Loan Collection Settlement", "Saving Settlement Entry") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    ((ActivityCollection) getActivity()).refreshData(null);
                    //((ActivityCollection) getActivity()).refreshSettlement(FragmentCollectionSettlement.this);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), error.getMessage() + "\n" + (new String(responseBody)), Toast.LENGTH_LONG).show();
            }
        };

        (new WebOperations()).postEntity(getContext(), "POSDATA", "instcollection", "savereceipt", WebOperations.convertToJson(instRcv), asyncResponseHandler);
    }


    public void refreshData() {
        searchView.setQuery(null, false);
        AdapterInstSettlementData adapterDueData = (AdapterInstSettlementData) lv.getAdapter();
        adapterDueData.clear();
        adapterDueData.addAll(((ActivityCollection) getActivity()).getRecSettlementData());
        adapterDueData.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    @Override
    public void onStart() {
        refreshData();
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_DB_NAME, mDbName);
        outState.putString(ARG_DB_DESC, mDbDesc);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mDbName = savedInstanceState.getString(ARG_DB_NAME);
            mDbDesc = savedInstanceState.getString(ARG_DB_DESC);
        }
    }


}
