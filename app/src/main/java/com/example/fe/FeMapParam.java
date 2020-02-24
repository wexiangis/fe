package com.example.fe;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

/*
    加载地图后所产生的一系列关键参数,用于传递给地图上的人物动画
 */
public class FeMapParam {
    //屏幕实际宽高
    public int screenWidth = 1920, screenHeight = 1080;

    //地图实际显示区域
    public Rect mapDist = null;
    //地图移动格子数
    public int xGridErr = 0, yGridErr = 0;

    //地图实际显示宽高像素
    public int width = 1920, height = 1080;
    //地图横纵向格数
    public int xGrid = 20, yGrid = 10;
    //屏幕横纵向格数
    public int screenXGrid = 20, screenYGrid = 10;

    //横纵向每格像素
    public float xGridPixel = 96, yGridPixel = 108;
    //横纵向动画方块像素大小
    public int xAnimGridPixel = 192, yAnimGridPixel = 216;
    //横纵向动画初始偏移
    public int xAnimOffsetPixel = 48, yAnimOffsetPixel = 54;

    //方格显示时可以接受的最小像素值
    public int _piexlPerGrid = 64;

    //----- 地图梯形变换 -----
    public int reduceGrid = 0;
    public float reduce = 0;
    public int srcGridX = 0, srcGridY = 0, srcGridXStart = 0, srcGridYStart = 0;
    public float[][] srcGridLine = new float[64][4];//[n][0]:行高, [n][1]:总行高, [n][2]:横向offset, [n][3]:平均宽
    public float[] srcPoint = new float[8];
    public float[] srcPointBitmap = new float[8];
    public float[] distPoint = new float[8];

    public void getMatrix(Matrix matrix, int bitmapWidth, int bitmapHeight){
        //
        srcPoint[0] = -mapDist.left;
        srcPoint[1] = -mapDist.top;
        srcPoint[2] = -mapDist.left;
        srcPoint[3] = -mapDist.top + screenHeight;
        srcPoint[4] = -mapDist.left + screenWidth;
        srcPoint[5] = -mapDist.top + screenHeight;
        srcPoint[6] = -mapDist.left + screenWidth;
        srcPoint[7] = -mapDist.top;
        //梯形左右和上边缩进格数
        reduce = xGridPixel*4;
        //地图靠近边界时逐渐恢复比例
        if(reduce > width - srcPoint[6])
            reduce = width - srcPoint[6];
        if(reduce > srcPoint[0])
            reduce = srcPoint[0];
        if(reduce > srcPoint[1])
            reduce = srcPoint[1];
        //
        reduceGrid = Math.round(reduce/xGridPixel);
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
        srcGridX = reduceGrid*2 + screenXGrid;
        srcGridY = reduceGrid + screenYGrid;
        //
        float xPow = (float)bitmapWidth/width;
        float yPow = (float)bitmapHeight/height;
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
        //
        matrix.setPolyToPoly(srcPointBitmap, 0, distPoint, 0, 4);

        //第一行的高, 总高, 横向offset, 平均宽
        srcGridLine[0][0] = yGridPixel - reduce*2/(srcGridY-1);
        srcGridLine[0][1] = srcGridLine[0][0];
        srcGridLine[0][2] = srcGridLine[0][1]/screenHeight*reduce;
        srcGridLine[0][3] = (srcGridLine[0][2]*2 + screenWidth)/srcGridX;
        //最后一行和第一行高的差值
        float ySErr = yGridPixel - srcGridLine[0][0];

        int i = 0;
        //统计每行信息
        for(i = 1; i < srcGridY; i++) {
            srcGridLine[i][0] = srcGridLine[0][0] + (float)i/srcGridY*ySErr;
            srcGridLine[i][1] = srcGridLine[i-1][1] + srcGridLine[i][0];
            srcGridLine[i][2] = srcGridLine[i][1]/screenHeight*reduce;
            srcGridLine[i][3] = (srcGridLine[i][2]*2 + screenWidth)/srcGridX;
        }
        //把存在的误差均摊给中间格子
        float errCorrect = (srcGridLine[srcGridY-1][1] - screenHeight)/(srcGridY-1);
        srcGridLine[0][0] -= errCorrect;
        srcGridLine[0][1] = srcGridLine[0][0];
        srcGridLine[0][2] = srcGridLine[0][1]/screenHeight*reduce;
        srcGridLine[0][3] = (srcGridLine[0][2]*2 + screenWidth)/srcGridX;
        for(i = 1; i < srcGridY-1; i++) {
            srcGridLine[i][0] -= errCorrect;
            srcGridLine[i][1] = srcGridLine[i-1][1] + srcGridLine[i][0];
            srcGridLine[i][2] = srcGridLine[i][1]/screenHeight*reduce;
            srcGridLine[i][3] = (srcGridLine[i][2]*2 + screenWidth)/srcGridX;
        }
        //
        srcGridLine[i][0] = yGridPixel;
        srcGridLine[i][1] = srcGridLine[i-1][1] + srcGridLine[i][0];
        srcGridLine[i][2] = reduce;
        srcGridLine[i][3] = xGridPixel;
    }

    //----- 地图梯形变换 -----

    //输入坐标求格子位置
    public void getRectByLocation(float x, float y, Rect r) {
        for(int i = 0; i < srcGridY; i++){
            if(y < srcGridLine[i][1]){
                r.top = (int)(srcGridLine[i][1] - srcGridLine[i][0]);
                r.bottom = (int)(srcGridLine[i][1]);
                r.left = (int)((x + srcGridLine[i][2])/srcGridLine[i][3]);
                r.left = (int)(r.left*srcGridLine[i][3] - srcGridLine[i][2]);
                r.right = (int)(r.left + srcGridLine[i][3]);
                break;
            }
        }
    }

    //输入格子求位置
    public void getRectByGrid(int xG, int yG, Rect r){
        r.left = (int)(xG*xGridPixel + mapDist.left);
        r.top = (int)(yG*yGridPixel + mapDist.top);
        r.right = (int)(r.left + xGridPixel);
        r.bottom = (int)(r.top + yGridPixel);
    }

    //地图适配屏幕
    public void init(int screenXSixe, int screenYSize, int mapXGrid, int mapYGrid, int piexlPerGrid){
        //比较屏幕和地图长和高比例
        float screenXDivY = (float)screenXSixe/screenYSize;
        float mapXDivY = (float)mapXGrid/mapYGrid;
        //限制屏幕最大显示格数
        screenXGrid = screenXSixe/piexlPerGrid;
        screenYGrid = screenYSize/piexlPerGrid;
        //关键参数备份
        _piexlPerGrid = piexlPerGrid;
        screenWidth = screenXSixe;
        screenHeight = screenYSize;
        xGrid = mapXGrid;
        yGrid = mapYGrid;
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
                mapDist.left = (int)((int)((float)mapDist.left/width*xGrid)*xGridPixel);
            }
            mapDist.right = width - mapDist.left;
            //
            if(mapDist.top + height < screenHeight)
                mapDist.top = screenHeight - height;
            else{
                mapDist.top -= (height - (mapDist.bottom - mapDist.top))/2;
                mapDist.top = (int)((int)((float)mapDist.top/height*yGrid)*yGridPixel);
            }
            mapDist.bottom = height - mapDist.top;
        }
        //
        xGridErr = yGridErr = 0;
    }
}

