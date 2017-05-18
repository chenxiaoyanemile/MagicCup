package com.example.sweetgirl.magiccup1.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.sweetgirl.magiccup1.Bean.SelectGiftResponseData;
import com.example.sweetgirl.magiccup1.Bean.TatalSelectData;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.util.CreateSelectDataJson;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.example.sweetgirl.magiccup1.view.recycleView.RecyclerViewActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import java.io.IOException;



public class MyMakeGiftActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = LogUtil.makeLogTag(MyMakeGiftActivity.class);

    public final static int MY_REQUEST_CODE1 = 0;
    public final static int MY_REQUEST_CODE2 = 1;
    public final static int MY_REQUEST_CODE3 = 2;

    private Button btn_gift_story;
    private Button btn_gift_scene;
    private Button btn_gift_letter;
    private Button btn_gift_preview;

    private ImageView set_toolbar_back;

    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    private String scene1_id;

    private String story;
    private String storyId;



    private String scene;
    private String sceneId;


    private String weatherId;

    private String backgroundId;

    private String letter;
    private String letterContent;           //传送给unity的

    private String scene4_id;

    private String user_id;

    private String url="http://139.199.190.245:8010/api/between";
    private String jsonData;

    private int judgeCode=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_make_gift);

        initView();
        initData();


    }
    //[1]找到相关控件
    public void initView(){

        btn_gift_story=(Button)findViewById(R.id.btn_gift_story);
        btn_gift_scene=(Button)findViewById(R.id.btn_gift_scene);
        btn_gift_letter=(Button)findViewById(R.id.btn_gift_letter);
        btn_gift_preview=(Button)findViewById(R.id.btn_gift_preview);

        set_toolbar_back=(ImageView)findViewById(R.id.set_toolbar_back);

        btn_gift_story.setOnClickListener(this);
        btn_gift_scene.setOnClickListener(this);
        btn_gift_letter.setOnClickListener(this);
        btn_gift_preview.setOnClickListener(this);

        set_toolbar_back.setOnClickListener(this);

    }
    //[2]设置默认值
    public void initData(){


        scene1_id="9776bc5e-cdda-40d3-b031-f142d0d1d760";

        storyId="9776bc5e-cdda-40d3-b031-f142d0d1d760";


        sceneId="ab1cdab7-1fca-4ce3-8ef7-777a8dksa";


        weatherId="ab1cdab7-1fca-4ce3-8ef7-777a8d7dk";


        backgroundId="ab1cdab7-1fca-4ce3-8ef7-777a8djxa";

        letterContent="I LOVE YOU";

        L.d(TAG,"默认值");

        scene4_id="ab1cdab7-1fca-4ce3-8ef7-777ajxjw";

    }
    //[3]响应点击事件
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.set_toolbar_back:
                Intent intent=new Intent(MyMakeGiftActivity.this,MainPageActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_gift_story:
                Intent intent1=new Intent(MyMakeGiftActivity.this,RecyclerViewActivity.class);

                startActivityForResult(intent1, MY_REQUEST_CODE1);
                L.i(TAG,"选择故事");
                break;
            case R.id.btn_gift_scene:
                Intent intent2=new Intent(MyMakeGiftActivity.this,MyGiftScene2Activity.class);

                startActivityForResult(intent2, MY_REQUEST_CODE2);
                L.i(TAG,"选择情景");
                break;
            case R.id.btn_gift_letter:
                Intent intent3=new Intent(MyMakeGiftActivity.this,MyGiftScene3Activity.class);

                startActivityForResult(intent3, MY_REQUEST_CODE3);
                L.i(TAG,"写信");
                break;
            case R.id.btn_gift_preview:

                showSimpleDialog(view);

                L.i(TAG,"预览");
                break;

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        L.i(TAG,"成功返回数据");
        switch (requestCode)
        {
            case MY_REQUEST_CODE1:
                story=data.getExtras().getString("data1");    //选择的第二幕
                storyId=data.getExtras().getString("dataId");


                L.d(TAG,"第二幕选择的是"+story);
                L.d(TAG,"场景id"+storyId);


                btn_gift_story.setText(story);

                break;
            case MY_REQUEST_CODE2:

                scene=data.getExtras().getString("data2");  //选择的天气、背景、情景

                btn_gift_scene.setText(scene);

                break;
            case MY_REQUEST_CODE3:
                letter=data.getExtras().getString("data3");

                letterContent=data.getExtras().getString("data4");  //信的内容

                L.d(TAG,"信的内容"+letterContent);
                btn_gift_letter.setText(letter);
                break;
        }
    }


    //显示基本Dialog
    private void showSimpleDialog(View view) {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_dialog);
        builder.setMessage(R.string.dialog_message);

        //监听下方button点击事件
        builder.setPositiveButton(R.string.postive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                getUserId();   //获取到用户id;
                L.d(TAG,"获取到用户id");

                getBackData();   //获取返回的值

                CreateRequestData();  //将所选信息汇聚称json数据
                L.d(TAG,"将所选信息汇聚称json数据");


                L.d(TAG,"提交选择的项到服务器");

                if (doPost()){

                    Intent intent=new Intent(MyMakeGiftActivity.this,DownloadActivity.class);
                    startActivity(intent);
                    L.d(TAG,"预览选择的信息");

                }

                else {

                    Toast.makeText(getApplicationContext(), "预览失败", Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.toast_negative, Toast.LENGTH_SHORT).show();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    //从sharedPreferences文件中读取存储的user_id
    private void getUserId(){

        SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id=preferences.getString("user_id", "user_id");

        L.i(TAG,"从sharedPreferences文件中读取存储的user_id"+user_id);

    }

    //从sharedPreferences文件中读取存储的选择的信息
    private void getBackData(){

        SharedPreferences preferences=getSharedPreferences("SceneTwo", Context.MODE_PRIVATE);

        sceneId=preferences.getString("sceneId", "sceneId");

        weatherId=preferences.getString("weatherId", "weatherId");

        backgroundId=preferences.getString("backgroundId", "backgroundId");

        L.i(TAG,"从sharedPreferences文件中读取存储的sceneId"+sceneId+weatherId+backgroundId);

    }
    //将所选信息创建json值
    public String CreateRequestData(){

        TatalSelectData tatalSelectData=new TatalSelectData();

        tatalSelectData.setUser_id(user_id);
        tatalSelectData.setText(letterContent);
        tatalSelectData.setScene1_id(scene1_id);
        tatalSelectData.setScene2_id(storyId);
        tatalSelectData.setScene31_id(sceneId);
        tatalSelectData.setScene32_id(weatherId);
        tatalSelectData.setScene33_id(backgroundId);
        tatalSelectData.setScene4_id(scene4_id);

        jsonData= CreateSelectDataJson.CreateSelectDataJson(tatalSelectData);

        return jsonData;
    }

    //[4]提交选择的结果到服务器   doPost();
    public boolean doPost(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SendDataToPost(url,jsonData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }
    //提交数据到服务器
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private void SendDataToPost(String url, String json){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        //[3]将Request封装为call
        Call call = client.newCall(request);
        //[4]执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                L.i(TAG, "onFailure" + e.getMessage());
                e.printStackTrace();

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                parseSuc(result);
                L.i(TAG, "onResponse" + result);
            }
        });

    }


    public void parseSuc(String s){
        L.d(TAG,"解析返回提交选项后返回的数据");

        try{
            L.d(TAG,""+s);
            //json数据解析成一个对象
            SelectGiftResponseData selectGiftResponseData= com.alibaba.fastjson.JSON.parseObject(s,SelectGiftResponseData.class);
            String message = selectGiftResponseData.getMsg();
            L.i("message"," "+message);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }


}
