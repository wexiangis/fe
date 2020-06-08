package fans.develop.fe;

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
        FeData.addHeartUnit(heartUnit);
    }

    //删除人物,之后需自行 removeView()
    public void delete(){
        //解除心跳注册
        FeData.removeHeartUnit(heartUnit);
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
        if(FeData.section.checkClickState(FeSection.ON_MOVE))
            return;

        if(this.colorMode == 0)
            paint.setShader(FeData.section.sectionUnit.getShaderB());
        else if(this.colorMode == 1)
            paint.setShader(FeData.section.sectionUnit.getShaderR());
        else
            paint.setShader(FeData.section.sectionUnit.getShaderG());

        canvas.drawPath(FeData.section.sectionMap.selectSite.path, paint);
    }
}
