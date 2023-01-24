package com.softeksol.paisalo.jlgsourcing.activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterRecViewPreclosureCases;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.dto.PreclosureCaseDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.PrematureCaseEntryDTO;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ActivityPreClosure extends AppCompatActivity implements AdapterRecViewPreclosureCases.OnPreclosureCaseClickListener {
    List<PreclosureCaseDTO> preclosureCaseList;
    AdapterRecViewPreclosureCases adapterRecViewPreclosureCases;
    private Manager manager;
    private PreclosureCaseDTO preclosureCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_closure);
        manager = (Manager) getIntent().getSerializableExtra(Global.MANAGER_TAG);
        preclosureCaseList = new ArrayList<>();
        adapterRecViewPreclosureCases = new AdapterRecViewPreclosureCases(preclosureCaseList, this);
        RecyclerView rvPreclosureCase = findViewById(R.id.rv_pre_closure);
        rvPreclosureCase.setAdapter(adapterRecViewPreclosureCases);
        setTitle(R.string.pre_close_case);
    }

    @Override
    protected void onResume() {
        refreshData();
        super.onResume();
    }

    public void refreshData() {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Case Pre-Closure", "Fetching Pre-Closure Cases") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Type listType = new TypeToken<List<PreclosureCaseDTO>>() {
                    }.getType();
                    preclosureCaseList.clear();
                    String jsonString = (new String(responseBody));
                    //Log.d("Preclosure Casess", jsonString);
                    preclosureCaseList = WebOperations.convertToObjectArray(jsonString, listType);
                    adapterRecViewPreclosureCases.updateList(preclosureCaseList);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("DueData Error", new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.add("gdate", DateUtils.getFormatedDate(new Date(), "yyyy-MM-dd"));
        params.add("CityCode", manager.AreaCd);

        //String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : BuildConfig.DATABASE_NAME;
        String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : IglPreferences.getPrefString(ActivityPreClosure.this, SEILIGL.DATABASE_NAME, "")+"";
        (new WebOperations()).getEntity(getApplicationContext(), queryDB, "casepremature", "posfopreclosure", params, asyncResponseHandler);
    }

    public void getPreclosureEntries(final PreclosureCaseDTO preclosureCase) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Case Pre-Closure", "Fetching Pre-Closure Details") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Type listType = new TypeToken<List<PrematureCaseEntryDTO>>() {
                    }.getType();
                    //preclosureCaseList.clear();
                    String jsonString = (new String(responseBody));
                    //Log.d("Preclosure Data", jsonString);
                    List<PrematureCaseEntryDTO> preClosureEntries = WebOperations.convertToObjectArray(jsonString, listType);
                    //ArrayList<PrematureCaseEntryDTO> preClosureEntries =
                    showPreclosureEntries(preclosureCase, (ArrayList) preClosureEntries);
                    //adapterRecViewPreclosureCases.updateList(preclosureCaseList);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("DueData Error", new String(responseBody));
            }
        };


        RequestParams params = new RequestParams();
        params.add("SmCode", preclosureCase.getCaseCode());//getPreCloseData(string SmCode)

        //String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : BuildConfig.DATABASE_NAME;
        String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : IglPreferences.getPrefString(ActivityPreClosure.this, SEILIGL.DATABASE_NAME, "")+"";
        //Log.d("QueryDB", queryDB);
        (new WebOperations()).getEntity(this, "casepremature", "getPreCloseData", params, asyncResponseHandler);
    }

    @Override
    public void onPreclosureCaseInteraction(PreclosureCaseDTO preclosureCase) {
        getPreclosureEntries(preclosureCase);
    }

    private void showPreclosureEntries(final PreclosureCaseDTO preclosureCase, final ArrayList<PrematureCaseEntryDTO> preClosureEntries) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_dialog_pre_closure, null);
        dialogBuilder.setView(dialogView);
        TableLayout mTableLayout = dialogView.findViewById(R.id.table_layout_precolsure_entries);

        final double amt = loadData1(mTableLayout, preClosureEntries);

        TextInputEditText tietCollAmount = dialogView.findViewById(R.id.tiet_pre_close_coll_amt);

        ((TextView) dialogView.findViewById(R.id.tv_pre_closure_title)).setText(preclosureCase.getCustName());
        ((TextView) dialogView.findViewById(R.id.tv_pre_closure_case_code)).setText(preclosureCase.getCaseCode());
        dialogBuilder
                .setMessage(R.string.pre_close_case)
                .setPositiveButton(R.string.pre_close_case, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postPreClosure(preclosureCase.getCaseCode());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, null);

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        tietCollAmount.addTextChangedListener(new MyTextWatcher(tietCollAmount) {
            @Override
            public void validate(EditText editText, String text) {
                if (text.length() > 0) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(Double.parseDouble(text) == amt);
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });
    }

    public double loadData1(TableLayout mTableLayout, final ArrayList<PrematureCaseEntryDTO> data) {
        double collectionAmount = 0;
        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);*/
        //mTableLayout.setLayoutParams(lp);
        mTableLayout.setStretchAllColumns(true);
        int leftRowMargin = 0;
        int topRowMargin = 0;
        int rightRowMargin = 0;
        int bottomRowMargin = 0;
        int textSize = 0;
        textSize = (int) getResources().getDimension(R.dimen.margin_12dp);
        DecimalFormat decimalFormat = new DecimalFormat("0");
        TextView textSpacer = null;
        for (int index = -1; index < data.size(); index++) {
            //Log.d("Int",index+"");
            PrematureCaseEntryDTO row = null;
            if (index == -1) {
                textSpacer = new TextView(this);
                textSpacer.setText("");
            } else {
                row = data.get(index);
                if (row.getAhead().trim().equals("CASHFILD")) {
                    collectionAmount = row.getFcBrkupDr();
                }
                String desc = row.getFcBrkupDesc();
                if (desc.equals("")) {
                    continue;
                }
            }

            // data columns
            final TextView tvID = new TextView(this);
            tvID.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvID.setGravity(Gravity.LEFT);
            tvID.setPadding(5, 15, 0, 15);
            if (index == -1) {
                tvID.setText("SN.");
                tvID.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tvID.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            } else {
                tvID.setBackgroundColor(Color.parseColor("#f8f8f8"));
                tvID.setText(String.valueOf(row.getId()));
                tvID.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            final TextView tvAcHead = new TextView(this);
            if (index == -1) {
                tvAcHead.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAcHead.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            } else {
                tvAcHead.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tvAcHead.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            tvAcHead.setGravity(Gravity.LEFT);
            tvAcHead.setPadding(5, 15, 0, 15);
            if (index == -1) {
                tvAcHead.setText("Account Head");
                tvAcHead.setBackgroundColor(Color.parseColor("#f7f7f7"));
            } else {
                tvAcHead.setBackgroundColor(Color.parseColor("#ffffff"));
                tvAcHead.setTextColor(Color.parseColor("#000000"));
                tvAcHead.setText(row.getFcBrkupDesc());
            }
            final TextView tvDr = new TextView(this);
            tvDr.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tvDr.setPadding(5, 15, 0, 15);
            tvDr.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            tvDr.setGravity(Gravity.RIGHT);
            if (index == -1) {
                tvDr.setText("Debit");
                tvDr.setBackgroundColor(Color.parseColor("#f0f0f0"));
            } else {
                tvDr.setBackgroundColor(Color.parseColor("#f8f8f8"));
                tvDr.setTextColor(Color.parseColor("#000000"));
                tvDr.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                tvDr.setText(decimalFormat.format(row.getFcBrkupDr()));
            }

            final TextView tvCr = new TextView(this);
            tvCr.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvCr.setPadding(5, 15, 5, 15);
            tvCr.setGravity(Gravity.RIGHT);
            if (index == -1) {
                tvCr.setText("Credit");
                tvCr.setBackgroundColor(Color.parseColor("#f7f7f7"));
                tvCr.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            } else {
                tvCr.setBackgroundColor(Color.parseColor("#ffffff"));
                tvCr.setTextColor(Color.parseColor("#000000"));
                tvCr.setText(decimalFormat.format(row.getFcBrkupCr()));
                tvCr.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }

            // add table row
            final TableRow tr = new TableRow(this);
            tr.setId(index + 1);
            TableLayout.LayoutParams trParams = new
                    TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin,
                    bottomRowMargin);
            tr.setPadding(0, 0, 0, 0);
            tr.setLayoutParams(trParams);
            tr.addView(tvID);
            tr.addView(tvAcHead);
            tr.addView(tvDr);
            tr.addView(tvCr);
            mTableLayout.addView(tr, trParams);
            if (index > -1) {
                // add separator row
                final TableRow trSep = new TableRow(this);
                TableLayout.LayoutParams trParamsSep = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParamsSep.setMargins(leftRowMargin, topRowMargin,
                        rightRowMargin, bottomRowMargin);
                trSep.setLayoutParams(trParamsSep);
                TextView tvSep = new TextView(this);
                TableRow.LayoutParams tvSepLay = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tvSepLay.span = 4;
                tvSep.setLayoutParams(tvSepLay);
                tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                tvSep.setHeight(1);
                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }
        }
        return collectionAmount;
    }

    public void postPreClosure(final String CaseCode) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Case Pre-Closure", "Finalising Pre-Closure ...") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Utils.alert(ActivityPreClosure.this, "Pre-Closure Successful");
                    refreshData();
                }
            }
        };


        RequestParams params = new RequestParams();
        params.add("CaseCode", CaseCode);//getPreCloseData(string SmCode)

        String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : IglPreferences.getPrefString(ActivityPreClosure.this, SEILIGL.DATABASE_NAME, "")+"";
        //Log.d("QueryDB", queryDB);
        (new WebOperations()).postEntity(this, "casepremature", "PostCasePreclosure", params, asyncResponseHandler);
    }

}
