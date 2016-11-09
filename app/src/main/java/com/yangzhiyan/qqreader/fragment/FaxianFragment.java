package com.yangzhiyan.qqreader.fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.utils.DBHelper;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaxianFragment extends Fragment {
    private RecyclerView recyclerView;
    private DBHelper helper;
    private SQLiteDatabase sd;
    private List<Map<String,String>> booklist;
    private Button btchange;
    private boolean flag;
    private MyRecycleviewAdapter adapter;


    public FaxianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faxian, container, false);
        helper = new DBHelper(getContext());
        sd = helper.getReadableDatabase();
        btchange = (Button) view.findViewById(R.id.btchange);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        Cursor cursor = sd.query("mybook",null,null,null,null,null,null);
        booklist = new ArrayList<>();
        while (cursor.moveToNext()){
            Map<String,String> map = new HashMap<>();
            map.put("imgurl", cursor.getString(cursor.getColumnIndex("bookimgurl")));
            map.put("bookname", cursor.getString(cursor.getColumnIndex("bookname")));
            map.put("updateto", cursor.getString(cursor.getColumnIndex("updateto")));
            booklist.add(map);
        }
        cursor.close();
        listview();
        btchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false){
                    gridview();
                }else {
                    listview();
                }
                flag = !flag;
            }
        });
        return view;
    }
    private void gridview(){
        GridLayoutManager layoutManager = new GridLayoutManager(
                getContext(),2,LinearLayoutManager.HORIZONTAL,false
        );
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecycleviewAdapter(getContext(),booklist);
        recyclerView.setAdapter(adapter);
    }

    private void listview(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.VERTICAL,false
        );
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecycleviewAdapter(getContext(),booklist);
        recyclerView.setAdapter(adapter);
    }

    private class MyRecycleviewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder>{
        private Context context;
        private List<Map<String,String>> booklist;

        public MyRecycleviewAdapter(Context context, List<Map<String, String>> booklist) {
            this.context = context;
            this.booklist = booklist;
        }

        @Override
        public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.faxian_recyclerview_gridview_layout,null);
            MyRecyclerViewHolder viewHolder = new MyRecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
            Map<String,String> map = booklist.get(position);
            String imgurl = map.get("imgurl");
            Picasso.with(context).load(imgurl).into(holder.ivcover);
            holder.tvtitle.setText(map.get("bookname"));
            holder.tvupdateto.setText(map.get("updateto"));
        }

        @Override
        public int getItemCount() {
            return booklist.size();
        }
    }
    private class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.ivcover)
        ImageView ivcover;
        @ViewInject(R.id.tvtitle)
        TextView tvtitle;
        @ViewInject(R.id.tvupdateto)
        TextView tvupdateto;

        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }
    }

}
