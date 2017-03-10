package com.example.sweetgirl.magiccup1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;


import com.example.sweetgirl.magiccup1.Bean.TextImage;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.ui.TextImageViewHolder;
import com.example.sweetgirl.magiccup1.ui.recycler.RefreshRecyclerView;
import com.example.sweetgirl.magiccup1.ui.recycler.SpaceItemDecoration;
import com.example.sweetgirl.magiccup1.ui.recycler.adapter.Action;
import com.example.sweetgirl.magiccup1.ui.recycler.adapter.MultiTypeAdapter;
import com.example.sweetgirl.magiccup1.ui.recycler.adapter.OnItemClickListener;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
* 选择故事（新版）第一幕
* */

public class MultiTypeActivity extends AppCompatActivity {
    private static final String TAG = LogUtil.makeLogTag(MultiTypeActivity.class);

    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;


    private int mPage = 0;

    List<TextImage> list=new ArrayList<TextImage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);

        mAdapter=new MultiTypeAdapter(this);
        mRecyclerView=(RefreshRecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845,0xFFE44F98,0xFF2FAC21);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(12,8,12,30));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        L.i(TAG,"设置适配器");
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View view) {
                L.i(TAG,"点击事件");
                Toast.makeText(MultiTypeActivity.this,"你选择了",Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                L.i(TAG,"获取数据");
                getData(true);
            }
        });
        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
            }
        });
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });
    }
    public void getData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 0;
        } else {
            mPage++;
        }
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    mAdapter.clear();
                    mRecyclerView.dismissSwipeRefresh();
                }
                doGet();
                mAdapter.addAll(TextImageViewHolder.class, list);
                if (mPage >= 3) {
                    mAdapter.showNoMore();
                }
            }
        }, 2000);
    }

    /*public TextImage[] getTextImageVirualData() {
         b= new TextImage[]{
                new TextImage("http://i03.pictn.sogoucdn.com/3c28af542f2d49f7-9e7c5d699eaea93e-3f7e1bcc57cbe10e050bf58559b0d5ae_qq", "唠叨","说你是爱你"),
                new TextImage("http://i03.pictn.sogoucdn.com/3c28af542f2d49f7-8f0182a4cf50287e-ba43c5aef499c64e6a3297c5c500c7dc_qq", "唠叨","说你是爱你"),
                new TextImage("http://img02.sogoucdn.com/app/a/100520093/803d8006b5d521bb-2eb356b9e8bc4ae6-0725fb0ad48d8b3a32f08eb150380bba.jpg", "看片。。。","说你是爱你")
        };
        return b;
    }*/

    //[3]从服务器获取数据
    private  boolean doGet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    enqueue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return false;
    }
    private void enqueue(){
        //[1]拿到OkHttpClient
        OkHttpClient client = new OkHttpClient();
        //[2]构造Request   "http://119.29.222.54:8010/api/scene/1
        Request request = new Request.Builder()
                .url("http://139.199.190.245:8010/api/scene/1")
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
                list=jsonParser(res);

            }
        });
    }
    //[4]解析数据
    private List<TextImage> jsonParser(String jsonData) {
        try {
            JSONTokener jsonTokener = new JSONTokener(jsonData);
            org.json.JSONObject jsonObject = (org.json.JSONObject) jsonTokener.nextValue();
            org.json.JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                org.json.JSONObject object = array.getJSONObject(i);
                String name=object.getString("name");
                String image=object.getString("image");
                String dep=object.getString("depiction");
                L.i(TAG,"小燕子"+name);
                L.i(TAG,"小燕子"+image);
                L.i(TAG,"小燕子"+dep);
                list.add(new TextImage(image, name, dep));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
