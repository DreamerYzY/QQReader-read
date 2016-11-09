package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.bean.ChuBanClass;
import com.yangzhiyan.qqreader.bean.YuanChuangClass;

/**
 * Created by YZY on 2016/10/24.
 */

public class shuku_chuban_gv_adapter extends BaseAdapter {
    private ChuBanClass chuBanClass;
    private Context context;

    public shuku_chuban_gv_adapter(ChuBanClass chuBanClass, Context context) {
        this.chuBanClass = chuBanClass;
        this.context = context;
    }
    @Override
    public int getCount() {
        return chuBanClass.recmd.size();
    }

    @Override
    public Object getItem(int position) {
        return chuBanClass.recmd.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.shuku_gv_item,null);
            holder.ivpic = (ImageView) convertView.findViewById(R.id.ivpic);
            holder.tvtitle = (TextView) convertView.findViewById(R.id.tvtitle);
            holder.tvintro = (TextView) convertView.findViewById(R.id.tvintro);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvtitle.setText(chuBanClass.recmd.get(position).title);
        holder.tvintro.setText(chuBanClass.recmd.get(position).intro);
        switch (position){
            case 0:
                holder.ivpic.setBackgroundResource(R.color.gv1);
                break;
            case 1:
                holder.ivpic.setBackgroundResource(R.color.gv2);
                break;
            case 2:
                holder.ivpic.setBackgroundResource(R.color.gv3);
                break;
            case 3:
                holder.ivpic.setBackgroundResource(R.color.gv4);
                break;
        }
        String imgurl = chuBanClass.recmd.get(position).image;
        Picasso.with(context).load(imgurl).into(holder.ivpic);
        return convertView;
    }
    class ViewHolder{
        ImageView ivpic;
        TextView tvtitle;
        TextView tvintro;

    }
}
