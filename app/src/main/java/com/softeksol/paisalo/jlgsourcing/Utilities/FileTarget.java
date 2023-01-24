package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sachindra on 1/23/2017.
 */
public class FileTarget extends SimpleTarget<Bitmap> {
    String fileName;
    Bitmap.CompressFormat format;
    int quality;

    public FileTarget(String fileName, int width, int height) {
        this(fileName, width, height, Bitmap.CompressFormat.JPEG, 70);
    }

    public FileTarget(String fileName, int width, int height, Bitmap.CompressFormat format, int quality) {
        super(width, height);
        this.fileName = fileName;
        this.format = format;
        this.quality = quality;
    }

    /*public FileTarget(String fileName , int dimention, int quality) {
        this.fileName = fileName;
        this.format = Bitmap.CompressFormat.JPEG;
        this.quality = quality;
    }*/
    /*public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            bitmap.compress(format, quality, out);
            out.flush();
            out.close();
            onFileSaved();
        } catch (IOException e) {
            e.printStackTrace();
            onSaveException(e);
        }
    }*/

    public void onFileSaved() {
        // do nothing, should be overriden (optional)
    }

    public void onSaveException(Exception e) {
        // do nothing, should be overriden (optional)
    }

    @Override
    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            bitmap.compress(format, quality, out);
            out.flush();
            out.close();
            onFileSaved();
        } catch (IOException e) {
            e.printStackTrace();
            onSaveException(e);
        }
    }
}