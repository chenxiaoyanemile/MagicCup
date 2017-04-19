package com.example.sweetgirl.magiccup1.util;

import android.app.Application;
import android.content.Context;

/**
 *  2017/4/19.
 */

public class App extends Application{

    public Context context;
    public static SharedPreferenceHelper sharedPreferenceHelper;

    public static App instance;


    public App(Context context){
        sharedPreferenceHelper.init(context);

    }

    public static App getInstance(){
        return instance;
    }


    public static void saveUser_id(String user_id) {
        SharedPreferenceHelper.getInstance().saveUser_id(user_id);
    }

    public static String getUser_id() {

        String user_id = SharedPreferenceHelper.getInstance().getUser_id();

        return user_id;
    }

    public static void removeUser_id() {
        SharedPreferenceHelper.getInstance().removeUser_id();
    }

    public static void removeAll() {
       SharedPreferenceHelper.getInstance().removeAll();
    }

}
