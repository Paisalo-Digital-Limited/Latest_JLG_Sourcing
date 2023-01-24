package com.softeksol.paisalo.jlgsourcing.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterExpListCollectionDetail;
import com.softeksol.paisalo.jlgsourcing.entities.dto.CollectionData;
import com.softeksol.paisalo.jlgsourcing.entities.dto.CollectionGroup;
import com.softeksol.paisalo.jlgsourcing.handlers.AsyncResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;


public class ActivityCollectionDetails extends AppCompatActivity implements View.OnClickListener {
    private Calendar myCalendar;
    private TextInputEditText tietSearchDate;
    private AppCompatImageButton imageButton;
    private AdapterExpListCollectionDetail adapterExpListCollectionDetail;
    private TextView tvTotal;
    private DatePickerDialog.OnDateSetListener dateSetListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_details);
        tietSearchDate = (TextInputEditText) findViewById(R.id.tietSearchDate);
        tietSearchDate.setOnClickListener(this);
        tietSearchDate.setFocusable(false);

        imageButton = (AppCompatImageButton) findViewById(R.id.imgBtnSearch);
        imageButton.setOnClickListener(this);
        imageButton.setEnabled(false);

        tvTotal = (TextView) findViewById(R.id.tv_collection_total);
        tvTotal.setText("0");

        adapterExpListCollectionDetail = new AdapterExpListCollectionDetail(this, new ArrayList<CollectionGroup>());
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expand_lv_collection_detail);
        expandableListView.setAdapter(adapterExpListCollectionDetail);

        myCalendar = Calendar.getInstance(TimeZone.getDefault());
        myCalendar.setTime(new Date());
        dateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(year, monthOfYear, dayOfMonth);
                tietSearchDate.setText(DateUtils.getFormatedDate(myCalendar.getTime(), "dd-MMM-yyyy"));
                clearData();
                imageButton.setEnabled(true);
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tietSearchDate:
                new DatePickerDialog(this, dateSetListner,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.imgBtnSearch:
                fetchCollectionDetail();
                break;
        }
    }

    private void fetchCollectionDetail() {
        clearData();
        AsyncResponseHandler dataAsyncResponseHandler = new AsyncResponseHandler(this, "Loan Financing\nFetching Collection Data", "For the Date " + tietSearchDate.getText().toString()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                Type listType = new TypeToken<List<CollectionData>>() {
                }.getType();
                List<CollectionData> collectionData = WebOperations.convertToObjectArray(jsonString, listType);
                //Log.d("Response Data", collectionData.toString());
                if (collectionData != null) {
                    List<CollectionGroup> groupList = CollectionData.getCollectionGroups(collectionData);
                    adapterExpListCollectionDetail.updaateData(groupList);
                    tvTotal.setText(CollectionGroup.getGroupsTotal(groupList) + "");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        };

        RequestParams params = new RequestParams();
        params.add("CollDate", DateUtils.getFormatedDate(myCalendar.getTime(), "yyyy-MM-dd"));
        Log.d("checkdate",DateUtils.getFormatedDate(myCalendar.getTime(), "yyyy-MM-dd"));
        params.add("CSOCode", IglPreferences.getPrefString(this, SEILIGL.USER_ID, ""));
        Log.d("userIdcheck",IglPreferences.getPrefString(this, SEILIGL.USER_ID, ""));

        (new WebOperations()).getEntity(this, "instcollection", "getCSOCollection", params, dataAsyncResponseHandler);
    }

    private void clearData() {
        adapterExpListCollectionDetail.clearData();
        tvTotal.setText("0");
    }
}
