package com.softeksol.paisalo.jlgsourcing.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.reflect.TypeToken;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.location.AttendenceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;
import static com.softeksol.paisalo.jlgsourcing.SEILIGL.DEVICE_ID;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    final static int CALLBACK_NUMBER = 1001;

    DataAsyncResponseHandler dataAsyncResponseHandler;
    File storageDir;
    String apkPath;
    private EditText userName;
    private EditText password;
    private TextView btnShareDeviceID;
    private Button btnSignIn;
    private Spinner database;
    private String deviceId;
    private long deviceImei;
    String UserName;
     String Password;

    public static final String BASE_URL = BuildConfig.APPLICATION_ID + ".BASE_URL";
    public static final String DEVICE_IMEI = BuildConfig.APPLICATION_ID + ".IMEI";
    public static final String DEVICE_ID = BuildConfig.APPLICATION_ID + ".DEV_ID";
    public static final String DATABASE_NAME = BuildConfig.APPLICATION_ID + ".DBNAME";
    public static final String LANGUAGE = BuildConfig.APPLICATION_ID + ".LANGUAGE";
    public static final String USER_ID = BuildConfig.APPLICATION_ID + ".USER_ID";
    public static final String LOGIN_TOKEN = BuildConfig.APPLICATION_ID + ".LOGIN_TOKEN";
    public static final String ALLOW_COLLECTION = BuildConfig.APPLICATION_ID + ".ALLOW_COLLECTION";
    public static final String IS_ACTUAL = BuildConfig.APPLICATION_ID + ".IS_ACTUAL";
    public static final String APP_UPDATE_URL = BuildConfig.APPLICATION_ID + ".APP_UPDATE_URL";

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle(getString(R.string.appname) + " (" + BuildConfig.VERSION_NAME + ")");

        if (checkAndRequestPermissions()){
//            getDeviceID();
            Toast.makeText(this, "All permissions are granted", Toast.LENGTH_SHORT).show();
        }else {
            permissionCheck();
        }

        btnShareDeviceID = findViewById(R.id.btnLoginShareDeviceId);
        btnShareDeviceID.setEnabled(false);
        checkBtnShareIDStatus(false);

        btnShareDeviceID.setTextColor(getResources().getColor(R.color.defaultTextColor));
        btnShareDeviceID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName = ((EditText) findViewById(R.id.til_login_username)).getText().toString();

                if (checkAndRequestPermissions()){
                    getDeviceID();
                }else {
                    permissionCheck();
                }

                Log.e("DEVICEID_NEW", IglPreferences.getPrefString(ActivityLogin.this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase() + "");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "For Mobile Registration\nDevice ID = " + IglPreferences.getPrefString(ActivityLogin.this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        btnSignIn = findViewById(R.id.btnLoginSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 UserName = ((EditText) findViewById(R.id.til_login_username)).getText().toString();
                Password = ((EditText) findViewById(R.id.etLoginPassword)).getText().toString();

                if (checkAndRequestPermissions()){
                    getDeviceID();
                }else {
                    permissionCheck();
                }

                Log.d("TAG",IglPreferences.getPrefString(ActivityLogin.this, SEILIGL.DATABASE_NAME, ""));
                Log.d("TAG",IglPreferences.getPrefString(ActivityLogin.this, SEILIGL.DEVICE_ID, ""));
                Log.d("TAG",SEILIGL.USER_ID+"");


                dataAsyncResponseHandler = new DataAsyncResponseHandler(ActivityLogin.this, "Logging in ...") {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String jsonString = new String(responseBody);
                        Log.e("TAG", "onSuccess: "+"++++++++++++++++++++++++++++++==============="+getRequestURI());
                        Log.e("TAG", "onSuccess: "+responseBody+headers);
                        Log.d("Login Resp",jsonString);
                        JSONObject jo;
                        JSONArray ja;
                        try {
                            jo = new JSONObject(jsonString);

                            Type listType = new TypeToken<List<Manager>>() {
                            }.getType();

                            JSONObject loginData = new JSONObject(jo.getString("LoginData"));
                            jo.remove("LoginData");

                            //JSONObject json = new JSONObject(String.valueOf(loginData.getJSONArray("folist")));

                            JSONArray jArray = loginData.getJSONArray("folist");

                            JSONObject jsonObject = (JSONObject) jArray.get(0);
                            Log.d("CHeckingLoginValue", jsonObject.getString("FOCode"));
                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.LOGIN_TOKEN, jo.toString());

                            //Log.d("Token", jo.toString());
                            JSONObject foImei = loginData.getJSONObject("foImei");
                            String branchCode = jsonObject.getString("FOCode");
                            String Creator = jsonObject.getString("Creator");
                            String IMEI_NO = jsonObject.getString("IMEINO");
                            loginData.remove("foImei");
                            Log.d("FOiemi", foImei.toString());
                            Log.d("LoginData", loginData.toString());
                            Log.d("CREATORAAYA", Creator+"");
                            Log.d("branchCode", branchCode+"");
                            Log.d("IMEI_NO", IMEI_NO+"");

                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.USER_ID, UserName);
                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.CREATOR, Creator);
                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.IMEI, IMEI_NO+"");
                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BRANCH_CODE, branchCode);
                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.ALLOW_COLLECTION, foImei.getString("SIMNO"));
                            //IglPreferences.setSharedPref(getBaseContext(),SEILIGL.DATABASE_NAME,foImei.getString("actualYN").equals("N")?"IGLDIG_TEST_PL":"IGLDIG_PL");
                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.IS_ACTUAL, foImei.getString("actualYN"));
                            IglPreferences.setSharedPref(getBaseContext(), SEILIGL.DEVICE_IMEI, foImei.getString("IMEINO"));

                            List<Manager> managers = WebOperations.convertToObjectArray(loginData.getString("folist"), listType);

                            int version = foImei.getInt("NewAppVerison");
                            Log.e("NewVersionCheck",version+"");
                            Log.e("NewVersionCheck",BuildConfig.VERSION_CODE+"");
                            if (version > BuildConfig.VERSION_CODE) {
//                            String appPath = foImei.getString("AppDownPath");
//                            if (appPath.toUpperCase().startsWith("ID")) {
//                                //https://docs.google.com/uc?export=download&id=
//                                //
//                                appPath = "https://drive.google.com/uc?export=download&" + appPath;
//                                IglPreferences.setSharedPref(getBaseContext(), SEILIGL.APP_UPDATE_URL, appPath);
//                            }
//                            else {
//                                IglPreferences.setSharedPref(getBaseContext(), SEILIGL.APP_UPDATE_URL, appPath);
//                            }
//
//
//                            final AlertDialog alertDialog = new AlertDialog.Builder(ActivityLogin.this)
//                                    .setTitle("Loan Financing")
//                                    .setMessage("New version of this app is available\nWant to install it now?")
//                                    .setPositiveButton("Yes", null)
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            finish();
//                                        }
//                                    }).create();
//                            alertDialog.show();
//                            final Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                            btnPositive.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    view.setEnabled(false);
//                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
//                                    alertDialog.setMessage("Downloading update, please wait.");
//                                    updateApplication(alertDialog);
//                                }
//                            });
                                if (foImei.getString("RequestUrl").length() > 4) {
                                    //IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BASE_URL, foImei.getString("RequestUrl"));
                                    //(new WebOperations()).getAccessToken(context,UserName, Password, reloginResponseHandler);
                                    updateReloginToken(foImei.getString("RequestUrl"), UserName, Password);
                                }
                                //updateManager( managers);
                                Intent intent = new Intent(ActivityLogin.this, ActivityOperationSelect.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                if (foImei.getString("RequestUrl").length() > 4) {
                                    //IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BASE_URL, foImei.getString("RequestUrl"));
                                    //(new WebOperations()).getAccessToken(context,UserName, Password, reloginResponseHandler);
                                    updateReloginToken(foImei.getString("RequestUrl"), UserName, Password);
                                }
                                //updateManager( managers);
                                Intent intent = new Intent(ActivityLogin.this, ActivityOperationSelect.class);
//                            Intent intent = new Intent(ActivityLogin.this, AttendenceActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        super.onFailure(statusCode, headers, responseBody, error);
                        Log.e("TAG", "onFailure: "+"++++++++++++++++++++++++++++++==============="+getRequestURI()+getRequestHeaders());
                        Log.e("TAG", "onFailure: "+responseBody+error.getMessage());

                    }
                };

                (new WebOperations()).getAccessToken(ActivityLogin.this, UserName, Password, dataAsyncResponseHandler);

            }
        });
        btnSignIn.setEnabled(false);

        //Displaying TextInputLayout Error
        userName = (EditText) findViewById(R.id.til_login_username);
        //tilUserName.setErrorEnabled(true);
        userName.setError("Must be 10 Characters to share Device ID");

       password = (EditText) findViewById(R.id.etLoginPassword);
        //tilPassowrd.setErrorEnabled(true);
        password.setError("Min 5 chars required");

        //Displaying EditText Error

        database = (Spinner) findViewById(R.id.database);
        userName.setError("Required");
        userName.addTextChangedListener(new MyTextWatcher(userName) {
            @Override
            public void validate(EditText editText, String text) {
                btnShareDeviceID.setEnabled(false);
                checkBtnShareIDStatus(false);


                editText.setError(null);

                switch (text.length()) {
                    case 0:
                        editText.setError("Required");
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        editText.setError("Min 5 chars required");
                        break;
                    case 10:
                        btnShareDeviceID.setEnabled(true);
                        checkBtnShareIDStatus(true);

                        break;
                }
                if (validateInputs()) {
                    btnSignIn.setEnabled(true);
                } else {
                    btnSignIn.setEnabled(false);
                }
            }
        });

        password.setError("Required");
        password.addTextChangedListener(new MyTextWatcher(password) {
            @Override
            public void validate(EditText editText, String text) {
                switch (text.trim().length()) {
                    case 0:
                        editText.setError("Required");
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        editText.setError("Min 5 chars required");
                        break;
                }
                if (validateInputs()) {
                    btnSignIn.setEnabled(true);
                } else {
                    btnSignIn.setEnabled(false);
                }
            }
        });

        TextView btnBackup = (TextView) findViewById(R.id.btnLoginBackup);
        btnBackup.setOnClickListener(this);
        btnBackup.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);

        ArrayList<RangeCategory> DatabaseName = new ArrayList<>();
        DatabaseName.add(new RangeCategory("SBI COLENDING", "Database"));
        DatabaseName.add(new RangeCategory("IGL DIGITAL", "Database"));
