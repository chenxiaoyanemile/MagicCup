package com.example.sweetgirl.magiccup1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.fragment.ARFragment;
import com.example.sweetgirl.magiccup1.fragment.MyFragment;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;

public class MainPageActivity extends AppCompatActivity {
    private static final String TAG = LogUtil.makeLogTag(MainPageActivity.class);

    private Fragment arFragment,myFragment,currentFragment;

    private ImageView iv_page_scan;
    private TextView tv_page_scan;
    private ImageView iv_page_my;
    private TextView tv_page_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();
        initTab();
    }
    //[1]初始化控件
    private void init(){

        LinearLayout layout_main_ar = (LinearLayout) findViewById(R.id.layout_main_ar);
        LinearLayout layout_main_my = (LinearLayout) findViewById(R.id.layout_main_my);

        iv_page_scan=(ImageView)findViewById(R.id.iv_page_scan);
        tv_page_scan=(TextView)findViewById(R.id.tv_page_scan);
        iv_page_my=(ImageView)findViewById(R.id.iv_page_my);
        tv_page_my=(TextView)findViewById(R.id.tv_page_my);


        layout_main_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_page_scan.setImageResource(R.mipmap.bg_scan_h);
                tv_page_scan.setTextColor(Color.rgb(255, 255, 255));
                iv_page_my.setImageResource(R.mipmap.bg_me_n);
                tv_page_my.setTextColor(Color.rgb(102, 102, 102));
                clickScan();
                L.i(TAG,"ar扫描");

            }
        });
        layout_main_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_page_scan.setImageResource(R.mipmap.bg_scan_n);
                tv_page_scan.setTextColor(Color.rgb(102, 102, 102));
                iv_page_my.setImageResource(R.mipmap.bg_me_h);
                tv_page_my.setTextColor(Color.rgb(255, 255, 255));
                clickMe();
                L.i(TAG,"个人中心");
            }
        });

    }
    //[2]初始化底部按钮
    public void initTab(){
        if(arFragment==null){
            arFragment=new ARFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_fragment,arFragment).commit();
        currentFragment=arFragment;
    }
    //[3]响应点击事件
    @SuppressLint("CommitTransaction")
    public void clickScan(){
        if(arFragment==null){
            arFragment=new ARFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),arFragment);
    }
    @SuppressLint("CommitTransaction")
    public void clickMe(){
        if(myFragment==null){
            myFragment=new MyFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),myFragment);
    }
    //[4]替换fragment
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {

        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.content_fragment, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}
