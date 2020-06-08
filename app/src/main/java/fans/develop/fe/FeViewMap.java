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

    private FeSectionMap sectionMap;
    private FeSectionUnit sectionUnit;

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
        sectionMap = FeData.section.sectionMap;
        sectionUnit = FeData.section.sectionUnit;
        //输入坐标求格子位置
        sectionMap.getRectByLocation(0, 0, sectionMap.selectSite);
        //画笔
        paintMap = new Paint();
        paintMap.setColor(Color.BLUE);
        //引入心跳
        FaData.addHeartUnit(heartMapMov);
    }

    //删除人物,之后需自行 removeView()
    public void delete(){
        //解除心跳注册
        FaData.removeHeartUnit(heartUnit);
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
        xGridErr = x - sectionMap.srcGridCenter.centerX();
        yGridErr = y - sectionMap.srcGridCenter.centerY();
    }

    //设置(x,y)所在格子为地图中心
    public void setCenter(int x, int y){
        //先把挪动停止
        xGridErr = yGridErr = 0;
        //居中比较
        sectionMap.xGridErr += x - sectionMap.srcGridCenter.centerX();
        sectionMap.yGridErr += y - sectionMap.srcGridCenter.centerY();
        //防止把地图移出屏幕
        if (sectionMap.xGridErr < 0)
            sectionMap.xGridErr = 0;
        else if (sectionMap.xGridErr + sectionMap.screenXGrid > sectionMap.map.xGrid)
            sectionMap.xGridErr = sectionMap.map.xGrid - sectionMap.screenXGrid;
        if (sectionMap.yGridErr < 0)
            sectionMap.yGridErr = 0;
        else if (sectionMap.yGridErr + sectionMap.screenYGrid > sectionMap.map.yGrid)
            sectionMap.yGridErr = sectionMap.map.yGrid - sectionMap.screenYGrid;
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
                    sectionMap.xGridErr += 1;
                }else if(xGridErr < 0) {
                    xGridErr += 1;
                    sectionMap.xGridErr -= 1;
                }
                if(yGridErr > 0) {
                    yGridErr -= 1;
                    sectionMap.yGridErr += 1;
                }else if(yGridErr < 0) {
                    yGridErr += 1;
                    sectionMap.yGridErr -= 1;
                }
                //防止地图移出屏幕
                if (sectionMap.xGridErr < 0){
                    sectionMap.xGridErr = 0;
                    xGridErr = 0;
                }else if (sectionMap.xGridErr + sectionMap.screenXGrid > sectionMap.map.xGrid){
                    sectionMap.xGridErr = sectionMap.map.xGrid - sectionMap.screenXGrid;
                    xGridErr = 0;
                }
                if (sectionMap.yGridErr < 0){
                    sectionMap.yGridErr = 0;
                    yGridErr = 0;
                }else if (sectionMap.yGridErr + sectionMap.screenYGrid > sectionMap.map.yGrid){
                    sectionMap.yGridErr = sectionMap.map.yGrid - sectionMap.screenYGrid;
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
        sectionMap.mapDist.left = (int)this.getTranslationX() - (int)(sectionMap.xGridErr*sectionMap.xGridPixel);
        sectionMap.mapDist.top = (int)this.getTranslationY() - (int)(sectionMap.yGridErr*sectionMap.yGridPixel);
        sectionMap.mapDist.right = sectionMap.mapDist.left + sectionMap.width;
        sectionMap.mapDist.bottom = sectionMap.mapDist.top + sectionMap.height;
        //梯形变换
        sectionMap.getMatrix();
        //显示地图
        canvas.drawBitmap(sectionMap.bitmap, sectionMap.matrix, paintMap);
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
                FeData.section.cleanClickState(FeSection.ON_MOVE);
            }
            break;
            case MotionEvent.ACTION_UP: {
                float tUpX = event.getX();
                float tUpY = event.getY();
                //选中方格标志
                FeData.section.cleanClickState(FeSection.ON_MOVE);
                //是否是点击事件
                if(!isMove) {
                    FeData.section.setClickState(FeSection.ON_HIT_MAP);
                    //上传click事件
                    ((FeLayoutSection)getParent().getParent()).click(tUpX, tUpY);
                }
                else{
                    FeData.section.cleanClickState(FeSection.ON_HIT_MAP);
                }
                isMove = false;
                //输入坐标求格子位置
                sectionMap.getRectByLocation(tUpX, tUpY, sectionMap.selectSite);
                //选中人物太过靠近边界,挪动地图
                if(FeData.section.checkClickState(FeSection.ON_HIT_UNIT) &&
                    !sectionMap.srcGridCenter.contains(
                    sectionUnit.selectSite.point[0],
                    sectionUnit.selectSite.point[1])){
//                    //挪动地图,把选中点居中
//                    moveCenter(sectionUnit.selectSite.point[0], sectionUnit.selectSite.point[1]);
                    //把需要移动的量先记到xGridErr,yGridErr, 动画心跳回调会慢慢把这些差值吃掉
                    if(sectionUnit.selectSite.point[0] < sectionMap.srcGridCenter.left)
                        xGridErr = sectionUnit.selectSite.point[0] - sectionMap.srcGridCenter.left;
                    else if(sectionUnit.selectSite.point[0] > sectionMap.srcGridCenter.right)
                        xGridErr = sectionUnit.selectSite.point[0] - sectionMap.srcGridCenter.right;
                    if(sectionUnit.selectSite.point[1] < sectionMap.srcGridCenter.top)
                        yGridErr = sectionUnit.selectSite.point[1] - sectionMap.srcGridCenter.top;
                    else if(sectionUnit.selectSite.point[1] > sectionMap.srcGridCenter.bottom)
                        yGridErr = sectionUnit.selectSite.point[1] - sectionMap.srcGridCenter.bottom;
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
                if (Math.abs(xErr) > sectionMap.xGridPixel) {
                    if (xErr > 0) sectionMap.xGridErr -= 1;
                    else sectionMap.xGridErr += 1;
                    tDownX = tMoveX;
                    needRefresh = true;
                    isMove = true;
                }
                //纵向移动是否满一格像素,是就移动一格
                if (Math.abs(yErr) > sectionMap.yGridPixel) {
                    if (yErr > 0) sectionMap.yGridErr -= 1;
                    else sectionMap.yGridErr += 1;
                    tDownY = tMoveY;
                    needRefresh = true;
                    isMove = true;
                }
                //防止把地图移出屏幕
                if (sectionMap.xGridErr < 0)
                    sectionMap.xGridErr = 0;
                else if (sectionMap.xGridErr + sectionMap.screenXGrid > sectionMap.map.xGrid)
                    sectionMap.xGridErr = sectionMap.map.xGrid - sectionMap.screenXGrid;
                if (sectionMap.yGridErr < 0)
                    sectionMap.yGridErr = 0;
                else if (sectionMap.yGridErr + sectionMap.screenYGrid > sectionMap.map.yGrid)
                    sectionMap.yGridErr = sectionMap.map.yGrid - sectionMap.screenYGrid;
                //调用一次onDraw
                if (needRefresh) {
                    //清触屏点击
                    FeData.section.cleanClickState(FeSection.ON_HIT_MAP);
                    //设置触屏移动
                    FeData.section.setClickState(FeSection.ON_MOVE);
                    //输入坐标求格子位置
                    sectionMap.getRectByLocation(tMoveX, tMoveY, sectionMap.selectSite);
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

