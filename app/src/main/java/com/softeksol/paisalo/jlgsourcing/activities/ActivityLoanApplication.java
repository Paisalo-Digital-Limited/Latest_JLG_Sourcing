package com.softeksol.paisalo.jlgsourcing.activities;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterFragmentPager;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower_Table;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.softeksol.paisalo.jlgsourcing.fragments.AbsFragment;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentBorrowerAadhar;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentBorrowerFinance;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentBorrowerGuarantors;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentBorrowerPersonal;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentBorrowerPersonal_Additional;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentFamilyExpense;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentFamilyIncome;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentFamilyLoans;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentKycScanning;

import java.util.ArrayList;


public class ActivityLoanApplication extends AppCompatActivity implements
        FragmentBorrowerAadhar.OnFragmentBorrowerAadharInteractionListener,
        FragmentBorrowerPersonal.OnFragmentBorrowerPersonalInteractionListener,
        FragmentBorrowerPersonal_Additional.OnFragmentBorrowerPersonal_AddInteractionListener,
        FragmentBorrowerFinance.OnFragmentBorrowerFinanceInteractionListener,
        //FragmentBorrowerExtra.OnFragmentBorrowerExtraInteractionListener,
        FragmentBorrowerGuarantors.OnListFragmentBorrowerGuarantorsInteractionListener,
        FragmentKycScanning.OnListFragmentKycScanInteractionListener {
    TabLayout tabLayout;
    private long borrower_id;
    private Borrower borrower;
    private View.OnClickListener navOnClikListner;
    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link androidx.fragment.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link androidx.fragment.app.FragmentStatePagerAdapter}.
     */
    private AdapterFragmentPager mSectionsPagerAdapter;
    private ArrayList<AbsFragment> fragments;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_loan_application);
        setContentView(R.layout.layout_viewpager);

        borrower_id = getIntent().getLongExtra(Global.BORROWER_TAG, 0);
        refreshBorrower();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragments = new ArrayList<>();
            loadFragments();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new AdapterFragmentPager(getSupportFragmentManager(), fragments);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
        navOnClikListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnNavLeft:
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                        break;
                    case R.id.btnNavRight:
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        break;
                }
            }
        };
    }

    private void refreshBorrower() {
        borrower = SQLite.select().from(Borrower.class).where(Borrower_Table.FiID.eq(borrower_id)).querySingle();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loan_application, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_application) {
            borrower.delete();
            borrower = null;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public Borrower getBorrower() {
        return this.borrower;
    }

    @Override
    public void onListFragmentBorrowerGuarantorsInteraction(Guarantor guarantor) {

    }

    @Override
    public void onFragmentBorrowerPersonalInteraction(Borrower borrower) {

    }

    @Override
    public void onFragmentBorrowerAadharInteraction(Borrower borrower) {

    }

    @Override
    public void onFragmentBorrowerFinanceInteraction(Borrower borrower) {

    }

    @Override
    public void onListFragmentKycScanInteraction(Borrower borrower) {

    }

    private void loadFragments() {
        fragments.add(FragmentBorrowerAadhar.newInstance(borrower_id));
        fragments.add(FragmentBorrowerPersonal.newInstance(borrower_id));
        fragments.add(FragmentBorrowerPersonal_Additional.newInstance(borrower_id));
        fragments.add(FragmentBorrowerFinance.newInstance(borrower_id));
        //fragments.add(FragmentBorrowerExtra.newInstance(borrower_id));
        if (BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.groupfin")
                || BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.groupfin.coorigin")
                || BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.groupfin.sbicolending")
                || BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.paisalo.loan.app")) {
            fragments.add(FragmentFamilyIncome.newInstance());
            fragments.add(FragmentFamilyExpense.newInstance());
            fragments.add(FragmentFamilyLoans.newInstance());
            //fragments.add(FragmentHouseDetails.newInstance());
        }
        fragments.add(FragmentBorrowerGuarantors.newInstance());
        fragments.add(FragmentKycScanning.newInstance());
        //fragments.add();
    }

    @Override
    public void onBackPressed() {
        borrower.Oth_Prop_Det = null;
        borrower.save();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        refreshBorrower();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (borrower != null) {
            borrower.Oth_Prop_Det = null;
            borrower.save();
        }
    }

    public void setNavOnClikListner(View view) {
        view.setOnClickListener(navOnClikListner);
    }

}
