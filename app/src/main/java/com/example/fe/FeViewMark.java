package com.example.fe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class FeViewMark extends View {

    private Paint paint;
    private int colorMode;

    /*
        colorMode: 0/蓝色 1/红色 2/绿色
     */
    public FeViewMark(Context context, int colorMode){
        super(context);
        this.colorMode = colorMode;
        //画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        //引入心跳
        FeData.feHeart.addUnit(heartUnit);
    }

    //动画心跳回调
    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
        FeViewMark.this.invalidate();
        }
    });

    //绘图回调
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //移动中不绘制
        if(FeData.feEvent.checkSelectType(FeEvent.EVENT_MOVE))
            return;

        if(this.colorMode == 0)
            paint.setShader(FeData.feParamUnit.getShaderB());
        else if(this.colorMode == 1)
            paint.setShader(FeData.feParamUnit.getShaderR());
        else
            paint.setShader(FeData.feParamUnit.getShaderG());

        canvas.drawPath(FeData.feParamMap.selectSite.path, paint);
    }
}