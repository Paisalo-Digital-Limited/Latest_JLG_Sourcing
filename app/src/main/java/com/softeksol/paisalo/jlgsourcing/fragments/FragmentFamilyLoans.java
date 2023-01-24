package com.softeksol.paisalo.jlgsourcing.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListBorrowerFamilyLoans;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyLoan;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link FragmentFamilyLoans#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFamilyLoans extends AbsFragment {
    private ActivityLoanApplication activity;
    private AdapterListBorrowerFamilyLoans adapterListBorrowerFamilyLoans;
    private BorrowerFamilyLoan familyLoan;
    private ArrayList<RangeCategory> lenderType;

    public FragmentFamilyLoans() {
        // Required empty public constructor
    }

    public static FragmentFamilyLoans newInstance() {
        return new FragmentFamilyLoans();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ActivityLoanApplication) getActivity();
        lenderType = new ArrayList<>();
        lenderType.add(new RangeCategory("Organised Sector", "Lender Type"));
        lenderType.add(new RangeCategory("Unorganised Sector", "Lender Type"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_family_loans, container, false);
        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);
        ListView lvcFamilyLoans = (ListView) v.findViewById(R.id.lvcFamilyLoans);

        lvcFamilyLoans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                familyLoan = (BorrowerFamilyLoan) parent.getItemAtPosition(position);
                showFamilyLoanDialog(familyLoan);
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fabFamilyAddLoan);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                familyLoan = new BorrowerFamilyLoan();
                familyLoan.associateBorrower(activity.getBorrower());
                showFamilyLoanDialog(familyLoan);
            }
        });

        adapterListBorrowerFamilyLoans = new AdapterListBorrowerFamilyLoans(getContext(), R.layout.layout_item_borrower_loan, activity.getBorrower().getFiFamilyLoans());
        lvcFamilyLoans.setAdapter(adapterListBorrowerFamilyLoans);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public String getName() {
        return "Family Borrowings";
    }

    private void showFamilyLoanDialog(final BorrowerFamilyLoan familyLoan) {
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_new_loan_detail, null);
        showMember(dialogView, familyLoan);
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
                addUpdateLoan(dialogView, familyLoan);
                dialog.dismiss();
            }
        });
        acbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (familyLoan.exists()) {
                    familyLoan.delete();
                    refreshFamilyLoans();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addUpdateLoan(View dialogView, BorrowerFamilyLoan familyLoan) {
        TextInputEditText tietLenderName = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderName);
        TextInputEditText tietLoanAmt = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderLoanAMount);
        TextInputEditText tietLoanEMI = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderEMIAmount);
        TextInputEditText tietLoanBal = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderBalanceAmount);

        AppCompatSpinner acsLenderType = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewLenderType);
        AppCompatSpinner acsLoanUsedBy = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewLenderLoanUsedBy);
        AppCompatSpinner acsLoanReason = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewLenderLoanReason);

        CheckBox chkIsMFI = (CheckBox) dialogView.findViewById(R.id.chkNewLenderMFI);

        familyLoan.setLenderName(tietLenderName.getText().toString());
        familyLoan.setLoanAmount(Integer.parseInt(tietLoanAmt.getText().toString()));
        familyLoan.setLoanEMIAmount(Integer.parseInt(tietLoanEMI.getText().toString()));
        familyLoan.setLoanBalanceAmount(Integer.parseInt(tietLoanBal.getText().toString()));

        familyLoan.setLenderType(Utils.getSpinnerStringValue(acsLenderType));
        familyLoan.setLoneeName(Utils.getSpinnerStringValue(acsLoanUsedBy));
        familyLoan.setLoanReason(Utils.getSpinnerStringValue(acsLoanReason));

        familyLoan.setIsMFI(chkIsMFI.isChecked() ? "Y" : "N");
        familyLoan.save();
        //Log.d("Loan Detail", familyLoan.toString());
        refreshFamilyLoans();
    }

    private void showMember(View dialogView, BorrowerFamilyLoan familyLoan) {
        TextInputEditText tietLenderName = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderName);
        TextInputEditText tietLoanAmt = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderLoanAMount);
        TextInputEditText tietLoanEMI = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderEMIAmount);
        TextInputEditText tietLoanBal = (TextInputEditText) dialogView.findViewById(R.id.tietNewLenderBalanceAmount);

        AppCompatSpinner acsLenderType = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewLenderType);
        AppCompatSpinner acsLoanUsedBy = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewLenderLoanUsedBy);
        AppCompatSpinner acsLoanReason = (AppCompatSpinner) dialogView.findViewById(R.id.acspNewLenderLoanReason);

        CheckBox chkIsMFI = (CheckBox) dialogView.findViewById(R.id.chkNewLenderMFI);

        acsLenderType.setAdapter(new AdapterListRange(getContext(), lenderType, true));
        acsLoanUsedBy.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("relationship"), false));
        acsLoanReason.setAdapter(new AdapterListRange(getContext(), RangeCategory.getRangesByCatKey("loan_purpose"), false));

        tietLenderName.setText(Utils.getNotNullString(familyLoan.getLenderName()));
        tietLoanAmt.setText(Utils.getNotNullString(familyLoan.getLoanAmount()));
        tietLoanEMI.setText(Utils.getNotNullString(familyLoan.getLoanEMIAmount()));
        tietLoanBal.setText(Utils.getNotNullString(familyLoan.getLoanBalanceAmount()));

        Utils.setOnFocuseSelect(tietLoanAmt, "0");
        Utils.setOnFocuseSelect(tietLoanEMI, "0");
        Utils.setOnFocuseSelect(tietLoanBal, "0");


        Utils.setSpinnerPosition(acsLenderType, familyLoan.getLenderType());
        Utils.setSpinnerPosition(acsLoanUsedBy, familyLoan.getLoneeName());
        Utils.setSpinnerPosition(acsLoanReason, familyLoan.getLoanReason());

        chkIsMFI.setChecked(Utils.getNotNullString(familyLoan.getIsMFI()).equals("Y"));
    }

    private void refreshFamilyLoans() {
        adapterListBorrowerFamilyLoans.clear();
        adapterListBorrowerFamilyLoans.addAll(activity.getBorrower().getFiFamilyLoans());
        adapterListBorrowerFamilyLoans.notifyDataSetChanged();

    }
}
