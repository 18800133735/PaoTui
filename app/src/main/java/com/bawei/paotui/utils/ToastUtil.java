package com.bawei.paotui.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 姓名：李鑫琪
 * 日期：
 * 描述：
 */
public class ToastUtil {

    public static void showToast(Context context,String msg){

       Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }
}
