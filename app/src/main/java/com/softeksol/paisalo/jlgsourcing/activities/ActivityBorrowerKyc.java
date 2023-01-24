package com.softeksol.paisalo.jlgsourcing.activities;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.RequestParams;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.AadharUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterRecViewListDocuments;
import com.softeksol.paisalo.jlgsourcing.entities.AadharData;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtraBank;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.OldFIById;
import com.softeksol.paisalo.jlgsourcing.handlers.AsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import cz.msebera.android.httpclient.Header;

import static com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff.validateCaseCode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ActivityBorrowerKyc extends AppCompatActivity implements View.OnClickListener,AdapterRecViewListDocuments.ItemListener, CameraUtils.OnCameraCaptureUpdate { //, CameraUtils.OnCameraCaptureUpdate
    private final AppCompatActivity activity = this;
    private Borrower borrower;
    //private BorrowerExtraBank borrowerExtraBank;
    private Uri uriPicture;
    private ImageView imgViewScanQR;
    private Manager manager;
    private long borrower_id;
    private CheckBox chkTvTopup;
    private AppCompatSpinner acspGender, acspRelationship, acspAadharState, acspOccupation,
            acspLoanAmount, acspBusinessDetail, acspLoanPurpose;
    private TextInputEditText tietAadharId, tietName, tietAge, tietDob, tietGuardian,
            tietAddress1, tietAddress2, tietAddress3, tietCity, tietPinCode, tietMobile,
            tietDrivingLic, tietPanNo, tietVoterId,
            tietFather, tietMother, tietBankCIF, tietBankAccount;
    private SearchView svOldCase;
    private TextView textViewFiDetails;
    private TextWatcher ageTextWatcher;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateSetListner;
    private MyTextWatcher aadharTextChangeListner;
    private RecyclerView recyclerView;
    private AdapterRecViewListDocuments adapterRecViewListDocuments;
    private DocumentStore documentPic;
    private boolean showSubmitBorrowerMenuItem = true;
    private LinearLayoutCompat llTopupCode;

    protected static final byte SEPARATOR_BYTE = (byte)255;
    protected static final int VTC_INDEX = 15;
    protected int emailMobilePresent, imageStartIndex, imageEndIndex;
    protected ArrayList<String> decodedData;
    protected String signature,email,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_entry);
        manager = (Manager) getIntent().getSerializableExtra(Global.MANAGER_TAG);

        //borrower = new Borrower();
        borrower = new Borrower(manager.Creator, manager.TAG, manager.FOCode, manager.AreaCd, IglPreferences.getPrefString(ActivityBorrowerKyc.this, SEILIGL.USER_ID, ""));

        borrower.fiExtra = null;
        //borrowerExtraBank=new BorrowerExtraBank(manager.Creator,manager.TAG);
        borrower.associateExtraBank(new BorrowerExtraBank(manager.Creator, manager.TAG));

        //borrower.fi
        borrower.isAadharVerified = "N";


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(actionBar.getTitle() + "    Borrower KYC");


//        ArrayList<RangeCategory> genders = new ArrayList<>();
//        genders.add(new RangeCategory("Female", "Gender"));

        ArrayList<RangeCategory> genders = new ArrayList<>();

        genders.add(new RangeCategory("Female", "Gender"));
        genders.add(new RangeCategory("Male", "Gender"));
        genders.add(new RangeCategory("Transgender", "Gender"));

