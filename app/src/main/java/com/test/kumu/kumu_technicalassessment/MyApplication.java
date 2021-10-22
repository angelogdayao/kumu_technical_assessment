package com.test.kumu.kumu_technicalassessment;


import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

//Used to fetch the context on when calling network from other activity
public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
