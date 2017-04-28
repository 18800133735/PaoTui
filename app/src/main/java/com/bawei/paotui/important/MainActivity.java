package com.bawei.paotui.important;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bawei.paotui.home.homepage.LoginActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bawei.paotui.fragmenton.FragmentOne;
import com.bawei.paotui.fragmenton.FragmentThree;
import com.bawei.paotui.fragmenton.FragmentTwo;
import com.bawei.paotui.fragmenton.ShowFragment;
import com.bawei.paotui.utils.Constant;
import com.bawei.paotui.utils.DoubleClickExitHelper;
import com.bawei.paotui.utils.PreferencesUtil;
import com.bawei.paotui.utils.network.UpLoadImage;
import com.fangz.maot.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import bwei.com.citysortlist.MinActivity;

public class MainActivity extends BaseActivity {


    Button iv_main_home,iv_main_renwu,btu_main_jiedan;
    List<ShowFragment> fragmentlist = new ArrayList<>();
    //获得管理者
    FragmentManager manager;
    //开启事务
    FragmentTransaction transaction;
    //声明Fragment
    FragmentOne fragmentOne;
    FragmentTwo fragmentTwo;
    FragmentThree fragmentThree;
    //    声明退出app的工具类
    DoubleClickExitHelper exitHelper;


    private Button btn_picture, btn_photo, btn_cancle;
    private ImageButton ivHead;
    private Bitmap head;// 头像Bitmap
    @SuppressLint("SdCardPath")
    private static String path = "/sdcard/myHead/";// sd路径
    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        setMenu();
        initHeader();
        initWidget();
        setWidgetState();

    }

    private void setMenu() {
        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        menu.setBehindOffset(120);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.menu);

