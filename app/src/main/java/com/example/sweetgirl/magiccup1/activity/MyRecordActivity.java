package com.example.sweetgirl.magiccup1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;

public class MyRecordActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView image_record_back;
    private TextView view_record_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        initView();
    }
    //[1]初始化控件
    public void initView(){

        image_record_back=(ImageView)findViewById(R.id.image_record_back);
        view_record_delete=(TextView)findViewById(R.id.view_record_delect);

        image_record_back.setOnClickListener(this);
        view_record_delete.setOnClickListener(this);
    }
    //[2]响应点击事件
    public void onClick(View view){
        switch (view.getId()){
            case R.id.image_record_back:
                finish();
                break;
            case R.id.view_record_delect:
                Toast.makeText(getApplicationContext(), "什么都没有！",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
