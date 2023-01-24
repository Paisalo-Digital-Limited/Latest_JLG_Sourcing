package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.journeyapps.barcodescanner.Size;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentKycScanning;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sachindra on 12/7/2016.
 */
public class CameraUtils {
    public static final int REQUEST_TAKE_PHOTO = 101;
    public static final int SELECT_PICTURE = 110;
    static String mCurrentPhotoPath;
    private static String TAG = "CameraUtils";

    /**
     * {@link FragmentKycScanning.OnListFragmentKycScanInteractionListener} interface
     **/
    public static void dispatchTakePictureIntent(Activity activity) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = createImageFile(activity);
            mCurrentPhotoPath = photoFile.getAbsolutePath();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = Uri.fromFile(photoFile);
                Uri photoURI = FileProvider.getUriForFile(activity,
                        activity.getApplicationContext().getPackageName() + ".provider", photoFile);
                ((OnCameraCaptureUpdate) activity).cameraCaptureUpdate(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public static void dispatchTakePictureIntent(Fragment fragment) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.requireActivity().getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = createImageFile(fragment.getActivity());
            mCurrentPhotoPath = photoFile.getAbsolutePath();

            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = Uri.fromFile(photoFile);
                Uri photoURI = FileProvider.getUriForFile(fragment.getActivity(), fragment.getActivity().getApplicationContext().getPackageName() + ".provider", photoFile);
                ((OnCameraCaptureUpdate) fragment).cameraCaptureUpdate(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public static void picMediaImage(Activity activity) throws IOException {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public static File createImageFile(Context context) throws IOException {
        return createImageFile(context, "JPEG");
    }

    public static File createImageFile(Context context, String fileExtention) throws IOException {
        return createImageFile(context, null, fileExtention);
    }

    public static File getImagePath() {
        return Utils.getFilePath(Environment.DIRECTORY_PICTURES);
    }

    public static File createImageFile(Context context, String fileName, String fileExtention) throws IOException {
        return Utils.createFile(context, fileName, fileExtention, Environment.DIRECTORY_PICTURES);
    }

    private static File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    public interface OnCameraCaptureUpdate {
        void cameraCaptureUpdate(Uri uriImage);
    }

    public static byte[] finaliseImageCrop(int resultCode, Intent data, int MaxDimentions, @Nullable Exception error) {
        return finaliseImageCrop(resultCode, data, MaxDimentions, error, false);
    }

    public static byte[] finaliseImageCrop(int resultCode, Intent data, int MaxDimentions, @Nullable Exception error, Boolean toGrayScale) {
        byte[] bytes = null;
        error = null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            Bitmap bitmap = BitmapUtility.getPic(resultUri, new Size(MaxDimentions, MaxDimentions));
            if (toGrayScale) {
                bitmap = BitmapUtility.androidGrayScale(bitmap);
            }
            bytes = BitmapUtility.getBitmapByteArray(bitmap);
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
        }
        return bytes;
    }

    /*public static Uri finaliseImageCropUri(int resultCode, Intent data,int MaxDimentions, @Nullable Exception error, Boolean toGrayScale){
        error=null;
        Uri resultUri=null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            resultUri = result.getUri();
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
        }
        return resultUri;
    }*/
    public static Uri finaliseImageCropUri(int resultCode, Intent data, int MaxDimentions, @Nullable Exception error, boolean preserverAspectRatio) {
        //error=null;
        Uri resultUri = null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            //resultUri = result.getUri();
            try {
                resultUri = BitmapUtility.getPicUri(result.getUri(), result.getUri(), MaxDimentions, preserverAspectRatio);
            } catch (IOException e) {
                error = result.getError();
                e.printStackTrace();

            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
            Log.e("ErrorCameraUtilCrop",error.toString());
        }
        return resultUri;
    }

    public static boolean finaliseImageCropUri(int resultCode, Intent data, int MaxDimentions, Uri destinationUri, @Nullable Exception error, Boolean toGrayScale) throws IOException {
        error = null;
        Boolean status = false;
        Uri resultUri = null;
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            resultUri = result.getUri();
            destinationUri = BitmapUtility.getPicUri(resultUri, destinationUri, new Size(0, 300));
            (new File(resultUri.getPath())).deleteOnExit();
            status = true;
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            error = result.getError();
        }
        return status;
    }

   /* public static void toGreyScale(Context context, Uri imageUri, int MaxDimention) {
        Glide.with(context)
                .load(imageUri)
                .asBitmap()
                .transform(new GrayscaleTransformation(context))
                .into(new FileTarget(imageUri.getPath(), MaxDimention, MaxDimention));
    }*/

    public static File moveCachedImage2Storage(Context context, File cachedImage, boolean deleteSource) throws IOException {
        File croppedImage = CameraUtils.createImageFile(context);
        Utils.copyFile(cachedImage, croppedImage);
        if (deleteSource) cachedImage.delete();
        return croppedImage;
    }
}
