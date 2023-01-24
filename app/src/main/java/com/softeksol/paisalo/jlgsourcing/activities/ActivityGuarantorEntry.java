package com.softeksol.paisalo.jlgsourcing.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.AadharUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.AadharData;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower_Table;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor_Table;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class ActivityGuarantorEntry extends AppCompatActivity implements View.OnClickListener, CameraUtils.OnCameraCaptureUpdate {
    private Guarantor guarantor;
    private Borrower borrower;
    private Uri uriPicture;
    private ImageView imageView, imgViewScanQR;
    private Boolean cropState = false;
    private long guarantor_id;
    private long borrower_id;

    private AppCompatSpinner acspGender, acspRelationship, acspAadharState;
    private TextInputEditText tietName, tietAadharId, tietAge, tietDob, tietGuardian,
            tietAddress1, tietAddress2, tietAddress3, tietCity, tietPinCode,
            tietMobile, tietVoter, tietDrivingLic, tietPan;
    private Button btnAdd, btnUpdate, btnCancel, btnDelete;
    private TextWatcher dobTextWatcher, ageTextWatcher;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateSetListner;
    private Bitmap bitmap;
    private String ImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_guarantor_entry);
        borrower_id = getIntent().getLongExtra(Global.BORROWER_TAG, 0);
        guarantor_id = getIntent().getLongExtra(Global.GUARANTOR_TAG, 0);
        borrower = SQLite.select().from(Borrower.class).where(Borrower_Table.FiID.eq(borrower_id)).querySingle();
        guarantor = getGuarantor(guarantor_id, borrower_id);

        btnAdd = findViewById(R.id.btnGuarantorAdd);
        btnUpdate = findViewById(R.id.btnGuarantorUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnGuarantorDelete);
        btnDelete.setOnClickListener(this);
        btnAdd.setVisibility(guarantor == null ? View.VISIBLE : View.GONE);
        /*btnUpdate.setVisibility(guarantor!=null?View.VISIBLE:View.GONE);
        btnDelete.setVisibility(guarantor!=null?View.VISIBLE:View.GONE);*/

        ArrayList<RangeCategory> genders = new ArrayList<>();
        genders.add(new RangeCategory("Female", "Gender"));
        genders.add(new RangeCategory("Male", "Gender"));
        genders.add(new RangeCategory("Transgender", "Gender"));

        myCalendar = Calendar.getInstance();
        myCalendar.setTime(new Date());
        acspGender = (findViewById(R.id.acspGender));
        acspGender.setAdapter(new AdapterListRange(this, genders, false));

        acspRelationship = findViewById(R.id.acspRelationship);
        acspRelationship.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("relationship"), false));

        acspAadharState = findViewById(R.id.acspAadharState);
        acspAadharState.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("state"), false));

        tietName = findViewById(R.id.tietName);
        tietName.addTextChangedListener(new MyTextWatcher(tietName) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietAadharId = findViewById(R.id.tietAadhar);
        tietAadharId.addTextChangedListener(new MyTextWatcher(tietAadharId) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

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
        tietMobile = findViewById(R.id.tietMobile);
        tietMobile.addTextChangedListener(new MyTextWatcher(tietMobile) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietVoter = findViewById(R.id.tietVoter);
        tietVoter.addTextChangedListener(new MyTextWatcher(tietVoter) {
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
        tietPan = findViewById(R.id.tietPAN);
        tietPan.addTextChangedListener(new MyTextWatcher(tietPan) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });


        imageView = findViewById(R.id.imgViewAadharPhoto);
        imageView.setOnClickListener(this);
        imgViewScanQR = findViewById(R.id.imgViewScanQR);
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
    }

    private void setDataToView(View v) {
        tietAadharId.setText(guarantor.getAadharid());
        tietName.setText(guarantor.getName());

        tietAge.setText(String.valueOf(guarantor.getAge()));
        Utils.setOnFocuseSelect(tietAge, "0");
        if (guarantor.getDOB() == null) {
            tietDob.setText(DateUtils.getDobFrmAge(Integer.parseInt(tietAge.getText().toString())));
        } else {
            myCalendar.setTime(guarantor.getDOB());
            tietDob.setText(DateUtils.getFormatedDate(guarantor.getDOB(), "dd-MMM-yyyy"));
        }

        tietGuardian.setText(guarantor.getGurName());
        tietAddress1.setText(guarantor.getPerAdd1());
        tietAddress2.setText(guarantor.getPerAdd2());
        tietAddress3.setText(guarantor.getPerAdd3());
        tietCity.setText(guarantor.getPerCity());
        tietPinCode.setText(String.valueOf(guarantor.getP_pin()));
        Utils.setOnFocuseSelect(tietPinCode, "0");


        if (guarantor.getGender() != null) {
            Utils.setSpinnerPosition(acspGender, guarantor.getGender().charAt(0), true);
        }
        if (guarantor.getResFax() != null) {
            Utils.setSpinnerPosition(acspRelationship, guarantor.getResFax(), false);
        }
        if (guarantor.getP_StateID() != null) {
            Utils.setSpinnerPosition(acspAadharState, guarantor.getP_StateID());
        }

        tietMobile.setText(guarantor.getPerMob1());
        tietVoter.setText(guarantor.getVoterid());

        tietPan.setText(guarantor.getPANNo());
        tietDrivingLic.setText(guarantor.getDrivinglic());


        if (guarantor.getIsAadharVerified().equals("Q")) {
            tietName.setEnabled(false);
            if (Utils.NullIf(guarantor.getGurName(), "").trim().length() > 0)
                tietGuardian.setEnabled(false);
            if (Utils.NullIf(guarantor.getAge(), 0) > 0) {
                tietAge.setEnabled(false);
                tietDob.setEnabled(false);
            }
            acspGender.setEnabled(false);
            acspAadharState.setEnabled(false);
            if (guarantor.getPerAdd1().trim().length() > 0) tietAddress1.setEnabled(false);
            if (Utils.NullIf(guarantor.getPerAdd2(), "").trim().length() > 0)
                tietAddress2.setEnabled(false);
            if (Utils.NullIf(guarantor.getPerAdd3(), "").trim().length() > 0)
                tietAddress3.setEnabled(false);
            if (Utils.NullIf(guarantor.getPerCity(), "").trim().length() > 0)
                tietCity.setEnabled(false);
            if (Utils.NullIf(guarantor.getP_pin(), 0) > 0) tietPinCode.setEnabled(false);
        }
        showPicture();
    }

    private void getDataFromView(View v) {
        guarantor.setAadharid(Utils.getNotNullText(tietAadharId));
        guarantor.setName(Utils.getNotNullText(tietName));
        guarantor.setAge(Utils.getNotNullInt(tietAge));
        guarantor.setDOB(myCalendar.getTime());

        guarantor.setGurName(Utils.getNotNullText(tietGuardian));
        guarantor.setPerAdd1(Utils.getNotNullText(tietAddress1));
        guarantor.setPerAdd2(Utils.getNotNullText(tietAddress2));
        guarantor.setPerAdd3(Utils.getNotNullText(tietAddress3));
        guarantor.setPerCity(Utils.getNotNullText(tietCity));
        guarantor.setP_pin(Utils.getNotNullInt(tietPinCode));

        guarantor.setGender(((RangeCategory) acspGender.getSelectedItem()).RangeCode.substring(0, 1));
        guarantor.setResFax(((RangeCategory) acspRelationship.getSelectedItem()).RangeCode);
        guarantor.setP_StateID(((RangeCategory) acspAadharState.getSelectedItem()).RangeCode);

        guarantor.setPerMob1(Utils.getNotNullText(tietMobile));

        guarantor.setVoterid(Utils.getNotNullText(tietVoter));
        guarantor.setDrivinglic(Utils.getNotNullText(tietDrivingLic));
        guarantor.setPANNo(Utils.getNotNullText(tietPan));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewScanQR:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
                break;
            case R.id.btnGuarantorUpdate:
                updateGuarantor();
//                finish();
                break;
            case R.id.btnGuarantorDelete:
                deleteGuarantor();
                //guarantor = null;
                break;
            case R.id.imgViewAadharPhoto:
                cropState = true;
                ImagePicker.with(this)
                        .cameraOnly()
                        .start(CameraUtils.REQUEST_TAKE_PHOTO);
//                try {
//                    CameraUtils.dispatchTakePictureIntent(this);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.tietDob:
                Date dob = DateUtils.getParsedDate(tietDob.getText().toString(), "dd-MMM-yyyy");
                myCalendar.setTime(dob);
                new DatePickerDialog(ActivityGuarantorEntry.this, dateSetListner,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if (scanFormat != null) {
                    setAadharContent(scanContent);
                }
            }
        }
        if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {

                if (data!=null){
                    uriPicture = data.getData();
                    CropImage.activity(uriPicture)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(45, 52)
                            .start(this);
                }
                else {
                    Toast.makeText(ActivityGuarantorEntry.this, "Image Data Null", Toast.LENGTH_SHORT).show();
                }
//                CropImage.activity(this.uriPicture)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setAspectRatio(45, 52)
//                        .start(this);
            } else {
                Utils.alert(this, "Could not take Picture");
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Exception error = null;

            if (data!=null){
                Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 300, error, false);
                File tempCroppedImage = new File(imageUri.getPath());

                Log.e("tempCroppedImageGrntr",tempCroppedImage.getAbsolutePath()+"");
                if (error ==null){

                    if (imageUri!=null){
                        if (tempCroppedImage.length()>100){
                            if (guarantor != null) {
                                (new File(this.uriPicture.getPath())).delete();


                                try {

//                                    if (android.os.Build.VERSION.SDK_INT >= 29) {
//                                        bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getContentResolver(), imageUri));
//                                    } else {
//                                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                                    }
//
//
//                                    ImageString = bitmapToBase64(bitmap);

                                    File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true);
                                    Log.e("croppedImageGRN",croppedImage.getAbsolutePath()+"");
                                    Log.e("croppedImageGRN1",croppedImage.getPath()+"");
                                    guarantor.setPicture(croppedImage.getPath());
                                    //guarantor.setPictureGuarantor(croppedImage.getPath(),ImageString);
                                    guarantor.save();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else {
                            Log.e("tempCroppedImage",tempCroppedImage.length()+"");
                            //Toast.makeText(ActivityGuarantorEntry.this, tempCroppedImage.length()+"", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Log.e("imageUriguarantor",imageUri.toString()+"");
                        //Toast.makeText(ActivityGuarantorEntry.this, imageUri.toString()+"", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Log.e("ErrorGuarantor",error.toString()+"");
                    //Toast.makeText(ActivityGuarantorEntry.this, error.toString()+"", Toast.LENGTH_SHORT).show();
                }
            }else {
                Log.e("CropImageDataGuarantor","Null");
                //Toast.makeText(ActivityGuarantorEntry.this, "CropImage data: NULL" , Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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
            guarantor.setIsAadharVerified("Q");
            guarantor.setName(aadharData.Name);
            guarantor.setDOB(aadharData.DOB);
            guarantor.setAge(aadharData.Age);
            guarantor.setGender(aadharData.Gender);
            guarantor.setGurName(aadharData.GurName);
            guarantor.setPerCity(aadharData.City);
            guarantor.setP_pin(aadharData.Pin);
            guarantor.setPerAdd1(aadharData.Address1);
            guarantor.setPerAdd2(aadharData.Address2);
            guarantor.setPerAdd3(aadharData.Address3);
            guarantor.setP_StateID(AadharUtils.getStateCode(aadharData.State));
            if (aadharData.AadharId != null)
                guarantor.setAadharid(aadharData.AadharId);
            setDataToView(this.findViewById(android.R.id.content).getRootView());
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
        showGuarantor();
    }

    private void showPicture() {

//        if (guarantor.getPictureGuarantor()!=null){
//            imageView.setImageBitmap(StringToBitmap(guarantor.getPictureGuarantor()));
//        }
        if (guarantor.getPicture() != null && (new File(guarantor.getPicture().getPath())).length() > 100) {

            setImagepath(new File(guarantor.getPicture().getPath()));
            //Glide.with(this).load(guarantor.getPicture().getPath()).override(Target.SIZE_ORIGINAL, 300).into(imageView);
        } else {
            DocumentStore documentStore = guarantor.getPictureStore();
            if (documentStore != null && documentStore.updateStatus) {
                Glide.with(this).load(R.mipmap.picture_uploaded).override(300, 300).into(imageView);
                imageView.setOnClickListener(null);
            }
        }
    }

    private Bitmap StringToBitmap(String string){
        try {
            byte [] encodebyte  = Base64.decode(string, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodebyte,0,encodebyte.length);
        } catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    private void setImagepath(File file) {

//        File imgFile = new  File("/sdcard/Images/test_image.jpg");

        if(file.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

            if (myBitmap!=null){
                imageView.setImageBitmap(myBitmap);
            }else {
                //Toast.makeText(ActivityGuarantorEntry.this, "Bitmap Null", Toast.LENGTH_SHORT).show();
                Log.e("BitmapImage","Null");
            }
        }else  {
            Toast.makeText(ActivityGuarantorEntry.this, "Filepath Empty", Toast.LENGTH_SHORT).show();

        }
    }

    private Guarantor getGuarantor(long gid, long bid) {
        guarantor = null;
        if (gid > 0) {
            guarantor = SQLite.select().from(Guarantor.class).where(Guarantor_Table.id.eq(gid)).querySingle();
        }
        if (guarantor == null) {
            guarantor = new Guarantor();
            guarantor.setP_StateID(borrower.p_state);
            guarantor.associateBorrower(borrower);
            guarantor.setIsAadharVerified("N");
            //guarantor.setUserID();
        }
        return guarantor;
    }

    private void showGuarantor() {
        if (guarantor != null) {
            setDataToView(this.findViewById(android.R.id.content).getRootView());
        }
    }

    private void updateGuarantor() {
        if (guarantor != null) {
            getDataFromView(this.findViewById(android.R.id.content).getRootView());
            guarantor.associateBorrower(borrower);
            if (guarantor.getPictureStore() == null) {
                Toast.makeText(this,"Guarantor Picture not captured",Toast.LENGTH_SHORT).show();
                //Utils.alert(this, "Guarantor Picture not captured");
            } else {
                if (validateGuarantor()) {
                    guarantor.save();
                    finish();
                } else {
                    Toast.makeText(this,"There is at least one errors in the Guarantor Data",Toast.LENGTH_SHORT).show();
                    //Utils.alert(this, "There is at least one errors in the Guarantor Data");
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateGuarantor();
    }

    @Override
    public void onBackPressed() {
        updateGuarantor();
        finish();
        super.onBackPressed();
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

    private boolean validateGuarantor() {
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

    private void deleteGuarantor() {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Financing", "Fetching Co-Borrower / Guarantor 1 Data") {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                guarantor.delete();
                guarantor = null;
                finish();
            }
        };
        RequestParams params = new RequestParams();
        params.add("FiCode", String.valueOf(borrower.Code));
        params.add("Creator", guarantor.getCreator());
        params.add("GrNo", String.valueOf(guarantor.getGrNo()));

        (new WebOperations()).getEntity(this, "POSGuarantor", "DeleteGuarantor", params, asyncResponseHandler);

    }

}
