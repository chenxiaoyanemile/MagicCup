package com.example.sweetgirl.magiccup1.http;

import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 2017/3/18.
 */

public class DoGet {

    private static final String TAG = LogUtil.makeLogTag(DoGet.class);

    public static String enqueue(String url){
        String res;
        //[1]拿到OkHttpClient
        OkHttpClient client = new OkHttpClient();
        //[2]构造Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //[3]将Request封装为call
        Call call=client.newCall(request);
        //[4]执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                L.i(TAG,"onFailure"+e.getMessage());
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String res=response.body().string();
                L.i(TAG,"onResponse"+res);
            }
        });
        return null;
    }
}
