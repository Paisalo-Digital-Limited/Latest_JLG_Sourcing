package com.softeksol.paisalo.jlgsourcing.handlers;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.softeksol.paisalo.jlgsourcing.WebOperations;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sachindra on 11/25/2016.
 */
public abstract class AsyncResponseHandler extends AsyncHttpResponseHandler {
    private ProgressDialog progressDialog;
    private Context context;


    public AsyncResponseHandler(Context context, String title, String msg) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
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
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        WebOperations.handleHttpFailure(context, statusCode, headers, responseBody, error);
    }
}
