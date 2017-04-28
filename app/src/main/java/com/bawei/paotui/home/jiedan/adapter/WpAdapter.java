package com.bawei.paotui.home.jiedan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.fangz.maot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 姓名：李鑫琪
 * 日期：2017/2/27 15:35
 * 描述：jiiii
 */
public class WpAdapter extends BaseAdapter {

    private Context context;
    List<String> list = new ArrayList<>();
    public WpAdapter(Context context) {
        this.context = context;
    }

    public void addrest(List<String> list1){
        list.addAll(list1);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = View.inflate(context, R.layout.wupin_adapter_item,null);
        }

        final TextView wp_item_bt = (TextView) view.findViewById(R.id.wp_item_bt);
        wp_item_bt.setText(list.get(i));

        wp_item_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int j = 0; j < list.size() ; j++) {
                    if (j!=i){
                        wp_item_bt.setSelected(false);
                    }else{
                        wp_item_bt.setSelected(true);
                    }
                }
            }
        });
        return view;
    }
}
