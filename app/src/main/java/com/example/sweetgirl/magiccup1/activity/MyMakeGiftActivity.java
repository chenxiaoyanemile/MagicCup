package com.example.sweetgirl.magiccup1.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.model.TallScene;
import com.example.sweetgirl.magiccup1.util.CreateJson;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.example.sweetgirl.magiccup1.view.recycleView.RecyclerViewActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
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

    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    private String story;
    private String storyId;
    private String storyResource;
    private String scene;
    private String sceneId;
    private String weatherId;
    private String backgroundId;

    private String letter;
    private String letterContent;

    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_make_gift);

        initView();
    }
    //[1]找到相关控件
    public void initView(){
        btn_gift_story=(Button)findViewById(R.id.btn_gift_story);
        btn_gift_scene=(Button)findViewById(R.id.btn_gift_scene);
        btn_gift_letter=(Button)findViewById(R.id.btn_gift_letter);
        btn_gift_preview=(Button)findViewById(R.id.btn_gift_preview);

        btn_gift_story.setOnClickListener(this);
        btn_gift_scene.setOnClickListener(this);
        btn_gift_letter.setOnClickListener(this);
        btn_gift_preview.setOnClickListener(this);

    }
    //[2]设置默认值
    public void initData(){

    }
    //[3]响应点击事件
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_gift_story:
                Intent intent1=new Intent(MyMakeGiftActivity.this,RecyclerViewActivity.class);
                //startActivity(intent1);
                startActivityForResult(intent1, MY_REQUEST_CODE1);
                L.i(TAG,"选择故事");
                break;
            case R.id.btn_gift_scene:
                Intent intent2=new Intent(MyMakeGiftActivity.this,MyGiftScene2Activity.class);
                //startActivity(intent2);
                startActivityForResult(intent2, MY_REQUEST_CODE2);
                L.i(TAG,"选择情景");
                break;
            case R.id.btn_gift_letter:
                Intent intent3=new Intent(MyMakeGiftActivity.this,MyGiftScene3Activity.class);
                //startActivity(intent3);
                startActivityForResult(intent3, MY_REQUEST_CODE3);
                L.i(TAG,"写信");
                break;
            case R.id.btn_gift_preview:
                showSimpleDialog(view);
                //Toast.makeText(getApplicationContext(), "加载中...", Toast.LENGTH_SHORT).show();
                //Intent intent4=new Intent(MyMakeGiftActivity.this,ShowARActivity.class);
                //startActivity(intent4);
                L.i(TAG,"写信");
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
                String storyId=data.getExtras().getString("dataId");
                String storyResource=data.getExtras().getString("dataResource");

                L.d(TAG,"第二幕选择的是"+story);
                L.d(TAG,"场景id"+storyId);
                L.d(TAG,"场景资源"+storyResource);

                btn_gift_story.setText(story);
                break;
            case MY_REQUEST_CODE2:
                scene=data.getExtras().getString("data2");  //选择的天气、背景、情景

                btn_gift_scene.setText(scene);
                break;
            case MY_REQUEST_CODE3:
                letter=data.getExtras().getString("data3");

                letterContent=data.getExtras().getString("data4");  //信的内容

                L.d(TAG,letterContent);
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
                Toast.makeText(getApplicationContext(), "开始预览，加载中...", Toast.LENGTH_LONG).show();
                Intent intent4=new Intent(MyMakeGiftActivity.this,ShowARActivity.class);
                startActivity(intent4);
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
        SharedPreferences preferences= PreferenceManager.
                getDefaultSharedPreferences(this);
        user_id=preferences.getString("user_id","");
        L.i(TAG,""+user_id);
    }
    //[4]提交选择的结果到服务器
    public boolean doPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getUserId();
                    enqueue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }
    private void enqueue(){
        //[1]拿到OkHttpClient
        OkHttpClient client = new OkHttpClient();

        //[2]构造Request
        RequestBody requestBody = new FormEncodingBuilder()
                .add("user_id",user_id)
                .add("text", letterContent)
                .add("scene2_id", storyId)
                .add("scene31_id", sceneId)
                .add("scene32_id", weatherId)
                .add("scene33_id", backgroundId)
                .add("scene4_id", "643abd25-ffec-11e6-9354-a4db303d2fd7")
                .build();

        Request request = new Request.Builder()
                .url("http://10.110.101.201:8010/api/between")
                .post(requestBody)
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
    }

    private void CreateUnityData(){

        TallScene scene = new TallScene();

        scene.getSecondScene().setAction("secondanimation.assetbundle");   //story
        scene.getSecondScene().setBackground("");

        scene.getThirdScene().setAction("ACHuge.assetbundle");      //scene
        scene.getThirdScene().setBackground("BGYinXing.assetbundle");
        scene.getThirdScene().setText(letterContent);    //信中的内容
        scene.getThirdScene().setTime("ACHuge.assetbundle");

        scene.getFourScene().setText("自定义文字");   //
        scene.getFourScene().setInjection("huapen.assetbundle");

        String first= CreateJson.createJson(scene);
    }

}
