package com.softeksol.paisalo.jlgsourcing.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityFinancing;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterRecViewLoanApplication;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnListFragmentLoanAppSubmitInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLoanAppSubmitList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLoanAppSubmitList extends Fragment {
    private static final String ARG_BORROWER_FILTER = "BORROWER_FILTER";
    List<Borrower> borrowers = new ArrayList<Borrower>();
    private String mBorrowerFilter = "";
    private SQLCondition sqlCondition = null;
    private int mColumnCount = 1;
    private OnListFragmentLoanAppSubmitInteractionListener mListener;
    public AdapterRecViewLoanApplication loanApplicationRVA;

    private RecyclerView recyclerView;
    private Manager manager;

    public FragmentLoanAppSubmitList() {
        // Required empty public constructor
    }

    public static FragmentLoanAppSubmitList newInstance() {
        return new FragmentLoanAppSubmitList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBorrowerFilter = getArguments().getString(ARG_BORROWER_FILTER);
            sqlCondition = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Utils.setLayoutToRecyclerView(view, 1);
        recyclerView = (RecyclerView) view;
        loanApplicationRVA = new AdapterRecViewLoanApplication(borrowers, mListener);
        recyclerView.setAdapter(loanApplicationRVA);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentLoanAppSubmitInteractionListener) {
            mListener = (OnListFragmentLoanAppSubmitInteractionListener) context;
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
        //Log.d("Fragment", " OnResume");
        updateList();
    }

    private void updateList() {
        manager = ((ActivityFinancing) getActivity()).getManager();
        //TODO: To check for KYC scanned for the the borrower to get listed for the submittion
        borrowers = ((ActivityFinancing) getActivity()).getBorrowers();
        if (borrowers != null && loanApplicationRVA != null) {
            loanApplicationRVA.updateList(borrowers);
        }
        /*if(((ActivityFinancing)getActivity()).getPendingFis() !=null && adapterRecViewPendingFI!=null) {
            adapterRecViewPendingFI.updateList(((ActivityFinancing)getActivity()).getPendingFis());
        }*/
    }

    public interface OnListFragmentLoanAppSubmitInteractionListener {
        void onListFragmentLoanAppSubmitInteraction(Borrower borrower, int position);
    }
}
