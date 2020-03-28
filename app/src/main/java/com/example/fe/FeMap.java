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

import java.io.IOException;
import java.io.InputStream;

/*
    地图视图管理,管理地图和屏幕适配、挪动、方格选中等
 */
public class FeMap extends View {

    private Context _context;
    private FeMapParam mapParam;
//    private FeHeart _animHeart;

    //画图
    private Paint paint, paint2;

    public FeMap(Context context, FeHeart animHeart, FeMapParam feMapParam) {
        super(context);
        _context = context;
        mapParam = feMapParam;
//        _animHeart = animHeart;
        //画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(50);
        paint2 = new Paint();
        paint2.setColor(0x800000FF);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //相对布局位置偏移
        mapParam.mapDist.left = (int)this.getTranslationX() - (int)(mapParam.xGridErr*mapParam.xGridPixel);
        mapParam.mapDist.top = (int)this.getTranslationY() - (int)(mapParam.yGridErr*mapParam.yGridPixel);
        mapParam.mapDist.right = mapParam.mapDist.left + mapParam.width;
        mapParam.mapDist.bottom = mapParam.mapDist.top + mapParam.height;
        //梯形变换
        mapParam.getMatrix();
        //显示地图
        canvas.drawBitmap(mapParam.bitmap, mapParam.matrix, paint);

        //select
        if(mapParam.select) {
            mapParam.select = false;
//            canvas.drawRect(mapParam.selectRect, paint2);
            canvas.drawPath(mapParam.selectPath, paint2);
//            canvas.drawText(
//                    String.valueOf(
//                    mapParam.selectRect.bottom - mapParam.selectRect.top),
//                    50, 50, paint);
        }

        //
        ((FeSectionLayout)getParent().getParent()).refresh();
    }

    //触屏按下时记录坐标
    private float tDownX, tDownY;
    //分辨移动事件还是点击事件
    private boolean isMove = false;

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                ((FeSectionLayout)getParent().getParent()).onTouch(event.getAction(), event.getX(), event.getY(), isMove);
                //
                tDownX = event.getX();
                tDownY = event.getY();
                isMove = false;
            }
            break;
            case MotionEvent.ACTION_UP: {
                ((FeSectionLayout)getParent().getParent()).onTouch(event.getAction(), event.getX(), event.getY(), isMove);
                //
                if(!isMove) {
                    //输入坐标求格子位置
                    mapParam.getRectByLocation(event.getX(), event.getY());
                    //调用一次onDraw
                    mapParam.select = true;
                    invalidate();
                }
                isMove = false;
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
                if (Math.abs(xErr) > mapParam.xGridPixel) {
                    if (xErr > 0) mapParam.xGridErr -= 1;
                    else mapParam.xGridErr += 1;
                    tDownX = tMoveX;
                    needRefresh = true;
                    isMove = true;
                }
                //
                if (Math.abs(yErr) > mapParam.yGridPixel) {
                    if (yErr > 0) mapParam.yGridErr -= 1;
                    else mapParam.yGridErr += 1;
                    tDownY = tMoveY;
                    needRefresh = true;
                    isMove = true;
                }
                //防止地图移出屏幕
                if (mapParam.xGridErr < 0) mapParam.xGridErr = 0;
                else if (mapParam.xGridErr + mapParam.screenXGrid > mapParam.xGrid)
                    mapParam.xGridErr = mapParam.xGrid - mapParam.screenXGrid;
                if (mapParam.yGridErr < 0) mapParam.yGridErr = 0;
                else if (mapParam.yGridErr + mapParam.screenYGrid > mapParam.yGrid)
                    mapParam.yGridErr = mapParam.yGrid - mapParam.screenYGrid;
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

