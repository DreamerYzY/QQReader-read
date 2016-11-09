package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.bean.ShukuLvInfo;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by YZY on 2016/10/26.
 */

public class Shuku_lv_info_adapter extends BaseAdapter {
    private Context context;
    private List<ShukuLvInfo.BookListBean> bookListBeen;
    private String a;

    public Shuku_lv_info_adapter(Context context, List<ShukuLvInfo.BookListBean> bookListBeen) {
        this.context = context;
        this.bookListBeen = bookListBeen;
    }

    @Override
    public int getCount() {

        return bookListBeen.size();
    }


    @Override
    public Object getItem(int position) {
        return bookListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shuku_lv_refresh_layout, null);
            holder = new ViewHolder();
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
            } else {
            holder = (ViewHolder) convertView.getTag();
            }
        ShukuLvInfo.BookListBean bookListBeans = bookListBeen.get(position);
        String imgurl = "http://wfqqreader.3g.qq.com/cover/"
                + bookListBeans.bid % 1000 + "/" + bookListBeans.bid + "/t5_" +
                bookListBeans.bid + ".jpg";
        if (bookListBeans.ext.recTitle.isEmpty()){
            holder.tvrecTitle.setText("");
            holder.tvcatrgoryName.setText(bookListBeans.categoryName);
            holder.tvrenqi.setBackground(context.getResources().getDrawable(R.drawable.tvrenqiback));
            holder.tvcatrgoryName.setBackground(context.getResources().getDrawable(R.drawable.tvcateback));
        }else {
            holder.tvrecTitle.setText(bookListBeans.ext.recTitle);
            holder.tvcatrgoryName.setText("");
            holder.tvrenqi.setBackground(null);
            holder.tvcatrgoryName.setBackground(null);
        }
        if (bookListBeans.num.equals("null")){
            holder.tvrenqi.setText("");
        }else {
            float num = (float) Integer.parseInt(bookListBeans.num) / 10000;
            DecimalFormat decimalFormat = new DecimalFormat("#0.0");
            a = decimalFormat.format(num);
            holder.tvrenqi.setText(a + "万" + bookListBeans.ext.unit);
        }
        holder.tvtitle.setText(bookListBeans.title);
        holder.tvintro.setText(bookListBeans.intro);
        holder.tvauthor.setText(bookListBeans.author);
        Picasso.with(context).load(imgurl).into(holder.ivcover);

        return convertView;
    }


    class ViewHolder {
        @ViewInject(R.id.ivcover)
        ImageView ivcover;
        @ViewInject(R.id.tvtitle)
        TextView tvtitle;
        @ViewInject(R.id.tvintro)
        TextView tvintro;
        @ViewInject(R.id.tvauthor)
        TextView tvauthor;
        @ViewInject(R.id.tvcategoryName)
        TextView tvcatrgoryName;
        @ViewInject(R.id.tvrenqi)
        TextView tvrenqi;
        @ViewInject(R.id.tvrecTitle)
        TextView tvrecTitle;
    }
}
