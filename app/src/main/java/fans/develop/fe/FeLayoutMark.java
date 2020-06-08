package fans.develop.fe;

import android.content.Context;
import android.graphics.RectF;
import android.widget.RelativeLayout;
import android.graphics.Shader;

public class FeLayoutMark extends FeLayoutParent {

    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMark(Context context) {
        super(context);
        //初始化着色器列表
        FeData.section.sectionUnit.shaderR = new FeShader(
            new RectF(0, 0, FeData.section.sectionMap.xGridPixel, FeData.section.sectionMap.yGridPixel),
            (int)(FeData.section.sectionMap.xGridPixel/10), 1,
            20,
            new int[]{0x80FF8080, 0x80FF2020, 0x80FF8080},
            new float[] {0.25F, 0.5F, 7.5F },
            Shader.TileMode.REPEAT
        );
        FeData.section.sectionUnit.shaderG = new FeShader(
            new RectF(0, 0, FeData.section.sectionMap.xGridPixel, FeData.section.sectionMap.yGridPixel),
            (int)(FeData.section.sectionMap.xGridPixel/10), 1,
            20,
            new int[]{0x8060FF60, 0x8020FF20, 0x8060F60F},
            new float[] {0.25F, 0.5F, 7.5F },
            Shader.TileMode.REPEAT
        );
        FeData.section.sectionUnit.shaderB = new FeShader(
            new RectF(0, 0, FeData.section.sectionMap.xGridPixel, FeData.section.sectionMap.yGridPixel),
            (int)(FeData.section.sectionMap.xGridPixel/10), 1,
            20,
            new int[]{0x808080FF, 0x802020FF, 0x808080FF},
            new float[] {0.1F, 0.5F, 0.9F },
            Shader.TileMode.REPEAT
        );
        //
        addView(new FeViewMark(context, 1));
        //引入心跳,让渐变色动起来
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
            //让渐变色动起来
            if(FeData.section.sectionUnit.shaderCount + 1 < FeData.section.sectionUnit.shaderR.xCount())
                FeData.section.sectionUnit.shaderCount += 1;
            else
                FeData.section.sectionUnit.shaderCount = 0;
        }
    });
}
