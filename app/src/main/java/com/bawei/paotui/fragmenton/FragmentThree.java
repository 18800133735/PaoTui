package com.bawei.paotui.fragmenton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fangz.maot.R;


/**
 * 姓名：李鑫琪
 * 日期：2017/2/11 12:29
 * 描述：
 */
public class FragmentThree extends BaseFragment{
    Button order_bt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentthree,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHeader();
        initWidget();
        setWidgetState();
    }
    @Override
    public void initHeader() {

    }

    @Override
    public void initWidget() {
      order_bt = (Button) getView().findViewById(R.id.order_bt);
    }

    @Override
    public void setWidgetState() {
        order_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.order_bt:


                break;
        }
    }
}
