package com.softeksol.paisalo.jlgsourcing.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.softeksol.paisalo.jlgsourcing.fragments.AbsCollectionFragment;
import com.softeksol.paisalo.jlgsourcing.fragments.AbsFragment;

import java.util.ArrayList;
import java.util.List;

public class AdapterCollectionFragmentPager extends FragmentPagerAdapter {
    private List<AbsCollectionFragment> fragments;
    private List<String> titles;

    public AdapterCollectionFragmentPager(FragmentManager fm, ArrayList<AbsCollectionFragment> fragmentArrayList) {
        super(fm);
        this.fragments = fragmentArrayList;
        titles = new ArrayList<>();
        updateTitleList(this.fragments);
    }

    public AdapterCollectionFragmentPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(AbsCollectionFragment fragment, int position) {
        fragments.add(position, fragment);
        titles.add(position, fragment.getName());
    }

    public void removeFragment(int position) {
        fragments.remove(position);
        titles.remove(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    private void updateTitleList(List<AbsCollectionFragment> fragmentArrayList) {
        for (AbsFragment frag : fragmentArrayList) {
            titles.add(frag.getName());
        }
    }

    public void setFragments(ArrayList<AbsCollectionFragment> fragments) {
        clearFragments();
        this.fragments.addAll(fragments);
        updateTitleList(this.fragments);
    }

    public void clearFragments() {
        this.fragments.clear();
        this.titles.clear();
    }

}
