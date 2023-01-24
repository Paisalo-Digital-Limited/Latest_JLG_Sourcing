package com.softeksol.paisalo.jlgsourcing.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.AadharUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.Aadhar;
import com.softeksol.paisalo.jlgsourcing.entities.AadharData;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower_Table;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.PendingFi;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory_Table;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.DocumentStoreDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.GuarantorDTO;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentHome;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentKycSubmit;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentLoanAppList;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentLoanAppSubmitList;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentSubmittedApplications;
import com.softeksol.paisalo.jlgsourcing.handlers.AsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ActivityFinancing extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentLoanAppList.OnListFragmentLoanAppInteractionListener,
        FragmentLoanAppSubmitList.OnListFragmentLoanAppSubmitInteractionListener,
        FragmentKycSubmit.OnListFragmentKycScanInteractionListener,
        FragmentSubmittedApplications.OnSubmittedApplicationsFragmentInteractionListener,
        FragmentHome.OnListFragmentHomeInteractionListener,
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    NavigationView navigationView;
    private FloatingActionButton fab;
    private Borrower borrower;
    private List<PendingFi> pendingFis;
    private List<Borrower> borrowers;

    private Manager manager;
    private int screenPosition = 0;
    private Fragment fragment;

    private String AuthType;

    AppCompatSpinner spinnerVillage, spinnerCenter, spinnerGroup;
    TextInputEditText tietAadhar, tietOldCase;
    CheckedTextView chkTvTopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing);

        //manager = WebOperations.convertToObject(getIntent().getStringExtra(Global.MANAGER_TAG), Manager.class);
        manager = (Manager) getIntent().getSerializableExtra(Global.MANAGER_TAG);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //FloatingActionButton
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.getMenu().findItem(R.id.nav_loan_reekyc).setEnabled(!IglPreferences.getPrefString(this, SEILIGL.ALLOW_COLLECTION, "N").contains("M"));
        navigationView.getMenu().findItem(R.id.nav_loan_reekyc).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_loan_home).setVisible(false);
        View header = navigationView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.tvNavHeadName)).setText(manager.FOName);
        ((TextView) header.findViewById(R.id.tvNavHeadDetail)).setText(manager.Creator + " / " + manager.FOCode + " / " + manager.AreaName + " (" + manager.AreaCd + ")");
        showFragment(FragmentHome.newInstance());
        fetchPendingFiList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public Manager getManager() {
        return manager;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        getSupportActionBar().setTitle(getString(R.string.app_name) + " (" + BuildConfig.VERSION_NAME + ")" + " / " + item.getTitle());
        switch (id) {
            case R.id.nav_loan_application:
                fab.setVisibility(View.GONE);
                /*if (manager.GroupClosed.equals("Y")) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                    fab.setImageResource(android.R.drawable.ic_input_add);
                }*/
                displaySelectedScreen(id);
                break;
            case R.id.nav_loan_reekyc:
                fab.setVisibility(View.GONE);
                fab.setImageResource(android.R.drawable.ic_popup_sync);
                displaySelectedScreen(id);
                break;
            case R.id.nav_loan_submit_application:
                fab.setVisibility(View.VISIBLE);
                fab.setImageResource(android.R.drawable.ic_input_add);
                displaySelectedScreen(id);
            case R.id.nav_loan_submitted_applications:
                fab.setVisibility(View.VISIBLE);
                fab.setImageResource(android.R.drawable.ic_popup_sync);
                displaySelectedScreen(id);
            case R.id.nav_loan_submit_kyc:
                fab.setVisibility(View.VISIBLE);
                fab.setImageResource(android.R.drawable.ic_popup_sync);
                displaySelectedScreen(id);
            case R.id.nav_loan_home:
                fab.setVisibility(View.GONE);
                displaySelectedScreen(id);
            default:
                fab.setVisibility(View.GONE);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        fragment = null;
        screenPosition = itemId;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_loan_home:
                fragment = FragmentHome.newInstance();
                break;
            case R.id.nav_loan_application:
                fragment = FragmentLoanAppList.newInstance("");
                break;
            case R.id.nav_loan_submit_application:
                fragment = FragmentLoanAppSubmitList.newInstance();
                break;
            case R.id.nav_loan_submit_kyc:
                fragment = FragmentKycSubmit.newInstance(0);
                break;
            case R.id.nav_loan_submitted_applications:
                fragment = FragmentSubmittedApplications.newInstance();
                break;
            default:
                fragment = null;
                screenPosition = 0;
        }

        showFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void showFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    private void selectAreaGroup() {

        final View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_group, null);
        AppCompatButton select = (AppCompatButton) dialogView.findViewById(R.id.btnNavLeft);
        AppCompatButton cancel = (AppCompatButton) dialogView.findViewById(R.id.btnNavRight);
        spinnerVillage = (AppCompatSpinner) dialogView.findViewById(R.id.cSpDlgGrpVillage);
        spinnerCenter = (AppCompatSpinner) dialogView.findViewById(R.id.cSpDlgGrpCenter);
        spinnerGroup = (AppCompatSpinner) dialogView.findViewById(R.id.cSpDlgGrpGroup);
        spinnerVillage.setOnItemSelectedListener(this);
        spinnerCenter.setOnItemSelectedListener(this);
        spinnerGroup.setOnItemSelectedListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        cancel.setText(R.string.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        select.setText("Select");
        select.setEnabled(false);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        List<RangeCategory> rangeCategories = SQLite.select()
                .from(RangeCategory.class)
                .where(RangeCategory_Table.cat_key.eq("loan_purpose"))
                .orderBy(RangeCategory_Table.SortOrder, true)
                .queryList();
        //Log.d("RangeCategory", rangeCategories.toString());
        spinnerVillage.setAdapter(new AdapterListRange(this, rangeCategories, true));
        ((AdapterListRange) spinnerVillage.getAdapter()).notifyDataSetChanged();

        /*spinnerCenter.setAdapter(new AdapterListState(this,SQLite.select()
                .from(StateData.class)
                .orderBy(StateData_Table.StateName,true)
                .queryList()));*/
        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.cSpDlgGrpVillage:
            case R.id.cSpDlgGrpCenter:
            case R.id.cSpDlgGrpGroup:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                switch (screenPosition) {
                    case R.id.nav_loan_application:

                        Intent intent = new Intent(ActivityFinancing.this, ActivityBorrowerEntry.class);
                        intent.putExtra(Global.MANAGER_TAG, manager);
                        startActivityForResult(intent, Global.MANUAL_FORM_REQUEST_CODE);
                        break;
                }
                break;
        }
    }

    private void newAadharScan() {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
    }

    private void newApplication() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_new_application, null);
        tietAadhar = (TextInputEditText) dialogView.findViewById(R.id.tietNewAppAadhar);
        tietOldCase = (TextInputEditText) dialogView.findViewById(R.id.tietNewAppOldCaseCode);
        tietOldCase.setVisibility(View.GONE);
        AppCompatButton btnKyc = (AppCompatButton) dialogView.findViewById(R.id.acbtnPerformKyc);
        AppCompatButton btnCancel = (AppCompatButton) dialogView.findViewById(R.id.acbtnCancel);
        chkTvTopup = (CheckedTextView) dialogView.findViewById(R.id.chkTvNewAppTopup);
        chkTvTopup.setChecked(false);
        chkTvTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkTvTopup.setChecked(!chkTvTopup.isChecked());
                chkTvTopup.setCheckMarkDrawable(chkTvTopup.isChecked() ? R.drawable.ic_check_box_black_24dp : R.drawable.ic_check_box_outline_blank_black_24dp);
                tietOldCase.setVisibility(chkTvTopup.isChecked() ? View.VISIBLE : View.GONE);
            }
        });

        builder.setTitle("New Loan Application");
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        btnKyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if (scanFormat != null) {
                    setAadharContent(scanContent);
                }
            }
        } else if (requestCode == Global.EKYC_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                processKycresponse(data.getStringExtra("EKYCDATA"));
            } else {
                Toast.makeText(getBaseContext(),"Error Performing eKYC", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Global.MANUAL_FORM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                borrower = Borrower.getBorrower(data.getLongExtra(Global.BORROWER_TAG, 0));
                associateManager(borrower);
                //setSourcingType(borrower);
                borrower.Oth_Prop_Det = null;
                borrower.save();
                showScreen(borrower);
                //processKycresponse(data.getStringExtra("EKYCDATA"));
            } else {
                //Log.d("Error", "Could not scan QR");
                Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setAadharContent(String aadharDataString) {
        try {
            final AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));
            if (aadharData.Address2 == null) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address2.trim().equals("")) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }
            if (aadharData.Address1 == null) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address1.trim().equals("")) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }

            if (aadharData.GurName == null || aadharData.GurName == "") {
                final EditText input = new EditText(ActivityFinancing.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityFinancing.this);
                builder.setTitle("Aadhar eKYC ");
                builder.setMessage("Father /Husband name missing, please input below");
                builder.setView(input);
                DialogInterface.OnClickListener onSaveClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aadharData.GurName = input.getText().toString();
                        createBorrowerFromAadhar(aadharData);
                    }
                };
                builder.setPositiveButton("Save", onSaveClickListener);
                final AlertDialog dialog = builder.create();
                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(s.length() >= 3);
                    }
                });

                dialog.show();
            } else {
                createBorrowerFromAadhar(aadharData);
            }
        } catch (Exception ex) {
            Utils.alert(this, ex.getMessage());
        }
    }

    private void showScreen(Borrower borrower) {
        Intent intent = new Intent(this, ActivityLoanApplication.class);
        intent.putExtra(Global.BORROWER_TAG, borrower.FiID);
        startActivity(intent);
    }

    @Override
    public void onListFragmentLoanAppInteraction(Borrower borrower, int position) {
        this.borrower = borrower;
        showScreen(borrower);
        ((FragmentLoanAppList) fragment).loanApplicationRVA.notifyItemChanged(position);
    }

    @Override
    public void onListFragmentLoanAppInteraction(PendingFi pendingFi, int position) {
        Borrower borrower = SQLite.select().from(Borrower.class)
                .where(Borrower_Table.GroupCode.eq(pendingFi.GroupCode))
                .and(Borrower_Table.Creator.eq(pendingFi.Creator))
                .and(Borrower_Table.Code.eq(pendingFi.Code))
                .and(Borrower_Table.CityCode.eq(pendingFi.CityCode))
                .querySingle();
        if (borrower == null) {
            fetchBorrower(pendingFi);
        } else {
            showScreen(borrower);
        }
    }

    private void submitLoanApplication(Borrower borrowerToSubmit) {
        final Context context = this;
        final Borrower borrower = Borrower.getBorrower(borrowerToSubmit.FiID);
        if (borrower.UserID == null)
            borrower.UserID = IglPreferences.getPrefString(ActivityFinancing.this, SEILIGL.USER_ID, "");

        Map<String, String> message = borrower.validateLoanApplication(context);
        if (message.size() > 0) {
            String combineMessage = Arrays.toString(message.values().toArray());
            combineMessage = combineMessage.replace("[", "->").replace(", ", "\n->").replace("]", "");
            Utils.alert(context, combineMessage);
            return;
        }

        final AsyncResponseHandler guarantorAsyncResponseHandler = new AsyncResponseHandler(this, "Loan Financing\nSubmittiong Loan Application", "Updating Guarantor Information") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                //Log.d("Response Data",jsonString);
                Utils.showSnakbar(findViewById(android.R.id.content), "Guarantors saved with Loan Application Saved");
                fragment = FragmentKycSubmit.newInstance(borrower.FiID);
                fab.setVisibility(View.GONE);
                showFragment(fragment);
            }
        };
        AsyncResponseHandler dataAsyncResponseHandler = new AsyncResponseHandler(this, "Loan Financing\nSubmittiong Loan Application","Submitting Borrower Information") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                //Log.d("Response Data",jsonString);
                Utils.showSnakbar(findViewById(android.R.id.content), "Borrower Loan Application Saved");

                try {
                    JSONObject jo = new JSONObject(jsonString);


                    Log.d("CHeckJsonFinancing",jo+"");
                    Log.d("CHeckJsonFinancing1",jsonString+"");


                    borrower.Code = jo.getLong("FiCode");
                    borrower.Oth_Prop_Det = "U";

                    borrower.save();
                    if (borrower.getFiGuarantors().size() > 0) {
                        borrower.updateFiGuarantors();
                        (new WebOperations()).postEntity(context, "posguarantor", "saveguarantors", WebOperations.convertToJson(borrower.getFiGuarantors()), guarantorAsyncResponseHandler);
                    } else {
                        fragment = FragmentKycSubmit.newInstance(borrower.FiID);
                        fab.setVisibility(View.GONE);
                        showFragment(fragment);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        if (borrower.SEL != null) {
            if (borrower.SEL.equals(""))
                borrower.SEL = "0";
        } else {
            borrower.SEL = "0";
        }
        borrower.SEL = String.valueOf(Integer.parseInt(borrower.SEL) + 1);
        if (borrower.Approved != null && borrower.Approved.equals("NPR"))
            borrower.Approved = null;
        (new WebOperations()).postEntity(context, "posfi", "updatefi", WebOperations.convertToJson(borrower), dataAsyncResponseHandler);
    }

    @Override
    public void onListFragmentLoanAppSubmitInteraction(Borrower borrower, int position) {
        submitLoanApplication(borrower);
    }

    @Override
    public void onListFragmentKycScanInteraction(Borrower borrower) {
    }

    @Override
    public void onSubmittedApplicationsFragmentInteraction(Borrower borrower) {
    }

    @Override
    public void onListFragmentHomeInteraction(Borrower borrower) {

    }

    private void processKycresponse(String kycStatus) {
        JSONObject jsonObj = null;
        Log.i("kyc", kycStatus);
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
                    fetchKYC(uuid);
                } else {
                    Utils.alert(this, c.getString("errcode") + "\n" + c.getString("errmsg"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetchKYC(String uuid) {
        Toast.makeText(this, "Fetching eKYC", Toast.LENGTH_LONG).show();
        //Log.d("eKYC","Fetching eKYC");

        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Financing", "Fetching EKYC") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Aadhar aadhar1 = (new Gson()).fromJson((new JsonParser()).parse(new String(responseBody)), Aadhar.class);
                    final Aadhar aadhar = aadhar1;
                    if (aadhar1.getPoaco() == null || aadhar1.getPoaco().equals("")) {
                        final EditText input = new EditText(ActivityFinancing.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityFinancing.this);
                        builder.setTitle("Aadhar eKYC ");
                        builder.setMessage("Father /Husband name missing, please input below");
                        builder.setView(input);
                        DialogInterface.OnClickListener onSaveClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AadharData aadharData = AadharUtils.parseAadhar(aadhar);
                                aadharData.GurName = input.getText().toString();
                                createBorrowerFromAadhar(aadharData);
                            }
                        };
                        builder.setPositiveButton("Save", onSaveClickListener);
                        final AlertDialog dialog = builder.create();
                        input.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(s.length() >= 3);
                            }
                        });

                        dialog.show();
                    } else {
                        AadharData aadharData = AadharUtils.parseAadhar(aadhar);
                        createBorrowerFromAadhar(aadharData);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //Log.d("eKYC Response", error.getLocalizedMessage());
            }
        };

        RequestParams params = new RequestParams();
        params.add("UUID", uuid);
        (new WebOperations()).getEntity(this, BuildConfig.EKYC_FETCH_URL, params, asyncResponseHandler);
    }

    private void createBorrowerFromAadhar(AadharData aadharData) {
        borrower = new Borrower(manager.Creator, manager.TAG, manager.FOCode, manager.AreaCd, IglPreferences.getPrefString(ActivityFinancing.this, SEILIGL.USER_ID, ""));
        //setSourcingType(borrower);
        AadharUtils.setAadharToBorrower(aadharData, borrower);
        borrower.Oth_Prop_Det = null;
        borrower.save();
        showScreen(borrower);
    }

    private void associateManager(Borrower borrower) {
        borrower.Creator = manager.Creator;
        borrower.Tag = manager.TAG;
        borrower.GroupCode = manager.FOCode;
        borrower.CityCode = manager.AreaCd;
        borrower.UserID = IglPreferences.getPrefString(ActivityFinancing.this, SEILIGL.USER_ID, "");
    }

    private void setSourcingType(Borrower borrower) {

        if (BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing")) {
            borrower.person_Contact_place = "FM";
        } else {
            borrower.person_Contact_place = "CSO";
            borrower.T_ph3 = "WP424";
        }
    }

    private void fetchPendingFiList() {
        //Toast.makeText(this,"Fetching List Pending FIs",Toast.LENGTH_LONG).show();
        //Log.d("eKYC","Fetching eKYC");
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Financing", "Fetching List of Pending FIs") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String jsonString = new String(responseBody);
                    //Log.d("Pending FI List", jsonString);
                    Type listType = new TypeToken<List<PendingFi>>() {
                    }.getType();
                    pendingFis = WebOperations.convertToObjectArray(jsonString, listType);
                    onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_loan_application));
                    //Log.d("Pending FI List", pendingFis.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //Log.d("eKYC Response", error.getLocalizedMessage());
            }
        };

        RequestParams params = new RequestParams();
        //getFiListEditing(string IMEINO, string GroupCode,string CityCode, string Creator)
        params.add("IMEINO", IglPreferences.getPrefString(this, SEILIGL.DEVICE_IMEI, "0"));
        params.add("AreaCd", manager.AreaCd);
        params.add("FOCode", manager.FOCode);
        params.add("Creator", manager.Creator);
        (new WebOperations()).getEntity(this, "POSFI", "getFiListEditing", params, asyncResponseHandler);
    }

    public List<PendingFi> getPendingFis() {
        return this.pendingFis;
    }

    public List<Borrower> getBorrowers() {
        borrowers = SQLite.select().from(Borrower.class)
                .where(Borrower_Table.GroupCode.eq(manager.FOCode))
                .and(Borrower_Table.Creator.eq(manager.Creator))
                .and(Borrower_Table.CityCode.eq(manager.AreaCd))
                .and(Borrower_Table.Oth_Prop_Det.isNull())
                .queryList();
        //Log.d("Borrowers", borrowers.toString());
        return borrowers;
    }

    private void fetchBorrower(final PendingFi pendingFi) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Financing", "Fetching Borrower Data") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String jsonString = new String(responseBody);
                    jsonString = jsonString
                            .replace("{\"Fi", ",\"fi").replace(",\"Fi", ",\"fi");

                    //Log.d("Borrower from JSON ", jsonString);
                    BorrowerDTO borrowerDto = WebOperations.convertToObject(jsonString, BorrowerDTO.class);
                    //Log.d("Borrower from DTO ", borrowerDto.toString());
                    //borrower=borrowerDto.getBorrower();
                    //borrower=Borrower.getBorrower(borrower.Code,borrower.Creator);
                    borrower = new Borrower(borrowerDto);



                    fetchUploadedGuarantor(borrower);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //Log.d("eKYC Response", error.getLocalizedMessage());
            }
        };

        RequestParams params = new RequestParams();
        //getFiToEdit(Int64 FiCode, string Creator)
        //params.add("IMEINO", IglPreferences.getPrefString(this, SEILIGL.DEVICE_IMEI, "0"));
        params.add("FiCode", String.valueOf(pendingFi.Code));
        params.add("Creator", pendingFi.Creator);
        (new WebOperations()).getEntity(this, "POSFI", "getFiToEdit", params, asyncResponseHandler);
    }

    private void fetchUploadedDocsList(final Borrower borrower) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(ActivityFinancing.this, "Loan Financing", "Fetching List of Uploaded Documents") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                jsonString = jsonString.replace("FiCode", "FICode");

                Type listType = new TypeToken<List<DocumentStoreDTO>>() {
                }.getType();
                List<DocumentStoreDTO> borrowerDocumentList = WebOperations.convertToObjectArray(jsonString, listType);
                //Log.d("DocumentList", borrowerDocumentList.toString());
                DocumentStore documentStore;
                for (DocumentStoreDTO dto : borrowerDocumentList) {
                    documentStore = new DocumentStore(dto);
                    documentStore.FiID = borrower.FiID;
                    documentStore.updateStatus = true;
                    documentStore.save();
                }
                borrower.Oth_Prop_Det = "U";
                borrower.save();
                showScreen(borrower);
            }

        };
        RequestParams params = new RequestParams();
        params.add("FiCode", String.valueOf(borrower.Code));
        params.add("Creator", borrower.Creator);
        (new WebOperations()).getEntity(ActivityFinancing.this, "POSFI", "getFiUploadedDocs", params, asyncResponseHandler);

    }

    private void fetchUploadedGuarantor(final Borrower borrower) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Financing", "Fetching Co-Borrower / Guarantor 1 Data") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                //Log.d("Guarantor Json", jsonString);

                //jsonString=jsonString.replace("FiCode","FICode");

                Type listType = new TypeToken<List<GuarantorDTO>>() {
                }.getType();
                List<GuarantorDTO> guarantorDTOList = WebOperations.convertToObjectArray(jsonString, listType);
                Guarantor guarantor;
                for (GuarantorDTO guarantorDTO : guarantorDTOList) {
                    guarantor = guarantorDTO.getGuarantor(borrower);
                    guarantor.setIsAadharVerified("Q");
                    guarantor.save();
                }
                fetchUploadedDocsList(borrower);
                //GuarantorDTO guarantorDTO=WebOperations.convertToObject(jsonString, GuarantorDTO.class);
                //Log.d("Guarantor",guarantor.toString());

            }

        };
        RequestParams params = new RequestParams();
        params.add("FiCode", String.valueOf(borrower.Code));
        params.add("Creator", borrower.Creator);
        params.add("GrNo", "1");

        (new WebOperations()).getEntity(ActivityFinancing.this, "POSGuarantor", "GetGuarantor", params, asyncResponseHandler);

    }
}
