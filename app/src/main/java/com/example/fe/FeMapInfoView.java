package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class FeMapInfoView extends View {

    private Context _context;
    private FeMapParam mapParam;

    private Rect rectSrcInfo, rectDistInfo;
    private Rect rectSrcHead, rectDistHead;
    private Rect rectPaint;
    private Bitmap bitmapMapInfo, bitmapUnitInfo;
    private Paint paintBitmap, paintName, paintParam;
    private float pixelPow, pixelPow2;

    public FeMapInfoView(Context context, FeMapParam feMapParam){
        super(context);
        _context = context;
        mapParam = feMapParam;
        //
        bitmapMapInfo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapinfo);
        bitmapUnitInfo = BitmapFactory.decodeResource(context.getResources(), R.drawable.header);
        //
        pixelPow = mapParam.yGridPixel*2/bitmapMapInfo.getHeight();
        pixelPow2 = mapParam.yGridPixel*2/bitmapUnitInfo.getHeight();
        //
        rectSrcInfo = new Rect(0, 0, bitmapMapInfo.getWidth(), bitmapMapInfo.getHeight());
        rectDistInfo = new Rect(
                (int)(mapParam.xGridPixel/4),
                mapParam.screenHeight - (int)(mapParam.yGridPixel/4 + bitmapMapInfo.getHeight()*pixelPow),
                (int)(mapParam.xGridPixel/4 + bitmapMapInfo.getWidth()*pixelPow),
                mapParam.screenHeight - (int)(mapParam.yGridPixel/4));
        rectSrcHead = new Rect(0, 0, bitmapUnitInfo.getWidth(), bitmapUnitInfo.getHeight());
        rectDistHead = new Rect(
                (int)(mapParam.xGridPixel/4),
                (int)(mapParam.yGridPixel/4),
                (int)(mapParam.xGridPixel/4 + bitmapUnitInfo.getWidth()*pixelPow2),
                (int)(mapParam.yGridPixel/4 + bitmapUnitInfo.getHeight()*pixelPow2));
        //
        paintBitmap = new Paint();
        paintBitmap.setColor(0xE00000FF);//半透明
        paintName = new Paint();
        paintName.setColor(Color.WHITE);
        paintName.setTextSize(rectDistInfo.width()/5);
        paintName.setTextAlign(Paint.Align.CENTER);
        paintParam = new Paint();
        paintParam.setTextSize(rectDistInfo.width()/7);
        //
        rectPaint = new Rect(
                (int)(rectDistInfo.left + rectDistInfo.width()/5),
                (int)(rectDistInfo.bottom - rectDistInfo.height()/2 + pixelPow*15),
                (int)(rectDistInfo.right - rectDistInfo.width()/5),
                (int)(rectDistInfo.bottom - rectDistInfo.height()/2 + pixelPow*15 + paintParam.getTextSize()*2 + pixelPow*5));
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //图像位置自动调整
        if(mapParam.selectRect.right > mapParam.screenWidth/2){ //放到左边
            rectDistInfo.left = (int)(mapParam.xGridPixel/4);
            rectDistInfo.right = (int)(mapParam.xGridPixel/4 + bitmapMapInfo.getWidth()*pixelPow);
            rectDistHead.left = (int)(mapParam.xGridPixel/4);
            rectDistHead.right = (int)(mapParam.xGridPixel/4 + bitmapUnitInfo.getWidth()*pixelPow2);
        }else{ //放到右边
            rectDistInfo.left = (int)(mapParam.screenWidth - mapParam.xGridPixel/4 - bitmapMapInfo.getWidth()*pixelPow);
            rectDistInfo.right = (int)(mapParam.screenWidth - mapParam.xGridPixel/4);
            rectDistHead.left = (int)(mapParam.screenWidth - mapParam.xGridPixel/4 - bitmapUnitInfo.getWidth()*pixelPow2);
            rectDistHead.right = (int)(mapParam.screenWidth - mapParam.xGridPixel/4);
        }
        rectPaint.left = (int)(rectDistInfo.left + rectDistInfo.width()/5);
        rectPaint.right = (int)(rectDistInfo.right - rectDistInfo.width()/5);

        //画选中框
        if(!mapParam.isSelectUnit && mapParam.isSelect)
            canvas.drawPath(mapParam.selectPath, paintBitmap);
        //画人物头像
        if(mapParam.isSelectUnit){
            mapParam.isSelectUnit = false;
            //
            canvas.drawBitmap(bitmapUnitInfo, rectSrcHead, rectDistHead, paintBitmap);
            //填信息
        }
        //画地图信息
        if(mapParam.isSelect){
            mapParam.isSelect = false;
            //
            canvas.drawBitmap(bitmapMapInfo, rectSrcInfo, rectDistInfo, paintBitmap);
            //选中方格会提供一个序号,用来检索地图类型信息
            int mapInfoOrder = mapParam.mapInfo.grid
                    [mapParam.selectPoint[1]]
                    [mapParam.selectPoint[0]];
            //填地形信息
            canvas.drawText(
                    mapParam.mapInfo.name[mapInfoOrder],
                    rectDistInfo.left + rectDistInfo.width()/2,
                    rectDistInfo.top + rectDistInfo.height()/2 - pixelPow*4,
                    paintName);
            //地形参数
            paintParam.setColor(Color.BLUE);
            paintParam.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("DEF.",
                    rectPaint.left,
                    rectPaint.top + paintParam.getTextSize(),
                    paintParam);
            canvas.drawText("AVO.",
                    rectPaint.left,
                    rectPaint.bottom,
                    paintParam);
            //地形参数数据
            paintParam.setColor(Color.BLACK);
            paintParam.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(mapParam.mapInfo.defend[mapInfoOrder]),
                    rectPaint.right,
                    rectPaint.top + paintParam.getTextSize(),
                    paintParam);
            canvas.drawText(String.valueOf(mapParam.mapInfo.avoid[mapInfoOrder]),
                    rectPaint.right,
                    rectPaint.bottom,
                    paintParam);
        }
    }

}
