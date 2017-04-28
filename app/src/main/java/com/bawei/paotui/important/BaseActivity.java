package com.bawei.paotui.important;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangz.maot.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import com.bawei.paotui.autolayout.AutoLayoutActivity;

/**
 * 李鑫琪
 * 2017/02/11 11:27
 */
public abstract class BaseActivity extends AutoLayoutActivity implements  View.OnClickListener{

    //宏定义
    public static final int LEFT_IMAEG_RIGHT_IMAGE = 1;//左边图片 和右边图片
    public static final int LEFT_IMAGE_RIGHT_TEXT = 2;//左边图片,右边文字
    public static final int LEFT_IMAGE_RIGHT_NO = 3; //左边图片,右边没有
    public static final int LEFT_TEXT_RIGHT_NO = 4; //左边文字,右边没有
    public static final int LEFT_TEXT_RIGHT_IMAGE = 5; //左边文字右边图片
    public static final int LEFT_TEXT_RIGHT_TEXT = 6; //左边文字右边文字
    public static final int LEFT_NO_RIGHT_IMAGE = 7; //左边没有,右边图片
    public static final int LEFT_NO_RIGHT_TEXT = 8; //左边没有,右边文字
    public static final int LEFT_NO_RIGHT_NO = 9; //左边没有,右边没有

    private ImageView iv_right_view;
    private ImageView iv_left_view;
    private TextView id_tv_left_back;
    private TextView id_tv_right_setting;
    private TextView tv_main_title_textview;

    //用来在做网络请求的时候封装的数据
    public Map<String,String> map;

    //数据解析用的
    public Gson gson = new Gson();

    public Intent intent;

    public abstract void initHeader();
    public abstract void initWidget();
    public abstract void setWidgetState();

    public void initHeaderWidget() {

        //初始化加载网络的数据
        map = new HashMap<String, String>();

//        map.put("terminalType","MOBIE");
//        map.put("os","ANDROID");

        id_tv_left_back = (TextView) findViewById(R.id.id_tv_back);
        id_tv_right_setting = (TextView) findViewById(R.id.tv_main_title_setting);
        tv_main_title_textview = (TextView) findViewById(R.id.tv_main_title_textview);
        iv_right_view = (ImageView) findViewById(R.id.iv_right_view);
        iv_left_view = (ImageView) findViewById(R.id.iv_left_view);

    }
    //    左边图片或文字监听，显示
    public void addImageLeftViewlistener(View.OnClickListener listener){
        iv_left_view.setVisibility(View.VISIBLE);
        iv_left_view.setOnClickListener(listener);

    }
    public void addImageLeftViewlistener(View.OnClickListener listener,int id){
        iv_left_view.setVisibility(View.VISIBLE);
        iv_left_view.setOnClickListener(listener);
        iv_left_view.setImageResource(id);

    }
    public void addBtuLeftViewlistener(View.OnClickListener listener){
        id_tv_left_back.setVisibility(View.VISIBLE);
        id_tv_left_back.setOnClickListener(listener);
    }
    public void addLeftViewlistener(View.OnClickListener listener,String title){
        id_tv_left_back.setVisibility(View.VISIBLE);
        id_tv_left_back.setOnClickListener(listener);
        id_tv_left_back.setText(title);
    }
//    右边图片或文字监听，显示
    public void addImageRightViewlistener(View.OnClickListener listener){
        iv_right_view.setVisibility(View.VISIBLE);
        iv_right_view.setOnClickListener(listener);
    }
    public void addImageRightViewlistener(View.OnClickListener listener,int id){
        iv_right_view.setVisibility(View.VISIBLE);
        iv_right_view.setOnClickListener(listener);
        iv_right_view.setImageResource(id);
    }
    public void addBtuRightViewlistener(View.OnClickListener listener){
        id_tv_right_setting.setVisibility(View.VISIBLE);
        id_tv_right_setting.setOnClickListener(listener);
    }
    public void addBtuRightViewlistener(View.OnClickListener listener,String title){
        id_tv_right_setting.setVisibility(View.VISIBLE);
        id_tv_right_setting.setOnClickListener(listener);
        id_tv_right_setting.setText(title);
    }


    public void setTitle(String title) {//设置中间文字的内容
        id_tv_left_back.setVisibility(View.GONE);
        id_tv_right_setting.setVisibility(View.GONE);
        iv_right_view.setVisibility(View.GONE);
        iv_left_view.setVisibility(View.GONE);
        tv_main_title_textview.setText(title);
    }

    public void Left_Image(){
        iv_left_view.setVisibility(View.VISIBLE);
    }
    public void Left_Text(){
        id_tv_left_back.setVisibility(View.VISIBLE);
    }
    public void Right_Image(){
        iv_right_view.setVisibility(View.VISIBLE);
    }
    public void Right_Text(){
        id_tv_right_setting.setVisibility(View.VISIBLE);
    }
//   隐藏控件
    public void SetGone(){
        id_tv_left_back.setVisibility(View.GONE);
        id_tv_right_setting.setVisibility(View.GONE);
        iv_right_view.setVisibility(View.GONE);
        iv_left_view.setVisibility(View.GONE);
    }

    public <T> void setback(Class<T> t) {
        intent = new Intent(this, t);
        startActivity(intent);
//        finish();
    }
    public <T> void setbackput(Class<T> t,String title,int password_title) {
        intent = new Intent(this, t);
        intent.putExtra(title,password_title);
        startActivity(intent);
    }

    //封装
    public void SetTitleStatue(int statue){
        switch (statue){
            case LEFT_IMAEG_RIGHT_IMAGE:

                Left_Image();
                Right_Image();

                break;
            case LEFT_IMAGE_RIGHT_TEXT:

                Left_Image();
                Right_Text();

                break;
            case LEFT_IMAGE_RIGHT_NO:
                SetGone();
                Left_Image();

                break;
            case LEFT_TEXT_RIGHT_NO:
                SetGone();
                Left_Text();
                break;
            case LEFT_TEXT_RIGHT_IMAGE:
                Left_Text();
                Right_Image();
                break;
            case LEFT_TEXT_RIGHT_TEXT:
                Left_Text();
                Right_Text();
                break;
            case LEFT_NO_RIGHT_IMAGE:
                SetGone();
                Right_Image();
                break;
            case LEFT_NO_RIGHT_TEXT:
                SetGone();
                Right_Text();
                break;
            case LEFT_NO_RIGHT_NO:
                SetGone();
                break;
        }
    }

    public void exit(){

    }
}
