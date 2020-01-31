package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

/*
电影胶片式播放动画
 */
public class FeAnimFilm extends View {

    private Context act;

    //循环模式  //模式0: 0-1-2-0-1-2, 模式1: 0-1-2-1-0-1-2
    private int _circle_mode = 0;

    //传参备份
    private int _frameHeight, _frameSkip, _frameNum;
    private int[] _frameInterval, _alignXY_sizeXY;

    //画图
    private Paint paint;

    //原始图片
    private Bitmap bitmap;

    //扣取图片位置
    private Rect bitmapBody, bitmapDist;

    //动画输出位置控制
    private RelativeLayout.LayoutParams layoutParams;

    //帧计数
    private int timerCount = 0, intervalCount = 0, frameCount = 0;
    private boolean intervalCountDir = false, frameCountDir = false;

    //切图心跳
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask()
    {
        @Override
        public void run() {
            synchronized (_frameInterval) {
                if (++timerCount >= _frameInterval[intervalCount]) {
                    //清计数
                    timerCount = 0;
                    //调用一次onDraw
                    invalidate();
                    //循环播放模式0
                    if(_circle_mode == 0){
                        //移动帧计数
                        if(frameCountDir){
                            if(--frameCount <= 0){
                                frameCount = 0;
                                frameCountDir = !frameCountDir;
                            }
                        }else{
                            if(++frameCount >= _frameNum - 1){
                                frameCount = _frameNum - 1;
                                frameCountDir = !frameCountDir;
                            }
                        }
                        //移动延时加权,循环使用_frameInterval数组
                        if(intervalCountDir){
                            if(--intervalCount <= 0){
                                intervalCount = 0;
                                intervalCountDir = !intervalCountDir;
                            }
                        }else{
                            if(++intervalCount >= _frameInterval.length - 1){
                                intervalCount = _frameInterval.length - 1;
                                intervalCountDir = !intervalCountDir;
                            }
                        }
                    }
                    //循环播放模式1
                    else {
                        //移动帧计数
                        if (++frameCount >= _frameNum)
                            frameCount = 0;
                        //移动延时加权,循环使用_frameInterval数组
                        if (++intervalCount >= _frameInterval.length)
                            intervalCount = 0;
                    }
                    //移动框图
                    bitmapBody.left = 0;
                    bitmapBody.top = _frameHeight*(_frameSkip+frameCount);
                    bitmapBody.right = bitmap.getWidth();
                    bitmapBody.bottom = bitmapBody.top + _frameHeight;
                }
            }
        }
    };

    /*
    id: drawable图片,如: R.drawable.xxx
    frameHeight: 按这个高度把图片切块
    frameSkip: 分块后,从上往下数,跳过几帧后作为第一帧,0从头开始
    frameNum: 从确定的第一帧开始,选用后面的frameNum帧图片循环播放
    intervalMs: 帧动画间隔延时
    frameInterval: 对每帧动画的延时进行加权,当frameInterval[x]!=0时,第n帧动画实际延时为frameInterval[n]*intervalMs
     */
    public FeAnimFilm(Context context, int id, int frameWidth, int frameHeight, int frameNum, int frameSkip, int alignX, int alignY, int width, int height, int intervalMs, int[] frameInterval, int circle_mode)
    {
        super(context);
        act = context;
        //图片加载和颜色变换
        bitmap = BitmapFactory.decodeResource(act.getResources(), id);
        bitmap = replaceBitmapColor(bitmap, 1);
        //参数备份
        _frameHeight = (int)((double)bitmap.getWidth()/frameWidth*frameHeight);
        _frameSkip = frameSkip;
        _frameNum = frameNum;
        if(_frameNum == 0)
            _frameNum = bitmap.getHeight()/_frameHeight;
        _frameInterval = frameInterval;
        _circle_mode = circle_mode;
        //输出宽高备份
        _alignXY_sizeXY = new int[]{alignX, alignY, width, height};
        bitmapDist = new Rect(0,0,width,height);
        //自己添加相对布局参数
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.setMargins(alignX,alignY,0,0);
        this.setLayoutParams(layoutParams);
        //图片扣取位置计算
        bitmapBody = new Rect(0,0,0,0);
        bitmapBody.left = 0;
        bitmapBody.top = _frameHeight*(_frameSkip+frameCount);
        bitmapBody.right = bitmap.getWidth();
        bitmapBody.bottom = bitmapBody.top + _frameHeight;
        //画笔初始化
        paint = new Paint();
        paint.setColor(Color.GREEN);
        //时钟心跳启动
        timer.schedule(timerTask, intervalMs, intervalMs);//ms
    }

    public void move(int x, int y){
        _alignXY_sizeXY[0] += x;
        _alignXY_sizeXY[1] += y;
        layoutParams.setMargins(_alignXY_sizeXY[0],_alignXY_sizeXY[1],0,0);
        this.setLayoutParams(layoutParams);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //相对布局位置偏移
        bitmapDist.left = (int)this.getTranslationX();
        bitmapDist.top = (int)this.getTranslationY();
        bitmapDist.right = bitmapDist.left + _alignXY_sizeXY[2];
        bitmapDist.bottom = bitmapDist.top + _alignXY_sizeXY[3];
        //绘图
        canvas.drawRect(bitmapDist, paint);
        canvas.drawBitmap(bitmap, bitmapBody, bitmapDist, paint);
    }

    public Bitmap replaceBitmapColor(Bitmap oldBitmap,int type){
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

