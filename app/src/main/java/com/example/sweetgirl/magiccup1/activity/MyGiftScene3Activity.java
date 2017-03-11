package com.example.sweetgirl.magiccup1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;

public class MyGiftScene3Activity extends AppCompatActivity {
    private static final String TAG = LogUtil.makeLogTag(MyGiftScene3Activity.class);

    private ImageView set_toolbar_back;

    private EditText scene3_et_letter;
    private Button scene3_btn_commit;

    private String letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift_scene3);

        set_toolbar_back=(ImageView)findViewById(R.id.set_toolbar_back);
        set_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyGiftScene3Activity.this.getIntent();
                intent.putExtra("data3", "3、默认效果");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        scene3_btn_commit=(Button)findViewById(R.id.scene3_btn_commit);
        scene3_et_letter=(EditText)findViewById(R.id.scene3_et_letter);

        scene3_btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letter=scene3_et_letter.getText().toString().trim();
                Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //手机系统返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.i(TAG, "back键 ");
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = MyGiftScene3Activity.this.getIntent();
            intent.putExtra("data3", "3、默认效果");
            setResult(RESULT_OK, intent);
            this.finish();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
