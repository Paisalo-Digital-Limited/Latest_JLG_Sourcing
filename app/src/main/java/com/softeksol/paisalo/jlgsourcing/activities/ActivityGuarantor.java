package com.softeksol.paisalo.jlgsourcing.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor_Table;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

public class ActivityGuarantor extends AppCompatActivity implements View.OnClickListener, CameraUtils.OnCameraCaptureUpdate {
    private Guarantor guarantor;
    private Uri uriPicture;
    private ImageView imageView;
    private Boolean cropState = false;
    private long guarantor_id;
    private long borrower_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_guarantor);
        borrower_id = getIntent().getLongExtra(Global.BORROWER_TAG, 0);
        guarantor_id = getIntent().getLongExtra(Global.GUARANTOR_TAG, 0);
        guarantor = null;
        guarantor = getGuarantor(guarantor_id, borrower_id);
        Button btnAdd = (Button) findViewById(R.id.btnGuarantorAdd);
        Button btnUpdate = (Button) findViewById(R.id.btnGuarantorUpdate);
        btnUpdate.setOnClickListener(this);
        Button btnDelete = (Button) findViewById(R.id.btnGuarantorDelete);
        btnDelete.setOnClickListener(this);
        btnAdd.setVisibility(guarantor == null ? View.VISIBLE : View.GONE);
        btnUpdate.setVisibility(guarantor != null ? View.VISIBLE : View.GONE);
        btnDelete.setVisibility(guarantor != null ? View.VISIBLE : View.GONE);
        imageView = ((ImageView) findViewById(R.id.imgViewAadharPhoto));
        imageView.setOnClickListener(this);
    }

    private void setDataToView(View v) {
        ((TextView) v.findViewById(R.id.tvAadharAadhar)).setText(guarantor.getAadharid());
        ((TextView) v.findViewById(R.id.tvAadharName)).setText(guarantor.getName());
        ((TextView) v.findViewById(R.id.tvAadharAge)).setText(String.valueOf(guarantor.getAge()));
        ((TextView) v.findViewById(R.id.tvAadharDob)).setText(DateUtils.getFormatedDate(guarantor.getDOB(), "dd-MMM-yyyy"));
        ((TextView) v.findViewById(R.id.tvAadharGuardian)).setText(guarantor.getGurName());
        ((TextView) v.findViewById(R.id.tvAadharAddress1)).setText(guarantor.getPerAdd1());
        ((TextView) v.findViewById(R.id.tvAadharAddress2)).setText(guarantor.getPerAdd2());
        ((TextView) v.findViewById(R.id.tvAadharAddress3)).setText(guarantor.getPerAdd3());
        ((TextView) v.findViewById(R.id.tvAadharCity)).setText(guarantor.getPerCity());
        ((TextView) v.findViewById(R.id.tvAadharPinCode)).setText(String.valueOf(guarantor.getP_pin()));

        ((TextView) v.findViewById(R.id.tvAadharState)).setText(guarantor.getP_StateID());
        ((TextView) v.findViewById(R.id.tvAadharGender)).setText(guarantor.getGender());

        ((EditText) v.findViewById(R.id.etLoanAppGuarantorMobile)).setText(guarantor.getPerMob1());
        ((EditText) v.findViewById(R.id.etLoanAppGuarantorVoter)).setText(guarantor.getVoterid());
        showPicture();
    }

    private void getDataFromView(View v) {
        guarantor.setPerMob1(Utils.getNotNullText((EditText) v.findViewById(R.id.etLoanAppGuarantorMobile)));
        guarantor.setVoterid(Utils.getNotNullText((EditText) v.findViewById(R.id.etLoanAppGuarantorVoter)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuarantorUpdate:
                updateGuarantor();
                finish();
                break;
            case R.id.btnGuarantorDelete:
                guarantor.delete();
                guarantor = null;
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
        if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                CropImage.activity(this.uriPicture)
                        .setGuidelines(CropImageView.Guidelines.ON)
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
                if (guarantor != null) {
                    (new File(this.uriPicture.getPath())).delete();
                    try {
                        File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true);
                        guarantor.setPicture(croppedImage.getPath());
                        guarantor.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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
            Glide.with(this).load(guarantor.getPicture().getPath()).override(Target.SIZE_ORIGINAL, 300).into(imageView);
        } else {
            DocumentStore documentStore = guarantor.getPictureStore();
            if (documentStore != null && documentStore.updateStatus) {
                Glide.with(this).load(R.mipmap.picture_uploaded).override(300, 300).into(imageView);
                imageView.setOnClickListener(null);
            }
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
            Toast.makeText(ActivityGuarantor.this, "Filepath Empty", Toast.LENGTH_SHORT).show();

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

    private Guarantor getGuarantor(long gid, long bid) {
        Guarantor guarantor = null;
        if (gid > 0) {
            guarantor = SQLite.select().from(Guarantor.class).where(Guarantor_Table.id.eq(gid)).querySingle();
        }
        return guarantor;
    }

    private void showGuarantor() {
        guarantor = getGuarantor(guarantor_id, borrower_id);
        if (guarantor != null) {
            setDataToView(this.findViewById(android.R.id.content).getRootView());
        }
    }

    private void updateGuarantor() {
        if (guarantor != null) {
            getDataFromView(this.findViewById(android.R.id.content).getRootView());
            guarantor.save();
        }
    }

    @Override
    protected void onPause() {
        updateGuarantor();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // updateGuarantor();
        // finish();
        //super.onBackPressed();
    }
}
