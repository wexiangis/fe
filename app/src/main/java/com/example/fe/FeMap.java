package com.example.fe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/*
    地图视图管理,管理地图和屏幕适配、挪动、方格选中等
 */
public class FeMap extends View {

    private Context _context;
    private FeMapParam _mapParam;
    private FeHeart _animHeart;

    //
    private Bitmap bitmap = null, tBitmap = null;
    private Matrix matrix = null;

    //画图
    private Paint paint, paint2;

    public FeMap(Context context, FeHeart animHeart, FeMapParam mapParam, int id, int xGrid, int yGrid) {
        super(context);
        _context = context;
        _mapParam = mapParam;
        _animHeart = animHeart;
        //获取屏幕参数
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)_context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        //初始化map参数结构体
        _mapParam.init(dm.widthPixels, dm.heightPixels, xGrid, yGrid, 72);
        //矩阵缩放
        tBitmap = BitmapFactory.decodeResource(_context.getResources(), id);
        matrix = new Matrix();
        //两参数分别为xy缩放比例
        matrix.postScale(
                (float)_mapParam.width/tBitmap.getWidth()/2,
                (float)_mapParam.height/tBitmap.getHeight()/2);
        bitmap = Bitmap.createBitmap(tBitmap, 0, 0, (int)tBitmap.getWidth(), (int)tBitmap.getHeight(), matrix, true);
        //释放
        tBitmap.recycle();
        tBitmap = null;
        //画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint2 = new Paint();
        paint2.setColor(0x800000FF);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //相对布局位置偏移
        _mapParam.mapDist.left = (int)this.getTranslationX() + (int)xError;
        _mapParam.mapDist.top = (int)this.getTranslationY() + (int)yError;
        _mapParam.mapDist.right = _mapParam.mapDist.left + _mapParam.width;
        _mapParam.mapDist.bottom = _mapParam.mapDist.top + _mapParam.height;
        //
        canvas.drawBitmap(bitmap, null, _mapParam.mapDist, paint);
        //select
        if(selectDrawFlag) {
            selectDrawFlag = false;
            canvas.drawRect(selectDraw, paint2);
        }
        //
        ((FeSectionMap)getParent().getParent()).refresh();
    }

    private float tDownX, tDownY;
    private float xError = 0, yError = 0;
    private int touchType = 0;//分辨移动事件还是点击事件

    private boolean selectDrawFlag = false;
    private Rect selectDraw = new Rect(0,0,0,0);
    private int[] selectPoint = new int[]{0,0};

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                tDownX = event.getX();
                tDownY = event.getY();
                touchType = 1;
            }
            break;
            case MotionEvent.ACTION_UP: {
                if(touchType == 1) {
                    float tUpX = event.getX();
                    float tUpY = event.getY();
                    //
                    selectPoint[0] = (int)((tUpX - xError)/_mapParam.width*_mapParam.xGrid);
                    selectPoint[1] = (int)((tUpY - yError)/_mapParam.height*_mapParam.yGrid);
                    selectDraw.left = (int)((float)selectPoint[0]/_mapParam.xGrid*_mapParam.width + xError);
                    selectDraw.top = (int)((float)selectPoint[1]/_mapParam.yGrid*_mapParam.height + yError);
                    selectDraw.right = selectDraw.left + (int)_mapParam.xGridPixel;
                    selectDraw.bottom = selectDraw.top + (int)_mapParam.yGridPixel;
                    //调用一次onDraw
                    selectDrawFlag = true;
                    invalidate();
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                boolean needRefresh = false;
                //
                float tMoveX = event.getX();
                float tMoveY = event.getY();
                //
                float xErr = tMoveX - tDownX;
                float yErr = tMoveY - tDownY;
                //
                if (Math.abs(xErr) > _mapParam.xGridPixel) {
                    if (xErr > 0) xError += _mapParam.xGridPixel;
                    else xError -= _mapParam.xGridPixel;
                    tDownX = tMoveX;
                    needRefresh = true;
                    touchType = 2;
                }
                //
                if (Math.abs(yErr) > _mapParam.yGridPixel) {
                    if (yErr > 0) yError += _mapParam.yGridPixel;
                    else yError -= _mapParam.yGridPixel;
                    tDownY = tMoveY;
                    needRefresh = true;
                    touchType = 2;
                }
                //防止地图移出屏幕
                if (xError > 0) xError = 0;
                else if (xError + _mapParam.width < _mapParam.screenWidth)
                    xError = _mapParam.screenWidth - _mapParam.width;
                if (yError > 0) yError = 0;
                else if (yError + _mapParam.height < _mapParam.screenHeight)
                    yError = _mapParam.screenHeight - _mapParam.height;
                //调用一次onDraw
                if (needRefresh)
                    invalidate();
            }
            break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}

