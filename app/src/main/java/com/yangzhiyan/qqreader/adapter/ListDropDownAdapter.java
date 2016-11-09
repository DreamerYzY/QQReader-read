package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yangzhiyan.qqreader.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by YZY on 2016/10/27.
 */

public class ListDropDownAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position){
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public ListDropDownAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView!=null){
            holder = (ViewHolder) convertView.getTag();
        }else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_default_drop_down,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        fillValue(position,holder);
        return convertView;
    }

    private final static String KEY_TEXT = "Text";
    private final static String KEY_VALUE = "Value";
    private void fillValue(int position,ViewHolder holder){
        holder.text.setText(list.get(position).get(KEY_TEXT));
        if (checkItemPosition != -1){
            holder.text.setTextColor(context.getResources().getColor(R.color.textunselectedcolor));
            holder.text.setBackgroundResource(R.color.check_bg);
        }else {
            holder.text.setTextColor(context.getResources().getColor(R.color.textselectedcolor));
            holder.text.setBackgroundResource(R.color.white);
        }
    }

    static class ViewHolder{
        @ViewInject(R.id.text) private TextView text;

        ViewHolder(View v){
            x.view().inject(this,v);
        }
    }

}
