package com.softeksol.paisalo.jlgsourcing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListManager;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterOperation;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.dto.OperationItem;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ActivityOperationSelect extends AppCompatActivity {
    private AdapterListManager adapterListManager;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_select);
        drawerLayout = findViewById(R.id.my_drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close){
            public void onDrawerClosed(View view)
            {
                Log.d("drawerToggle", "Drawer closed");
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); //Creates call to onPrepareOptionsMenu()
            }

            /*
             * Called when a drawer has settled in a completely open state
             */
            public void onDrawerOpened(View drawerView)
            {
                Log.d("drawerToggle", "Drawer opened");
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);
        List<OperationItem> operationItems = new ArrayList<>();
        if (IglPreferences.getPrefString(this, SEILIGL.ALLOW_COLLECTION, "N").contains("S")) {
            operationItems.add(new OperationItem(1, "KYC", R.color.colorMenuKyc, "POSDB", "Getmappedfo"));
            operationItems.add(new OperationItem(2, "Application Form", R.color.colorMenuApplicationForm, "POSDB", "Getmappedfo"));
        }
        if (IglPreferences.getPrefString(this, SEILIGL.ALLOW_COLLECTION, "N").contains("C")) {
            operationItems.add(new OperationItem(3, "Collection", R.color.colorMenuCollection, "POSDB", "Getmappedfoforcoll"));
        }
        if (IglPreferences.getPrefString(this, SEILIGL.ALLOW_COLLECTION, "N").contains("D")) {
            operationItems.add(new OperationItem(4, "Deposit", R.color.colorMenuDeposit, "POSDB", "Getmappedfofordepo"));
        }
        if (IglPreferences.getPrefString(this, SEILIGL.ALLOW_COLLECTION, "N").contains("P")) {
            operationItems.add(new OperationItem(5, "Premature", R.color.colorMenuPremature, "casepremature", "FOForPreclosure"));
        }

        GridView lv = (GridView) findViewById(R.id.lvcOpSelect);
        lv.setAdapter(new AdapterOperation(ActivityOperationSelect.this, operationItems));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OperationItem operationItem = (OperationItem) parent.getAdapter().getItem(position);
                updateManagers(operationItem);
            }
        });
    }


    private void updateManagers(final OperationItem operationItem) {
        //if (WebOperations.checkSession(this)) {
        DataAsyncResponseHandler dataAsyncHttpResponseHandler = new DataAsyncResponseHandler(this, "Loan Financing", "Downloading Manager's List") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                Log.d("ManageDataLatest", jsonString);
                Type listType = new TypeToken<List<Manager>>() {
                }.getType();

                List<Manager> managers = WebOperations.convertToObjectArray(jsonString, listType);
                Log.d("CheckSizeList",managers.size()+"");

                SQLite.delete().from(Manager.class).query();
                for (Manager manager : managers) {
                    manager.insert();
                }

                Intent intent = null;
                switch (operationItem.getId()) {
                    case 1:
                        intent = new Intent(ActivityOperationSelect.this, ActivityManagerSelect.class);
                        break;
                    case 2:
                        intent = new Intent(ActivityOperationSelect.this, ActivityManagerSelect.class);
                        break;
                    case 3:
                        intent = new Intent(ActivityOperationSelect.this, ActivityManagerSelect.class);
                        break;
                    case 4:
                        intent = new Intent(ActivityOperationSelect.this, ActivityManagerSelect.class);
                        break;
                    case 5:
                        intent = new Intent(ActivityOperationSelect.this, ActivityManagerSelect.class);
                        break;
                }

                assert intent != null;
                intent.putExtra(Global.OPTION_ITEM, operationItem);
                intent.putExtra("Title", operationItem.getOprationName());
                startActivity(intent);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("failure", String.valueOf(statusCode) + "\n" + (new String(responseBody, StandardCharsets.UTF_8)));
            }
        };
        RequestParams params = new RequestParams();
        params.add("UserId", IglPreferences.getPrefString(this, SEILIGL.USER_ID, ""));
        params.add("IMEINO", IglPreferences.getPrefString(this, SEILIGL.DEVICE_IMEI, "0"));
        //params.add("Operation", operationItem.getUrlEndpoint());
        (new WebOperations()).getEntity(this, operationItem.getUrlController(), operationItem.getUrlEndpoint(), params, dataAsyncHttpResponseHandler);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item);
    }
}
