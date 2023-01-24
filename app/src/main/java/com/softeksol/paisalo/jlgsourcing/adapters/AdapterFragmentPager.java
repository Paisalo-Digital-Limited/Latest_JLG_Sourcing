package com.softeksol.paisalo.jlgsourcing.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.softeksol.paisalo.jlgsourcing.fragments.AbsFragment;

import java.util.ArrayList;
import java.util.List;

public class AdapterFragmentPager extends FragmentPagerAdapter {
    private List<AbsFragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public AdapterFragmentPager(FragmentManager fm, ArrayList<AbsFragment> fragmentArrayList) {
        super(fm);
        this.fragments.addAll(fragmentArrayList);
        updateTitleList(this.fragments);
    }

    public AdapterFragmentPager(FragmentManager fm) {
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

    public void addFragment(AbsFragment fragment, int position) {
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

    private void updateTitleList(List<AbsFragment> fragmentArrayList) {
        for (AbsFragment frag : fragmentArrayList) {
            titles.add(frag.getName());
        }
    }

    public void setFragments(ArrayList<AbsFragment> fragments) {
        clearFragments();
        this.fragments.addAll(fragments);
        updateTitleList(this.fragments);
    }

    public void clearFragments() {
        this.fragments.clear();
        this.titles.clear();
    }
}
