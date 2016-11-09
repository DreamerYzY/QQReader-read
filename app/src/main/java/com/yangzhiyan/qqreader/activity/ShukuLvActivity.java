package com.yangzhiyan.qqreader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.adapter.ChoiceGridviewAdapter;
import com.yangzhiyan.qqreader.adapter.FilterListviewAdapter;
import com.yangzhiyan.qqreader.adapter.ListDropDownAdapter;
import com.yangzhiyan.qqreader.adapter.Shuku_lv_info_adapter;
import com.yangzhiyan.qqreader.bean.ShukuLvInfo;
import com.yangzhiyan.qqreader.utils.ConverUtility;
import com.yyydjk.library.DropDownMenu;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShukuLvActivity extends AppCompatActivity {
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.tvsubject) private TextView tvsubject;
    @ViewInject(R.id.shuku_dropmenu) private DropDownMenu shuku_dropmenu;

    private int pagestamp;
    private String selectedActionId;
    private int actionId;
    private String actionTag;
    private String actionTag_1;
    private String actionTag_2;
    private String actionTag_3;
    private String actionTag_4;
    private String actionTag_5;
    private String actionTag_6;

    private List<Map<String,String>> data_SubActionIds;
    private List<Map<String, String>> data_ActionTag_1;
    private List<Map<String, String>> data_ActionTag_2;
    private List<Map<String, String>> data_ActionTag_3;
    private List<Map<String, String>> data_ActionTag_4;
    private List<Map<String, String>> data_ActionTag_5;
    private List<Map<String, String>> data_ActionTag_6;

    private final static String KEY_TEXT = "Text";
    private final static String KEY_VALUE = "Value";


    private String shuku_lv_url = "http://android.reader.qq.com/v6_2/listDispatch?action=categoryV3";


    private String[] tabs = {"按人气","筛选"};
    private List<View> popviews = new ArrayList<>();
    private String[] renqis = {"按人气","按更新","按字数","按收藏","最畅销"};

    private Shuku_lv_info_adapter adapter;
    private ShukuLvInfo shukuLvInfo;

    private List<ShukuLvInfo.BookListBean> bookListBeen;

    private View actionTag6View;
    private View filterView;
    private View contentView;

    private ListDropDownAdapter actionTag6Adapter;
    private ChoiceGridviewAdapter[] adapters;
    private FilterListviewAdapter listviewAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuku_lv);

        x.view().inject(this);

        initParameter();
        initActionTagData();
        Http();

    }
    private final static String ACTION_TAG_2_全部 = "-1";
    private final static String ACTION_TAG_2_连载 = "0";
    private final static String ACTION_TAG_2_完结 = "1";
    private final static String ACTION_TAG_2_节选 = "2";

    private final static String ACTION_TAG_3_全部 = "-1";
    private final static String ACTION_TAG_3_免费 = "0";
    private final static String ACTION_TAG_3_包月 = "1";
    private final static String ACTION_TAG_3_收费 = "6";

    private final static String ACTION_TAG_4_全部 = "-1";
    private final static String ACTION_TAG_4_3天内 = "0";
    private final static String ACTION_TAG_4_7天内 = "1";
    private final static String ACTION_TAG_4_15天内 = "2";
    private final static String ACTION_TAG_4_1月内 = "3";

    private final static String ACTION_TAG_5_全部 = "-1";
    private final static String ACTION_TAG_5_20万字以内 = "11";
    private final static String ACTION_TAG_5_20到30万字 = "12";
    private final static String ACTION_TAG_5_30到50万字 = "3";
    private final static String ACTION_TAG_5_50到100万字 = "24";
    private final static String ACTION_TAG_5_100到20万字 = "25";
    private final static String ACTION_TAG_5_200万字以上 = "26";

    private final static String ACTION_TAG_6_按人气 = "6";
    private final static String ACTION_TAG_6_按更新 = "2";
    private final static String ACTION_TAG_6_按字数 = "9";
    private final static String ACTION_TAG_6_按收藏 = "3";
    private final static String ACTION_TAG_6_最畅销 = "10";


    private void initParameter() {
        Intent intent = getIntent();
        actionId = intent.getIntExtra("actionId", 0);
        selectedActionId = String.valueOf(actionId);

        pagestamp = 1;
        actionTag_1 = "";
        actionTag_2 = ACTION_TAG_2_全部;
        actionTag_3 = ACTION_TAG_3_全部;
        actionTag_4 = ACTION_TAG_4_全部;
        actionTag_5 = ACTION_TAG_5_全部;
        actionTag_6 = ACTION_TAG_6_按人气;

        buildActionTag();
    }

    private void buildActionTag() {
        actionTag = actionTag_1 + "," +
                actionTag_2 + "," +
                actionTag_3 + "," +
                actionTag_4 + "," +
                actionTag_5 + "," +
                actionTag_6;
    }

    private void initActionTagData(){

        data_SubActionIds = new ArrayList<>();
        data_ActionTag_1 = new ArrayList<>();

        data_ActionTag_2 = new ArrayList<>();
        String[] actionTag2Texts = new String[]{"全部","连载","完结","节选"};
        String[] actionTag2Values = new String[]{"-1","0","1","2"};
        for (int i=0;i<actionTag2Texts.length;i++){
            Map<String,String> map = new HashMap<>();
            map.put(KEY_TEXT,actionTag2Texts[i]);
            map.put(KEY_VALUE,actionTag2Values[i]);
            data_ActionTag_2.add(map);
        }

        data_ActionTag_3 = new ArrayList<>();
        String[] actionTag3Texts = new String[]{"全部","免费","包月","收费"};
        String[] actionTag3Values = new String[]{"-1","0","1","6"};
        for (int i=0;i<actionTag3Texts.length;i++){
            Map<String,String> map = new HashMap<>();
            map.put(KEY_TEXT,actionTag3Texts[i]);
            map.put(KEY_VALUE,actionTag3Values[i]);
            data_ActionTag_3.add(map);
        }

        data_ActionTag_4 = new ArrayList<Map<String, String>>();
        String[] actionTag4Texts = new String[]{"全部","3天内","7天内","15天内","1月内"};
        String[] actionTag4Values = new String[]{"-1","0","1","2","3"};
        for (int i=0; i<actionTag4Texts.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(KEY_TEXT, actionTag4Texts[i]);
            map.put(KEY_VALUE, actionTag4Values[i]);
            data_ActionTag_4.add(map);
        }

        data_ActionTag_5 = new ArrayList<Map<String, String>>();
        String[] actionTag5Texts = new String[]{"全部","20万字以内","20到30万字","30到50万字","50到100万字","100到20万字","200万字以上"};
        String[] actionTag5Values = new String[]{"-1","11","12","3","24","25","26"};
        for (int i=0; i<actionTag5Texts.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(KEY_TEXT, actionTag5Texts[i]);
            map.put(KEY_VALUE, actionTag5Values[i]);
            data_ActionTag_5.add(map);
        }

        data_ActionTag_6 = new ArrayList<Map<String, String>>();
        String[] actionTag6Texts = new String[]{"按人气","按更新","按字数","按收藏","最畅销"};
        String[] actionTag6Values = new String[]{"6","2","9","3","10"};
        for (int i=0; i<actionTag6Texts.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(KEY_TEXT, actionTag6Texts[i]);
            map.put(KEY_VALUE, actionTag6Values[i]);
            data_ActionTag_6.add(map);
        }

    }

    private void initDropDownMenu(){
        initActionTag6FilterControl();
        initActionTag1to5FilterControl();

        contentView = LayoutInflater.from(this).inflate(
                R.layout.listview_book_list,null
        );
        pullToRefreshListView = (PullToRefreshListView) contentView.findViewById(R.id.booklv);
        String[] tabTitles = new String[]{"按人气","筛选"};
        View[] tabViews = new View[]{actionTag6View,filterView};
        shuku_dropmenu.setDropDownMenu(
                Arrays.asList(tabTitles),Arrays.asList(tabViews),contentView
        );
    }
    private void initActionTag6FilterControl(){
        actionTag6View = LayoutInflater.from(this).inflate(R.layout.view_booklist_actiontag6_layout
                ,null);
        ListView listView = (ListView) actionTag6View.findViewById(R.id.listview);
        actionTag6Adapter = new ListDropDownAdapter(this,data_ActionTag_6);
        listView.setAdapter(actionTag6Adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionTag6Adapter.setCheckItem(position);
                shuku_dropmenu.setTabText(data_ActionTag_6.get(position).get(KEY_TEXT));
                shuku_dropmenu.closeMenu();

                actionTag_6 = data_ActionTag_6.get(position).get(KEY_VALUE);
                buildActionTag();
                bookListBeen.clear();
                Http();
            }
        });
    }
    private void initActionTag1to5FilterControl(){
        filterView = LayoutInflater.from(this).inflate(R.layout.view_booklist_filterview_layout
        ,null);
        ListView listView = (ListView) filterView.findViewById(R.id.listView);
        String[] filterGroupTitle = {"分类","标签","状态","价格","更新时间","字数"};

        adapters = new ChoiceGridviewAdapter[6];

        adapters[0] = new ChoiceGridviewAdapter(this,data_ActionTag_1,true);
        adapters[1] = new ChoiceGridviewAdapter(this,data_SubActionIds,true);
        adapters[2] = new ChoiceGridviewAdapter(this,data_ActionTag_2,false);
        adapters[3] = new ChoiceGridviewAdapter(this,data_ActionTag_3,false);
        adapters[4] = new ChoiceGridviewAdapter(this,data_ActionTag_4,false);
        adapters[5] = new ChoiceGridviewAdapter(this,data_ActionTag_5,false);

        listviewAdapter = new FilterListviewAdapter(
                this,Arrays.asList(filterGroupTitle),Arrays.asList(adapters)
        );
        listView.setAdapter(listviewAdapter);

        Button btok = (Button) filterView.findViewById(R.id.btok);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuku_dropmenu.closeMenu();

                changeActionTag();

                for (ChoiceGridviewAdapter adp:adapters){
                    adp.clearAllChoice();
                }
            }
        });


    }
    private void changeActionTag(){
        List<Integer> selectedSubActionIds = adapters[0].getSelectedIndexes();
        List<String> subActionIds = new ArrayList<>();
        for (Integer index:selectedSubActionIds){
            subActionIds.add(data_SubActionIds.get(index).get(KEY_VALUE));
        }
        if (subActionIds.size()==0){
            selectedActionId = String.valueOf(actionId);
        }
        else {
            selectedActionId = ConverUtility.join(subActionIds,",");
        }
        List<Integer> selectedTag1Indexes = adapters[1].getSelectedIndexes();
        List<String> tag1Collection = new ArrayList<>();
        for (Integer index:selectedTag1Indexes){
            tag1Collection.add(data_ActionTag_1.get(index).get(KEY_VALUE));
        }
        actionTag_1 = ConverUtility.join(tag1Collection,":");

        actionTag_2 = data_ActionTag_2.get(adapters[2].getSelectedIndex()).get(KEY_VALUE);
        actionTag_3 = data_ActionTag_3.get(adapters[3].getSelectedIndex()).get(KEY_VALUE);
        actionTag_4 = data_ActionTag_4.get(adapters[4].getSelectedIndex()).get(KEY_VALUE);
        actionTag_5 = data_ActionTag_5.get(adapters[5].getSelectedIndex()).get(KEY_VALUE);

        buildActionTag();
        bookListBeen.clear();
        Http();
    }
    private void Http(){
        RequestParams request = new RequestParams(shuku_lv_url);
        request.addQueryStringParameter("actionTag",actionTag);
        request.addQueryStringParameter("actionId",actionId+"");
        request.addQueryStringParameter("pagestamp",pagestamp+"");
        x.http().get(request,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        shukuLvInfo = gson.fromJson(result,ShukuLvInfo.class);
                        getData(shukuLvInfo);

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

    }
    private void init(){
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagestamp = shukuLvInfo.pagestamp;
                Http();

            }
        });
        bookListBeen = new ArrayList<>();
        adapter = new Shuku_lv_info_adapter(ShukuLvActivity.this,bookListBeen);
        pullToRefreshListView.setAdapter(adapter);

    }

    private void getData(ShukuLvInfo shukuLvInfos){
        shukuLvInfo = shukuLvInfos;
        if (data_ActionTag_1.size()==0 || data_SubActionIds.size() == 0){
            for (ShukuLvInfo.InfoBean.Catel3Bean tag : shukuLvInfo.info.catel3){
                Map<String,String> map = new HashMap<String, String>();
                map.put(KEY_TEXT,tag.keyword);
                map.put(KEY_VALUE,String.valueOf(tag.id));
                data_ActionTag_1.add(map);
            }
            for (ShukuLvInfo.InfoBean.TagBean tag : shukuLvInfo.info.tag){
                Map<String,String> map = new HashMap<String, String>();
                map.put(KEY_TEXT,tag.keyword);
                map.put(KEY_VALUE,String.valueOf(tag.id));
                data_SubActionIds.add(map);
            }
            initDropDownMenu();
            init();
        }
        bookListBeen.addAll(shukuLvInfo.bookList);
        tvsubject.setText(shukuLvInfo.info.pagetitle);
        adapter.notifyDataSetChanged();

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(ShukuLvActivity.this,BookDetailedInfoActivity.class);
                intent1.putExtra("bid",bookListBeen.get(position-1).bid);
                startActivity(intent1);
            }
        });
        if (pullToRefreshListView.isRefreshing()){
            pullToRefreshListView.onRefreshComplete();
        }
        if (shukuLvInfo.pagestamp!=0){
            pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        }else {
            pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        }

    }
    @Event(R.id.ivreturn)
    private void iv_click(View v){
        this.finish();
    }

    @Override
    public void onBackPressed() {
        if (shuku_dropmenu.isShowing()){
            shuku_dropmenu.closeMenu();

            for (ChoiceGridviewAdapter adp:adapters){
                adp.clearAllChoice();
            }
        }else {
            super.onBackPressed();
        }
    }
}
