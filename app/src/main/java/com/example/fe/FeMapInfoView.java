package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

public class FeMapInfoView extends View {

    private Context _context;
    private FeMapParam mapParam;

    private Rect rectSrcInfo, rectDistInfo;
    private Rect rectPaintInfo;
    private Bitmap bitmapInfo;
    private Paint paintBitmap, paintInfoName, paintInfoParam;
    private float pixelPowInfo;
    private boolean drawInfo = false;

    public FeMapInfoView(Context context, FeMapParam feMapParam){
        super(context);
        _context = context;
        mapParam = feMapParam;
        //
        bitmapInfo = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapinfo);
        //
        pixelPowInfo = mapParam.yGridPixel*2/bitmapInfo.getHeight();
        //
        rectSrcInfo = new Rect(0, 0, bitmapInfo.getWidth(), bitmapInfo.getHeight());
        rectDistInfo = new Rect(
                (int)(mapParam.xGridPixel/4),
                mapParam.screenHeight - (int)(mapParam.yGridPixel/4 + bitmapInfo.getHeight()*pixelPowInfo),
                (int)(mapParam.xGridPixel/4 + bitmapInfo.getWidth()*pixelPowInfo),
                mapParam.screenHeight - (int)(mapParam.yGridPixel/4));
        //
        paintBitmap = new Paint();
        paintBitmap.setColor(0xE00000FF);//半透明
        //
        paintInfoName = new Paint();
        paintInfoName.setColor(Color.WHITE);
        paintInfoName.setTextSize(rectDistInfo.width()/5);
        paintInfoName.setTextAlign(Paint.Align.CENTER);
//        paintInfoName.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintInfoName.setStrokeWidth(2);
//        paintInfoName.setAntiAlias(true);
//        paintInfoName.setStrokeCap(Paint.Cap.ROUND);
        paintInfoName.setTypeface(Typeface.DEFAULT_BOLD);
        //
        paintInfoParam = new Paint();
        paintInfoParam.setTextSize(rectDistInfo.width()/7);
//        paintInfoParam.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintInfoParam.setStrokeWidth(2);
//        paintInfoParam.setAntiAlias(true);
//        paintInfoParam.setStrokeCap(Paint.Cap.ROUND);
        paintInfoParam.setTypeface(Typeface.DEFAULT_BOLD);
        //
        rectPaintInfo = new Rect(
                (int)(rectDistInfo.left + rectDistInfo.width()/5),
                (int)(rectDistInfo.bottom - rectDistInfo.height()/2 + pixelPowInfo*15),
                (int)(rectDistInfo.right - rectDistInfo.width()/5),
                (int)(rectDistInfo.bottom - rectDistInfo.height()/2 + pixelPowInfo*15 + paintInfoParam.getTextSize()*2 + pixelPowInfo*5));
    }

    public boolean checkHit(float x, float y){
        if(drawInfo && rectDistInfo.contains((int)x, (int)y))
            return true;
        return false;
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //移动中不绘制
        if(mapParam.checkSelectType(FeMapParam.SELECT_MOVE)){
            drawInfo = false;
            return;
        }

        //图像位置自动调整
        if(mapParam.selectMap.selectRect.right > mapParam.screenWidth/2){ //放到左边
            rectDistInfo.left = (int)(mapParam.xGridPixel/4);
            rectDistInfo.right = (int)(mapParam.xGridPixel/4 + bitmapInfo.getWidth()*pixelPowInfo);
        }else{ //放到右边
            rectDistInfo.left = (int)(mapParam.screenWidth - mapParam.xGridPixel/4 - bitmapInfo.getWidth()*pixelPowInfo);
            rectDistInfo.right = (int)(mapParam.screenWidth - mapParam.xGridPixel/4);
        }
        rectPaintInfo.left = (int)(rectDistInfo.left + rectDistInfo.width()/5);
        rectPaintInfo.right = (int)(rectDistInfo.right - rectDistInfo.width()/5);

        //画选中框
        if(mapParam.checkSelectType(FeMapParam.SELECT_MAP) &&
            !mapParam.checkSelectType(FeMapParam.SELECT_UNIT))
            canvas.drawPath(mapParam.selectMap.selectPath, paintBitmap);
        //画地图信息
        if(mapParam.checkSelectType(FeMapParam.SELECT_MAP)){
            drawInfo = true;
            canvas.drawBitmap(bitmapInfo, rectSrcInfo, rectDistInfo, paintBitmap);
            //选中方格会提供一个序号,用来检索地图类型信息
            int mapInfoOrder = mapParam.mapInfo.grid
                    [mapParam.selectMap.selectPoint[1]]
                    [mapParam.selectMap.selectPoint[0]];
            //填地形信息
            canvas.drawText(
                    mapParam.mapInfo.name[mapInfoOrder],
                    rectDistInfo.left + rectDistInfo.width()/2,
                    rectDistInfo.top + rectDistInfo.height()/2 - pixelPowInfo*4,
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
            canvas.drawText(String.valueOf(mapParam.mapInfo.defend[mapInfoOrder]),
                    rectPaintInfo.right,
                    rectPaintInfo.top + paintInfoParam.getTextSize(),
                    paintInfoParam);
            canvas.drawText(String.valueOf(mapParam.mapInfo.avoid[mapInfoOrder]),
                    rectPaintInfo.right,
                    rectPaintInfo.bottom,
                    paintInfoParam);
        }else
            drawInfo = false;
    }

}
