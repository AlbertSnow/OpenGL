package com.example.albertsnow.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by albertsnow on 6/22/17.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApplication = this;
    }

    public static MyApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
