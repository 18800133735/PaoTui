package com.bawei.paotui.home.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.paotui.important.BaseActivity;
import com.bawei.paotui.PaoTuiAppliction;
import com.bawei.paotui.home.bean.UserBean;
import com.bawei.paotui.utils.Constant;
import com.bawei.paotui.utils.jiami.DESMD5Utils;
import com.bawei.paotui.utils.network.PaoTuiOkhttpSingTop;
import com.fangz.maot.R;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewsActivity extends BaseActivity {

    private EditText news_et_username;
    private EditText news_et_sex;
    private EditText news_et_adress;
    private Button news_bt_tijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initHeader();
        initWidget();
        setWidgetState();

    }

    @Override
    public void initHeader() {
        initHeaderWidget();
        setTitle("完善信息");
    }

    @Override
    public void initWidget() {

        news_et_username = (EditText) findViewById(R.id.news_et_username);
        news_et_sex = (EditText) findViewById(R.id.news_et_sex);
        news_et_adress = (EditText) findViewById(R.id.news_et_adress);
        news_bt_tijiao = (Button) findViewById(R.id.news_bt_tijiao);



    }

    @Override
    public void setWidgetState() {
        news_bt_tijiao.setOnClickListener(this);

        SetTitleStatue(LEFT_IMAGE_RIGHT_NO);
        addImageLeftViewlistener(this,R.mipmap.left1);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.news_bt_tijiao:
                pOk();
                break;
            case R.id.iv_left_view:
                setback(RegisterActivity.class);
                break;
        }
    }


    public void pOk(){

        Intent intent = getIntent();
        String number1 = intent.getStringExtra("number");
        String password1 = intent.getStringExtra("password");
        String username = news_et_username.getText().toString().trim();
        String adress = news_et_adress.getText().toString().trim();
//     手机号加密
        DESMD5Utils desmd5Utils = new DESMD5Utils();
        String num =desmd5Utils.encrypt(number1);

//      密码加密
        String pw1 = DESMD5Utils.MD5(password1, false);
        String pw =desmd5Utils.encrypt(pw1);

//     所在区域加密
        String district =desmd5Utils.encrypt(adress);

//       昵称加密
        String nick_name =desmd5Utils.encrypt(username);

        String url = Constant.PaoTui_SERVICE+Constant.ServiceConstant.SEND_REGISTER;

        Map<String,String> map = new HashMap<>();
        map.put("phone_num",num);
        map.put("user_password",pw);
        map.put("user_district",district);
        map.put("nick_name",nick_name);


        PaoTuiAppliction.paoTuiOkhttpSingTop.setUrl(url,map, UserBean.class, PaoTuiOkhttpSingTop.Methods.PSOT);

        //观察者模式
        PaoTuiAppliction.paoTuiOkhttpSingTop.setCallbackM(new PaoTuiOkhttpSingTop.CallbackM() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Object response) {

                UserBean userBean = (UserBean) response;

                Log.d("NNNNN",userBean.toString());
                Log.d("getUserId----zhuce",userBean.userinfo.getUserId());
                if (userBean.getCode().equals("07000")){
                    setback(LoginActivity.class);
                }else if (userBean.getCode().equals("07010")){
                    Toast.makeText(NewsActivity.this, "注册失败，账号已经存在！", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(NewsActivity.this, "注册失败，请重新注册！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
