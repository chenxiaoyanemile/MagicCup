package com.example.sweetgirl.magiccup1.activity;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.sweetgirl.magiccup1.Bean.ScanCodeResult;
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*http://139.199.190.245:8010/api/users/2
* 1.扫描二维码
* 2.获取用户id
* 3.获取关联信息
* */

public class StartActivity extends AppCompatActivity {


    final private int MY_PERMISSIONS_REQUEST_READ_CONTACTS=124;
    //final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    private static final String TAG = LogUtil.makeLogTag(StartActivity.class);

    private SharedPreferences preferences;
    private String res;    //返回的json数据转成String

    private String result;   //扫描二维码结果

    int REQUEST_CODE;

    private String message;   //第一次扫描后的返回信息
    //private String url="http://139.199.190.245:8010/api/user/"+result+"/true";   //扫描二维码
    private String url;
    private String user_id;         //用户id http://139.199.190.245:8010/api/between/0c40b024-c1c1-4d65-85b3-e8b881bca7fa
    private String path="http://139.199.190.245:8010/api/between/"+user_id;         //关联信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.activity_start);

        preferences = getSharedPreferences("count", Context.MODE_PRIVATE);

        init();
        L.d(TAG,"界面");
       insertDummyContactWrapper();
        L.d(TAG,"请求权限");
    }


    //授权方法
    private static final String TAGLOG = "Contacts";
    private void insertDummyContact() {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "__DUMMY CONTACT from runtime permissions sample");
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (Exception e) {
            L.d(TAGLOG, "Could not add a new contact: " + e.getMessage());
        }
    }

   final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "请赋予App以下权限 " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(StartActivity.this,permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(StartActivity.this,permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        insertDummyContact();
    }
    //添加权限
    private boolean addPermission(List<String> permissionsList, String permission) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(StartActivity.this,
                Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(StartActivity.this,permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(StartActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    //请求权限的结果处理
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[]grantResults){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
            {

                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(StartActivity.this, "有一些权限未授权", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
                    url="http://139.199.190.245:8010/api/user/"+result+"/true";
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
        L.d(TAG,"URL="+url);
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
            ScanCodeResult scanNumber=JSON.parseObject(jsonData,ScanCodeResult.class);
            message = scanNumber.getMessage();
            L.i("message"," "+scanNumber.getMessage());
            if (message==null){
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "你在没有网络的异次元空间。。。", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            else {
                judgeResult(message);
            }

        }catch(Exception e){
                e.printStackTrace();
        }

    }
    //[5]判断返回结果
    private void judgeResult(String message){

        if (message.equals("serial not found")){
            /*Looper.prepare();
            Toast.makeText(getApplicationContext(), "这不是杯子的二维码o", Toast.LENGTH_SHORT).show();
            Looper.loop();
            L.i(TAG,message);*/
            StartActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "这不是杯子的二维码o", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (message.equals("success")){
            L.i(TAG,message);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        parserJson(res);
                        saveData(user_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            //判断是否已经选择
            if (relationship()){
                //存在对方赠送的礼物  ar
                Intent intent = new Intent(StartActivity.this, DownloadActivity.class);
                startActivity(intent);
                finish();
                L.i(TAG,"查看对方送的礼物");
            }
            Intent intent = new Intent(StartActivity.this, SelectActivity.class);
            startActivity(intent);
            finish();

            L.i(TAG, "选择星座");
        }
        else if (message.equals("Not First")){
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
                ParseJson(res);
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
            L.d(TAG,resource1);
            //doDownload(resource1,"scene1");

            Scene2 scene2=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene2();
            String resource2=scene2.getResource();
            L.d(TAG,resource2);
           // doDownload(resource2,"scene2");

            Scene31 scene31=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene31();
            String resource31=scene31.getResource();
            L.d(TAG,resource31);
            //doDownload(resource31,"scene31");

            Scene32 scene32=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene32();
            String resource32=scene32.getResource();
            L.d(TAG,resource32);
           // doDownload(resource32,"scene32");

            Scene33 scene33=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene33();
            String resource33=scene33.getResource();
            L.d(TAG,resource33);
            //doDownload(resource33,"scene33");

            Scene4 scene4=JSON.parseObject(jsonData,ShowAllScene.class).getDbScene4();
            String resource4=scene4.getResource();
            L.d(TAG,resource4);
            //doDownload(resource4,"scene4");
        }catch(Exception e){
            e.printStackTrace();
        }

    }




}
