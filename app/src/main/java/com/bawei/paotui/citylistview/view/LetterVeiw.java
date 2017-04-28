package com.bawei.paotui.citylistview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 姓名：李鑫琪
 * 日期：2016/12/27 8:37
 * 描述：
 */
public class LetterVeiw extends View {

    private int height;
    private int width;

    private int lastPosition = -1;
    String[] b = {"#","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private int singleheight;
    private boolean showBg;
    private OnLetterSelectedLintener letterSelectedLintener;

    public LetterVeiw(Context context) {
        super(context,null);
    }

    public LetterVeiw(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public LetterVeiw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = this.getHeight();
        width = this.getWidth();

        singleheight = height/b.length;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint =   new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(18);
        paint.setAntiAlias(true);
//        当手指按下或滑动时，改变自定义控件的背景色
        if (showBg){
            canvas.drawColor(Color.GRAY);
        }
        for (int i=0;i<b.length;i++){
            float measureText = paint.measureText(b[i]);
            canvas.drawText(b[i],width/2-measureText/2,singleheight*(i+1),paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                showBg = true;
                float y = event.getY();
                int position = (int) (y*b.length/height);
//                Toast.makeText(getContext(),"当前字母"+b[position],Toast.LENGTH_SHORT).show();
                if (lastPosition != position){
                    letterSelectedLintener.setSelectLetter(b[position]);
                }
                lastPosition=position;
                break;
            default:
                showBg = false;
                lastPosition = -1;
                break;
        }
        postInvalidate();
        return true;
    }

    public interface OnLetterSelectedLintener{
        public void setSelectLetter(String s);
    }
    public void setOnLetterSelectedLintener(OnLetterSelectedLintener letterSelectedLintener){
        this.letterSelectedLintener = letterSelectedLintener;
    }
}
