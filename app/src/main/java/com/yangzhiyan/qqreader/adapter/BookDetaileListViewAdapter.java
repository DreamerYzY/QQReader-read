package com.yangzhiyan.qqreader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.bean.BookDetaileBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by YZY on 2016/10/30.
 */

public class BookDetaileListViewAdapter extends BaseAdapter {
    private Context context;
    private BookDetaileBean bookDetaileBean;

    public BookDetaileListViewAdapter(Context context, BookDetaileBean bookDetaileBean) {
        this.context = context;
        this.bookDetaileBean = bookDetaileBean;
    }

    @Override
    public int getCount() {
        if (bookDetaileBean.commentinfo.commentlist.size()!=0){
            return bookDetaileBean.commentinfo.commentlist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return bookDetaileBean.commentinfo.commentlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer = null;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,0);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.book_detaile_listview_item_layout,null
            );
            holer = new ViewHoler();
            x.view().inject(holer,convertView);
            convertView.setTag(holer);
        }else {
            holer = (ViewHoler) convertView.getTag();
        }
        BookDetaileBean.CommentinfoBean.CommentlistBean commentlistBean =
                bookDetaileBean.commentinfo.commentlist.get(position);
        String imgurl = commentlistBean.user.icon;
        if (!imgurl.equals("")){
            Picasso.with(context).load(imgurl).into(holer.ivtalker);
        }else {
            holer.ivtalker.setImageResource(R.mipmap.ic_launcher);
        }
        holer.tvtalk.setText(commentlistBean.content.toString());
        holer.talkerfrom.setText(commentlistBean.platformname);
        holer.tvreplycount.setText(commentlistBean.replycount+"");
        holer.talkagree.setText(commentlistBean.agree+"");
        holer.tvtalker.setText(commentlistBean.user.nickname);
        String level = commentlistBean.user.fanslevelname.toString();
        if (level.equals("无")){
            holer.tvlevel.setVisibility(View.GONE);
        }
        else {
            switch (level){
                case "盟主":
                    holer.tvlevel.setBackgroundColor(Color.RED);
                    break;
                case "舵主":case "堂主":case "护法":
                    holer.tvlevel.setBackgroundColor(Color.GREEN);
                    break;
                case "执事":case "弟子":
                    holer.tvlevel.setBackgroundColor(context.getResources().getColor(R.color.gv4));
            }
            holer.tvlevel.setText(level);
        }
        if (commentlistBean.scoreInfo != null&&commentlistBean.scoreInfo.score>0){
            holer.talkerstar.setRating((float) commentlistBean.scoreInfo.score);
            holer.talkintro.setText(commentlistBean.scoreInfo.intro.toString());
        }
        else {
            holer.talkerstar.setVisibility(View.GONE);
            holer.talkerstar.setLayoutParams(lp);
            holer.talkintro.setVisibility(View.GONE);
            holer.talkintro.setLayoutParams(lp);
        }
        if (commentlistBean.title.equals("")){
            holer.tvtalktitle.setVisibility(View.GONE);
            holer.tvtalktitle.setLayoutParams(lp);
        }else {
            holer.tvtalktitle.setText(commentlistBean.title.toString());
        }
        return convertView;
    }

    class ViewHoler{
        @ViewInject(R.id.ivtalker)
        ImageView ivtalker;
        @ViewInject(R.id.tvtalker)
        TextView tvtalker;
        @ViewInject(R.id.tvlevel)
        TextView tvlevel;
        @ViewInject(R.id.talkerstar)
        RatingBar talkerstar;
        @ViewInject(R.id.talkintro)
        TextView talkintro;
        @ViewInject(R.id.tvtalktitle)
        TextView tvtalktitle;
        @ViewInject(R.id.tvtalk)
        TextView tvtalk;
        @ViewInject(R.id.talkerfrom)
        TextView talkerfrom;
        @ViewInject(R.id.talkagree)
        TextView talkagree;
        @ViewInject(R.id.tvreplycount)
        TextView tvreplycount;

    }
}
