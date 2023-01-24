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
 * {@link FragmentBorrowerPersonal.OnFragmentBorrowerPersonalInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBorrowerPersonal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBorrowerPersonal extends AbsFragment implements AdapterView.OnItemSelectedListener {
    View v;

    private long borrowerId;
    private OnFragmentBorrowerPersonalInteractionListener mListener;
    private AdapterListRange rlaIncome, rlaCaste, rlaReligion, rlaMarritalStatus, rla1to9, rla1kto11k, rlaOwner;
    private ActivityLoanApplication activity;
    private Borrower borrower;
    private AdapterListRange rlaEarningMember, rlaEarningMemberOccupation, rlaEarningMemberIncome, rlaEMployerType, rlaShopOwner, rla0to9;
    private AdapterListRange rlaOtherAgriLandOwnership, rlaOtherBusinessType, rla0to11, rlaOtherAgriLandType, rlaPurposeType, rlaSchoolingChildren;
    private BorrowerExtra borrowerExtra;


    public FragmentBorrowerPersonal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param borrowerId Parameter.
     * @return A new instance of fragment FragmentBorrowerAadhar.
     */
    public static FragmentBorrowerPersonal newInstance(long borrowerId) {
        FragmentBorrowerPersonal fragment = new FragmentBorrowerPersonal();
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

        rlaCaste = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("caste")).queryList(), false);
        rlaReligion = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("religion")).queryList(), false);
        rlaOwner = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("land_owner")).queryList(), false);
        rlaMarritalStatus = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("marrital_status")).queryList(), false);

        //added 30 in end counter for making monthly income upto 30k for only e-vehicle
        rlaIncome = new AdapterListRange(this.getContext(), Utils.getList(1, 30, 1, 1000, "Rupees"), true);

        rla1to9 = new AdapterListRange(this.getContext(), Utils.getList(1, 9, 1, 1, null), false);

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

        rla0to11 = new AdapterListRange(this.getContext(), Utils.getList(1, 11, 1, 1, getString(R.string.acres)), true);
        rla0to9 = new AdapterListRange(this.getContext(), Utils.getList(0, 9, 1, 1, null), false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_borrower_personal, container, false);

        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);

        Spinner spinnerIncomeMonthly = (Spinner) v.findViewById(R.id.spinLoanAppPersonalIncomeMonthly);
        spinnerIncomeMonthly.setAdapter(rlaIncome);
        Spinner spinnerCaste = (Spinner) v.findViewById(R.id.spinLoanAppPersonalCaste);
        spinnerCaste.setAdapter(rlaCaste);
        Spinner spinnerReligion = (Spinner) v.findViewById(R.id.spinLoanAppPersonalReligion);
        spinnerReligion.setAdapter(rlaReligion);
        Spinner spinnerMarritalStatus = (Spinner) v.findViewById(R.id.spinLoanAppPersonalMarritalStatus);
        spinnerMarritalStatus.setAdapter(rlaMarritalStatus);
        Spinner spinnerHouseOwner = (Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentResidenceOwner);
        spinnerHouseOwner.setAdapter(rlaOwner);
        spinnerHouseOwner.setOnItemSelectedListener(this);
        Spinner spinnerHouseRent = (Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentHouseRent);
        spinnerHouseRent.setAdapter(rlaIncome);
        Spinner spinnerHouseResidingFor = (Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentResidenceDuration);
        spinnerHouseResidingFor.setAdapter(rla1to9);


        Spinner spinnerIncomeFuture = (Spinner) v.findViewById(R.id.spinLoanAppExtraIncomeFuture);
        spinnerIncomeFuture.setAdapter(rlaIncome);
        Spinner spinnerFamilyMembers = (Spinner) v.findViewById(R.id.spinLoanAppPersonalFamilyMembers);
        spinnerFamilyMembers.setAdapter(rla0to9);
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
        if (context instanceof OnFragmentBorrowerPersonalInteractionListener) {
            mListener = (OnFragmentBorrowerPersonalInteractionListener) context;
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
        borrower = activity.getBorrower();
        borrowerExtra = borrower.getBorrowerExtra();
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
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalIncomeMonthly), borrower.Income);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalCaste), borrower.Cast);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalReligion), borrower.Religion);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalMarritalStatus), borrower.isMarried);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentResidenceOwner), borrower.House_Owner);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentHouseRent), borrower.Rent_of_House);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentResidenceDuration), borrower.Live_In_Present_Place);

        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinLoanAppPersonalFamilyMembers), borrower.FAmily_member);

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
        borrower.Income = Integer.parseInt(Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalIncomeMonthly)));
        borrower.Cast = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalCaste));
        borrower.Religion = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalReligion));
        borrower.isMarried = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalMarritalStatus));
        borrower.House_Owner = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentResidenceOwner));
        borrower.Live_In_Present_Place = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentResidenceDuration));
        if (((RangeCategory) ((Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentResidenceOwner)).getSelectedItem()).DescriptionEn.equals("Other")) {
            borrower.Rent_of_House = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalPresentHouseRent));
        } else {
            borrower.Rent_of_House = 0;
        }
        borrower.FAmily_member = Utils.getSpinnerIntegerValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalFamilyMembers));

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
            case R.id.spinLoanAppPersonalPresentResidenceOwner:
                Boolean bol = ((RangeCategory) adapterView.getSelectedItem()).DescriptionEn.equals("Other");
                getView().findViewById(R.id.spinLoanAppPersonalPresentHouseRent).setVisibility(bol ? View.VISIBLE : View.GONE);
                break;
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
                if (BuildConfig.APPLICATION_ID == "net.softeksol.seil.groupfin" || BuildConfig.APPLICATION_ID == "net.softeksol.seil.groupfin.coorigin" || BuildConfig.APPLICATION_ID == "net.softeksol.seil.groupfin.sbicolending" ) {
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
        return "Personal Data 1";
    }

    public interface OnFragmentBorrowerPersonalInteractionListener {
        void onFragmentBorrowerPersonalInteraction(Borrower borrower);
    }

}
