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
    private Matrix matrix = new Matrix();

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
        _mapParam.init(dm.widthPixels, dm.heightPixels, xGrid, yGrid, 106);
        //矩阵缩放
        tBitmap = BitmapFactory.decodeResource(_context.getResources(), id);
        //两参数分别为xy缩放比例
        float xp = (float)_mapParam.width/tBitmap.getWidth()/2;
        if(xp > 1.5f)
            xp = 1.5f;
        float yp = (float)_mapParam.height/tBitmap.getHeight()/2;
        if(yp > 1.5f)
            yp = 1.5f;
        matrix.postScale(xp, yp);
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
        _mapParam.mapDist.left = (int)this.getTranslationX() - (int)(_mapParam.xGridErr*_mapParam.xGridPixel);
        _mapParam.mapDist.top = (int)this.getTranslationY() - (int)(_mapParam.yGridErr*_mapParam.yGridPixel);
        _mapParam.mapDist.right = _mapParam.mapDist.left + _mapParam.width;
        _mapParam.mapDist.bottom = _mapParam.mapDist.top + _mapParam.height;
        //
        _mapParam.getMatrix(matrix, bitmap.getWidth(), bitmap.getHeight());
        //
        canvas.drawBitmap(bitmap, matrix, paint);
//        canvas.drawBitmap(bitmap, null, _mapParam.mapDist, paint);

        //----- 地图梯形变换 -----

        //select
        if(selectDrawFlag) {
            selectDrawFlag = false;
            canvas.drawRect(selectDraw, paint2);
        }
        //
        ((FeSectionLayout)getParent().getParent()).refresh();
    }

    //触屏按下时记录坐标
    private float tDownX, tDownY;
    //分辨移动事件还是点击事件
    private int touchType = 0;

    private boolean selectDrawFlag = false;
    private Rect selectDraw = new Rect(0,0,0,0);

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                ((FeSectionLayout)getParent().getParent()).checkHit(event.getAction(), event.getX(), event.getY());
                //
                tDownX = event.getX();
                tDownY = event.getY();
                touchType = 1;
            }
            break;
            case MotionEvent.ACTION_UP: {
                ((FeSectionLayout)getParent().getParent()).checkHit(event.getAction(), event.getX(), event.getY());
                //
                if(touchType == 1) {
                    //输入坐标求格子位置
                    _mapParam.getRectByLocation(event.getX(), event.getY(), selectDraw);
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
                    if (xErr > 0) _mapParam.xGridErr -= 1;
                    else _mapParam.xGridErr += 1;
                    tDownX = tMoveX;
                    needRefresh = true;
                    touchType = 2;
                }
                //
                if (Math.abs(yErr) > _mapParam.yGridPixel) {
                    if (yErr > 0) _mapParam.yGridErr -= 1;
                    else _mapParam.yGridErr += 1;
                    tDownY = tMoveY;
                    needRefresh = true;
                    touchType = 2;
                }
                //防止地图移出屏幕
                if (_mapParam.xGridErr < 0) _mapParam.xGridErr = 0;
                else if (_mapParam.xGridErr + _mapParam.screenXGrid > _mapParam.xGrid)
                    _mapParam.xGridErr = _mapParam.xGrid - _mapParam.screenXGrid;
                if (_mapParam.yGridErr < 0) _mapParam.yGridErr = 0;
                else if (_mapParam.yGridErr + _mapParam.screenYGrid > _mapParam.yGrid)
                    _mapParam.yGridErr = _mapParam.yGrid - _mapParam.screenYGrid;
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

