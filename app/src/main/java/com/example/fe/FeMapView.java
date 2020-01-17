package com.example.fe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/10/1.
 */
public class FeMapView extends View {
    //用于getBitMap的句柄
    private Resources res;
    //画图
    private Paint paint;
    //刷屏心跳
    private  int cc = 0;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask()
    {
        @Override
        public void run()
        {
            cc += 5;
            if(cc > 500)
                cc = 0;
            invalidate();//调用一次onDraw
        }
    };

    Bitmap getBitMap(int id){
        return BitmapFactory.decodeResource(res, id);
    }

    public FeMapView(Context context) {
        super(context);
        //
        res = context.getResources();
        //
        paint = new Paint();
        paint.setColor(Color.RED);
        //
        timer.schedule(timerTask, 1000, 100);//ms
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect(cc, 0, 500+cc, 800, paint);
        canvas.drawBitmap(getBitMap(R.drawable.tt), 500-cc, 500, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }

    public static Bitmap replaceBitmapColor(Bitmap oldBitmap,int type){
        if(type == 0)   //使用原色
            return oldBitmap;
        //相关说明可参考 http://xys289187120.blog.51cto.com/3361352/657590/
        Bitmap mBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //循环获得bitmap所有像素点
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        int mArrayColorLengh = mBitmapWidth * mBitmapHeight;
        int r,g,b,color,tcolor;

        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                //获得Bitmap 图片中每一个点的color颜色值
                //将需要填充的颜色值如果不是
                //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
                //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
                color = mBitmap.getPixel(j, i);
                //将颜色值存在一个数组中 方便后面修改
                /*if (color == oldColor) {
                    mBitmap.setPixel(j, i, newColor);  //将白色替换成透明色
                }
                mBitmap.setPixel(j, i, newColor);*/
                tcolor = color&0xff000000;
                r = (color&0x00ff0000)>>16;
                g = (color&0x0000ff00)>>8;
                b = color&0x000000ff;
                if(type == 1 && b > g){         //蓝色 换 绿色
                    tcolor |= (int)(r*0.8) << 16;
                    tcolor |= g << 0;
                    tcolor |= (int)(b*0.8) << 8;
                    mBitmap.setPixel(j, i, tcolor);
                }
                else if(type == 2 && b > r){    //蓝色 换 红色
                    tcolor |= r << 0;
                    tcolor |= (int)(g*0.8) << 8;
                    tcolor |= b << 16;
                    mBitmap.setPixel(j, i, tcolor);
                }
            }
        }
        return mBitmap;
    }

}

