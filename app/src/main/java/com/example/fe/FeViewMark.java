package com.example.fe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

public class FeViewMark extends View {

    private Paint paint;

    public FeViewMark(Context context){
        super(context);
        //画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        //引入心跳
        FeData.feHeart.addUnit(heartUnit);
    }

    private int linearGradientCount = 0;
    //动画心跳回调
    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            FeViewMark.this.linearGradientCount = count%(FeData.feParamMap.width/20)*20;
            FeViewMark.this.invalidate();
        }
    });

    //绘图回调
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int[] mColors = {0x808080FF, 0x800000FF, 0x808080FF};
        float[] loaction = new float[] {0.25F, 0.5F, 7.5F };
        LinearGradient linearGradient = new LinearGradient(
                linearGradientCount,
                0,
                FeData.feParamMap.xGridPixel + linearGradientCount,
                FeData.feParamMap.yGridPixel,
                mColors, loaction,
                Shader.TileMode.REPEAT);

//        LinearGradient linearGradient = new LinearGradient(
//                linearGradientCount,
//                0,
//                FeData.feParamMap.xGridPixel + linearGradientCount,
//                FeData.feParamMap.yGridPixel,
//                0x800000FF, 0x808080FF,
//                Shader.TileMode.REPEAT);

        paint.setShader(linearGradient);

        canvas.drawPath(FeData.feParamMap.selectSite.path, paint);
//        canvas.drawRect(FeData.feParamMap.selectSite.rect, paint);
    }
}
