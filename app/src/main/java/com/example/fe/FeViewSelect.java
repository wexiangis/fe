package com.example.fe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/*
    光标选中框
 */
public class FeViewSelect extends View {

    private FeParamMap paramMap;

    //选中框图片
    private Bitmap bitmapSelect;
    //
    private Rect rectSrcSelect, rectDistSelect;
    //
    private int bitmapSelectFrameHeight;
    //
    private Paint paintSelct;

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

    public FeViewSelect(Context context){
        super(context);
        paramMap = FeData.feParamMap;
        //
        bitmapSelect = getAssetsBitmap("/assets/menu/map/select.png");
        bitmapSelectFrameHeight = bitmapSelect.getWidth();
        rectDistSelect = new Rect(0, 0, 0, 0);
        rectSrcSelect = new Rect(0, 0, bitmapSelect.getWidth(), bitmapSelect.getHeight());
        //
        paintSelct = new Paint();
        paintSelct.setColor(Color.BLUE);
        //引入心跳
        FeData.feHeart.addUnit(heartUnit);
    }

    private boolean needRefresh;
    private int heartCount = 0;
    //动画心跳回调
    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            //移动框图
            rectSrcSelect.left = 0;
            rectSrcSelect.right = bitmapSelect.getWidth();
            //
            needRefresh = true;
            //记数0,1,2,3,
            if(heartCount == 1)
                rectSrcSelect.top = bitmapSelectFrameHeight * 3;
            else if(heartCount == 0 || heartCount == 2)
                rectSrcSelect.top = bitmapSelectFrameHeight * 2;
            else {
                if(rectSrcSelect.top == bitmapSelectFrameHeight)
                    needRefresh = false;
                else
                    rectSrcSelect.top = bitmapSelectFrameHeight;
            }
            if(++heartCount > 6)
                heartCount = 0;
            //
            rectSrcSelect.bottom = rectSrcSelect.top + bitmapSelectFrameHeight;
            //调用一次onDrow
            if(needRefresh)
                FeViewSelect.this.invalidate();
        }
    });

    //绘图回调
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //移动中不绘制
        if(paramMap.checkSelectType(FeParamMap.SELECT_MOVE))
            return;

        //画选中框
        if(paramMap.checkSelectType(FeParamMap.SELECT_MAP) &&
                !paramMap.checkSelectType(FeParamMap.SELECT_UNIT)) {
            //计算输出位置
            rectDistSelect.left = paramMap.selectMap.selectRect.left - paramMap.selectMap.selectRect.width()/4;
            rectDistSelect.right = paramMap.selectMap.selectRect.right + paramMap.selectMap.selectRect.width()/4;
            rectDistSelect.top = paramMap.selectMap.selectRect.top - paramMap.selectMap.selectRect.height()/4;
            rectDistSelect.bottom = paramMap.selectMap.selectRect.bottom + paramMap.selectMap.selectRect.height()/4;
            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            //画图
//            canvas.drawPath(paramMap.selectMap.selectPath, paintSelct);
            canvas.drawBitmap(bitmapSelect, rectSrcSelect, rectDistSelect, paintSelct);
        }
        else if(paramMap.checkSelectType(FeParamMap.SELECT_UNIT)){
            //计算输出位置
            rectDistSelect.left = paramMap.selectUnit.selectRect.left - paramMap.selectUnit.selectRect.width()/4;
            rectDistSelect.right = paramMap.selectUnit.selectRect.right + paramMap.selectUnit.selectRect.width()/4;
            rectDistSelect.top = paramMap.selectUnit.selectRect.top - paramMap.selectUnit.selectRect.height()/2;
            rectDistSelect.bottom = paramMap.selectUnit.selectRect.bottom;
            //使用大框
            rectSrcSelect.top = 0;
            rectSrcSelect.bottom = bitmapSelectFrameHeight;
            //抗锯齿
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            //画图
//            canvas.drawPath(paramMap.selectMap.selectPath, paintSelct);
            canvas.drawBitmap(bitmapSelect, rectSrcSelect, rectDistSelect, paintSelct);
        }
    }
}
