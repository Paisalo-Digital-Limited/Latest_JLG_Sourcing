package com.softeksol.paisalo.jlgsourcing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by sachindra on 2016-09-23.
 */

public class SEILIGL extends Application {

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
    public static final String BRANCH_CODE = BuildConfig.APPLICATION_ID + ".BRANCH_CODE";
    public static final String CREATOR = BuildConfig.APPLICATION_ID + ".CREATOR";
    public static final String IMEI = BuildConfig.APPLICATION_ID + ".IMEI_NO";
    private String deviceId;
    private long deviceImei;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (!BuildConfig.DEBUG) {
//
//            if (Utils.isEmulator()) {
//                Toast.makeText(getApplicationContext(), "This app is not supposed to run on Emulator", Toast.LENGTH_LONG).show();
//                // Utils.alert(this, "This app is not supposed to run on Emulator");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.exit(0);
//                    }
//                },500);
//            }
//            if (Utils.isRootAvailable()) {
//                Toast.makeText(getApplicationContext(), "This app is not supposed to run on Rooted device", Toast.LENGTH_LONG).show();
//                //  Utils.alert(this.getApplicationContext(), "This app is not supposed to run on Rooted device");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.exit(0);
//                    }
//                },500);
//            }
//
//            // check Device Developer mode is on or off
//            //Below line commented on 19_nov_2021
//            //below code is useful for only audit purpose
////            int adb = Settings.Secure.getInt(this.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0);
////            if (adb == 1){
////                Toast.makeText(getApplicationContext(), "This app is not supposed to run when Device Developer Mode is On", Toast.LENGTH_LONG).show();
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        System.exit(0);
////                    }
////                },500);
////            }
//
//        }


        IglPreferences.removePref(getBaseContext(), DEVICE_IMEI);
        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        // add for verbose logging
        //FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        IglPreferences.loadLanguage(getApplicationContext());

        //IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, BuildConfig.DATABASE_NAME);

//      IglPreferences.setSharedPref(getBaseContext(), BASE_URL, BuildConfig.BASE_URL);

        IglPreferences.setSharedPref(getBaseContext(), BASE_URL, BuildConfig.BASE_URL);

        //below line commected on 19_nov_2021
        //below line code is used only for audit purpose
        // Toast.makeText(this, deviceId, Toast.LENGTH_LONG).show();

//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        handlePermissionException();
//                    }
//                },1500);
//
//                return;
//            }
//            deviceImei = 0;
//            getDeviceIMEI();
//
//            Log.d("checkIDDD",deviceId+"");
//            if (deviceId.length() > 8) {
//                if (deviceImei > 0) {
//                    IglPreferences.setSharedPref(getBaseContext(), DEVICE_IMEI, deviceImei);
//                }
//                IglPreferences.setSharedPref(getBaseContext(), BASE_URL, BuildConfig.BASE_URL);
//
//            }
//        } catch (Exception e) {
//            //  handlePermissionException();
//        }
    }

    public static void startInstalledAppDetailsActivity(Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void handlePermissionException() {
        startInstalledAppDetailsActivity(this);
        System.exit(0);
    }

    /**
     * Returns the unique identifier for the device
     *
     */
    @SuppressLint("HardwareIds")
    public void getDeviceIMEI() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            handlePermissionException();
            return;
        }

        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        try {
            File dir = Utils.getFilePath("/", false);
            Log.e("DirectoryDeviceID",dir+"");
            File file = new File(dir, "." + BuildConfig.ESIGN_APP + ".info");
            Log.e("FileLocation",file+"");
            OutputStream out = new FileOutputStream(file);
            out.write(deviceId.getBytes());
            Log.e("DeviceIDWrite", Arrays.toString(deviceId.getBytes()) +"");
            out.flush();
            out.close();

            //Utils.writeBytesToFile(deviceId.getBytes(),file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        IglPreferences.setSharedPref(getBaseContext(), DEVICE_ID, deviceId);


    }
}
