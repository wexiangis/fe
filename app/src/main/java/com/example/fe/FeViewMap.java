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
public class FeViewMap extends View {

    private FeParamMap paramMap;

    //地图移动格子数
    private int xGridErr = 0, yGridErr = 0;

    //触屏按下时记录坐标
    private float tDownX, tDownY;
    //分辨移动事件还是点击事件
    private boolean isMove = false;

    //画笔
    private Paint paintMap;

    public FeViewMap(Context context) {
        super(context);
        paramMap = FeData.feParamMap;
        //输入坐标求格子位置
        paramMap.getRectByLocation(0, 0, paramMap.selectMap);
        //画笔
        paintMap = new Paint();
        paintMap.setColor(Color.BLUE);
        //引入心跳
        FeData.feHeart.addUnit(heartMapMov);
    }

    //动态挪动地图,x>0时地图往右移,y>0时地图往下移
    public void moveGrid(int x, int y){
        xGridErr -= x;
        yGridErr -= y;
    }

    //动态挪动地图,设置(x,y)所在格子为地图中心
    public void moveCenter(int x, int y){
        //先把挪动停止
        xGridErr = yGridErr = 0;
        //居中比较
        xGridErr = x - paramMap.srcGridCenter.centerX();
        yGridErr = y - paramMap.srcGridCenter.centerY();
    }

    //设置(x,y)所在格子为地图中心
    public void setCenter(int x, int y){
        //先把挪动停止
        xGridErr = yGridErr = 0;
        //居中比较
        paramMap.xGridErr += x - paramMap.srcGridCenter.centerX();
        paramMap.yGridErr += y - paramMap.srcGridCenter.centerY();
        //防止把地图移出屏幕
        if (paramMap.xGridErr < 0)
            paramMap.xGridErr = 0;
        else if (paramMap.xGridErr + paramMap.screenXGrid > paramMap.xGrid)
            paramMap.xGridErr = paramMap.xGrid - paramMap.screenXGrid;
        if (paramMap.yGridErr < 0)
            paramMap.yGridErr = 0;
        else if (paramMap.yGridErr + paramMap.screenYGrid > paramMap.yGrid)
            paramMap.yGridErr = paramMap.yGrid - paramMap.screenYGrid;
    }

