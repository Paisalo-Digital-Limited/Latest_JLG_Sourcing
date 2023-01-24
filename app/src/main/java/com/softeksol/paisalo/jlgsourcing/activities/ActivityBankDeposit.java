package com.softeksol.paisalo.jlgsourcing.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.BitmapUtility;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.entities.BankAccountData;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentDeposit;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ActivityBankDeposit extends AppCompatActivity implements View.OnClickListener, CameraUtils.OnCameraCaptureUpdate, View.OnLongClickListener {


    FragmentDeposit.PendigBatch pendigBatch;
    private TextView tvAmount;
    private int amount, amt;
    private Uri uriPicture;
    private ImageView imageView;
    private AppCompatActivity activity;
    private BankAccountData.BankDetail bankDetail = null;
    private int depositAmount;
    private boolean isImageCaptured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_deposit);
        activity = this;
        pendigBatch = (FragmentDeposit.PendigBatch) getIntent().getSerializableExtra("PENDING_BATCH");
        amount = pendigBatch.getBalance();
        TextView tvMaxAmount = (TextView) findViewById(R.id.tvBankDepMaxAmount);
        tvMaxAmount.setText("" + amount);
        amt = (amount / 100) * 100;
        //amt = amount;
        depositAmount = amt;
        tvAmount = (TextView) findViewById(R.id.tvBankDepAmount);
        tvAmount.setTypeface(null, Typeface.BOLD);
        tvAmount.setText("" + amt);


        findViewById(R.id.imgBtnBankDepPlus100).setOnClickListener(this);
        findViewById(R.id.imgBtnBankDepPlus1000).setOnClickListener(this);
        findViewById(R.id.imgBtnBankDepMinus100).setOnClickListener(this);
        findViewById(R.id.imgBtnBankDepMinus1000).setOnClickListener(this);
        findViewById(R.id.btnBankDepSelectBank).setOnClickListener(this);
        findViewById(R.id.btnBankDepSubmit).setOnClickListener(this);
        findViewById(R.id.btnBankDepCancel).setOnClickListener(this);
        //findViewById(R.id.imgVeiwCaptureBankReceipt).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.imgVeiwCaptureBankReceipt);
        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int tAmt = Integer.parseInt(tvAmount.getText().toString());
        switch (v.getId()) {
            case R.id.imgBtnBankDepPlus100:
                tAmt += 100;
                updateAmount(tAmt);
                break;
            case R.id.imgBtnBankDepMinus100:
                tAmt -= 100;
                updateAmount(tAmt);
                break;
            case R.id.imgBtnBankDepPlus1000:
                tAmt += 1000;
                updateAmount(tAmt);
                break;
            case R.id.imgBtnBankDepMinus1000:
                tAmt -= 1000;
                updateAmount(tAmt);
                break;
            case R.id.btnBankDepSelectBank:
                selectBank();
                break;
            case R.id.btnBankDepSubmit:
                updateBankDep();
                break;
            case R.id.btnBankDepCancel:
                finish();
                break;
            case R.id.imgVeiwCaptureBankReceipt:
                try {
                    CameraUtils.dispatchTakePictureIntent(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.imgVeiwCaptureBankReceipt:
                try {
                    CameraUtils.picMediaImage(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }

    private void updateAmount(int tAmt) {
        if (tAmt > amt) tAmt = amount;
        if (tAmt < 100) tAmt = 100;
        tvAmount.setText("" + tAmt);
        depositAmount = tAmt;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtils.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            CropImage.activity(uriPicture)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CameraUtils.SELECT_PICTURE && resultCode == RESULT_OK) {
            uriPicture = data.getData();
            if (uriPicture != null) {
                CropImage.activity(uriPicture)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Exception error = null;
            Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 1000, error, true);
            if (error == null && imageUri != null) {
                showPicture(imageUri);
                isImageCaptured = true;
                /*if(mDocumentStore!=null ){
                    mDocumentStore.imagePath=imageUri.getPath();
                    mDocumentStore.save();
                }*/
            }
        }
    }

    @Override
    public void cameraCaptureUpdate(Uri uriImage) {
        this.uriPicture = uriImage;
    }

    private void showPicture(Uri imageUri) {
        Glide.with(this).load(imageUri.getPath()).into(imageView);
    }

    private void selectBank() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Bank");
        ExpandableListView listView = new ExpandableListView(this);
        listView.setAdapter(new BankExpandableListAdapter(this, (new BankAccountData()).getBankList()));
        builder.setView(listView);
        final AlertDialog alertDialog = builder.create();
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                bankDetail = (BankAccountData.BankDetail) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                BankAccountData.BankList bankList = (BankAccountData.BankList) parent.getExpandableListAdapter().getGroup(groupPosition);
                ((TextView) activity.findViewById(R.id.tvBankDepBankName)).setText(bankList.getBankName());
                ((TextView) activity.findViewById(R.id.tvBankDepBankAccNo)).setText(bankDetail.getAccountNo());
                alertDialog.dismiss();
                return false;
            }
        });
        alertDialog.show();
    }

    private void updateBankDep() {
        UpdateBatch updtBatch = new UpdateBatch();
        imageView.buildDrawingCache();
        updtBatch.setSlipImgB64(BitmapUtility.getStringImage(imageView.getDrawingCache(), 500));

        if (bankDetail == null) {
            Utils.alert(this, "Please select a Bank Account");
        } else if (!isImageCaptured) {
            Utils.alert(this, "Please capture Bank Receipt");
        } else if (depositAmount < 100) {
            Utils.alert(this, "Deposit Amount should be greater than Rs.99/-");
        } else {

            updtBatch.setAhead(bankDetail.getBankCode());
            updtBatch.setDepositAmt(depositAmount);
            updtBatch.setDepositDateTimeUTC(new Date());
            updtBatch.setFoCode(pendigBatch.getFocode());
            updtBatch.setCreator(pendigBatch.getCreator());
            updtBatch.setDatabaseName(pendigBatch.getDatabasename());
            updtBatch.setBatchNo(pendigBatch.getBatchno());
            updtBatch.setBatchDate(pendigBatch.getBatchDate());


            DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(this, "Loan Collection", "Saving Bank Deposit Entry") {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle(activity.getString(R.string.appname) + " (" + BuildConfig.VERSION_NAME + ")");
                        builder.setMessage(new String(responseBody));
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.create().show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(activity, error.getMessage() + "\n" + (new String(responseBody)), Toast.LENGTH_LONG).show();
                    //Log.d("eKYC Response",error.getLocalizedMessage());
                }
            };


            (new WebOperations()).postEntity(this, "POSDATA", "instcollection", "bankdepositreceipt", WebOperations.convertToJson(updtBatch), asyncResponseHandler);
        }
    }

    class UpdateBatch implements Serializable {
        @Expose
        private int ADepRecptNo = 0;
        @Expose
        private int IMEI = 0;
        @Expose
        private String Ahead;
        @Expose
        private int DepositAmt;
        @Expose
        private Date DepositDateTimeUTC;
        @Expose
        private String Flag;
        @Expose
        private String CreationDate;
        @Expose
        private String ReconcileFlag = "N";
        @Expose
        private String FoCode;
        @Expose
        private String Creator;
        @Expose
        private String DatabaseName;
        @Expose
        private int BatchNo;
        @Expose
        private Date BatchDate;
        @Expose
        private String SlipImgB64;

        public String getAhead() {
            return Ahead;
        }

        public void setAhead(String ahead) {
            Ahead = ahead;
        }

        public int getDepositAmt() {
            return DepositAmt;
        }

        public void setDepositAmt(int depositAmt) {
            DepositAmt = depositAmt;
        }

        public Date getDepositDateTimeUTC() {
            return DepositDateTimeUTC;
        }

        public void setDepositDateTimeUTC(Date depositDateTimeUTC) {
            DepositDateTimeUTC = depositDateTimeUTC;
        }

        public String getFoCode() {
            return FoCode;
        }

        public void setFoCode(String foCode) {
            FoCode = foCode;
        }

        public String getCreator() {
            return Creator;
        }

        public void setCreator(String creator) {
            Creator = creator;
        }

        public String getDatabaseName() {
            return DatabaseName;
        }

        public void setDatabaseName(String databaseName) {
            DatabaseName = databaseName;
        }

        public int getBatchNo() {
            return BatchNo;
        }

        public void setBatchNo(int batchNo) {
            BatchNo = batchNo;
        }

        public Date getBatchDate() {
            return BatchDate;
        }

        public void setBatchDate(Date batchDate) {
            BatchDate = batchDate;
        }

        public String getSlipImgB64() {
            return SlipImgB64;
        }

        public void setSlipImgB64(String slipImgB64) {
            SlipImgB64 = slipImgB64;
        }

        @Override
        public String toString() {
            return "UpdateBatch{" +
                    "ADepRecptNo=" + ADepRecptNo +
                    ", IMEI=" + IMEI +
                    ", Ahead='" + Ahead + '\'' +
                    ", DepositAmt=" + DepositAmt +
                    ", DepositDateTimeUTC=" + DepositDateTimeUTC +
                    ", Flag='" + Flag + '\'' +
                    ", CreationDate='" + CreationDate + '\'' +
                    ", ReconcileFlag='" + ReconcileFlag + '\'' +
                    ", FoCode='" + FoCode + '\'' +
                    ", Creator='" + Creator + '\'' +
                    ", DatabaseName='" + DatabaseName + '\'' +
                    ", BatchNo=" + BatchNo +
                    ", BatchDate=" + BatchDate +
                    ", SlipImgB64='" + SlipImgB64 + '\'' +
                    '}';
        }
    }

    public class BankExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private ArrayList<BankAccountData.BankList> bankLists;
        private List<String> laptops;

        public BankExpandableListAdapter(Context context, ArrayList<BankAccountData.BankList> bankLists) {
            this.context = context;
            this.bankLists = bankLists;
            this.laptops = laptops;
        }

        public BankAccountData.BankDetail getChild(int groupPosition, int childPosition) {
            return bankLists.get(groupPosition).getBankDetails().get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = infalInflater.inflate(R.layout.layout_item_single, null);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 5, 5, 5);
            TextView item = (TextView) convertView.findViewById(R.id.tvListItemSingle);
            item.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            item.setLayoutParams(params);

            item.setText(getChild(groupPosition, childPosition).getAccountNo());
            return convertView;
        }

        public int getChildrenCount(int groupPosition) {
            return bankLists.get(groupPosition).getBankDetails().size();
        }

        public BankAccountData.BankList getGroup(int groupPosition) {
            return bankLists.get(groupPosition);
        }

        public int getGroupCount() {
            return bankLists.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.layout_item_single,
                        null);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 5, 5);
            TextView item = (TextView) convertView.findViewById(R.id.tvListItemSingle);
            item.setTypeface(null, Typeface.BOLD);
            item.setLayoutParams(params);
            BankAccountData.BankList bankList = getGroup(groupPosition);
            item.setText(bankList.getBankName() + " (" + bankList.getBankDetails().size() + ")");
            return convertView;
        }

        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
