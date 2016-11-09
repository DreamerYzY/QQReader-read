package com.yangzhiyan.qqreader.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by YZY on 2016/10/26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
