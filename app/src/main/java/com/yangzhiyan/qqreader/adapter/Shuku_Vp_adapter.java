package com.yangzhiyan.qqreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by YZY on 2016/10/24.
 */

public class Shuku_Vp_adapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] tabs;

    public Shuku_Vp_adapter(FragmentManager fm, List<Fragment> fragmentList, String[] tabs) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position%tabs.length];
    }
}
