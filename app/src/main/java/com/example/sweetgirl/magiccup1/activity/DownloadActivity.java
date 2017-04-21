package com.example.sweetgirl.magiccup1.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;

import com.example.sweetgirl.magiccup1.model.TallScene;
import com.example.sweetgirl.magiccup1.util.CreateJson;
import com.example.sweetgirl.magiccup1.util.FileDownloadThread;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;


import java.io.File;
import java.net.URL;
import java.net.URLConnection;

public class DownloadActivity extends UnityPlayerActivity {

    private static final String TAG = LogUtil.makeLogTag(DownloadActivity.class);


    private RelativeLayout scan;  //边框\
    private Button btn_unity;

    private String resource1="http://ojphnknti.bkt.clouddn.com/scene1/Piscesman.assetbundle";   //星座男
    private String resource2 = "http://ojphnknti.bkt.clouddn.com/scene1/VirgoWoman.assetbundle";   //星座女
    private String backgroundResource;   //背景
    private String sceneResource;   //动作
    private String weatherResource;  //天气，\
    // 时间
    private String resource4="http://ojphnknti.bkt.clouddn.com/scene4/huapen.assetbundle";  //固定的


    public String text="I love you";  //
    public String fileName1="Piscesman.assetbundle";    //暂定
    public String fileName2="VirgoWoman.assetbundle";   //暂定
    public String fileName31="BGYinXing.assetbundle";    //背景
    public String fileName32="ACHuge.assetbundle";    //动作
    public String fileName33="ACHuge.assetbundle";   //天气，时间
    public String fileName4="huapen.assetbundle";  //固定


    private int judgeDownload=0;  //判断下载

    private String[] arFileName;
    private String[] arUrl;

    private String UnityJsonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        scan=(RelativeLayout)findViewById(R.id.scan);
        btn_unity=(Button)findViewById(R.id.btn_unity);

        getUserResource();  //获取传过的数据

        L.d(TAG,"获取传过的数据");

        GetResourceData(); //下载资源

        L.d(TAG,"下载资源");


