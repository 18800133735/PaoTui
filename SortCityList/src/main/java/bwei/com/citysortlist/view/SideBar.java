package bwei.com.citysortlist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：JinCaiXia
 * 邮箱：jincaixia123@aliyun.com
 * 时间：2017/2/9
 * 文档说明：
 */

public class SideBar extends View {
    public static String[] b = { "#","A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
    private int width;
    private int height;
    private Paint mPaint;
    private int singleHeight;
    private boolean isClick;
    OnSingleTouchListener onSingleTouchListener;

    public SideBar(Context context) {
        this(context,null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#4E98BE"));
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(25);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mPaint.setTypeface( font );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <b.length ; i++) {
            float textMeasure = mPaint.measureText(b[i]);
            canvas.drawText(b[i],(width-textMeasure)/2,singleHeight*(i+1),mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        singleHeight = height/b.length;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isClick=true;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int position= (int)(y / singleHeight);
                if(null!=onSingleTouchListener){
                    onSingleTouchListener.onSingleTouch(b[position]);
                }
                break;
            default:
                isClick=false;
                break;

        }

        return true;
    }

    public interface  OnSingleTouchListener{
        void onSingleTouch(String s);
    }
    public void setOnSingleTouchListener(OnSingleTouchListener o){
        this.onSingleTouchListener=o;
    }
}
