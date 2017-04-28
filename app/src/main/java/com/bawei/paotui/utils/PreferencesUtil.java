package com.bawei.paotui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fangz.maot.R;


public class PreferencesUtil {

    public static SharedPreferences preferences;

    //初始化 SharedPreferences
    public static void init(Context context){

        if(null == preferences){
            preferences = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE);
        }
    }


    //讲数据加入到 SharedPreferences 里面
    //方法里面使用泛型 参数的泛型的时候必须 声明
    // preferences 可以添加的数据类型 基本数据类型 引用数据类型(String) set


    public static  <T> void put(Context context,String key, T values){

        init(context);

        SharedPreferences.Editor editor = preferences.edit();

        //判断当前是什么类型
        if(values instanceof String){
            editor.putString(key,values.toString());
        }else if(values instanceof Integer){
            editor.putInt(key,((Integer) values).intValue());
        }else if(values instanceof Long){
            editor.putLong(key,((Long) values).longValue());
        }else if(values instanceof Boolean){
            editor.putBoolean(key,((Boolean)values).booleanValue());
        }else if(values instanceof Float){
            editor.putFloat(key,((Float) values).floatValue());
        }

        editor.commit();
        editor.apply();

    }


    //将数据取出来
    // T 默认返回数据
    public static <T> T get(Context context, String key, T values){

        init(context);

        Object o = null;

        if(values instanceof String){
            o = preferences.getString(key,values.toString());
        }else if(values instanceof Integer){
            o = preferences.getInt(key,((Integer) values).intValue());
        }else if(values instanceof Long){
            o = preferences.getLong(key,((Long) values).longValue());
        }else if(values instanceof Boolean){
            o = preferences.getBoolean(key,((Boolean) values).booleanValue());
        }else if(values instanceof Float){
            o = preferences.getFloat(key,((Float) values).floatValue());
        }

        T t = (T)o;

        return t;
    }


    public static void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


}
