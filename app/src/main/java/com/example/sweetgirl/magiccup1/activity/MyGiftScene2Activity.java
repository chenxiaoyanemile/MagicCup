package com.example.sweetgirl.magiccup1.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.fragment.BackgroundFragment;
import com.example.sweetgirl.magiccup1.fragment.SceneFragment;
import com.example.sweetgirl.magiccup1.fragment.WeatherFragment;

import com.example.sweetgirl.magiccup1.util.LogUtil;


public class MyGiftScene2Activity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = LogUtil.makeLogTag(MyGiftScene2Activity.class);

    private TextView scene2_tv_weather;
    private TextView scene2_tv_background;
    private TextView scene2_tv_scene;


    private Fragment weatherFragment,backgroundFragment,sceneFragment,contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift_scene2);
        initView();
        initTab();
    }

    private void initView(){

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
        if(weatherFragment==null){
            weatherFragment=new WeatherFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.dep_fragment,weatherFragment).commit();
        contentFragment=weatherFragment;
    }
    //[3]响应点击事件
    @SuppressLint("CommitTransaction")
    public void clickWeather(){
        if(weatherFragment==null){
            weatherFragment=new WeatherFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),weatherFragment);
    }
    @SuppressLint("CommitTransaction")
    public void clickBackground(){
        if(backgroundFragment==null){
            backgroundFragment=new BackgroundFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),backgroundFragment);
    }
    @SuppressLint("CommitTransaction")
    public void clickScene(){
        if(sceneFragment==null){
            sceneFragment=new SceneFragment();
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

}