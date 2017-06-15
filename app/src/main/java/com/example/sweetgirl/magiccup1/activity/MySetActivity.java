package com.example.sweetgirl.magiccup1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;

public class MySetActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String LOGTAG = LogUtil.makeLogTag(MySetActivity.class);

    private RelativeLayout view_set_clean;
    private RelativeLayout view_set_feedback;
    private RelativeLayout view_set_about;

    private ImageView set_toolbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_set);

        init();

    }
    //[1]找到相关控件
    public void init(){
        view_set_clean=(RelativeLayout)findViewById(R.id.view_set_clear);
        view_set_feedback=(RelativeLayout)findViewById(R.id.view_set_feedback);
        view_set_about=(RelativeLayout)findViewById(R.id.view_set_about);

        set_toolbar_back=(ImageView)findViewById(R.id.set_toolbar_back);

        set_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MySetActivity.this,MainPageActivity.class);
                startActivity(intent);
                finish();

            }
        });

        view_set_clean.setOnClickListener(this);
        view_set_feedback.setOnClickListener(this);
        view_set_about.setOnClickListener(this);
    }
    //[2]相应点击事件
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.view_set_clear:
                Toast.makeText(getApplicationContext(), "什么都没有！",Toast.LENGTH_SHORT).show();
                L.i(LOGTAG,"清除缓存");
                break;
            case R.id.view_set_feedback:
                Intent intent1=new Intent(MySetActivity.this,MySetBackActivity.class);
                startActivity(intent1);
                L.i(LOGTAG,"用户反馈");
                break;
            case R.id.view_set_about:
                Intent intent2=new Intent(MySetActivity.this,MySetAboutActivity.class);
                startActivity(intent2);
                L.i(LOGTAG,"关于magic cup");
                break;

        }
    }
}
