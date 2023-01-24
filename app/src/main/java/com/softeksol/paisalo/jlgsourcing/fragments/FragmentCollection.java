package com.softeksol.paisalo.jlgsourcing.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityCollection;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterDueData;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterInstallment;
import com.softeksol.paisalo.jlgsourcing.entities.DueData;
import com.softeksol.paisalo.jlgsourcing.entities.Installment;
import com.softeksol.paisalo.jlgsourcing.entities.PosInstRcv;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCollection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCollection extends AbsCollectionFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DB_NAME = "param1";
    private static final String ARG_DB_DESC = "param2";

    // TODO: Rename and change types of parameters
    private String mDbName;
    private String mDbDesc;
    private ListView lv;
    private boolean isDialogActive;
    private int collectionAmount;
    private int latePmtIntAmt;

    public FragmentCollection() {
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
    public static FragmentCollection newInstance(String dbName, String dbDesc) {
        FragmentCollection fragment = new FragmentCollection();

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
                AdapterDueData adapterDueData = (AdapterDueData) parent.getAdapter();
                final DueData dueData = (DueData) adapterDueData.getItem(position);
                Log.d("DueData",dueData.toString());
                latePmtIntAmt=0;
                adapterDueData.notifyDataSetChanged();
                final int maxDue = dueData.getMaxDueAmount();
                final int latePaymentInterest = dueData.getInterestAmt();
                final List<Installment> installments = dueData.getInstData();

                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_collect, null);
                final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.rgCollectionType);
                dialogView.setBackgroundResource(R.color.colorLightGreen);
                AppCompatButton cancel = (AppCompatButton) dialogView.findViewById(R.id.btnCollectRight);
                cancel.setText("Cancel");

                final AppCompatButton collect = (AppCompatButton) dialogView.findViewById(R.id.btnCollectLeft);
                collect.setText("Collect");
                collect.setEnabled(false);

                final LinearLayout llLatePayment = dialogView.findViewById(R.id.llLatePmtInterest);
                final CheckBox chkLatePayment = dialogView.findViewById(R.id.chkLatePmtInterest);
                final TextView tvLatePayAmount = dialogView.findViewById(R.id.tvLatePmtInterestAmt);
                if (latePaymentInterest > 0) {
                    llLatePayment.setVisibility(View.VISIBLE);
                    tvLatePayAmount.setText(String.valueOf(latePaymentInterest));
                }

                final ToggleButton tglBtnPaidBy = (ToggleButton) dialogView.findViewById(R.id.tglPaidBy);
                tglBtnPaidBy.setVisibility(BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing") ? View.VISIBLE : View.GONE);
                tglBtnPaidBy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        dialogView.setBackgroundResource(isChecked ? R.color.colorLightRed : R.color.colorLightGreen);
                    }
                });

                final TextView tvSelectedCount = (TextView) dialogView.findViewById(R.id.tvCollectSelected);
                final TextView tvTotDue = (TextView) dialogView.findViewById(R.id.tvCollectTotalAmt);
                TextView tvSundary = (TextView) dialogView.findViewById(R.id.tvCollectSundry);
                //int total=dueData.getInstallmentSum(false);
                //final int dif =total-  dueData.getInstsAmtDue();
                tvSundary.setText(String.valueOf(dueData.getSundryBalance()));

                final TextInputEditText teitLumpSumAccount = (TextInputEditText) dialogView.findViewById(R.id.tietLumSumAmount);
                final TextInputLayout tilLumpsumAccount = (TextInputLayout) dialogView.findViewById(R.id.tilLumSumAmount);
                teitLumpSumAccount.addTextChangedListener(new MyTextWatcher(teitLumpSumAccount) {
                    @Override
                    public void validate(EditText editText, String text) {
                        int amt = Utils.getNotNullInt(editText);
                        collectionAmount = amt;
                        tvTotDue.setText(text);
                        collect.setEnabled(false);

                        if (amt < 1) {
                            editText.setError("Amount should be greater than or equals to 1");
                            return;
                        }
                        if (amt > maxDue) {
                            editText.setError("Amount should be less than or equals to " + maxDue);
                            return;
                        }
                        //collectEnableDisable(latePaymentInterest, collect, tvTotDue);
                        collect.setEnabled(true);

                        editText.setError(null);
                    }
                });
                tilLumpsumAccount.setVisibility(View.INVISIBLE);

                final ListView lvc = (ListView) dialogView.findViewById(R.id.lvcCollectInstallments);
                lvc.setItemsCanFocus(false);
                lvc.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                lvc.setAdapter(new AdapterInstallment(getContext(), R.layout.layout_item_installment, installments));

                lvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //System.out.println("clicked" + position);

                        Installment installment = (Installment) parent.getItemAtPosition(position);
                        installment.setSelected(!installment.isSelected());
                        CheckBox chb = (CheckBox) view.findViewById(R.id.cbItemInstallmentSelected);
                        chb.setChecked(installment.isSelected());
                        int selectedCount = DueData.getSelectedCount(installments);
                        int selectedAmount = DueData.getInstallmentSum(installments, true);
                        collectionAmount = selectedAmount;
                        tvSelectedCount.setText(String.valueOf(selectedCount));

                        collect.setEnabled(collectionAmount + latePmtIntAmt> (latePmtIntAmt>0?latePmtIntAmt-1:0));
                        tvTotDue.setText(String.valueOf(collectionAmount+ latePmtIntAmt));
                    }
                });
                chkLatePayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked){
                            latePmtIntAmt =latePaymentInterest;
                        }else{
                            latePmtIntAmt=0;
                        }
                        if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount) {
                            tvTotDue.setText(String.valueOf(collectionAmount));
                        }else {
                            tvTotDue.setText(String.valueOf(collectionAmount+latePmtIntAmt));
                        }
                        collect.setEnabled(collectionAmount> (latePmtIntAmt>0?latePmtIntAmt-1:0));

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select on or more Installments");
                builder.setView(dialogView);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        chkLatePayment.setEnabled(true);
                        chkLatePayment.setChecked(false);
                        switch (checkedId) {
                            case R.id.rbFixedAmount:
                                lvc.setEnabled(true);
                                tilLumpsumAccount.setVisibility(View.INVISIBLE);
                                teitLumpSumAccount.setText("0");
                                break;
                            case R.id.rbLumpSumAmount:
                                dueData.clearSection(installments);
                                tvSelectedCount.setText("0");
                                lvc.setEnabled(false);
                                ((AdapterInstallment) lvc.getAdapter()).notifyDataSetChanged();
                                tilLumpsumAccount.setVisibility(View.VISIBLE);
                                tilLumpsumAccount.setHint(getResources().getString(R.string.lump_sum_amount) + " (Max " + maxDue + ")");
                                collect.setEnabled(false);
                                teitLumpSumAccount.setText("0");
                                break;
                        }
                    }
                });

                final AlertDialog dialog = builder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dueData.setEnabled(false);
                        collect.setEnabled(false);
                        int totCollectAmt;
                        if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount){
                            totCollectAmt=collectionAmount;
                        }else{
                            totCollectAmt=collectionAmount+latePmtIntAmt;
                        }
                        saveDeposit(dueData, totCollectAmt,latePmtIntAmt,
                                tglBtnPaidBy.isChecked() ? "F" : "B");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Log.e("MDatabaseName",mDbName+"");
        Log.e("MDB_DESC",mDbDesc+"");
        lv.setAdapter(new AdapterDueData(getContext(), R.layout.layout_item_collection, ((ActivityCollection) getActivity()).getDueDataByDbName(mDbName)));
        SearchView searchView = (SearchView) view.findViewById(R.id.searchViewDueData);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((AdapterDueData) lv.getAdapter()).getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    public String getName() {
        return getArguments().getString(ARG_DB_DESC);
    }

    private void collectEnableDisable(int latePaymentInterest, AppCompatButton collect, TextView tvTotDue) {
        collect.setEnabled(collectionAmount > (latePaymentInterest > 0 ? latePaymentInterest - 1 : 0));
        //collect.setEnabled(collectionAmount > 0);
        tvTotDue.setText(String.valueOf(collectionAmount));
        //collect.setEnabled(latePaymentInterest > 0);
    }

    private void saveDeposit(DueData dueData, int collectedAmount, int latePmtAmount, String depBy) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Loan Collection", "Saving Collection Entry") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    ((ActivityCollection) getActivity()).refreshData(FragmentCollection.this);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), error.getMessage() + "\n" + (new String(responseBody)), Toast.LENGTH_LONG).show();
                //Log.d("eKYC Response",error.getLocalizedMessage());
            }
        };

        PosInstRcv instRcv = new PosInstRcv();
        instRcv.setCaseCode(dueData.getCaseCode());
        instRcv.setCreator(dueData.getCreator());
        instRcv.setDataBaseName(dueData.getDb());
        //instRcv.setDataBaseName("SBIPDL_TEST");
        instRcv.setIMEI(IglPreferences.getPrefString(getContext(), SEILIGL.DEVICE_IMEI, "0"));
        instRcv.setInstRcvAmt(collectedAmount - latePmtAmount);
        instRcv.setInstRcvDateTimeUTC(new Date());
        instRcv.setFoCode(dueData.getFoCode());
        instRcv.setCustName(dueData.getCustName());
        instRcv.setPartyCd(dueData.getPartyCd());
        instRcv.setInterestAmt(latePmtAmount);
        instRcv.setPayFlag(depBy);



        //Log.d("Json", String.valueOf(instRcv.getInstRcvDateTimeUTC()));

        Log.d("JsonInstRcv", String.valueOf(WebOperations.convertToJson(instRcv)));

        (new WebOperations()).postEntity(getContext(), "POSDATA", "instcollection", "savereceipt", WebOperations.convertToJson(instRcv), asyncResponseHandler);
    }

    public void refreshData() {
        AdapterDueData adapterDueData = (AdapterDueData) lv.getAdapter();
        adapterDueData.clear();
        adapterDueData.addAll(((ActivityCollection) getActivity()).getDueDataByDbName(mDbName));
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
