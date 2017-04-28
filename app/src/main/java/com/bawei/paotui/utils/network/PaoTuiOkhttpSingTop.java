package com.bawei.paotui.utils.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 姓名：李鑫琪
 * 日期：2017/2/16 10:04
 * 描述：
 */
public class PaoTuiOkhttpSingTop {

    //声明对象
    CallbackM callbackM;

    //获取主线程 handler
    private Handler handler;
    //解析对象
    private Gson gson;

    //声明对象 私有化
    private static volatile PaoTuiOkhttpSingTop paoTuiOkhttpSingTop;
    //构造函数 私有化
    private PaoTuiOkhttpSingTop() {
        handler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }
    // 提供公共方法
    public static PaoTuiOkhttpSingTop getIntance(){

        if (null == paoTuiOkhttpSingTop){
            synchronized (PaoTuiOkhttpSingTop.class){
                if (null == paoTuiOkhttpSingTop){
                    paoTuiOkhttpSingTop = new PaoTuiOkhttpSingTop();
                }
            }
        }
        return paoTuiOkhttpSingTop;
    }


 /*
    * 拼接 url的方法
    *
    */

    public <T> void setUrl(String url, Map<String,String> map, Class<T> Cls, Methods methods){


        switch (methods){

            case GET: //get 方法

                get(url,map,Cls);

                break;
            case PSOT: // post方法

                Post(url,map,Cls);

                break;

        }

    }


    /*
    * get 方法
    */

    public <T> void get(String url,Map<String,String> map,Class<T> Cls){

        int i=0;

        // 遍历
        Iterator<String> iterator = map.keySet().iterator();

        while (iterator.hasNext()){

            String key  = iterator.next();
            String value = map.get(key);

            if(i == 0){

                url +=key+"="+value;

            }else {

                url += "&" + key+"="+value;
            }

            i++;

        }
        geturl(url,Cls);
    }


    // get  请求
    public <T> void geturl(String url, final Class<T> Cls) {

        //创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //创建一个Request

        final Request request = new Request.Builder()
                .url(url)
                .build();

        ////new call
        Call call = okHttpClient.newCall(request);

        //回调接口

        call.enqueue(new Callback() {


            @Override
            public void onFailure(Request request, IOException e) {

                if (null != callbackM) {
                    callbackM.onFailure(request, e);
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String json = response.body().string();


                getJSon(json, Cls);

            }
        });


    }


    /*
    * GSON 解析
    *
    * */

    public <T> void getJSon(String json, Class<T> Cls) {

        T t1 = gson.fromJson(json, Cls);

        MainThread(t1);

    }


    public void MainThread(final Object response) {

        handler.post(new Runnable() {
            @Override
            public void run() {

                if (null != callbackM) {
                    callbackM.onResponse(response);
                }
            }
        });
    }


    /*
    *  post 方法
    *  url 请求网络的地址
    *  map post 请求的参数
    *
    */

    public <T> void Post(String url, Map<String,String> map, final Class<T> Cls){


        //得到 client 对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //得到 body
        FormEncodingBuilder builder = new FormEncodingBuilder();
        //遍历
        Iterator<String> iterator = map.keySet().iterator();
        //加入到 builder 对象

        while (iterator.hasNext()){

            String key = iterator.next();
            String values = map.get(key);

            //加入到 builder 里面
            builder.add(key,values);

        }

        // 得到 request

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        Log.i("url---",url);
        //得到 call

        Call call = okHttpClient.newCall(request);


        //接口回调

        call.enqueue(new Callback() {


            @Override
            public void onFailure(Request request, IOException e) {

                if (null != callbackM) {
                    callbackM.onFailure(request, e);
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String json = response.body().string();
                Log.d("YYYYY---",json);
                getJSon(json, Cls);
            }
        });


    }




    //设置监听的方法

    public void setCallbackM(CallbackM callbackM) {
        this.callbackM = callbackM;
    }


    //观察者模式
    public interface CallbackM {

        public void onFailure(Request request, IOException e);

        public void onResponse(Object response);
    }



    /*
    *  定义枚举类型
    *  GET PSOT DOWN
    *  关键字段 enum
    *
    */

    public enum Methods{

        GET,PSOT,DOWN
    }

}
