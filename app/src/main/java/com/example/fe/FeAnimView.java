package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class FeAnimView extends View {

    private Context act;

    //画图
    private Paint paint;

    //位置
    private Rect distRect;

    //帧列表
    private int [] framearray;

    //帧计数
    private  int frameCount = 0, frameMax = 0;

    //切图心跳
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask()
    {
        @Override
        public void run()
        {
            frameCount ++;
            if(frameCount >= frameMax)
                frameCount = 0;
            invalidate();//调用一次onDraw
        }
    };

    public FeAnimView(Context context, int[] frameArray, int intervalMs, Rect paintRect)
    {
        super(context);
        act = context;
        framearray = frameArray;
        frameMax = framearray.length;
        //
        distRect = paintRect;
        //
        paint = new Paint();
        //
        timer.schedule(timerTask, intervalMs, intervalMs);//ms
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //加载图片
        Bitmap bitmap = BitmapFactory.decodeResource(act.getResources(), framearray[frameCount]);
        //颜色替换
        ;
        //绘图
        canvas.drawBitmap(bitmap, null, distRect, paint);
    }

}

