package com.softeksol.paisalo.jlgsourcing.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.CustomProgress;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityCollection;
import com.softeksol.paisalo.jlgsourcing.entities.Attendence;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import cz.msebera.android.httpclient.Header;

public class AttendenceActivity extends AppCompatActivity {

    RadioButton AttendenceIN,AttendenceOut;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//    ProgressBar progressBar;
    TextView textLatLong, address, postcode, locaity, state, district, country;
    ResultReceiver resultReceiver;
    private  CustomProgress customProgress;
    private AppCompatButton SubmitAttendence;
    private String MarkAttendence = "";
    private Attendence attendence;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String jsonString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        resultReceiver = new AddressResultReceiver(new Handler());

        AttendenceIN = findViewById(R.id.AttendenceIN);
        AttendenceOut = findViewById(R.id.AttendenceOUT);

        address = findViewById(R.id.textaddress);
        locaity = findViewById(R.id.textlocality);
        postcode = findViewById(R.id.textcode);
        country = findViewById(R.id.textcountry);
        district = findViewById(R.id.textdistrict);
        state = findViewById(R.id.textstate);
        textLatLong = findViewById(R.id.textLatLong);
        SubmitAttendence = findViewById(R.id.submitAttendence);

        customProgress = new CustomProgress();

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AttendenceActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

        AttendenceIN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("IN_BUTTON",b+"");

                if (b){
                    MarkAttendence = "IN";
                }else {
                    MarkAttendence = "";
                }
            }
        });


        AttendenceOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("OUT_BUTTON", b+"");

                if (b){
                    MarkAttendence = "OUT";
                }else {
                    MarkAttendence = "";
                }
            }
        });

        SubmitAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("CheckingAttendence",MarkAttendence+"");
                if (!MarkAttendence.equals("")){
                    attendence = new Attendence();
                    attendence.setCreator(IglPreferences.getPrefString(AttendenceActivity.this, SEILIGL.CREATOR, ""));
                    attendence.setUserID(IglPreferences.getPrefString(AttendenceActivity.this, SEILIGL.USER_ID, ""));
                    attendence.setMobileImei(IglPreferences.getPrefString(AttendenceActivity.this, SEILIGL.IMEI, "0"));
                    attendence.setBranchCode(IglPreferences.getPrefString(AttendenceActivity.this, SEILIGL.BRANCH_CODE, ""));
                    attendence.setGpsLatInOut(String.valueOf(latitude));
                    attendence.setGpsLongInOut(String.valueOf(longitude));
                    attendence.setInOutFlag(MarkAttendence);
                    attendence.setAddrInOut("LOCALITY: "+address.getText().toString()+"  "+"CITY: "+locaity.getText().toString()+""
                            +"  "+"STATE: "+state.getText().toString()+""
                            +"  "+"COUNTRY: "+country.getText().toString()+""
                            +"  "+"PINCODE: "+postcode.getText().toString()+"");

                    if (locaity.getText().toString()!=null){
                        Gson gson = new Gson();
                        String json = gson.toJson(attendence);
                        Log.e("GsonData",json+"");
                        PushAttendence(attendence);
                    }else {
                        Toast.makeText(AttendenceActivity.this, "Kindly Restart Device or contact Coordinators", Toast.LENGTH_SHORT).show();
                    }





                }else {
                    Toast.makeText(AttendenceActivity.this, "Kindly Check IN or OUT for marking attendance", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
       customProgress.showProgress(this,"Please wait...",false);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(AttendenceActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            longitude = locationResult.getLocations().get(latestlocIndex).getLongitude();
                            textLatLong.setText(String.format("Latitude : %s\n Longitude: %s", latitude, longitude));

                            Location location = new Location("providerNA");
                            location.setLongitude(longitude);
                            location.setLatitude(latitude);
                            fetchaddressfromlocation(location);

                        } else {
                            customProgress.hideProgress();

                        }
                    }
                }, Looper.getMainLooper());

    }

    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {

                Log.e("MY_ADDRESS","LOCALITY: "+Constants.ADDRESS+""
                        +"\n"+"CITY: "+Constants.LOCAITY+""
                +"\n"+"STATE: "+Constants.STATE+""
                +"\n"+"COUNTRY: "+Constants.COUNTRY+""
                +"\n"+"PINCODE: "+Constants.POST_CODE+"");

                Log.e("FUllAddress", resultData+"");
                address.setText(resultData.getString(Constants.ADDRESS));
                locaity.setText(resultData.getString(Constants.LOCAITY));
                state.setText(resultData.getString(Constants.STATE));
                district.setText(resultData.getString(Constants.DISTRICT));
                country.setText(resultData.getString(Constants.COUNTRY));
                postcode.setText(resultData.getString(Constants.POST_CODE));
            } else {
                Toast.makeText(AttendenceActivity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            customProgress.hideProgress();
        }


    }

    private void fetchaddressfromlocation(Location location) {
        Log.e("CheckingLoc",location.getLongitude()+""+ "   "+location.getLatitude()+"");
        Intent intent = new Intent(this, FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private void PushAttendence(Attendence attendence){

        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(AttendenceActivity.this, "Loan Collection Settlement", "Saving Settlement Entry") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String responseString = new String(responseBody);
                    Log.e("AttendenceResponse",responseString+"");
//                    ((ActivityCollection) getActivity()).refreshData(null);
//                    //((ActivityCollection) getActivity()).refreshSettlement(FragmentCollectionSettlement.this);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("AttendenceFailure",error.getMessage() + "\n" + (new String(responseBody))+"");
                Toast.makeText(AttendenceActivity.this, error.getMessage() + "\n" + (new String(responseBody)), Toast.LENGTH_LONG).show();
            }
        };

        (new WebOperations()).postEntity(AttendenceActivity.this, "SBIPDLCOL", "AttndLatLong", "SaveAttendanceIn", WebOperations.convertToJson(attendence), asyncResponseHandler);
    }
}