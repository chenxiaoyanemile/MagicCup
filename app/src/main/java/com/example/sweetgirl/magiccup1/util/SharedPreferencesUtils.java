package com.example.sweetgirl.magiccup1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 *  2017/4/19.
 */

public class SharedPreferencesUtils {
    /**
     * 设置的文件
     */
    public static String SP_NAME = "ar_redpacket";

    /**
     * @param @param  context
     * @param @param  spName
     * @param @return
     * @return SharedPreferences
     * @throws
     * @Description: 获取SP
     * @author kesar
     * @date 2015-12-31
     */
    public static SharedPreferences getSharedPreferences(Context context,
                                                         String spName) {
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * @param @param  context
     * @param @return
     * @return SharedPreferences
     * @throws
     * @Description: 获取SP
     * @author kesar
     * @date 2015-12-31
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return getSharedPreferences(context, SP_NAME);
    }

    /**
     * @param @param  context
     * @param @return
     * @return Editor
     * @throws
     * @Description: 获取SP编辑器
     * @author kesar
     * @date 2015-12-31
     */
    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    /**
     * @param @param context
     * @param @param key
     * @param @param value
     * @return void
     * @throws
     * @Description: 写入值
     * @author kesar
     * @date 2015-12-4
     */
    public static void putValue(Context context, String key, int value) {
        SharedPreferences.Editor sp = getEditor(context);
        sp.putInt(key, value);
        sp.commit();
    }

    /**
     * @param @param context
     * @param @param key
     * @param @param value
     * @return void
     * @throws
     * @Description: 写入值
     * @author kesar
     * @date 2015-12-4
     */
    public static void putValue(Context context, String key, boolean value) {
        SharedPreferences.Editor sp = getEditor(context);
        sp.putBoolean(key, value);
        sp.commit();
    }

    /**
     * @param @param context
     * @param @param key
     * @param @param value
     * @return void
     * @throws
     * @Description: 写入值
     * @author kesar
     * @date 2015-12-4
     */
    public static void putValue(Context context, String key, String value) {
        SharedPreferences.Editor sp = getEditor(context);
        sp.putString(key, value);
        sp.commit();
    }

    /**
     * @param @param context
     * @param @param key
     * @return void
     * @throws
     * @Description: 删除key
     * @author kesar
     * @date 2015-12-4
     */
    public static void removeValue(Context context, String key) {
        SharedPreferences.Editor sp = getEditor(context);
        sp.remove(key);
        sp.commit();
    }

    /**
     * @param @param  context
     * @param @param  key
     * @param @param  defValue
     * @param @return
     * @return int
     * @throws
     * @Description: 得到值
     * @author kesar
     * @date 2015-12-4
     */
    public static int getValue(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }

    /**
     * @param @param  context
     * @param @param  key
     * @param @param  defValue
     * @param @return
     * @return boolean
     * @throws
     * @Description: 得到值
     * @author kesar
     * @date 2015-12-4
     */
    public static boolean getValue(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    /**
     * @param @param  context
     * @param @param  key
     * @param @param  defValue
     * @param @return
     * @return String
     * @throws
     * @Description: 得到值
     * @author kesar
     * @date 2015-12-4
     */
    public static String getValue(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

    public static void clearAll(Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}
