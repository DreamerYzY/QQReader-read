package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.bean.SinaNewsBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by YZY on 2016/11/1.
 */

public class JingxuanListviewAdapter extends BaseAdapter {
    private Context context;
    private SinaNewsBean sinaNewsBean;

    public JingxuanListviewAdapter(Context context, SinaNewsBean sinaNewsBean) {
        this.context = context;
        this.sinaNewsBean = sinaNewsBean;
    }

    @Override
    public int getCount() {
        return sinaNewsBean.data.feed.size();
    }

    @Override
    public Object getItem(int position) {
        return sinaNewsBean.data.feed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        String type = sinaNewsBean.data.feed.get(position).category;
        int result = 0;
        switch (type){
            case "subject":
                result = 0;
                break;
            case "cms":
                result = 1;
                break;
            case "hdpic":
                result = 2;
                break;
            case "video":
                result = 3;
                break;
            default:
                result = 4;
                break;
        }
        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SinaNewsBean.DataBean.FeedBean feedBean = sinaNewsBean.data.feed.get(position);

        String type = feedBean.category;
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        ViewHolder4 holder4 = null;
        ViewHolder5 holder5 = null;

        if (convertView == null){
            switch (type){
                case "subject":
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.jingsuan_listview_item_subject,null);
                    holder1 = new ViewHolder1(convertView);
                    convertView.setTag(holder1);
                    break;
                case "cms":
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.jingxuan_listview_item_cms,null);
                    holder2 = new ViewHolder2(convertView);
                    convertView.setTag(holder2);
                    break;
                case "hdpic":
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.jingxuan_listview_item_hdpic,null);
                    holder3 = new ViewHolder3(convertView);
                    convertView.setTag(holder3);
                    break;
                case "video":
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.jingxuan_listview_item_video,null);
                    holder4 = new ViewHolder4(convertView);
                    convertView.setTag(holder4);
                    break;
                default:
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.jingxuan_listview_item_video,null);
                    holder5 = new ViewHolder5(convertView);
                    convertView.setTag(holder5);
                    break;
            }
        }else {
            switch (type){
                case "subject":
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case "cms":
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case "hdpic":
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;
                case "video":
                    holder4 = (ViewHolder4) convertView.getTag();
                    break;
                default:
                    holder5 = (ViewHolder5) convertView.getTag();
                    break;
            }
        }
        switch (type){
            case "subject":
                String imgurl = feedBean.kpic;
                Picasso.with(context).load(imgurl).into(holder1.ivcover);
                holder1.tvcomment.setText(feedBean.comment+"");
                holder1.tvtitle.setText(feedBean.longTitle);
                holder1.tvintro.setText(feedBean.intro);
                break;
            case "cms":
                String imgurl2 = feedBean.kpic;
                Picasso.with(context).load(imgurl2).into(holder2.ivcover);
                holder2.tvcomment.setText(feedBean.comment+"");
                holder2.tvtitle.setText(feedBean.longTitle);
                holder2.tvintro.setText(feedBean.intro);
                holder2.tvsource.setText(feedBean.source);
                break;
            case "hdpic":
                String imgurl3_1 = feedBean.kpic;
                String imgurl3_2 = feedBean.pic;
                Picasso.with(context).load(imgurl3_1).into(holder3.ivcover);
                Picasso.with(context).load(imgurl3_2).into(holder3.ivcover2);
                holder3.tvtitle.setText(feedBean.longTitle);
                holder3.tvintro.setText(feedBean.intro);
                break;
            case "video":
                String imgurl4 = feedBean.kpic;
                Picasso.with(context).load(imgurl4).into(holder4.ivcover);
                holder4.tvcomment.setText(feedBean.comment+"");
                holder4.tvtitle.setText(feedBean.longTitle);
                holder4.tvintro.setText(feedBean.intro);
                holder4.tvsource.setText(feedBean.source);
                break;
            default:
                String imgurl5 = feedBean.kpic;
                Picasso.with(context).load(imgurl5).into(holder5.ivcover);
                holder5.tvcomment.setText(feedBean.comment+"");
                holder5.tvtitle.setText(feedBean.longTitle);
                holder5.tvintro.setText(feedBean.intro);
                holder5.tvsource.setText(feedBean.source);
                break;

        }
        return convertView;
    }

    private class ViewHolder1{
        @ViewInject(R.id.ivcover)
        ImageView ivcover;
        @ViewInject(R.id.tvtitle)
        TextView tvtitle;
        @ViewInject(R.id.tvintro)
        TextView tvintro;
        @ViewInject(R.id.tvcomment)
        TextView tvcomment;
        ViewHolder1(View view){
            x.view().inject(this,view);
        }

    }
    private class ViewHolder2{
        @ViewInject(R.id.ivcover)
        ImageView ivcover;
        @ViewInject(R.id.tvtitle)
        TextView tvtitle;
        @ViewInject(R.id.tvintro)
        TextView tvintro;
        @ViewInject(R.id.tvcomment)
        TextView tvcomment;
        @ViewInject(R.id.tvsource)
        TextView tvsource;
        ViewHolder2(View view){
            x.view().inject(this,view);
        }

    }
    private class ViewHolder3{
        @ViewInject(R.id.ivcover)
        ImageView ivcover;
        @ViewInject(R.id.tvtitle)
        TextView tvtitle;
        @ViewInject(R.id.tvintro)
        TextView tvintro;
        @ViewInject(R.id.ivcover2)
        ImageView ivcover2;
        ViewHolder3(View view){
            x.view().inject(this,view);
        }

    }
    private class ViewHolder4{
        @ViewInject(R.id.ivcover)
        ImageView ivcover;
        @ViewInject(R.id.tvtitle)
        TextView tvtitle;
        @ViewInject(R.id.tvintro)
        TextView tvintro;
        @ViewInject(R.id.tvcomment)
        TextView tvcomment;
        @ViewInject(R.id.tvsource)
        TextView tvsource;
        ViewHolder4(View view){
            x.view().inject(this,view);
        }

    }
    private class ViewHolder5{
        @ViewInject(R.id.ivcover)
        ImageView ivcover;
        @ViewInject(R.id.tvtitle)
        TextView tvtitle;
        @ViewInject(R.id.tvintro)
        TextView tvintro;
        @ViewInject(R.id.tvcomment)
        TextView tvcomment;
        @ViewInject(R.id.tvsource)
        TextView tvsource;
        ViewHolder5(View view){
            x.view().inject(this,view);
        }

    }
}
