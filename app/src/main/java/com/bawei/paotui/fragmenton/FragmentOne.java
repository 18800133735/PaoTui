package com.bawei.paotui.fragmenton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.paotui.baidu.PoiSearchDemo;
import com.bawei.paotui.home.homepage.BuyActivity;
import com.bawei.paotui.home.datedialog.DateTimePickerDialog;
import com.bawei.paotui.home.jiedan.OrderTaskActivity;
import com.fangz.maot.R;

import java.text.SimpleDateFormat;

/**
 * 姓名：李鑫琪
 * 日期：2017/2/11 12:29
 * 描述：
 */
public class FragmentOne extends BaseFragment{

    private Button btu_fone_send;
    private Button btu_fone_buy;
    private Button btu_fone_help;
    private Button btu_fone_task;
    private TextView tv_fone_start,fgone_start_adress;
    private TextView tv_fone_end,fgone_end_adress;
    private TextView tv_fone_quike,fgone_quike_adress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentone,null);
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
        btu_fone_send = (Button) getView().findViewById(R.id.btu_fone_send);
        btu_fone_buy = (Button) getView().findViewById(R.id.btu_fone_buy);
        btu_fone_help = (Button) getView().findViewById(R.id.btu_fone_help);
        btu_fone_task = (Button) getView().findViewById(R.id.btu_fone_task);
        tv_fone_start = (TextView) getView().findViewById(R.id.tv_fone_start);
        tv_fone_end = (TextView) getView().findViewById(R.id.tv_fone_end);
        tv_fone_quike = (TextView) getView().findViewById(R.id.tv_fone_quike);
        fgone_start_adress = (TextView) getView().findViewById(R.id.fgone_start_adress);
        fgone_end_adress = (TextView) getView().findViewById(R.id.fgone_end_adress);
        fgone_quike_adress = (TextView) getView().findViewById(R.id.fgone_quike_adress);



    }

    @Override
    public void setWidgetState() {
        btu_fone_send.setOnClickListener(this);
        btu_fone_buy.setOnClickListener(this);
        btu_fone_help.setOnClickListener(this);
        btu_fone_task.setOnClickListener(this);
        tv_fone_start.setOnClickListener(this);
        tv_fone_end.setOnClickListener(this);
        tv_fone_quike.setOnClickListener(this);

        setStatue(R.id.btu_fone_send);
    }
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btu_fone_send:
                setStatue(R.id.btu_fone_send);

                break;
            case R.id.btu_fone_buy:
                setStatue(R.id.btu_fone_buy);
                join1(getActivity(),BuyActivity.class);
                break;
            case R.id.btu_fone_help:
                setStatue(R.id.btu_fone_help);
//                join1(getActivity(),HelpActivity.class);
                join1(getActivity(),PoiSearchDemo.class);
                break;
            case R.id.tv_fone_start:

                Intent start_intent = new Intent(getActivity(),PoiSearchDemo.class);
                start_intent.putExtra("title",1);
                startActivityForResult(start_intent,1);

                break;
            case R.id.tv_fone_end:
                Intent end_intent = new Intent(getActivity(),PoiSearchDemo.class);
                end_intent.putExtra("title",2);
                startActivityForResult(end_intent,2);
                break;
            case R.id.tv_fone_quike:

                showDialog();
                break;
            case R.id.btu_fone_task:

                Intent intent = new Intent(getActivity(), OrderTaskActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1&&resultCode==2){
//            返回开始地址
            String adress = data.getStringExtra("adress");
            fgone_start_adress.setText(adress);

        }else if (requestCode==2&&resultCode==2){
//            返回结束地址
            String adress = data.getStringExtra("adress");
            fgone_end_adress.setText(adress);
        }
    }

    private <T> void join1(Context context, Class<T> t) {
        Intent intent = new Intent(context, t);
        startActivity(intent);
    }

    public void setStatue(int id){
        switch(id){
            case R.id.btu_fone_send:
                btu_fone_send.setSelected(true);

                break;
            case R.id.btu_fone_buy:
                break;
            case R.id.btu_fone_help:
                break;
        }
    }

    public void showDialog()
    {
        DateTimePickerDialog dialog  = new DateTimePickerDialog(getActivity(), System.currentTimeMillis());
        dialog.setOnDateTimeSetListener(new DateTimePickerDialog.OnDateTimeSetListener()
        {
            @Override
            public void OnDateTimeSet(android.app.AlertDialog dialog, long date) {

                Toast.makeText(getActivity(), "日期是："+getStringDate(date), Toast.LENGTH_SHORT).show();
                fgone_quike_adress.setText(getStringDate(date));

            }

        });
        dialog.show();
    }
    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     */
    public static String getStringDate(Long date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);

        return dateString;
    }

}
