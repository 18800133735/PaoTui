package com.bawei.paotui;

import android.app.Application;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.bawei.paotui.utils.network.PaoTuiOkhttpSingTop;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * 姓名：李鑫琪
 * 日期：2017/2/16 10:08
 * 描述：
 */
public class PaoTuiAppliction extends Application {

    public static PaoTuiOkhttpSingTop paoTuiOkhttpSingTop;
    @Override
    public void onCreate() {
        super.onCreate();


        paoTuiOkhttpSingTop = PaoTuiOkhttpSingTop.getIntance();
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);

      SDKInitializer.initialize(this);

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

    }
}
