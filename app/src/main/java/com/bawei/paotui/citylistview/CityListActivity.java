package com.bawei.paotui.citylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.List;

import com.bawei.paotui.citylistview.bean.City;
import com.bawei.paotui.citylistview.dao.CityDao;
import com.bawei.paotui.citylistview.view.LetterVeiw;
import com.fangz.maot.R;

public class CityListActivity extends AppCompatActivity {
    private LetterVeiw letter_main_view;
    private ListView lv_main_listview;
    private List<City> list;
    private ListAdapter adapter;
    private HashMap<String, Integer> cityMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_city_list);

        letter_main_view = (LetterVeiw) findViewById(R.id.letter_main_view);
        lv_main_listview = (ListView) findViewById(R.id.lv_main_listview);
        letter_main_view.setOnLetterSelectedLintener(new LetterVeiw.OnLetterSelectedLintener() {
            @Override
            public void setSelectLetter(String s) {
                Toast toast = new Toast(CityListActivity.this);
                View view = View.inflate(CityListActivity.this,R.layout.toast,null);
                TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
                tv_toast.setText(s);
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                if (cityMap.get(s) != null) {
                    lv_main_listview.setSelection(cityMap.get(s));
                }

            }
        });

        CityDao cityDao = new CityDao(this);
        list = cityDao.getCityList();
        initMap();
        adapter = new ListAdapter();
        lv_main_listview.setAdapter(adapter);

    }

    public void initMap() {
        cityMap = new HashMap<>();
        String preLetter = "";
        for (int i = 0; i < list.size(); i++) {
            if (i - 1 >= 0) {
                preLetter = list.get(i - 1).getNameSort();
            }
            if (!list.get(i).getNameSort().equals(preLetter)) {
                cityMap.put(list.get(i).getNameSort(), i);
            }
        }
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(CityListActivity.this, R.layout.city_item, null);
            TextView tv_cityname = (TextView) view.findViewById(R.id.tv_cityname);
            TextView tv_namesort = (TextView) view.findViewById(R.id.tv_namesort);
            String preNameSort = "";
            if (i - 1 >= 0) {
                preNameSort = list.get(i - 1).getNameSort();
            }
            if (list.get(i).getNameSort().equals(preNameSort)) {
                tv_namesort.setVisibility(View.GONE);
            } else {
                tv_namesort.setVisibility(View.VISIBLE);
            }

            tv_cityname.setText(list.get(i).getCityName());
            tv_namesort.setText(list.get(i).getNameSort());
            return view;
        }
    }
}
