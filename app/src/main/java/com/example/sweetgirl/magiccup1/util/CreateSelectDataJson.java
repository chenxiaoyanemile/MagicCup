package com.example.sweetgirl.magiccup1.util;

import com.example.sweetgirl.magiccup1.Bean.TatalSelectData;

import org.json.JSONObject;

/**
 * 2017/4/19.
 */

public class CreateSelectDataJson {


    public static String CreateSelectDataJson(TatalSelectData tatalSelectData){

        try {
            JSONObject tatalSelect=new JSONObject();
            tatalSelect.put("user_id",tatalSelectData.getUser_id());
            tatalSelect.put("text",tatalSelectData.getText());
            tatalSelect.put("scene1_id",tatalSelectData.getScene1_id());
            tatalSelect.put("scene2_id",tatalSelectData.getScene2_id());
            tatalSelect.put("scene31_id",tatalSelectData.getScene31_id());
            tatalSelect.put("scene32_id",tatalSelectData.getScene32_id());
            tatalSelect.put("scene33_id",tatalSelectData.getScene33_id());
            tatalSelect.put("scene4_id",tatalSelectData.getScene4_id());


            L.i("这个是所有场景的json", tatalSelect.toString() );

            return tatalSelect.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }
}
