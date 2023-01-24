package com.softeksol.paisalo.jlgsourcing.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListBorrowerFamilyMembers;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyMember;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link FragmentFamilyIncome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFamilyIncome extends AbsFragment {
    private ActivityLoanApplication activity;
    private AdapterListBorrowerFamilyMembers adapterFamilyMembersList;
    private BorrowerFamilyMember familyMember;
    private ArrayList<RangeCategory> genders;
    private TextInputEditText tietName, tietAge, tietBusiness, tietIncome;

    public FragmentFamilyIncome() {
        // Required empty public constructor
    }

    public static FragmentFamilyIncome newInstance() {
        FragmentFamilyIncome fragment = new FragmentFamilyIncome();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ActivityLoanApplication) getActivity();
        genders = new ArrayList<>();
        genders.add(new RangeCategory("Male", "Gender"));
        genders.add(new RangeCategory("Female", "Gender"));
        genders.add(new RangeCategory("TGender", "Gender"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_family_income, container, false);
        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);
        ListView lvcFamilyMembers = (ListView) v.findViewById(R.id.lvcFamilyMembers);
        lvcFamilyMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                familyMember = (BorrowerFamilyMember) parent.getItemAtPosition(position);
                showFamilyMemberDialog(familyMember);
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fabFamilyAddMember);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                familyMember = new BorrowerFamilyMember();
                familyMember.associateBorrower(activity.getBorrower());
                showFamilyMemberDialog(familyMember);
            }
        });

        adapterFamilyMembersList = new AdapterListBorrowerFamilyMembers(getContext(), R.layout.layout_item_borrower_family, activity.getBorrower().getFiFamilyMembers());
        lvcFamilyMembers.setAdapter(adapterFamilyMembersList);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public String getName() {
        return "Family Members' Income";
    }

    private void showFamilyMemberDialog(final BorrowerFamilyMember familyMember) {
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_new_family_member, null);
        showMember(dialogView, familyMember);
        AppCompatButton acbAddMember = (AppCompatButton) dialogView.findViewById(R.id.acbtnAddmember);
        AppCompatButton acbCancel = (AppCompatButton) dialogView.findViewById(R.id.acbtnCancel);
        AppCompatButton acbDelete = (AppCompatButton) dialogView.findViewById(R.id.acbtnDelete);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        acbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        acbAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUpdateMember(dialogView, familyMember);
                dialog.dismiss();
            }
        });
        acbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (familyMember.exists()) {
                    familyMember.delete();
                    refreshMembers();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addUpdateMember(View dialogView, BorrowerFamilyMember familyMember) {
        tietName = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemName);
        tietAge = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemAge);
        tietBusiness = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemBusiness);
        tietIncome = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemIncome);

        AppCompatSpinner acsRelationship = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemRelationship);
        AppCompatSpinner acsGender = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemGender);
        AppCompatSpinner acsHealth = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemHealth);
        AppCompatSpinner acsEducation = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemEducation);
        AppCompatSpinner acsSchoolType = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemSchoolType);
        AppCompatSpinner acsBusinessTupe = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemBusinessType);
        AppCompatSpinner acsIncomeType = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemIncomeType);

        familyMember.setAge(Integer.parseInt(tietAge.getText().toString()));
        familyMember.setMemName(tietName.getText().toString());
        familyMember.setBusiness(tietBusiness.getText().toString());
        familyMember.setIncome(Integer.parseInt(tietIncome.getText().toString()));

        familyMember.setRelationWBorrower(Utils.getSpinnerStringValue(acsRelationship));
        familyMember.setGender(Utils.getSpinnerStringValue(acsGender));
        familyMember.setHealth(Utils.getSpinnerStringValue(acsHealth));
        familyMember.setEducatioin(Utils.getSpinnerStringValue(acsEducation));
        familyMember.setSchoolType(Utils.getSpinnerStringValue(acsSchoolType));
        familyMember.setBusinessType(Utils.getSpinnerStringValue(acsBusinessTupe));
        familyMember.setIncomeType(Utils.getSpinnerStringValue(acsIncomeType));
        familyMember.save();
        //activity.getBorrower().getFiFamilyMembers().add(familyMember);
        //Log.d("MemberList",activity.getBorrower().getFiFamilyMembers().toString());
        refreshMembers();
    }

    private void showMember(View dialogView, BorrowerFamilyMember familyMember) {
        tietName = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemName);
        tietAge = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemAge);
        tietBusiness = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemBusiness);
        tietIncome = (TextInputEditText) dialogView.findViewById(R.id.tietNewMemIncome);

        AppCompatSpinner acsRelationship = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemRelationship);
        AppCompatSpinner acsGender = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemGender);
        AppCompatSpinner acsHealth = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemHealth);
        AppCompatSpinner acsEducation = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemEducation);
        AppCompatSpinner acsSchoolType = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemSchoolType);
        AppCompatSpinner acsBusinessTupe = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemBusinessType);
        AppCompatSpinner acsIncomeType = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewMemIncomeType);

        acsGender.setAdapter(new AdapterListRange(getContext(), genders, false));
        acsRelationship.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("relationship"), false));
        acsHealth.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("health"), false));
        acsEducation.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("education"), false));
        acsSchoolType.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("school-type"), false));
        acsBusinessTupe.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("business-type"), false));
        acsIncomeType.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("income-type"), false));

        tietName.setText(Utils.getNotNullString(familyMember.getMemName()));
        tietAge.setText(Utils.getNotNullString(familyMember.getAge()));
        tietBusiness.setText(Utils.getNotNullString(familyMember.getBusiness()));
        tietIncome.setText(Utils.getNotNullString(familyMember.getIncome()));

        Utils.setOnFocuseSelect(tietAge, "0");
        Utils.setOnFocuseSelect(tietIncome, "0");

        Utils.setSpinnerPosition(acsRelationship, familyMember.getRelationWBorrower());
        Utils.setSpinnerPosition(acsGender, familyMember.getGender());
        Utils.setSpinnerPosition(acsHealth, familyMember.getHealth());
        Utils.setSpinnerPosition(acsEducation, familyMember.getEducatioin());
        Utils.setSpinnerPosition(acsSchoolType, familyMember.getSchoolType());
        Utils.setSpinnerPosition(acsBusinessTupe, familyMember.getBusiness());
        Utils.setSpinnerPosition(acsIncomeType, familyMember.getIncomeType());
    }

    private void refreshMembers() {
        adapterFamilyMembersList.clear();
        adapterFamilyMembersList.addAll(activity.getBorrower().getFiFamilyMembers());
        adapterFamilyMembersList.notifyDataSetChanged();
    }
}
