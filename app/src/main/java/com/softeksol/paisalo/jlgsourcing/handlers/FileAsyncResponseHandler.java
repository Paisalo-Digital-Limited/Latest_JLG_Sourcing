package com.softeksol.paisalo.jlgsourcing.handlers;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;

import java.io.File;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sachindra on 11/25/2016.
 */
public abstract class FileAsyncResponseHandler extends FileAsyncHttpResponseHandler {
    private ProgressDialog progressDialog;
    private Context context;

    protected FileAsyncResponseHandler(Context context, String title, String msg) {
        super(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    protected FileAsyncResponseHandler(Context context, int resIdtitle, int resIdMessage) {
        super(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(resIdtitle);
        progressDialog.setMessage(context.getResources().getString(resIdMessage));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialog.show();
    }

    @Override
    public void onFinish() {
        progressDialog.dismiss();
        super.onFinish();
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        super.onProgress(bytesWritten, totalSize);
        progressDialog.setMax((int) totalSize);
        progressDialog.setProgress((int) bytesWritten);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
        WebOperations.handleHttpFailure(context, statusCode, headers, Utils.getBytesFromFile(file), throwable);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, File file) {
    }
}
