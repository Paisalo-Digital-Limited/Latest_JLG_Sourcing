package com.softeksol.paisalo.jlgsourcing.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyExpenses;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFamilyExpense#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFamilyExpense extends AbsFragment {
    private BorrowerFamilyExpenses expense;
    private Borrower borrower;
    private ActivityLoanApplication activity;
    private TextInputEditText tietRent, tietFooding, tietEducation, tietHealth, tietTravelling, tietEntertainment, tietOthers;
    private AppCompatSpinner acspHomeType, acspHomeRoofType, acspToiletType, acspLiveingWithSpouse;

    public FragmentFamilyExpense() {
        // Required empty public constructor
    }

    public static FragmentFamilyExpense newInstance() {
        FragmentFamilyExpense fragment = new FragmentFamilyExpense();
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
        View v = inflater.inflate(R.layout.fragment_family_expense, container, false);
        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);
        tietRent = (TextInputEditText) v.findViewById(R.id.tietFamilyExpensesRent);
        tietFooding = (TextInputEditText) v.findViewById(R.id.tietFamilyExpensesFood);
        tietEducation = (TextInputEditText) v.findViewById(R.id.tietFamilyExpensesEducation);
        tietHealth = (TextInputEditText) v.findViewById(R.id.tietFamilyExpensesHealth);
        tietTravelling = (TextInputEditText) v.findViewById(R.id.tietFamilyExpensesTravelling);
        tietEntertainment = (TextInputEditText) v.findViewById(R.id.tietFamilyExpensesEntertainment);
        tietOthers = (TextInputEditText) v.findViewById(R.id.tietFamilyExpensesOthers);

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
        return "Family Expenses";
    }

    @Override
    public void onResume() {
        super.onResume();
        expense = activity.getBorrower().getFiFamilyExpenses();
        if (expense == null) {
            expense = new BorrowerFamilyExpenses();
        }
        setDataToView(getView());
    }

    @Override
    public void onPause() {
        getDataFromView(getView());
        super.onPause();
    }

    private void setDataToView(View v) {

        tietRent.setText(String.valueOf(expense.getRent()));
        tietFooding.setText(String.valueOf(expense.getFooding()));
        tietEducation.setText(String.valueOf(expense.getEducation()));
        tietHealth.setText(String.valueOf(expense.getHealth()));
        tietTravelling.setText(String.valueOf(expense.getTravelling()));
        tietEntertainment.setText(String.valueOf(expense.getEducation()));
        tietOthers.setText(String.valueOf(expense.getOthers()));
        Utils.setOnFocuseSelect(tietRent, "0");
        Utils.setOnFocuseSelect(tietFooding, "0");
        Utils.setOnFocuseSelect(tietEducation, "0");
        Utils.setOnFocuseSelect(tietHealth, "0");
        Utils.setOnFocuseSelect(tietTravelling, "0");
        Utils.setOnFocuseSelect(tietEntertainment, "0");
        Utils.setOnFocuseSelect(tietOthers, "0");

        Utils.setSpinnerPosition(acspHomeType, expense.getHomeType());
        Utils.setSpinnerPosition(acspHomeRoofType, expense.getHomeRoofType());
        Utils.setSpinnerPosition(acspToiletType, expense.getToiletType());
        Utils.setSpinnerPosition(acspLiveingWithSpouse, expense.getLivingWSpouse());

    }

    private void getDataFromView(View v) {
        expense.setRent(Utils.getNotNullInt(tietRent));
        expense.setFooding(Utils.getNotNullInt(tietFooding));
        expense.setEducation(Utils.getNotNullInt(tietEducation));
        expense.setHealth(Utils.getNotNullInt(tietHealth));
        expense.setTravelling(Utils.getNotNullInt(tietTravelling));
        expense.setEntertainment(Utils.getNotNullInt(tietEntertainment));
        expense.setOthers(Utils.getNotNullInt(tietOthers));

        expense.setHomeType(Utils.getSpinnerStringValue(acspHomeType));
        expense.setHomeRoofType(Utils.getSpinnerStringValue(acspHomeRoofType));
        expense.setToiletType(Utils.getSpinnerStringValue(acspToiletType));
        expense.setLivingWSpouse(Utils.getSpinnerStringValue(acspLiveingWithSpouse));

        activity.getBorrower().associateBorrowerFamilyExpenses(expense);
        expense.save();
    }

}
