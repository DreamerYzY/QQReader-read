package com.yangzhiyan.qqreader.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.adapter.BookDetaileGridviewAdapter;
import com.yangzhiyan.qqreader.adapter.BookDetaileListViewAdapter;
import com.yangzhiyan.qqreader.bean.BookDetaileBean;
import com.yangzhiyan.qqreader.bean.MyGridView;
import com.yangzhiyan.qqreader.utils.DBHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BookDetailedInfoActivity extends AppCompatActivity {
    @ViewInject(R.id.ivcover) ImageView ivcover;
    @ViewInject(R.id.tvtitle) TextView tvtitle;
    @ViewInject(R.id.myratingbar) RatingBar myratingbar;
    @ViewInject(R.id.tvscoretext) TextView tvscoretext;
    @ViewInject(R.id.tvtalkers) TextView tvtalkers;
    @ViewInject(R.id.tvauthor) TextView tvauthor;
    @ViewInject(R.id.tvwordcount) TextView tvwordcount;
    @ViewInject(R.id.tvprices) TextView tvprices;
    @ViewInject(R.id.tvcategory) TextView tvcategory;
    @ViewInject(R.id.tvtalkcount) TextView tvtalkcount;
    @ViewInject(R.id.tvbookintro) TextView tvbookintro;
    @ViewInject(R.id.tvlianzai) TextView tvlianzai;
    @ViewInject(R.id.tvupdatetime) TextView tvupdatetime;
    @ViewInject(R.id.lvtalkabout) ListView lvtalkabout;
    @ViewInject(R.id.gvtonglei) MyGridView gvtonglei;
    @ViewInject(R.id.gvever) MyGridView gvever;
    @ViewInject(R.id.tvcopyright) TextView tvcopyright;
    @ViewInject(R.id.ivtext) ImageView ivtext;
    @ViewInject(R.id.ivaddshujia) ImageView ivaddshujia;
    @ViewInject(R.id.tvaddshujia) TextView tvaddshujia;
    @ViewInject(R.id.rladdshujia) RelativeLayout rladdshujia;
    @ViewInject(R.id.llauthorhome) LinearLayout llauthor;
    @ViewInject(R.id.llauthorinfo) LinearLayout llauthorinfo;
    @ViewInject(R.id.ivauthoricon) ImageView ivauthoricon;
    @ViewInject(R.id.tvauthorname) TextView tvauthorname;
    @ViewInject(R.id.fanscount) TextView fanscount;
    @ViewInject(R.id.commentcount) TextView commentcounts;
    @ViewInject(R.id.authorintro) TextView authorintro;
    @ViewInject(R.id.myscrollview) ScrollView myscrollview;
    @ViewInject(R.id.btchangeever) Button btchangeever;

    private String url = "http://android.reader.qq.com/v6_2/nativepage/book/detail?bid=";
    private Intent intent;
    private BookDetaileBean bookDetaileBean;
    private BookDetaileListViewAdapter bookDetaileListViewAdapter;
    private BookDetaileGridviewAdapter bookDetaileGridviewAdapter;
    private boolean unfold = true;
    private SQLiteDatabase sd;
    private DBHelper helper;
    private List<BookDetaileBean.ColumbooksBean.BookListBean> bookListBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detailed_info);
        x.view().inject(this);
        View footerview = LayoutInflater.from(this).inflate(
                R.layout.book_detaile_listview_footer_layout,null);
        lvtalkabout.addFooterView(footerview);
        bookListBeen = new ArrayList<>();
        intent = getIntent();
        helper = new DBHelper(this);
        Http();
    }
    @Event(value = {R.id.ivreturn,R.id.ivshare,R.id.rlcategory,R.id.rlhudong,R.id.rllike,
            R.id.rltalkabout,R.id.rlseedirectory,R.id.bttotalk, R.id.rldownload,R.id.rlfreeread,
            R.id.rladdshujia,R.id.ivtext,R.id.btchangecategory},
            type = View.OnClickListener.class)
    private void onclick(View view){
        switch (view.getId()){
            case R.id.ivreturn:
                finish();
                break;
            case R.id.ivshare:
                Toast.makeText(this, "分享了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btchangecategory:
                bookListBeen.clear();
                random();
                bookDetaileGridviewAdapter.notifyDataSetChanged();
                gvtonglei.setAdapter(bookDetaileGridviewAdapter);
                break;
            case R.id.ivtext:
                if (unfold){
                    tvbookintro.setMaxLines(Integer.MAX_VALUE);
                    ivtext.setImageResource(R.mipmap.editor_comment_fold);
                }else {
                    tvbookintro.setMaxLines(4);
                    ivtext.setImageResource(R.mipmap.editor_comment_unfold);
                }
                unfold = !unfold;
                break;
            case R.id.rladdshujia:
                sd = helper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("bookname",bookDetaileBean.introinfo.book.title.toString());
                values.put("updateto","更新至: "+bookDetaileBean.chapinfo.lastcname.toString());
                values.put("state","加入");
                values.put("bookimgurl","http://wfqqreader.3g.qq.com/cover/"
                        + bookDetaileBean.commentinfo.bid% 1000 + "/" +
                        bookDetaileBean.commentinfo.bid + "/t5_" +
                        bookDetaileBean.commentinfo.bid + ".jpg");
                values.put("bid",bookDetaileBean.commentinfo.bid+"");
                long a = sd.insert("mybook",null,values);
                int b = Integer.parseInt(a+"");
                if (b>0){
                    Toast.makeText(BookDetailedInfoActivity.this, "已加入书架", Toast.LENGTH_SHORT).show();
                    ivaddshujia.setImageResource(R.mipmap.detail_add_book_shelf_icon_disabled);
                    tvaddshujia.setText("已在书架");
                    tvaddshujia.setTextColor(getResources().getColor(R.color.textunselectedcolor));
                }
                break;
        }
    }

    private void Http(){
        String dataurl = url+intent.getIntExtra("bid",0);
        RequestParams request = new RequestParams(dataurl);
        request.addQueryStringParameter("pagestamp","1");
        request.addQueryStringParameter("alg","0");
        request.addQueryStringParameter("data_type","0");
        request.addQueryStringParameter("origin","908");
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                bookDetaileBean = gson.fromJson(result,BookDetaileBean.class);
                bindData(bookDetaileBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void bindData(BookDetaileBean bookDetaileBeans){
        bookDetaileBean = bookDetaileBeans;
        String imgurl = "http://wfqqreader.3g.qq.com/cover/"
                + bookDetaileBean.commentinfo.bid% 1000 + "/" + bookDetaileBean.commentinfo.bid + "/t5_" +
                bookDetaileBean.commentinfo.bid + ".jpg";
        Picasso.with(this).load(imgurl).into(ivcover);
        tvtitle.setText(bookDetaileBean.introinfo.book.title);
        myratingbar.setRating(Float.parseFloat(bookDetaileBean.introinfo.scoreInfo.score));
        tvscoretext.setText(bookDetaileBean.introinfo.scoreInfo.scoretext+"分");
        tvtalkers.setText(bookDetaileBean.introinfo.scoreInfo.intro);
        tvauthor.setText(bookDetaileBean.introinfo.book.author);
        float num = (float) bookDetaileBean.introinfo.book.totalwords/10000;
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        String a = decimalFormat.format(num);
        tvwordcount.setText(a+"万字");
        tvprices.setText(bookDetaileBean.introinfo.prices.first);
        tvcategory.setText(bookDetaileBean.introinfo.book.categoryshortname);
        int commentcount = bookDetaileBean.commentinfo.commentcount;
        if (commentcount>10000){
            float num2 = (float) bookDetaileBean.commentinfo.commentcount/10000;
            DecimalFormat decimalFormat2 = new DecimalFormat("#0.0");
            String b = decimalFormat.format(num2);
            tvtalkcount.setText(b+"万");
        }else {

            tvtalkcount.setText(bookDetaileBean.commentinfo.commentcount+"");
        }
        tvbookintro.setText(bookDetaileBean.introinfo.book.intro+"");
        tvlianzai.setText("连载至"+bookDetaileBean.chapinfo.chapsize+"章");
        tvupdatetime.setText("更新于"+bookDetaileBean.chapinfo.lastChapterUpdateTime);
        tvcopyright.setText(bookDetaileBean.introinfo.book.bookfrom);
        if (bookDetaileBean.authorRec==null){
            llauthor.setVisibility(View.GONE);
            llauthorinfo.setVisibility(View.GONE);
        }else {
            String authorimgurl = bookDetaileBean.authorRec.authorInfo.author.avatar;
            if (!authorimgurl.equals("")){
                Picasso.with(this).load(authorimgurl).into(ivauthoricon);
            }else {
                ivauthoricon.setImageResource(R.mipmap.ic_launcher);
            }
            tvauthorname.setText(bookDetaileBean.authorRec.authorInfo.author.penName);
            float fansnum = bookDetaileBean.authorRec.authorInfo.fansCount/10000;
            DecimalFormat decimalFormat1 = new DecimalFormat("#0.0");
            String fans = decimalFormat.format(fansnum);
            fanscount.setText(fans+"万");
            commentcounts.setText(bookDetaileBean.authorRec.authorInfo.commentCount+"");
            authorintro.setText(bookDetaileBean.authorRec.authorInfo.author.intro+"");

        }
        random();
        bookDetaileGridviewAdapter = new BookDetaileGridviewAdapter(
                BookDetailedInfoActivity.this,bookListBeen);
        gvtonglei.setAdapter(bookDetaileGridviewAdapter);
        bookDetaileListViewAdapter = new BookDetaileListViewAdapter(
                BookDetailedInfoActivity.this,bookDetaileBean);
        lvtalkabout.setAdapter(bookDetaileListViewAdapter);
        gvtonglei.setFocusable(false);
        lvtalkabout.setFocusable(false);
        myscrollview.smoothScrollTo(0,0);


        sd = helper.getReadableDatabase();
        if (bookDetaileBean.commentinfo!=null){
            Cursor cursor = sd.query("mybook",null,"bid=?",new String[]{bookDetaileBean.commentinfo.bid+""},null,null,null);
            if (cursor.moveToNext()){
                ivaddshujia.setImageResource(R.mipmap.detail_add_book_shelf_icon_disabled);
                tvaddshujia.setText("已在书架");
                tvaddshujia.setTextColor(getResources().getColor(R.color.textunselectedcolor));
                rladdshujia.setClickable(false);
            }
        }

    }
    private void random(){
        HashSet<Integer> set = new HashSet();
        for (int i=0;i<6;i++){
            int random = new Random().nextInt(6);
            set.add(random);
            if (set.size()==3){
                break;
            }else {
                continue;
            }
        }
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            bookListBeen.add(bookDetaileBean.columbooks.bookList.get(iterator.next()));
        }
    }
}
