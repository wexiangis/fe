package com.example.fe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/*
    地图绘制和全局触屏回调管理
 */
public class FeMapView extends View {

    private Context _context;
    private FeMapParam mapParam;
//    private FeHeart _animHeart;

    //画图
    private Paint paintMap;

    public FeMapView(Context context, FeHeart animHeart, FeMapParam feMapParam) {
        super(context);
        _context = context;
        mapParam = feMapParam;
//        _animHeart = animHeart;
        //画笔
        paintMap = new Paint();
        paintMap.setColor(Color.BLUE);
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
        canvas.drawBitmap(mapParam.bitmap, mapParam.matrix, paintMap);
        //地图移动了,刷新其他信息
        ((FeSectionLayout)getParent().getParent()).refresh();
    }

    //触屏按下时记录坐标
    private float tDownX, tDownY;
    //分辨移动事件还是点击事件
    private boolean isMove = false;

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                ((FeSectionLayout)getParent().getParent()).onTouch(MotionEvent.ACTION_DOWN, event.getX(), event.getY());
                //
                tDownX = event.getX();
                tDownY = event.getY();
                isMove = false;
                //
                mapParam.cleanSelectType(FeMapParam.SELECT_MOVE);
            }
            break;
            case MotionEvent.ACTION_UP: {
                ((FeSectionLayout)getParent().getParent()).onTouch(event.getAction(), event.getX(), event.getY());
                //
                if(!isMove) {
                    //选中方格标志
                    mapParam.setSelectType(FeMapParam.SELECT_MAP);
                    //输入坐标求格子位置
                    mapParam.getRectByLocation(event.getX(), event.getY());
                }else
                    mapParam.cleanSelectType(FeMapParam.SELECT_MAP);
                //
                isMove = false;
                //调用一次onDraw
                invalidate();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                boolean needRefresh = false;
                //
                float tMoveX = event.getX();
                float tMoveY = event.getY();
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
                if (needRefresh) {
                    //
                    mapParam.cleanSelectType(FeMapParam.SELECT_MAP);
                    mapParam.setSelectType(FeMapParam.SELECT_MOVE);
                    //
                    ((FeSectionLayout)getParent().getParent()).onTouch(event.getAction(), event.getX(), event.getY());
                    //
                    invalidate();
                }
            }
            break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}

