package com.example.sweetgirl.magiccup1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sweetgirl.magiccup1.R;
import com.unity3d.player.UnityPlayerNativeActivity;

public class ShowARActivity extends UnityPlayerNativeActivity {

    private LinearLayout scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ar);

        scan=(LinearLayout)findViewById(R.id.scan);
        //UnityPlayer.UnitySendMessage("Directional Light","ReceiveJson",edit.getText().toString());
        View view=mUnityPlayer.getView();
        scan.addView(view);
    }
}
