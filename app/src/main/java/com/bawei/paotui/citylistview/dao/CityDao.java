package com.bawei.paotui.citylistview.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.bawei.paotui.citylistview.bean.City;

/**
 * 姓名：李鑫琪
 * 日期：2016/12/27 10:15
 * 描述：
 */
public class CityDao {
    private Context context;

    List<City> cityList = new ArrayList<>();
    public CityDao(Context context) {
        this.context = context;
        copyDb();
    }

    private void copyDb() {
        try {
            InputStream inputStream = context.getAssets().open("china_city_name");
            FileOutputStream outputStream = context.openFileOutput("china_city_name.db", Context.MODE_PRIVATE);

            byte[] arr = new byte[1024];
            int len = 0;
            while ((len=inputStream.read(arr))!=-1){
                outputStream.write(arr,0,len);
            }
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<City> getCityList(){

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir() + "/china_city_name.db", null);

        Cursor cursor = db.rawQuery("select * from T_city order by NameSort", null);
        cityList.clear();
        while (cursor.moveToNext()){
            String cityName = cursor.getString(cursor.getColumnIndex("CityName"));
            String nameSort = cursor.getString(cursor.getColumnIndex("NameSort"));

            cityList.add(new City(cityName,nameSort));
        }
        return cityList;
    }

}
