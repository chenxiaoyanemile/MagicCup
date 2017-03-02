package com.example.sweetgirl.magiccup1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOGTAG = LogUtil.makeLogTag(SelectActivity.class);
    private String user_id;

    private Button btn_select_my_boy;
    private Button btn_select_my_girl;

    private Button btn_select_he_girl;
    private Button btn_select_he_boy;

    private TextView view_select_aquarius,view_select_pisces,view_select_aries;
    private TextView view_select_taurus,view_select_gemini,view_select_cancer;
    private TextView view_select_leo,view_select_virgo,view_select_libra;
    private TextView view_select_scorpio,view_select_sagittarius,view_select_capricorn;

    private TextView view_select_aquarius_ta,view_select_pisces_ta,view_select_aries_ta;
    private TextView view_select_taurus_ta,view_select_gemini_ta,view_select_cancer_ta;
    private TextView view_select_leo_ta,view_select_virgo_ta,view_select_libra_ta;
    private TextView view_select_scorpio_ta,view_select_sagittarius_ta,view_select_capricorn_ta;

    private int size1;
    private int size2;

    private TextView tv_size;
    private TextView tv_size2;

    private HorizontalScrollView mHorizontalScrollView;
    private String[] arrText;
    private LayoutInflater mInflater;

    private Button btn_select_finish;

    private String constellation1;
    private String constellation2;
    private String sex1;
    private String sex2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        mInflater = LayoutInflater.from(this);

        //initData();
        initView();
        //scrollViewInit();
    }
    //[1]设置默认值
    public void initData(){

         arrText = new String[]{
                "水瓶座", "双鱼座", "白羊座",
                "金牛座", "双子座", "巨蟹座",
                "狮子座", "处女座", "天平座",
                "天蝎座", "射手座", "摩羯座"
        };

    }
    //[2]初始化控件
    public void initView(){

        btn_select_my_boy=(Button)findViewById(R.id.btn_select_my_boy);
        btn_select_my_girl=(Button)findViewById(R.id.btn_select_my_girl);
        btn_select_he_boy=(Button)findViewById(R.id.btn_select_he_boy);
        btn_select_he_girl=(Button)findViewById(R.id.btn_select_he_girl);
        btn_select_finish=(Button)findViewById(R.id.btn_select_finish);

        view_select_aquarius=(TextView)findViewById(R.id.view_select_Aquarius);
        view_select_aquarius_ta=(TextView)findViewById(R.id.view_select_Aquarius2);

        view_select_pisces=(TextView)findViewById(R.id.view_select_Pisces);
        view_select_pisces_ta=(TextView)findViewById(R.id.view_select_Pisces2);

        view_select_aries=(TextView)findViewById(R.id.view_select_Aries);
        view_select_aries_ta=(TextView)findViewById(R.id.view_select_Aries2);

        view_select_taurus=(TextView)findViewById(R.id.view_select_Taurus);
        view_select_taurus_ta=(TextView)findViewById(R.id.view_select_Taurus2);

        view_select_gemini=(TextView)findViewById(R.id.view_select_Gemini);
        view_select_gemini_ta=(TextView)findViewById(R.id.view_select_Gemini2);

        view_select_cancer=(TextView)findViewById(R.id.view_select_Cancer);
        view_select_cancer_ta=(TextView)findViewById(R.id.view_select_Cancer2);

        view_select_leo=(TextView)findViewById(R.id.view_select_Leo);
        view_select_leo_ta=(TextView)findViewById(R.id.view_select_Leo2);

        view_select_virgo=(TextView)findViewById(R.id.view_select_Virgo);
        view_select_virgo_ta=(TextView)findViewById(R.id.view_select_Virgo2);

        view_select_libra=(TextView)findViewById(R.id.view_select_Libra);
        view_select_libra_ta=(TextView)findViewById(R.id.view_select_Libra2);

        view_select_scorpio=(TextView)findViewById(R.id.view_select_Scorpio);
        view_select_scorpio_ta=(TextView)findViewById(R.id.view_select_Scorpio2);

        view_select_sagittarius=(TextView)findViewById(R.id.view_select_Sagittarius);
        view_select_sagittarius_ta=(TextView)findViewById(R.id.view_select_Sagittarius2);

        view_select_capricorn=(TextView)findViewById(R.id.view_select_Capricorn);
        view_select_capricorn_ta=(TextView)findViewById(R.id.view_select_Capricorn2);

        tv_size=(TextView)findViewById(R.id.tv_size);
        tv_size2=(TextView)findViewById(R.id.tv_size2);


        btn_select_my_boy.setOnClickListener(this);
        btn_select_my_girl.setOnClickListener(this);

        view_select_aquarius.setOnClickListener(this);
        view_select_aquarius_ta.setOnClickListener(this);

        view_select_pisces.setOnClickListener(this);
        view_select_pisces_ta.setOnClickListener(this);

        view_select_aries.setOnClickListener(this);
        view_select_aries_ta.setOnClickListener(this);

        view_select_taurus.setOnClickListener(this);
        view_select_taurus_ta.setOnClickListener(this);

        view_select_gemini.setOnClickListener(this);
        view_select_gemini_ta.setOnClickListener(this);

        view_select_cancer.setOnClickListener(this);
        view_select_cancer_ta.setOnClickListener(this);

        view_select_leo.setOnClickListener(this);
        view_select_leo_ta.setOnClickListener(this);

        view_select_virgo.setOnClickListener(this);
        view_select_virgo_ta.setOnClickListener(this);

        view_select_libra.setOnClickListener(this);
        view_select_libra_ta.setOnClickListener(this);

        view_select_scorpio.setOnClickListener(this);
        view_select_scorpio_ta.setOnClickListener(this);

        view_select_sagittarius.setOnClickListener(this);
        view_select_sagittarius_ta.setOnClickListener(this);

        view_select_capricorn.setOnClickListener(this);
        view_select_capricorn_ta.setOnClickListener(this);

        btn_select_he_boy.setOnClickListener(this);
        btn_select_he_girl.setOnClickListener(this);

        btn_select_finish.setOnClickListener(this);

    }
    /*public void scrollViewInit(){
        LinearLayout mGallery0 = (LinearLayout) findViewById(R.id.id_gallery0);
        LinearLayout mGallery1 = (LinearLayout) findViewById(R.id.id_gallery1);

        for (int i = 0; i < arrText.length; i++)
        {

            View view = mInflater.inflate(R.layout.activity_select_item,
                    mGallery0, false);
            TextView txt = (TextView) view
                    .findViewById(R.id.ItemText);
            txt.setText(arrText[i]);
            mGallery0.addView(view);
        }
        for (int i = 0; i < arrText.length; i++)
        {

            View view = mInflater.inflate(R.layout.activity_select_item,
                    mGallery1, false);
            TextView txt = (TextView) view
                    .findViewById(R.id.ItemText);
            txt.setText(arrText[i]);
            mGallery1.addView(view);
        }
       //Todo

    }*/
    //[3]处理点击事件
    @Override
    public void onClick(View view){
        switch (view.getId()){

            case R.id.btn_select_my_boy:
                btn_select_my_boy.setBackgroundResource(R.mipmap.bg_boy_h);
                btn_select_my_girl.setBackgroundResource(R.mipmap.bg_girl_n);
                sex1 = btn_select_my_boy.getText().toString().trim();
                break;
            case R.id.btn_select_my_girl:
                btn_select_my_boy.setBackgroundResource(R.mipmap.bg_boy_n);
                btn_select_my_girl.setBackgroundResource(R.mipmap.bg_girl_h);
                sex1 = btn_select_my_girl.getText().toString().trim();
                break;
            case R.id.btn_select_he_boy:
                btn_select_he_boy.setBackgroundResource(R.mipmap.bg_boy_h);
                btn_select_he_girl.setBackgroundResource(R.mipmap.bg_girl_n);
                sex2 = btn_select_he_boy.getText().toString();
                break;
            case R.id.btn_select_he_girl:
                btn_select_he_boy.setBackgroundResource(R.mipmap.bg_boy_n);
                btn_select_he_girl.setBackgroundResource(R.mipmap.bg_girl_h);
                sex2 = btn_select_he_girl.getText().toString();
                break;
            case R.id.btn_select_finish:
                SelectFinish();
                break;
            case R.id.view_select_Aquarius:
                changeTextSize();
                view_select_aquarius.setTextSize(size1);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_aquarius.getText().toString();
                break;
            case R.id.view_select_Aquarius2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size1);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_aquarius_ta.getText().toString();
                break;
            case R.id.view_select_Pisces:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size1);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_pisces.getText().toString();
                break;
            case R.id.view_select_Pisces2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size1);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_pisces_ta.getText().toString();
                break;
            case R.id.view_select_Aries:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size1);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_aries.getText().toString();
                break;
            case R.id.view_select_Aries2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size1);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_aries_ta.getText().toString();
                break;
            case R.id.view_select_Taurus:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size1);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_taurus.getText().toString();
                break;
            case R.id.view_select_Taurus2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size1);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_taurus_ta.getText().toString();
                break;
            case R.id.view_select_Gemini:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size1);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_gemini.getText().toString();
                break;
            case R.id.view_select_Gemini2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size1);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_gemini_ta.getText().toString();
                break;
            case R.id.view_select_Cancer:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size1);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_cancer.getText().toString();
                break;
            case R.id.view_select_Cancer2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size1);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_cancer_ta.getText().toString();
                break;
            case R.id.view_select_Leo:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size1);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_leo.getText().toString();
                break;
            case R.id.view_select_Leo2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size1);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_leo_ta.getText().toString();
                break;
            case R.id.view_select_Virgo:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size1);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_virgo.getText().toString();
                break;
            case R.id.view_select_Virgo2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size1);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_virgo_ta.getText().toString();
                break;
            case R.id.view_select_Libra:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size1);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_libra.getText().toString();
                break;
            case R.id.view_select_Libra2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size1);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_libra_ta.getText().toString();
                break;
            case R.id.view_select_Scorpio:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size1);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_scorpio.getText().toString();
                break;
            case R.id.view_select_Scorpio2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size1);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_scorpio_ta.getText().toString();
                break;
            case R.id.view_select_Sagittarius:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size1);
                view_select_capricorn.setTextSize(size2);
                constellation1=view_select_sagittarius.getText().toString();
                break;
            case R.id.view_select_Sagittarius2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size1);
                view_select_capricorn_ta.setTextSize(size2);
                constellation2=view_select_sagittarius_ta.getText().toString();
                break;
            case R.id.view_select_Capricorn:
                changeTextSize();
                view_select_aquarius.setTextSize(size2);
                view_select_pisces.setTextSize(size2);
                view_select_aries.setTextSize(size2);
                view_select_taurus.setTextSize(size2);
                view_select_gemini.setTextSize(size2);
                view_select_cancer.setTextSize(size2);
                view_select_leo.setTextSize(size2);
                view_select_virgo.setTextSize(size2);
                view_select_libra.setTextSize(size2);
                view_select_scorpio.setTextSize(size2);
                view_select_sagittarius.setTextSize(size2);
                view_select_capricorn.setTextSize(size1);
                constellation1=view_select_capricorn.getText().toString();
                break;
            case R.id.view_select_Capricorn2:
                changeTextSize();
                view_select_aquarius_ta.setTextSize(size2);
                view_select_pisces_ta.setTextSize(size2);
                view_select_aries_ta.setTextSize(size2);
                view_select_taurus_ta.setTextSize(size2);
                view_select_gemini_ta.setTextSize(size2);
                view_select_cancer_ta.setTextSize(size2);
                view_select_leo_ta.setTextSize(size2);
                view_select_virgo_ta.setTextSize(size2);
                view_select_libra_ta.setTextSize(size2);
                view_select_scorpio_ta.setTextSize(size2);
                view_select_sagittarius_ta.setTextSize(size2);
                view_select_capricorn_ta.setTextSize(size1);
                constellation2=view_select_capricorn_ta.getText().toString();
                break;

        }

    }
    //点击后改变字体大小
    private void changeTextSize(){
        size1 = (int) tv_size.getTextSize();
        size2=(int)tv_size2.getTextSize();
    }

    public void SelectFinish(){
        if (sex1==null||sex2==null)
        {
            Toast.makeText(getApplicationContext(), "请选择性别", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (constellation1==null||constellation2==null)
            {
                Toast.makeText(getApplicationContext(), "请选择星座", Toast.LENGTH_LONG).show();
            }else
            {
                L.i(LOGTAG, "确定选择的星座");
                if (doPost())
                {
                    sex1 =btn_select_my_boy.getText().toString();
                    sex2 =btn_select_he_girl.getText().toString();
                    L.i(LOGTAG,"默认性别"+ sex1 + sex2);
                    L.i(LOGTAG,"完成选择进入主页");
                    Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(this,MainPageActivity.class);
                    startActivity(intent);
                    finish();
                }else
                {
                    Toast.makeText(getApplicationContext(), "服务器响应异常，请稍后再试!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    //从sharedPreferences文件中读取存储的user_id
    private void getUserId(){
        SharedPreferences preferences= PreferenceManager.
                getDefaultSharedPreferences(this);
        user_id=preferences.getString("user_id","");
        L.i(LOGTAG,""+user_id);
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
                .add("sex1", sex1)
                .add("sex2", sex2)
                .add("constellation1", constellation1)
                .add("constellation2", constellation2)
                .build();

        Request request = new Request.Builder()
                .url("http://10.110.101.201:8010/api/user/"+user_id+"/true")
                .post(requestBody)
                .build();

        //[3]将Request封装为call
        Call call=client.newCall(request);
        //[4]执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                L.i(LOGTAG,"onFailure"+e.getMessage());
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String res=response.body().string();
                L.i(LOGTAG,"onResponse"+res);
            }
        });
    }
    //下载星座和性别的ar文件
}
