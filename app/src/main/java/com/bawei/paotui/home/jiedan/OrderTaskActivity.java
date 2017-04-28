package com.bawei.paotui.home.jiedan;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bawei.paotui.PaoTuiAppliction;
import com.bawei.paotui.home.bean.IssueBeanInfo;
import com.bawei.paotui.home.bean.UserBean;
import com.bawei.paotui.home.jiedan.adapter.WpAdapter;
import com.bawei.paotui.important.BaseActivity;
import com.bawei.paotui.important.MainActivity;
import com.bawei.paotui.utils.Constant;
import com.bawei.paotui.utils.PreferencesUtil;
import com.bawei.paotui.utils.jiami.DESMD5Utils;
import com.bawei.paotui.utils.network.PaoTuiOkhttpSingTop;
import com.fangz.maot.R;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderTaskActivity extends BaseActivity implements OnGetGeoCoderResultListener {

    private EditText order_et_sendname;
    private EditText order_et_jieshuoname;
    private EditText order_et_sendphone;
    private EditText order_et_jieshouphone;
    private EditText order_et_beizhu;
    private TextView order_tv_truename;
    private RelativeLayout order_relative_sort;
    private RelativeLayout order_relative_peisong;
    private TextView order_tv_money;
    private ImageView order_iv_yuyin;
    private Button order_tijiao;
    private ImageView order_iv_sendlixiren1;
    private ImageView order_iv_sendlixiren2;


    // 声明姓名，电话
    private String username, usernumber;
    private String result;
    private String phoneNum;
    private TextView wupin_bt_quexiao;
    private TextView wupin_bt2_ok;
    private GridView wupin_gv;
    private GeoCoder mSearch;
    private DESMD5Utils desmd5Utils;
    private String consignee_longitude = "";
    private String consignee_latitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_task);

        initHeader();
        initWidget();
        setWidgetState();


    }

    @Override
    public void initHeader() {
     // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        initHeaderWidget();
        setTitle("帮我送");

    }

    @Override
    public void initWidget() {
        /*
        * 收发人姓名
        * */
        order_et_sendname = (EditText) findViewById(R.id.order_et_sendname);
        order_et_jieshuoname = (EditText) findViewById(R.id.order_et_jieshuoname);
        /*
        * 收发人电话
        * */
        order_et_sendphone = (EditText) findViewById(R.id.order_et_sendphone);
        order_et_jieshouphone = (EditText) findViewById(R.id.order_et_jieshouphone);
        /*备注*/
        order_et_beizhu = (EditText) findViewById(R.id.order_et_beizhu);
        /*
        * 实名认证*/
        order_tv_truename = (TextView) findViewById(R.id.order_tv_truename);

        /*物品品类*/
        order_relative_sort = (RelativeLayout) findViewById(R.id.order_relative_sort);
        /*配送费按钮及费用*/
        order_relative_peisong = (RelativeLayout) findViewById(R.id.order_relative_peisong);
        order_tv_money = (TextView) findViewById(R.id.order_tv_money);
        /*语音*/
        order_iv_yuyin = (ImageView) findViewById(R.id.order_iv_yuyin);
        /*提交按钮*/
        order_tijiao = (Button) findViewById(R.id.order_tijiao);
        /*联系人按钮*/
        order_iv_sendlixiren1 = (ImageView) findViewById(R.id.order_iv_sendlixiren1);
        order_iv_sendlixiren2 = (ImageView) findViewById(R.id.order_iv_sendlixiren2);

    }

    @Override
    public void setWidgetState() {
        /*通讯录点击事件*/
        order_iv_sendlixiren1.setOnClickListener(this);
        order_iv_sendlixiren2.setOnClickListener(this);
        /*实名认证*/
        order_tv_truename.setOnClickListener(this);
        /*物品品类*/
        order_relative_sort.setOnClickListener(this);
        /*语音*/
        order_iv_yuyin.setOnClickListener(this);
        /*配送*/
        order_relative_peisong.setOnClickListener(this);
        /*提交*/
        order_tijiao.setOnClickListener(this);


        SetTitleStatue(LEFT_IMAGE_RIGHT_NO);
        addImageLeftViewlistener(this,R.mipmap.left1);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.order_iv_sendlixiren1:
                tong();
                break;
            case R.id.order_iv_sendlixiren2:
                tong2();
                break;
            case R.id.order_tv_truename:
                setback(TrueNameActivity.class);
                break;
            case R.id.order_relative_sort:
                setPopupWindow();
                break;
            case R.id.order_iv_yuyin:

                break;
            case R.id.order_relative_peisong:
                setPopupWindowJieGe();
                break;
            case R.id.order_tijiao:
                OkHttp();
                break;
            case R.id.iv_left_view:
                setback(MainActivity.class);
                break;
        }
    }
    /*
    调用手机通讯录
    */
    private void tong() {

        startActivityForResult(new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI), 1);
    }
    private void tong2() {

        startActivityForResult(new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI), 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case (1) :
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);

                    // 获得DATA表中的名字

                    c.moveToFirst();
                    username = c.getString(c
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    phoneNum = this.getContactPhone(c);

                    order_et_sendname.setText(username);
                    order_et_sendphone.setText(phoneNum);

                }
                break;

            }
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);

                    // 获得DATA表中的名字

                    c.moveToFirst();
                    username = c.getString(c
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    phoneNum = this.getContactPhone(c);

                    order_et_jieshuoname.setText(username);
                    order_et_jieshouphone.setText(phoneNum);

                }
                break;

            }
        }

    }

    //获取联系人电话
    private String getContactPhone(Cursor cursor)
    {

        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult="";
        //System.out.print(phoneNum);
        if (phoneNum > 0)
        {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = " + contactId,
                    null, null);
            //int phoneCount = phones.getCount();
            //allPhoneNum = new ArrayList<String>(phoneCount);
            if (phones.moveToFirst())
            {
                // 遍历所有的电话号码
                for (;!phones.isAfterLast();phones.moveToNext())
                {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
//                    order_et_sendname.setText(username);
                    String phoneNumber = phones.getString(index);
                    switch(phone_type)
                    {
                        case 2:
                            phoneResult=phoneNumber;
                            break;
                    }
                    //allPhoneNum.add(phoneNumber);
                }
                if (!phones.isClosed())
                {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }

    public void setPopupWindow(){
        String[] arr = {"休闲食品","生鲜果蔬","办公/居家","其他","鲜花","蛋糕","大件物品"};

        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
            Log.d("jjjjj",arr[i]);
        }

        View popupWindowView = getLayoutInflater().inflate(R.layout.wupin_popupwindow, null);

        final PopupWindow popupWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_order_task, null), Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        wupin_bt_quexiao = (TextView) popupWindowView.findViewById(R.id.wupin_bt_quexiao);
        wupin_bt2_ok = (TextView) popupWindowView.findViewById(R.id.wupin_bt2_ok);
        wupin_gv = (GridView) popupWindowView.findViewById(R.id.wupin_gv);

        WpAdapter adapter = new WpAdapter(this);
        adapter.addrest(list);
        wupin_gv.setAdapter(adapter);

        wupin_bt_quexiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        wupin_bt2_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        wupin_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                final TextView wp_item_bt = (TextView) view.findViewById(R.id.wp_item_bt);
                wp_item_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


            }
        });

    }
    public void setPopupWindowJieGe() {

        View popupWindowView = getLayoutInflater().inflate(R.layout.popuwindow_jiage, null);

        final PopupWindow popupWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_order_task, null), Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        TextView jiage_quxiao = (TextView) popupWindowView.findViewById(R.id.jiage_quxiao);
        TextView jiage_queding = (TextView) popupWindowView.findViewById(R.id.jiage_queding);
        final TextView txt_price = (TextView) popupWindowView.findViewById(R.id.txt_price);
        SeekBar sb_normal = (SeekBar) popupWindowView.findViewById(R.id.sb_normal);
        sb_normal.setMax(50);
        jiage_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        jiage_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order_tv_money.setText(txt_price.getText().toString().trim());
                popupWindow.dismiss();
            }
        });
        sb_normal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txt_price.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public void OkHttp(){

        Map<String,String> map = new HashMap<>();

        String content="";
        String startTime="";
        String endTime="";
        String startAddress="";
        String money="";
        String type="";
        String state="";
        String goodstype="";
        String district="";
        String name="";
        String consigneeID="";
        String consignee_phone="";
        String consignee_address="";

        desmd5Utils = new DESMD5Utils();
        try {
            content = desmd5Utils.encrypt(URLEncoder.encode("帮忙带一箱苹果","utf-8"));
            startTime= desmd5Utils.encrypt("2017020313:00");
            endTime= desmd5Utils.encrypt("2017020315:00");
            startAddress= desmd5Utils.encrypt(URLEncoder.encode("北京市海淀区","utf-8"));
            money= desmd5Utils.encrypt("39元");
            type= desmd5Utils.encrypt("5");
            state= desmd5Utils.encrypt("4");
            goodstype= desmd5Utils.encrypt(URLEncoder.encode("鲜花","utf-8"));
            district= desmd5Utils.encrypt(URLEncoder.encode("beijingshihaidianqu","utf-8"));
            name= desmd5Utils.encrypt(URLEncoder.encode("李鑫琪","utf-8"));
            mSearch.geocode(new GeoCodeOption().city("北京市").address("北京市海淀区"));
            consignee_phone= desmd5Utils.encrypt("15890989313");
            consignee_address= desmd5Utils.encrypt(URLEncoder.encode("beijingshihaidianqu","utf-8"));
            consigneeID = desmd5Utils.encrypt("123456");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * user_token，assignment_content，start_time，end_time，start_address，
         * assignment_money，assignment_distance，assignment_type，assignment_state，
         * goods_type，consigneeID，consignee_name，
         * consignee_phone，consignee_address，consignee_longitude，consignee_latitude
         *
         * 没有的
         * assignment_distance，consigneeID
         */
        String token = PreferencesUtil.get(this,"token","");
        map.put("user_token", token);
        map.put("assignment_content", content);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("start_address", startAddress);
        map.put("assignment_money", money);
        map.put("assignment_distance", "5");
        map.put("assignment_type",type);
        map.put("assignment_state",state);
        map.put("goods_type",goodstype);
        map.put("consigneeID",consigneeID);
        map.put("consignee_name",name);
        map.put("consignee_phone",consignee_phone);
        map.put("consignee_address",consignee_address);
        map.put("assignment_district",district);
        map.put("consignee_longitude",consignee_longitude);
        map.put("consignee_latitude",consignee_latitude);



        String url = Constant.PaoTui_SERVICE+Constant.ServiceConstant.ORDER_TASK;

        PaoTuiAppliction.paoTuiOkhttpSingTop.setUrl(url,map, IssueBeanInfo.class, PaoTuiOkhttpSingTop.Methods.PSOT);

        //观察者模式
        PaoTuiAppliction.paoTuiOkhttpSingTop.setCallbackM(new PaoTuiOkhttpSingTop.CallbackM() {

            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Object response) {

                IssueBeanInfo response1 = (IssueBeanInfo) response;
                Log.d("issueBeanInfo======",response1.toString());
            }
        });

    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        consignee_longitude = desmd5Utils.encrypt(reverseGeoCodeResult.getLocation().longitude+"");
        consignee_latitude = desmd5Utils.encrypt(reverseGeoCodeResult.getLocation().latitude+"");

    }
}
