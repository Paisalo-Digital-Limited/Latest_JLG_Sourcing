package com.softeksol.paisalo.jlgsourcing.handlers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.softeksol.paisalo.jlgsourcing.WebOperations;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sachindra on 11/25/2016.
 */
public abstract class DataAsyncResponseHandler extends AsyncHttpResponseHandler {
    private ProgressDialog progressDialog = null;
    private ProgressBar progressBar = null;
    private Context context;

    public DataAsyncResponseHandler(Context context, String title, String msg) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public DataAsyncResponseHandler(Context context, int resIdtitle, int resIdMessage) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(resIdtitle);
        progressDialog.setMessage(context.getResources().getString(resIdMessage));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public DataAsyncResponseHandler(Context context, String title) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public DataAsyncResponseHandler(Context context, int resIdtitle) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(resIdtitle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public DataAsyncResponseHandler(ProgressDialog progressDialog) {
        this.context = progressDialog.getContext();
        this.progressDialog = progressDialog;
    }

    public DataAsyncResponseHandler(ProgressBar progressBar1) {
        this.context = progressBar1.getContext();
        this.progressBar = progressBar1;
    }

    public DataAsyncResponseHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (progressDialog != null) progressDialog.show();
        if (progressBar != null) {
            progressBar.setIndeterminate(true);
        }
    }

    @Override
    public void onFinish() {
        //progressDialog.hide();
        if (progressDialog != null) progressDialog.dismiss();
        super.onFinish();
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        super.onProgress(bytesWritten, totalSize);
        if (progressDialog != null) {
            if (progressDialog.isIndeterminate()) progressDialog.setIndeterminate(false);
            progressDialog.setMax((int) totalSize);
            progressDialog.setProgress((int) bytesWritten);
        }
        if (progressBar != null) {
            if (progressBar.isIndeterminate()) progressBar.setIndeterminate(false);
            progressBar.setMax((int) totalSize);
            progressBar.setProgress((int) bytesWritten);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        WebOperations.handleHttpFailure(context, statusCode, headers, responseBody, error);
    }

}
