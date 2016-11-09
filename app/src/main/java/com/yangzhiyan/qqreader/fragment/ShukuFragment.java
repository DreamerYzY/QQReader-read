package com.yangzhiyan.qqreader.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.adapter.Shuku_Vp_adapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShukuFragment extends Fragment {
    private TabLayout shuku_tabs;
    private String[] tabs = {"出版图书","原创文学"};
    private ViewPager shuku_vp;
    private List<Fragment> fragmentList;
    private Shuku_Vp_adapter adapter;
    private String chubanurl = "http://android.reader.qq.com/v6_2/queryOperation?categoryFlag=3";
    private String yuanchuangurl="http://android.reader.qq.com/v6_2/queryOperation?categoryFlag=1";


    public ShukuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_shuku, container, false);
        fragmentList = new ArrayList<>();
        shuku_tabs = (TabLayout) view.findViewById(R.id.shuku_tabs);
        for (int i =0;i<tabs.length;i++){
            shuku_tabs.addTab(shuku_tabs.newTab().setText(tabs[i]));
        }
        shuku_vp = (ViewPager) view.findViewById(R.id.shuku_vp);

        FragmentOfShuku chubanfragment = new FragmentOfShuku();
        FragmentOfShuku yuanchuangfragment = new FragmentOfShuku();

        Bundle bundle = new Bundle();
        bundle.putString("chubanurl",chubanurl);
        bundle.putInt("id",3);
        chubanfragment.setArguments(bundle);
        fragmentList.add(chubanfragment);

        Bundle bundle1 = new Bundle();
        bundle1.putString("yuanchuangurl",yuanchuangurl);
        bundle1.putInt("id",1);
        yuanchuangfragment.setArguments(bundle1);
        fragmentList.add(yuanchuangfragment);
        adapter = new Shuku_Vp_adapter(getChildFragmentManager(),fragmentList,tabs);
        shuku_vp.setAdapter(adapter);
        shuku_tabs.setupWithViewPager(shuku_vp);
        return  view;
    }

}
