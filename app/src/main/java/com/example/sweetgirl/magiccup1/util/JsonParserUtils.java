package com.example.sweetgirl.magiccup1.util;

import com.example.sweetgirl.magiccup1.model.Scene;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * 解析数据
 */

public class JsonParserUtils {
    public static List<Scene>parserJson(String jsonData){
        try {
            JSONArray jsonArray=new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String name=jsonObject.getString("name");
                //image
                String depiction=jsonObject.getString("depiction");
                String resource=jsonObject.getString("resource");
                //saveUserInfo(context,resource);
                //L.i(LOGTAG,user_id+"解析数据");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
