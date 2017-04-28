package bwei.com.citysortlist.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import bwei.com.citysortlist.bean.CityBean;

/**
 * 作者：JinCaiXia
 * 邮箱：jincaixia123@aliyun.com
 * 时间：2017/2/9
 * 文档说明：
 */

public class CityDao {
    private Context context;
    private  File file;

    public CityDao(Context context) {
        try {
            InputStream inputStrean = context.getAssets().open("china_city_name");
            file = new File(Environment.getExternalStorageDirectory(),"china_city");
            if(!file.exists()){
                file.mkdirs();
            }
            File file1=new File(file,"china_city_name.db");
            if(!file1.exists()){
                FileOutputStream outputStream = new FileOutputStream(file1);
                // FileOutputStream outputStream = context.openFileOutput("china_city_name.db", Context.MODE_PRIVATE);
                byte[] buffer=new byte[1024];
                int len=0;
                while((len=inputStrean.read(buffer))!=-1){
                    outputStream.write(buffer,0,len);
                    outputStream.flush();
                }
                outputStream.close();
                inputStrean.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<CityBean> query(){
        List<CityBean> list=new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file.getAbsoluteFile()+"/"+"china_city_name.db", null);
        Cursor cursor = db.rawQuery("select * from T_city order by NameSort", null);
        while(cursor.moveToNext()){
            String cityName = cursor.getString(cursor.getColumnIndex("CityName"));
            String nameSort = cursor.getString(cursor.getColumnIndex("NameSort"));
            CityBean cityBean = new CityBean(cityName, nameSort);
            list.add(cityBean);

        }
        cursor.close();
        db.close();
        return  list;
    }
}
