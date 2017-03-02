package com.example.sweetgirl.magiccup1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sweetgirl.magiccup1.Bean.Item;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.ui.ItemViewHolder;
import com.example.sweetgirl.magiccup1.ui.recycler.RefreshRecyclerView;
import com.example.sweetgirl.magiccup1.ui.recycler.SpaceItemDecoration;
import com.example.sweetgirl.magiccup1.ui.recycler.adapter.Action;
import com.example.sweetgirl.magiccup1.ui.recycler.adapter.MultiTypeAdapter;
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


public class SceneFragment extends Fragment {
    private static final String TAG = LogUtil.makeLogTag(SceneFragment.class);
    private RefreshRecyclerView scene2_recycle_scene;

    List<Item> mItem=new ArrayList<Item>();
    private MultiTypeAdapter mAdapter;
    private int mPage = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_scene, container, false);
        init(view);
        return view;
    }

    private void init(View view){

        mAdapter=new MultiTypeAdapter(getActivity());
        scene2_recycle_scene=(RefreshRecyclerView)view.findViewById(R.id.scene2_recycler_scene);
        scene2_recycle_scene.setSwipeRefreshColors(0xFF437845,0xFFE44F98,0xFF2FAC21);
        scene2_recycle_scene.addItemDecoration(new SpaceItemDecoration(12,8,12,30));
        scene2_recycle_scene.setLayoutManager(new LinearLayoutManager(getActivity()));
        scene2_recycle_scene.setAdapter(mAdapter);
        scene2_recycle_scene.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(true);
            }
        });
        scene2_recycle_scene.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(false);
            }
        });
        scene2_recycle_scene.post(new Runnable() {
            @Override
            public void run() {
                scene2_recycle_scene.showSwipeRefresh();
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
        scene2_recycle_scene.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    mAdapter.clear();
                    scene2_recycle_scene.dismissSwipeRefresh();
                }
                doGet();
                mAdapter.addAll(ItemViewHolder.class, mItem);
                if (mPage >= 3) {
                    mAdapter.showNoMore();
                }
            }
        }, 2000);
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
        //[2]构造Request   http://139.199.190.245:8010/api/user
        Request request = new Request.Builder()
                .url("http://139.199.190.245:8010/api/scene/33")
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
                mItem=jsonParser(res);

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
                L.i(TAG,"背景"+name);
                L.i(TAG,"背景"+image);
                mItem.add(new Item(name, image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mItem;
    }
}
