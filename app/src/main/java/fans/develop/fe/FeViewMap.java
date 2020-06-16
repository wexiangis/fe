package fans.develop.fe;

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

    private FeLayoutSection.Callback callback;

    //地图移动格子数
    private int xGridErr = 0, yGridErr = 0;

    //触屏按下时记录坐标
    private float tDownX, tDownY;
    //分辨移动事件还是点击事件
    private boolean isMove = false;

    //画笔
    private Paint paintMap;

    public FeViewMap(Context context, FeLayoutSection.Callback callback) {
        super(context);
        this.callback = callback;
        //输入坐标求格子位置
        callback.getSectionMap().getRectByLocation(0, 0, callback.getSectionMap().selectSite);
        //画笔
        paintMap = new Paint();
        paintMap.setColor(Color.BLUE);
        //引入心跳
        callback.addHeartUnit(heartMapMov);
    }

    //删除人物,之后需自行 removeView()
    public void delete(){
        //解除心跳注册
        callback.removeHeartUnit(heartMapMov);
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
        xGridErr = x - callback.getSectionMap().srcGridCenter.centerX();
        yGridErr = y - callback.getSectionMap().srcGridCenter.centerY();
    }

    //设置(x,y)所在格子为地图中心
    public void setCenter(int x, int y){
        //先把挪动停止
        xGridErr = yGridErr = 0;
        //居中比较
        callback.getSectionMap().xGridErr += x - callback.getSectionMap().srcGridCenter.centerX();
        callback.getSectionMap().yGridErr += y - callback.getSectionMap().srcGridCenter.centerY();
        //防止把地图移出屏幕
        if (callback.getSectionMap().xGridErr < 0)
            callback.getSectionMap().xGridErr = 0;
        else if (callback.getSectionMap().xGridErr + callback.getSectionMap().screenXGrid > callback.getSectionMap().map.xGrid)
            callback.getSectionMap().xGridErr = callback.getSectionMap().map.xGrid - callback.getSectionMap().screenXGrid;
        if (callback.getSectionMap().yGridErr < 0)
            callback.getSectionMap().yGridErr = 0;
        else if (callback.getSectionMap().yGridErr + callback.getSectionMap().screenYGrid > callback.getSectionMap().map.yGrid)
            callback.getSectionMap().yGridErr = callback.getSectionMap().map.yGrid - callback.getSectionMap().screenYGrid;
    }

    //动画心跳回调
    private FeHeartUnit heartMapMov = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART_QUICK, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            //需要挪图?
            if(xGridErr != 0 || yGridErr != 0)
            {
                //每次移动一格
                if(xGridErr > 0) {
                    xGridErr -= 1;
                    callback.getSectionMap().xGridErr += 1;
                }else if(xGridErr < 0) {
                    xGridErr += 1;
                    callback.getSectionMap().xGridErr -= 1;
                }
                if(yGridErr > 0) {
                    yGridErr -= 1;
                    callback.getSectionMap().yGridErr += 1;
                }else if(yGridErr < 0) {
                    yGridErr += 1;
                    callback.getSectionMap().yGridErr -= 1;
                }
                //防止地图移出屏幕
                if (callback.getSectionMap().xGridErr < 0){
                    callback.getSectionMap().xGridErr = 0;
                    xGridErr = 0;
                }else if (callback.getSectionMap().xGridErr + callback.getSectionMap().screenXGrid > callback.getSectionMap().map.xGrid){
                    callback.getSectionMap().xGridErr = callback.getSectionMap().map.xGrid - callback.getSectionMap().screenXGrid;
                    xGridErr = 0;
                }
                if (callback.getSectionMap().yGridErr < 0){
                    callback.getSectionMap().yGridErr = 0;
                    yGridErr = 0;
                }else if (callback.getSectionMap().yGridErr + callback.getSectionMap().screenYGrid > callback.getSectionMap().map.yGrid){
                    callback.getSectionMap().yGridErr = callback.getSectionMap().map.yGrid - callback.getSectionMap().screenYGrid;
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
        callback.getSectionMap().mapDist.left = (int)this.getTranslationX() - (int)(callback.getSectionMap().xGridErr*callback.getSectionMap().xGridPixel);
        callback.getSectionMap().mapDist.top = (int)this.getTranslationY() - (int)(callback.getSectionMap().yGridErr*callback.getSectionMap().yGridPixel);
        callback.getSectionMap().mapDist.right = callback.getSectionMap().mapDist.left + callback.getSectionMap().width;
        callback.getSectionMap().mapDist.bottom = callback.getSectionMap().mapDist.top + callback.getSectionMap().height;
        //梯形变换
        callback.getSectionMap().getMatrix();
        //显示地图
        canvas.drawBitmap(callback.getSectionMap().bitmap, callback.getSectionMap().matrix, paintMap);
        //地图移动了,刷新其他信息
        callback.refresh();
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
                callback.cleanClickState(FeSection.ON_MOVE);
            }
            break;
            case MotionEvent.ACTION_UP: {
                float tUpX = event.getX();
                float tUpY = event.getY();
                //选中方格标志
                callback.cleanClickState(FeSection.ON_MOVE);
                //是否是点击事件
                if(!isMove) {
                    callback.setClickState(FeSection.ON_HIT_MAP);
                    //上传click事件
                    callback.checkHit(tUpX, tUpY);
                }
                else{
                    callback.cleanClickState(FeSection.ON_HIT_MAP);
                }
                isMove = false;
                //输入坐标求格子位置
                callback.getSectionMap().getRectByLocation(tUpX, tUpY, callback.getSectionMap().selectSite);
                //选中人物太过靠近边界,挪动地图
                if(callback.checkClickState(FeSection.ON_HIT_UNIT) &&
                    !callback.getSectionMap().srcGridCenter.contains(
                    callback.getSectionUnit().selectSite.point[0],
                    callback.getSectionUnit().selectSite.point[1])){
//                    //挪动地图,把选中点居中
//                    moveCenter(callback.getSectionUnit().selectSite.point[0], callback.getSectionUnit().selectSite.point[1]);
                    //把需要移动的量先记到xGridErr,yGridErr, 动画心跳回调会慢慢把这些差值吃掉
                    if(callback.getSectionUnit().selectSite.point[0] < callback.getSectionMap().srcGridCenter.left)
                        xGridErr = callback.getSectionUnit().selectSite.point[0] - callback.getSectionMap().srcGridCenter.left;
                    else if(callback.getSectionUnit().selectSite.point[0] > callback.getSectionMap().srcGridCenter.right)
                        xGridErr = callback.getSectionUnit().selectSite.point[0] - callback.getSectionMap().srcGridCenter.right;
                    if(callback.getSectionUnit().selectSite.point[1] < callback.getSectionMap().srcGridCenter.top)
                        yGridErr = callback.getSectionUnit().selectSite.point[1] - callback.getSectionMap().srcGridCenter.top;
                    else if(callback.getSectionUnit().selectSite.point[1] > callback.getSectionMap().srcGridCenter.bottom)
                        yGridErr = callback.getSectionUnit().selectSite.point[1] - callback.getSectionMap().srcGridCenter.bottom;
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
                if (Math.abs(xErr) > callback.getSectionMap().xGridPixel) {
                    if (xErr > 0) callback.getSectionMap().xGridErr -= 1;
                    else callback.getSectionMap().xGridErr += 1;
                    tDownX = tMoveX;
                    needRefresh = true;
                    isMove = true;
                }
                //纵向移动是否满一格像素,是就移动一格
                if (Math.abs(yErr) > callback.getSectionMap().yGridPixel) {
                    if (yErr > 0) callback.getSectionMap().yGridErr -= 1;
                    else callback.getSectionMap().yGridErr += 1;
                    tDownY = tMoveY;
                    needRefresh = true;
                    isMove = true;
                }
                //防止把地图移出屏幕
                if (callback.getSectionMap().xGridErr < 0)
                    callback.getSectionMap().xGridErr = 0;
                else if (callback.getSectionMap().xGridErr + callback.getSectionMap().screenXGrid > callback.getSectionMap().map.xGrid)
                    callback.getSectionMap().xGridErr = callback.getSectionMap().map.xGrid - callback.getSectionMap().screenXGrid;
                if (callback.getSectionMap().yGridErr < 0)
                    callback.getSectionMap().yGridErr = 0;
                else if (callback.getSectionMap().yGridErr + callback.getSectionMap().screenYGrid > callback.getSectionMap().map.yGrid)
                    callback.getSectionMap().yGridErr = callback.getSectionMap().map.yGrid - callback.getSectionMap().screenYGrid;
                //调用一次onDraw
                if (needRefresh) {
                    //清触屏点击
                    callback.cleanClickState(FeSection.ON_HIT_MAP);
                    //设置触屏移动
                    callback.setClickState(FeSection.ON_MOVE);
                    //输入坐标求格子位置
                    callback.getSectionMap().getRectByLocation(tMoveX, tMoveY, callback.getSectionMap().selectSite);
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