        btn_unity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (judgeDownload==8){

                    L.d(TAG,UnityJsonData);
                    UnityPlayer.UnitySendMessage("Directional Light","ReceiveJson",UnityJsonData);
                    L.i(TAG,"传送数据给ar");
                    //ShowARActivity.this.finish();
                    // L.i(TAG,"结束android,开启ar");
                    //file:///storage/emulated/0/Android/data/com.example.sweetgirl.magiccup1/files
                    //path:/storage/emulated/0/Android/data/com.example.sweetgirl.magiccup1/files/
                    //String path2="jar:file://"+getBaseContext().getExternalFilesDir("")+"/";
                    UnityPlayer.UnitySendMessage("Directional Light","PlaySceneAll","");
                    L.i(TAG,"PlaySceneAll");

                    View view=mUnityPlayer.getView();
                    scan.addView(view);
                    L.i(TAG,"展示ar");
                    btn_unity.setVisibility(view.GONE);


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"加载资源中！请稍等。。。",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    //从sharedPreferences文件中读取存储的user_id
    private void getUserResource(){

        SharedPreferences preferences=getSharedPreferences("SceneTwo", Context.MODE_PRIVATE);
        text=preferences.getString("text","text");
        backgroundResource=preferences.getString("backgroundResource", "backgroundResource");
        sceneResource=preferences.getString("sceneResource", "sceneResource");
        weatherResource=preferences.getString("weatherResource", "weatherResource");

        L.i(TAG,"从sharedPreferences文件中读取存储的resource1"+text+backgroundResource+sceneResource+weatherResource);

    }

    private void GetResourceData(){


        fileName1=judgmentDownloadComplete(resource1);

        fileName2=judgmentDownloadComplete(resource2);


        fileName31=judgmentDownloadComplete(backgroundResource);  //action



        fileName32=judgmentDownloadComplete(sceneResource);   //time


        fileName33=judgmentDownloadComplete(weatherResource);   //back


        fileName4=judgmentDownloadComplete(resource4);


        final String downloadUrl7 = "http://ojphnknti.bkt.clouddn.com/scene1/DYM.assetbundle";
        final String fileName7="DYM.assetbundle";

        final String downloadUrl8 = "http://ojphnknti.bkt.clouddn.com/scene4/disimu.assetbundle";
        final String fileName8="disimu.assetbundle";

        final String downloadUrl9 = "http://ojphnknti.bkt.clouddn.com/scene31/Cloud.assetbundle";
        final String fileName9="Cloud.assetbundle";


        arFileName=new String[]{
                fileName1,fileName2,fileName31,
                fileName32,fileName33,fileName4,
                fileName7,fileName8,fileName9
        };

        arUrl=new String[]{
                resource1,resource2,backgroundResource,
                sceneResource,weatherResource,resource4,
                downloadUrl7,downloadUrl8,downloadUrl9
        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (int i=0;i<9;i++){
                        doDownload(arUrl[i],arFileName[i]);
                        L.d(TAG,"第"+i+"下载完成");
                    }

                    if (judgeDownload==8){
                        DownloadActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                btn_unity.setText("资源加载完成，请点击我！");
                                Toast.makeText(getApplicationContext(),"资源加载完成",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        CreateUnityJsonData(fileName1,fileName2,fileName33,fileName31,fileName32,text,fileName4);
        L.d(TAG,"CreateUnityJsonData");

        //judgeDownload=1;

        //btn_unity.setText("资源加载完成，请点击我！");

        //Toast.makeText(getApplicationContext(),"资源加载完成",Toast.LENGTH_SHORT).show();


    }

    private String CreateUnityJsonData(String sex1,String sex2,String background,String action,String time,String text,String file4){

        TallScene scene = new TallScene();
        scene.getFirstScene().setSexwoman(sex2);   //性别女
        scene.getFirstScene().setSexman(sex1);    //性别男

        scene.getSecondScene().setAction("");    //没有
        scene.getSecondScene().setBackground("");

        scene.getThirdScene().setBackground(background);   //背景（树）
        scene.getThirdScene().setAction(action);   //情景（躺)
        scene.getThirdScene().setText("字幕");                   //字幕，暂时不用传
        scene.getThirdScene().setTime(time);        //天气（早上）

        scene.getFourScene().setText(text);   //那封信
        scene.getFourScene().setInjection(file4);   //喷花，暂时固定

        UnityJsonData= CreateJson.createJson(scene);
        return UnityJsonData;

    }


    private String judgmentDownloadComplete(String url){

        //匹配最后一个斜杆的位置
        //http://ojphnknti.bkt.clouddn.com/scene1/
        String res=url.substring(url.lastIndexOf("/")+1);
        L.d(TAG,res);

        return res;

    }


    //下载文件
    private void doDownload(String downloadUrl,String fileName){
        // 获取SD卡路径
        String path2=getBaseContext().getExternalFilesDir("")+"/";

        //获取当前应用的安装目录路径path:/storage/emulated/0/Android/data/com.example.sweetgirl.magiccup1/files/

        //String path = getActivity().getApplicationContext().getFilesDir().getAbsolutePath();

        L.d(TAG, "download file  path:" + path2);
        File file = new File(path2);
        // 如果SD卡目录不存在创建
        if (!file.exists()) {
            file.mkdir();
        }
        // 简单起见，我先把URL和文件名称写死，其实这些都可以通过HttpHeader获取到

        int threadNum = 5;
        String filepath = path2+fileName;
        L.d(TAG, "download file  path:" + filepath);
        downloadTask task = new downloadTask(downloadUrl, threadNum, filepath);
        task.start();
    }

    /**
     * 使用Handler更新UI界面信息
     */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //[获取下载文件大小]
            int progress=msg.getData().getInt("size");

            //int temp=100;
        if (progress==100){
               judgeDownload++;
            L.d(TAG,"第judgeDownload=个下载完成"+judgeDownload);
            }


        }
    };
    //多线程下载
    class downloadTask extends Thread {
        private String downloadUrl;// 下载链接地址
        private int threadNum;// 开启的线程数
        private String filePath;// 保存文件路径地址
        private int blockSize;// 每一个线程的下载量

        public downloadTask(String downloadUrl, int threadNum, String fileptah) {
            this.downloadUrl = downloadUrl;
            this.threadNum = threadNum;
            this.filePath = fileptah;
        }

        @Override
        public void run() {

            FileDownloadThread[] threads = new FileDownloadThread[threadNum];

            try {
                URL url = new URL(downloadUrl);
                L.d(TAG, "download file http path:" + downloadUrl);
                URLConnection conn = url.openConnection();
                // 读取下载文件总大小
                int fileSize = conn.getContentLength();
                if (fileSize <= 0) {
                    System.out.println("读取文件失败");
                    return;
                }

                // 计算每条线程下载的数据长度
                blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum
                        : fileSize / threadNum + 1;

                L.d(TAG, "fileSize:" + fileSize + "  blockSize:");

                File file = new File(filePath);
                for (int i = 0; i < threads.length; i++) {
                    // 启动线程，分别下载每个线程需要下载的部分
                    threads[i] = new FileDownloadThread(url, file, blockSize,
                            (i + 1));
                    threads[i].setName("Thread:" + i);
                    threads[i].start();
                }

              /*  fileDownloadThread.setCallback(new FileDownloadThread.Callback() {
                    @Override
                    public void onSuccess() {
                        i++;
                        doDownload();
                    }
                });
*/
                boolean isfinished = false;
                int downloadedAllSize = 0;
                while (!isfinished) {
                    isfinished = true;
                    // 当前所有线程下载总量
                    downloadedAllSize = 0;
                    for (int i = 0; i < threads.length; i++) {
                        downloadedAllSize += threads[i].getDownloadLength();
                        if (!threads[i].isCompleted()) {
                            isfinished = false;
                        }
                    }
                    // 通知handler去更新视图组件
                    Message msg = new Message();
                    msg.getData().putInt("size", downloadedAllSize);
                    mHandler.sendMessage(msg);
                    L.d(TAG, "current downloadSize:" + downloadedAllSize);
                    Thread.sleep(1000);// 休息1秒后再读取下载进度
                }
                L.d(TAG, " all of downloadSize:" + downloadedAllSize);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键");
            Intent intent=new Intent(DownloadActivity.this,MyMakeGiftActivity.class);
            startActivity(intent);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
