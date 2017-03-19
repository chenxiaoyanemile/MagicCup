package com.example.sweetgirl.magiccup1.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.app.MyApplication;
import com.example.sweetgirl.magiccup1.model.DataBean;
import com.example.sweetgirl.magiccup1.model.Relation;
import com.example.sweetgirl.magiccup1.model.ScanNumber;
import com.example.sweetgirl.magiccup1.model.Scene1;
import com.example.sweetgirl.magiccup1.model.Scene2;
import com.example.sweetgirl.magiccup1.model.Scene31;
import com.example.sweetgirl.magiccup1.model.Scene32;
import com.example.sweetgirl.magiccup1.model.Scene33;
import com.example.sweetgirl.magiccup1.model.Scene4;
import com.example.sweetgirl.magiccup1.model.ShowAllScene;
import com.example.sweetgirl.magiccup1.model.ShowScene;
import com.example.sweetgirl.magiccup1.util.FileDownloadThread;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/*
* 1.扫描二维码
* 2.获取用户id
* 3.获取关联信息
* */

public class StartActivity extends AppCompatActivity {
    private static final String TAG = LogUtil.makeLogTag(StartActivity.class);

    private SharedPreferences preferences;
    private String res;    //返回的json数据转成String

    private String result;   //扫描二维码结果

    int REQUEST_CODE;

    private Boolean success;   //第一次扫描后的返回信息
//1488436357855N0E0Y5DLzlohdUJbtUOLzCDVX http://119.29.222.54:8888/api/user/1488436357855N0E0Y5DLzlohdUJbtUOLzCDVX?state=true
    private String url="http://119.29.222.54:8888/api/user/"+result+"?state=true";   //扫描二维码
    private String user_id;         //用户id
    private String path="http://139.199.190.245:8010/api/between/"+user_id;         //关联信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.activity_start);

        preferences = getSharedPreferences("count", Context.MODE_PRIVATE);

