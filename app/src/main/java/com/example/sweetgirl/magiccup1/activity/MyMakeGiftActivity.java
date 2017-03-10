package com.example.sweetgirl.magiccup1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.example.sweetgirl.magiccup1.view.recycleView.RecyclerViewActivity;


public class MyMakeGiftActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String LOGTAG = LogUtil.makeLogTag(MyMakeGiftActivity.class);

    private Button btn_gift_story;
    private Button btn_gift_scene;
    private Button btn_gift_letter;
    private Button btn_gift_preview;

    private String story;
    private String scene;
    private String letter;

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
                startActivity(intent1);
                L.i(LOGTAG,"选择故事");
                break;
            case R.id.btn_gift_scene:
                Intent intent2=new Intent(MyMakeGiftActivity.this,MyGiftScene2Activity.class);
                startActivity(intent2);
                L.i(LOGTAG,"选择情景");
                break;
            case R.id.btn_gift_letter:
                Intent intent3=new Intent(MyMakeGiftActivity.this,MyGiftScene3Activity.class);
                startActivity(intent3);
                L.i(LOGTAG,"写信");
                break;
            case R.id.btn_gift_preview:
                Toast.makeText(getApplicationContext(), "加载中...", Toast.LENGTH_SHORT).show();
                Intent intent4=new Intent(MyMakeGiftActivity.this,ShowARActivity.class);
                startActivity(intent4);
                L.i(LOGTAG,"写信");
                break;

        }

    }
}
