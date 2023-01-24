package com.softeksol.paisalo.jlgsourcing.entities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by sachindra on 2016-10-04.
 */
@ModelContainer
@Table(database = DbIGL.class)
public class RangeCategory extends BaseModel {

    @Column
    public String cat_key;
    @Column
    public String GroupDescriptionEn;
    @Column
    public String GroupDescriptionHi;
    @Column
    public String DescriptionEn;
    @Column
    public String DescriptionHi;
    @Column
    public long SortOrder;
    @Column
    public String RangeCode;

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    public RangeCategory() {
        if (this.RangeCode == null) this.RangeCode = this.DescriptionEn;
    }

    public RangeCategory(String Description, String GroupDescription) {
        this.RangeCode = Description;
        this.DescriptionEn = Description;
        this.DescriptionHi = Description;
        if (GroupDescription != null) {
            this.GroupDescriptionEn = GroupDescription;
            this.GroupDescriptionHi = GroupDescription;
        }
    }

    public static void updateOptions(final Context context) {
        //DataAsyncHttpResponseHandler dataAsyncHttpResponseHandler = new DataAsyncHttpResponseHandler() {
        DataAsyncResponseHandler dataAsyncHttpResponseHandler = new DataAsyncResponseHandler(context, "Loan Financing", "Updating Master Data") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                Log.d("RangeCategoryData", jsonString);
                Type listType = new TypeToken<List<RangeCategory>>() {
                }.getType();
                List<RangeCategory> rangeCategoryList = WebOperations.convertToObjectArray(jsonString, listType);

                SQLite.delete().from(RangeCategory.class).query();
                for (RangeCategory rangeCategory : rangeCategoryList) {
                    rangeCategory.insert();
                }
                Toast.makeText(context, "Options Master Updated " + String.valueOf(rangeCategoryList.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    Log.d("failure", String.valueOf(statusCode) + "\n" + (new String(responseBody, "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        (new WebOperations()).getEntity(context, "POSDB", "GetRangeCategories", (String) null, dataAsyncHttpResponseHandler);
    }

    @Override
    public String toString() {
        return "RangeCategory{" +
                "cat_key='" + cat_key + '\'' +
                ", GroupDescriptionEn='" + GroupDescriptionEn + '\'' +
                ", GroupDescriptionHi='" + GroupDescriptionHi + '\'' +
                ", DescriptionEn='" + DescriptionEn + '\'' +
                ", DescriptionHi='" + DescriptionHi + '\'' +
                ", SortOrder=" + SortOrder +
                ", RangeCode='" + RangeCode + '\'' +
                ", id=" + id +
                '}';
    }

    public static List<RangeCategory> getRangesByCatKey(String categoryKey) {
        return SQLite.select()
                .from(RangeCategory.class)
                .where(RangeCategory_Table.cat_key.eq(categoryKey))
                .queryList();
    }

    public static List<RangeCategory> getRangesByCatKey(String categoryKey, String sortField, boolean ascending) {
        return SQLite.select()
                .from(RangeCategory.class)
                .where(RangeCategory_Table.cat_key.eq(categoryKey))
                .orderBy(RangeCategory_Table.getProperty(sortField), ascending)
                .queryList();
    }
}
