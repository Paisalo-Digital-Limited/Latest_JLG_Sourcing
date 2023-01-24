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
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterRecViewPendingFI;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.PendingFi;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLoanAppList.OnListFragmentLoanAppInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLoanAppList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLoanAppList extends Fragment {
    private static final String ARG_BORROWER_FILTER = "BORROWER_FILTER";
    List<Borrower> borrowers = new ArrayList<Borrower>();
    private String mBorrowerFilter = "";
    private SQLCondition sqlCondition = null;
    private int mColumnCount = 1;
    private OnListFragmentLoanAppInteractionListener mListener;
    public AdapterRecViewLoanApplication loanApplicationRVA;
    public AdapterRecViewPendingFI adapterRecViewPendingFI;

    private RecyclerView recyclerView;
    private Manager manager;

    public FragmentLoanAppList() {
        // Required empty public constructor
    }

    public static FragmentLoanAppList newInstance(String mBorrowerFilter) {
        FragmentLoanAppList fragment = new FragmentLoanAppList();
        Bundle args = new Bundle();
        args.putString(ARG_BORROWER_FILTER, mBorrowerFilter);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentLoanAppList newInstance() {
        return new FragmentLoanAppList();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Utils.setLayoutToRecyclerView(view, 1);
        recyclerView = (RecyclerView) view;
        //loanApplicationRVA=new AdapterRecViewLoanApplication(borrowers, mListener);

        //adapterRecViewPendingFI=new AdapterRecViewPendingFI(((ActivityFinancing)getActivity()).getBorrowers(),mListener);
        adapterRecViewPendingFI = new AdapterRecViewPendingFI(((ActivityFinancing) requireActivity()).getPendingFis(), mListener,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapterRecViewPendingFI);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentLoanAppInteractionListener) {
            mListener = (OnListFragmentLoanAppInteractionListener) context;
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
        updateList();
    }

    private void updateList() {
        manager = ((ActivityFinancing) getActivity()).getManager();
        if (((ActivityFinancing) getActivity()).getPendingFis() != null && adapterRecViewPendingFI != null) {
            adapterRecViewPendingFI.updateList(((ActivityFinancing) getActivity()).getPendingFis());
        }

    }

    public interface OnListFragmentLoanAppInteractionListener {
        void onListFragmentLoanAppInteraction(Borrower borrower, int position);

        void onListFragmentLoanAppInteraction(PendingFi pendingFi, int position);
    }
}