//        if (BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing")) {
//            genders.add(new RangeCategory("Female", "Gender"));
//            genders.add(new RangeCategory("Male", "Gender"));
//            genders.add(new RangeCategory("Transgender", "Gender"));
//        } else if (BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.groupfin.sbicolending")){
//            genders.add(new RangeCategory("Female", "Gender"));
//            genders.add(new RangeCategory("Male", "Gender"));
//            genders.add(new RangeCategory("Transgender", "Gender"));
//        }else {
//            genders.add(new RangeCategory("Female", "Gender"));
//        }

        /*switch (BuildConfig.APPLICATION_ID) {
            case "net.softeksol.seil.groupfin":
            case "net.softeksol.seil.groupfin.sbi":
                genders.add(new RangeCategory("Female", "Gender"));
                break;
            default:
                genders.add(new RangeCategory("Male", "Gender"));
                genders.add(new RangeCategory("Female", "Gender"));
                genders.add(new RangeCategory("Transgender", "Gender"));
        }*/
        acspGender = findViewById(R.id.acspGender);
        acspGender.setAdapter(new AdapterListRange(this, genders, false));

        myCalendar = Calendar.getInstance();
        myCalendar.setTime(new Date());

        acspRelationship = findViewById(R.id.acspRelationship);
        ArrayList<RangeCategory> relationSips = new ArrayList<>();
        relationSips.add(new RangeCategory("Husband", ""));
        relationSips.add(new RangeCategory("Father", ""));

        acspRelationship.setAdapter(new AdapterListRange(this, relationSips, false));

        //acspRelationship.setVisibility(View.GONE);
        //findViewById(R.id.llUidRelationship).setVisibility(View.GONE);

        findViewById(R.id.imgViewAadharPhoto).setVisibility(View.GONE);

        acspAadharState = findViewById(R.id.acspAadharState);
        acspAadharState.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("state", "DescriptionEn", true), false));

        tietName = findViewById(R.id.tietName);
        tietName.addTextChangedListener(new MyTextWatcher(tietName) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        llTopupCode = findViewById(R.id.llTopupCode);
        llTopupCode.setVisibility(View.INVISIBLE);

        svOldCase = findViewById(R.id.svTopupCaseNumber);
        svOldCase.setVisibility(View.GONE);
        svOldCase.setIconified(false);
        svOldCase.setOnClickListener(this);
        /*svOldCase.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Query Click", svOldCase.getQuery().toString());
            }
        });*/
        svOldCase.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Query Submit", query);

                if (validateCaseCode(query)) {
                    fetchTopupDetails(svOldCase.getQuery().toString());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("QueryTextChange", newText);
                boolean validationStatus = validateCaseCode(newText);
                svOldCase.setBackgroundResource(validationStatus ? android.R.color.transparent : R.color.colorLightRed);
                return validationStatus;
            }
        });

        chkTvTopup = findViewById(R.id.chkTopup);
        chkTvTopup.setChecked(false);
        chkTvTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svOldCase.setVisibility(chkTvTopup.isChecked() ? View.VISIBLE : View.GONE);
                svOldCase.setQuery("", false);
                tietAadharId.setEnabled(true);
                borrower.OldCaseCode = null;

            }
        });

        tietAadharId = findViewById(R.id.tietAadhar);
        aadharTextChangeListner = new MyTextWatcher(tietAadharId) {
            @Override
            public void validate(EditText editText, String text) {
                String aadharId = editText.getText().toString();
                if (validateControls(editText, text)) {
                    llTopupCode.setVisibility(View.VISIBLE);
                    Borrower borrower1 = Borrower.getBorrower(aadharId);
                    if (borrower1 != null) {
                        borrower = borrower1;
                        setDataToView(activity.findViewById(android.R.id.content).getRootView());
                    } /*else {
                        fetchAadharDetails(aadharId);
                    }*/
                } else {
                    llTopupCode.setVisibility(View.INVISIBLE);
                }
            }
        };
        tietAadharId.addTextChangedListener(aadharTextChangeListner);

        tietAge = findViewById(R.id.tietAge);
        tietAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((TextInputEditText) v).addTextChangedListener(ageTextWatcher);
                } else {
                    ((TextInputEditText) v).removeTextChangedListener(ageTextWatcher);
                }
            }
        });
        tietAge.addTextChangedListener(new MyTextWatcher(tietAge) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietDob = findViewById(R.id.tietDob);
        tietDob.setFocusable(false);
        tietDob.setEnabled(false);

        tietGuardian = findViewById(R.id.tietGuardian);
        tietGuardian.addTextChangedListener(new MyTextWatcher(tietGuardian) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietAddress1 = findViewById(R.id.tietAddress1);
        tietAddress1.addTextChangedListener(new MyTextWatcher(tietAddress1) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietAddress2 = findViewById(R.id.tietAddress2);
        tietAddress3 = findViewById(R.id.tietAddress3);
        tietCity = findViewById(R.id.tietCity);
        tietCity.addTextChangedListener(new MyTextWatcher(tietCity) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietPinCode = findViewById(R.id.tietPinCode);
        tietPinCode.addTextChangedListener(new MyTextWatcher(tietPinCode) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        ImageView imageView = ((ImageView) findViewById(R.id.imgViewAadharPhoto));
        imageView.setOnClickListener(this);
        imgViewScanQR = (ImageView) findViewById(R.id.imgViewScanQR);
        imgViewScanQR.setOnClickListener(this);
        imgViewScanQR.setVisibility(View.VISIBLE);

        ageTextWatcher = new TextWatcher() {
            String dtString;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0)
                    dtString = DateUtils.getDobFrmAge(Integer.parseInt(s.toString()));
                myCalendar.setTime(DateUtils.getParsedDate(dtString, "dd-MMM-yyyy"));
                tietDob.setText(dtString);
            }
        };

        dateSetListner = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                tietAge.clearFocus();
                myCalendar.set(year, monthOfYear, dayOfMonth);
                tietAge.setText(String.valueOf(DateUtils.getAge(myCalendar)));
                tietDob.setText(DateUtils.getFormatedDate(myCalendar.getTime(), "dd-MMM-yyyy"));
            }
        };
        tietDob.setOnClickListener(this);

        tietMobile = findViewById(R.id.tietMobile);
        tietMobile.addTextChangedListener(new MyTextWatcher(tietMobile) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietPanNo = findViewById(R.id.tietPAN);
        tietPanNo.addTextChangedListener(new MyTextWatcher(tietPanNo) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietVoterId = findViewById(R.id.tietVoter);
        tietVoterId.addTextChangedListener(new MyTextWatcher(tietVoterId) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietDrivingLic = findViewById(R.id.tietDrivingLlicense);
        tietDrivingLic.addTextChangedListener(new MyTextWatcher(tietDrivingLic) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        textViewFiDetails = ((TextView) findViewById(R.id.tv_fi_details));
        textViewFiDetails.setVisibility(View.GONE);


        acspLoanAmount = findViewById(R.id.acspLoanAppFinanceLoanAmount);
        acspLoanAmount.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("loan_amt"), false));

        acspOccupation = findViewById(R.id.acspOccupation);
        acspOccupation.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("occupation-type", "DescriptionEn", true), false));

        acspBusinessDetail = findViewById(R.id.acspBusinessDetail);
        acspBusinessDetail.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("loan_purpose", "DescriptionEn", true), false));

        acspLoanPurpose = findViewById(R.id.acspLoanReason);
        acspLoanPurpose.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("loan_purpose", "DescriptionEn", true), false));

        tietMother = findViewById(R.id.tietMotherName);
        tietMother.addTextChangedListener(new MyTextWatcher(tietMother) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietFather = findViewById(R.id.tietFatherName);
        tietFather.addTextChangedListener(new MyTextWatcher(tietFather) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietBankAccount = findViewById(R.id.tietBankAccount);
        tietBankCIF = findViewById(R.id.tietBankCIF);

        recyclerView = (RecyclerView) findViewById(R.id.rv_document_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterRecViewListDocuments = new AdapterRecViewListDocuments(this, R.layout.layout_item_document_cardview, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecViewListDocuments);
        recyclerView.setVisibility(View.GONE);
    }

    private void setDataToView(View v) {
        if (borrower.Gender != null) {
            if (Utils.setSpinnerPosition((AppCompatSpinner) v.findViewById(R.id.acspGender), borrower.Gender.charAt(0), true) < 0) {
                Utils.alert(this, "Please check Gender, Cannot accept this Aadhar for Loan Application");
                return;
            }
        }
        tietAadharId.removeTextChangedListener(aadharTextChangeListner);
        tietAadharId.setText(borrower.aadharid);
        tietAadharId.addTextChangedListener(aadharTextChangeListner);


        tietName.setText(borrower.getBorrowerName());

        tietAge.setText(String.valueOf(borrower.Age));
        Utils.setOnFocuseSelect(tietAge, "0");
        if (borrower.DOB == null) {
            tietDob.setText(DateUtils.getDobFrmAge(Integer.parseInt(tietAge.getText().toString())));
        } else {
            myCalendar.setTime(borrower.DOB);
            tietDob.setText(DateUtils.getFormatedDate(borrower.DOB, "dd-MMM-yyyy"));
        }

        tietGuardian.setText(borrower.getGurName());
        tietAddress1.setText(borrower.P_Add1);
        tietAddress2.setText(borrower.P_add2);
        tietAddress3.setText(borrower.P_add3);
        tietCity.setText(borrower.P_city);
        tietPinCode.setText(String.valueOf(borrower.p_pin));
        Utils.setOnFocuseSelect(tietPinCode, "0");


        if (borrower.RelationWBorrower != null) {
            Utils.setSpinnerPosition(acspRelationship, borrower.RelationWBorrower, false);
        }
        if (borrower.p_state != null) {
            Utils.setSpinnerPosition(acspAadharState, borrower.p_state);
        }


        if (borrower.isAadharVerified.equals("Q")) {
            imgViewScanQR.setVisibility(View.GONE);
            tietAadharId.setEnabled(false);
            tietName.setEnabled(false);
            if (Utils.NullIf(borrower.getGurName(), "").trim().length() > 0)
                tietGuardian.setEnabled(false);
            if (Utils.NullIf(borrower.Age, 0) > 0) {
                tietAge.setEnabled(false);
                tietDob.setEnabled(false);
            }
            acspGender.setEnabled(false);
            acspAadharState.setEnabled(false);
            if (borrower.P_Add1.trim().length() > 0) tietAddress1.setEnabled(false);
            if (Utils.NullIf(borrower.P_add2, "").trim().length() > 0)
                tietAddress2.setEnabled(false);
            if (Utils.NullIf(borrower.P_add3, "").trim().length() > 0)
                tietAddress3.setEnabled(false);
            if (Utils.NullIf(borrower.P_city, "").trim().length() > 0) tietCity.setEnabled(false);
            if (Utils.NullIf(borrower.p_pin, 0) > 0) tietPinCode.setEnabled(false);
        }else{
            tietName.setEnabled(false);
            if (Utils.NullIf(borrower.getGurName(), "").trim().length() > 0)
                tietGuardian.setEnabled(false);
            if (Utils.NullIf(borrower.Age, 0) > 0) {
                tietAge.setEnabled(false);
                tietDob.setEnabled(false);
            }
            acspGender.setEnabled(false);
            acspAadharState.setEnabled(false);
            if (borrower.P_Add1.trim().length() > 0) tietAddress1.setEnabled(false);
            if (Utils.NullIf(borrower.P_add2, "").trim().length() > 0)
                tietAddress2.setEnabled(false);
            if (Utils.NullIf(borrower.P_add3, "").trim().length() > 0)
                tietAddress3.setEnabled(false);
            if (Utils.NullIf(borrower.P_city, "").trim().length() > 0) tietCity.setEnabled(false);
            if (Utils.NullIf(borrower.p_pin, 0) > 0) tietPinCode.setEnabled(false);

        }  //sir call pick karo
        //showPicture();
        tietMobile.setText(borrower.P_ph3);
        tietPanNo.setText(borrower.PanNO);
        tietVoterId.setText(borrower.voterid);
        tietDrivingLic.setText(borrower.drivinglic);

        tietFather.setText(borrower.fiExtraBank.getFatherName());
        tietMother.setText(borrower.fiExtraBank.getMotherName());
        tietBankAccount.setText(Utils.NullIf(borrower.bank_ac_no, ""));
        tietBankCIF.setText(borrower.fiExtraBank.getBankCif());
        if (borrower.fiExtraBank.getCkycOccupationCode() != null) {
            Utils.setSpinnerPosition(acspOccupation, borrower.fiExtraBank.getCkycOccupationCode(), true);
            //Utils.setSpinnerPosition(acspOccupation,String.class,borrowerExtraBank.getCkycOccupationCode(),"RangeCode");
        }
        if (borrower.Code > 0) {
            textViewFiDetails.setVisibility(View.VISIBLE);
            textViewFiDetails.setText(borrower.Creator + " / " + borrower.Code);
            tietAadharId.setEnabled(false);
            imgViewScanQR.setVisibility(View.GONE);
            showSubmitBorrowerMenuItem = false;
            invalidateOptionsMenu();
            showScanDocs();
        }
    }

    private void getDataFromView(View v) {
        borrower.aadharid = Utils.getNotNullText(tietAadharId);
        borrower.setNames(Utils.getNotNullText(tietName));
        borrower.Age = Utils.getNotNullInt(tietAge);
        borrower.DOB = myCalendar.getTime();

        borrower.setGuardianNames(Utils.getNotNullText(tietGuardian));
        borrower.P_Add1 = Utils.getNotNullText(tietAddress1);
        borrower.P_add2 = Utils.getNotNullText(tietAddress2);
        borrower.P_add3 = Utils.getNotNullText(tietAddress3);
        borrower.P_city = Utils.getNotNullText(tietCity);
        borrower.p_pin = Utils.getNotNullInt(tietPinCode);

        borrower.Gender = ((RangeCategory) acspGender.getSelectedItem()).RangeCode.substring(0, 1);
        if (acspRelationship.getVisibility() == View.VISIBLE && acspRelationship.getAdapter().getCount() > 0)
            borrower.RelationWBorrower = ((RangeCategory) acspRelationship.getSelectedItem()).RangeCode;
        borrower.p_state = ((RangeCategory) acspAadharState.getSelectedItem()).RangeCode;
        borrower.P_ph3 = Utils.getNotNullText(tietMobile);
        borrower.PanNO = Utils.getNotNullText(tietPanNo);
        borrower.drivinglic = Utils.getNotNullText(tietDrivingLic);
        borrower.voterid = Utils.getNotNullText(tietVoterId);
        borrower.Loan_Amt = Utils.getSpinnerIntegerValue((AppCompatSpinner) v.findViewById(R.id.acspLoanAppFinanceLoanAmount));

        borrower.fiExtraBank.setFatherName(Utils.getNotNullText(tietFather));
        borrower.fiExtraBank.setMotherName(Utils.getNotNullText(tietMother));
        borrower.fiExtraBank.setBankCif(Utils.getNotNullText(tietBankCIF));
        String occCode = Utils.getSpinnerStringValue((AppCompatSpinner) v.findViewById(R.id.acspOccupation));
        borrower.fiExtraBank.setCkycOccupationCode(occCode);
        borrower.Business_Detail = ((RangeCategory) acspBusinessDetail.getSelectedItem()).RangeCode;
        borrower.Loan_Reason = ((RangeCategory) acspLoanPurpose.getSelectedItem()).RangeCode;
        borrower.bank_ac_no = Utils.getNotNullText(tietBankAccount);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kyc_submit_cancel, menu);
        MenuItem mnuItemSubmitBorrower = menu.findItem(R.id.action_submit_kyc);
        mnuItemSubmitBorrower.setVisible(showSubmitBorrowerMenuItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean reVal = true;
        switch (item.getItemId()) {
            case R.id.action_submit_kyc:
                updateBorrower();
                break;
            case R.id.action_cancel:
                finish();
                break;
            default:
                reVal = super.onOptionsItemSelected(item);
        }
        return reVal;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewScanQR:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.setOrientationLocked(false);
                scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
                break;
            /*
            case R.id.btnSubmitKyc:
                updateBorrower();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnCapturePhoto:
                launchScanning();
                break;
                */
            case R.id.tietDob:
                Date dob = DateUtils.getParsedDate(tietDob.getText().toString(), "dd-MMM-yyyy");
                myCalendar.setTime(dob);
                new DatePickerDialog(this, dateSetListner,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            //Log.d("QR Scan","Executed");
            if (scanningResult != null) {
                //we have a result
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                Log.d("CheckXMLDATA3","AadharData:->" + scanContent);
                if (scanFormat != null) {
                    setAadharContent(scanContent);
                }
            }
        } else {
            if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO) {
                if (resultCode == RESULT_OK) {
                    if (documentPic.checklistid == 0) {
                        CropImage.activity(this.uriPicture)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(45, 52)
                                .start(this);
                    } else {
                        CropImage.activity(this.uriPicture)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(this);
                    }
                } else {
                    Utils.alert(this, "Could not take Picture");
                }
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                Exception error = null;
                int maxDimentions = (documentPic.checklistid == 0 ? 300 : 1000);
                Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, maxDimentions, error, false);
                File tempCroppedImage = new File(imageUri.getPath());

                if (tempCroppedImage.length() > 100) {
                    if (borrower != null) {
                        (new File(this.uriPicture.getPath())).delete();
                        try {
                            File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true);
                            documentPic.imagePath = croppedImage.getPath();
                            documentPic.save();
                            if (documentPic.checklistid == 1) putExifData(documentPic);
                            adapterRecViewListDocuments.updateList(getDocumentStore(borrower));
                            //borrower.setPicture(croppedImage.getPath());
                            //borrower.save();
                            //borrower_id = borrower.FiID;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void setAadharContent(String aadharDataString) {
        try {


            Log.d("CheckXMLDATA2", "AadharData:->" + aadharDataString);
            if (aadharDataString.toUpperCase().contains("XML")) {

                Log.d("XML printing", "AadharData:->" + aadharDataString);
                //AadharData aadharData = AadharUtils.getAadhar(aadharDataString);
                AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));

                if (aadharData.AadharId != null) {

                    Borrower borrower1 = Borrower.getBorrower(aadharData.AadharId);
                    if (borrower1 != null) {
                        borrower = borrower1;
                        setDataToView(activity.findViewById(android.R.id.content).getRootView());
                        tietAadharId.setEnabled(false);
                        return;
                    }

                    if (chkTvTopup.isChecked()) {
                        if (tietAadharId.getText().toString().equals(aadharData.AadharId)) {
                            Utils.alert(this, "Aadhar ID did not match with Topup Case");
                            return;
                        }
                    }
                    borrower.aadharid = aadharData.AadharId;
                }

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
                borrower.isAadharVerified = aadharData.isAadharVerified;
                borrower.setNames(aadharData.Name);
                borrower.DOB = aadharData.DOB;
                borrower.Age = aadharData.Age;
                borrower.Gender = aadharData.Gender;
                borrower.setGuardianNames(aadharData.GurName);
                borrower.P_city = aadharData.City;
                borrower.p_pin = aadharData.Pin;
                borrower.P_Add1 = aadharData.Address1;
                borrower.P_add2 = aadharData.Address2;
                borrower.P_add3 = aadharData.Address3;
                borrower.p_state = AadharUtils.getStateCode(aadharData.State);
                setDataToView(this.findViewById(android.R.id.content).getRootView());
                validateBorrower();
                tietAge.setEnabled(false);
                tietDob.setEnabled(false);

               //====== ================================




            } else {

                final BigInteger bigIntScanData = new BigInteger(aadharDataString, 10);
                Log.e("testbigin======", "AadharData:->" + bigIntScanData);
                // 2. Convert BigInt to Byte Array
                final byte byteScanData[] = bigIntScanData.toByteArray();

                // 3. Decompress Byte Array
                final byte[] decompByteScanData = decompressData(byteScanData);

                // 4. Split the byte array using delimiter
                List<byte[]> parts = separateData(decompByteScanData);
                // Throw error if there are no parts
                Log.e("Parts======11======> ", "part data =====> " + parts.toString());
                decodeData(parts);
                decodeSignature(decompByteScanData);
                decodeMobileEmail(decompByteScanData);
            }
            } catch(Exception ex) {
            Utils.alert(this, ex.getMessage());
        }

    }

     // 20/11/2022 ========================================
    protected byte[] decompressData(byte[] byteScanData) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(byteScanData.length);
        ByteArrayInputStream bin = new ByteArrayInputStream(byteScanData);
        GZIPInputStream gis = null;

        try {
            gis = new GZIPInputStream(bin);
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, Opening byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in opening Gzip byte stream while decompressing QRcode",e);
        }

        int size = 0;
        byte[] buf = new byte[1024];
        while (size >= 0) {
            try {
                size = gis.read(buf,0,buf.length);
                if(size > 0){
                    bos.write(buf,0,size);
                }
            } catch (IOException e) {
                Log.e("Exception", "Decompressing QRcode, writing byte stream failed: " + e.toString());
                // throw new QrCodeException("Error in writing byte stream while decompressing QRcode",e);
            }
        }

        try {
            gis.close();
            bin.close();
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, closing byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in closing byte stream while decompressing QRcode",e);
        }

        return bos.toByteArray();
    }

    protected List<byte[]> separateData(byte[] source) {
        List<byte[]> separatedParts = new LinkedList<>();
        int begin = 0;

        for (int i = 0; i < source.length; i++) {
            if(source[i] == SEPARATOR_BYTE){
                // skip if first or last byte is separator
                if(i != 0 && i != (source.length -1)){
                    separatedParts.add(Arrays.copyOfRange(source, begin, i));
                }
                begin = i + 1;
                // check if we have got all the parts of text data
                if(separatedParts.size() == (VTC_INDEX + 1)){
                    // this is required to extract image data
                    imageStartIndex = begin;
                    break;
                }
            }
        }
        return separatedParts;
    }

    protected void decodeSignature(byte[] decompressedData){
        // extract 256 bytes from the end of the byte array
        int startIndex = decompressedData.length - 257,
                noOfBytes = 256;
        try {
            signature = new String (decompressedData,startIndex,noOfBytes,"ISO-8859-1");
            Log.e("signature======>","signature===> "+signature);
        } catch (UnsupportedEncodingException e) {
            Log.e("Exception", "Decoding Signature of QRcode, ISO-8859-1 not supported: " + e.toString());
           // throw new QrCodeException("Decoding Signature of QRcode, ISO-8859-1 not supported",e);
        }

    }


    protected void decodeData(List<byte[]> encodedData) throws ParseException{
        Iterator<byte[]> i = encodedData.iterator();
        decodedData = new ArrayList<String>();
        while(i.hasNext()){
            try {
                decodedData.add(new String(i.next(), "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                Log.e("Exception", "Decoding QRcode, ISO-8859-1 not supported: " + e.toString());
               // throw new QrCodeException("Decoding QRcode, ISO-8859-1 not supported",e);
            }
        }
        // set the value of email/mobile present flag
        Log.e("Parts======2======> ","part data =====> "+decodedData.toString());
        //emailMobilePresent = Integer.parseInt(decodedData[0]);

        Log.e("Parts======3======> ","part data =====> "+decodedData.get(15));

        // populate decoded data
        SimpleDateFormat sdt = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdt = new SimpleDateFormat("dd-MM-YYYY");

        }
        Date result = null;
        try {
            result = sdt.parse(decodedData.get(4));


        } catch (ParseException e) {
            e.printStackTrace();
        }



        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(decodedData.get(4));

        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = date.toInstant();
            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
            LocalDate givenDate = zone.toLocalDate();

            Period period = Period.between(givenDate, LocalDate.now());

            borrower.Age = period.getYears();
            System.out.print(period.getYears()+" years "+period.getMonths()+" and "+period.getDays()+" days");
        }
    // run karo sir ok



        borrower.setNames(decodedData.get(3));
        borrower.DOB = date;

        borrower.Gender = decodedData.get(5);
        borrower.setGuardianNames(decodedData.get(6));
        borrower.P_city = decodedData.get(7);
        borrower.p_pin = Integer.parseInt(decodedData.get(11));
        borrower.P_Add1 = decodedData.get(9);
        borrower.P_add2 = decodedData.get(8);
        borrower.P_add3 = decodedData.get(10);
        borrower.p_state = AadharUtils.getStateCode(decodedData.get(13));
        setDataToView(this.findViewById(android.R.id.content).getRootView());
        validateBorrower();
        tietAge.setEnabled(false);
        tietDob.setEnabled(false);


    }

    protected void decodeMobileEmail(byte[] decompressedData){
        int mobileStartIndex = 0,mobileEndIndex = 0,emailStartIndex = 0,emailEndIndex = 0;
        switch (emailMobilePresent){
            case 3:
                // both email mobile present
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256
                emailStartIndex = decompressedData.length - 322;// length -1 -256 -32 -1 -32
                emailEndIndex = decompressedData.length - 290;// length -1 -256 -32 -1

                mobile = bytesToHex (Arrays.copyOfRange(decompressedData,mobileStartIndex,mobileEndIndex+1));
                email = bytesToHex (Arrays.copyOfRange(decompressedData,emailStartIndex,emailEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 323;
                break;

            case 2:
                // only mobile
                email = "";
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256

                mobile = bytesToHex (Arrays.copyOfRange(decompressedData,mobileStartIndex,mobileEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;

                break;

            case 1:
                // only email
                mobile = "";
                emailStartIndex = decompressedData.length - 289; // length -1 -256 -32
                emailEndIndex = decompressedData.length - 257; // length -1 -256

                email = bytesToHex (Arrays.copyOfRange(decompressedData,emailStartIndex,emailEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;
                break;

            default:
                // no mobile or email
                mobile = "";
                email = "";
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 257;
        }

        Log.e("email mobile======> ","Data=====>"+email +"   "+mobile);

    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    //=================================================


    @Override
    public void cameraCaptureUpdate(Uri uriImage) {
        this.uriPicture = uriImage;
    }

    private void updateBorrower() {
        if (borrower != null) {
            getDataFromView(this.findViewById(android.R.id.content).getRootView());

            if (validateBorrower()) {
                Map<String, String> messages = borrower.validateKyc(this);
                if (messages.size() > 0) {
                    String combineMessage = Arrays.toString(messages.values().toArray());
                    combineMessage = combineMessage.replace("[", "->").replace(", ", "\n->").replace("]", "");
                    Utils.alert(this, combineMessage);
                } else {
                    showSubmitBorrowerMenuItem = false;
                    invalidateOptionsMenu();
                    if (!chkTvTopup.isChecked()) borrower.OldCaseCode = null;
                    borrower.Oth_Prop_Det = null;
                    borrower.save();
                    borrower.associateExtraBank(borrower.fiExtraBank);
                    borrower.fiExtraBank.save();

                    BorrowerDTO borrowerDTO = new BorrowerDTO(borrower);
                    borrowerDTO.fiFamExpenses = null;
                    borrowerDTO.fiExtra = null;
                    AsyncResponseHandler dataAsyncResponseHandler = new AsyncResponseHandler(this, "Loan Financing\nSubmittiong Loan Application", "Submitting Borrower Information") {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String jsonString = new String(responseBody);
                            //Log.d("Response Data",jsonString);
                            try {
                                JSONObject jo = new JSONObject(jsonString);
                                long FiCode = jo.getLong("FiCode");
                                borrower.updateFiCode(FiCode, borrower.Tag);
                                borrower.Oth_Prop_Det = null;
                                borrower.save();

                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBorrowerKyc.this);
                                builder.setTitle("Borrower KYC");
                                builder.setMessage("KYC Saved with " + manager.Creator + " / " + FiCode + "\nPlease capture / scan documents");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showScanDocs();
                                    }
                                });
                                builder.create().show();
                            } catch (JSONException jo) {
                                Utils.showSnakbar(findViewById(android.R.id.content), jo.getMessage());

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            super.onFailure(statusCode, headers, responseBody, error);
                            //btnSubmit.setEnabled(true);
                            showSubmitBorrowerMenuItem = true;
                            invalidateOptionsMenu();
                        }
                    };


                    //Log.d("Borrower Json",WebOperations.convertToJson(borrower));
                    String borrowerJsonString = WebOperations.convertToJson(borrowerDTO);
                    //Log.d("Borrower Json", borrowerJsonString);
                    Log.d("TAG", "updateBorrower: "+borrowerJsonString+dataAsyncResponseHandler);

                    (new WebOperations()).postEntity(this, "posfi", "savefi", borrowerJsonString, dataAsyncResponseHandler);
                }
            } else {
                Utils.alert(this, "There is at least one errors in the Aadhar Data");
            }
        }
    }

    private void fetchTopupDetails(final String oldCaseCode) {

        DataAsyncResponseHandler dataAsyncResponseHandler = new DataAsyncResponseHandler(this, "Borrower KYC", "Fetching Topup Details") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String jsonString = new String(responseBody);
                    Log.d("Response", jsonString);
                    JSONObject jo = null;
                    try {
                        jo = (new JSONArray(jsonString)).getJSONObject(0);
                        int noInst = jo.getInt("Total_Installments");
                        int lastInstR = jo.getInt("LastInst_Recd");
                        String aadharId = jo.getString("aadharid");

                        if (aadharId.length() == 12) {
                            if (tietAadharId.getText().length() == 12) {
                                if (!tietAadharId.getText().toString().equals(aadharId)) {
                                    Utils.alert(ActivityBorrowerKyc.this, "Aadhar IDs did not match");
                                    return;
                                }
                            }
                                /*if (tietAadharId.getText().length() == 0 || tietAadharId.getText().length() != 12) {
                                    tietAadharId.setText(aadharId);
                                    tietAadharId.setEnabled(false);
                                }*/
                            borrower.OldCaseCode = jo.getString("CODE");
                            //tietName.setText(jo.getString("SUBS_NAME"));
                            if (lastInstR >= ((noInst / 2) - 1)) {

                            } else {
                                Utils.alert(ActivityBorrowerKyc.this, "Minimum installment not received for topup");
                            }
                        } else {
                            Utils.alert(ActivityBorrowerKyc.this, "Please get Aadhar ID updated for Topup Case");
                        }


                    } catch (JSONException je) {
                        if (("[]").equals(jsonString))
                            Utils.alert(ActivityBorrowerKyc.this, "No record found for Loan A/c " + oldCaseCode);
                        else {

                        }
                    }
                }
            }
        };
        RequestParams params = new RequestParams();
        params.add("SmCode", oldCaseCode);
        (new WebOperations()).getEntity(this, "posfi", "gettopupcasedtls", params, dataAsyncResponseHandler);

    }

    private void fetchAadharDetails(String aadharId) {

        DataAsyncResponseHandler dataAsyncResponseHandler = new DataAsyncResponseHandler(this, "Borrower KYC", "Fetching Previous Applications") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String jsonString = new String(responseBody);
                    Log.d("Response", jsonString);
                    //JSONObject jo = null;
                    //Type listType = new TypeToken<List<OldFIById>>() {}.getType();
                    List<OldFIById> oldFIByIds = WebOperations.convertToObjectArray(jsonString);
                    

                    /*try {
                        jo = (new JSONArray(jsonString)).getJSONObject(0);
                        int noInst = jo.getInt("Total_Installments");
                        int lastInstR = jo.getInt("LastInst_Recd");
                        String aadharId = jo.getString("AadharID");
                        if (lastInstR >= ((noInst / 2) - 1)) {
                            if (aadharId.length() == 12) {
                                if (tietAadharId.getText().length() == 12) {
                                    if (!tietAadharId.getText().toString().equals(aadharId)) {
                                        Utils.alert(ActivityBorrowerKyc.this, "Aadhar IDs did not match");
                                        return;
                                    }
                                }
                                if (tietAadharId.getText().length() == 0 || tietAadharId.getText().length() != 12) {
                                    tietAadharId.setText(aadharId);
                                    tietAadharId.setEnabled(false);
                                }
                                borrower.OldCaseCode = jo.getString("CODE");
                                tietName.setText(jo.getString("SUBS_NAME"));

                            } else {
                                Utils.alert(ActivityBorrowerKyc.this, "Please get Aadhar ID updated for Topup Case");
                            }

                        } else {
                            Utils.alert(ActivityBorrowerKyc.this, "Minimum installment not received for topup");
                        }

                    } catch (JSONException je) {

                    }*/
                }
            }
        };
        RequestParams params = new RequestParams();
        params.add("Aadharid", aadharId);
        (new WebOperations()).getEntity(this, "posdb", "getaadharstatus", params, dataAsyncResponseHandler);

    }


    private boolean validateControls(EditText editText, String text) {
        boolean retVal = true;
        editText.setError(null);
        switch (editText.getId()) {
            case R.id.tietAadhar:
                if (editText.length() != 12) {
                    editText.setError("Should be of 12 Characters");
                    retVal = false;
                } else if (!Verhoeff.validateVerhoeff(editText.getText().toString())) {
                    editText.setError("Aadhar is not Valid");
                    retVal = false;
                }
                break;
            case R.id.tietDob:
            case R.id.tietGuardian:
            case R.id.tietName:
            case R.id.tietMotherName:
            case R.id.tietFatherName:
                if (editText.length() < 3) {
                    editText.setError("Should be more than 3 Characters");
                    retVal = false;
                }
                break;

            case R.id.tietAge:
                if (text.length() == 0) text = "0";
                int age = Integer.parseInt(text);
                if (age < 18) {
                    editText.setError("Age should be greater than 17");
                    retVal = false;
                } else if (age > 65) {
                    editText.setError("Age should be less than 66");
                    retVal = false;
                }
                tietDob.setEnabled(retVal);

                break;
            case R.id.tietAddress1:
                if (editText.getText().toString().trim().length() < 6) {
                    editText.setError("Should be more than 5 Characters");
                    retVal = false;
                }
                break;
            case R.id.tietCity:
                if (editText.getText().toString().trim().length() < 3) {
                    editText.setError("Should be more than 2 Characters");
                    retVal = false;
                }
                break;
            case R.id.tietPinCode:
                if (editText.getText().toString().trim().length() != 6) {
                    editText.setError("Should be of 6 digits");
                    retVal = false;
                }
                break;
            case R.id.tietVoter:
            case R.id.tietPAN:
            case R.id.tietDrivingLlicense:
            case R.id.tietBankCIF:
                if (editText.getText().toString().trim().length() > 0) {
                    if (editText.getText().toString().trim().length() < 10) {
                        editText.setError("Should be more than 9 Characters");
                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietMobile:
                if (editText.getText().toString().trim().length() > 0) {
                    if (editText.getText().toString().trim().length() != 10) {
                        editText.setError("Should be of 10 digits");
                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
        }
        return retVal;
    }

    private boolean validateBorrower() {
        boolean retVal = true;
        retVal &= validateControls(tietAadharId, tietAadharId.getText().toString());
        retVal &= validateControls(tietName, tietName.getText().toString());
        retVal &= validateControls(tietGuardian, tietGuardian.getText().toString());
        retVal &= validateControls(tietAge, tietAge.getText().toString());
        retVal &= validateControls(tietDob, tietDob.getText().toString());
        retVal &= validateControls(tietAddress1, tietAddress1.getText().toString());
        retVal &= validateControls(tietCity, tietCity.getText().toString());
        retVal &= validateControls(tietPinCode, tietPinCode.getText().toString());
        retVal &= validateControls(tietMobile, tietMobile.getText().toString());
        retVal &= validateControls(tietVoterId, tietVoterId.getText().toString());
        retVal &= validateControls(tietPanNo, tietPanNo.getText().toString());
        retVal &= validateControls(tietDrivingLic, tietDrivingLic.getText().toString());
        retVal &= validateControls(tietMother, tietMother.getText().toString());
        retVal &= validateControls(tietFather, tietFather.getText().toString());
        return retVal;
    }

    private List<DocumentStore> getDocumentStore(Borrower borrower) {
        List<DocumentStore> documentStores = new ArrayList<>();
        DocumentStore documentStore = new DocumentStore();
        documentStores.add(documentStore.getDocumentStore(borrower, 0, "Picture"));
        if (!Utils.NullIf(borrower.isAadharVerified, "N").equals("Y")) {
            documentStores.add(documentStore.getDocumentStore(borrower, 1, "AadharFront"));
            documentStores.add(documentStore.getDocumentStore(borrower, 1, "AadharBack"));
        }

        if (BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing") {
            documentStores.add(documentStore.getDocumentStore(borrower, 5, "VoterFront"));
            documentStores.add(documentStore.getDocumentStore(borrower, 5, "VoterBack"));

            if (borrower.PanNO.length() == 10) {
                documentStores.add(documentStore.getDocumentStore(borrower, 6, "PANCard"));
            }
        } else {
            if (borrower.voterid.length() > 9) {
                documentStores.add(documentStore.getDocumentStore(borrower, 3, "VoterFront"));
                documentStores.add(documentStore.getDocumentStore(borrower, 3, "VoterBack"));
            }
            if (borrower.PanNO.length() == 10) {
                documentStores.add(documentStore.getDocumentStore(borrower, 4, "PANCard"));
            }
        }
        if (borrower.drivinglic.length() > 9) {
            documentStores.add(documentStore.getDocumentStore(borrower, 15, "DrivingLicFront"));
            documentStores.add(documentStore.getDocumentStore(borrower, 15, "DrivingLicBack"));
        }

        //documentStores.add(documentStore.getDocumentStore(borrower,2,"PassbookFirst"));
        //documentStores.add(documentStore.getDocumentStore(borrower,2,"PassbookLast"));
        //documentStores.add(documentStore.getDocumentStore(borrower,4,"Mandate"));
        Collections.sort(documentStores, DocumentStore.sortByUploadStatus);
        return documentStores;
    }

    @Override
    public void onKycCapture(DocumentStore item) {
        documentPic = item;
        Boolean cropState = true;
        try {
            CameraUtils.dispatchTakePictureIntent(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onKycUpload(final DocumentStore documentStore, final View view) {
        DataAsyncResponseHandler responseHandler = new DataAsyncResponseHandler(this, "Loan Financing", DocumentStore.getDocumentName(documentStore.checklistid)) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = new String(responseBody);
                Utils.showSnakbar(activity.findViewById(android.R.id.content).getRootView(), responseString);
                //if(responseString.equals("")) {
                documentStore.updateStatus = true;
                documentStore.update();
                (new File(documentStore.imagePath)).delete();
                showScanDocs();
            }
        };

        String jsonString = WebOperations.convertToJson(documentStore.getDocumentDTO());
        //Log.d("Document Json",jsonString);
        String apiPath = documentStore.checklistid == 0 ? "/api/uploaddocs/savefipicjson" : "/api/uploaddocs/savefidocsjson";
        (new WebOperations()).postEntity(activity, BuildConfig.BASE_URL + apiPath, jsonString, responseHandler);
    }

    public void showScanDocs() {
        recyclerView.setVisibility(View.VISIBLE);
        List<DocumentStore> documentStoreList = getDocumentStore(borrower);
        recyclerView.invalidate();
        adapterRecViewListDocuments.updateList(documentStoreList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void putExifData(DocumentStore documentStore) {
        try {
            ExifInterface newExif = new ExifInterface(documentStore.imagePath);
            newExif.setAttribute(ExifInterface.TAG_USER_COMMENT, documentStore.Creator + "_" + documentStore.ficode);
            SecretKey secretKey = Utils.generateKey(documentStore.Creator + "#" + documentStore.ficode);
            String id = new String(Utils.encryptMsg(borrower.aadharid, secretKey));
            newExif.setAttribute(ExifInterface.TAG_IMAGE_UNIQUE_ID, id);
            newExif.saveAttributes();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | InvalidKeySpecException | IllegalBlockSizeException | InvalidParameterSpecException e) {
            e.printStackTrace();
        }
    }

}