//        DatabaseName.add(new RangeCategory("GROUP FINANCE", "Database"));
//        DatabaseName.add(new RangeCategory("SBI PDL", "Database"));

//        DatabaseName.add(new RangeCategory("PNB COLENDING", "Database"));

        database.setAdapter(new AdapterListRange(this, R.layout.spinner_card_orange,DatabaseName));

        storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        apkPath = storageDir.getPath() + "/sourcing.apk";
        File destination = new File(apkPath);
        if (destination.exists()) destination.delete();


    database.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("SpinnerStrng",DatabaseName.get(i)+"");

                IglPreferences.removePref(getBaseContext(), DATABASE_NAME);


                if (DatabaseName.get(i).DescriptionEn.equals("IGL DIGITAL")) {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "IGLDIG_PL");
                }
                else  if (DatabaseName.get(i).DescriptionEn.equals("GROUP FINANCE")) {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "GROUPFIN_MFI");
                }
                else  if (DatabaseName.get(i).DescriptionEn.equals("SBI PDL")) {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "SBIPDL");
                }
                else {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "SBIPDLCOL");
                }
//                else {
//                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "PNBPDLCOL");
//                }

                Log.e("CHECK_DB_DOC_NAME0",IglPreferences.getPrefString(ActivityLogin.this, SEILIGL.DATABASE_NAME, "")+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void checkBtnShareIDStatus(boolean b) {
        if (b){
            btnShareDeviceID.setTextColor(getResources().getColor(R.color.blueTextColor));

        }else {
            btnShareDeviceID.setTextColor(getResources().getColor(R.color.defaultTextColor));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean retVal;
        switch (item.getItemId()) {
            case R.id.action_language_english:
                IglPreferences.setLanguage(getApplicationContext(), "en");
                reStartApplication();
                retVal = true;
                break;
            case R.id.action_language_hindi:
                retVal = true;
                IglPreferences.setLanguage(getApplicationContext(), "hi");
                reStartApplication();
                break;
            default:
                retVal = super.onOptionsItemSelected(item);
        }
        return retVal;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLoginSignIn) {

        }
        if (view.getId() == R.id.btnLoginBackup) {
            /*String aadharString="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<PrintLetterBarcodeData uid=\"344347946174\" name=\"Anoop Agarwal\" gender=\"M\" dateOfBirth=\"02-02-1979\" careOf=\"S/O Bal Kishan Agarwal\" building=\"7\" street=\"new avdhesh puri\" landmark=\"karmyogi\" locality=\"kamla nagar\" vtcName=\"Agra\" poName=\"Dayal Bagh\" districtName=\"Agra\" subDistrictName=\"Kiraoli\" stateName=\"Uttar Pradesh\" pincode=\"282005\"/>";
            Document doc= null;
            try {
                doc = Utils.parseXmlString(aadharString);
                NamedNodeMap nodeMap= AadharUtils.getAadharFields(doc);
                List<String> uidLIst=new ArrayList<>(Arrays.asList("u","uid"));
                String name=AadharUtils.getAadharFieldValue(nodeMap,uidLIst);
                Log.d("FieldName",name);

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }*/

            File newFIle = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "PAISALO_SRC_DB" + "_bkp.db");
            //File newFIle = new File(Environment.getExternalStorageDirectory().getPath() + "/test/mobilelending.db");
            //Log.d("Backup File",newFIle.getAbsolutePath());
            File DbFile = new File(FlowManager.getContext().getDatabasePath("PAISALO_SRC_DB" + ".db").getPath());
            //Log.d("Database File",DbFile.getPath());
            try {
                Utils.copyFile(DbFile, newFIle);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(newFIle));
                getApplicationContext().sendBroadcast(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (view.getId() == R.id.btnLoginShareDeviceId) {
            /*TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActivityLogin.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
                return;
            }
            String IMEINumber = telephonyManager.getImei();*/

            Log.e("DEVICEID_NEW", IglPreferences.getPrefString(this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase() + "");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "For Mobile Registration\nDevice ID = " + IglPreferences.getPrefString(this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
    }

    private void updateReloginToken(String newUrl, String UserName, String Password) {
        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BASE_URL, newUrl);

        DataAsyncResponseHandler reloginResponseHandler = new DataAsyncResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                //Log.d("Login Resp",jsonString);
                JSONObject jo;
                try {
                    jo = new JSONObject(jsonString);
                    jo.remove("LoginData");

                    IglPreferences.setSharedPref(getBaseContext(), SEILIGL.LOGIN_TOKEN, jo.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        (new WebOperations()).getAccessToken(this, UserName, Password, reloginResponseHandler);

    }

    private void reStartApplication() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void updateManager(List<Manager> managers) {
        SQLite.delete().from(Manager.class).query();
        for (Manager manager : managers) {
            manager.insert();
        }
        Snackbar.make(findViewById(android.R.id.content), "Managers List Updated", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Intent intent = new Intent(ActivityLogin.this, ActivityManagerSelect.class);
        startActivity(intent);
        finish();
    }

    private boolean validateInputs() {
        if (userName.getText().length() < 5) {
            return false;
        }
        if (password.getText().length() < 5) {
            return false;
        }
        return true;
    }

    private void updateApplication(final AlertDialog dialog) {

        final String url = IglPreferences.getPrefString(getBaseContext(), SEILIGL.APP_UPDATE_URL, "");

        if (!url.equals("")) {
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    Log.d("Dir", "Cannot make directory");
                }
            }
            final File destination = new File(apkPath);
            if (destination.exists()) destination.delete();
            final Uri uri = Uri.parse("file://" + destination);

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading Application Update ...");
            request.setTitle(ActivityLogin.this.getString(R.string.app_name));

            //set destination
            request.setDestinationUri(uri);

            // get download service and enqueue file
            final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(request);
            showDownloadProgress(downloadId, dialog);
            //set BroadcastReceiver to install app when .apk is downloaded
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    install.setDataAndType(FileProvider.getUriForFile(ActivityLogin.this,
                            ActivityLogin.this.getApplicationContext().getPackageName() + ".provider",
                            destination),
                            manager.getMimeTypeForDownloadedFile(downloadId));
                    startActivity(install);

                    unregisterReceiver(this);
                    finish();
                }
            };
            //register receiver for when .apk download is compete
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    @SuppressLint("Range")
    private void showDownloadProgress(final long downloadId, final AlertDialog alertDialog) {
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloading = true;
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);
                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();

                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                        alertDialog.dismiss();
                    }
                    if (bytes_total > 0) {
                        final int dl_progress = (int) ((bytes_downloaded * 100L) / bytes_total);
                        alertDialog.setMessage(dl_progress + " % downloaded");
                    } else {
                        alertDialog.setMessage(bytes_downloaded / 1024 + " KB downloaded");
                    }
                    cursor.close();
                }
            }
        }).start();
    }

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int readPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);

        } if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1111);
            return false;
        }
        return true;
    }


    private void permissionCheck() {



        String[] permissions = {
                Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please provide permission so that app can work smoothly ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.

                if (ActivityCompat.checkSelfPermission(ActivityLogin.this,
                        Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
                    handlePermissionException();
                }
                else {
                    getDeviceID();
                }

                // If you have access to the external storage, do whatever you nee
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    if (Environment.isExternalStorageManager()){
//
//                       getDeviceID();
//
//    // If you don't have access, launch a new activity to show the user the system's dialog
//    // to allow access to the external storage
//                    }else{
//                      handlePermissionException();
//                    }
//                }

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                //permissionCheck();
            }
        });

    }

    private void getDeviceID() {
        deviceImei = 0;
        getDeviceIMEI();

        Log.d("checkIDDD", deviceId + "");
        if (deviceId.length() > 8) {

            if (deviceImei > 0) {
                IglPreferences.setSharedPref(getBaseContext(), DEVICE_IMEI, deviceImei);
            }
            IglPreferences.setSharedPref(getBaseContext(), BASE_URL, BuildConfig.BASE_URL);

        }
    }


    private void handlePermissionException() {
        InstalledAppDetailsActivity(this);
        System.exit(0);
    }


    private void InstalledAppDetailsActivity(ActivityLogin activityLogin) {

        if (activityLogin == null) {
            return;
        }

        Intent intent = new Intent();
        //intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);

    }

    @SuppressLint("HardwareIds")
    public void getDeviceIMEI() {

//        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String lastThreeChars = "";

        try {
            if (UserName.trim().length() > 3) {
                lastThreeChars = UserName.trim().substring(UserName.trim().length() - 3);
            } else {
                lastThreeChars = UserName;
            }
                    }catch (Exception e)
        {
            Toast.makeText(this, "Please Enter User Id for getting device Id", Toast.LENGTH_SHORT).show();

        }
             deviceId = lastThreeChars + //we make this look like a valid IMEI
                    Build.BOARD.length()%10+ Build.BRAND.length()%10 +
                    Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                    Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                    Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                    Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                    Build.TAGS.length()%10 + Build.TYPE.length()%10 +
                    Build.USER.length()%10 ; //13 digits

            Toast.makeText(this, ""+lastThreeChars, Toast.LENGTH_SHORT).show();




        try {
            File dir = Utils.getFilePath("/", false);

            Log.e("DirectoryDeviceID", dir + "");
            File file = new File(dir, "." + BuildConfig.ESIGN_APP + ".info");
            Log.e("FileLocation", file + "");
            OutputStream out = new FileOutputStream(file);
            out.write(deviceId.getBytes());

            Log.e("DeviceIDWrite", Arrays.toString(deviceId.getBytes()) + "");
            Log.e("DeviceIDWrite0", deviceId + "");
            out.flush();
            out.close();

            //Utils.writeBytesToFile(deviceId.getBytes(),file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        IglPreferences.setSharedPref(getBaseContext(), DEVICE_ID, deviceId);


    }
}
