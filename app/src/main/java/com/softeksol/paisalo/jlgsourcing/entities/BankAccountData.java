package com.softeksol.paisalo.jlgsourcing.entities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

@ModelContainer
@Table(database = DbIGL.class)
public class BankAccountData extends BaseModel
        implements Serializable {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Expose
    @Column
    private String BankName;
    @Expose
    @Column
    private String BankCode;
    @Expose
    @Column
    private String BankAccount;
    @Expose
    @Column
    private String AccountNo;

    public static void updateBankAccounts(final Context context) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(context, "Loan Financing", "Updating Master Data") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                //Log.d("data", jsonString);
                Type listType = new TypeToken<List<BankAccountData>>() {
                }.getType();
                List<BankAccountData> bankAccounts = WebOperations.convertToObjectArray(jsonString, listType);

                SQLite.delete().from(BankAccountData.class).query();
                for (BankAccountData bankAccount : bankAccounts) {
                    bankAccount.insert();
                }
                Toast.makeText(context, "BankAccount Updated " + String.valueOf(bankAccounts.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    Log.d("failure", String.valueOf(statusCode) + "\n" + (new String(responseBody, "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        (new WebOperations()).getEntity(context, "POSDATA", "instcollection", "banklist", null, asyncResponseHandler);
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "BankName='" + BankName + '\'' +
                ", BankCode='" + BankCode + '\'' +
                ", BankAccount='" + BankAccount + '\'' +
                ", AccountNo='" + AccountNo + '\'' +
                '}';
    }

    public ArrayList<BankList> getBankList() {
        ArrayList<BankList> bankLists = new ArrayList<>();
        for (BankAccountData bankAccountData : SQLite.select(BankAccountData_Table.BankName).distinct().from(BankAccountData.class).queryList()) {
            BankList bankList = new BankList();
            bankList.BankName = bankAccountData.BankName;
            bankList.bankDetails = getBankDetails(bankList.BankName);
            bankLists.add(bankList);
        }
        return bankLists;
    }

    private ArrayList<BankDetail> getBankDetails(String bankName) {
        ArrayList<BankDetail> bankDetails = new ArrayList<>();
        for (BankAccountData bankAccountData : SQLite.select().from(BankAccountData.class).where(BankAccountData_Table.BankName.eq(bankName)).queryList()) {
            BankDetail bankDetail = new BankDetail();
            bankDetail.AccountNo = bankAccountData.AccountNo;
            bankDetail.BankCode = bankAccountData.BankCode;
            bankDetails.add(bankDetail);
        }
        return bankDetails;
    }

    public class BankList {
        private String BankName;
        private ArrayList<BankDetail> bankDetails;

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String bankName) {
            BankName = bankName;
        }

        public ArrayList<BankDetail> getBankDetails() {
            return bankDetails;
        }

        public void setBankDetails(ArrayList<BankDetail> bankDetails) {
            this.bankDetails = bankDetails;
        }

        @Override
        public String toString() {
            return "BankList{" +
                    "BankName='" + BankName + '\'' +
                    ", bankDetails=" + bankDetails +
                    '}';
        }
    }

    public class BankDetail {
        private String BankCode;
        private String AccountNo;

        public String getBankCode() {
            return BankCode;
        }

        public void setBankCode(String bankCode) {
            BankCode = bankCode;
        }

        public String getAccountNo() {
            return AccountNo;
        }

        public void setAccountNo(String accountNo) {
            AccountNo = accountNo;
        }

        @Override
        public String toString() {
            return "BankDetail{" +
                    "BankCode='" + BankCode + '\'' +
                    ", AccountNo='" + AccountNo + '\'' +
                    '}';
        }
    }
}
