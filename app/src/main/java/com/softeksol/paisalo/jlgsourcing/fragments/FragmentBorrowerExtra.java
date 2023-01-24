package com.softeksol.paisalo.jlgsourcing.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtra;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory_Table;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentBorrowerExtraInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBorrowerExtra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBorrowerExtra extends AbsFragment implements AdapterView.OnItemSelectedListener {
    View v;
    private long borrowerId;
    private OnFragmentBorrowerExtraInteractionListener mListener;
    private AdapterListRange rlaEarningMember, rlaEarningMemberOccupation, rlaEarningMemberIncome, rlaEMployerType, rlaShopOwner, rla1to9, rla0to9;
    private AdapterListRange rlaOtherAgriLandOwnership, rlaOtherBusinessType, rla0to11, rlaOtherAgriLandType, rlaPurposeType, rlaIncome, rlaSchoolingChildren;
    private ActivityLoanApplication activity;
    private BorrowerExtra borrowerExtra;


    public FragmentBorrowerExtra() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param borrowerId Parameter.
     * @return A new instance of fragment FragmentBorrowerAadhar.
     */
    public static FragmentBorrowerExtra newInstance(long borrowerId) {
        FragmentBorrowerExtra fragment = new FragmentBorrowerExtra();
        Bundle args = new Bundle();
        args.putLong(Global.BORROWER_TAG, borrowerId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ActivityLoanApplication) getActivity();
        if (getArguments() != null) {
            borrowerId = getArguments().getLong(Global.BORROWER_TAG, 0);
        }

        rlaEarningMember = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("other_income")).queryList(), false);

        rlaEarningMemberOccupation = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("other_employment")).queryList(), false);

        rlaEarningMemberIncome = new AdapterListRange(this.getContext(), Utils.getList(1, 11, 1, 1000, getString(R.string.rupees)), true);

        rlaEMployerType = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("employer_type")).queryList(), false);

        rlaShopOwner = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("land_owner")).queryList(), false);

        rlaOtherBusinessType = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("loan_purpose")).queryList(), false);

        rlaOtherAgriLandOwnership = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("agri_land_ownership")).queryList(), false);

        rlaOtherAgriLandType = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("agri_land_type")).queryList(), false);

        rlaPurposeType = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("loan_purpose"))
                        .orderBy(RangeCategory_Table.SortOrder, true).queryList(), true);

        rlaIncome = new AdapterListRange(this.getContext(), Utils.getList(0, 11, 1, 1000, "Rupees"), true);

        rla0to11 = new AdapterListRange(this.getContext(), Utils.getList(1, 11, 1, 1, getString(R.string.acres)), true);
        //rla0to11Acres = new AdapterListRange(this.getContext(), Utils.getList(1, 11, 1, 1,  getString(R.string.acres)), true);

        rla1to9 = new AdapterListRange(this.getContext(), Utils.getList(1, 9, 1, 1, null), false);

        rla0to9 = new AdapterListRange(this.getContext(), Utils.getList(0, 9, 1, 1, null), false);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_borrower_extra, container, false);

        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);

        Spinner spinnerIncomeFuture = (Spinner) v.findViewById(R.id.spinLoanAppExtraIncomeFuture);
        spinnerIncomeFuture.setAdapter(rlaIncome);
        Spinner spinnerDependentAdults = (Spinner) v.findViewById(R.id.spinLoanAppExtraDependentAdults);
        spinnerDependentAdults.setAdapter(rla0to9);
        Spinner spinnerChildren = (Spinner) v.findViewById(R.id.spinLoanAppExtraChildren);
        spinnerChildren.setAdapter(rla0to9);
        spinnerChildren.setOnItemSelectedListener(this);

        Spinner spinnerChildrenSpend = (Spinner) v.findViewById(R.id.spinLoanAppExtraChildrenSpending);
        spinnerChildrenSpend.setAdapter(rlaIncome);
        Spinner spinnerOtherEarningMember = (Spinner) v.findViewById(R.id.spinLoanAppExtraEarningMember);
        spinnerOtherEarningMember.setAdapter(rlaEarningMember);
        Spinner spinnerOtherEarningMemberIncome = (Spinner) v.findViewById(R.id.spinLoanAppExtraIncome);
        spinnerOtherEarningMemberIncome.setAdapter(rlaIncome);

        Spinner spinnerOtherEarningMemberOccupation = (Spinner) v.findViewById(R.id.spinLoanAppExtraOccupationType);
        spinnerOtherEarningMemberOccupation.setAdapter(rlaEarningMemberOccupation);
        spinnerOtherEarningMemberOccupation.setOnItemSelectedListener(this);

        ((Spinner) v.findViewById(R.id.spinLoanAppExtraEmployerType)).setAdapter(rlaEMployerType);
        ((Spinner) v.findViewById(R.id.spinLoanAppExtraShopOwner)).setAdapter(rlaShopOwner);
        ((Spinner) v.findViewById(R.id.spinLoanAppExtraBusinessType)).setAdapter(rlaOtherBusinessType);

        ((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureLandOwner)).setAdapter(rlaOtherAgriLandOwnership);
        ((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureLandType)).setAdapter(rlaOtherAgriLandType);
        ((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureArea)).setAdapter(rla0to11);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBorrowerExtraInteractionListener) {
            mListener = (OnFragmentBorrowerExtraInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.d("Detaching", "");
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        borrowerExtra = activity.getBorrower().getBorrowerExtra();
        if (borrowerExtra == null) {
            borrowerExtra = new BorrowerExtra();
            activity.getBorrower().associateExtra(borrowerExtra);
            borrowerExtra.save();
        }
        setDataToView(getView());
    }

    @Override
    public void onPause() {
        getDataFromView(getView());
        super.onPause();
    }


    private void setDataToView(View v) {
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraIncomeFuture), borrowerExtra.FutureIncome);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraDependentAdults), borrowerExtra.OtherDependents);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraChildren), borrowerExtra.NoOfChildren);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraChildrenSchooling), borrowerExtra.SchoolingChildren);

        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraChildrenSpending), borrowerExtra.SpendOnChildren);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraEarningMember), borrowerExtra.FamIncomeSource);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraIncome), borrowerExtra.FamMonthlyIncome);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraOccupationType), borrowerExtra.FamOccupation);

        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraEmployerType), borrowerExtra.FamJobCompType);
        ((EditText) v.findViewById(R.id.etLoanAppExtraEmployerName)).setText(borrowerExtra.FamJobCompName);

        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraShopOwner), borrowerExtra.FamBusinessShopType);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraBusinessType), borrowerExtra.FamBusinessType);

        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureLandOwner), borrowerExtra.FamAgriLandOwner);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureLandType), borrowerExtra.FamAgriLandType);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureArea), borrowerExtra.FamAgriLandArea);

        //Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppExtraOtherIncomeType),borrowerExtra.FamOtherIncomeType);
        ((EditText) v.findViewById(R.id.etLoanAppExtraOtherIncomeOther)).setText(borrowerExtra.FamOtherIncomeType);

    }

    private void getDataFromView(View v) {
        borrowerExtra.FutureIncome = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppExtraIncomeFuture));
        borrowerExtra.OtherDependents = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppExtraDependentAdults));
        borrowerExtra.NoOfChildren = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppExtraChildren));

        Spinner childrenSchooling = (Spinner) v.findViewById(R.id.spinLoanAppExtraChildrenSchooling);
        if (childrenSchooling.getVisibility() == View.VISIBLE)
            borrowerExtra.SchoolingChildren = Utils.getSpinnerIntegerValue(childrenSchooling);

        borrowerExtra.SpendOnChildren = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppExtraChildrenSpending));

        borrowerExtra.FamIncomeSource = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppExtraEarningMember));
        borrowerExtra.FamMonthlyIncome = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppExtraIncome));
        borrowerExtra.FamOccupation = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppExtraOccupationType));

        borrowerExtra.FamJobCompType = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppExtraEmployerType));
        borrowerExtra.FamJobCompName = Utils.getNotNullText((EditText) v.findViewById(R.id.etLoanAppExtraEmployerName));

        borrowerExtra.FamBusinessShopType = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppExtraShopOwner));
        borrowerExtra.FamBusinessType = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppExtraBusinessType));

        borrowerExtra.FamAgriLandOwner = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureLandOwner));
        borrowerExtra.FamAgriLandType = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureLandType));
        borrowerExtra.FamAgriLandArea = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppExtraAgricultureArea));

        borrowerExtra.FamOtherIncomeType = Utils.getNotNullText((EditText) v.findViewById(R.id.etLoanAppExtraOtherIncomeOther));

        borrowerExtra.save();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        RangeCategory rangeCategory;
        switch (adapterView.getId()) {
            case R.id.spinLoanAppExtraOccupationType:
                //Log.d("OnItemSelected","done");
                View v = getView();
                View viewService = v.findViewById(R.id.lyLoanAppExtraIncomeService);
                View viewBusiness = v.findViewById(R.id.lyLoanAppExtraIncomeBusiness);
                View viewAgri = v.findViewById(R.id.lyLoanAppExtraIncomeAgri);
                View viewOther = v.findViewById(R.id.lyLoanAppExtraIncomeOther);
                if (BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing") {
                    rangeCategory = (RangeCategory) adapterView.getSelectedItem();
                    viewService.setVisibility(rangeCategory.DescriptionEn.equals("Service") ? View.VISIBLE : View.GONE);
                    viewBusiness.setVisibility(rangeCategory.DescriptionEn.equals("Business") ? View.VISIBLE : View.GONE);
                    viewAgri.setVisibility(rangeCategory.DescriptionEn.equals("Agriculture") ? View.VISIBLE : View.GONE);
                    viewOther.setVisibility(rangeCategory.DescriptionEn.equals("Other") ? View.VISIBLE : View.GONE);
                }
                if (BuildConfig.APPLICATION_ID == "net.softeksol.seil.groupfin" || BuildConfig.APPLICATION_ID == "net.softeksol.seil.groupfin.coorigin" || BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.groupfin.sbicolending") ) {
                    viewService.setVisibility(View.GONE);
                    viewBusiness.setVisibility(View.GONE);
                    viewAgri.setVisibility(View.GONE);
                    viewOther.setVisibility(View.GONE);
                }
                break;
            case R.id.spinLoanAppExtraChildren:
                rangeCategory = (RangeCategory) adapterView.getSelectedItem();
                Spinner spinnerSchoolingChildren = (Spinner) getView().findViewById(R.id.spinLoanAppExtraChildrenSchooling);
                ;
                Spinner spinnerMonthlySpendOnChildren = (Spinner) getView().findViewById(R.id.spinLoanAppExtraChildrenSpending);
                ;

                if (Integer.parseInt(rangeCategory.RangeCode) > 0) {
                    //Log.d("Children","Schooling Children");
                    rlaSchoolingChildren = new AdapterListRange(this.getContext(), Utils.getList(0, Integer.parseInt(rangeCategory.RangeCode), 1, 1, null), false);
                    spinnerSchoolingChildren.setAdapter(rlaSchoolingChildren);
                    rlaSchoolingChildren.notifyDataSetChanged();
                    Utils.setSpinnerPosition(spinnerSchoolingChildren, borrowerExtra.SchoolingChildren);
                    spinnerSchoolingChildren.setVisibility(View.VISIBLE);
                    spinnerMonthlySpendOnChildren.setVisibility(View.VISIBLE);
                } else {
                    spinnerSchoolingChildren.setVisibility(View.GONE);
                    spinnerMonthlySpendOnChildren.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public String getName() {
        return "Other Details";
    }

    public interface OnFragmentBorrowerExtraInteractionListener {
        void onFragmentBorrowerExtraInteraction(Borrower borrower);
    }

}
