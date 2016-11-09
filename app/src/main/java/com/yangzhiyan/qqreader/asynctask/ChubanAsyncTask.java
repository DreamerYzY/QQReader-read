package com.yangzhiyan.qqreader.asynctask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.yangzhiyan.qqreader.bean.ChuBanClass;
import com.yangzhiyan.qqreader.interfaces.ChubanResultCallback;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YZY on 2016/10/24.
 */

public class ChubanAsyncTask extends AsyncTask<String,Void,ChuBanClass> {
    private ChubanResultCallback resultCallback;

    public ChubanAsyncTask(ChubanResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    @Override
    protected ChuBanClass doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(params[0]).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                Gson gson = new Gson();
                String jsonstr = response.body().string();
                ChuBanClass chuBanClass = gson.fromJson(jsonstr,ChuBanClass.class);

                return chuBanClass;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ChuBanClass chuBanClasses) {
        resultCallback.CallBack(chuBanClasses);
        super.onPostExecute(chuBanClasses);
    }
}
