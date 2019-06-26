package com.example.administrator.my2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2019/4/2.
 * 自定义每个小方格
 */

public class Item2048 extends View {
    private int number = 0;//方格中的数字
    private String numberStr;//用于判断数字的长度，绘制小方格
    private Paint myPaint;
    private Rect myRect;
    public Item2048(Context context) {
        this(context,null);
    }

    public Item2048(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Item2048(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myPaint = new Paint();
    }

    //绘制方格，设置数字，重绘
    public void setNumber(int numberTemp){
        number = numberTemp;
        numberStr = String.valueOf(numberTemp);
        myPaint.setTextSize(50.0f);
        myRect = new Rect();
        myPaint.getTextBounds(numberStr,0,numberStr.length(),myRect);
        invalidate();//重绘
    }

    public int getNumber() {
        return number;
    }

    //重写onDraw()
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        String []itemColor = {"#CCC0B3","#EEE4DA","#EDE0C8","#F2B179",
                "#F49563","#F5794D","#F55D37","#EEE863","#EDB04D","#ECB04D","#EB9437","#EA7821","#EA7821"};
        String myItemColor;
        switch (number){
            case 0:
                myItemColor = itemColor[0];
                break;
            case 2:
                myItemColor = itemColor[1];
                break;
            case 4:
                myItemColor = itemColor[2];
                break;
            case 8:
                myItemColor = itemColor[3];
                break;
            case 16:
                myItemColor = itemColor[4];
                break;
            case 32:
                myItemColor = itemColor[5];
                break;
            case 64:
                myItemColor = itemColor[6];
                break;
            case 128:
                myItemColor = itemColor[7];
                break;
            case 256:
                myItemColor = itemColor[8];
                break;
            case 512:
                myItemColor = itemColor[9];
                break;
            case 1024:
                myItemColor = itemColor[10];
                break;
            case 2048:
                myItemColor = itemColor[11];
                break;
            default:
                myItemColor = itemColor[12];
                break;
        }
        myPaint.setColor(Color.parseColor(myItemColor));
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,getWidth(),getHeight(),myPaint);
        if(number != 0){
            myPaint.setColor(Color.BLACK);
            float x = (getWidth() - myRect.width())/2;
            float y = (2*(getHeight() - myRect.height()))/3;
            canvas.drawText(numberStr,x,y,myPaint);
        }
    }
}
