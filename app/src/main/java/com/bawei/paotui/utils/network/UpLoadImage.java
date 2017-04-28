package com.bawei.paotui.utils.network;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * 姓名：李鑫琪
 * 日期：2017/2/22 15:23
 * 描述：
 */
public class UpLoadImage {


    public static void upFile(final String filePath, final String fileName, final String UP_URL){

        new AsyncTask<String, Integer, String>(){

            @Override
            protected String doInBackground(String... params) {
                String rs = "";
                String httpUrl = UP_URL+"?fileName="+fileName;
                HttpPost request = new HttpPost(httpUrl);
                File file = new File(filePath);
                FileEntity entity = new FileEntity(file,"binary/octet-stream");
                entity.setContentEncoding("binary/octet-stream");
                request.setEntity(entity);

                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response;
                try {
                    response = httpClient.execute(request);
                    //如果返回状态为200，获得返回的结果
                    if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                        //图片上传成功
                        Log.i("TAG","上传成功");
                        HttpEntity rsEntity = response.getEntity();
                        rs = EntityUtils.toString(rsEntity, "utf-8");
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return rs;

            }

            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);

                Log.d("uplaod",result);
            }
        }.execute();
    }

}
