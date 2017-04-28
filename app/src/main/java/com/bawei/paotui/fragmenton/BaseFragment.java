package com.bawei.paotui.fragmenton;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 姓名：李鑫琪
 * 日期：2017/2/11 12:28
 * 描述：
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public abstract void initHeader();
    public abstract void initWidget();
    public abstract void setWidgetState();

    @Override
    public void onClick(View view) {


    }
}
