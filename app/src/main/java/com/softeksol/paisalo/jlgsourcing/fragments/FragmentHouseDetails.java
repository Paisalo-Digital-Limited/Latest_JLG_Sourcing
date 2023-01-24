package com.softeksol.paisalo.jlgsourcing.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyExpenses;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHouseDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHouseDetails extends AbsFragment {
    private BorrowerFamilyExpenses expense;
    private ActivityLoanApplication activity;
    private AppCompatSpinner acspHomeType, acspHomeRoofType, acspToiletType, acspLiveingWithSpouse;

    public FragmentHouseDetails() {
        // Required empty public constructor
    }

    public static FragmentHouseDetails newInstance() {
        return new FragmentHouseDetails();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ActivityLoanApplication) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_house_details, container, false);
        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);

        acspHomeType = (AppCompatSpinner) v.findViewById(R.id.acspHomeType);
        acspHomeRoofType = (AppCompatSpinner) v.findViewById(R.id.acspHomeRoofType);
        acspToiletType = (AppCompatSpinner) v.findViewById(R.id.acspToiletType);
        acspLiveingWithSpouse = (AppCompatSpinner) v.findViewById(R.id.acspLivigWithSpouse);

        acspHomeType.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("house-type"), false));
        acspHomeRoofType.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("house-roof-type"), false));
        acspToiletType.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("toilet-type"), false));
        acspLiveingWithSpouse.setAdapter(new AdapterListRange(this.getContext(), Utils.getList(1, 6, 1, 1, "Year(s)"), true));

        return v;
    }

    @Override
    public String getName() {
        return "House Details";
    }

    private void setDataToView(View v) {
        Utils.setSpinnerPosition(acspHomeType, expense.getHomeType());
        Utils.setSpinnerPosition(acspHomeRoofType, expense.getHomeRoofType());
        Utils.setSpinnerPosition(acspToiletType, expense.getToiletType());
        Utils.setSpinnerPosition(acspLiveingWithSpouse, expense.getLivingWSpouse());
    }

    private void getDataFromView(View v) {
        expense.setHomeType(Utils.getSpinnerStringValue(acspHomeType));
        expense.setHomeRoofType(Utils.getSpinnerStringValue(acspHomeRoofType));
        expense.setToiletType(Utils.getSpinnerStringValue(acspToiletType));
        expense.setLivingWSpouse(Utils.getSpinnerStringValue(acspLiveingWithSpouse));

        activity.getBorrower().associateBorrowerFamilyExpenses(expense);
        expense.save();
    }

    @Override
    public void onResume() {
        super.onResume();
        expense = activity.getBorrower().getFiFamilyExpenses();
        setDataToView(getView());
    }

    @Override
    public void onPause() {
        getDataFromView(getView());
        super.onPause();
    }

}
