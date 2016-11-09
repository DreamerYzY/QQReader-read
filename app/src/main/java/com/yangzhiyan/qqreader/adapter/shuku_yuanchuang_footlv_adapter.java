package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.bean.ChuBanClass;
import com.yangzhiyan.qqreader.bean.YuanChuangClass;

/**
 * Created by YZY on 2016/10/24.
 */

public class shuku_yuanchuang_footlv_adapter extends BaseAdapter {
    private YuanChuangClass yuanChuangClass;
    private Context context;
    private String imgurl;
    private String url="http://wfqqreader.3g.qq.com/cover/";
    private String bids;
    private String[] bid;

    public shuku_yuanchuang_footlv_adapter(YuanChuangClass yuanChuangClass, Context context) {
        this.yuanChuangClass = yuanChuangClass;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (yuanChuangClass.girlCategoryList==null){
            return 0;
        }
        return yuanChuangClass.girlCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return yuanChuangClass.girlCategoryList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.shuku_lv_item,null);
            holder.ivpic = (ImageView) convertView.findViewById(R.id.ivpic);
            holder.tvtitle = (TextView) convertView.findViewById(R.id.tvtitle);
            holder.tvcount = (TextView) convertView.findViewById(R.id.tvcount);
            holder.ivleft = (ImageView) convertView.findViewById(R.id.ivleft);
            holder.ivright = (ImageView) convertView.findViewById(R.id.ivright);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvtitle.setText(yuanChuangClass.girlCategoryList.get(position).categoryName);
        holder.tvcount.setText("共"+yuanChuangClass.girlCategoryList.get(position).bookCount+"册");
        bids = yuanChuangClass.girlCategoryList.get(position).bids;
        bid = bids.split(",");
        imgurl = url+(bid[0].substring(bid[0].length()-3)).replaceFirst("^0*","")+"/"+bid[0]+"/t5_"+bid[0]+".jpg";
        Picasso.with(context).load(imgurl).into(holder.ivpic);
        imgurl = url+(bid[1].substring(bid[1].length()-3)).replaceFirst("^0*","")+"/"+bid[1]+"/t5_"+bid[1]+".jpg";
        Picasso.with(context).load(imgurl).into(holder.ivleft);
        imgurl = url+(bid[2].substring(bid[2].length()-3)).replaceFirst("^0*","")+"/"+bid[2]+"/t5_"+bid[2]+".jpg";
        Picasso.with(context).load(imgurl).into(holder.ivright);

        return convertView;
    }
    class ViewHolder {
        ImageView ivleft;
        ImageView ivpic;
        ImageView ivright;
        TextView tvtitle;
        TextView tvcount;
    }
}

