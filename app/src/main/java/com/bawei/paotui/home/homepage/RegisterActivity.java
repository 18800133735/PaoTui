package com.bawei.paotui.home.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.paotui.important.BaseActivity;
import com.bawei.paotui.utils.ToastUtil;
import com.bawei.paotui.utils.jiami.StringIsUtils;
import com.fangz.maot.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.ResHelper.getStringRes;

public class RegisterActivity extends BaseActivity {
//  手机号
    private EditText phone;
//    验证码
    private EditText cord;
//    密码
    private EditText rg_et_password;
    private EditText rg_et_passwords;
//    提交按钮，获取验证码按钮
    private Button getCord,saveCord;
//    获取验证码倒计时
    private TextView now;


    private String iPhone;

    private String iCord;

    private int time = 60;

    private boolean flag = true;

//    获得数入的字符串
    private String number;
    private String yanzheng;
    private String password;
    private String passwords;

    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(time>0){
                    now.setText("验证码已发送"+time+"秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);

                }else{
                    now.setText("提示信息");
                    time = 60;
                    now.setVisibility(View.GONE);
                    getCord.setVisibility(View.VISIBLE);
                }

            }else{
                cord.setText("");
                now.setText("提示信息");
                time = 60;
                now.setVisibility(View.GONE);
                getCord.setVisibility(View.VISIBLE);
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initHeader();
        initWidget();
        setWidgetState();

        GetCode();
    }




    @Override
    public void initHeader() {
        initHeaderWidget();
        setTitle("注册");
    }

    @Override
    public void initWidget() {

        rg_et_password = (EditText) findViewById(R.id.rg_et_password);
        rg_et_passwords = (EditText) findViewById(R.id.rg_et_passwords);
        phone = (EditText) findViewById(R.id.phone);
        cord = (EditText) findViewById(R.id.cord);
        now = (TextView) findViewById(R.id.now);
        getCord = (Button) findViewById(R.id.getCord);
        saveCord = (Button) findViewById(R.id.saveCord);

    }

    @Override
    public void setWidgetState() {
        getCord.setOnClickListener(this);
        saveCord.setOnClickListener(this);

        SetTitleStatue(LEFT_IMAGE_RIGHT_NO);
        addImageLeftViewlistener(this,R.mipmap.left1);
    }
    private void GetCode() {
        SMSSDK.initSDK(this, "1b70bd3667928", "537c455ae5261bb145ba7ec0d5bf3025");
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.getCord:
                if(!TextUtils.isEmpty(phone.getText().toString().trim())){
                    if(phone.getText().toString().trim().length()==11){
                        iPhone = phone.getText().toString().trim();
                        SMSSDK.getVerificationCode("86",iPhone);
                        cord.requestFocus();
                        getCord.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(RegisterActivity.this, "请输入完整电话号码", Toast.LENGTH_SHORT).show();
                        phone.requestFocus();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "请输入您的电话号码", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }
                break;
            case R.id.saveCord:

                if(!TextUtils.isEmpty(cord.getText().toString().trim())){
                    if(cord.getText().toString().trim().length()==4){
                        iCord = cord.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86", iPhone, iCord);
                        flag = false;
                    }else{
                        Toast.makeText(RegisterActivity.this, "请输入完整验证码", Toast.LENGTH_SHORT).show();
                        cord.requestFocus();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    cord.requestFocus();
                }
                break;
            case R.id.iv_left_view:
                setback(LoginActivity.class);
                break;
        }
    }
    //验证码送成功后提示文字
    private void reminderText() {
        now.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过

                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                    jion();
                    handlerText.sendEmptyMessage(2);

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功

                    reminderText();

                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表

                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();

                }

            } else {

                if(flag){

                    getCord.setVisibility(View.VISIBLE);

                    Toast.makeText(RegisterActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();

                    phone.requestFocus();

                }else{

                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(RegisterActivity.this, "smssdk_network_error");

                    Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    cord.selectAll();
                    if (resId > 0) {
                        Toast.makeText(RegisterActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    };
    private void jion() {
        number = phone.getText().toString().trim();
        yanzheng = cord.getText().toString().trim();
        password = rg_et_password.getText().toString().trim();
        passwords = rg_et_passwords.getText().toString().trim();

        StringIsUtils utils = new StringIsUtils();
        if (!utils.check_str(number, password, passwords,yanzheng)){
            ToastUtil.showToast(this,"不能有为空");
        }else{
            if (password.equals(passwords)){
                jions();
            }else{
                ToastUtil.showToast(this,"密码不一致");
            }

        }
    }

    private void jions() {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("number", number);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();

    }

}
