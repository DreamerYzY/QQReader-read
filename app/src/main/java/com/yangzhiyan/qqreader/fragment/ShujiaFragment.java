package com.yangzhiyan.qqreader.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.adapter.ShujiaListviewAdapter;
import com.yangzhiyan.qqreader.utils.DBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShujiaFragment extends Fragment {

    private LinearLayout linearLayout;
    private SQLiteDatabase sd;
    private DBHelper helper;
    private List<Map<String, String>> booklist;
    private ShujiaListviewAdapter adapter;
    private ListView shujialistView;
    private ScrollView myscrollview;


    public ShujiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shujia, container, false);
        myscrollview = (ScrollView) view.findViewById(R.id.myscrollview);
        myscrollview.fullScroll(View.FOCUS_UP);
        helper = new DBHelper(getContext());
        linearLayout = (LinearLayout) view.findViewById(R.id.myshujia);
        View headview = getActivity().getLayoutInflater().inflate(R.layout.myshujia_header, null);
        headview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                400));
        linearLayout.addView(headview);
        View listview = getActivity().getLayoutInflater().inflate(R.layout.myshujia_listview, null);
        listview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(listview);
        shujialistView = (ListView) listview.findViewById(R.id.shujia_listview);
        booklist = new ArrayList<>();
        sd = helper.getReadableDatabase();
        Cursor cursor = sd.query("mybook", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put("imgurl", cursor.getString(cursor.getColumnIndex("bookimgurl")));
            map.put("bookname", cursor.getString(cursor.getColumnIndex("bookname")));
            map.put("state", cursor.getString(cursor.getColumnIndex("state")));
            map.put("updateto", cursor.getString(cursor.getColumnIndex("updateto")));
            map.put("bid", cursor.getString(cursor.getColumnIndex("bid")));
            booklist.add(map);
            Collections.reverse(booklist);
        }
        cursor.close();
        adapter = new ShujiaListviewAdapter(getContext(), booklist);
        shujialistView.setAdapter(adapter);
        shujialistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("确定从书架删除本书？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sd = helper.getReadableDatabase();
                                int a = sd.delete("mybook", "bid=?", new String[]{booklist.
                                        get(position).get("bid")});
                                if (a > 0) {
                                    booklist.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "删除成功",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
                return true;
            }
        });
        return view;
    }
}
