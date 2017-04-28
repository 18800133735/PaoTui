package com.bawei.paotui.home.jiedan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawei.paotui.important.BaseActivity;
import com.fangz.maot.R;

public class TrueNameActivity extends BaseActivity {

    private EditText true_et_name;
    private EditText true_et_idcard;
    private Button true_bt_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_name);

        initHeader();
        initWidget();
        setWidgetState();
    }

    @Override
    public void initHeader() {
        initHeaderWidget();
        setTitle("实名认证");
    }

    @Override
    public void initWidget() {

        true_et_name = (EditText) findViewById(R.id.true_et_name);
        true_et_idcard = (EditText) findViewById(R.id.true_et_idcard);
        true_bt_ok = (Button) findViewById(R.id.true_bt_ok);
    }

    @Override
    public void setWidgetState() {
        true_bt_ok.setOnClickListener(this);

        SetTitleStatue(LEFT_IMAGE_RIGHT_NO);
        addImageLeftViewlistener(this,R.mipmap.left1);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.true_bt_ok:

                break;
            case R.id.iv_left_view:
                setback(OrderTaskActivity.class);
                break;
        }
    }
}
