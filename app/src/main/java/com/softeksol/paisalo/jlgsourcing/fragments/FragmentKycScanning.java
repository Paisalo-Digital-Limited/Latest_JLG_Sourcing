package com.softeksol.paisalo.jlgsourcing.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListDocuments;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils.REQUEST_TAKE_PHOTO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentKycScanning.OnListFragmentKycScanInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentKycScanning#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKycScanning extends AbsFragment implements AdapterView.OnItemClickListener, CameraUtils.OnCameraCaptureUpdate {
    private OnListFragmentKycScanInteractionListener mListener;
    private List<DocumentStore> documentStores = new ArrayList<>();
    private AdapterListDocuments adapterListDocuments;
    private DocumentStore mDocumentStore;
    private long docId;
    private ListView listView;
    private ActivityLoanApplication activity;
    private Uri uriPicture;
    private String ImageString;
    private Bitmap bitmap;

    public FragmentKycScanning() {
        // Required empty public constructor
    }


    public static FragmentKycScanning newInstance() {
        return new FragmentKycScanning();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterListDocuments = new AdapterListDocuments(getActivity(), R.layout.layout_item_loan_app_kyc_capture, documentStores);
        activity = (ActivityLoanApplication) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_borrower_kyc_scanning, container, false);
        ImageView imgViewLeft = (ImageView) v.findViewById(R.id.btnNavLeft);

        activity.setNavOnClikListner(imgViewLeft);
        ImageView imgViewRight = (ImageView) v.findViewById(R.id.btnNavRight);
        imgViewRight.setVisibility(View.GONE);

        listView = (ListView) v.findViewById(R.id.lvList);
        listView.setAdapter(adapterListDocuments);

        listView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentKycScanInteractionListener) {
            mListener = (OnListFragmentKycScanInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        documentStores.clear();
        documentStores.addAll(getDocumentStore(activity.getBorrower()));
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                adapterListDocuments.notifyDataSetChanged();
            }
        }, 1000);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDocumentStore = (DocumentStore) adapterView.getItemAtPosition(i);
        //Utils.showSnakbar(getView(),String.valueOf(mDocumentStore.checklistid));
        //Log.d("Document Store", mDocumentStore.toString());
        if (mDocumentStore.updateStatus == true) {
            Utils.showSnakbar(getView(), "This Document Already uploaded");
        } else {

            ImagePicker.with(this)
                    .cameraOnly()
                    .compress(500)
                    .start(CameraUtils.REQUEST_TAKE_PHOTO);
//            try {
//                CameraUtils.dispatchTakePictureIntent(this);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void cameraCaptureUpdate(Uri imagePath) {
        uriPicture = imagePath;
    }

    @Override
    public String getName() {
        return "KYC Scanning";
    }

    public interface OnListFragmentKycScanInteractionListener {
        void onListFragmentKycScanInteraction(Borrower borrower);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("Success0", "Data Aaya hai Image kaa");
        if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                Log.e("Success", "Data Aaya hai Image kaa");
                Log.e("SuccessCam", data.getData() + "");
                uriPicture = data.getData();
                CropImage.activity(uriPicture)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(requireContext(), this);
            } else {
                Toast.makeText(activity, "Image Data Null", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Exception error = null;

            if (data != null) {
                Log.e("SuccessCrop", "Data Aaya hai Crop Image kaa");
                Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 1000, error, true);
                File tempCroppedImage = new File(imageUri.getPath());
                if (error == null) {

                    if (imageUri != null) {

                        if (tempCroppedImage.length() > 100) {

                            if (mDocumentStore != null) {

                                (new File(uriPicture.getPath())).delete();

                                try {



                                    File croppedImage = CameraUtils.moveCachedImage2Storage(getContext(), tempCroppedImage, true);
                                    //Toast.makeText(activity, "CroppedImageFile: " + croppedImage.getPath() + "", Toast.LENGTH_SHORT).show();
                                    Log.e("CroppedImageFile2", croppedImage.getPath() + "");

                                    //bitmap = BitmapFactory.decodeFile(croppedImage.getAbsolutePath());

                                    Log.e("CroppedImageMyBitmap", bitmap+ "");

                                    mDocumentStore.imagePath = croppedImage.getPath();
                                    //mDocumentStore.imageshow = ImageString;
                                    mDocumentStore.save();

                                    //adapterListDocuments.notifyDataSetChanged();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("mDocumentStore", "Null_AAYA_HAI");
                            }
                        } else {
                            Log.e("tempCroppedImage", tempCroppedImage.length() + "");
                            Toast.makeText(activity, tempCroppedImage.length() + "", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.e("imageUriguarantor", imageUri.toString() + "");
                        Toast.makeText(activity, imageUri.toString() + "", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("ErrorGuarantor", error.toString() + "");
                    Toast.makeText(activity, error.toString() + "", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("CropImageDataGuarantor", "Null");
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



    private List<DocumentStore> getDocumentStore(Borrower borrower) {
        Log.e("DocumentStoreChecking", "DONE");
        List<DocumentStore> documentStores = new ArrayList<>();
        DocumentStore documentStore = new DocumentStore();
        //documentStores.add(documentStore.getDocumentStore(borrower,0,"Picture"));
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

        documentStores.add(documentStore.getDocumentStore(borrower, 2, "PassbookFirst"));
        documentStores.add(documentStore.getDocumentStore(borrower, 2, "PassbookLast"));
        //documentStores.add(documentStore.getDocumentStore(borrower,4,"Mandate"));
        for (Guarantor guarantor : borrower.getFiGuarantors()) {
            //documentStores.add(documentStore.getDocumentStore(guarantor,0,"Picture"));

            if (BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing") {
                if (!Utils.NullIf(guarantor.getIsAadharVerified(), "N").equals("Y")) {
                    documentStores.add(documentStore.getDocumentStore(guarantor, 3, "AadharFront"));
                    documentStores.add(documentStore.getDocumentStore(guarantor, 3, "AadharBack"));
                }
                documentStores.add(documentStore.getDocumentStore(guarantor, 7, "VoterFront"));
                documentStores.add(documentStore.getDocumentStore(guarantor, 7, "VoterBack"));
            } else {
                if (!Utils.NullIf(guarantor.getIsAadharVerified(), "N").equals("Y")) {
                    documentStores.add(documentStore.getDocumentStore(guarantor, 7, "AadharFront"));
                    documentStores.add(documentStore.getDocumentStore(guarantor, 7, "AadharBack"));
                }
                if (guarantor.getVoterid().length() > 9) {



                    documentStores.add(documentStore.getDocumentStore(guarantor, 5, "VoterFront"));
                    documentStores.add(documentStore.getDocumentStore(guarantor, 5, "VoterBack"));
                }
            }
            if (guarantor.getPANNo().length() == 10) {
                documentStores.add(documentStore.getDocumentStore(guarantor, 8, "PANCard"));
            }
            if (guarantor.getDrivinglic().length() > 9) {
                documentStores.add(documentStore.getDocumentStore(guarantor, 16, "DrivingLicFront"));
                documentStores.add(documentStore.getDocumentStore(guarantor, 16, "DrivingLicBack"));
            }
        }
        return documentStores;
    }
}
