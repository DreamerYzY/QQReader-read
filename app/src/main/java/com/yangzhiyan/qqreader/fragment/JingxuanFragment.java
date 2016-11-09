package com.yangzhiyan.qqreader.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.adapter.JingxuanListviewAdapter;
import com.yangzhiyan.qqreader.bean.SinaNewsBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * A simple {@link Fragment} subclass.
 */
public class JingxuanFragment extends Fragment {
    private ListView lvnews;
    private JingxuanListviewAdapter adapter;
    private SinaNewsBean sinaNewsBean;


    public JingxuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jingxuan, container, false);
        lvnews = (ListView) view.findViewById(R.id.lvnews);
        RequestParams requestParams = new RequestParams("http://newsapi.sina.cn/?resource" +
                "=feed&channel=news_toutiao&pullDirection=down&pullTimes=1&replacedFlag=0&lo" +
                "adingAdTimestamp=0&behavior=auto&lastTimeStamp=1477895579&listCount=21&upTimes=0" +
                "&downTimes=0&deviceId=c113574db2d2cca6&from=6054195012&weiboUid=&weiboSuid=&imei=" +
                "860162035035754&wm=b207&chwm=14010_0001&oldChwm=12040_0001&osVersion=5.0.2&connect" +
                "ionType=2&resolution=1080x1920&city=CHXX0019&deviceModel=Xiaomi__Xiaomi__Redmi+" +
                "Note+3&location=38.887956%2C121.538739&link=&mac=10%3A2a%3Ab3%3Ac4%3Afe%3Afb" +
                "&ua=Xiaomi-Redmi+Note+3__sinanews__5.4.1__android__5.0.2&cmd_mac=10%3A2a%3Ab3" +
                "%3Ac4%3Afe%3Afb%0A&urlSign=bf17fefe52&rand=802");
        x.http().get(requestParams,
                new Callback.CacheCallback<String>() {
                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }

                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        sinaNewsBean = gson.fromJson(result,SinaNewsBean.class);
                        adapter = new JingxuanListviewAdapter(getContext(),sinaNewsBean);
                        lvnews.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
        return view;
    }

}
