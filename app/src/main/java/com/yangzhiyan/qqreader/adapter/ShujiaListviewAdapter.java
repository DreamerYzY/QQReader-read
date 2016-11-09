package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by YZY on 2016/10/30.
 */

public class ShujiaListviewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> booklist;

    public ShujiaListviewAdapter(Context context, List<Map<String, String>> booklist) {
        this.context = context;
        this.booklist = booklist;
    }

    @Override
    public int getCount() {
        return booklist.size();
    }

    @Override
    public Object getItem(int position) {
        return booklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.shujia_listview_item,null);
            holder = new ViewHolder();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String,String> map = booklist.get(position);
        String imgurl = map.get("imgurl");
        Picasso.with(context).load(imgurl).into(holder.bookicon);
        holder.tvbooktitle.setText(map.get("bookname"));
        holder.tvbookstate.setText(map.get("state"));
        holder.tvupdate.setText(map.get("updateto"));
        return convertView;
    }
    class ViewHolder{
        @ViewInject(R.id.bookicon)
        ImageView bookicon;
        @ViewInject(R.id.tvbooktitle)
        TextView tvbooktitle;
        @ViewInject(R.id.tvbookstate)
        TextView tvbookstate;
        @ViewInject(R.id.tvupdate)
        TextView tvupdate;
    }
}
