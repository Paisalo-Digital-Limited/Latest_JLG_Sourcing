package com.softeksol.paisalo.jlgsourcing.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtra;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory_Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBorrowerPersonal_Additional#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBorrowerPersonal_Additional extends AbsFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ActivityLoanApplication activity;
    private BorrowerExtra borrowerExtra;
    private Borrower borrower;
    String newDate;
    private OnFragmentBorrowerPersonal_AddInteractionListener mListener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private ImageView imgViewCal,ImgViewCal2;
    private String mParam2;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    EditText editFORM60_TNX_DT,editFORM60_SUBMISSIONDATE;
    private long borrowerId;
    private AdapterListRange AGRICULTURAL_INCOME, SOC_ATTR_2_INCOME, ANNUAL_INCOME, OTHER_THAN_AGRICULTURAL_INCOME, MARITAL_STATUS, OCCUPATION_TYPE, RESERVATN_CATEGORY;
    private ArrayAdapter<String> SOC_ATTR_4_SPL_ABLD,SOC_ATTR_5_SPL_SOC_CTG,VISUALLY_IMPAIRED_YN,FORM60_PAN_APPLIED_YN;
    ArrayList<String> items=new ArrayList<String>();
    Spinner spinAGRICULTURAL_INCOME,spinSOC_ATTR_2_INCOME,spinSOC_ATTR_4_SPL_ABLD,spinANNUAL_INCOME,spinOTHER_THAN_AGRICULTURAL_INCOME;







