package com.softeksol.paisalo.jlgsourcing.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityFinancing;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListBorrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower_Table;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnSubmittedApplicationsFragmentInteractionListener}
 * interface.
 */
public class FragmentSubmittedApplications extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnSubmittedApplicationsFragmentInteractionListener mListener;
    private ListView listView;
    private List<Borrower> borrowers = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentSubmittedApplications() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentSubmittedApplications newInstance() {
        return new FragmentSubmittedApplications();
        /*FragmentSubmittedApplications fragment = new FragmentSubmittedApplications();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_view, container, false);
        listView = (ListView) view.findViewById(R.id.lvList);
        AdapterListBorrower adapterListBorrower = new AdapterListBorrower(getContext(), R.layout.layout_item_loan_app_submitted, borrowers);
        listView.setAdapter(adapterListBorrower);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSubmittedApplicationsFragmentInteractionListener) {
            mListener = (OnSubmittedApplicationsFragmentInteractionListener) context;
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
        Manager manager = ((ActivityFinancing) getActivity()).getManager();
        AdapterListBorrower adapterListBorrower = (AdapterListBorrower) listView.getAdapter();
        borrowers = SQLite.select().from(Borrower.class)
                .where(Borrower_Table.Code.greaterThan(0))
                .and(Borrower_Table.Creator.eq(manager.Creator))
                .and(Borrower_Table.GroupCode.eq(manager.FOCode))
                .queryList();
        adapterListBorrower.clear();
        adapterListBorrower.addAll(borrowers);
        adapterListBorrower.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSubmittedApplicationsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSubmittedApplicationsFragmentInteraction(Borrower borrower);
    }
}
