package com.bawei.paotui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 姓名：李鑫琪
 * 日期：2016/02/11 18:29
 * 描述：
 */
public class DateTime {

    public static String setTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");

        sdf.format(date);//如:（2016-6-24 11:32:45）
        return sdf.format(date);//如:（2016-6-24 11:32:45）
    }
}
