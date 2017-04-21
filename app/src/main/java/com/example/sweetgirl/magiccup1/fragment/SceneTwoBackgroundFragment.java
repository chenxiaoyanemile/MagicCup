package com.example.sweetgirl.magiccup1.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.Bean.Item;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.ui.recycler.SpaceItemDecoration;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.example.sweetgirl.magiccup1.view.recycleView.EndLessOnScrollListener;
import com.example.sweetgirl.magiccup1.view.recycleView.SceneTwoRecyclerViewAdapter;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.alien95.util.Utils.getSharedPreferences;


public class SceneTwoBackgroundFragment extends Fragment {

    private static final String TAG = LogUtil.makeLogTag(SceneTwoBackgroundFragment.class);

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private SceneTwoRecyclerViewAdapter sceneTwoRecyclerViewAdapter;

    private String mName="第三幕樱花树";
    private String mId="ab1cdab7-1fca-4ce3-8ef7-777a8dgad";
    private String mResource="http://ojphnknti.bkt.clouddn.com/scene31/BGYingHua.assetbundle";


    ArrayList<Item> mItem=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_scene_two_background, container, false);

        initView(view);
        initData();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        sceneTwoRecyclerViewAdapter=new SceneTwoRecyclerViewAdapter(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(12,8,12,30));
        recyclerView.setAdapter(sceneTwoRecyclerViewAdapter);
        sceneTwoRecyclerViewAdapter.setItems(mItem);
        sceneTwoRecyclerViewAdapter.setOnItemClickListener(new SceneTwoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                mName=mItem.get(position).getName();
                mId=mItem.get(position).getId();
                mResource=mItem.get(position).getResource();

                //saveData(mName,mId,mResource);   //保存选在的情景

                L.d(TAG,"选择了背景"+mName+mId+mResource);
                Toast.makeText(getActivity(),"你选择了"+mName,Toast.LENGTH_SHORT).show();
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sceneTwoRecyclerViewAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });

        saveData(mName,mId,mResource);   //保存选在的情景
        L.d(TAG,"用户的选择保存到本地");

        return view;
    }

    public void initView(View view){
        recyclerView=(RecyclerView)view.findViewById(R.id.new_recyclerView);
        mRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.layout_swipe_refresh);

    }
    //[6]保存数据
    private void saveData(String data,String idData,String resourceData){

        SharedPreferences preferences=this.getActivity().getSharedPreferences("SceneTwo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("backgroundName", data);
        editor.putString("backgroundId",idData);
        editor.putString("backgroundResource",resourceData);
        editor.apply();
        L.i(TAG,"保存用户选择的 background"+data+idData+resourceData);

    }


    public void initData(){
        doGet();
        L.i(TAG,"初始化数据");

    }

    //每次上拉加载的时候，给RecyclerView的后面添加了10条数据数据
    private void loadMoreData(){
        for (int i =0; i < 10; i++){
            // mData.add("嘿，我是“上拉加载”生出来的"+i);
            sceneTwoRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


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
        //[2]构造Request   "http://139.199.190.245:8010/api/scene/1
        Request request = new Request.Builder()
                .url("http://139.199.190.245:8010/api/scene/31")
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
                jsonParser(res);

            }
        });
    }
    //[4]解析数据
    private List<Item> jsonParser(String jsonData) {
        try {
            JSONTokener jsonTokener = new JSONTokener(jsonData);
            org.json.JSONObject jsonObject = (org.json.JSONObject) jsonTokener.nextValue();
            org.json.JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                org.json.JSONObject object = array.getJSONObject(i);
                String name=object.getString("name");
                String image=object.getString("image");
                String id=object.getString("id");
                String resource=object.getString("resource");
                L.i(TAG,"背景"+name);
                L.i(TAG,"背景"+image);
                L.d(TAG,"背景"+id);
                L.d(TAG,"背景"+resource);
                mItem.add(new Item(name, image,id,resource));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mItem;
    }
}
