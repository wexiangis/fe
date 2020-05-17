package com.example.fe;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.DisplayMetrics;


/*
    加载地图后所产生的一系列关键参数,用于传递给地图上的人物动画
 */
public class FeParamMap {

    public int section;

    public Matrix matrix = new Matrix();

    public FeInfoMap map;

    public Bitmap bitmap = null;

    //----- 地图基本信息 -----

    //屏幕实际宽高
    public int screenWidth = 1920, screenHeight = 1080;

    //地图实际显示区域
    public Rect mapDist = null;
    //地图移动格子数
    public int xGridErr = 0, yGridErr = 0;

    //地图实际显示宽高像素
    public int width = 1920, height = 1080;
    //屏幕横纵向格数
    public int screenXGrid = 20, screenYGrid = 10;

    //横纵向每格像素
    public float xGridPixel = 96, yGridPixel = 108;
    //横纵向动画方块像素大小
    public int xAnimGridPixel = 192, yAnimGridPixel = 216;
    //横纵向动画初始偏移
    public int xAnimOffsetPixel = 48, yAnimOffsetPixel = 54;

    public FeParamMap(int feSection)
    {
        section = feSection;
        //获取屏幕参数
        DisplayMetrics dm = new DisplayMetrics();
        FeData.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //初始化map参数结构体
        map = FeData.feAssets.map.getMap(section);
        //适配屏幕
        init(dm.widthPixels, dm.heightPixels, map.xGrid, map.yGrid, map.pixelPerGrid);
        //两参数分别为xy缩放比例
        float xp = (float)width/map.bitmap.getWidth()/2;
        if(xp > 1.5f)
            xp = 1.5f;
        float yp = (float)height/map.bitmap.getHeight()/2;
        if(yp > 1.5f)
            yp = 1.5f;
        matrix.postScale(xp, yp);
        bitmap = Bitmap.createBitmap(map.bitmap, 0, 0,
                (int)map.bitmap.getWidth(), (int)map.bitmap.getHeight(), matrix, true);
    }

    //地图适配屏幕
    public void init(int screenXSixe, int screenYSize, int mapXGrid, int mapYGrid, int pixelPG){
        //比较屏幕和地图长和高比例
        float screenXDivY = (float)screenXSixe/screenYSize;
        float mapXDivY = (float)mapXGrid/mapYGrid;
        //限制屏幕最大显示格数
        screenXGrid = screenXSixe/pixelPG;
        screenYGrid = screenYSize/pixelPG;
        //关键参数备份
        map.pixelPerGrid = pixelPG;
        screenWidth = screenXSixe;
        screenHeight = screenYSize;
        map.xGrid = mapXGrid;
        map.yGrid = mapYGrid;
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
        xGridPixel = (float)screenWidth/screenXGrid;
        yGridPixel = (float)screenHeight/screenYGrid;
        //关联参数
        xAnimGridPixel = (int)(xGridPixel*2);
        yAnimGridPixel = (int)(yGridPixel*2);
        xAnimOffsetPixel = (int)(-xGridPixel/2);
        yAnimOffsetPixel = (int)(-yGridPixel);
        //得到地图实际显示大小
        width = (int)(xGridPixel*mapXGrid);
        height = (int)(yGridPixel*mapYGrid);
        //和原大小进行比较后中心缩放
        if(mapDist == null)
            mapDist = new Rect(0, 0, width, height);
        else{
            if(mapDist.left + width < screenWidth)
                mapDist.left = screenWidth - width;
            else{
                mapDist.left -= (width - (mapDist.right - mapDist.left))/2;
                mapDist.left = (int)((int)((float)mapDist.left/width*map.xGrid)*xGridPixel);
            }
            mapDist.right = width - mapDist.left;
            //
            if(mapDist.top + height < screenHeight)
                mapDist.top = screenHeight - height;
            else{
                mapDist.top -= (height - (mapDist.bottom - mapDist.top))/2;
                mapDist.top = (int)((int)((float)mapDist.top/height*map.yGrid)*yGridPixel);
            }
            mapDist.bottom = height - mapDist.top;
        }
        //
        xGridErr = yGridErr = 0;
    }

    //----- 地图梯形变换 -----

