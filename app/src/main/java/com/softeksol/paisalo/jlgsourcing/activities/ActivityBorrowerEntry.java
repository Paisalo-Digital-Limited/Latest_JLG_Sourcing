package com.softeksol.paisalo.jlgsourcing.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.AadharData;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class ActivityBorrowerEntry extends AppCompatActivity implements View.OnClickListener, CameraUtils.OnCameraCaptureUpdate {
    private Borrower borrower;
    private Uri uriPicture;
    private ImageView imageView, imgViewScanQR;
    private Boolean cropState = false;
    private Manager manager;
    private long borrower_id;

    private AppCompatSpinner acspGender, acspRelationship, acspAadharState, acspLoanAmount;
    private TextInputEditText tietAadharId, tietName, tietAge, tietDob, tietGuardian,
            tietAddress1, tietAddress2, tietAddress3, tietCity, tietPinCode,
            tietMobile, tietVoter, tietDrivingLic, tietPan;
    private Button btnAdd, btnUpdate, btnCancel, btnDelete; //
    private TextWatcher dobTextWatcher, ageTextWatcher;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateSetListner;

    /*@BindView(R.id.btnGuarantorAdd)
    Button btnAdd;
    @BindView(R.id.acspLoanAppFinanceLoanAmount)
    AppCompatSpinner acspLoanAmount;*/
    /*@BindView(R.id.acspLoanAppFinanceLoanAmount)
    AppCompatSpinner LoanDuration;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_entry);
        manager = (Manager) getIntent().getSerializableExtra(Global.MANAGER_TAG);
        //ButterKnife.bind(this, findViewById(android.R.id.content));
        //borrower = new Borrower();
        borrower = new Borrower(manager.Creator, manager.TAG, manager.FOCode, manager.AreaCd, IglPreferences.getPrefString(ActivityBorrowerEntry.this, SEILIGL.USER_ID, ""));
        //borrower.fiExtra=
        //borrower.fi
        borrower.isAadharVerified = "N";

        btnAdd = (Button) findViewById(R.id.btnGuarantorAdd);
        btnUpdate = (Button) findViewById(R.id.btnGuarantorUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete = (Button) findViewById(R.id.btnGuarantorDelete);
        btnDelete.setOnClickListener(this);
        btnAdd.setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.tvLoanAppGuarantorTitle)).setText(R.string.borrower);

        ArrayList<RangeCategory> genders = new ArrayList<>();

        genders.add(new RangeCategory("Female", "Gender"));
        genders.add(new RangeCategory("Male", "Gender"));
        genders.add(new RangeCategory("Transgender", "Gender"));
//        if (BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing")) {
//            genders.add(new RangeCategory("Male", "Gender"));
//            genders.add(new RangeCategory("Female", "Gender"));
//            genders.add(new RangeCategory("Transgender", "Gender"));
//        } else if (BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.groupfin.sbicolending")){
//            genders.add(new RangeCategory("Male", "Gender"));
//            genders.add(new RangeCategory("Female", "Gender"));
//            genders.add(new RangeCategory("Transgender", "Gender"));
//        }else {
//            genders.add(new RangeCategory("Female", "Gender"));
//        }

        acspGender = ((AppCompatSpinner) findViewById(R.id.acspGender));
        acspGender.setAdapter(new AdapterListRange(this, genders, false));

        myCalendar = Calendar.getInstance();
        myCalendar.setTime(new Date());

        acspRelationship = ((AppCompatSpinner) findViewById(R.id.acspRelationship));
        acspRelationship.setVisibility(View.GONE);
        findViewById(R.id.llUidRelationship).setVisibility(View.GONE);
        findViewById(R.id.imgViewAadharPhoto).setVisibility(View.GONE);

        acspAadharState = ((AppCompatSpinner) findViewById(R.id.acspAadharState));
        acspAadharState.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("state"), false));

        tietName = (TextInputEditText) findViewById(R.id.tietName);
        tietName.addTextChangedListener(new MyTextWatcher(tietName) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietAadharId = (TextInputEditText) findViewById(R.id.tietAadhar);
        tietAadharId.addTextChangedListener(new MyTextWatcher(tietAadharId) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietAge = (TextInputEditText) findViewById(R.id.tietAge);
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

        tietDob = (TextInputEditText) findViewById(R.id.tietDob);
        tietDob.setFocusable(false);
        tietDob.setEnabled(false);

        tietGuardian = (TextInputEditText) findViewById(R.id.tietGuardian);
        tietAddress1 = (TextInputEditText) findViewById(R.id.tietAddress1);
        tietAddress1.addTextChangedListener(new MyTextWatcher(tietAddress1) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietAddress2 = (TextInputEditText) findViewById(R.id.tietAddress2);
        tietAddress3 = (TextInputEditText) findViewById(R.id.tietAddress3);
        tietCity = (TextInputEditText) findViewById(R.id.tietCity);
        tietCity.addTextChangedListener(new MyTextWatcher(tietCity) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietPinCode = (TextInputEditText) findViewById(R.id.tietPinCode);
        tietPinCode.addTextChangedListener(new MyTextWatcher(tietPinCode) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietMobile = (TextInputEditText) findViewById(R.id.tietMobile);
        tietMobile.addTextChangedListener(new MyTextWatcher(tietMobile) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietVoter = (TextInputEditText) findViewById(R.id.tietVoter);
        tietVoter.addTextChangedListener(new MyTextWatcher(tietVoter) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietDrivingLic = (TextInputEditText) findViewById(R.id.tietDrivingLlicense);
        tietDrivingLic.addTextChangedListener(new MyTextWatcher(tietDrivingLic) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietPan = (TextInputEditText) findViewById(R.id.tietPAN);
        tietPan.addTextChangedListener(new MyTextWatcher(tietPan) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });


        imageView = ((ImageView) findViewById(R.id.imgViewAadharPhoto));
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

        /*acspLoanAmount=(AppCompatSpinner) findViewById(R.id.acspLoanAppFinanceLoanAmount);
        acspLoanAmount.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("loan_amt"), false));*/
        //acspLoanDuration.setAdapter(new AdapterListRange(this, Utils.getList(1, 2, 1, 12, "Months"), true));


        /*rlaLoanAmount = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("loan_amt")).queryList(), false);*/

    }

    private void setDataToView(View v) {
        tietAadharId.setText(borrower.aadharid);
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


        if (borrower.Gender != null) {
            Utils.setSpinnerPosition((AppCompatSpinner) v.findViewById(R.id.acspGender), borrower.Gender.charAt(0), true);
        }
        if (borrower.RelationWBorrower != null) {
            Utils.setSpinnerPosition(acspRelationship, borrower.RelationWBorrower, false);
        }
        if (borrower.p_state != null) {
            Utils.setSpinnerPosition(acspAadharState, borrower.p_state);
        }

        tietMobile.setText(borrower.P_ph3);
        tietVoter.setText(borrower.voterid);

        tietPan.setText(borrower.PanNO);
        tietDrivingLic.setText(borrower.drivinglic);


        if (borrower.isAadharVerified.equals("Q")) {
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
        }
        showPicture();
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

        borrower.voterid = Utils.getNotNullText(tietVoter);
        borrower.drivinglic = Utils.getNotNullText(tietDrivingLic);
        borrower.PanNO = Utils.getNotNullText(tietPan);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewScanQR:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.setOrientationLocked(false);
                scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
                break;
            case R.id.btnGuarantorUpdate:
                updateBorrower();
                Intent intent = new Intent();
                intent.putExtra(Global.BORROWER_TAG, borrower_id);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnGuarantorDelete:
                if (borrower != null) {
                    borrower.delete();
                    borrower = null;
                }
                finish();
                break;
            case R.id.imgViewAadharPhoto:
                cropState = true;
                try {
                    CameraUtils.dispatchTakePictureIntent(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tietDob:
                Date dob = DateUtils.getParsedDate(tietDob.getText().toString(), "dd-MMM-yyyy");
                myCalendar.setTime(dob);
                new DatePickerDialog(this, dateSetListner,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            //Log.d("QR Scan","Executed");
            if (scanningResult != null) {
                //we have a result
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if (scanFormat != null) {
                    setAadharContent(scanContent);
                }
            }
        } else {
            if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO) {
                if (resultCode == RESULT_OK) {
                    CropImage.activity(this.uriPicture)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(45, 52)
                            .start(this);
                } else {
                    Utils.alert(this, "Could not take Picture");
                }
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                Exception error = null;
                Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 300, error, false);
                File tempCroppedImage = new File(imageUri.getPath());

                if (error == null && imageUri != null && tempCroppedImage.length() > 100) {
                    if (borrower != null) {
                        (new File(this.uriPicture.getPath())).delete();
                        try {
                            File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true);
                            borrower.setPicture(croppedImage.getPath());
                            borrower.Oth_Prop_Det = null;
                            borrower.save();
                            borrower_id = borrower.FiID;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setAadharContent(String aadharDataString) {
        try {
            AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));
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
            borrower.isAadharVerified = "Q";
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
            //BorrowerExtra borrowerExtra=new BorrowerExtra();
            //borrower.associateExtra(borrowerExtra);
            if (aadharData.AadharId != null)
                borrower.aadharid = aadharData.AadharId;
            setDataToView(this.findViewById(android.R.id.content).getRootView());
            validateBorrower();
            tietAge.setEnabled(false);
            tietDob.setEnabled(false);
        } catch (Exception ex) {
            Utils.alert(this, ex.getMessage());
        }
    }

    @Override
    public void cameraCaptureUpdate(Uri uriImage) {
        this.uriPicture = uriImage;
    }

    @Override
    public void onResume() {
        super.onResume();
        showBorrower();
    }

    private void showPicture() {
        if ( borrower.getPicture() != null && borrower.getPicture() != null &&
                (new File(borrower.getPicture().getPath())).length() > 100 ) {
            Glide.with(this).load(borrower.getPicture().getPath()).override(Target.SIZE_ORIGINAL, 300).into(imageView);
        }
    }

    /*private Guarantor getGuarantor(long gid, long bid) {
        borrower = null;
        if (gid > 0) {
            borrower = SQLite.select().from(Borrower.class).where(Borrower_Table.FiID.eq(gid)).querySingle();
        }
        if (borrower == null) {
            borrower = new Borrower();
            guarantor.setP_StateID(borrower.P_State);
            guarantor.associateBorrower(borrower);
            guarantor.setIsAadharVerified("N");
        }
        return guarantor;
    }*/

    private void showBorrower() {
        if (borrower != null && borrower.FiID > 0) {
            setDataToView(this.findViewById(android.R.id.content).getRootView());
        }
    }

    private void updateBorrower() {
        if (borrower != null) {
            getDataFromView(this.findViewById(android.R.id.content).getRootView());
            if (validateBorrower()) {
                borrower.Oth_Prop_Det = null;
                borrower.save();
                borrower_id = borrower.FiID;
            } else {
                Utils.alert(this, "There is at least one errors in the Borrower Data");
            }
        }
    }

    @Override
    protected void onPause() {
        updateBorrower();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // updateGuarantor();
        // finish();
        //super.onBackPressed();
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
                }
                break;
            case R.id.tietName:
                if (editText.length() < 3) {
                    editText.setError("Should be more than 3 Characters");
                    retVal = false;
                }
                break;
            case R.id.tietDob:

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

            case R.id.tietVoter:
            case R.id.tietPAN:
            case R.id.tietDrivingLlicense:
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
        }
        return retVal;
    }

    private boolean validateBorrower() {
        boolean retVal = true;
        retVal &= validateControls(tietAadharId, tietAadharId.getText().toString());
        retVal &= validateControls(tietName, tietName.getText().toString());
        retVal &= validateControls(tietAge, tietAge.getText().toString());
        retVal &= validateControls(tietDob, tietDob.getText().toString());
        retVal &= validateControls(tietAddress1, tietAddress1.getText().toString());
        retVal &= validateControls(tietCity, tietCity.getText().toString());
        retVal &= validateControls(tietPinCode, tietPinCode.getText().toString());
        retVal &= (validateControls(tietVoter, tietVoter.getText().toString())
                || validateControls(tietPan, tietPan.getText().toString())
                || validateControls(tietDrivingLic, tietDrivingLic.getText().toString()));

        return retVal;
    }

}
