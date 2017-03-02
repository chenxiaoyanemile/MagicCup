package com.example.sweetgirl.magiccup1.app;

import android.app.Application;

/**
 *全局变量
 */

public class MyApplication extends Application{

    public String user_id;

    @Override
    public void onCreate() {
        super.onCreate();
        setUser_id("");
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