    //梯形缩进的格数和像素
    public int reduceGrid = 0;
    public float reduce = 0;
    //梯形区域里的方格状态: 横纵向格数, 起始格子
    public int srcGridX = 0, srcGridY = 0, srcGridXStart = 0, srcGridYStart = 0;
    //中心甜区,用于判断是否需要挪动地图来让选中人物居中
    public Rect srcGridCenter = new Rect(0,0,0,0);
    //[n][0]:行高, [n][1]:总行高, [n][2]:横向offset, [n][3]:平均宽
    public float[][] srcGridLine = new float[64][4];
    //屏幕在地图上框了一个矩形,然后拉高拉宽上边两个点,框到一个倒梯形区域
    public float[] srcPoint = new float[8];
    //srcPoint的坐标数据按地图的实际分辨率转换之后的位置
    public float[] srcPointBitmap = new float[8];
    //上面框到的倒梯形输出到屏幕的位置
    public float[] distPoint = new float[8];

    //梯形变换缩进格数
    private int transferGrid = 4;

    //获取梯形转换矩阵,用于绘制
    public void getMatrix(){
        srcPoint[0] = -mapDist.left;
        srcPoint[1] = -mapDist.top;
        srcPoint[2] = -mapDist.left;
        srcPoint[3] = -mapDist.top + screenHeight;
        srcPoint[4] = -mapDist.left + screenWidth;
        srcPoint[5] = -mapDist.top + screenHeight;
        srcPoint[6] = -mapDist.left + screenWidth;
        srcPoint[7] = -mapDist.top;
        //梯形左右和上边缩进格数
        reduce = xGridPixel*transferGrid;
        //地图靠近边界时逐渐恢复比例
        if(reduce > width - srcPoint[6])
            reduce = width - srcPoint[6];
        if(reduce > srcPoint[0])
            reduce = srcPoint[0];
        if(reduce > srcPoint[1])
            reduce = srcPoint[1];
        //梯形变换
        srcPoint[0] -= reduce;
        srcPoint[6] += reduce;
        srcPoint[1] -= reduce;
        srcPoint[7] -= reduce;
        //防止梯形出屏
//        if(srcPoint[0] < 0) srcPoint[0] = 0;
//        if(srcPoint[6] > width) srcPoint[6] = width;
//        if(srcPoint[1] < 0) srcPoint[1] = 0;
//        if(srcPoint[7] < 0) srcPoint[7] = 0;
        //
        float xPow = (float)bitmap.getWidth()/width;
        float yPow = (float)bitmap.getHeight()/height;
        //
        srcPointBitmap[0] = srcPoint[0]*xPow;
        srcPointBitmap[1] = srcPoint[1]*yPow;
        srcPointBitmap[2] = srcPoint[2]*xPow;
        srcPointBitmap[3] = srcPoint[3]*yPow;
        srcPointBitmap[4] = srcPoint[4]*xPow;
        srcPointBitmap[5] = srcPoint[5]*yPow;
        srcPointBitmap[6] = srcPoint[6]*xPow;
        srcPointBitmap[7] = srcPoint[7]*yPow;
        //
        distPoint[0] = 0;
        distPoint[1] = 0;
        distPoint[2] = 0;
        distPoint[3] = screenHeight;
        distPoint[4] = screenWidth;
        distPoint[5] = screenHeight;
        distPoint[6] = screenWidth;
        distPoint[7] = 0;
        //梯形变换
        matrix.setPolyToPoly(srcPointBitmap, 0, distPoint, 0, 4);

        //关键参数提取
        reduceGrid = Math.round(reduce/xGridPixel);
        srcGridX = reduceGrid*2 + screenXGrid;
        srcGridY = reduceGrid + screenYGrid;
        srcGridXStart = Math.round(srcPoint[0]/xGridPixel);
        srcGridYStart = Math.round(srcPoint[1]/yGridPixel);

        //中心甜区
        srcGridCenter.left = srcGridXStart + reduceGrid + 3;
        srcGridCenter.top = srcGridYStart + reduceGrid + 3;
        srcGridCenter.right = srcGridCenter.left + (screenXGrid - 6) - 1;
        srcGridCenter.bottom = srcGridCenter.top + (screenYGrid - 6) - 1;

        //第一行的高, 总高, 横向offset, 平均宽
        srcGridLine[0][0] = yGridPixel - reduce*1.5f/srcGridY - reduceGrid*3.0f;
        srcGridLine[0][1] = srcGridLine[0][0];
        srcGridLine[0][2] = srcGridLine[0][1]/screenHeight*reduce;
        srcGridLine[0][3] = (srcGridLine[0][2]*2 + screenWidth)/srcGridX;
        //最后一行的高, 总高, 横向offset, 平均宽
        srcGridLine[srcGridY-1][0] = yGridPixel + reduceGrid;
        srcGridLine[srcGridY-1][1] = screenHeight;
        srcGridLine[srcGridY-1][2] = srcGridLine[srcGridY-1][1]/screenHeight*reduce;
        srcGridLine[srcGridY-1][3] = (srcGridLine[srcGridY-1][2]*2 + screenWidth)/srcGridX;
        //最后一行和第一行高的差值
        float ySErr = srcGridLine[srcGridY-1][0]*srcGridY - screenHeight;
        float n = 0;//分母
        float[] m = new float[srcGridY];//分子
        //把分子数组累加到分母n
        float basePoint = 10000;
        float basePointCount = 0;
        float basePointPlusBase = 0.99f;
        for(int i = 0; i < srcGridY; i++) {
            basePointCount += basePoint;
            m[i] = basePointCount;
            n += m[i];
            //
            if(reduceGrid > 0) {
                basePoint *= basePointPlusBase;
                basePointPlusBase *= 0.99f;
            }
        }
        //统计每行信息
        for(int i = srcGridY-2; i >= 0; i--) {
            srcGridLine[i][0] = srcGridLine[srcGridY-1][0] - m[srcGridY-1-i]/n*ySErr;
            srcGridLine[i][1] = srcGridLine[i+1][1] - srcGridLine[i+1][0];
            srcGridLine[i][2] = srcGridLine[i][1]/screenHeight*reduce;
            srcGridLine[i][3] = (srcGridLine[i][2]*2 + screenWidth)/srcGridX;
        }
    }

