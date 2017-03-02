package com.example.sweetgirl.magiccup1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;

public class MySetBackActivity extends AppCompatActivity {

    private EditText back_et_content;
    private Button back_btn_commit;

    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_set_back);

        back_et_content=(EditText)findViewById(R.id.back_et_content);
        back_btn_commit=(Button)findViewById(R.id.back_btn_commit);

        back_btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content=back_et_content.getText().toString().trim();
                Toast.makeText(getApplicationContext(),"感谢您的反馈，已提交",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
