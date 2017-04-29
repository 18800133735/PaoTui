package com.bawei.paotui.home.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.paotui.important.BaseActivity;
import com.bawei.paotui.important.MainActivity;
import com.bawei.paotui.PaoTuiAppliction;
import com.bawei.paotui.home.bean.UserBean;
import com.bawei.paotui.utils.Constant;
import com.bawei.paotui.utils.PreferencesUtil;
import com.fangz.maot.R;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bawei.paotui.utils.jiami.DESMD5Utils;
import com.bawei.paotui.utils.network.PaoTuiOkhttpSingTop;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class LoginActivity extends BaseActivity {

    private EditText lg_et_number;
    private EditText lg_et_password;
    private Button lg_bt_log;
    private Button lg_bt_logwx;
    private Button lg_bt_logqq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initHeader();
        initWidget();
        setWidgetState();
    }


    @Override
    public void initHeader() {
        initHeaderWidget();
        setTitle("登录");
    }

    @Override
    public void initWidget() {
        lg_et_number = (EditText) findViewById(R.id.lg_et_number);
        lg_et_password = (EditText) findViewById(R.id.lg_et_password);

        lg_bt_log = (Button) findViewById(R.id.lg_bt_log);
        lg_bt_logwx = (Button) findViewById(R.id.lg_bt_logwx);
        lg_bt_logqq = (Button) findViewById(R.id.lg_bt_logqq);

    }

    @Override
    public void setWidgetState() {
//        lg_et_number.setOnClickListener(this);
//        lg_et_password.setOnClickListener(this);
        lg_bt_log.setOnClickListener(this);
        lg_bt_logwx.setOnClickListener(this);
        lg_bt_logqq.setOnClickListener(this);

        SetTitleStatue(LEFT_IMAGE_RIGHT_TEXT);
        addImageLeftViewlistener(this, R.mipmap.left1);
        addBtuRightViewlistener(this, "注册");

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lg_bt_log:
                pOk();
                break;
            case R.id.lg_bt_logwx:

                break;
            case R.id.lg_bt_logqq:
                qqlog();
                break;
            case R.id.iv_left_view:
                setback(MainActivity.class);
                break;
            case R.id.tv_main_title_setting:
                setback(RegisterActivity.class);
                break;
        }
    }

    private void qqlog() {
        UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);

        mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    public void pOk() {

        String number = lg_et_number.getText().toString().trim();
        String password = lg_et_password.getText().toString().trim();
//      MD5加密
        DESMD5Utils desmd5Utils = new DESMD5Utils();
        String num = desmd5Utils.encrypt(number);

        String pw1 = DESMD5Utils.MD5(password, false);
        String pw = desmd5Utils.encrypt(pw1);
//      拼接网络请求的url
        String url = Constant.PaoTui_SERVICE + Constant.ServiceConstant.SEND_LOGIN;
//      map集合保存post的参数
        Map<String, String> map = new HashMap<>();
        map.put("phone_num", num);
        map.put("user_password", pw);


        PaoTuiAppliction.paoTuiOkhttpSingTop.setUrl(url, map, UserBean.class, PaoTuiOkhttpSingTop.Methods.PSOT);

        //观察者模式
        PaoTuiAppliction.paoTuiOkhttpSingTop.setCallbackM(new PaoTuiOkhttpSingTop.CallbackM() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Object response) {
//              网络请求获得返回的对象
                UserBean userBean = (UserBean) response;
                Log.d("YYYYY", userBean.toString());

                if (userBean.getCode().equals("07000")) {
                    setback(MainActivity.class);
//                    保存登录的状态
                    PreferencesUtil.put(getApplication(), "flag", true);
                    Log.d("getUserId------login", userBean.userinfo.getUserId());
//                    设置推送的标识（userid）
                    JPushInterface.setAliasAndTags(LoginActivity.this, userBean.userinfo.getUserId(), null, null);
//                    保存token
                    PreferencesUtil.put(getApplicationContext(), "token", userBean.getUserinfo().getToken());
                } else if (userBean.getCode().equals("07050")) {
                    Toast.makeText(LoginActivity.this, userBean.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

}