    //动画心跳回调
    private FeHeartUnit heartMapMov = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            //需要挪图?
            if(xGridErr != 0 || yGridErr != 0)
            {
                //每次移动一格
                if(xGridErr > 0) {
                    xGridErr -= 1;
                    paramMap.xGridErr += 1;
                }else if(xGridErr < 0) {
                    xGridErr += 1;
                    paramMap.xGridErr -= 1;
                }
                if(yGridErr > 0) {
                    yGridErr -= 1;
                    paramMap.yGridErr += 1;
                }else if(yGridErr < 0) {
                    yGridErr += 1;
                    paramMap.yGridErr -= 1;
                }
                //防止地图移出屏幕
                if (paramMap.xGridErr < 0){
                    paramMap.xGridErr = 0;
                    xGridErr = 0;
                }else if (paramMap.xGridErr + paramMap.screenXGrid > paramMap.xGrid){
                    paramMap.xGridErr = paramMap.xGrid - paramMap.screenXGrid;
                    xGridErr = 0;
                }
                if (paramMap.yGridErr < 0){
                    paramMap.yGridErr = 0;
                    yGridErr = 0;
                }else if (paramMap.yGridErr + paramMap.screenYGrid > paramMap.yGrid){
                    paramMap.yGridErr = paramMap.yGrid - paramMap.screenYGrid;
                    yGridErr = 0;
                }
                //调用一次onDraw
                FeViewMap.this.invalidate();
            }
        }
    });

    //
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //相对布局位置偏移
        paramMap.mapDist.left = (int)this.getTranslationX() - (int)(paramMap.xGridErr*paramMap.xGridPixel);
        paramMap.mapDist.top = (int)this.getTranslationY() - (int)(paramMap.yGridErr*paramMap.yGridPixel);
        paramMap.mapDist.right = paramMap.mapDist.left + paramMap.width;
        paramMap.mapDist.bottom = paramMap.mapDist.top + paramMap.height;
        //梯形变换
        paramMap.getMatrix();
        //显示地图
        canvas.drawBitmap(paramMap.bitmap, paramMap.matrix, paintMap);
        //地图移动了,刷新其他信息
        ((FeLayoutSection)getParent().getParent()).refresh();
    }

    //
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                tDownX = event.getX();
                tDownY = event.getY();
                isMove = false;
                //
                xGridErr = yGridErr = 0;
                paramMap.cleanSelectType(FeParamMap.SELECT_MOVE);
                paramMap.cleanSelectType(FeParamMap.SELECT_MOVE_END);
                ((FeLayoutSection)getParent().getParent()).onTouch(MotionEvent.ACTION_DOWN, tDownX, tDownY);
            }
            break;
            case MotionEvent.ACTION_UP: {
                float tUpX = event.getX();
                float tUpY = event.getY();
                //选中方格标志
                paramMap.cleanSelectType(FeParamMap.SELECT_MOVE);
                if(!isMove)
                    paramMap.setSelectType(FeParamMap.SELECT_MAP);
                else{
                    paramMap.cleanSelectType(FeParamMap.SELECT_MAP);
                    paramMap.setSelectType(FeParamMap.SELECT_MOVE_END);
                }
                isMove = false;
                //输入坐标求格子位置
                paramMap.getRectByLocation(tUpX, tUpY, paramMap.selectMap);
                //上传onTouch事件
                ((FeLayoutSection)getParent().getParent()).onTouch(event.getAction(), tUpX, tUpY);
                //选中人物太过靠近边界,挪动地图
                if(paramMap.checkSelectType(FeParamMap.SELECT_UNIT) &&
                    !paramMap.srcGridCenter.contains(
                    paramMap.selectUnit.selectPoint[0],
                    paramMap.selectUnit.selectPoint[1])){
//                    //挪动地图,把选中点居中
//                    moveCenter(paramMap.selectUnit.selectPoint[0], paramMap.selectUnit.selectPoint[1]);
                    //把需要移动的量先记到xGridErr,yGridErr, 动画心跳回调会慢慢把这些差值吃掉
                    if(paramMap.selectUnit.selectPoint[0] < paramMap.srcGridCenter.left)
                        xGridErr = paramMap.selectUnit.selectPoint[0] - paramMap.srcGridCenter.left;
                    else if(paramMap.selectUnit.selectPoint[0] > paramMap.srcGridCenter.right)
                        xGridErr = paramMap.selectUnit.selectPoint[0] - paramMap.srcGridCenter.right;
                    if(paramMap.selectUnit.selectPoint[1] < paramMap.srcGridCenter.top)
                        yGridErr = paramMap.selectUnit.selectPoint[1] - paramMap.srcGridCenter.top;
                    else if(paramMap.selectUnit.selectPoint[1] > paramMap.srcGridCenter.bottom)
                        yGridErr = paramMap.selectUnit.selectPoint[1] - paramMap.srcGridCenter.bottom;
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                boolean needRefresh = false;
                //
                float tMoveX = event.getX();
                float tMoveY = event.getY();
                float xErr = tMoveX - tDownX;
                float yErr = tMoveY - tDownY;
                //横向移动是否满一格像素,是就移动一格
                if (Math.abs(xErr) > paramMap.xGridPixel) {
                    if (xErr > 0) paramMap.xGridErr -= 1;
                    else paramMap.xGridErr += 1;
                    tDownX = tMoveX;
                    needRefresh = true;
                    isMove = true;
                }
                //纵向移动是否满一格像素,是就移动一格
                if (Math.abs(yErr) > paramMap.yGridPixel) {
                    if (yErr > 0) paramMap.yGridErr -= 1;
                    else paramMap.yGridErr += 1;
                    tDownY = tMoveY;
                    needRefresh = true;
                    isMove = true;
                }
                //防止把地图移出屏幕
                if (paramMap.xGridErr < 0)
                    paramMap.xGridErr = 0;
                else if (paramMap.xGridErr + paramMap.screenXGrid > paramMap.xGrid)
                    paramMap.xGridErr = paramMap.xGrid - paramMap.screenXGrid;
                if (paramMap.yGridErr < 0)
                    paramMap.yGridErr = 0;
                else if (paramMap.yGridErr + paramMap.screenYGrid > paramMap.yGrid)
                    paramMap.yGridErr = paramMap.yGrid - paramMap.screenYGrid;
                //调用一次onDraw
                if (needRefresh) {
                    //清触屏点击
                    paramMap.cleanSelectType(FeParamMap.SELECT_MAP);
                    //设置触屏移动
                    paramMap.setSelectType(FeParamMap.SELECT_MOVE);
                    //输入坐标求格子位置
                    paramMap.getRectByLocation(tMoveX, tMoveY, paramMap.selectMap);
                    //上传onTouch事件
                    ((FeLayoutSection)getParent().getParent()).onTouch(event.getAction(), tMoveX, tMoveY);
                    //调用一次onDraw
                    invalidate();
                }
            }
            break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}

