package com.bawei.paotui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 姓名：李鑫琪
 * 日期：2017/2/17 11:49
 * 描述：
 */
public class CustomImage extends ImageView{

    private Paint paint;

    public CustomImage(Context context) {
        this(context,null);
    }

    public CustomImage(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public CustomImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        BitmapDrawable drawable = (BitmapDrawable) getDrawable();

        if(null!=drawable){
//            Toast.makeText(getContext(),"走了",Toast.LENGTH_LONG).show();
            Bitmap bitmap = drawable.getBitmap();
            Bitmap b = getBackground(bitmap);
            Rect rect2 = new Rect(0,0,b.getWidth(),b.getHeight());
            Rect rect1 = new Rect(0,0,getWidth(),getHeight());
//            Log.d("rect1",getWidth()+"......"+getHeight()+"");
//            Log.d("rect2",b.getWidth()+"......"+b.getHeight()+"");
            paint.reset();
            canvas.drawBitmap(b,rect2,rect1,paint);

        }else {
            super.onDraw(canvas);
//            Toast.makeText(getContext(),"走了......",Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap getBackground(Bitmap bitmap){

        //获取原图大小
        int width = getWidth();
        int height = getHeight();
//        Log.d("yuantu",width+"......"+height+"");
        //以最短的边设置圆的直径
        int r = width>height ?height:width;
        //绘制和原图一样的bitmap
        Bitmap backBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        //在这个bitmap上绘制
        Canvas canvas = new Canvas(backBitmap);
        //创建画笔设置抗锯齿
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形
        RectF rect = new RectF(0,0,r,r);
//        Log.d("R+++",r+"");
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,paint);
        //两图相交取交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap,null,rect,paint);

        return backBitmap;
    }
}
