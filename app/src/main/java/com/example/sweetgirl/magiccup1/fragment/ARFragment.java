package com.example.sweetgirl.magiccup1.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.activity.ShowARActivity;
import com.example.sweetgirl.magiccup1.activity.StartActivity;
import com.example.sweetgirl.magiccup1.util.FileDownloadThread;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;


public class ARFragment extends Fragment {

    private static final String TAG = LogUtil.makeLogTag(ARFragment.class);

    private Button ar_btn_scan;

    private ProgressBar pb_show_download;
    /** 显示下载进度TextView */
    private TextView tv_download_msg;

    private boolean DownloadComplete;

    private Message msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ar, container, false);
        init(view);
        //doGet();
        return view;
    }

    public void init(View view){
        ar_btn_scan=(Button)view.findViewById(R.id.ar_btn_scan);
        pb_show_download=(ProgressBar)view.findViewById(R.id.pb_show_download);
        tv_download_msg=(TextView)view.findViewById(R.id.tv_download_msg);


        ar_btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DownloadComplete){
                    Intent intent=new Intent(getActivity(), ShowARActivity.class);
                    startActivity(intent);

                }
                else {

                    Toast.makeText(getActivity(),"资源还未下载完成，请耐心等待。。。",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //[3]将扫描结果提交到服务器
    private  void doGet(){
        final String downloadUrl1 = "http://ojphnknti.bkt.clouddn.com/scene1/Piscesman.assetbundle";
        final String fileName1="Piscesman.assetbundle";

        final String downloadUrl2 = "http://ojphnknti.bkt.clouddn.com/scene1/VirgoWoman.assetbundle";
        final String fileName2="VirgoWoman.assetbundle";

        final String downloadUrl3 = "http://ojphnknti.bkt.clouddn.com/scene32/ACHuge.assetbundle";
        final String fileName3="ACHuge.assetbundle";

        final String downloadUrl4 = "http://ojphnknti.bkt.clouddn.com/scene31/BGYinXing.assetbundle";
        final String fileName4="BGYinXing.assetbundle";

        final String downloadUrl5 = "http://ojphnknti.bkt.clouddn.com/scene33/TMAfternoon.assetbundle";
        final String fileName5="TMAfternoon.assetbundle";

        final String downloadUrl6 = "http://ojphnknti.bkt.clouddn.com/scene4/huapen.assetbundle";
        final String fileName6="huapen.assetbundle";

        final String downloadUrl7 = "http://ojphnknti.bkt.clouddn.com/scene1/DYM.assetbundle";
        final String fileName7="DYM.assetbundle";

        final String downloadUrl8 = "http://ojphnknti.bkt.clouddn.com/scene4/disimu.assetbundle";
        final String fileName8="disimu.assetbundle";

        final String downloadUrl9 = "http://ojphnknti.bkt.clouddn.com/scene31/Cloud.assetbundle";
        final String fileName9="Cloud.assetbundle";


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doDownload(downloadUrl1,fileName1);
                    L.i(TAG,"第一个下载完成");
                    doDownload(downloadUrl2,fileName2);
                    L.i(TAG,"第二个下载完成");

                    doDownload(downloadUrl3,fileName3);
                    L.i(TAG,"第三个下载完成");
                    doDownload(downloadUrl4,fileName4);
                    L.i(TAG,"第四个下载完成");

                    doDownload(downloadUrl5,fileName5);
                    L.i(TAG,"第五个下载完成");
                    doDownload(downloadUrl6,fileName6);
                    L.i(TAG,"第六个下载完成");
                    doDownload(downloadUrl7,fileName7);
                    L.i(TAG,"第七个下载完成");
                    doDownload(downloadUrl8,fileName8);
                    L.i(TAG,"第八个下载完成");
                    doDownload(downloadUrl9,fileName9);
                    L.i(TAG,"第九个下载完成");

                    DownloadComplete=true;
                    Toast.makeText(getActivity(), "下载完成！", Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //下载文件
    private void doDownload(String downloadUrl,String fileName){
        // 获取SD卡路径
        String path2=getContext().getExternalFilesDir("")+"/";

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
            pb_show_download.setProgress(msg.getData().getInt("size"));

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



}