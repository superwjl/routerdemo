package com.tik.routerdemo;

import android.app.Application;

import com.tik.arouter.ARouter;

/**
 *
 **/
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
