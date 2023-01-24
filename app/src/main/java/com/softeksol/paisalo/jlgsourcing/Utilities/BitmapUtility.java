package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import com.journeyapps.barcodescanner.Size;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by sachindra on 2015-12-29.
 */
public class BitmapUtility {
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    public static byte[] getBytes(Uri fileUri) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        return getBytes(getImage(fileUri));
    }

    public static byte[] getImageBytes(Bitmap bm) {
        int bytes = bm.getByteCount();
        //or we can calculate bytes this way. Use a different value than 4 if you don't use 32bit images.
        //int bytes = b.getWidth()*b.getHeight()*4;

        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        bm.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
        return buffer.array();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        if (image == null) return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Bitmap getImage(Uri fileUri) {
        return BitmapFactory.decodeFile(fileUri.getPath());
    }


    public static <T, S> T getResizedImage(S image, int targetW, int targetH, boolean preserveAspectRatio, Boolean filter) {
        Bitmap bm;
        T target = null;
        if (Bitmap.class == image.getClass()) {
            bm = getResizedBitmap((Bitmap) image, targetW, targetH, preserveAspectRatio, filter);
        } else if (Uri.class == image.getClass()) {
            bm = getResizedBitmap(getImage((Uri) image), targetW, targetH, preserveAspectRatio, filter);
        } else {
            bm = getResizedBitmap(getImage((byte[]) image), targetW, targetH, preserveAspectRatio, filter);
        }
        if (Bitmap.class == target.getClass()) {
            target = (T) bm;
        } else {
            target = (T) getBytes(bm);
        }
        return target;
    }

    public static Bitmap getResizedBitmap(byte[] imageData, int targetW, int targetH) {
        return Bitmap.createScaledBitmap(getImage(imageData), targetW, targetH, true);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int targetW, int targetH, boolean preserveAspectRatio, Boolean filter) {
        int finalWidth = targetW;
        int finalHeight = targetH;

        if (preserveAspectRatio) {
            float imageRatio = (float) bm.getHeight() / (float) bm.getWidth();

            if (targetW == 0 && targetH == 0) {
                finalHeight = bm.getHeight();
                finalWidth = bm.getWidth();
            } else if (targetW == 0) {
                finalWidth = (int) ((float) targetH / imageRatio);
            } else if (targetH == 0) {
                finalHeight = (int) ((float) targetW * imageRatio);
            }
        }
        // Resize the bitmap to 150x100 (width x height)
        return Bitmap.createScaledBitmap(bm, finalWidth, finalHeight, filter);
    }


    public static Bitmap cropBitmap(Bitmap bm, int width, int height) {
        //Log.d("CropBitmap", "Width " + width + "    Height" + height);
        return Bitmap.createBitmap(bm, 0, 0, width, height);
    }

    public static byte[] getResizedBytes(Bitmap bm, int maxSize, boolean filter) {
        if (bm == null) return null;
        return getBytes(getResizedBitmap(bm, maxSize, filter));
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int maxSize, boolean filter) {
        int finalWidth = maxSize;
        int finalHeight = maxSize;
        float imageRatio = (float) bm.getHeight() / (float) bm.getWidth();

        if (bm.getHeight() == bm.getWidth()) {
            finalHeight = maxSize;
            finalWidth = maxSize;
        } else if (bm.getHeight() > bm.getWidth()) {
            finalWidth = (int) ((float) maxSize / imageRatio);
        } else {
            finalHeight = (int) ((float) maxSize * imageRatio);
        }
        // Resize the bitmap to 150x100 (width x height)
        return Bitmap.createScaledBitmap(bm, finalWidth, finalHeight, filter);
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    //Converts image to grayscale (NEW)
    public static Bitmap toGrayscale(byte[] mImageBuffer, int mImageWidth, int mImageHeight) {
        byte[] Bits = new byte[mImageBuffer.length * 4];
        for (int i = 0; i < mImageBuffer.length; i++) {
            Bits[i * 4] = Bits[i * 4 + 1] = Bits[i * 4 + 2] = mImageBuffer[i]; // Invert the source bits
            Bits[i * 4 + 3] = -1;// 0xff, that's the alpha.
        }
        Bitmap bmpGrayscale = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ALPHA_8);
        //Bitmap bm contains the fingerprint img
        bmpGrayscale.copyPixelsFromBuffer(ByteBuffer.wrap(Bits));
        return bmpGrayscale;
    }

    /*//Converts image to grayscale (NEW)
    public Bitmap toGrayscale(byte[] mImageBuffer)
    {
        byte[] Bits = new byte[mImageBuffer.length * 4];
        for (int i = 0; i < mImageBuffer.length; i++) {
            Bits[i * 4] = Bits[i * 4 + 1] = Bits[i * 4 + 2] = mImageBuffer[i]; // Invert the source bits
            Bits[i * 4 + 3] = -1;// 0xff, that's the alpha.
        }
        Bitmap bmpGrayscale = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
        //Bitmap bm contains the fingerprint img
        bmpGrayscale.copyPixelsFromBuffer(ByteBuffer.wrap(Bits));
        return bmpGrayscale;
    }*/

    //Converts image to grayscale (NEW)
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int color = bmpOriginal.getPixel(x, y);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;
                int gray = (r + g + b) / 3;
                color = Color.rgb(gray, gray, gray);
                //color = Color.rgb(r/3, g/3, b/3);
                bmpGrayscale.setPixel(x, y, color);
            }
        }
        return bmpGrayscale;
    }

    /*//Converts image to binary (OLD)
    public Bitmap toBinary(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }*/

    public static byte[] getBitmapByteArray(ImageView imageView) {
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
        //Bitmap bitmap = bitmapDrawable .getBitmap();
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //return stream.toByteArray();
        return getBitmapByteArray(bitmapDrawable.getBitmap());
    }

    public static byte[] getBitmapByteArray(Bitmap bitmap) {
        //BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
        //Bitmap bitmap = bitmapDrawable .getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmap(byte[] bitmapArray) {
        /*BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;*/
        if (bitmapArray == null) return null;
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);

    }

    public static Bitmap getPic(Uri mCurrentPhotoPath, Size size) {
        // Get the dimensions of the View
        int targetW = size.width;
        int targetH = size.height;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath.getPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor;// = Math.min(photoW/targetW, photoH/targetH);
        if (targetH > 0 && targetW > 0)
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        else if (targetH > 0)
            scaleFactor = photoH / targetH;
        else
            scaleFactor = photoW / targetW;


        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath.getPath(), bmOptions);
        return bitmap;
    }

    public static Uri getPicUri(Uri sourcePhotoUti, Uri destinationPhotoUri, Size size) throws IOException {
        byte[] bytes = getBitmapByteArray(getPic(sourcePhotoUti, size));
        Utils.writeBytesToFile(bytes, sourcePhotoUti);
        return destinationPhotoUri;
    }

    public static Uri getPicUri(Uri sourcePhotoUti, Uri destinationPhotoUri, int size, boolean preserveAspectRatio) throws IOException {
        //byte[] bytes=getBitmapByteArray(getPic(sourcePhotoUti,size));
        Bitmap bm = getImage(sourcePhotoUti);
        Bitmap bm1 = getResizedBitmap(bm, size, preserveAspectRatio);
        byte[] bytes = getBitmapByteArray(bm1);
        //Log.d("Source", sourcePhotoUti.getPath() + (new File(sourcePhotoUti.getPath())).length());
        Utils.writeBytesToFile(bytes, destinationPhotoUri);
        //Log.d("Destination", destinationPhotoUri.getPath() + (new File(destinationPhotoUri.getPath())).length());
        return destinationPhotoUri;
    }

    public static Bitmap getPic(byte[] mImageByteArray, Size size) {
        // Get the dimensions of the View
        int targetW = size.width;
        int targetH = size.height;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(mImageByteArray, 0, mImageByteArray.length, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;

        // Determine how much to scale down the image
        if (targetH > 0 && targetW > 0)
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        else if (targetH > 0)
            scaleFactor = photoH / targetH;
        else
            scaleFactor = photoW / targetW;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeByteArray(mImageByteArray, 0, mImageByteArray.length, bmOptions);
        return bitmap;
    }

    public static byte[] getGrayScaleBytes(byte[] imageBytes) {
        Bitmap bitmap = getBitmap(imageBytes);
        bitmap = toGrayscale(bitmap);
        return getBitmapByteArray(bitmap);
    }

    public static Bitmap androidGrayScale(final Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static String getStringImage(Bitmap bm) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 90, ba);
        byte[] by = ba.toByteArray();
        String encod = Base64.encodeToString(by, Base64.DEFAULT);
        return encod;
    }

    public static String getStringImage(Bitmap bm, int maxSize) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        getResizedBitmap(bm, maxSize, false).compress(Bitmap.CompressFormat.JPEG, 90, ba);
        byte[] by = ba.toByteArray();
        String encod = Base64.encodeToString(by, Base64.DEFAULT);
        return encod;
    }
}
