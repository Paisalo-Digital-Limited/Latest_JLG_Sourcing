package com.softeksol.paisalo.jlgsourcing;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonStreamerEntity;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

/**
 * Created by sachindra on 2016-10-07.
 */
public class WebOperations {
    public static <E> String convertToJson(E object) {
        String jsonString;
        Gson gson = new GsonBuilder().serializeNulls().setExclusionStrategies(new DbFlowExclusionStrategy()).excludeFieldsWithoutExposeAnnotation().create();
        jsonString = gson.toJson(object);
        return jsonString;
    }

    public static <E> List<E> convertToObjectArray(String jsonString, Type listType) {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        List<E> eList = gson.fromJson(jsonString, listType);
        return eList;
    }

    public static <E> List<E> convertToObjectArray(String jsonString) {
        Type listType = new TypeToken<List<E>>() {
        }.getType();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        List<E> eList = gson.fromJson(jsonString, listType);
        return eList;
    }

    public static <E> E convertToObject(JsonElement jsonElement, Class<E> eClass) {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        E rObject = gson.fromJson(jsonElement, eClass);
        return rObject;
    }

    public static <E> E convertToObject(String jsonString, Class<E> eClass) {
        JsonElement jsonElement = (new JsonParser()).parse(jsonString);
        return convertToObject(jsonElement, eClass);
    }

    /*public static <E> List<E> convertToObjectArray(JsonElement jsonElement, Class<List<E>> eClass){
        Gson gson = new Gson();
        List<E> eList=gson.fromJson(jsonElement, eClass);
        return eList;
    }
    public static <E> List<E> convertToObjectArray(JsonElement jsonElement, Type listType){
        Gson gson = new Gson();
        List<E> eList=gson.fromJson(jsonElement, listType);
        return eList;
    }

    public static <E> List<E> convertToArray( String jsonString, Class<List<E>> eClass){
        Type listType = new TypeToken<List<BankData>>() {}.getType();
        JsonElement jsonElement=(new JsonParser()).parse(jsonString);
        return convertToObjectArray(jsonElement,eClass);
    }
    public static <E,F> List<E> convertToObjectArray( String jsonString, Class<F> eClass){
        Type listType = new TypeToken<List<F>>() {}.getType();
        Gson gson = new Gson();
        List<E> eList=gson.fromJson(jsonString, listType);
        return eList;
    }*/

    public static void setHttpHeadersJson(Context context, AsyncHttpClient client, Boolean setBearer) {
        setHttpHeaders(context, client, setBearer);
            client.addHeader("accept", "application/json");
        client.addHeader("access", "application/json");
        client.addHeader("procname", BuildConfig.PROC_NAME);
        Log.d("TAG PROC", "setHttpHeadersJson: "+BuildConfig.PROC_NAME);
        //client.addHeader("content-type", "application/json;charset=utf-8");
    }

    public static ArrayList<Header> getHttpHeadersJson(Context context, Boolean setBearer) {
        ArrayList<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Access", "application/json"));
        headers.add(new BasicHeader("procname", BuildConfig.PROC_NAME));
        //headers.add(new BasicHeader("content-type", "application/json;charset=utf-8"));
        getHttpHeaders(context, headers, setBearer);
        return headers;
    }

    public static void setHttpHeadersJson(Context context, AsyncHttpClient client) {
        setHttpHeadersJson(context, client, true);
    }

