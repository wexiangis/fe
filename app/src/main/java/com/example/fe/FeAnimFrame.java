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

/*
帧动画
 */
public class FeAnimFrame extends View {

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

    public FeAnimFrame(Context context, int[] frameArray, int intervalMs, Rect paintRect)
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
        //绘图
        canvas.drawBitmap(bitmap, null, distRect, paint);
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

