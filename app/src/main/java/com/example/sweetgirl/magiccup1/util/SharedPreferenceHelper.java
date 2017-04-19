package com.example.sweetgirl.magiccup1.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author kesar
 * @Description: SharedPreferences
 * @date 2017-4-19
 */

public class SharedPreferenceHelper {


    public String SP_NAME;

    private static final String PREFIX = "setting_";

    private final String PREF_USERID = "user_id";

    public Context context;
    public SharedPreferences sp;  //单例


    //初始化单例
    private final static class SingleTon {
        public static final SharedPreferenceHelper singleTon = new SharedPreferenceHelper();
    }

    private SharedPreferenceHelper() {

    }

   /* *
     * @param @return
     * @return SharedPreferenceHelper
     * @throws
     * @Description: 唯一的实例
     * @author kesar
     * @date 2015年9月18日*/

    public static SharedPreferenceHelper getInstance() {
        return SingleTon.singleTon;
    }

    /**
     * 初始化单例
     * @param context
     */

    public void init(Context context) {
        this.context = context;
        sp = SharedPreferencesUtils.getSharedPreferences(context, PREFIX);
    }



    public void saveUser_id(String user_id) {
        SharedPreferencesUtils.putValue(App.getInstance(), PREF_USERID, user_id);
    }

    public String getUser_id() {
        String user_id = SharedPreferencesUtils.getValue(App.getInstance(),
                PREF_USERID, "");

        return user_id.equals("") ? null : user_id;
    }

    public void removeUser_id() {
        SharedPreferencesUtils.removeValue(App.getInstance(), PREF_USERID);
    }

    public void removeAll() {
        SharedPreferencesUtils.clearAll(App.getInstance());
    }
}
