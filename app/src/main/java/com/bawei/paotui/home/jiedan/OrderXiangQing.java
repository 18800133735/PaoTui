package com.bawei.paotui.home.jiedan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bawei.paotui.important.BaseActivity;
import com.bawei.paotui.important.MainActivity;
import com.fangz.maot.R;

public class OrderXiangQing extends BaseActivity {

    private Button order_details_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);

        initHeader();
        initWidget();
        setWidgetState();
    }

    @Override
    public void initHeader() {

        initHeaderWidget();
        setTitle("订单详情");
    }

    @Override
    public void initWidget() {

        order_details_bt = (Button)findViewById(R.id.order_details_bt);
    }

    @Override
    public void setWidgetState() {
        SetTitleStatue(LEFT_IMAGE_RIGHT_NO);
        addImageLeftViewlistener(this,R.mipmap.left1);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.order_details_bt:


                break;
            case R.id.iv_left_view:
                setback(OrderTaskActivity.class);
                break;
        }
    }
}