        init();
    }

    //[1]初始化控件
    public void init(){
        Button btn_start_scan = (Button) findViewById(R.id.btn_start_scan);

        btn_start_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          if (preferences.getBoolean("firstStart", true)) {
                    L.i(TAG, "扫码");
                    Intent intent1 = new Intent(StartActivity.this, CaptureActivity.class);
                    startActivityForResult(intent1, REQUEST_CODE);
                } else {
                    L.i(TAG, "主页");
                    Intent intent = new Intent(StartActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
    //[2]处理扫描结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        REQUEST_CODE = 20;
        if (requestCode == REQUEST_CODE) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    result = bundle.getString(CodeUtils.RESULT_STRING);
                    L.i("扫描结果：", result);
                    doGet();
                }
                else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getApplicationContext(), "解析二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //[3]将扫描结果提交到服务器
    private  void doGet(){
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
    }
    private void enqueue(){
        //[1]拿到OkHttpClient
        OkHttpClient client = new OkHttpClient();
        //[2]构造Request
        Request request = new Request.Builder()
                .url(url)
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
                res=response.body().string();
                L.i(TAG,"onResponse"+res);
                jsonParser(res);
            }
        });
    }
    //[4]解析数据
    private void jsonParser(String jsonData) {
        try{
            //json数据解析成一个对象
            ScanNumber scanNumber=JSON.parseObject(jsonData,ScanNumber.class);
            success = scanNumber.getSuccess();
            L.i("message"," "+scanNumber.getSuccess());
            if (success){
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "你在没有网络的异次元空间。。。", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            else {
                //judgeResult(message);
            }

        }catch(Exception e){
                e.printStackTrace();
        }

    }
    //[5]判断返回结果
    private void judgeResult(String message){
        if (message.equals("serial not found")){
            Looper.prepare();
            Toast.makeText(getApplicationContext(), "这不是杯子的二维码o", Toast.LENGTH_SHORT).show();
            Looper.loop();
            L.i(TAG,message);
            /*StartActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "这不是杯子的二维码o", Toast.LENGTH_SHORT).show();
                }
            });*/
        }
        if (message.equals("success")){
            L.i(TAG,message);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //parserJson(res);
                        saveData(user_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            //判断是否已经选择
            if (relationship()){
                //存在对方赠送的礼物  ar
                Intent intent = new Intent(StartActivity.this, ShowARActivity.class);
                startActivity(intent);
                finish();

                L.i(TAG,"查看对方送的礼物");
            }
            Intent intent = new Intent(StartActivity.this, SelectActivity.class);
            startActivity(intent);
            finish();

            L.i(TAG, "选择星座");
        }
        if (message.equals("Not First")){
            L.i(TAG,message);
            //[1]查看当前线程
            Thread currentThread = Thread.
                    currentThread();
            //[2]获得当前的线程
            String threadName = currentThread.getName();
            //[3]得到当前线程的名字
            System.out.println("执行代码的线程名叫做：" + threadName);
            //[4]在子线程中更新ui
            Looper.prepare();
            Toast.makeText(getApplicationContext(), "不是第一次扫描o,将会替换先前用户", Toast.LENGTH_SHORT).show();
            Looper.loop();
            Intent intent = new Intent(StartActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
            L.i(TAG,message);
        }
    }
    private void parserJson(String jsonData){
        try{
            DataBean data=JSON.parseObject(jsonData,ScanNumber.class).getData();

            relationship(); //获取关联信息

            L.i(TAG,"user_id"+user_id);
        }catch(Exception e){
                e.printStackTrace();
        }
    }

    //[6]保存数据
    private void saveData(String data){
        //[1]保存ID，获得对象
        MyApplication myApplication = (MyApplication) getApplication();
        //更改全局变量的值
        myApplication.setUser_id(data);
        L.i("保存全局变量的值",""+user_id);

        //[2]保存第几次扫描
        preferences = getSharedPreferences("count", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
        L.i("保存结果","firstStart");
    }
    //[7]获取关联信息
    private boolean relationship(){
        getMessage();
        return false;
    }
    private  void getMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    http();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void http(){
        //[1]拿到OkHttpClient
        OkHttpClient client = new OkHttpClient();
        //[2]构造Request

        Request request = new Request.Builder()
                .url(path)
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
                res=response.body().string();
                L.i(TAG,"onResponse"+res);
               // ParseJson(res);
            }
        });
    }
    //[4]解析数据
    private void ParseJson(String jsonData) {
        try{
            //json数据解析成一个对象
            ShowScene showScene=JSON.parseObject(jsonData,Relation.class).getShowScene();
            //String text=showScene.getText();
            ShowAllScene allScene=JSON.parseObject(jsonData,ShowScene.class).getData();


            Scene1 scene1=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene1();
            String resource1=scene1.getResource();
            doDownload(resource1,"scene1");

            Scene2 scene2=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene2();
            String resource2=scene2.getResource();
            doDownload(resource2,"scene2");

            Scene31 scene31=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene31();
            String resource31=scene31.getResource();
            doDownload(resource31,"scene31");

            Scene32 scene32=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene32();
            String resource32=scene32.getResource();
            doDownload(resource32,"scene32");

            Scene33 scene33=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene33();
            String resource33=scene33.getResource();
            doDownload(resource33,"scene33");

            Scene4 scene4=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene4();
            String resource4=scene4.getResource();
            doDownload(resource4,"scene4");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    //下载文件
    private void doDownload(String downloadUrl,String fileName){
        // 获取SD卡路径
        String path = Environment.getExternalStorageDirectory()
                + "/data/";
        L.d(TAG, "download file  path:" + path);
        File file = new File(path);
        // 如果SD卡目录不存在创建
        if (!file.exists()) {
            file.mkdir();
        }
        // 简单起见，我先把URL和文件名称写死，其实这些都可以通过HttpHeader获取到
         downloadUrl = "http://oexlqeny2.bkt.clouddn.com/scene0.zip";
         fileName = "scene1";
        int threadNum = 5;
        String filepath = path + fileName;
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
            int temp=100;
            if (progress==100){
                Toast.makeText(getApplicationContext(),"加载完成！",Toast.LENGTH_SHORT).show();
            }

            //mProgressbar.setProgress(msg.getData().getInt("size"));

            //float temp = (float) mProgressbar.getProgress()
            //  (float) mProgressbar.getMax();

           /* int progress = 100;
            if (progress == 100) {
                Toast.makeText(getApplicationContext(), "加载完成！", Toast.LENGTH_LONG).show();
            }*/

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




}
