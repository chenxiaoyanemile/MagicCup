package com.example.sweetgirl.magiccup1.activity;

import android.support.v7.app.AppCompatActivity;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PreviewActivity extends UnityPlayerNativeActivity {

    private static final String TAG = LogUtil.makeLogTag(PreviewActivity.class);



    private LinearLayout scan;
    private String sex1="Piscesman.assetbundle";
    private String sex2="VirgoWoman.assetbundle";
    private String action;
    private String background;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        EventBus.getDefault().register(this);


        scan=(LinearLayout)findViewById(R.id.scan);


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

        String first= CreateJson.createJson(scene);

        UnityPlayer.UnitySendMessage("Directional Light","ReceiveJson",first);
        L.i(TAG,"传送数据给ar");

        View view=mUnityPlayer.getView();
        scan.addView(view);
        L.i(TAG,"展示ar");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public String helloEventBus(String message){

        return message;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
