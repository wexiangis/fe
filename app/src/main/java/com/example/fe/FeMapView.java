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

public class FeMapView extends View {

    private Context _context;
    private FeMapParam feMapParam;

    //
    private Bitmap bitmap = null, tBitmap = null;
    private Matrix matrix = null;

    //画图
    private Paint paint, paint2;

    public FeMapView(Context context, FeMapParam mapParam, int id, int xGrid, int yGrid) {
        super(context);
        _context = context;
        feMapParam = mapParam;
        //
        feMapParam.xGrid = xGrid;
        feMapParam.yGrid = yGrid;
        //获取屏幕参数
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)_context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        feMapParam.screenWidth = dm.widthPixels;
        feMapParam.screenHeight = dm.heightPixels;
        //地图显示大小与屏幕适配
        feMapParam.mapWidthHeight = mapFillRule(feMapParam.screenWidth, feMapParam.screenHeight, xGrid, yGrid);
        xGridPiexl = feMapParam.mapWidthHeight[0]/xGrid;
        yGridPiexl = feMapParam.mapWidthHeight[1]/yGrid;
        //矩阵缩放
        tBitmap = BitmapFactory.decodeResource(_context.getResources(), id);
        matrix = new Matrix();
        //两参数分别为xy缩放比例
        matrix.postScale(
                (float)feMapParam.mapWidthHeight[0]/tBitmap.getWidth()/2,
                (float)feMapParam.mapWidthHeight[1]/tBitmap.getHeight()/2);
        bitmap = Bitmap.createBitmap(tBitmap, 0, 0, (int)tBitmap.getWidth(), (int)tBitmap.getHeight(), matrix, true);
        //画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint2 = new Paint();
        paint2.setColor(0x800000FF);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //相对布局位置偏移
        feMapParam.rect.left = (int)this.getTranslationX() + (int)xError;
        feMapParam.rect.top = (int)this.getTranslationY() + (int)yError;
        feMapParam.rect.right = feMapParam.rect.left + feMapParam.mapWidthHeight[0];
        feMapParam.rect.bottom = feMapParam.rect.top + feMapParam.mapWidthHeight[1];
        //
        canvas.drawBitmap(bitmap, null, feMapParam.rect, paint);
        //select
        if(selectDrawFlag) {
            selectDrawFlag = false;
            canvas.drawRect(selectDraw, paint2);
        }
        //
        ((FeSectionMap)getParent()).refresh();
    }

    private float tDownX, tDownY, xGridPiexl = 24, yGridPiexl = 24;
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
                    selectPoint[0] = (int)((tUpX - xError)/feMapParam.mapWidthHeight[0]*feMapParam.xGrid);
                    selectPoint[1] = (int)((tUpY - yError)/feMapParam.mapWidthHeight[1]*feMapParam.yGrid);
                    selectDraw.left = (int)((float)selectPoint[0]/feMapParam.xGrid*feMapParam.mapWidthHeight[0] + xError);
                    selectDraw.top = (int)((float)selectPoint[1]/feMapParam.yGrid*feMapParam.mapWidthHeight[1] + yError);
                    selectDraw.right = selectDraw.left + (int)xGridPiexl;
                    selectDraw.bottom = selectDraw.top + (int)yGridPiexl;
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
                if (Math.abs(xErr) > xGridPiexl) {
                    if (xErr > 0) xError += xGridPiexl;
                    else xError -= xGridPiexl;
                    tDownX = tMoveX;
                    needRefresh = true;
                    touchType = 2;
                }
                //
                if (Math.abs(yErr) > yGridPiexl) {
                    if (yErr > 0) yError += yGridPiexl;
                    else yError -= yGridPiexl;
                    tDownY = tMoveY;
                    needRefresh = true;
                    touchType = 2;
                }
                //防止地图移除屏幕
                if (xError > 0) xError = 0;
                else if (xError + feMapParam.mapWidthHeight[0] < feMapParam.screenWidth)
                    xError = feMapParam.screenWidth - feMapParam.mapWidthHeight[0];
                if (yError > 0) yError = 0;
                else if (yError + feMapParam.mapWidthHeight[1] < feMapParam.screenHeight)
                    yError = feMapParam.screenHeight - feMapParam.mapWidthHeight[1];
                //调用一次onDraw
                if (needRefresh)
                    invalidate();
            }
            break;
        }
        return true;
    }

    //方格显示时可以接受的最小像素值
    final int piexlPerGrid = 64;

    //返回地图实际显示长和高
    public int[] mapFillRule(int screenWidth, int screenHeight, int mapXGrid, int mapYGrid){
        int[] ret = new int[] {screenWidth, screenHeight};
        //限制屏幕最大显示格数
        int screenXGrid = screenWidth/piexlPerGrid, screenYGrid = screenHeight/piexlPerGrid;
        //比较屏幕和地图长和高比例
        float screenXDivY = (float)screenWidth/screenHeight;
        float mapXDivY = (float)mapXGrid/mapYGrid;
        //屏幕的长高比例大于地图,地图参照屏幕长来缩放
        if(screenXDivY > mapXDivY){
            //得到屏幕横向实际显示格数
            if(mapXGrid < screenXGrid)
                screenXGrid = mapXGrid;
            //得到屏幕竖向实际显示格数
            screenYGrid = (int)((float)screenXGrid/screenWidth*screenHeight);
        }
        //其他时候,地图参照屏幕高来缩放
        else{
            //得到屏幕竖向实际显示格数
            if(mapYGrid < screenYGrid)
                screenYGrid = mapYGrid;
            //得到屏幕横向实际显示格数
            screenXGrid = (int)((float)screenYGrid/screenHeight*screenWidth);
        }
        //横纵向每格像素
        feMapParam.xGridPixel = (float)screenWidth/screenXGrid;
        feMapParam.yGridPixel = (float)screenHeight/screenYGrid;
        //得到地图实际显示大小
        ret[0] = (int)(feMapParam.xGridPixel*mapXGrid);
        ret[1] = (int)(feMapParam.yGridPixel*mapYGrid);
        //
        return ret;
    }
}

