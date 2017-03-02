package com.example.sweetgirl.magiccup1.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.activity.MyHelpActivity;
import com.example.sweetgirl.magiccup1.activity.MyMakeGiftActivity;
import com.example.sweetgirl.magiccup1.activity.MyRecordActivity;
import com.example.sweetgirl.magiccup1.activity.MySetActivity;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;


public class MyFragment extends Fragment implements View.OnClickListener{
    private static final String LOGTAG = LogUtil.makeLogTag(MyFragment.class);

    private RelativeLayout layout_my_record;
    private RelativeLayout layout_my_gift;
    private RelativeLayout layout_my_card;
    private RelativeLayout layout_my_set;
    private RelativeLayout layout_my_help;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }
    //[1]初始化界面
    public void initView(View v){

        layout_my_record=(RelativeLayout)v.findViewById(R.id.layout_my_record);
        layout_my_gift=(RelativeLayout)v.findViewById(R.id.layout_my_gift);
        layout_my_card=(RelativeLayout)v.findViewById(R.id.layout_my_card);
        layout_my_set=(RelativeLayout)v.findViewById(R.id.layout_my_set);
        layout_my_help=(RelativeLayout)v.findViewById(R.id.layout_my_help);

        layout_my_record.setOnClickListener(this);
        layout_my_gift.setOnClickListener(this);
        layout_my_card.setOnClickListener(this);
        layout_my_set.setOnClickListener(this);
        layout_my_help.setOnClickListener(this);
    }
    //[2]点击事件
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.layout_my_record:
                Intent intent0=new Intent(getActivity(), MyRecordActivity.class);
                MyFragment.this.startActivity(intent0);
                L.i(LOGTAG,"我的记录");
                break;
            case R.id.layout_my_gift:
                Intent intent1=new Intent(getActivity(), MyMakeGiftActivity.class);
                MyFragment.this.startActivity(intent1);
                L.i(LOGTAG,"制作礼物");
                break;
            case R.id.layout_my_card:
                Toast.makeText(getActivity(),"将带领你穿越到magic cup 的网页世界！",Toast.LENGTH_SHORT).show();
                L.i(LOGTAG,"制作贺卡");
                break;
            case R.id.layout_my_set:
                Intent intent3=new Intent(getActivity(), MySetActivity.class);
                MyFragment.this.startActivity(intent3);
                L.i(LOGTAG,"设置");
                break;
            case R.id.layout_my_help:
                Intent intent4=new Intent(getActivity(), MyHelpActivity.class);
                MyFragment.this.startActivity(intent4);
                L.i(LOGTAG,"帮助");
                break;
        }
    }


}
