package com.example.sweetgirl.magiccup1.outdated;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.model.Scene;
import com.example.sweetgirl.magiccup1.util.FileDownloadThread;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.example.sweetgirl.magiccup1.view.FindItemDataSource;
import com.example.sweetgirl.magiccup1.view.ListViewAdapter;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class MyGiftScene1Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = LogUtil.makeLogTag(MyGiftScene1Activity.class);

    private String res;

    private ListView scene1_lv_story;

    List<Scene> sceneList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift_scene1);
        initView();

    }
    private void initView(){

        scene1_lv_story=(ListView)findViewById(R.id.lv_story);
        doGet();
        ListViewAdapter listViewAdapter=new ListViewAdapter(R.layout.scene1_list_item,this,sceneList);
        scene1_lv_story.setAdapter(listViewAdapter);

        L.i(TAG,"这下终于好了");
    }

    private void initListData(){
        SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(), FindItemDataSource.getMaps(),
                R.layout.scene1_list_item,
                new String[]{"image","text01","text02"},
                new int[]{R.id.scene1_iv_photo,R.id.scene1_tv_name,R.id.scene1_tv_dep});
        scene1_lv_story.setAdapter(adapter);
        scene1_lv_story.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    //[3]从服务器获取数据
    private  boolean doGet(){
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
        return false;
    }
    private void enqueue(){
        //[1]拿到OkHttpClient
        OkHttpClient client = new OkHttpClient();
        //[2]构造Request   http://139.199.190.245:8010/api/user
        Request request = new Request.Builder()
                .url("http://119.29.222.54:8010/api/scene/1")
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
                sceneList=jsonParser(res);
                L.i(TAG," "+sceneList.size());
            }
        });
    }
    //[4]解析数据
    private List<Scene> jsonParser(String jsonData) {
        List<Scene> sceneList=new ArrayList<Scene>();
        try{
            JSONTokener jsonTokener = new JSONTokener(jsonData);
            org.json.JSONObject jsonObject = (org.json.JSONObject) jsonTokener.nextValue();
            org.json.JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                org.json.JSONObject object = array.getJSONObject(i);
                Scene scene1 = new Scene();
                scene1.setName(object.getString("name"));
                String name=scene1.getName();
                L.i(TAG,"这次终于成功了"+name);
                scene1.setDepiction(object.getString("depiction"));
                scene1.setImage(object.getString("image"));
                sceneList.add(scene1);

                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sceneList;
    }
    //下载文件
    private void doDownload(){
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
        String downloadUrl = "http://oexlqeny2.bkt.clouddn.com/scene1";
        String fileName = "scene1";
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
