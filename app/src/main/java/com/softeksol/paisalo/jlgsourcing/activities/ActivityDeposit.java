package com.softeksol.paisalo.jlgsourcing.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterFragmentPager;
import com.softeksol.paisalo.jlgsourcing.fragments.AbsFragment;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentBatchCreation;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentDeposit;

import java.util.ArrayList;

public class ActivityDeposit extends AppCompatActivity implements FragmentBatchCreation.OnFragmentBatchCreationInteractionListener {
    ArrayList<AbsFragment> absFragments;
    private ViewPager mViewPager;
    private FragmentDeposit bankDepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewpager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        absFragments = new ArrayList<>();
        absFragments.add(FragmentDeposit.newInstance());
        absFragments.add(FragmentBatchCreation.newInstance());

        AdapterFragmentPager fragmentPagerAdapter = new AdapterFragmentPager(getSupportFragmentManager(), absFragments);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
    }

    @Override
    public void OnFragmentBatchCreationInteraction() {
        ((FragmentDeposit) absFragments.get(0)).refreshData();
        mViewPager.setCurrentItem(0);
    }
}
