package com.yangzhiyan.qqreader.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.yangzhiyan.qqreader.bean.YuanChuangClass;
import com.yangzhiyan.qqreader.interfaces.ChubanResultCallback;
import com.yangzhiyan.qqreader.interfaces.YuanchuangResultCallback;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YZY on 2016/10/25.
 */

public class YuanchuangAsyncTask extends AsyncTask<String,Void,YuanChuangClass> {
    private YuanchuangResultCallback resultCallback;

    public YuanchuangAsyncTask(YuanchuangResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    @Override
    protected YuanChuangClass doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(params[0]).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                Gson gson = new Gson();
                String jsonstr = response.body().string();
                YuanChuangClass yuanChuangClass = gson.fromJson(jsonstr,YuanChuangClass.class);
                return yuanChuangClass;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(YuanChuangClass yuanChuangClass) {
        resultCallback.CallBack2(yuanChuangClass);
        super.onPostExecute(yuanChuangClass);
    }
}
