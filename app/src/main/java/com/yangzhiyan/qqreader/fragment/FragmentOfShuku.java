package com.yangzhiyan.qqreader.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.activity.ShukuLvActivity;
import com.yangzhiyan.qqreader.adapter.shuku_chuban_gv_adapter;
import com.yangzhiyan.qqreader.adapter.shuku_chuban_lv_adapter;
import com.yangzhiyan.qqreader.adapter.shuku_yuanchuang_footlv_adapter;
import com.yangzhiyan.qqreader.adapter.shuku_yuanchuang_gv_adapter;
import com.yangzhiyan.qqreader.adapter.shuku_yuanchuang_lv_adapter;
import com.yangzhiyan.qqreader.asynctask.ChubanAsyncTask;
import com.yangzhiyan.qqreader.asynctask.YuanchuangAsyncTask;
import com.yangzhiyan.qqreader.bean.ChuBanClass;
import com.yangzhiyan.qqreader.bean.YuanChuangClass;
import com.yangzhiyan.qqreader.interfaces.ChubanResultCallback;
import com.yangzhiyan.qqreader.interfaces.YuanchuangResultCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOfShuku extends Fragment implements ChubanResultCallback,YuanchuangResultCallback {
    private TextView tvtitle;
    private TextView tvcount;
    private TextView tvnewcount;
    private GridView shuku_gv;
    private ListView shuku_lv;
    private ChuBanClass realchuBanClass;
    private String chubanurl;
    private shuku_chuban_gv_adapter gv_adapter;
    private shuku_chuban_lv_adapter lv_adapter;

    private YuanChuangClass realyuanchuangclass;
    private String yuanchuangurl;
    private shuku_yuanchuang_gv_adapter gv_adapter1;
    private shuku_yuanchuang_lv_adapter lv_adapter1;

    private TextView tvfoottitle;
    private ListView tvfootlv;
    private shuku_yuanchuang_footlv_adapter footlv_adapter;

    private int id;

    private View view;
    private View footerview;

    public FragmentOfShuku() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_of_shuku, container, false);
        View headerview = getActivity().getLayoutInflater().inflate(R.layout.shuku_lv_header_layout,null);
        footerview = getActivity().getLayoutInflater().inflate(R.layout.shuku_lv_foot_layout,null);
        tvfoottitle = (TextView) footerview.findViewById(R.id.tvfoottitle);
        tvfootlv = (ListView) footerview.findViewById(R.id.tvfootlv);

        shuku_lv = (ListView) view.findViewById(R.id.lvcategory);
        shuku_lv.addHeaderView(headerview);
        tvtitle = (TextView) headerview.findViewById(R.id.tvtitle);
        tvcount = (TextView) headerview.findViewById(R.id.tvcount);
        tvnewcount = (TextView) headerview.findViewById(R.id.tvnewcount);
        shuku_gv = (GridView) headerview.findViewById(R.id.gvcategory);
        id = getArguments().getInt("id");
        switch (id){
            case 3:
                chubanurl = getArguments().getString("chubanurl");
                new ChubanAsyncTask(this).execute(chubanurl);
                break;
            case 1:
                yuanchuangurl = getArguments().getString("yuanchuangurl");
                new YuanchuangAsyncTask(this).execute(yuanchuangurl);
                break;
        }
        return view;
    }

    @Override
    public void CallBack(ChuBanClass chuBanClass) {
        realchuBanClass = chuBanClass;
        tvtitle.setText("出版分类共");
        tvcount.setText(realchuBanClass.count.bookCount+"");
        tvnewcount.setText(realchuBanClass.count.newBookCount+"");
        gv_adapter = new shuku_chuban_gv_adapter(realchuBanClass,getContext());
        lv_adapter = new shuku_chuban_lv_adapter(realchuBanClass,getContext());
        shuku_gv.setAdapter(gv_adapter);
        shuku_lv.setAdapter(lv_adapter);
        shuku_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShukuLvActivity.class);
                intent.putExtra("actionId",realchuBanClass.publishCategoryList.get(position-1).actionId);
                startActivity(intent);
            }
        });

    }

    @Override
    public void CallBack2(YuanChuangClass yuanChuangClass) {

        realyuanchuangclass = yuanChuangClass;

        tvfoottitle.setText(realyuanchuangclass.line.title);
        footlv_adapter = new shuku_yuanchuang_footlv_adapter(realyuanchuangclass,getContext());
        tvfootlv.setAdapter(footlv_adapter);

        shuku_lv.addFooterView(footerview);

        tvtitle.setText("原创分类共");
        tvcount.setText(realyuanchuangclass.count.bookCount+"");
        tvnewcount.setText(realyuanchuangclass.count.newBookCount+"");


        gv_adapter1 = new shuku_yuanchuang_gv_adapter(realyuanchuangclass,getContext());
        shuku_gv.setAdapter(gv_adapter1);

        lv_adapter1 = new shuku_yuanchuang_lv_adapter(realyuanchuangclass,getContext());
        shuku_lv.setAdapter(lv_adapter1);

        shuku_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShukuLvActivity.class);
                intent.putExtra("actionId",realyuanchuangclass.boyCategoryList.get(position-1).actionId);
                startActivity(intent);
            }
        });
        tvfootlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShukuLvActivity.class);
                intent.putExtra("actionId",realyuanchuangclass.girlCategoryList.get(position).actionId);
                startActivity(intent);
            }
        });
    }
}
