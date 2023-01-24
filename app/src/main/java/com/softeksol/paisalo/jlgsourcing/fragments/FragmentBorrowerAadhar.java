package com.softeksol.paisalo.jlgsourcing.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.CustomProgress;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory_Table;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils.REQUEST_TAKE_PHOTO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBorrowerAadhar.OnFragmentBorrowerAadharInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBorrowerAadhar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBorrowerAadhar extends AbsFragment implements View.OnClickListener, CameraUtils.OnCameraCaptureUpdate {

    private long borrowerId;
    private OnFragmentBorrowerAadharInteractionListener mListener;
    private AdapterListRange rlaGender;

    private ActivityLoanApplication activity;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private Bitmap imageBitmap;
    private Uri uriPicture;
    private Boolean cropState = false, isCurrentAddressVisible = false;
    private ImageView imageView;
    private TextView tietMobile, tietDrivingLic, tietPanNo, tietVoterId;
    private TextView tietBusinessDetail, tietLoanReason, tieBankAcNo, tietLoanAMount;
    private TextInputEditText
            tietCurrentAddress1, tietCurrentAddress2, tietCurrentAddress3, tietCurrentCity, tietCurrentPin;
    private CheckBox chkCurrentAddressDifferent;

//    private  CustomProgress customProgress;

    private Bitmap bitmap;
    private String ImageString;

    public FragmentBorrowerAadhar() {
    }

    public static FragmentBorrowerAadhar newInstance(long borrowerId) {
        FragmentBorrowerAadhar fragment = new FragmentBorrowerAadhar();
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
        rlaGender = new AdapterListRange(this.getContext(),
                SQLite.select().from(RangeCategory.class)
                        .where(RangeCategory_Table.cat_key.eq("gender"))
                        .queryList(), false);
        activity = (ActivityLoanApplication) getActivity();
        super.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View v = inflater.inflate(R.layout.fragment_borrower_aadhar, container, false);
        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);
        //activity.setNavOnClikListner(imgViewLeft);
        imgViewLeft.setVisibility(View.GONE);


        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        activity.setNavOnClikListner(imgViewRight);


        imageView = (ImageView) v.findViewById(R.id.imgViewAadharPhoto);
        (imageView).setOnClickListener(this);
        tietMobile = (TextView) v.findViewById(R.id.tietLoanAppAadharApplicantMobile);

        tietPanNo = (TextView) v.findViewById(R.id.tietLoanAppAadharApplicantPan);

        tietVoterId = (TextView) v.findViewById(R.id.tietLoanAppAadharApplicantVoter);

        tietDrivingLic = (TextView) v.findViewById(R.id.tietLoanAppAadharApplicantDrivingLicense);

        tietBusinessDetail = (TextView) v.findViewById(R.id.tietLoanAppAadharBusinessDetail);
        tietLoanReason = (TextView) v.findViewById(R.id.tietLoanAppAadharLoanReason);
        tieBankAcNo = (TextView) v.findViewById(R.id.tietLoanAppAadharBankAcNo);
        tietLoanAMount = (TextView) v.findViewById(R.id.tietLoanAppAadharLoanAmount);


        tietCurrentAddress1 = (TextInputEditText) v.findViewById(R.id.tietCurrentAddress1);
        tietCurrentAddress1.addTextChangedListener(new MyTextWatcher(tietCurrentAddress1) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietCurrentAddress2 = (TextInputEditText) v.findViewById(R.id.tietCurrentAddress2);
        tietCurrentAddress2.addTextChangedListener(new MyTextWatcher(tietCurrentAddress2) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietCurrentAddress3 = (TextInputEditText) v.findViewById(R.id.tietCurrentAddress3);
        tietCurrentAddress3.addTextChangedListener(new MyTextWatcher(tietCurrentAddress3) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietCurrentCity = (TextInputEditText) v.findViewById(R.id.tietCurrentCity);
        tietCurrentCity.addTextChangedListener(new MyTextWatcher(tietCurrentCity) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietCurrentPin = (TextInputEditText) v.findViewById(R.id.tietCurrentPinCode);
        tietCurrentPin.addTextChangedListener(new MyTextWatcher(tietCurrentPin) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        chkCurrentAddressDifferent = (CheckBox) v.findViewById(R.id.chkDifferentAddressThenAadhar);
        chkCurrentAddressDifferent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCurrentAddressVisible = isChecked;
                v.findViewById(R.id.loCurrentAddress).setVisibility(isChecked ? View.VISIBLE : View.GONE);
                if (isChecked) {
                    validateControls(tietCurrentAddress1, "");
                    validateControls(tietCurrentCity, "");
                }
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentBorrowerAadharInteractionListener) {
            mListener = (OnFragmentBorrowerAadharInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        //Log.d("onDetach", "");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataToView(getView());
    }

    @Override
    public void onPause() {
        getDataFromView(getView());
        super.onPause();
    }

    private void setDataToView(View v) {
        Borrower borrower = activity.getBorrower();
        TextView tvAadharId = (TextView) v.findViewById(R.id.tvAadharAadhar);
        if (borrower.aadharid != null) {
            tvAadharId.setVisibility((borrower.aadharid.trim().length() == 12) ? View.VISIBLE : View.INVISIBLE);
            (tvAadharId).setText(borrower.aadharid);
        } else {
            tvAadharId.setVisibility(View.INVISIBLE);
        }
        ((TextView) v.findViewById(R.id.tvAadharName)).setText(borrower.getBorrowerName());
        ((TextView) v.findViewById(R.id.tvAadharAge)).setText(String.valueOf(borrower.Age));
        ((TextView) v.findViewById(R.id.tvAadharDob)).setText(DateUtils.getFormatedDate(borrower.DOB, "dd-MMM-yyyy"));
        ((TextView) v.findViewById(R.id.tvAadharGuardian)).setText(borrower.getGurName());
        ((TextView) v.findViewById(R.id.tvAadharAddress1)).setText(borrower.P_Add1);
        ((TextView) v.findViewById(R.id.tvAadharAddress2)).setText(borrower.P_add2);
        ((TextView) v.findViewById(R.id.tvAadharAddress3)).setText(borrower.P_add3);
        ((TextView) v.findViewById(R.id.tvAadharCity)).setText(borrower.P_city);
        ((TextView) v.findViewById(R.id.tvAadharPinCode)).setText(String.valueOf(borrower.p_pin));

        ((TextView) v.findViewById(R.id.tvAadharState)).setText(borrower.p_state);
        ((TextView) v.findViewById(R.id.tvAadharGender)).setText(borrower.Gender);


        showPicture(borrower);

        tietMobile.setText(borrower.P_ph3);
        tietPanNo.setText(borrower.PanNO);
        tietVoterId.setText(borrower.voterid);
        tietDrivingLic.setText(borrower.drivinglic);

        tietCurrentAddress1.setText(borrower.O_Add1);
        tietCurrentAddress2.setText(borrower.O_Add2);
        tietCurrentAddress3.setText(borrower.O_Add3);
        tietCurrentCity.setText(borrower.O_City);
        tietCurrentPin.setText(Utils.getNotNullString(borrower.o_pin));
        isCurrentAddressVisible = Utils.NullIf(activity.getBorrower().isCurrentAddressDifferent, "N").equals("Y");
        chkCurrentAddressDifferent.setChecked(isCurrentAddressVisible);

        tietBusinessDetail.setText(Utils.getNotNullString(borrower.Business_Detail));
        ;
        tietLoanReason.setText(Utils.getNotNullString(borrower.Loan_Reason));
        tieBankAcNo.setText(Utils.getNotNullString(borrower.bank_ac_no));
        tietLoanAMount.setText(Utils.getNotNullString(borrower.Loan_Amt));

        //v.findViewById(R.id.loCurrentAddress).setVisibility(isCurrentAddressVisible?View.VISIBLE:View.GONE);
    }

    private void getDataFromView(View v) {
        Borrower borrower = activity.getBorrower();
        if (v == null || borrower == null) return;
        borrower.P_ph3 = Utils.getNotNullText(tietMobile);
        borrower.PanNO = Utils.getNotNullText(tietPanNo);
        borrower.voterid = Utils.getNotNullText(tietVoterId);
        borrower.drivinglic = Utils.getNotNullText(tietDrivingLic);

        borrower.isCurrentAddressDifferent = isCurrentAddressVisible ? "Y" : "N";
        if (borrower.isCurrentAddressDifferent.equals("N")) {
            borrower.O_Add1 = borrower.P_Add1;
            borrower.O_Add2 = borrower.P_add2;
            borrower.O_Add3 = borrower.P_add3;
            borrower.O_City = borrower.P_city;
            borrower.o_pin = borrower.p_pin;
        } else {
            borrower.O_Add1 = Utils.getNotNullText(tietCurrentAddress1);
            borrower.O_Add2 = Utils.getNotNullText(tietCurrentAddress2);
            borrower.O_Add3 = Utils.getNotNullText(tietCurrentAddress3);
            borrower.O_City = Utils.getNotNullText(tietCurrentCity);
            borrower.o_pin = Utils.getNotNullInt(tietCurrentPin);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewAadharPhoto:
                cropState = true;

                ImagePicker.with(this)
                        .cameraOnly()
                        .start(REQUEST_TAKE_PHOTO);
//                try {
//                    CameraUtils.dispatchTakePictureIntent(FragmentBorrowerAadhar.this);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    @Override
    public void cameraCaptureUpdate(Uri imagePath) {
        uriPicture = imagePath;
    }

    @Override
    public String getName() {
        return "Aadhar Data";
    }

    public interface OnFragmentBorrowerAadharInteractionListener {
        void onFragmentBorrowerAadharInteraction(Borrower borrower);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {


            if (data != null) {
                uriPicture = data.getData();
                CropImage.activity(uriPicture)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(45, 52)
                        .start(requireContext(), this);
            } else {
                Log.e("ImageData","Null");
                Toast.makeText(activity, "Image Data Null", Toast.LENGTH_SHORT).show();
            }

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Exception error = null;


            if (data != null) {
                Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 300, error, false);
                //Toast.makeText(activity, imageUri.toString(), Toast.LENGTH_SHORT).show();
                File tempCroppedImage = new File(imageUri.getPath());
                Log.e("tempCroppedImage",tempCroppedImage.getPath()+"");


                if (error == null) {

                    if (imageUri != null) {

                        if (tempCroppedImage.length() > 100) {
                            Borrower borrower = activity.getBorrower();
                            if (borrower != null) {
                                (new File(uriPicture.getPath())).delete();
                                try {


//                                    if (android.os.Build.VERSION.SDK_INT >= 29) {
//                                        bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getActivity().getContentResolver(), imageUri));
//                                    } else {
//                                        bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
//                                    }
//
//
//                                    ImageString = bitmapToBase64(bitmap);

                                    File croppedImage = CameraUtils.moveCachedImage2Storage(getContext(), tempCroppedImage, true);
//                                    if (android.os.Build.VERSION.SDK_INT >= 29) {
//                                        bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getActivity().getContentResolver(), imageUri));
//                                    } else {
//                                        bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
//                                    }
//                                    //Log.e("CroppedImageMyBitmap", bitmap+ "");
                                    Log.e("CroppedImageFile1", croppedImage.getPath()+"");
                                    Log.e("CroppedImageFile2", croppedImage.getAbsolutePath()+"");
                                    Log.e("CroppedImageFile3", croppedImage.getCanonicalPath()+"");
                                    Log.e("CroppedImageFile4", croppedImage.getParent()+"");
                                    Log.e("CroppedImageFile5", croppedImage.getParentFile().getCanonicalPath()+"");
                                    Log.e("CroppedImageFile6", croppedImage.getParentFile().getName()+"");
//
//                                    ImageString = bitmapToBase64(bitmap);

//                                    borrower.setPicture(croppedImage.getPath(),ImageString);
                                    borrower.setPicture(croppedImage.getPath());
                                    borrower.Oth_Prop_Det = null;
                                    borrower.save();
                                    showPicture(borrower);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else {
                            Toast.makeText(activity, "CroppedImage FIle Length:"+tempCroppedImage.length() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, imageUri.toString() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, error.toString() + "", Toast.LENGTH_SHORT).show();
                }
            } else {
//                Log.e("Error",data.getData()+"");
                Toast.makeText(activity, "CropImage data: NULL", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void showPicture(Borrower borrower) {


//        Log.e("CHeckingNewCondition",borrower.getPictureborrower()+"");
        if (borrower != null) {


//            if (borrower.getPictureborrower()!=null){
//                imageView.setImageBitmap(StringToBitmap(borrower.getPictureborrower()));
//            }

            //Log.d("BorrowerImagePath",borrower.getPicture().getPath());
            if (borrower.getPicture() != null && (new File(borrower.getPicture().getPath())).length() > 100) {
                Log.d("BorrowerImagePath1",borrower.getPicture().getPath());
                Toast.makeText(activity, "BorrowerPicture: " + borrower.getPicture().getPath() + "", Toast.LENGTH_SHORT).show();

               setImagepath(new File(borrower.getPicture().getPath()));
                //imageView.setImageBitmap(StringToBitmap(borrower.getPictureborrower()));
                //Glide.with(activity).load(borrower.getPicture().getPath()).override(Target.SIZE_ORIGINAL, 300).into(imageView);
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

//        customProgress.hideProgress();
        Toast.makeText(activity, "Checking File: "+file.getAbsolutePath()+"", Toast.LENGTH_SHORT).show();

        if (file.length() != 0) {

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

            if (myBitmap != null) {
                imageView.setImageBitmap(myBitmap);
                Log.e("CHeckingmyBitmap22",myBitmap+"");
            } else {
                Toast.makeText(activity, "Bitmap Null", Toast.LENGTH_SHORT).show();
                Log.e("BitmapImage", "Null");
            }
        } else {
            Toast.makeText(activity, "Filepath Empty", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean validateControls(EditText editText, String text) {
        boolean retVal = true;
        editText.setError(null);
        switch (editText.getId()) {
            case R.id.tietLoanAppAadharApplicantMobile:
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
            case R.id.tietCurrentPinCode:
                if (editText.getText().toString().trim().length() > 0 && isCurrentAddressVisible) {
                    if (editText.getText().toString().trim().length() != 6) {
                        editText.setError("Should be of 6 digits");
                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietCurrentAddress1:
                if (isCurrentAddressVisible) {
                    if (editText.getText().toString().trim().length() < 10) {
                        editText.setError("Should be more than 9 Characters");
                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietCurrentCity:
                if (isCurrentAddressVisible) {
                    if (editText.getText().toString().trim().length() < 4) {
                        editText.setError("Should be more than 3 Characters");
                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietCurrentAddress2:
            case R.id.tietCurrentAddress3:
                if (editText.getText().toString().trim().length() > 0 && isCurrentAddressVisible) {
                    if (editText.getText().toString().trim().length() < 5) {
                        editText.setError("Should be of 5 characters");
                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietLoanAppAadharApplicantVoter:
            case R.id.tietLoanAppAadharApplicantPan:
            case R.id.tietLoanAppAadharApplicantDrivingLicense:
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

    private void editAadhar() {
        final EditText input = new EditText(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Edit Aadhar");
        builder.setView(input);
        DialogInterface.OnClickListener onSaveClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.getBorrower().aadharid = input.getText().toString();
                activity.getBorrower().save();
                setDataToView(getView());
            }
        };
        builder.setPositiveButton("Save", onSaveClickListener);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean enable = false;
                if (s.length() == 12)
                    if (!s.toString().toUpperCase().contains("X"))
                        enable = Verhoeff.validateVerhoeff(s.toString());
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(enable);
            }
        });
        dialog.show();
    }

}
