package com.example.sweetgirl.magiccup1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;

public class MyGiftScene3Activity extends AppCompatActivity {

    private EditText scene3_et_letter;
    private Button scene3_btn_commit;

    private String letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift_scene3);

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
}
