package com.example.sweetgirl.magiccup1.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.InputStream;
import java.net.URL;

/**
 *异步加载图片
 */

public class AsyncTaskImageLoad extends AsyncTask<String, Integer, Bitmap> {

    private ImageView Image=null;
    public AsyncTaskImageLoad(ImageView img)
    {
        Image=img;
    }
    //运行在子线程中
    protected Bitmap doInBackground(String... params) {

                 try
                 {
                     URL url=new URL(params[0]);
                     OkHttpClient client = new OkHttpClient();
                     Request request = new Request.Builder()
                             .url(url)
                             .build();
                     Response response = client.newCall(request).execute();
                     InputStream is = response.body().byteStream();
                     Bitmap bm = BitmapFactory.decodeStream(is);
                     L.i("下载推案","好");
                     return bm;
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 return null;
             }

    protected void onPostExecute(Bitmap result)
         {
                 if(Image!=null && result!=null)
                     {
                         Image.setImageBitmap(result);
                     }

                 super.onPostExecute(result);
             }
}
