package com.example.fe;

import android.graphics.Rect;

public class FeMapParam {

    //地图显示区域
    public Rect rect;
    //
    public int screenWidth, screenHeight;
    //地图实际显示长高
    public int[] mapWidthHeight;
    //地图横纵向格数
    public int xGrid, yGrid;
    //横纵向每格像素
    public float xGridPixel, yGridPixel;

    public FeMapParam(int left, int top, int right, int bottom) {
        rect = new Rect(left, top, right, bottom);
    }
}

