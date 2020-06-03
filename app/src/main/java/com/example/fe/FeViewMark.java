package com.example.fe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/*
    带动态渐变色的标记方格
 */
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
        FeData.heart.addUnit(heartUnit);
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
        if(FeData.section.checkClickState(FeEvent.ON_MOVE))
            return;

        if(this.colorMode == 0)
            paint.setShader(FeData.paramUnit.getShaderB());
        else if(this.colorMode == 1)
            paint.setShader(FeData.paramUnit.getShaderR());
        else
            paint.setShader(FeData.paramUnit.getShaderG());

        canvas.drawPath(FeData.paramMap.selectSite.path, paint);
    }
}
