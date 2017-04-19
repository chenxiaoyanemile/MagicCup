package com.example.sweetgirl.magiccup1.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.Bean.Item;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.model.TallScene;
import com.example.sweetgirl.magiccup1.util.CreateJson;
import com.example.sweetgirl.magiccup1.util.FileDownloadThread;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.example.sweetgirl.magiccup1.view.recycleView.RecyclerViewActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.unity3d.player.UnityPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class MyMakeGiftActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = LogUtil.makeLogTag(MyMakeGiftActivity.class);

    public final static int MY_REQUEST_CODE1 = 0;
    public final static int MY_REQUEST_CODE2 = 1;
    public final static int MY_REQUEST_CODE3 = 2;

    private Button btn_gift_story;
    private Button btn_gift_scene;
    private Button btn_gift_letter;
    private Button btn_gift_preview;

    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;

    private String story;
    private String storyId;
    private String storyResource;
    private String UnityParameter;   //第二幕


    private String scene;
    private String sceneId;
    private String sceneResource;

    private String weatherId;
    private String weatherResource;

    private String backgroundId;
    private String backgroundResource;

    private String letter;
    private String letterContent;

    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_make_gift);

        EventBus.getDefault().register(this);  //设置订阅者

        initView();
        initData();


    }
    //[1]找到相关控件
    public void initView(){
        btn_gift_story=(Button)findViewById(R.id.btn_gift_story);
        btn_gift_scene=(Button)findViewById(R.id.btn_gift_scene);
        btn_gift_letter=(Button)findViewById(R.id.btn_gift_letter);
        btn_gift_preview=(Button)findViewById(R.id.btn_gift_preview);

        btn_gift_story.setOnClickListener(this);
        btn_gift_scene.setOnClickListener(this);
        btn_gift_letter.setOnClickListener(this);
        btn_gift_preview.setOnClickListener(this);

    }
    //[2]设置默认值
    public void initData(){
        storyId="9776bc5e-cdda-40d3-b031-f142d0d1d760";
        sceneId="ab1cdab7-1fca-4ce3-8ef7-777a8dksa";
        weatherId="ab1cdab7-1fca-4ce3-8ef7-777a8d7dk";
        backgroundId="ab1cdab7-1fca-4ce3-8ef7-777a8djxa";
        letterContent="I LOVE YOU";
        L.d(TAG,"默认值");


    }
    //[3]响应点击事件
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_gift_story:
                Intent intent1=new Intent(MyMakeGiftActivity.this,RecyclerViewActivity.class);

                startActivityForResult(intent1, MY_REQUEST_CODE1);
                L.i(TAG,"选择故事");
                break;
            case R.id.btn_gift_scene:
                Intent intent2=new Intent(MyMakeGiftActivity.this,MyGiftScene2Activity.class);

                startActivityForResult(intent2, MY_REQUEST_CODE2);
                L.i(TAG,"选择情景");
                break;
            case R.id.btn_gift_letter:
                Intent intent3=new Intent(MyMakeGiftActivity.this,MyGiftScene3Activity.class);

                startActivityForResult(intent3, MY_REQUEST_CODE3);
                L.i(TAG,"写信");
                break;
            case R.id.btn_gift_preview:
                showSimpleDialog(view);

                L.i(TAG,"预览");
                break;

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        L.i(TAG,"成功返回数据");
        switch (requestCode)
        {
            case MY_REQUEST_CODE1:
                story=data.getExtras().getString("data1");    //选择的第二幕
                storyId=data.getExtras().getString("dataId");
                storyResource=data.getExtras().getString("dataResource");

                UnityParameter=GetData(storyResource);   //unity 参数

                L.d(TAG,"第二幕选择的是"+story);
                L.d(TAG,"场景id"+storyId);
                L.d(TAG,"场景资源"+storyResource);

                btn_gift_story.setText(story);
                break;
            case MY_REQUEST_CODE2:
                scene=data.getExtras().getString("data2");  //选择的天气、背景、情景

                btn_gift_scene.setText(scene);
                break;
            case MY_REQUEST_CODE3:
                letter=data.getExtras().getString("data3");

                letterContent=data.getExtras().getString("data4");  //信的内容

                L.d(TAG,letterContent);
                btn_gift_letter.setText(letter);
                break;
        }
    }

    //截取文件名
    public String GetData(String url){
        //http://ojphnknti.bkt.clouddn.com/scene1/
        String res=url.substring(url.lastIndexOf("/")+1);
        L.d(TAG,res);

        return res;


    }

    //显示基本Dialog
    private void showSimpleDialog(View view) {
        builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_dialog);
        builder.setMessage(R.string.dialog_message);

        //监听下方button点击事件
        builder.setPositiveButton(R.string.postive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getApplicationContext(), "开始预览，加载中...", Toast.LENGTH_LONG).show();
                getUserId();   //获取到用户id;
                L.d(TAG,"获取到用户id");

                doPost();      //提交选择的项到服务器
                L.d(TAG,"提交选择的项到服务器");

                doGet();  //下载unity资源
                L.d(TAG,"下载unity资源");

                CreateUnityData();   //预览选择的信息
                L.d(TAG,"预览选择的信息");



                //Intent intent4=new Intent(MyMakeGiftActivity.this,ShowARActivity.class);
                //startActivity(intent4);
            }
        });
        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.toast_negative, Toast.LENGTH_SHORT).show();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    //从sharedPreferences文件中读取存储的user_id
    private void getUserId(){
        SharedPreferences preferences= PreferenceManager.
                getDefaultSharedPreferences(this);
        user_id=preferences.getString("user_id","");
        L.i(TAG,""+user_id);
        doPost();
    }
    //[4]提交选择的结果到服务器
    public boolean doPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
                .add("user_id",user_id)
                .add("text", letterContent)
                .add("scene2_id", storyId)
                .add("scene31_id", sceneId)
                .add("scene32_id", weatherId)
                .add("scene33_id", backgroundId)
                .add("scene4_id", "643abd25-ffec-11e6-9354-a4db303d2fd7")
                .build();

        Request request = new Request.Builder()
                .url("http://139.199.190.245:8010/api/between")
                .post(requestBody)
                .build();

        //[3]将Request封装为call
        Call call=client.newCall(request);
        //[4]执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                L.i(TAG,"onFailure"+e.getMessage());
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String res=response.body().string();
                L.i(TAG,"onResponse"+res);
            }
        });
    }

    //下载选好的数据资源
    private  void doGet(){
        final String downloadUrl1 = storyResource;
        final String fileName1=UnityParameter;


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doDownload(downloadUrl1,fileName1);
                    L.i(TAG,"第一个下载完成");

                    Toast.makeText(MyMakeGiftActivity.this, "下载资源完成！", Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
            //int progress=msg.getData().getInt("size");
            //int temp=100;
           /* if (progress==100){
                Toast.makeText(getActivity(),"加载完成！",Toast.LENGTH_SHORT).show();
            }

            pb_show_download.setProgress(msg.getData().getInt("size"));*/

            /*float temp1 = (float) pb_show_download.getProgress()
            (float) pb_show_download.getMax();*/

           /*int progress = 100;
            if (progress == 100) {
                Toast.makeText(getApplicationContext(), "加载完成！", Toast.LENGTH_LONG).show();
            }*/
            //pb_show_download.setProgress(msg.getData().getInt("size"));

            // float temp = (float) pb_show_download.getProgress() / (float) pb_show_download.getMax();

            //int progress = (int) (temp * 100);
            // if (progress == 100) {
            //Toast.makeText(getActivity(), "下载完成！", Toast.LENGTH_LONG).show();
            // }
            //tv_download_msg.setText("下载进度:" + progress + " %");

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
                    // Log.d(TAG, "current downloadSize:" + downloadedAllSize);
                    Thread.sleep(1000);// 休息1秒后再读取下载进度
                }
                L.d(TAG, " all of downloadSize:" + downloadedAllSize);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void CreateUnityData(){

        TallScene scene = new TallScene();
        scene.getFirstScene().setSexwoman("Piscesman.assetbundle");
        scene.getFirstScene().setSexman("VirgoWoman.assetbundle");

        scene.getSecondScene().setAction(UnityParameter);
        scene.getSecondScene().setBackground("");

        scene.getThirdScene().setAction("ACHuge.assetbundle");
        scene.getThirdScene().setBackground("BGYinXing.assetbundle");
        scene.getThirdScene().setText(letterContent);
        scene.getThirdScene().setTime("ACHuge.assetbundle");

        scene.getFourScene().setText(letterContent);
        scene.getFourScene().setInjection("huapen.assetbundle");

        String first= CreateJson.createJson(scene);

        UnityPlayer.UnitySendMessage("Directional Light","ReceiveJson",first);
        L.i(TAG,"传送数据给ar");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(Item message){

        sceneId=message.getId();

        L.d(TAG,"返回的情景ID"+sceneId);

        sceneResource=message.getResource();
        L.d(TAG,"返回的情景ID"+sceneResource);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
