package com.softeksol.paisalo.jlgsourcing.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLoanApplication;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListDocuments;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower_Table;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore_Table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentKycSubmitNew.OnListFragmentKycScanInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentKycSubmitNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKycSubmitNew extends Fragment implements AdapterView.OnItemClickListener {
    private List<DocumentStore> documentStores = new ArrayList<>();
    private Borrower borrower;


    private OnListFragmentKycScanInteractionListener mListener;
    private AdapterListDocuments adapterListDocuments;
    private ListView listView;
    private ActivityLoanApplication activity;
    private DocumentStore mDocumentStore;
    private Boolean cropState = false;
    private Uri uriPicture;
    private long borrowerId;

    public FragmentKycSubmitNew() {
        // Required empty public constructor
    }

    public static FragmentKycSubmitNew newInstance(long borrowerId) {
        FragmentKycSubmitNew fragment = new FragmentKycSubmitNew();
        Bundle args = new Bundle();
        args.putLong(Global.BORROWER_TAG, borrowerId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            borrowerId = getArguments().getLong(Global.BORROWER_TAG, 0);
            if (borrowerId > 0) {
                borrower = SQLite.select().from(Borrower.class).where(Borrower_Table.FiID.eq(borrowerId)).querySingle();
            }
        }
        adapterListDocuments = new AdapterListDocuments(getActivity(), R.layout.layout_item_loan_app_kyc_upload, documentStores);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_list_view, container, false);
        listView = (ListView) view.findViewById(R.id.lvList);
        listView.setAdapter(adapterListDocuments);
        listView.setOnItemClickListener(this);

        Log.e("KYCSUBMITNEWW","DONE");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_kyc_submit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_submit_kyc:
                Utils.showSnakbar(getView(), "Option Selectd");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentKycScanInteractionListener) {
            mListener = (OnListFragmentKycScanInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //activity=(ActivityLoanApplication) getActivity();
        documentStores.clear();
        if (borrower == null) {
            documentStores.addAll(SQLite.select().from(DocumentStore.class)
                    .where(DocumentStore_Table.updateStatus.eq(false))
                    .and(DocumentStore_Table.ficode.greaterThan(0))
                    .queryList());
        } else {
            documentStores.addAll(SQLite.select().from(DocumentStore.class)
                    .where(DocumentStore_Table.updateStatus.eq(false))
                    .and(DocumentStore_Table.FiID.eq(borrower.FiID))
                    .and(DocumentStore_Table.ficode.greaterThan(0))
                    .queryList());
        }
        adapterListDocuments.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDocumentStore = (DocumentStore) adapterView.getItemAtPosition(i);

        DocumentStore documentStore = new DocumentStore();
        documentStore.Creator = mDocumentStore.Creator;
        documentStore.ficode = mDocumentStore.ficode;
        documentStore.fitag = mDocumentStore.fitag;

        documentStore.imageTag = mDocumentStore.imageTag;
        documentStore.GuarantorSerial = mDocumentStore.GuarantorSerial;
        documentStore.remarks = mDocumentStore.remarks;

        documentStore.checklistid = mDocumentStore.checklistid;
        documentStore.userid = mDocumentStore.userid;
        documentStore.fieldname = mDocumentStore.fieldname;

        documentStore.creationDate = mDocumentStore.creationDate;
        documentStore.latitude = mDocumentStore.latitude;
        documentStore.longitude = mDocumentStore.longitude;

        documentStore.DocId = mDocumentStore.DocId;
        documentStore.FiID = mDocumentStore.FiID;
        documentStore.apiRelativePath = mDocumentStore.apiRelativePath;
        
        documentStore.updateStatus = mDocumentStore.updateStatus;

        if (mDocumentStore.imagePath!=null){
            Log.e("KycSubmitNewImagePath",mDocumentStore.imagePath+"");
            Toast.makeText(getContext(), mDocumentStore.imagePath+"", Toast.LENGTH_SHORT).show();
            documentStore.imagePath = mDocumentStore.imagePath;
            uploadKyc(documentStore, view);
        }else {
            Toast.makeText(getContext(), "ImagePath_Null", Toast.LENGTH_SHORT).show();
        }

        //uploadKyc(documentStore, view);
    }


    public interface OnListFragmentKycScanInteractionListener {
        void onListFragmentKycScanInteraction(Borrower borrower);
    }

    private void uploadKyc(final DocumentStore documentStore, final View view) {

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pbKycItemLayoutUploadStatus);
        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Utils.showSnakbar(getView(), new String(responseBody));
                view.setVisibility(View.GONE);
                documentStore.updateStatus = true;
                //documentStore.imageshow = "";
                documentStore.update();
                (new File(documentStore.imagePath)).delete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utils.showSnakbar(getView(), error.getMessage());
            }

            @Override
            public void onStart() {
                super.onStart();
                //progressBar.setMax(documentStore.doc_data.getBlob().length);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                progressBar.setProgress((int) bytesWritten);
            }
        };
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = documentStore.configureClient(getContext(), client);
        //Log.d("Url", IglPreferences.getPrefString(getContext(), SEILIGL.BASE_URL,"")+documentStore.apiRelativePath+ "  ");
        client.post(getContext(), IglPreferences.getPrefString(getContext(), SEILIGL.BASE_URL, "") + documentStore.apiRelativePath, requestParams, responseHandler);
    }

    /*private void uploadKycJson(final DocumentStore documentStore, final View view, AsyncHttpClient client){

        final ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.pbKycItemLayoutUploadStatus);
        AsyncHttpResponseHandler responseHandler=new AsyncHttpResponseHandler() {



            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Utils.showSnakbar(getView(),new String(responseBody));
                view.setVisibility(View.GONE);
                documentStore.updateStatus=true;
                documentStore.update();
                (new File(documentStore.imagePath)).delete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utils.showSnakbar(getView(),error.getMessage());
            }

            @Override
            public void onStart() {
                super.onStart();
                //progressBar.setMax(documentStore.doc_data.getBlob().length);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                progressBar.setProgress((int)bytesWritten);
            }
        };
        (new WebOperations()).postEntity(getContext(),client,documentStore.apiRelativePath,WebOperations.convertToJson(documentStore.getDocumentDTO()),responseHandler);
    }*/


    /*private List<DocumentListHeader> getListDataHeader() {
        List<DocumentListHeader> documentListHeaders;
        if (borrower == null) {
            documentStores.addAll(SQLite.select().from(DocumentStore.class)
                    .where(DocumentStore_Table.updateStatus.eq(false))
                    .and(DocumentStore_Table.ficode.greaterThan(0))
                    .queryList());
        } else {
            documentStores.addAll(SQLite.select().from(DocumentStore.class)
                    .where(DocumentStore_Table.updateStatus.eq(false))
                    .and(DocumentStore_Table.FiID.eq(borrower.FiID))
                    .and(DocumentStore_Table.ficode.greaterThan(0))
                    .queryList());
        }
        for (DocumentStore documentStore : documentStores) {
            Borrower borrower = SQLite.select().from(Borrower.class).where(Borrower_Table.FiID.eq(documentStore.FiID)).querySingle();
            DocumentListHeader documentListHeader = new DocumentListHeader(borrower.getBorrowerName(), String.valueOf(documentStore.ficode) + "/" + documentStore.Creator);
            borrower = null;
        }
        adapterListDocuments.notifyDataSetChanged();
    }*/

}
