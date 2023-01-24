package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.StateData;
import com.softeksol.paisalo.jlgsourcing.entities.ViewHolders.BorrowerRecViewHolder;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by sachindra on 2016-10-04.
 */
public class Utils {

   /* Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    RestAdapter restAdapter = new RestAdapter.Builder()
            .setConverter(new GsonConverter(gson))
            .build();*/

    private static void confirmDelete(final BorrowerRecViewHolder vh) {
        final Context context = vh.mView.getContext();
        new AlertDialog.Builder(context)
                .setTitle("Loan Application")
                .setMessage("Do you really want to remove ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Borrower b = vh.mItem;
                        ViewGroup vg = (ViewGroup) vh.mView.getParent();
                        if (vh.mView.getParent() != null) {
                            b.delete();
                            vg.removeView(vh.mView);
                        }
                        vg.refreshDrawableState();
                        Toast.makeText(context, "Loan Application Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    public static <T extends TextView> String getNotNullText(T et) {
        String retString = "";
        if (et.getText().toString() != null) retString = et.getText().toString();
        return retString;
    }

    public static <T extends TextView> String getNotNullText(T et, String defValue) {
        String retString = defValue;
        if (et.getText().toString() != null) retString = et.getText().toString();
        return retString;
    }

    public static int getNotNullInt(EditText et) {
        int retInt = 0;
        try {
            retInt = Integer.parseInt(et.getText().toString());
        } catch (Exception e) {
            Log.e("IntergerParse", e.getMessage());
        }
        return retInt;
    }

    public static <T> String getNotNullString(T element) {
        String retString = "";
        if (element != null) {
            //Log.d("ControlType", element.getClass().getSimpleName());
            if (element.getClass().getSimpleName().equals("String")) {
                retString = (String) element;
            } else if (element.getClass().getSimpleName().equals("EditText")) {
                retString = ((EditText) element).getText().toString();
            } else {
                retString = String.valueOf(element);
            }
        }
        return retString;
    }

    public static List<RangeCategory> getList(long start, long end, long step, long multiplier, String description) {
        List<RangeCategory> rangeCategoryList = new ArrayList<RangeCategory>();
        for (long i = start; i <= end; i += step) {
            rangeCategoryList.add(new RangeCategory(String.valueOf(i * multiplier), description));
        }
        return rangeCategoryList;
    }

    public static <T, E> void setSpinnerPosition(Spinner spinner, Class<T> tClass, E searchValue, String fieldName) {
        int index;
        T retValue = null;
        if (spinner.getAdapter() != null) {
            try {
                Field f = tClass.getField(fieldName);
                for (index = 0; index < spinner.getAdapter().getCount(); index++) {
                    retValue = (T) spinner.getAdapter().getItem(index);
                    if (f.getType().getSimpleName().equals(searchValue.getClass().getSimpleName())) {
                        Object value = f.get(retValue);
                        if (value == searchValue) {
                            spinner.setSelection(index);
                            break;
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //return retValue;
    }

    public static <E> int setSpinnerPosition(Spinner spinner, E searchValue) {
        return setSpinnerPosition(spinner, searchValue, false);
    }

    public static <E> int setSpinnerPosition(Spinner spinner, E searchValue, boolean matchLength) {
        int index1 = -1;
        RangeCategory rangeCategory;
        String searchString = String.valueOf(searchValue);
        //Log.d("SearchValue", searchString);
        if (spinner.getAdapter() != null && searchValue != null) {
            for (int index = 0; index < spinner.getAdapter().getCount(); index++) {
                rangeCategory = (RangeCategory) spinner.getAdapter().getItem(index);
                if (matchLength) {
                    if (rangeCategory.RangeCode.substring(0, searchString.length()).equals(searchString)) {
                        spinner.setSelection(index);
                        index1 = index;
                        break;
                    }
                } else {
                    if (rangeCategory.RangeCode.equals(searchString)) {
                        spinner.setSelection(index);
                        index1 = index;
                        break;
                    }
                }
            }
        }
        return index1;
    }

    public static <T extends Object, E> T getSpinnerValue(Spinner spinner, Class<E> tClass) {
        //Log.d("getSpinnerValue", "Type name=" + tClass.getSimpleName());
        T retValue = null;
        Object o = null;
        String rValue = ((RangeCategory) spinner.getSelectedItem()).DescriptionEn;
        if (tClass.getSimpleName().equals("String"))
            o = rValue;
        if (tClass.getSimpleName().equals("Integer")) {
            o = Integer.parseInt(rValue);
        }
        if (tClass.getSimpleName().equals("Long")) {
            o = Long.parseLong(rValue);
        }
        retValue = (T) o;
        return retValue;
    }

    public static <T> T NullIf(T value, T defaultValue) {
        T retValue = null;
        Object o = null;
        if (value == null) {
            if (defaultValue == null) {
                if (value.getClass().getSimpleName().equals("String")) {
                    o = (defaultValue == null ? "" : defaultValue);
                } else {
                    o = 0;
                }
                retValue = (T) o;
            } else {
                retValue = defaultValue;
            }
        } else {
            retValue = value;
        }
        return retValue;
    }

    public static String getSpinnerStringValue(Spinner spinner) {
        String retValue = null;
        if (spinner.getSelectedItem() != null)
            retValue = ((RangeCategory) spinner.getSelectedItem()).RangeCode;
        return retValue;
    }

    public static int getSpinnerIntegerValue(Spinner spinner) {
        int retValue = 0;
        //if(spinner.getVisibility()==View.VISIBLE)
        retValue = Integer.parseInt(getSpinnerStringValue(spinner));
        return retValue;
    }

    public static long getSpinnerLongValue(Spinner spinner) {
        long retValue = Long.parseLong(getSpinnerStringValue(spinner));
        return retValue;
    }

    public static String getSpinnerStateId(Spinner spinner) {
        String retValue = ((StateData) spinner.getSelectedItem()).StateCode;
        return retValue;
    }

    public static <E> int setSpinnerPositionState(Spinner spinner, E searchValue) {
        int index = 0;
        StateData stateData;
        String searchString = String.valueOf(searchValue);
        if (spinner.getAdapter() != null) {
            for (index = 0; index < spinner.getAdapter().getCount(); index++) {
                stateData = (StateData) spinner.getAdapter().getItem(index);
                if (stateData.StateCode.equals(searchString)) {
                    spinner.setSelection(index);
                    break;
                }
            }
        }
        return index;
    }



    /*public static int getSpinnerValue(Spinner spinner,Class<Integer> tClass){
        int retValue=0;
        String rValue=((RangeCategory)spinner.getSelectedItem()).DescriptionEn;
        retValue=Integer.parseInt(rValue);
        return retValue;
    }
    public static String getSpinnerValue(Spinner spinner,Class<String> tClass){
        String retValue=0;
        String rValue=((RangeCategory)spinner.getSelectedItem()).DescriptionEn;
        retValue=Integer.parseInt(rValue);
        return retValue;
    }*/

    public static void initBorrowerSwipe(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final BorrowerRecViewHolder vh = (BorrowerRecViewHolder) viewHolder;
                if (direction == ItemTouchHelper.LEFT) {
                    confirmDelete(vh);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public static void setLayoutToRecyclerView(View v, int mColumnCount) {
        if (v instanceof RecyclerView) {
            Context context = v.getContext();
            RecyclerView recyclerView = (RecyclerView) v;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //Utils.initBorrowerSwipe(recyclerView);
        }
    }

    public static void alert(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name) + " (" + BuildConfig.VERSION_NAME + ")");
        builder.setMessage(msg);
        builder.create().show();
    }




    public static void showSnakbar(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public static void showSnakbar(View v, int resId) {
        Snackbar.make(v, resId, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }


    public static <T> T getFieldValue(Cursor cursor, String columnName, Class<T> tClass) {
        int columnId = cursor.getColumnIndex(columnName);
        T retValue;
        Object o = null;
        if (tClass.getSimpleName().equals("String")) o = cursor.getString(columnId);
        if (tClass.getSimpleName().equals("Integer")) o = cursor.getInt(columnId);
        if (tClass.getSimpleName().equals("Long")) o = cursor.getLong(columnId);
        if (tClass.getSimpleName().equals("Float")) o = cursor.getFloat(columnId);
        if (tClass.getSimpleName().equals("Blob")) o = cursor.getBlob(columnId);
        retValue = (T) o;
        return retValue;
    }


    public static void copyFile(File source, File Destination) throws IOException {
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(Destination);
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static void writeBytesToFile(byte[] bytes, File destination) throws IOException {
        //Log.d("Actual    File Size", "" + destination.length());
        if (destination.delete()) {
            OutputStream out = new FileOutputStream(destination);
            out.write(bytes);
            out.flush();
            out.close();
            //Log.d("GrayScale File Size", "" + destination.length());
        }
    }

    public static void writeBytesToFile(byte[] bytes, Uri destination) throws IOException {
        writeBytesToFile(bytes, new File(destination.getPath()));
    }

    public static byte[] getBytesFromFile(File file) {
        byte[] imageBytes = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    public static File getFilePath(String environmentDir) {
        return getFilePath(environmentDir, true);
    }

    public static File getFilePath(String environmentDir, boolean isNoMedia) {
        File file;
        if (isNoMedia) {

            file = new File(Environment.getExternalStoragePublicDirectory(environmentDir), ".PaisaloDocs");

//            file = new File(
//                    Environment.getExternalStoragePublicDirectory(environmentDir),"Photos");
        } else {
            file = new File(
                    Environment.getExternalStoragePublicDirectory(environmentDir), "");
        }
        return file;
    }

    public static File createFile(Context context, String fileName, String fileExtention, String environmentDir) throws IOException {
        String imageFileName;
        if (fileName == null || fileName.equals("")) {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageFileName = fileExtention + "_" + timeStamp + "_";
        } else {
            imageFileName = fileName;
        }
        File storageDir = getFilePath(environmentDir);

        // Create the storage directory if it does not exist
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                //Log.d(TAG, "Oops! Failed create "+ Global.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        return File.createTempFile(imageFileName, "." + fileExtention, storageDir);
    }

    public byte[] readFileBytes(String filename) throws IOException {
        return getBytesFromFile(new File(filename));
        //Path path = Paths.get(filename);
        //return Files.readAllBytes(path);
    }

    public PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }

    public PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public static byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }

    public static SecretKey generateKey(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(password.getBytes(), "AES");
    }

    public static byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        /* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }

    /*public void Hello()
    {
        try
        {
            PublicKey publicKey = readPublicKey("public.der");
            PrivateKey privateKey = readPrivateKey("private.der");
            byte[] message = "Hello World".getBytes("UTF8");
            byte[] secret = encrypt(publicKey, message);
            byte[] recovered_message = decrypt(privateKey, secret);
            System.out.println(new String(recovered_message, "UTF8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Intent getKycIntent(Context context, String AuthType, Class<?> cls){
        Intent i = new Intent(context, cls);
        if(AuthType.equals("FP")) {
            i.putExtra("ServiceType", Constants.KYC_FP);
            i.putExtra("DeviceId", Constants.morphoScanner);
        }
        if(AuthType.equals("OTP")) {
            i.putExtra("ServiceType", Constants.KYC_OTP);
            i.putExtra("DeviceId", "");
        }
        i.putExtra("Service", "kyc");
        i.putExtra("Stack", Constants.stack_android);
        return i;
    }*/

    public static String getAadharJavascript(String UID) {
        String UID1, UID2, UID3;
        UID1 = UID.substring(0, 4);
        UID2 = UID.substring(4, 8);
        UID3 = UID.substring(8, 12);

        return "javascript:$(function(){" +
                "$('#txtaadhaarnumbr').val('" + UID1 + "').attr('readonly', true).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');" +
                "$('#txtaadhaarnumbr1').val('" + UID2 + "').attr('readonly', true).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');" +
                "$('#txtaadhaarnumbr2').val('" + UID3 + "').attr('readonly', true).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');" +
                "$('#txtaadhaarno').val('" + UID1 + "').attr('readonly', true).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');" +
                "$('#txtaadharno1').val('" + UID2 + "').attr('readonly', true).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');" +
                "$('#txtaadharno2').val('" + UID3 + "').attr('readonly', true).parent('.mdl-textfield').addClass('is-dirty').removeClass('is-invalid');" +
                "})";
    }


    public static String getDeviceSerial() {
        String serial;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            //return Build.getSerial();
            serial = "";
        } else {
            serial = Build.SERIAL;
            // do something for phones running an SDK before lollipop
        }
        return serial;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static <T extends EditText> void setOnFocuseSelect(T editText, final String defValue) {
        editText.setSelectAllOnFocus(editText.getText().toString().trim().equals(defValue));
    }

    public static String cleanTextContent(String text) {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", " ");

        // erases all the ASCII control characters
        //text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", " ");

        text = text.replaceAll("[\\p{Cntrl}&&[^\t]]", " ");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", " ");

        return text.trim();
    }

    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float convertPxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static Document parseXmlString(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlString));
        Log.d("CheckXMLDATA1","AadharData:->" + xmlString);
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
    }

    public static String toUpperCase(String str) {
        String retVal = null;
        if (str != null) {
            retVal = str.toUpperCase();
        }
        return retVal;
    }

    public static boolean isRootAvailable() {
        for (String pathDir : System.getenv("PATH").split(":")) {
            if (new File(pathDir, "su").exists()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || Build.HARDWARE.contains("golfdish");
    }

}
