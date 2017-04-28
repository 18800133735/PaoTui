package com.bawei.paotui.fragmenton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fangz.maot.R;


/**
 * 姓名：李鑫琪
 * 日期：2017/2/11 12:29
 * 描述：
 */
public class FragmentTwo extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenttwo,null);
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

    }

    @Override
    public void setWidgetState() {

    }
}
