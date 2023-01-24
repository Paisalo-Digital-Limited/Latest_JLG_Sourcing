package com.softeksol.paisalo.jlgsourcing.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.loopj.android.http.DataAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.BankData;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory_Table;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBorrowerFinance.OnFragmentBorrowerFinanceInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBorrowerFinance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBorrowerFinance extends AbsFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    View v;
    private long borrowerId;
    private OnFragmentBorrowerFinanceInteractionListener mListener;
    private AdapterListRange rlaBankType, rlaPurposeType, rlaLoanAmount, rlaFinanceDuration, rlaSchemeType;
    private EditText etIFSC, etBaoDtate, etBankAccount, etCIF;
    private ActivityLoanApplication activity;
    private Borrower borrower;
    private Spinner spinnerPurpose, spinnerLoanAmount, spinnerLoanDuration, spinnerBankAcType, spinnerSchemeType;
    //private TextWatcher IFSCTextWatcher;


    public FragmentBorrowerFinance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param borrowerId Parameter.
     * @return A new instance of fragment FragmentBorrowerAadhar.
     */
    public static FragmentBorrowerFinance newInstance(long borrowerId) {
        FragmentBorrowerFinance fragment = new FragmentBorrowerFinance();
        Bundle args = new Bundle();
        args.putLong(Global.BORROWER_TAG, borrowerId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            borrowerId = getArguments().getLong(Global.BORROWER_TAG, 0);
        }
        activity = (ActivityLoanApplication) getActivity();

        rlaBankType = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("bank_ac_type")).queryList(), false);

        rlaSchemeType = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("DISBSCH")).queryList(), false);

        rlaPurposeType = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("loan_purpose"))
                        .orderBy(RangeCategory_Table.SortOrder, true).queryList(), true);

        //rlaLoanAmount = new AdapterListRange(this.getContext(), Utils.getList(16, 20, 1, 5000, "Rupees"), false);
        //Commented for E-Vehicle and above line added just for e-vehicle
        if (BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing")) {
            rlaLoanAmount = new AdapterListRange(this.getContext(), Utils.getList(16, 20, 1, 5000, "Rupees"), false);

        }
        else {
            rlaLoanAmount = new AdapterListRange(this.getContext(),
                    SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("loan_amt")).queryList(), false);
        }

        rlaFinanceDuration = new AdapterListRange(this.getContext(), Utils.getList(4, 8, 1, 3, "Months"), true);
        //rlaFinanceDuration = new AdapterListRange(this.getContext(), Utils.getList(5, 6, 1, 3, "Months"), true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_borrower_finance, container, false);

        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);

        spinnerPurpose = (Spinner) v.findViewById(R.id.spinLoanAppFinancePurposePrimary);
        spinnerPurpose.setAdapter(rlaBankType);
        spinnerBankAcType = (Spinner) v.findViewById(R.id.spinLoanAppFinanceAccountType);
        spinnerBankAcType.setAdapter(rlaPurposeType);
        spinnerLoanAmount = (Spinner) v.findViewById(R.id.spinLoanAppFinanceLoanAmount);
        spinnerLoanAmount.setAdapter(rlaLoanAmount);
        spinnerLoanDuration = (Spinner) v.findViewById(R.id.spinLoanAppFinanceDuration);
        spinnerLoanDuration.setAdapter(rlaFinanceDuration);


        spinnerSchemeType = (Spinner) v.findViewById(R.id.spinLoanAppFinanceScheme);
        spinnerSchemeType.setAdapter(rlaSchemeType);


        etIFSC = (EditText) v.findViewById(R.id.etLoanAppFinanceBankIfsc);