    public static void setHttpHeaders(Context context, AsyncHttpClient client, Boolean setBearer) {
        if (setBearer) {
            String bearerString = "";
            try {
                bearerString = IglPreferences.getAccesstoken(context).getString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("TAG", "setHttpHeaders: "+bearerString);
            client.addHeader("Authorization", "bearer " + bearerString);
        }
        client.addHeader("Content-Encoding", "gzip,deflate,compress");
        client.addHeader("imeino", IglPreferences.getPrefString(context, SEILIGL.DEVICE_IMEI, "0"));
//        client.addHeader("imeino", "354690572942373");
        Log.e("DeviceId","CheckingOnHttpheader: "+IglPreferences.getPrefString(context, SEILIGL.DEVICE_ID, "0")+"");
        client.addHeader("devid", IglPreferences.getPrefString(context, SEILIGL.DEVICE_ID, "0"));
//        client.addHeader("devid", "dcf5ed225bca6228");
//        client.addHeader("dbname", IglPreferences.getPrefString(context, SEILIGL.DATABASE_NAME, BuildConfig.DATABASE_NAME));
        client.addHeader("dbname", IglPreferences.getPrefString(context, SEILIGL.DATABASE_NAME, ""));
        client.addHeader("userid", IglPreferences.getPrefString(context, SEILIGL.USER_ID, ""));
        client.addHeader("procname", BuildConfig.PROC_NAME);

        client.setTimeout(700000);


    }

    public static ArrayList<Header> getHttpHeaders(Context context, ArrayList<Header> headers, Boolean setBearer) {
        if (setBearer) {
            String bearerString = "";
            try {
                bearerString = IglPreferences.getAccesstoken(context).getString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            headers.add(new BasicHeader("Authorization", "bearer " + bearerString));
        }
        headers.add(new BasicHeader("Content-Encoding", "gzip,deflate,compress"));
        headers.add(new BasicHeader("imeino", IglPreferences.getPrefString(context, SEILIGL.DEVICE_IMEI, "0")));
//        headers.add(new BasicHeader("imeino", "354690572942373"));
        headers.add(new BasicHeader("devid", IglPreferences.getPrefString(context, SEILIGL.DEVICE_ID, "0")));
//        headers.add(new BasicHeader("devid", "dcf5ed225bca6228"));
        headers.add(new BasicHeader("dbname", IglPreferences.getPrefString(context, SEILIGL.DATABASE_NAME, "")));
        headers.add(new BasicHeader("userid", IglPreferences.getPrefString(context, SEILIGL.USER_ID, "")));
        headers.add(new BasicHeader("procname", BuildConfig.PROC_NAME));
        Log.e("TAG", "getHttpHeaders: "+headers);
        return headers;
    }

    public static void setHttpHeaders(Context context, AsyncHttpClient client) {
        setHttpHeaders(context, client, true);
    }

    public static void handleHttpFailure(Context context, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        String errorString = "";
        if (responseBody != null) {
            try {
                JSONObject errorObject = new JSONObject(new String(responseBody));
                errorString = errorObject.getString(errorObject.keys().next()) + "\n";
            } catch (JSONException e) {
                e.printStackTrace();
                errorString = new String(responseBody) + "\n";
            }
        }
        errorString += "Failure " + statusCode + "->" + error.getMessage();
        //Toast.makeText(context,errorString,Toast.LENGTH_LONG).show();
        Utils.alert(context, errorString);
    }

    public void getAccessToken(Context context, String userId, String password, ResponseHandlerInterface dataAsyncResponseHandler) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            setHttpHeadersJson(context, client, false);
            RequestParams params = new RequestParams();
            params.add("grant_type", "password");
            params.add("username", userId);
            params.add("password", password);
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "token";
            client.post(url, params, dataAsyncResponseHandler);
            Log.d("CheckBaseUrl",url+"////"+userId+"////"+password+"////"+"base URL :"+IglPreferences.getPrefString(context, SEILIGL.BASE_URL, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void updateOtions(String userId, String password, DataAsyncHttpResponseHandler dataAsyncHttpResponseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        setHttpHeadersJson(client, false);
        RequestParams params = new RequestParams();
        params.add("grant_type", "password");
        params.add("username", userId);
        params.add("password", password);
        Log.d("BaseUrl", Global.BaseUrl + "token" + " " + userId + " " + password + "  " + Global.myIMEI);
        client.post(Global.BaseUrl + "token", params, dataAsyncHttpResponseHandler);
    }*/

    public void postEntity(Context context, String controller, String method, String jsonString, ResponseHandlerInterface responseHandler) {
        try {
            //Log.d("Json Data",jsonString);
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method;
            Log.d("TAG", "postEntity: "+ IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method);
            StringEntity entity = new StringEntity(Utils.cleanTextContent(jsonString));
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            setHttpHeadersJson(context, client, true);
            Log.d("Url", url+"///post entity:///"+entity+"/////response hendler:"+responseHandler);
            client.post(context, url, entity, "application/json", responseHandler);
            //client.getLogInterface().setLoggingEnabled(1);
            //client.setLoggingEnabled(true);
            //client.setLoggingLevel(9);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void postAttendence(Context context, String dbName, String controller, String method, String jsonString, ResponseHandlerInterface responseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method;
            //Log.d("Json Data", jsonString);
            StringEntity entity = new StringEntity(Utils.cleanTextContent(jsonString));
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            client.removeHeader("dbname");
            client.removeHeader("procname");
            client.addHeader("dbname", dbName);
            //client.addHeader("dbname", "SBIPDL_TEST");
            client.addHeader("procname", BuildConfig.PROC_NAME);
            Log.d("Response",url);
            setHttpHeaders(context, client, true);
            client.post(context, url, entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void postEntity(Context context, String dbName, String controller, String method, String jsonString, ResponseHandlerInterface responseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method;
            //Log.d("Json Data", jsonString);
            StringEntity entity = new StringEntity(Utils.cleanTextContent(jsonString));
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            client.removeHeader("dbname");
            client.removeHeader("procname");
            client.addHeader("dbname", dbName);
            //client.addHeader("dbname", "SBIPDL_TEST");
            client.addHeader("procname", BuildConfig.PROC_NAME);
            Log.d("Response",url);
            setHttpHeadersJson(context, client, true);
            Log.d("Url", url+"/// post entity:///"+entity+"/////response hendler:"+responseHandler);
            client.post(context, url, entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void postEntity(Context context, AsyncHttpClient client, String relativeUrl, String jsonString, ResponseHandlerInterface responseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + relativeUrl;
            StringEntity entity = new StringEntity(Utils.cleanTextContent(jsonString));
            Log.d("Url", url+"///posrt entity:///"+entity+"/////response hendler:"+responseHandler);
            client.post(context, url, entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void postEntity(Context context, AsyncHttpClient client, String relativeUrl, JsonStreamerEntity jsonStreamerEntity, ResponseHandlerInterface responseHandler) {
        String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + relativeUrl;
        Log.d("Url", url+"///jsonStreamerEntity:///"+jsonStreamerEntity+"/////response hendler:"+responseHandler);
        client.post(context, url, jsonStreamerEntity, "application/json", responseHandler);
    }

    public void postEntity(Context context, String url, String jsonString, ResponseHandlerInterface responseHandler) {
        try {
            StringEntity entity = new StringEntity(jsonString);
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            setHttpHeadersJson(context, client, true);
            Log.d("Url", url+"///post entity:///"+entity+"/////response hendler:"+responseHandler);
            client.post(context, url, entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void postEntity1(Context context, String controller, String method, String jsonString, ResponseHandlerInterface responseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "0") + "api/" + controller + "/" + method;
            StringEntity entity = new StringEntity(Utils.cleanTextContent(jsonString));
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(700000);
            client.setThreadPool(Executors.newSingleThreadExecutor());
            ArrayList<Header> alHeaders = getHttpHeadersJson(context, true);
            Header[] headers = (alHeaders.toArray(new Header[alHeaders.size()]));
            client.post(context, url, headers, entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void postEntity(Context context, String controller, String method, RequestParams params, ResponseHandlerInterface responseHandler) {
        String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method;
        AsyncHttpClient client = new AsyncHttpClient();
        client.setThreadPool(Executors.newSingleThreadExecutor());
        setHttpHeadersJson(context, client, true);
        //Log.d("Url", url);
        Log.d("Url", url+"///post Params:///"+params+"/////response hendler:"+responseHandler);
        client.post(context, url, params, responseHandler);
    }

    public void getEntity(Context context, String controller, String method, String jsonString, ResponseHandlerInterface responseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method;

            StringEntity entity = (jsonString == null ? null : new StringEntity(jsonString));
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            setHttpHeadersJson(context, client, true);
            //Log.d("Url", url);
            //Log.d("JsonData",jsonString);
            Log.d("Url", url+"///entity:///"+entity+"/////response hendler:"+responseHandler);
            client.get(context, url, entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getEntity(Context context, String dbname, String controller, String method, RequestParams params, ResponseHandlerInterface responseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method;
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            ArrayList<Header> headers = getHttpHeaders(context, new ArrayList<Header>(), true);
            for (Header header : headers) {
                if (header.getName().equals("dbname")) {
                    header = new BasicHeader("dbname", dbname);
                }
                client.addHeader(header.getName(), header.getValue());
            }
            //setHttpHeaders(context,client, true);
            Log.d("Url", url+"///Params:///"+params+"/////response hendler:"+responseHandler);
            client.get(context, url, params, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getEntity(Context context, String controller, String method, RequestParams params, ResponseHandlerInterface responseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/" + controller + "/" + method;
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            setHttpHeaders(context, client, true);
            Log.d("Url", url+"///Params:///"+params+"/////response hendler:"+responseHandler);
            //Log.d("JsonData",jsonString);
            client.get(context, url, params, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getEntity(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setThreadPool(Executors.newSingleThreadExecutor());
            setHttpHeaders(context, client, true);
            //Log.d("Url", url);
            //Log.d("JsonData",jsonString);
            Log.d("Url", url+"///Params:///"+params+"/////response hendler:"+responseHandler);
            client.get(context, url, params, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkLoginStatus(Context context, ResponseHandlerInterface dataAsyncResponseHandler) {
        try {
            String url = IglPreferences.getPrefString(context, SEILIGL.BASE_URL, "") + "api/checklogin/";
            AsyncHttpClient client = new AsyncHttpClient();
            setHttpHeadersJson(context, client, true);
            //Log.d("BaseUrl", Global.BaseUrl + "token" + " " + userId + " " + password + "  " + Utils.getImei(context));
            client.get(url, null, dataAsyncResponseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*public static Boolean checkSession(Context context){
        String url = IglPreferences.getPrefString(context,SEILIGL.BASE_URL,"") + "api/checklogin/";
        try {
            return (new CheckLoginSession()).run(context, url);
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }*/
    /*public static JSONObject JsonFromRequestParams(RequestParams params){

    }*/
    /*public static class CheckLoginSession{
        OkHttpClient client = new OkHttpClient();
        Boolean loginStatus=false;
        Boolean run(Context context,String url) throws IOException {

            Request.Builder builder= new Request.Builder()
                    .url(url);
            for (Header header:getHttpHeadersJson(context,true)){
                builder.header(header.getName(),header.getValue());
            }
            Request request= builder.build();
            try{
                Response response = client.newCall(request).execute();
                loginStatus= response.body().string().equals("OK");
            }catch (IOException e){
                e.printStackTrace();
            }
            return loginStatus;
        }
    }*/

    public static RequestParams configureBackupClient(Context context, AsyncHttpClient client, String userid) {
        WebOperations.setHttpHeaders(context, client, false);
        client.setThreadPool(Executors.newSingleThreadExecutor());
        if (userid != null) client.addHeader("UserID", userid);
        RequestParams params = new RequestParams();
        File dbFile = new File(FlowManager.getContext().getDatabasePath("PAISALO_SRC_DB" + ".db").getPath());
        //Log.d("Image",docFile.getPath()+ "   Size "+ docFile.length());
        try {
            params.put("backupfile", dbFile, "application/octet-stream", "test.db");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return params;
    }
}
