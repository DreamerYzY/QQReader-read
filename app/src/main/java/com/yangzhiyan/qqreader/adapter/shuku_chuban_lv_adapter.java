package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.bean.ChuBanClass;

/**
 * Created by YZY on 2016/10/24.
 */

public class shuku_chuban_lv_adapter extends BaseAdapter {
    private ChuBanClass chuBanClass;
    private Context context;
    private String imgurl;
    private String url="http://wfqqreader.3g.qq.com/cover/";
    private String bids;
    private String[] bid;

    public shuku_chuban_lv_adapter(ChuBanClass chuBanClass, Context context) {
        this.chuBanClass = chuBanClass;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (chuBanClass.publishCategoryList==null){
            return 0;
        }
        return chuBanClass.publishCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return chuBanClass.publishCategoryList.get(position);
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
        holder.tvtitle.setText(chuBanClass.publishCategoryList.get(position).categoryName);
        holder.tvcount.setText("共"+chuBanClass.publishCategoryList.get(position).bookCount+"册");
        bids = chuBanClass.publishCategoryList.get(position).bids;
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
