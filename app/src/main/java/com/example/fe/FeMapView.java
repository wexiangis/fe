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
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FeMapView extends View {

    private Context act;

    //
    private int screenWidth, screenHeight;
    private int[] mapWidthHeight;

    //
    private Bitmap bitmap = null, tBitmap = null;
    private Matrix matrix = null;

    //
    private RelativeLayout.LayoutParams layoutParams = null;

    //扣取图片位置
    private Rect bitmapBody = null, bitmapDist = null;

    //参数备份
    private int _xGrid, _yGrid;

    //画图
    private Paint paint, paint2;

    public FeMapView(Context context, int id, int xGrid, int yGrid) {
        super(context);
        act = context;
        //
        _xGrid = xGrid;
        _yGrid = yGrid;
        //获取屏幕参数
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)act).getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        //
        mapWidthHeight = mapFillRule(screenWidth, screenHeight, xGrid, yGrid);
        xGridPiexl = mapWidthHeight[0]/xGrid;
        yGridPiexl = mapWidthHeight[1]/yGrid;
        //
        bitmapBody = new Rect(0, 0, mapWidthHeight[0], mapWidthHeight[1]);
        bitmapDist = new Rect(0, 0, mapWidthHeight[0], mapWidthHeight[1]);
        //
        tBitmap = BitmapFactory.decodeResource(act.getResources(), id);
        matrix = new Matrix();
        matrix.postScale(
                (float)mapWidthHeight[0]/tBitmap.getWidth(),
                (float)mapWidthHeight[1]/tBitmap.getHeight());//两参数分别为xy缩放比例
        bitmap = Bitmap.createBitmap(tBitmap, 0, 0, (int)tBitmap.getWidth(), (int)tBitmap.getHeight(), matrix, true);
        //自己添加相对布局参数
        if(layoutParams == null)
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.setMargins(0,0,0,0);
        this.setLayoutParams(layoutParams);
        //
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint2 = new Paint();
        paint2.setColor(0x800000FF);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //相对布局位置偏移
        bitmapDist.left = (int)this.getTranslationX() + (int)xError;
        bitmapDist.top = (int)this.getTranslationY() + (int)yError;
        bitmapDist.right = bitmapDist.left + mapWidthHeight[0];
        bitmapDist.bottom = bitmapDist.top + mapWidthHeight[1];
        //
        canvas.drawBitmap(bitmap, null, bitmapDist, paint);
        //select
        if(selectDrawFlag) {
            selectDrawFlag = false;
            canvas.drawRect(selectDraw, paint2);
        }
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
                    selectPoint[0] = (int)((tUpX - xError)/mapWidthHeight[0]*_xGrid);
                    selectPoint[1] = (int)((tUpY - yError)/mapWidthHeight[1]*_yGrid);
                    selectDraw.left = (int)((float)selectPoint[0]/_xGrid*mapWidthHeight[0] + xError);
                    selectDraw.top = (int)((float)selectPoint[1]/_yGrid*mapWidthHeight[1] + yError);
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
                else if (xError + mapWidthHeight[0] < screenWidth)
                    xError = screenWidth - mapWidthHeight[0];
                if (yError > 0) yError = 0;
                else if (yError + mapWidthHeight[1] < screenHeight)
                    yError = screenHeight - mapWidthHeight[1];
                //调用一次onDraw
                if (needRefresh)
                    invalidate();
            }
            break;
        }
        return true;
    }

    //返回地图实际显示长和高
    public int[] mapFillRule(int screenWidth, int screenHeight, int mapXGrid, int mapYGrid){
        int[] ret = new int[] {screenWidth, screenHeight};
        //限制屏幕最大显示格数
        int screenXGrid = 32, screenYGrid = 16;
        //比较屏幕和地图长和高比例
        double screenXDivY = screenWidth/screenHeight;
        double mapXDivY = mapXGrid/mapYGrid;
        //屏幕的长高比例大于地图,地图参照屏幕长来缩放
        if(screenXDivY > mapXDivY){
            //得到屏幕横向实际显示格数
            if(mapXGrid < screenXGrid)
                screenXGrid = mapXGrid;
            //得到屏幕竖向实际显示格数
            screenYGrid = (int)((double)screenXGrid/screenWidth*screenHeight);
        }
        //其他时候,地图参照屏幕高来缩放
        else{
            //得到屏幕竖向实际显示格数
            if(mapYGrid < screenYGrid)
                screenYGrid = mapYGrid;
            //得到屏幕横向实际显示格数
            screenXGrid = (int)((double)screenYGrid/screenHeight*screenWidth);
        }
        //得到地图实际显示大小
        ret[0] = (int)((double)screenWidth/screenXGrid*mapXGrid);
        ret[1] = (int)((double)screenHeight/screenYGrid*mapYGrid);
        //
        return ret;
    }
}