/**
 * 拍照设置圆头像
 */
        setHead();
    }

    private void setHead() {
        ivHead = (ImageButton) findViewById(R.id.iv_head);
        ivHead.setOnClickListener(this);

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(toRoundBitmap(bt));// 转换成drawable
            ivHead.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        btn_picture = (Button) window.findViewById(R.id.btn_picture);
        btn_photo = (Button) window.findViewById(R.id.btn_photo);
        btn_cancle = (Button) window.findViewById(R.id.btn_cancle);

        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片

                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片

                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {

                        setPicToView(head);// 保存在SD卡中
                        ivHead.setImageBitmap(toRoundBitmap(head));// 用ImageView显示出来

                        /**
                         * 上传服务器代码
                         */
//                    将bitmap转换成uri
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage
                                (this.getContentResolver(), head, null, null));
//                     将uri转换成file
                        final File file = uriToFile(uri);
//                      获取登录后从sharepreferences中token
                        String token = PreferencesUtil.get(getApplicationContext(), "token", "");
                        Log.d("token---------",token);
                        String fileName = file.getName();
                        String fileName2 = file.getPath();

                        Log.d("ImageView---------",fileName);
                        Log.d("ImageView+++++++++++++",fileName2);

                        String uri2 = Constant.PaoTui_SERVICE+Constant.ServiceConstant.CHUAN_IMAGE+"?fileName="+fileName+"&token="+token;
                        Log.d("uri2+++++++++++++",uri2);
                        UpLoadImage.upFile(fileName2,fileName,uri2);

//                        new AsyncTask<String, String, String>(){
//                            @Override
//                            protected String doInBackground(String... strings) {
//                                //                      上传服务器
//                                String s = UpLoadImageUtils.uploadFile(file, uri2);
//                                return s;
//                            }
//
//                            @Override
//                            protected void onPostExecute(String s) {
//                                super.onPostExecute(s);
//
//                                Log.d("RESULT",s+"---");
//                            }
//                        }.execute();
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    };

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * 把bitmap转成圆形
     * */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        // 取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        // 构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        // 设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        // 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        // 且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        // 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    @Override
    public void initHeader() {
        initHeaderWidget();
        setTitle("帮我送");

    }

    @Override
    public void initWidget() {
        iv_main_home = (Button) findViewById(R.id.iv_main_home);
        iv_main_renwu = (Button) findViewById(R.id.iv_main_renwu);
        btu_main_jiedan = (Button) findViewById(R.id.btu_main_jiedan);
        //获得管理者
        manager = getSupportFragmentManager();
//        实例化fragment
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();

        addlist();
        addFragment(0);

    }

    public void addlist() {
        for (int i = 0; i < 3; i++) {
            ShowFragment fragment = new ShowFragment();

            switch (i) {
                case 0:
                    fragment.fragment = fragmentOne;
                    break;
                case 1:
                    fragment.fragment = fragmentTwo;
                    break;
                case 2:
                    fragment.fragment = fragmentThree;
                    break;
            }
            fragmentlist.add(fragment);
        }
    }

    //    将Fragment加载进来
    public void addFragment(int position) {
        transaction = manager.beginTransaction();
        //将fragment 加载进来
        for (int i = 0; i < fragmentlist.size(); i++) {

            if (i != position) {
                transaction.hide(fragmentlist.get(i).fragment);
            }
        }

        if (fragmentlist.get(position).statue == 0) {
            transaction.add(R.id.fram_main_content, fragmentlist.get(position).fragment, position + "");
            fragmentlist.get(position).statue = 1;
            transaction.show(fragmentlist.get(position).fragment);
        } else {
            transaction.show(fragmentlist.get(position).fragment);
        }
        transaction.commit();
    }

    @Override
    public void setWidgetState() {
        //底部控件设置监听
        iv_main_home.setOnClickListener(this);
        iv_main_renwu.setOnClickListener(this);
        btu_main_jiedan.setOnClickListener(this);
//      城市列表点击事件
        addImageLeftViewlistener(this);
//        title有图标的点击事件
        addImageRightViewlistener(this);
        //设置默认点击
        Setstate(R.id.iv_main_home);
//        //设置默认点击的图片
//        SetTitleStatue(LEFT_TEXT_RIGHT_TEXT);
//        addLeftViewlistener(this,"城市");
//        addBtuRightViewlistener(this,"登录");

/**
 * 判断登录状态之后在判断是否已经登录过了
 */
        if (PreferencesUtil.get(getApplicationContext(),"first",false)){
            //设置默认点击的图片
            SetTitleStatue(LEFT_NO_RIGHT_NO);
            SetTitleStatue(LEFT_TEXT_RIGHT_IMAGE);
            addLeftViewlistener(this,"城市");
            addImageRightViewlistener(this,R.mipmap.sort3);
        }else{
            //设置默认点击的图片
            SetTitleStatue(LEFT_TEXT_RIGHT_TEXT);
            addLeftViewlistener(this,"城市");
            addBtuRightViewlistener(this,"登录");
        }

//        按两次退出程序
        exitHelper = new DoubleClickExitHelper(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /**
         * 判断登录之后状态
         */
        if (PreferencesUtil.get(getApplicationContext(),"flag",false)){
            //设置默认点击的图片
            SetTitleStatue(LEFT_NO_RIGHT_NO);
            SetTitleStatue(LEFT_TEXT_RIGHT_IMAGE);
            addLeftViewlistener(this,"城市");
            addImageRightViewlistener(this,R.mipmap.sort3);
//            记录已经登录过了
            PreferencesUtil.put(getApplicationContext(),"first",true);
        }else{
            //设置默认点击的图片
            SetTitleStatue(LEFT_TEXT_RIGHT_TEXT);
            addLeftViewlistener(this,"城市");
            addBtuRightViewlistener(this,"登录");
        }
    }

    //    重写onKeyDown:
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return exitHelper.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_main_home:
                Setstate(R.id.iv_main_home);
//                SetTitleStatue(LEFT_TEXT_RIGHT_IMAGE);
                SetTitleStatue(LEFT_TEXT_RIGHT_TEXT);
                addLeftViewlistener(this,"城市");
                addBtuRightViewlistener(this,"登录");
                addFragment(0);
                break;
            case R.id.iv_main_renwu:
                Setstate(R.id.iv_main_renwu);
                SetTitleStatue(LEFT_NO_RIGHT_NO);
                addFragment(1);
                break;
            case R.id.btu_main_jiedan:
                Setstate(R.id.btu_main_jiedan);
                SetTitleStatue(LEFT_TEXT_RIGHT_IMAGE);

                addImageRightViewlistener(this,R.drawable.main_news_selector);
                addFragment(2);
                break;
            case R.id.id_tv_back:
                setback(MinActivity.class);
                break;
            case R.id.tv_main_title_setting:
                setback(LoginActivity.class);
                break;
            case R.id.iv_right_view:
//                打开侧滑菜单
                menu.toggle();
                break;
            case R.id.iv_head:
//                设置操作打开相机或选择相册的dialog
                showDialog();
                break;
        }
    }

    public void Setstate(int id) {
        switch (id) {
            case R.id.iv_main_home:
                iv_main_home.setSelected(true);
                iv_main_renwu.setSelected(false);
                btu_main_jiedan.setSelected(false);

                //设置标题
                setTitle(getString(R.string.main_home));
                break;
            case R.id.iv_main_renwu:
                iv_main_renwu.setSelected(true);
                iv_main_home.setSelected(false);
                btu_main_jiedan.setSelected(false);

                //设置标题
                setTitle(getString(R.string.main_renwu));
                break;
            case R.id.btu_main_jiedan:
                btu_main_jiedan.setSelected(true);
                iv_main_renwu.setSelected(false);
                iv_main_home.setSelected(false);

                //设置标题
                setTitle(getString(R.string.main_jiedan));
                break;
        }
    }
    /**
     * 将Uri转换为File
     *
     * @param uri
     * @return
     */
    private File uriToFile( Uri uri) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = this.managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }


}
