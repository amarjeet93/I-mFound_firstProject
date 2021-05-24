package com.e.found.Utils;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;


public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
