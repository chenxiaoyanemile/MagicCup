package com.example.sweetgirl.magiccup1.view;



import com.example.sweetgirl.magiccup1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindItemDataSourceScene {
    public static List<Map<String,Object>> getMaps(){
        List<Map<String ,Object>> list=new ArrayList<>();

        Map<String,Object> map=new HashMap<>();
        map.put("image", R.mipmap.iv_item);
        map.put("text01","下雨");
        list.add(map);

        map=new HashMap<>();
        map.put("image", R.mipmap.iv_item);
        map.put("text01","下雨");
        list.add(map);

        map=new HashMap<>();
        map.put("image", R.mipmap.iv_item);
        map.put("text01","下雨");
        list.add(map);

        map=new HashMap<>();
        map.put("image", R.mipmap.iv_item);
        map.put("text01","下雨");
        list.add(map);

        return list;
    }
}
