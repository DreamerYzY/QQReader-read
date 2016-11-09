package com.yangzhiyan.qqreader.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YZY on 2016/10/30.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "mybooklist.db";
    private static final int VERSION = 1;
    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists mybook(" +
                "bookname text," +
                "_id integer primary key autoincrement," +
                "updateto text," +
                "state text," +
                "bookimgurl text," +
                "bid text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<newVersion){
            db.execSQL("drop table mybook");
            onCreate(db);
        }

    }
}
