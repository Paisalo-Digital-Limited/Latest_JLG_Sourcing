package com.softeksol.paisalo.jlgsourcing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterCollectionFragmentPager;
import com.softeksol.paisalo.jlgsourcing.entities.DueData;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.PosInstRcv;
import com.softeksol.paisalo.jlgsourcing.fragments.AbsCollectionFragment;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentCollection;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentCollectionSettlement;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ActivityCollection extends AppCompatActivity {

    private AdapterCollectionFragmentPager fragmentPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<DueData> dueDataList = new ArrayList<>();
    private ArrayList<PosInstRcv> settlementDataList = new ArrayList<>();
    private Manager manager;
    private DueData dueData;
    private ArrayList<AbsCollectionFragment> absFragments;
    private AbsCollectionFragment fragSettlement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewpager);
        manager = (Manager) getIntent().getSerializableExtra(Global.MANAGER_TAG);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        absFragments = new ArrayList<>();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        fragmentPagerAdapter = new AdapterCollectionFragmentPager(getSupportFragmentManager(), absFragments);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        refreshData(null);
    }

    public void refreshData(final AbsCollectionFragment fragmentCollection) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Collection", "Fetching Dues Data") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Type listType = new TypeToken<List<DueData>>() {
                    }.getType();
                    dueDataList.clear();
                    String jsonString = (new String(responseBody))
                            .replace("\"[", "[")
                            .replace("]\"", "]")
                            .replace("\\\"", "\"");
                    Log.d("DueData_jsonString", jsonString);
                    List<DueData> dueData = WebOperations.convertToObjectArray(jsonString, listType);
                    Log.d("DueData_LIST", dueData.toString());
                    dueDataList.addAll(getDueDataByDbName(dueData, manager.FOCode, manager.Creator));
                    Log.d("checking",dueDataList.size()+"");
                    populateFragments();
                    refreshSettlement();
                    if (fragmentCollection != null)
                        fragmentCollection.refreshData();
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
        String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : IglPreferences.getPrefString(ActivityCollection.this, SEILIGL.DATABASE_NAME, "")+"";
        (new WebOperations()).getEntity(this, queryDB, "instcollection", "getdueinstallments", params, asyncResponseHandler);
    }

    public void refreshSettlement() {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Collection", "Fetching Settlement Data") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Type listType = new TypeToken<List<PosInstRcv>>() {
                    }.getType();
                    settlementDataList.clear();
                    String jsonString = (new String(responseBody));

                    List<PosInstRcv> dueData = WebOperations.convertToObjectArray(jsonString, listType);
                    settlementDataList.addAll(getSettlementDataByDbName(dueData, manager.FOCode, manager.Creator));
                    if (settlementDataList.size() > 0) {

                        if (fragSettlement == null) {
                            Log.d("checking","yha tak 2");
                            fragSettlement = FragmentCollectionSettlement.newInstance("POSDB", "Settlement");
                            absFragments.add(fragSettlement);
                            fragmentPagerAdapter.addFragment(fragSettlement, fragmentPagerAdapter.getCount());
                            Log.d("checking",fragmentPagerAdapter.getCount()+"");
                            fragmentPagerAdapter.notifyDataSetChanged();
                        }
                        fragSettlement.refreshData();
                    } else {
                        Log.d("checking","yha tak 3");
                        if (absFragments != null) {
                            Log.d("checking","yha tak 4");
                            absFragments.remove(absFragments.size() - 1);
                            fragmentPagerAdapter.removeFragment(fragmentPagerAdapter.getCount() - 1);
                            Log.d("checking",fragmentPagerAdapter.getCount() - 1+"");
                            fragmentPagerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.e("SettlementErrorLatest", Arrays.toString(responseBody) +"");
                Log.e("ErrorSettlementError", error+"");
                //Log.d("Settlement Error",new String(responseBody));
            }
        };

        RequestParams params = new RequestParams();
        params.add("Creator", manager.Creator);
        params.add("FoCode", manager.FOCode);
        //String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : BuildConfig.DATABASE_NAME;
        String queryDB = BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing" ? "POSDATA" : IglPreferences.getPrefString(ActivityCollection.this, SEILIGL.DATABASE_NAME, "")+"";
        (new WebOperations()).getEntity(this, queryDB, "instcollection", "getfmsettlementdata", params, asyncResponseHandler);
    }

    public ArrayList<PosInstRcv> getSettlementDataByDbName(List<PosInstRcv> instRcvs, String foCode, String creator) {
        ArrayList<PosInstRcv> instRcvArrayList = new ArrayList<>();
        for (PosInstRcv dueData : instRcvs) {
            if (dueData.getFoCode().equals(foCode) && dueData.getCreator().equals(creator))
                instRcvArrayList.add(dueData);
        }
        Collections.sort(instRcvArrayList, PosInstRcv.InstRcvName);
        return instRcvArrayList;
    }

    private void populateFragments() {
        absFragments = getFragments(getDatabases());
        refreshFragments();
    }

    private void refreshFragments() {
        fragmentPagerAdapter.clearFragments();
        fragmentPagerAdapter.setFragments(absFragments);
        fragmentPagerAdapter.notifyDataSetChanged();

    }

    private AbsCollectionFragment getFragmentByDbName(String dbName) {
        AbsCollectionFragment fragmentRet = null;
        for (AbsCollectionFragment fragment : absFragments) {
            if (fragment.getName().equals(dbName)) {
                fragmentRet = fragment;
                break;
            }
        }
        return fragmentRet;
    }

    private ArrayList<AbsCollectionFragment> getFragments(Map<String, String> databases) {
        ArrayList<AbsCollectionFragment> fragmentArrayList = new ArrayList<>();
        for (Map.Entry<String, String> dbEntry : databases.entrySet()) {
            fragmentArrayList.add(FragmentCollection.newInstance(dbEntry.getKey(), dbEntry.getValue()));
        }
        return fragmentArrayList;
    }

    private Map<String, String> getDatabases() {
        Map<String, String> dbs = new HashMap<>();
        for (DueData dueData : dueDataList) {
            dbs.put(dueData.getDb(), dueData.getDbName());
        }
        return dbs;
    }

    public List<DueData> getDueDataByDbName(String dbName) {
        List<DueData> dueDataSubList = new ArrayList<>();
        for (DueData dueData : dueDataList) {
            if (dueData.getDb().equals(dbName))
                dueDataSubList.add(dueData);
        }
        return dueDataSubList;
    }

    public List<PosInstRcv> getRecSettlementData() {
        return new ArrayList<>(settlementDataList);
    }

    public ArrayList<DueData> getDueDataByDbName(List<DueData> dueDataList, String foCode, String creator) {
        ArrayList<DueData> dueDataSubList = new ArrayList<>();
        for (DueData dueData : dueDataList) {
            if (dueData.getFoCode().equals(foCode) && dueData.getCreator().equals(creator))
                dueDataSubList.add(dueData);
        }
        Collections.sort(dueDataSubList, DueData.DueDataNachName);
        return dueDataSubList;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Global.MANAGER_TAG, manager);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        manager = (Manager) savedInstanceState.getSerializable(Global.MANAGER_TAG);
    }

    /*public void performEKyc(DueData dueData) {
        this.dueData = dueData;
        String AuthType = BuildConfig.AUTH_TYPE[0];
        Intent i = Utils.getKycIntent(this, AuthType, KycServicesActivity.class);
        i.putExtra("AADHAR", dueData.getAadhar());
        startActivityForResult(i, Global.EKYC_REQUEST_CODE);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.EKYC_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
               processKycresponse(data.getStringExtra("EKYCDATA"));
                //Toast.makeText(getBaseContext(), "eKYC successful", Toast.LENGTH_SHORT).show();
            } else {
                Utils.alert(this, "There was an error performing eKyc");
            }
        }
    }

    private void processKycresponse(String kycStatus) {
        JSONObject jsonObj = null;
        Log.d("kyc", kycStatus);
        try {
            jsonObj = new JSONObject(kycStatus);
            // Getting JSON Array node
            JSONArray Authentication = jsonObj.getJSONArray("KYCRES");

            // looping through All Contacts
            for (int i = 0; i < Authentication.length(); i++) {
                JSONObject c = Authentication.getJSONObject(i);

                String status = c.getString("status");
                if (status.equals("1")) {
                    String uuid = c.getString("uuid");
                    updateUUID(uuid);

                } else {
                    Utils.alert(this, c.getString("errcode") + "\n" + c.getString("errmsg"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUUID(String uuid) {
        String msg = "Updating UUID";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        //Log.d("eKYC", msg);

        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Collection", msg) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Utils.alert(ActivityCollection.this, "UUID updated Successfully");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utils.alert(ActivityCollection.this, "UUID cannot be updated");
            }
        };

        JSONObject pars = new JSONObject();
        // TODO : FiCode need to be updated
        try {
            pars.put("SmCode", String.valueOf(dueData.getCaseCode()));
            pars.put("Creator", dueData.getCreator());
            pars.put("KYCUUID", uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utils.alert(this, pars.toString());
        (new WebOperations()).postEntity(this, "POSFI", "UpdateUuid", pars.toString(), asyncResponseHandler);
    }
}
