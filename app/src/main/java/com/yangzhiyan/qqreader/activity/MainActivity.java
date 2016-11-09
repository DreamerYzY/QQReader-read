package com.yangzhiyan.qqreader.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yangzhiyan.qqreader.R;
import com.yangzhiyan.qqreader.fragment.FaxianFragment;
import com.yangzhiyan.qqreader.fragment.JingxuanFragment;
import com.yangzhiyan.qqreader.fragment.ShujiaFragment;
import com.yangzhiyan.qqreader.fragment.ShukuFragment;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rgtab;
    private RadioButton rbshujia;
    private RadioButton rbjingxuan;
    private RadioButton rbshuku;
    private RadioButton rbfaxian;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FragmentTransaction transaction2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgtab = (RadioGroup) findViewById(R.id.rgtab);
        rbshujia = (RadioButton) findViewById(R.id.rbshujia);
        rbjingxuan = (RadioButton) findViewById(R.id.rbjingxuan);
        rbshuku = (RadioButton) findViewById(R.id.rbshuku);
        rbfaxian = (RadioButton) findViewById(R.id.rbfaxian);
        Drawable drawable = getResources().getDrawable(R.drawable.rgshujia);
        drawable.setBounds(0,0,90,120);
        rbshujia.setCompoundDrawables(null,drawable,null,null);
        drawable = getResources().getDrawable(R.drawable.rgjingxuan);
        drawable.setBounds(0,0,90,120);
        rbjingxuan.setCompoundDrawables(null,drawable,null,null);
        drawable = getResources().getDrawable(R.drawable.rgshuku);
        drawable.setBounds(0,0,90,120);
        rbshuku.setCompoundDrawables(null,drawable,null,null);
        drawable = getResources().getDrawable(R.drawable.rgfaxian);
        drawable.setBounds(0,0,90,120);
        rbfaxian.setCompoundDrawables(null,drawable,null,null);


        manager = getSupportFragmentManager();
        transaction2 = manager.beginTransaction();
        transaction2.add(R.id.myframelayout,new ShujiaFragment());
        transaction2.commit();
        rgtab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = manager.beginTransaction();
                transaction.add(R.id.myframelayout,new ShujiaFragment());
                switch (checkedId){
                    case R.id.rbshujia:
                        transaction.replace(R.id.myframelayout,new ShujiaFragment());
                        break;
                    case R.id.rbjingxuan:
                        transaction.replace(R.id.myframelayout,new JingxuanFragment());
                        break;
                    case R.id.rbshuku:
                        transaction.replace(R.id.myframelayout,new ShukuFragment());
                        break;
                    case R.id.rbfaxian:
                        transaction.replace(R.id.myframelayout,new FaxianFragment());
                        break;
                }
                transaction.commit();
            }
        });
    }
}
