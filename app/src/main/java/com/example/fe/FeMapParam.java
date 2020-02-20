package com.example.fe;

import android.graphics.Rect;

/*
    加载地图后所产生的一系列关键参数,用于传递给地图上的人物动画
 */
public class FeMapParam {
    //屏幕实际宽高
    public int screenWidth = 1920, screenHeight = 1080;
    //地图实际显示区域
    public Rect mapDist = null;
    //地图实际显示宽高像素
    public int width = 1920, height = 1080;
    //地图横纵向格数
    public int xGrid = 20, yGrid = 10;
    //横纵向每格像素
    public float xGridPixel = 96, yGridPixel = 108;
    //横纵向动画方块像素大小
    public int xAnimGridPixel = 192, yAnimGridPixel = 216;
    //横纵向动画初始偏移
    public int xAnimOffsetPixel = 48, yAnimOffsetPixel = 54;

    //方格显示时可以接受的最小像素值
    private int _piexlPerGrid = 64;

    //地图适配屏幕
    public void init(int screenXSixe, int screenYSize, int mapXGrid, int mapYGrid, int piexlPerGrid){
        //限制屏幕最大显示格数
        int screenXGrid = screenXSixe/piexlPerGrid, screenYGrid = screenYSize/piexlPerGrid;
        //比较屏幕和地图长和高比例
        float screenXDivY = (float)screenXSixe/screenYSize;
        float mapXDivY = (float)mapXGrid/mapYGrid;
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
    }
}