    //----- 求梯形中的某一格子 -----

    public FeInfoGrid selectMap = new FeInfoGrid();

    //输入格子求位置
    public void getRectByGrid(int xG, int yG, FeInfoGrid fig){
        fig.selectPoint[0] = xG;
        fig.selectPoint[1] = yG;
        //
        if(xG >= srcGridXStart &&
                xG < srcGridXStart + srcGridX &&
                yG >= srcGridYStart &&
                yG < srcGridYStart + srcGridY)
        {
            int x = xG - srcGridXStart;
            int y = yG - srcGridYStart;
            //
            fig.selectRect.top = (int)(srcGridLine[y][1] - srcGridLine[y][0]);
            fig.selectRect.bottom = (int)(srcGridLine[y][1]);
            fig.selectRect.left = (int)(x*srcGridLine[y][3] - srcGridLine[y][2]);
            fig.selectRect.right = (int)(fig.selectRect.left + srcGridLine[y][3]);
            //
            fig.selectPath.reset();
            //
            if(y == 0){
                fig.selectPath.moveTo(x * screenWidth / srcGridX, 0);
                fig.selectPath.lineTo((x + 1) * screenWidth / srcGridX, 0);
            }else{
                fig.selectPath.moveTo(
                        x * srcGridLine[y-1][3] - srcGridLine[y-1][2],
                        srcGridLine[y-1][1]);
                fig.selectPath.lineTo(
                        (x + 1) * srcGridLine[y-1][3] - srcGridLine[y-1][2],
                        srcGridLine[y-1][1]);
            }
            fig.selectPath.lineTo(fig.selectRect.right, fig.selectRect.bottom);
            fig.selectPath.lineTo(fig.selectRect.left, fig.selectRect.bottom);
            fig.selectPath.close();
        }
        else{
            fig.selectRect.left = (int)(-xGridPixel)*2;
            fig.selectRect.right = (int)(-xGridPixel);
            fig.selectRect.top = (int)(-yGridPixel)*2;
            fig.selectRect.bottom = (int)(-yGridPixel);
        }
    }

    //输入坐标求格子位置
    public void getRectByLocation(float x, float y, FeInfoGrid fig) {
        for(int yCount = 0; yCount < srcGridY; yCount++){
            if(y < srcGridLine[yCount][1]){
                fig.selectPoint[1] = yCount + srcGridYStart;
                fig.selectPoint[0] = (int)((x + srcGridLine[yCount][2])/srcGridLine[yCount][3]) + srcGridXStart;
                //
                getRectByGrid(fig.selectPoint[0], fig.selectPoint[1], fig);
                return;
            }
        }
        getRectByGrid(-1, -1 + srcGridXStart, fig);
    }
}

