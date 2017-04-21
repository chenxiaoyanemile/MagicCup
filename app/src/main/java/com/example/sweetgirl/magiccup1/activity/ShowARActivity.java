package com.example.sweetgirl.magiccup1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.model.TallScene;
import com.example.sweetgirl.magiccup1.util.CreateJson;

import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerNativeActivity;



public class ShowARActivity extends UnityPlayerNativeActivity {

    private static final String TAG = LogUtil.makeLogTag(ShowARActivity.class);

    private LinearLayout show_scan;  //边框
    private String sex1="Piscesman.assetbundle";   //星座1
    private String sex2="VirgoWoman.assetbundle";   //星座2
    private String action;
    private String background;
    private String time;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ar);

        show_scan=(LinearLayout)findViewById(R.id.show_scan);


        TallScene scene = new TallScene();
        scene.getFirstScene().setSexwoman(sex2);
        scene.getFirstScene().setSexman(sex1);

        scene.getSecondScene().setAction("secondanimation.assetbundle");
        scene.getSecondScene().setBackground("");

        scene.getThirdScene().setAction("ACHuge.assetbundle");
        scene.getThirdScene().setBackground("BGYinXing.assetbundle");
        scene.getThirdScene().setText("我爱你亲爱的姑娘~见到你心就慌张！");
        scene.getThirdScene().setTime("ACHuge.assetbundle");

        scene.getFourScene().setText("自定义文字");
        scene.getFourScene().setInjection("huapen.assetbundle");

        String first=CreateJson.createJson(scene);

        UnityPlayer.UnitySendMessage("Directional Light","ReceiveJson",first);
        L.i(TAG,"传送数据给ar");
        //ShowARActivity.this.finish();
        // L.i(TAG,"结束android,开启ar");
        //file:///storage/emulated/0/Android/data/com.example.sweetgirl.magiccup1/files
        //path:/storage/emulated/0/Android/data/com.example.sweetgirl.magiccup1/files/
        //String path2="jar:file://"+getBaseContext().getExternalFilesDir("")+"/";
        UnityPlayer.UnitySendMessage("Directional Light","PlaySceneAll","");
        L.i(TAG,"PlaySceneAll");

        View view=mUnityPlayer.getView();
        show_scan.addView(view);
        L.i(TAG,"展示ar");
    }
}

