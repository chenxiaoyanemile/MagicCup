package com.example.sweetgirl.magiccup1.activity;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import com.example.sweetgirl.magiccup1.Bean.UserId;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.event.UnityResourceEvent;
import com.example.sweetgirl.magiccup1.model.Scene1;
import com.example.sweetgirl.magiccup1.model.Scene2;
import com.example.sweetgirl.magiccup1.model.Scene31;
import com.example.sweetgirl.magiccup1.model.Scene32;
import com.example.sweetgirl.magiccup1.model.Scene33;
import com.example.sweetgirl.magiccup1.model.Scene4;
import com.example.sweetgirl.magiccup1.model.ShowAllScene;
import com.example.sweetgirl.magiccup1.model.ShowScene;
import com.example.sweetgirl.magiccup1.model.UserBean;
import com.example.sweetgirl.magiccup1.util.L;
import com.example.sweetgirl.magiccup1.util.LogUtil;
import com.example.sweetgirl.magiccup1.util.SharedPreferencesUtils;
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

/*http://139.199.190.245:8010/api/users/1
* 1.扫描二维码
* 2.获取用户id
* 3.获取关联信息
* */

public class StartActivity extends AppCompatActivity {


    final private int MY_PERMISSIONS_REQUEST_READ_CONTACTS=124;


    private static final String TAG = LogUtil.makeLogTag(StartActivity.class);

    private SharedPreferences preferences;

    private SharedPreferencesUtils sharedPreferencesUtils;

    private String res;    //返回的json数据转成String

    private String res1;   //返回的关联信息

    private String result="123";   //扫描二维码结果

    int REQUEST_CODE=20;
    private int code=1;

    private String message="success";   //第一次扫描后的返回信息
    //private String url="http://139.199.190.245:8010/api/user/"+result+"/true";   //扫描二维码
    private String url;
    private String user_id;         //用户id http://139.199.190.245:8010/api/between/0c40b024-c1c1-4d65-85b3-e8b881bca7fa
   // private String path;         //关联信息 GET http://139.199.190.245:8010/api/between/50fd89bd-bf39-46b5-8bfd-ff2ba4174f7d

    ArrayList<UnityResourceEvent> list=new ArrayList<>();


     UserId userId=new UserId();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.activity_start);

        //注册订阅者
      //  EventBus.getDefault().register(this);

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
        L.d(TAG,"第一次扫描是否有返回");

        if (requestCode == REQUEST_CODE) {
            if (null != data) {
                L.d(TAG,"第一次扫描是否有返回");
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                L.d(TAG,"第一次扫描是否有返回");
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
                StartActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "你在没有网络的异次元空间。。。", Toast.LENGTH_SHORT).show();
                    }
                });
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

            StartActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "这不是杯子的二维码o", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (message.equals("success")){

            L.i(TAG,message);

            parserJson(res);     //解析成功返回的带user_id的数据

            L.d(TAG,"解析成功返回的带user_id的数据");

            saveData(user_id);   //保存user_id
            L.d(TAG,"保存user_id");

            getMessage();        //获取关联信息
            L.d(TAG,"获取关联信息");

            //判断是否已经选择

            L.d(TAG,code+"判断是否已经选择");

        }
        else if (message.equals("Not First")){
            L.i(TAG,message);
            StartActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "不是第一次扫描o,将会替换先前用户", Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(StartActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
            L.i(TAG,message);
        }
    }
    private void parserJson(String jsonData){
        try{

            UserBean data=JSON.parseObject(jsonData,ScanCodeResult.class).getUser();

            user_id=data.getUser_id();

            L.i(TAG,"user_id"+user_id);

        }catch(Exception e){
                e.printStackTrace();
        }
    }

    //[6]保存数据
    private void saveData(String data){

        SharedPreferences preferences=getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("user_id", user_id);
        editor.apply();
        L.i(TAG,"保存用户id"+user_id);

    }
   //获取关联信息
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
        String path="http://139.199.190.245:8010/api/between/"+user_id;
        //http://139.199.190.245:8010/api/between/50fd89bd-bf39-46b5-8bfd-ff2ba4174f7d

        Request request = new Request.Builder()
                .url(path)
                .build();
        //[3]将Request封装为call
        Call call=client.newCall(request);
        //[4]执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                StartActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "服务器异常。。。",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                L.i(TAG,"onFailure"+e.getMessage());
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {

                res1=response.body().string();

                ParseJson(res1);



                L.d(TAG,"返回的关联信息"+res1);
            }
        });
    }
    //[4]解析关联数据
    private void ParseJson(String jsonData) {

        try{

            ShowScene showScene=JSON.parseObject(jsonData,ShowScene.class);


            code=showScene.getCode();


            if (code==200)
            {
                //json数据解析成一个对象
                ShowAllScene allScene=JSON.parseObject(jsonData,ShowScene.class).getData();
                String text=allScene.getText();
                L.d(TAG,text);

                Scene1 scene1=JSON.parseObject(jsonData,ShowScene.class).getData().getDbScene1();
                String resource1=scene1.getResource();
                L.d(TAG,resource1);


                Scene2 scene2=JSON.parseObject(jsonData,ShowScene.class).getData().getDbScene2();
                String resource2=scene2.getResource();
                L.d(TAG,resource2);


                Scene31 scene31=JSON.parseObject(jsonData,ShowScene.class).getData().getDbScene31();
                String resource31=scene31.getResource();
                L.d(TAG,resource31);


                Scene32 scene32=JSON.parseObject(jsonData,ShowScene.class).getData().getDbScene32();
                String resource32=scene32.getResource();
                L.d(TAG,resource32);


                Scene33 scene33=JSON.parseObject(jsonData,ShowScene.class).getData().getDbScene33();
                String resource33=scene33.getResource();
                L.d(TAG,resource33);


                Scene4 scene4=JSON.parseObject(jsonData,ShowScene.class).getData().getDbScene4();
                String resource4=scene4.getResource();
                L.d(TAG,resource4);

                saveSelectData(text,resource1,resource2,resource31,resource32,resource33,resource4);

                StartActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //存在对方赠送的礼物
                        Intent intent1 = new Intent(StartActivity.this, DownloadActivity.class);
                        startActivity(intent1);
                        finish();
                        L.i(TAG,"查看对方送的礼物");

                    }
                });


            }
            else
            {
                L.i(TAG,"没有定制的礼物");
                StartActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "没有对方赠送的礼物，赶紧去定制礼物送给对方吧",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StartActivity.this, SelectActivity.class);
                        startActivity(intent);
                        finish();
                        L.i(TAG, "选择星座");

                    }
                });

            }


        }

        catch(Exception e){

            e.printStackTrace();
        }

    }

    //[6]保存数据
    private void saveSelectData(String text,String resource1,String resource2,String resource31,
        String resource32,String resource33,String resource4){

        SharedPreferences preferences=getSharedPreferences("SceneTwo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("text", text);
        editor.putString("resource1", resource1);
        editor.putString("resource2", resource2);
        editor.putString("backgroundResource", resource31);   //背景
        editor.putString("sceneResource", resource32);    //动作
        editor.putString("weatherResource", resource33);   //天气，时间
        editor.putString("resource4", resource4);
        editor.apply();
        L.i(TAG,"保存用户text"+text+resource1);

    }



}
