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

public class FeViewUnitInfo extends View {

    private Context _context;
    private FeParamMap paramMap;

    //头像背景框图片源位置和输出位置
    private Rect rectSrcHeadBg, rectDistHeadBg;
    //头像图片,头像背景框
    private Bitmap bitmapHead, bitmapHeadBg;
    //头像背景框,头像,参数文字画笔
    private Paint paintHeadBg, paintHead, paintParam;
    //
    private float pixelPowHead;
    private boolean drawHead = false;

    //
    public Bitmap getAssetsBitmap(String path)
    {
        Bitmap ret = null;
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if(is != null){
                ret = BitmapFactory.decodeStream(is);
                is.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("getAssetsBitmap: ", "not found");
        } catch (IOException e) {
            Log.d("getAssetsBitmap: ", e.getMessage());
        }
        return ret;
    }

    public FeViewUnitInfo(Context context, FeParamMap feParamMap){
        super(context);
        _context = context;
        paramMap = feParamMap;
        //
        bitmapHeadBg = getAssetsBitmap("/assets/menu/map/header.png");
        //
        pixelPowHead = paramMap.yGridPixel*2/bitmapHeadBg.getHeight();
        //
        rectSrcHeadBg = new Rect(0, 0, bitmapHeadBg.getWidth(), bitmapHeadBg.getHeight());
        rectDistHeadBg = new Rect(
                (int)(paramMap.xGridPixel/4),
                (int)(paramMap.yGridPixel/4),
                (int)(paramMap.xGridPixel/4 + bitmapHeadBg.getWidth()*pixelPowHead),
                (int)(paramMap.yGridPixel/4 + bitmapHeadBg.getHeight()*pixelPowHead));
        //
        paintHeadBg = new Paint();
        paintHeadBg.setColor(0xE00000FF);//半透明
        //
        paintHead = new Paint();
        paintHead.setColor(0xE00000FF);//半透明
        //
        paintParam = new Paint();
        paintParam.setTextSize(rectDistHeadBg.height()/8);
//        paintParam.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintParam.setStrokeWidth(2);
//        paintParam.setAntiAlias(true);
//        paintParam.setStrokeCap(Paint.Cap.ROUND);
        paintParam.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public boolean checkHit(float x, float y){
        if(drawHead && rectDistHeadBg.contains((int)x, (int)y))
            return true;
        return false;
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //移动中不绘制
        if(paramMap.checkSelectType(FeParamMap.SELECT_MOVE)){
            drawHead = false;
            return;
        }

        //图像位置自动调整
        if(paramMap.selectUnit.selectRect.right > paramMap.screenWidth/2){ //放到左边
            rectDistHeadBg.left = (int)(paramMap.xGridPixel/4);
            rectDistHeadBg.right = (int)(paramMap.xGridPixel/4 + bitmapHeadBg.getWidth()*pixelPowHead);
        }else{ //放到右边
            rectDistHeadBg.left = (int)(paramMap.screenWidth - paramMap.xGridPixel/4 - bitmapHeadBg.getWidth()*pixelPowHead);
            rectDistHeadBg.right = (int)(paramMap.screenWidth - paramMap.xGridPixel/4);
        }

        //画人物头像
        if(!paramMap.checkSelectType(FeParamMap.SELECT_UNIT) ||
            paramMap.selectUnit.selectRect.left > paramMap.screenWidth ||
            paramMap.selectUnit.selectRect.right < 0 ||
            paramMap.selectUnit.selectRect.top > paramMap.screenHeight ||
            paramMap.selectUnit.selectRect.bottom < 0){
            drawHead = false;
        }else {
            drawHead = true;
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//抗锯齿
            canvas.drawBitmap(bitmapHeadBg, rectSrcHeadBg, rectDistHeadBg, paintHeadBg);
            //填信息
        }
    }

}