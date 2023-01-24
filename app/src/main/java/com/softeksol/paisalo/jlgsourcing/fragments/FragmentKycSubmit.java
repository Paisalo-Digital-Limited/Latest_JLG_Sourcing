package com.softeksol.paisalo.jlgsourcing.fragments;

import android.content.Context;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListDocuments;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower_Table;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore_Table;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentKycSubmit.OnListFragmentKycScanInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentKycSubmit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKycSubmit extends Fragment implements AdapterView.OnItemClickListener {
    private List<DocumentStore> documentStores = new ArrayList<>();
    private Borrower borrower;


    private OnListFragmentKycScanInteractionListener mListener;
    private AdapterListDocuments adapterListDocuments;
    private ListView listView;
    //private Activity activity;
    private DocumentStore mDocumentStore;
    private long borrowerId;
    //private AsyncHttpClient client=new AsyncHttpClient();


    public FragmentKycSubmit() {
        // Required empty public constructor
    }

    public static FragmentKycSubmit newInstance(long borrowerId) {

        Log.e("BorrowerID123",borrowerId+"");
        FragmentKycSubmit fragment = new FragmentKycSubmit();
        Bundle args = new Bundle();
        args.putLong(Global.BORROWER_TAG, borrowerId);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity= getActivity();

        if (getArguments() != null) {
            borrowerId = getArguments().getLong(Global.BORROWER_TAG, 0);
            Log.e("BorrowerIDDDD",borrowerId+"");
            if (borrowerId > 0) {
                borrower = SQLite.select().from(Borrower.class).where(Borrower_Table.FiID.eq(borrowerId)).querySingle();
            }

        }
        adapterListDocuments = new AdapterListDocuments(getActivity(), R.layout.layout_item_loan_app_kyc_capture, documentStores);
        setHasOptionsMenu(true);
        /* client.setThreadPool(Executors.newSingleThreadExecutor());
        client.setTimeout(70000);
        WebOperations.setHttpHeadersJson(getContext(),client, true); */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_list_view, container, false);
        listView = (ListView) view.findViewById(R.id.lvList);
        listView.setAdapter(adapterListDocuments);
        listView.setOnItemClickListener(this);
        Log.e("KYCSUBMIT","DONE");
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
        documentStores.clear();

//        Log.e("DOCUMENTStoreChecking2",borrower.getPicture()+"");
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
        File file = new File(mDocumentStore.imagePath);
        //Log.d("Image Path", mDocumentStore.imagePath+ (file.exists()?"Exists":"does not exist")+ "  FileSize :" + file.length());

        /*if(mDocumentStore.fieldname.equals("documentpic")){
            uploadKycJson(mDocumentStore,view,client);
            //uploadKycJsonEntity(mDocumentStore,view,client);
        }else {*/


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
            Log.e("KycSubmitImagePath",mDocumentStore.imagePath+"");
            Toast.makeText(getContext(), mDocumentStore.imagePath+"", Toast.LENGTH_SHORT).show();
            //documentStore.imagePath = mDocumentStore.imagePath;
            //documentStore.imagePath = "file:" + mDocumentStore.imagePath;
            documentStore.imagePath = mDocumentStore.imagePath;
            uploadKycJson(documentStore, view);
        }else {
            Toast.makeText(getContext(), "ImagePath_Null", Toast.LENGTH_SHORT).show();
        }



        //}
    }


    public interface OnListFragmentKycScanInteractionListener {
        void onListFragmentKycScanInteraction(Borrower borrower);
    }


    private void uploadKycJson(final DocumentStore documentStore, final View view) {

        DataAsyncResponseHandler responseHandler = new DataAsyncResponseHandler(getContext(), "Loan Financing", "Uploading " + DocumentStore.getDocumentName(documentStore.checklistid)) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = new String(responseBody);
                Utils.showSnakbar(getActivity().findViewById(android.R.id.content).getRootView(), responseString);
                //if(responseString.equals("")) {
                view.setEnabled(false);
                view.setOnClickListener(null);
                view.setActivated(false);
                view.setClickable(false);
                documentStore.updateStatus = true;
               // documentStore.imageshow = "";
                documentStore.update();
                (new File(documentStore.imagePath)).delete();

                onResume();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
                view.setEnabled(true);
                view.setClickable(true);
                view.setActivated(true);
            }
        };

        String jsonString = WebOperations.convertToJson(documentStore.getDocumentDTO());
        //Log.d("Document Json",jsonString);
        String apiPath = documentStore.checklistid == 0 ? "/api/uploaddocs/savefipicjson" : "/api/uploaddocs/savefidocsjson";
        (new WebOperations()).postEntity(getContext(), BuildConfig.BASE_URL + apiPath, jsonString, responseHandler);
    }

    /*private void submitAllKyc(){
        for(int i=0)
        View view=        adapterListDocuments.
    }*/
    /*private void uploadKyc(final DocumentStore documentStore, final View view){

        DataAsyncResponseHandler responseHandler=new DataAsyncResponseHandler(getContext(),"Loan Financing","Uploading KYC Document") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString=new String(responseBody);
                Utils.showSnakbar(getView(),responseString);
                //if(responseString.equals("")) {
                    view.setEnabled(false);
                    view.setOnClickListener(null);
                    view.setActivated(false);
                    view.setClickable(false);
                    documentStore.updateStatus = true;
                    documentStore.update();
                    (new File(documentStore.imagePath)).delete();
                    onResume();
                //}

            }
        };
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams requestParams=documentStore.configureClient(getContext(),client);
        //Log.d("Url", IglPreferences.getPrefString(getContext(), SEILIGL.BASE_URL,"")+documentStore.apiRelativePath+ "  ");
        client.post(getContext(),IglPreferences.getPrefString(getContext(), SEILIGL.BASE_URL,"")+documentStore.apiRelativePath,requestParams,responseHandler);
    }*/
}