//


    public FragmentBorrowerPersonal_Additional() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBorrowerPersonal_Additional.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBorrowerPersonal_Additional newInstance(String param1, String param2) {
        FragmentBorrowerPersonal_Additional fragment = new FragmentBorrowerPersonal_Additional();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static AbsFragment newInstance(long borrower_id) {
        FragmentBorrowerPersonal_Additional fragment = new FragmentBorrowerPersonal_Additional();
        Bundle args = new Bundle();
        args.putLong(Global.BORROWER_TAG, borrower_id);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_borrower_personal__additional, container, false);
        items.add("YES");
        items.add("NO");
        imgViewCal=v.findViewById(R.id.imgViewCal);
        ImgViewCal2=v.findViewById(R.id.imgViewCal2);

        editFORM60_SUBMISSIONDATE=v.findViewById(R.id.editFORM60_SUBMISSIONDATE);
        editFORM60_TNX_DT=v.findViewById(R.id.editFORM60_TNX_DT);
        editFORM60_SUBMISSIONDATE.setEnabled(false);
        editFORM60_TNX_DT.setEnabled(false);
                spinAGRICULTURAL_INCOME=v.findViewById(R.id.spinAgriIncome);
        AGRICULTURAL_INCOME = new AdapterListRange(this.getContext(), Utils.getList(1, 30, 1, 1000, "Rupees"), true);
                spinAGRICULTURAL_INCOME.setAdapter(AGRICULTURAL_INCOME);
                spinSOC_ATTR_2_INCOME=v.findViewById(R.id.spinIncome);
        SOC_ATTR_2_INCOME = new AdapterListRange(this.getContext(), Utils.getList(1, 30, 1, 1000, "Rupees"), true);
            spinSOC_ATTR_2_INCOME.setAdapter(SOC_ATTR_2_INCOME);
         spinSOC_ATTR_4_SPL_ABLD=v.findViewById(R.id.spinSpecialAbility);
        SOC_ATTR_4_SPL_ABLD = new ArrayAdapter<String>(this.getContext(),R.layout.spinner_card_orange,R.id.text_cname, items);
        spinSOC_ATTR_4_SPL_ABLD.setAdapter(SOC_ATTR_4_SPL_ABLD);
                spinANNUAL_INCOME=v.findViewById(R.id.spinAnnualIncome);
        ANNUAL_INCOME = new AdapterListRange(this.getContext(), Utils.getList(1, 30, 1, 12000, "Rupees"), true);
            spinANNUAL_INCOME.setAdapter(ANNUAL_INCOME);
        Spinner spinSOC_ATTR_5_SPL_SOC_CTG=v.findViewById(R.id.spinSpecialSocialCategory);
        SOC_ATTR_5_SPL_SOC_CTG = new ArrayAdapter<String>(this.getContext(),R.layout.spinner_card_orange,R.id.text_cname, items);
        spinSOC_ATTR_5_SPL_SOC_CTG.setAdapter(SOC_ATTR_5_SPL_SOC_CTG);
               Spinner spinVISUALLY_IMPAIRED_YN=v.findViewById(R.id.spinVisuallyImpaired);
        VISUALLY_IMPAIRED_YN = new ArrayAdapter<String>(this.getContext(),R.layout.spinner_card_orange,R.id.text_cname, items);
        spinVISUALLY_IMPAIRED_YN.setAdapter(VISUALLY_IMPAIRED_YN);
               Spinner spinFORM60_PAN_APPLIED_YN=v.findViewById(R.id.spinFORM60_PAN_APPLIED_YN);
        FORM60_PAN_APPLIED_YN = new ArrayAdapter<String>(this.getContext(),R.layout.spinner_card_orange,R.id.text_cname, items);
        spinFORM60_PAN_APPLIED_YN.setAdapter(FORM60_PAN_APPLIED_YN);
                spinOTHER_THAN_AGRICULTURAL_INCOME=v.findViewById(R.id.spinOTHER_THAN_AGRICULTURAL_INCOME);
        OTHER_THAN_AGRICULTURAL_INCOME = new AdapterListRange(this.getContext(), Utils.getList(1, 30, 1, 12000, "Rupees"), true);
            spinOTHER_THAN_AGRICULTURAL_INCOME.setAdapter(OTHER_THAN_AGRICULTURAL_INCOME);
        Spinner spinMARITAL_STATUS=v.findViewById(R.id.spinMARITAL_STATUS);
        MARITAL_STATUS = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("marrital_status")).queryList(), false);
        spinMARITAL_STATUS.setAdapter(MARITAL_STATUS);
        Spinner spinOCCUPATION_TYPE=v.findViewById(R.id.spinOCCUPATION_TYPE);
        OCCUPATION_TYPE = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("other_employment")).queryList(), false);
        spinOCCUPATION_TYPE.setAdapter(OCCUPATION_TYPE);
               Spinner spinRESERVATN_CATEGORY  =v.findViewById(R.id.spinRESERVATN_CATEGORY);
        RESERVATN_CATEGORY = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("caste")).queryList(), false);
                spinRESERVATN_CATEGORY.setAdapter(RESERVATN_CATEGORY);
        imgViewCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
            }
        });

        ImgViewCal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v);
                //showDate(year, month+1, day);
            }
        });
        return  v;
    }
    public void openDatePickerDialog(final View v) {
        // Get Current Date
        Calendar cal=Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate =year  + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date date = dt.parse(selectedDate);
                         newDate= dt.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    switch (v.getId()) {
                        case R.id.imgViewCal:
                            editFORM60_TNX_DT.setText(newDate);
                            break;
                        case R.id.imgViewCal2:
                            editFORM60_SUBMISSIONDATE.setText(newDate);
                            break;
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    try {
                        showDate(arg1, arg2+1, arg3);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };
    private DatePickerDialog.OnDateSetListener myDateListener1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    try {
                        showDate2(arg1, arg2+1, arg3);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };

    private void showDate(int year, int month, int day) throws ParseException {
        String todaysDate=day+ "-"+ (month) + "-" + year;
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
        Date dateObj = curFormater.parse(todaysDate);
//        Date date = new Date(year,month,day);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate= curFormater.format(dateObj);
        editFORM60_TNX_DT.setText(strDate);
    }

    private void showDate2(int year, int month, int day) throws ParseException {
        String todaysDate=day+ "-"+ (month) + "-" + year;
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
        Date dateObj = curFormater.parse(todaysDate);
//        Date date = new Date(year,month,day);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate= curFormater.format(dateObj);
        editFORM60_TNX_DT.setText(strDate);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBorrowerPersonal_AddInteractionListener) {
            mListener = (OnFragmentBorrowerPersonal_AddInteractionListener) context;
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

    private void setDataToView(View v) {

        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinAgriIncome),borrowerExtra.AGRICULTURAL_INCOME);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinIncome),borrowerExtra.SOC_ATTR_2_INCOME);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinAnnualIncome),borrowerExtra.ANNUAL_INCOME);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinOTHER_THAN_AGRICULTURAL_INCOME),borrowerExtra.OTHER_THAN_AGRICULTURAL_INCOME);
        int spinnerPositionVisuallyImpaired= VISUALLY_IMPAIRED_YN.getPosition(borrowerExtra.VISUALLY_IMPAIRED_YN);
        ((Spinner)v.findViewById(R.id.spinVisuallyImpaired)).setSelection(spinnerPositionVisuallyImpaired);


        int spinnerPositionSpecialCategory= SOC_ATTR_5_SPL_SOC_CTG.getPosition(borrowerExtra.SOC_ATTR_5_SPL_SOC_CTG);
        ((Spinner)v.findViewById(R.id.spinSpecialSocialCategory)).setSelection(spinnerPositionSpecialCategory);


        int spinnerPositionPANApplied= FORM60_PAN_APPLIED_YN.getPosition(borrowerExtra.FORM60_PAN_APPLIED_YN);
        ((Spinner)v.findViewById(R.id.spinFORM60_PAN_APPLIED_YN)).setSelection(spinnerPositionPANApplied);

        int spinnerPositionSpecialAbility= SOC_ATTR_4_SPL_ABLD.getPosition(borrowerExtra.SOC_ATTR_4_SPL_ABLD);
        ((Spinner)v.findViewById(R.id.spinSpecialAbility)).setSelection(spinnerPositionSpecialAbility);

        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinMARITAL_STATUS),borrowerExtra.MARITAL_STATUS);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinRESERVATN_CATEGORY),borrowerExtra.RESERVATN_CATEGORY);
        Utils.setSpinnerPosition((Spinner) v.findViewById(R.id.spinOCCUPATION_TYPE),borrowerExtra.OCCUPATION_TYPE);

        ((EditText) v.findViewById(R.id.editLandHold)).setText(borrowerExtra.SOC_ATTR_3_LAND_HOLD);
        ((EditText) v.findViewById(R.id.editEducationalCode)).setText(borrowerExtra.EDUCATION_CODE);
        ((EditText) v.findViewById(R.id.editPlaceOfBirth)).setText(borrowerExtra.PLACE_OF_BIRTH);
        ((EditText) v.findViewById(R.id.editMailId)).setText(borrowerExtra.EMAIL_ID);
        ((EditText) v.findViewById(R.id.editFORM60_TNX_DT)).setText(borrowerExtra.FORM60_TNX_DT);
        ((EditText) v.findViewById(R.id.editFORM60_SUBMISSIONDATE)).setText(borrowerExtra.FORM60_SUBMISSIONDATE);
        ((EditText) v.findViewById(R.id.editMOTHER_TITLE)).setText(borrowerExtra.MOTHER_TITLE);
         ((EditText) v.findViewById(R.id.editMOTHER_FIRST_NAME)).setText(borrowerExtra.MOTHER_FIRST_NAME);
        ((EditText) v.findViewById(R.id.editMOTHER_MIDDLE_NAME)).setText(borrowerExtra.MOTHER_MIDDLE_NAME);
         ((EditText) v.findViewById(R.id.editMOTHER_LAST_NAME)).setText(borrowerExtra.MOTHER_LAST_NAME);
        ((EditText) v.findViewById(R.id.editMOTHER_MAIDEN_NAME)).setText(borrowerExtra.MOTHER_MAIDEN_NAME);
         ((EditText) v.findViewById(R.id.editSPOUSE_TITLE)).setText(borrowerExtra.SPOUSE_TITLE);
        ((EditText) v.findViewById(R.id.editSPOUSE_FIRST_NAME)).setText(borrowerExtra.SPOUSE_FIRST_NAME);
         ((EditText) v.findViewById(R.id.editSPOUSE_MIDDLE_NAME)).setText(borrowerExtra.SPOUSE_MIDDLE_NAME);
        ((EditText) v.findViewById(R.id.editSPOUSE_LAST_NAME)).setText(borrowerExtra.SPOUSE_LAST_NAME);
         ((EditText) v.findViewById(R.id.editAPPLICNT_TITLE)).setText(borrowerExtra.APPLICNT_TITLE);
        ((EditText) v.findViewById(R.id.editFATHER_TITLE)).setText(borrowerExtra.FATHER_TITLE);
         ((EditText) v.findViewById(R.id.editFather_FIRST_NAME)).setText(borrowerExtra.FATHER_FIRST_NAME);
        ((EditText) v.findViewById(R.id.editFATHER_MIDDLE_NAME)).setText(borrowerExtra.FATHER_MIDDLE_NAME);
        ((EditText) v.findViewById(R.id.editFATHER_LAST_NAME)).setText(borrowerExtra.FATHER_LAST_NAME);
        ((EditText) v.findViewById(R.id.editYearsInBusiness)).setText(borrowerExtra.Years_In_Business);

    }

    @Override
    public void onPause() {
            getDataFromView(getView());
            super.onPause();

    }

    private void getDataFromView(View view) {


        borrowerExtra.AGRICULTURAL_INCOME=Utils.getSpinnerStringValue((Spinner) view.findViewById(R.id.spinAgriIncome));
        borrowerExtra.SOC_ATTR_2_INCOME=Utils.getSpinnerStringValue((Spinner) view.findViewById(R.id.spinIncome));
        borrowerExtra.ANNUAL_INCOME=Utils.getSpinnerStringValue((Spinner) view.findViewById(R.id.spinAnnualIncome));
        borrowerExtra.OTHER_THAN_AGRICULTURAL_INCOME=Utils.getSpinnerStringValue((Spinner) view.findViewById(R.id.spinOTHER_THAN_AGRICULTURAL_INCOME));
        borrowerExtra.VISUALLY_IMPAIRED_YN=((Spinner) view.findViewById(R.id.spinVisuallyImpaired)).getSelectedItem().toString();
        borrowerExtra.SOC_ATTR_5_SPL_SOC_CTG=((Spinner) view.findViewById(R.id.spinSpecialSocialCategory)).getSelectedItem().toString();
        borrowerExtra.FORM60_PAN_APPLIED_YN=((Spinner) view.findViewById(R.id.spinFORM60_PAN_APPLIED_YN)).getSelectedItem().toString();
        borrowerExtra.MARITAL_STATUS=Utils.getSpinnerStringValue((Spinner) view.findViewById(R.id.spinMARITAL_STATUS));
        borrowerExtra.RESERVATN_CATEGORY=Utils.getSpinnerStringValue((Spinner) view.findViewById(R.id.spinRESERVATN_CATEGORY));
        borrowerExtra.SOC_ATTR_4_SPL_ABLD=((Spinner) view.findViewById(R.id.spinSpecialAbility)).getSelectedItem().toString();
        borrowerExtra.OCCUPATION_TYPE=Utils.getSpinnerStringValue((Spinner) view.findViewById(R.id.spinOCCUPATION_TYPE));

        borrowerExtra.SOC_ATTR_3_LAND_HOLD=((EditText) view.findViewById(R.id.editLandHold)).getText().toString();
                borrowerExtra.EDUCATION_CODE=((EditText) view.findViewById(R.id.editEducationalCode)).getText().toString();
        borrowerExtra.PLACE_OF_BIRTH=((EditText) view.findViewById(R.id.editPlaceOfBirth)).getText().toString();
                borrowerExtra.EMAIL_ID=((EditText) view.findViewById(R.id.editMailId)).getText().toString();
        borrowerExtra.FORM60_TNX_DT=((EditText) view.findViewById(R.id.editFORM60_TNX_DT)).getText().toString();
                borrowerExtra.FORM60_SUBMISSIONDATE=((EditText) view.findViewById(R.id.editFORM60_SUBMISSIONDATE)).getText().toString();
                borrowerExtra.FORM60SUBMISSIONDATE=((EditText) view.findViewById(R.id.editFORM60_SUBMISSIONDATE)).getText().toString();
        borrowerExtra.MOTHER_TITLE=((EditText) view.findViewById(R.id.editMOTHER_TITLE)).getText().toString();
                borrowerExtra.MOTHER_FIRST_NAME=((EditText) view.findViewById(R.id.editMOTHER_FIRST_NAME)).getText().toString();
        borrowerExtra.MOTHER_MIDDLE_NAME=((EditText) view.findViewById(R.id.editMOTHER_MIDDLE_NAME)).getText().toString();
                borrowerExtra.MOTHER_LAST_NAME=((EditText) view.findViewById(R.id.editMOTHER_LAST_NAME)).getText().toString();
        borrowerExtra.MOTHER_MAIDEN_NAME=((EditText) view.findViewById(R.id.editMOTHER_MAIDEN_NAME)).getText().toString();
                borrowerExtra.SPOUSE_TITLE=((EditText) view.findViewById(R.id.editSPOUSE_TITLE)).getText().toString();
        borrowerExtra.SPOUSE_FIRST_NAME=((EditText) view.findViewById(R.id.editSPOUSE_FIRST_NAME)).getText().toString();
                borrowerExtra.SPOUSE_MIDDLE_NAME=((EditText) view.findViewById(R.id.editSPOUSE_MIDDLE_NAME)).getText().toString();
        borrowerExtra.SPOUSE_LAST_NAME=((EditText) view.findViewById(R.id.editSPOUSE_LAST_NAME)).getText().toString();
                borrowerExtra.APPLICNT_TITLE=((EditText) view.findViewById(R.id.editAPPLICNT_TITLE)).getText().toString();
        borrowerExtra.FATHER_TITLE=((EditText) view.findViewById(R.id.editFATHER_TITLE)).getText().toString();
                borrowerExtra.FATHER_FIRST_NAME=((EditText) view.findViewById(R.id.editFather_FIRST_NAME)).getText().toString();
        borrowerExtra.FATHER_MIDDLE_NAME=((EditText) view.findViewById(R.id.editFATHER_MIDDLE_NAME)).getText().toString();
                borrowerExtra.FATHER_LAST_NAME=((EditText) view.findViewById(R.id.editFATHER_LAST_NAME)).getText().toString();
                borrowerExtra.Years_In_Business=((EditText) view.findViewById(R.id.editYearsInBusiness)).getText().toString();
        Log.d("TAG", "getDataFromView: yha tk chal rha h 1");
                borrowerExtra.save();

    }

    @Override
    public String getName() {
        return "Personal Data 2";
    }
    public interface OnFragmentBorrowerPersonal_AddInteractionListener{
        void onFragmentBorrowerPersonalInteraction(Borrower borrower);
    }
}