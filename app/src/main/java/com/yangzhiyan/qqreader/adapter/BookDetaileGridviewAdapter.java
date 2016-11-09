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
import com.yangzhiyan.qqreader.bean.BookDetaileBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Random;

/**
 * Created by YZY on 2016/10/31.
 */

public class BookDetaileGridviewAdapter extends BaseAdapter{
    private Context context;
    private List<BookDetaileBean.ColumbooksBean.BookListBean> bookListBeen;

    public BookDetaileGridviewAdapter(Context context, List<BookDetaileBean.ColumbooksBean.BookListBean> bookListBeen) {
        this.context = context;
        this.bookListBeen = bookListBeen;
    }

    @Override
    public int getCount() {
        return bookListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return bookListBeen;
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
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.book_detaile_gridview_item_layout,null);
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String imgurl = "http://wfqqreader.3g.qq.com/cover/"
                + bookListBeen.get(position).bid% 1000 + "/" + bookListBeen.get(position).bid + "/t5_" +
                bookListBeen.get(position).bid + ".jpg";
        Picasso.with(context).load(imgurl).into(holder.bookicon);
        holder.bookname.setText(bookListBeen.get(position).title+"");
        holder.author.setText(bookListBeen.get(position).author+"");
        return convertView;
    }
    class ViewHolder{
        @ViewInject(R.id.bookicon)
        ImageView bookicon;
        @ViewInject(R.id.bookname)
        TextView bookname;
        @ViewInject(R.id.author)
        TextView author;
    }
}
