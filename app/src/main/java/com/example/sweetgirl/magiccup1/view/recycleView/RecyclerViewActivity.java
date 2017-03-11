package com.example.sweetgirl.magiccup1.view.recycleView;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.Bean.TextImage;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.ui.recycler.SpaceItemDecoration;
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

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = LogUtil.makeLogTag(RecyclerViewActivity.class);

    private ImageView set_toolbar_back;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private MyRecycleViewAdapter mmAdapter;


    private TextImage textImage;

    private String mName;

    ArrayList<TextImage> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
        initData();

        mLinearLayoutManager = new LinearLayoutManager(this);

        mmAdapter=new MyRecycleViewAdapter(RecyclerViewActivity.this);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(12,8,12,30));
        mRecyclerView.setAdapter(mmAdapter);
        mmAdapter.setTextImages(list);
        mmAdapter.setOnItemClickListener(new MyRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view,int position) {
                mName=list.get(position).getName();
                Toast.makeText(RecyclerViewActivity.this,"你选择了"+mName,Toast.LENGTH_SHORT).show();
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                //我在List最前面加入一条数据
               // mData.add(0, "嘿，我是“下拉刷新”生出来的");

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mmAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });

    }

    //每次上拉加载的时候，给RecyclerView的后面添加了10条数据数据
    private void loadMoreData(){
        for (int i =0; i < 10; i++){
           // mData.add("嘿，我是“上拉加载”生出来的"+i);
            mmAdapter.notifyDataSetChanged();
        }
    }

    public void initView(){
        //toolbar上的back键
        set_toolbar_back=(ImageView)findViewById(R.id.set_toolbar_back);
        set_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RecyclerViewActivity.this.getIntent();
                intent.putExtra("data1", "1、"+mName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mRecyclerView=(RecyclerView) findViewById(R.id.new_recyclerView);
        mRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.layout_swipe_refresh);
    }

    public void initData(){
       doGet();
        L.i(TAG,"初始化数据");

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
                jsonParser(res);

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
                textImage=new TextImage(image,name,dep);
                list.add(textImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //手机系统返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.i(TAG, "back键 ");
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = RecyclerViewActivity.this.getIntent();
            intent.putExtra("data1","1、"+mName);
            setResult(RESULT_OK, intent);
            this.finish();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