//        etCIF = (EditText) v.findViewById(R.id.bankCIF);
        etIFSC.addTextChangedListener(new MyTextWatcher(etIFSC) {
            @Override
            public void validate(EditText editText, String text) {
                validateIFSC(editText, text);
            }
        });
        etBankAccount = (EditText) v.findViewById(R.id.etLoanAppFinanceBankAccountNo);

        // etBaoDtate=(EditText) v.findViewById(R.id.etLoanAppFinanceBankAcOpenDate);
        // etBaoDtate.setOnClickListener(this);
        borrower = activity.getBorrower();
        setDataToView(v);
        //etIFSC.addTextChangedListener(IFSCTextWatcher);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBorrowerFinanceInteractionListener) {
            mListener = (OnFragmentBorrowerFinanceInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.d("Detaching", "Detach");
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        borrower = activity.getBorrower();
        setDataToView(requireView());
    }

    @Override
    public void onPause() {
        //etIFSC.removeTextChangedListener(IFSCTextWatcher);
        getDataFromView(getView());
        super.onPause();
    }


    private void setDataToView(View v) {
        Utils.setSpinnerPosition(spinnerLoanAmount, borrower.Loan_Amt);
        //Commented for E-Vehicle
        spinnerLoanAmount.setEnabled(borrower.Loan_Amt < 10);

        Utils.setSpinnerPosition(spinnerLoanDuration, borrower.Loan_Duration);
        Utils.setSpinnerPosition(spinnerPurpose, borrower.Loan_Reason);
        Utils.setSpinnerPosition(spinnerSchemeType, borrower.T_ph3);

        spinnerPurpose.setEnabled(borrower.Loan_Reason.length() < 3);
        etBankAccount.setText(Utils.NullIf(borrower.bank_ac_no, ""));
        etBankAccount.setEnabled(Utils.NullIf(borrower.bank_ac_no, "").length() < 3);
        Utils.setSpinnerPosition(spinnerBankAcType, borrower.BankAcType);
        etIFSC.setText(Utils.NullIf(borrower.Enc_Property, ""));
        ((TextView) v.findViewById(R.id.tvLoanAppFinanceBankName)).setText(Utils.NullIf(borrower.BankName, ""));
        ((TextView) v.findViewById(R.id.tvLoanAppFinanceBankBranch)).setText(Utils.NullIf(borrower.Bank_Address, ""));
        //((EditText) v.findViewById(R.id.etLoanAppFinanceBankAcOpenDate)).setText(DateUtils.getFormatedDate(borrower.BankAcOpenDt,"dd-MMM-yyyy"));
    }

    private void getDataFromView(View v) {
        if (v != null) {
            borrower.Loan_Amt = Utils.getSpinnerIntegerValue(spinnerLoanAmount);
            borrower.Loan_Duration = Utils.getSpinnerStringValue(spinnerLoanDuration);
            borrower.Loan_Reason = Utils.getSpinnerStringValue(spinnerPurpose);
            borrower.bank_ac_no = Utils.getNotNullText(etBankAccount);
            borrower.BankAcType = Utils.getSpinnerStringValue(spinnerBankAcType);
            borrower.Enc_Property = Utils.getNotNullText(etIFSC);
//            borrower.fiExtraBank.setBankCif(Utils.getNotNullText(etCIF));
            borrower.BankName = Utils.getNotNullText((TextView) v.findViewById(R.id.tvLoanAppFinanceBankName));
            borrower.Bank_Address = Utils.getNotNullText((TextView) v.findViewById(R.id.tvLoanAppFinanceBankBranch));
            borrower.T_ph3 = Utils.getSpinnerStringValue(spinnerSchemeType);
            //borrower.Business_Detail = borrower.Loan_Reason;
            //borrower.BankAcOpenDt=DateUtils.getParsedDate(((EditText)v.findViewById(R.id.etLoanAppFinanceBankAcOpenDate)).getText().toString(),"dd-MMM-yyyy");
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
        Calendar baoDate = Calendar.getInstance();
        baoDate.set(selectedYear, selectedMonth, selectedDay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        etBaoDtate.setText(simpleDateFormat.format(baoDate.getTime()));
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()){
            case R.id.etLoanAppFinanceBankAcOpenDate:
                Calendar mcurrentDate=(Calendar.getInstance());
                mcurrentDate.add(Calendar.MONTH,-1);
                DateUtils.showDatePicker(getActivity(),this,mcurrentDate,0,0,mcurrentDate.getTimeInMillis());
                break;
        }*/
    }

    @Override
    public String getName() {
        return "Financial Info";
    }

    public interface OnFragmentBorrowerFinanceInteractionListener {
        void onFragmentBorrowerFinanceInteraction(Borrower borrower);
    }

    private void validateIFSC(final EditText editText, String text) {
        editText.setError(null);
        if (editText.length() != 11) {
            editText.setError("Must be 11 Character");
        } else if (!Verhoeff.validateIFSC(text)) {
            editText.setError("Please input a valid IFSC. " + " " + text + "is not a valid IFSC");
        } else {
            DataAsyncHttpResponseHandler dataAsyncHttpResponseHandler = new DataAsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String jsonString = new String(responseBody);
                    try {
                        TextView tvBankName = (TextView) getView().findViewById(R.id.tvLoanAppFinanceBankName);
                        TextView tvBankBranch = (TextView) getView().findViewById(R.id.tvLoanAppFinanceBankBranch);
                        tvBankName.setText("");
                        tvBankBranch.setText("");
                        BankData bankData = WebOperations.convertToObject(jsonString, BankData.class);
                        if (bankData.BANK == null) {
                            editText.setError("This IFSC not available");
                        } else if (borrower != null) {
                            borrower.BankName = bankData.BANK.replace("á", "").replace("┬", "").replace("û", "").replace("ô", "");
                            borrower.Bank_Address = bankData.ADDRESS.replace("á", "").replace("┬", "").replace("û", "").replace("ô", "");
                            tvBankName.setText(borrower.BankName);
                            tvBankBranch.setText(borrower.Bank_Address);
                        }
                    } catch (NullPointerException ne) {

                    } catch (Exception e) {
                        editText.setError("Try Again");
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getActivity(), "IFSC not found ", Toast.LENGTH_LONG).show();
                }
            };
            RequestParams params = new RequestParams();
            params.add("IFSC", text);
            (new WebOperations()).getEntity(getActivity(), "bankname", "getbankname", params, dataAsyncHttpResponseHandler);
        }
    }
}
