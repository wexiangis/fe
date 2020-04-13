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
    private FeMapParam mapParam;

    private Rect rectSrcHead, rectDistHead;
    private Bitmap bitmapHead;
    private Paint paintBitmap, paintHead, paintHeadParam;
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

    public FeViewUnitInfo(Context context, FeMapParam feMapParam){
        super(context);
        _context = context;
        mapParam = feMapParam;
        //
        bitmapHead = getAssetsBitmap("/assets/menu/map/header.png");
        //
        pixelPowHead = mapParam.yGridPixel*2/bitmapHead.getHeight();
        //
        rectSrcHead = new Rect(0, 0, bitmapHead.getWidth(), bitmapHead.getHeight());
        rectDistHead = new Rect(
                (int)(mapParam.xGridPixel/4),
                (int)(mapParam.yGridPixel/4),
                (int)(mapParam.xGridPixel/4 + bitmapHead.getWidth()*pixelPowHead),
                (int)(mapParam.yGridPixel/4 + bitmapHead.getHeight()*pixelPowHead));
        //
        paintBitmap = new Paint();
        paintBitmap.setColor(0xE00000FF);//半透明
        //
        paintHead = new Paint();
        paintHead.setColor(0xE00000FF);//半透明
        //
        paintHeadParam = new Paint();
        paintHeadParam.setTextSize(rectDistHead.height()/8);
//        paintHeadParam.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintHeadParam.setStrokeWidth(2);
//        paintHeadParam.setAntiAlias(true);
//        paintHeadParam.setStrokeCap(Paint.Cap.ROUND);
        paintHeadParam.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public boolean checkHit(float x, float y){
        if(drawHead && rectDistHead.contains((int)x, (int)y))
            return true;
        return false;
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //移动中不绘制
        if(mapParam.checkSelectType(FeMapParam.SELECT_MOVE)){
            drawHead = false;
            return;
        }

        //图像位置自动调整
        if(mapParam.selectUnit.selectRect.right > mapParam.screenWidth/2){ //放到左边
            rectDistHead.left = (int)(mapParam.xGridPixel/4);
            rectDistHead.right = (int)(mapParam.xGridPixel/4 + bitmapHead.getWidth()*pixelPowHead);
        }else{ //放到右边
            rectDistHead.left = (int)(mapParam.screenWidth - mapParam.xGridPixel/4 - bitmapHead.getWidth()*pixelPowHead);
            rectDistHead.right = (int)(mapParam.screenWidth - mapParam.xGridPixel/4);
        }

        //画人物头像
        if(!mapParam.checkSelectType(FeMapParam.SELECT_UNIT) ||
            mapParam.selectUnit.selectRect.left > mapParam.screenWidth ||
            mapParam.selectUnit.selectRect.right < 0 ||
            mapParam.selectUnit.selectRect.top > mapParam.screenHeight ||
            mapParam.selectUnit.selectRect.bottom < 0){
            drawHead = false;
        }else {
            drawHead = true;
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));//抗锯齿
            canvas.drawBitmap(bitmapHead, rectSrcHead, rectDistHead, paintBitmap);
            //填信息
        }
    }

}
