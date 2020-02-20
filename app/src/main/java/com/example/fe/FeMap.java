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
        _mapParam.mapDist.left = (int)this.getTranslationX() + (int)xError;
        _mapParam.mapDist.top = (int)this.getTranslationY() + (int)yError;
        _mapParam.mapDist.right = _mapParam.mapDist.left + _mapParam.width;
        _mapParam.mapDist.bottom = _mapParam.mapDist.top + _mapParam.height;
        //
        float reduceX = _mapParam.xGridPixel*3;
        float reduceY = _mapParam.yGridPixel*3;
        //
//        float srcPoint[] = new float[]{
//                0, 0,
//                0, bitmap.getHeight(),
//                bitmap.getWidth(), bitmap.getHeight(),
//                bitmap.getWidth(), 0};
//        float distPoint[] = new float[]{
//                _mapParam.mapDist.left + reduceX, _mapParam.mapDist.top,
//                _mapParam.mapDist.left, _mapParam.mapDist.bottom,
//                _mapParam.mapDist.right, _mapParam.mapDist.bottom,
//                _mapParam.mapDist.right - reduceX, _mapParam.mapDist.top};
        //
        float xPow = (float)bitmap.getWidth()/_mapParam.width;
        float yPow = (float)bitmap.getHeight()/_mapParam.height;
        //
        float srcPoint[] = new float[]{
                -_mapParam.mapDist.left*xPow, -_mapParam.mapDist.top*yPow,
                -_mapParam.mapDist.left*xPow, -_mapParam.mapDist.top*yPow + _mapParam.screenHeight*yPow,
                -_mapParam.mapDist.left*xPow + _mapParam.screenWidth*xPow, -_mapParam.mapDist.top*yPow + _mapParam.screenHeight*yPow,
                -_mapParam.mapDist.left*xPow + _mapParam.screenWidth*xPow, -_mapParam.mapDist.top*yPow};
        //梯形左右和上边缩进格数
        float reduce = _mapParam.xGridPixel*xPow*3;
        //地图靠近边界时逐渐恢复比例
        if(reduce > bitmap.getWidth() - srcPoint[6])
            reduce = bitmap.getWidth() - srcPoint[6];
        if(reduce > srcPoint[0])
            reduce = srcPoint[0];
        if(reduce > srcPoint[1])
            reduce = srcPoint[1];
        //
        srcPoint[0] -= reduce; srcPoint[1] -= reduce;
        srcPoint[6] += reduce; srcPoint[7] -= reduce;
//        if(srcPoint[0] < 0) srcPoint[0] = 0;
//        if(srcPoint[6] > bitmap.getWidth()) srcPoint[6] = bitmap.getWidth();
//        if(srcPoint[1] < 0) srcPoint[1] = 0;
//        if(srcPoint[7] < 0) srcPoint[7] = 0;
        //
        float distPoint[] = new float[]{
                0, 0,
                0, _mapParam.screenHeight,
                _mapParam.screenWidth, _mapParam.screenHeight,
                _mapParam.screenWidth, 0};
        //
        matrix.setPolyToPoly(srcPoint, 0, distPoint, 0, 4);
        //
        canvas.drawBitmap(bitmap, matrix, paint);
//        canvas.drawBitmap(bitmap, null, _mapParam.mapDist, paint);
        //select
        if(selectDrawFlag) {
            selectDrawFlag = false;
            canvas.drawRect(selectDraw, paint2);
        }
        //
        ((FeSectionLayout)getParent().getParent()).refresh();
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

