package com.example.sweetgirl.magiccup1.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.util.FileDownloadThread;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;


import java.io.File;
import java.net.URL;
import java.net.URLConnection;

public class DownloadActivity extends AppCompatActivity {

    private static final String TAG = LogUtil.makeLogTag(DownloadActivity.class);

    private String resource1;
    private String resource2;
    private String resource31;
    private String resource32;
    private String resource33;
    private String resource4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        getUserResource();  //获取传过的数据

        L.d(TAG,"获取传过的数据");

        GetResourceData(); //下载资源

        L.d(TAG,"下载资源");



    }


    //从sharedPreferences文件中读取存储的user_id
    private void getUserResource(){

        SharedPreferences preferences=getSharedPreferences("gift", Context.MODE_PRIVATE);
        resource1=preferences.getString("resource1", "resource1");
        resource2=preferences.getString("resource2", "resource2");
        resource31=preferences.getString("resource31", "resource31");
        resource32=preferences.getString("resource32", "resource32");
        resource33=preferences.getString("resource33", "resource33");
        resource4=preferences.getString("resource4", "resource4");

        L.i(TAG,"从sharedPreferences文件中读取存储的resource1"+resource1);

    }


    private void GetResourceData(){

        String fileName1=judgmentDownloadComplete(resource1);
        doDownload(resource1,fileName1);

        L.d(TAG,"第1个资源下载完成"+fileName1);

        String fileName2=judgmentDownloadComplete(resource2);
        doDownload(resource2,fileName2);
        L.d(TAG,"第2个资源下载完成"+fileName2);

        String fileName31=judgmentDownloadComplete(resource31);
        doDownload(resource31,fileName31);
        L.d(TAG,"第3个资源下载完成"+fileName31);

        String fileName32=judgmentDownloadComplete(resource32);
        doDownload(resource32,fileName32);

        L.d(TAG,"第4个资源下载完成"+fileName32);

        String fileName33=judgmentDownloadComplete(resource33);
        doDownload(resource33,fileName33);

        L.d(TAG,"第5个资源下载完成"+fileName33);

        String fileName4=judgmentDownloadComplete(resource4);
        doDownload(resource4,fileName4);

        L.d(TAG,"第6个资源下载完成"+fileName4);


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
        String path = Environment.getExternalStorageDirectory()
                + "/data/";
        L.d(TAG, "download file  path:" + path);
        File file = new File(path);
        // 如果SD卡目录不存在创建
        if (!file.exists()) {
            file.mkdir();
        }
        // 简单起见，我先把URL和文件名称写死，其实这些都可以通过HttpHeader获取到
       // downloadUrl = "http://oexlqeny2.bkt.clouddn.com/scene0.zip";
        //fileName = "scene1";
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
                Toast.makeText(getApplicationContext(),"加载资源中！",Toast.LENGTH_SHORT).show();
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
