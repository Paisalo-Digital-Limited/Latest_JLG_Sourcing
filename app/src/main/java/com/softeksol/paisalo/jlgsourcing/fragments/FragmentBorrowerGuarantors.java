package com.softeksol.paisalo.jlgsourcing.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.AadharUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityGuarantorEntry;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListGuarantor;
import com.softeksol.paisalo.jlgsourcing.entities.Aadhar;
import com.softeksol.paisalo.jlgsourcing.entities.AadharData;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBorrowerGuarantors.OnListFragmentBorrowerGuarantorsInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBorrowerGuarantors#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBorrowerGuarantors extends AbsFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    View v;

    private OnListFragmentBorrowerGuarantorsInteractionListener mListener;
    private SQLCondition sqlCondition = null;
    private int mColumnCount = 1;
    private List<Guarantor> guarantors;
    private ListView lvGuarantorList;
    private FloatingActionButton fab;
    private ActivityLoanApplication activity;
    //private Borrower borrower;

    private String AuthType;

    public FragmentBorrowerGuarantors() {
    }

    public static FragmentBorrowerGuarantors newInstance() {
        FragmentBorrowerGuarantors fragment = new FragmentBorrowerGuarantors();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ActivityLoanApplication) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_borrower_guarantors, container, false);
        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);
        TextView tv = (TextView) v.findViewById(R.id.tvSelectWithRefreshTitle);
        tv.setText(R.string.guarantor_title_select);
        //refreshGuarantors();
        lvGuarantorList = (ListView) v.findViewById(R.id.lvSelectWithRefresh);
        lvGuarantorList.setAdapter(new AdapterListGuarantor(activity, R.layout.layout_item_loan_app, new ArrayList<Guarantor>()));
        lvGuarantorList.setOnItemClickListener(this);
        fab = (FloatingActionButton) v.findViewById(R.id.fabSelectWithRefresh);
        fab.setOnClickListener(this);
        fab.setImageResource(android.R.drawable.ic_input_add);
        fab.setVisibility(View.VISIBLE);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentBorrowerGuarantorsInteractionListener) {
            mListener = (OnListFragmentBorrowerGuarantorsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.d("Detaching", "FragmentBorrowerGuarantor");
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        AdapterListGuarantor adapterListGuarantor = (AdapterListGuarantor) lvGuarantorList.getAdapter();
        List<Guarantor> guarantors = activity.getBorrower().getFiGuarantors();
        adapterListGuarantor.clear();
        if (guarantors.size() > 0) {
            adapterListGuarantor.addAll(guarantors);
            adapterListGuarantor.notifyDataSetChanged();
        } else {
            adapterListGuarantor.notifyDataSetInvalidated();
        }
        fab.setVisibility(guarantors.size() > 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabSelectWithRefresh:
                Intent intent = new Intent(activity, ActivityGuarantorEntry.class);
                intent.putExtra(Global.BORROWER_TAG, activity.getBorrower().FiID);
                intent.putExtra(Global.GUARANTOR_TAG, 0L);
                startActivity(intent);
                break;

        }
    }

    private void newAadharScan() {
        IntentIntegrator scanIntegrator = IntentIntegrator.forSupportFragment(this);
        scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.lvSelectWithRefresh:
                startGuarantorActivity((Guarantor) adapterView.getAdapter().getItem(i));
        }
    }

    @Override
    public String getName() {
        String name;
        if (BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing")) {
            name = "Guarantors";
        } else {
            name = "Nominees";
        }
        return name;
    }

    public interface OnListFragmentBorrowerGuarantorsInteractionListener {
        void onListFragmentBorrowerGuarantorsInteraction(Guarantor guarantor);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if (scanFormat != null) {
                    setAadharContent(scanContent);
                }
            }
        } else if (requestCode == Global.EKYC_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                processKycresponse(data.getStringExtra("EKYCDATA"));
            } else {
                Toast.makeText(getContext(), "Error Performing eKYC", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startGuarantorActivity(Guarantor guarantor) {
        Intent intent;
        /*if (BuildConfig.APPLICATION_ID == "net.softeksol.seil.groupfin") {
            intent = new Intent(getActivity(), ActivityGuarantorEntry.class);
        } else {
            intent = new Intent(getActivity(), ActivityGuarantor.class);
        }*/
        intent = new Intent(getActivity(), ActivityGuarantorEntry.class);
        intent.putExtra(Global.BORROWER_TAG, activity.getBorrower().FiID);
        intent.putExtra(Global.GUARANTOR_TAG, guarantor.getId());
        startActivity(intent);
    }

    private void processKycresponse(String kycStatus) {
        JSONObject jsonObj = null;
        Log.i("kyc", kycStatus);
        try {
            jsonObj = new JSONObject(kycStatus);
            // Getting JSON Array node
            JSONArray Authentication = jsonObj.getJSONArray("KYCRES");

            // looping through All Contacts
            for (int i = 0; i < Authentication.length(); i++) {
                JSONObject c = Authentication.getJSONObject(i);

                String status = c.getString("status");
                if (status.equals("1")) {
                    String uuid = c.getString("uuid");
                    fetchKYC(uuid);
                    Log.d("checkUUid",uuid);
                } else {
                    Utils.alert(getContext(), c.getString("errcode") + "\n" + c.getString("errmsg"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetchKYC(String uuid) {
        Toast.makeText(getContext(), "Fetching eKYC", Toast.LENGTH_LONG).show();
        //Log.d("eKYC","Fetching eKYC");

        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Loan Financing", "Performing ESigning") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Aadhar aadhar = (new Gson()).fromJson((new JsonParser()).parse(new String(responseBody)), Aadhar.class);
                    AadharData aadharData = AadharUtils.parseAadhar(aadhar);
                    createGuarantor(aadharData);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //Log.d("ESign Response",error.getLocalizedMessage());
            }
        };

        RequestParams params = new RequestParams();
        params.add("UUID", uuid);

        (new WebOperations()).getEntity(getContext(), BuildConfig.EKYC_FETCH_URL, params, asyncResponseHandler);
    }

    public void setAadharContent(String aadharDataString) {
        try {
            final AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));
            if (aadharData.Address2 == null) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address2.trim().equals("")) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }
            if (aadharData.Address1 == null) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address1.trim().equals("")) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }

            if (aadharData.GurName == null || aadharData.GurName == "") {
                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Aadhar eKYC ");
                builder.setMessage("Father /Husband name missing, please input below");
                builder.setView(input);
                DialogInterface.OnClickListener onSaveClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aadharData.GurName = input.getText().toString();
                        createGuarantor(aadharData);
                    }
                };
                builder.setPositiveButton("Save", onSaveClickListener);
                final AlertDialog dialog = builder.create();
                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(s.length() >= 3);
                    }
                });

                dialog.show();
            } else {
                createGuarantor(aadharData);
            }
        } catch (Exception ex) {
            Utils.alert(getActivity(), ex.getMessage());
        }
    }

    private void createGuarantor(AadharData aadharData) {
        Guarantor guarantor = new Guarantor();
        AadharUtils.setAadharToGuarantor(aadharData, guarantor);
        guarantor.associateBorrower(activity.getBorrower());
        guarantor.save();
        startGuarantorActivity(guarantor);
    }
}
