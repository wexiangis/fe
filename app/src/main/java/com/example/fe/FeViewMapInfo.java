package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/*
    地图上的地图类型等信息框
 */
public class FeViewMapInfo extends View {

    private FeParamMap paramMap;

    //背景框图片
    private Bitmap bitmapInfo;

    //背景框图片源和输出位置
    private Rect rectSrcInfo, rectDistInfo;

    //打印信息的大致输出范围
    private Rect rectPaintInfo;

    //背景框图片,地图类型,参数 画笔
    private Paint paintBitmap, paintInfoName, paintInfoParam;
    //
    private float pixelPowInfo;
    private boolean drawInfo = false;

    public FeViewMapInfo(Context context) {
        super(context);
        paramMap = FeData.feParamMap;
        //
        bitmapInfo = FeData.feAssets.menu.getMapInfo();
        //
        pixelPowInfo = paramMap.yGridPixel * 2 / bitmapInfo.getHeight();
        //
        rectSrcInfo = new Rect(0, 0, bitmapInfo.getWidth(), bitmapInfo.getHeight());
        rectDistInfo = new Rect(
                (int) (paramMap.xGridPixel / 4),
                paramMap.screenHeight - (int) (paramMap.yGridPixel / 4 + bitmapInfo.getHeight() * pixelPowInfo),
                (int) (paramMap.xGridPixel / 4 + bitmapInfo.getWidth() * pixelPowInfo),
                paramMap.screenHeight - (int) (paramMap.yGridPixel / 4));
        //
        paintBitmap = new Paint();
        paintBitmap.setColor(0xE00000FF);//半透明
        //
        paintInfoName = new Paint();
        paintInfoName.setColor(Color.WHITE);
        paintInfoName.setTextSize(rectDistInfo.width() / 5);
        paintInfoName.setTextAlign(Paint.Align.CENTER);
//        paintInfoName.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintInfoName.setStrokeWidth(2);
//        paintInfoName.setAntiAlias(true);
//        paintInfoName.setStrokeCap(Paint.Cap.ROUND);
        paintInfoName.setTypeface(Typeface.DEFAULT_BOLD);
        //
        paintInfoParam = new Paint();
        paintInfoParam.setTextSize(rectDistInfo.width() / 7);
//        paintInfoParam.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintInfoParam.setStrokeWidth(2);
//        paintInfoParam.setAntiAlias(true);
//        paintInfoParam.setStrokeCap(Paint.Cap.ROUND);
        paintInfoParam.setTypeface(Typeface.DEFAULT_BOLD);
        //
        rectPaintInfo = new Rect(
                (int) (rectDistInfo.left + rectDistInfo.width() / 5),
                (int) (rectDistInfo.bottom - rectDistInfo.height() / 2 + pixelPowInfo * 4),
                (int) (rectDistInfo.right - rectDistInfo.width() / 5),
                (int) (rectDistInfo.bottom - rectDistInfo.height() / 2 + pixelPowInfo * 4 + paintInfoParam.getTextSize() * 2 + pixelPowInfo * 2));
    }

    public boolean checkHit(float x, float y){
        if(drawInfo && rectDistInfo.contains((int)x, (int)y))
            return true;
        return false;
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //移动中不绘制
        if(paramMap.checkSelectType(FeParamMap.SELECT_MOVE)){
            drawInfo = false;
            return;
        }

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//抗锯齿

        //图像位置自动调整
        if(paramMap.selectMap.selectRect.right > paramMap.screenWidth/2){ //放到左边
            rectDistInfo.left = (int)(paramMap.xGridPixel/4);
            rectDistInfo.right = (int)(paramMap.xGridPixel/4 + bitmapInfo.getWidth()*pixelPowInfo);
        }else{ //放到右边
            rectDistInfo.left = (int)(paramMap.screenWidth - paramMap.xGridPixel/4 - bitmapInfo.getWidth()*pixelPowInfo);
            rectDistInfo.right = (int)(paramMap.screenWidth - paramMap.xGridPixel/4);
        }
        rectPaintInfo.left = (int)(rectDistInfo.left + rectDistInfo.width()/5);
        rectPaintInfo.right = (int)(rectDistInfo.right - rectDistInfo.width()/5);

        //画地图信息
        if(paramMap.checkSelectType(FeParamMap.SELECT_MAP)){
            drawInfo = true;
            canvas.drawBitmap(bitmapInfo, rectSrcInfo, rectDistInfo, paintBitmap);
            //选中方格会提供一个序号,用来检索地图类型信息
            int mapInfoOrder = paramMap.map.grid
                    [paramMap.selectMap.selectPoint[1]]
                    [paramMap.selectMap.selectPoint[0]];
            //填地形信息
            canvas.drawText(
                    paramMap.map.name[mapInfoOrder],
                    rectDistInfo.left + rectDistInfo.width()/2,
                    rectDistInfo.top + rectDistInfo.height()/2 - pixelPowInfo*1,
                    paintInfoName);
            //地形参数
            paintInfoParam.setColor(Color.BLUE);
            paintInfoParam.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("DEF.",
                    rectPaintInfo.left,
                    rectPaintInfo.top + paintInfoParam.getTextSize(),
                    paintInfoParam);
            canvas.drawText("AVO.",
                    rectPaintInfo.left,
                    rectPaintInfo.bottom,
                    paintInfoParam);
            //地形参数数据
            paintInfoParam.setColor(Color.BLACK);
            paintInfoParam.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.valueOf(paramMap.map.defend[mapInfoOrder]),
                    rectPaintInfo.right,
                    rectPaintInfo.top + paintInfoParam.getTextSize(),
                    paintInfoParam);
            canvas.drawText(String.valueOf(paramMap.map.avoid[mapInfoOrder]),
                    rectPaintInfo.right,
                    rectPaintInfo.bottom,
                    paintInfoParam);
        }else
            drawInfo = false;
    }

}
