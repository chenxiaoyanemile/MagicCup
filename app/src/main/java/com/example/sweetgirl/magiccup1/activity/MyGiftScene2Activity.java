package com.example.sweetgirl.magiccup1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.fragment.SceneTwoBackgroundFragment;
import com.example.sweetgirl.magiccup1.fragment.SceneTwoSceneFragment;
import com.example.sweetgirl.magiccup1.fragment.SceneTwoWeatherFragment;


import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;



public class MyGiftScene2Activity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = LogUtil.makeLogTag(MyGiftScene2Activity.class);

    private ImageView set_scene_toolbar_back;

    private TextView scene2_tv_weather;
    private TextView scene2_tv_background;
    private TextView scene2_tv_scene;


    private Fragment sceneTwoWeatherFragment,backgroundFragment,sceneFragment,contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift_scene2);
        initView();
        initTab();
    }

    private void initView(){

        set_scene_toolbar_back=(ImageView)findViewById(R.id.set_scene_toolbar_back);
        set_scene_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyGiftScene2Activity.this.getIntent();
                intent.putExtra("data2", "2、天气");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        scene2_tv_weather=(TextView)findViewById(R.id.scene2_tv_weather);
        scene2_tv_background=(TextView)findViewById(R.id.scene2_tv_background);
        scene2_tv_scene=(TextView)findViewById(R.id.scene2_tv_scene);

        scene2_tv_weather.setOnClickListener(this);
        scene2_tv_background.setOnClickListener(this);
        scene2_tv_scene.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.set_scene_toolbar_back:
                Intent intent = MyGiftScene2Activity.this.getIntent();
                intent.putExtra("data2", "2、天气");
                setResult(RESULT_OK, intent);
                break;
            case R.id.scene2_tv_weather:
                clickWeather();
                scene2_tv_weather.setTextColor(Color.rgb(255, 255, 255));
                scene2_tv_background.setTextColor(Color.rgb(51, 51, 51));
                scene2_tv_scene.setTextColor(Color.rgb(51, 51, 51));
                break;
            case R.id.scene2_tv_background:
                clickBackground();
                scene2_tv_weather.setTextColor(Color.rgb(51, 51, 51));
                scene2_tv_background.setTextColor(Color.rgb(255, 255, 255));
                scene2_tv_scene.setTextColor(Color.rgb(51, 51, 51));
                break;
            case R.id.scene2_tv_scene:
                clickScene();
                scene2_tv_weather.setTextColor(Color.rgb(51, 51, 51));
                scene2_tv_background.setTextColor(Color.rgb(51, 51, 51));
                scene2_tv_scene.setTextColor(Color.rgb(255, 255, 255));
                break;

        }
    }

    //[2]初始化底部按钮
    public void initTab(){
        if(sceneTwoWeatherFragment==null){
            sceneTwoWeatherFragment=new SceneTwoWeatherFragment();   //天气
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.dep_fragment,sceneTwoWeatherFragment).commit();
        contentFragment=sceneTwoWeatherFragment;
    }
    //[3]响应点击事件
    @SuppressLint("CommitTransaction")
    public void clickWeather(){
        if(sceneTwoWeatherFragment==null){
            sceneTwoWeatherFragment=new SceneTwoWeatherFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),sceneTwoWeatherFragment);
    }
    @SuppressLint("CommitTransaction")
    public void clickBackground(){
        if(backgroundFragment==null){
            backgroundFragment=new SceneTwoBackgroundFragment();   //背景
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),backgroundFragment);
    }
    @SuppressLint("CommitTransaction")
    public void clickScene(){
        if(sceneFragment==null){
            sceneFragment=new SceneTwoSceneFragment();       //情景
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),sceneFragment);
    }
    //[4]替换fragment
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {

        if (contentFragment == fragment)
            return;

        if (!fragment.isAdded()) {
            transaction.hide(contentFragment)
                    .add(R.id.dep_fragment, fragment).commit();
        } else {
            transaction.hide(contentFragment).show(fragment).commit();
        }
        contentFragment = fragment;
    }
    //手机系统返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.i(TAG, "back键 ");
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = MyGiftScene2Activity.this.getIntent();
            intent.putExtra("data2", "2、天气");
            setResult(RESULT_OK, intent);
            this.finish();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
