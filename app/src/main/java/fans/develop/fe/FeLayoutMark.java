package fans.develop.fe;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.Shader;

public class FeLayoutMark extends FeLayoutParent {

    private Context context;
    private FeSection.Callback callback;
    
    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMark(Context context, FeSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
        //初始化着色器列表
        callback.getSectionUnit().shaderR = new FeShader(
            new RectF(0, 0, FeData.section.sectionMap.xGridPixel, FeData.section.sectionMap.yGridPixel),
            (int)(FeData.section.sectionMap.xGridPixel/10), 1,
            20,
            new int[]{0x80FF8080, 0x80FF2020, 0x80FF8080},
            new float[] {0.25F, 0.5F, 7.5F },
            Shader.TileMode.REPEAT
        );
        callback.getSectionUnit().shaderG = new FeShader(
            new RectF(0, 0, FeData.section.sectionMap.xGridPixel, FeData.section.sectionMap.yGridPixel),
            (int)(FeData.section.sectionMap.xGridPixel/10), 1,
            20,
            new int[]{0x8060FF60, 0x8020FF20, 0x8060F60F},
            new float[] {0.25F, 0.5F, 7.5F },
            Shader.TileMode.REPEAT
        );
        callback.getSectionUnit().shaderB = new FeShader(
            new RectF(0, 0, FeData.section.sectionMap.xGridPixel, FeData.section.sectionMap.yGridPixel),
            (int)(FeData.section.sectionMap.xGridPixel/10), 1,
            20,
            new int[]{0x808080FF, 0x802020FF, 0x808080FF},
            new float[] {0.1F, 0.5F, 0.9F },
            Shader.TileMode.REPEAT
        );
        //引入心跳,让渐变色动起来
        FeData.addHeartUnit(heartUnit);
    }

    /*
        显示特定人物的mark范围
     */
    public void showUnit(int id){
        addView(new FeViewMark(context, 1, callback));
    }

    /*
        关闭特定人物的mark范围
     */
    public void hideUnit(int id){
        ;
    }

    /*
        关闭全部人物的mark范围
     */
    public void hideAllUnit(){
        this.removeAllView();
    }

    //删除之前需自行 removeView()
    public void delete(){
        //解除心跳注册
        FeData.removeHeartUnit(heartUnit);
    }
    
    //动画心跳回调
    private FeHeartUnit heartUnit = new FeHeartUnit(FeHeart.TYPE_FRAME_HEART, new FeHeartUnit.TimeOutTask(){
        public void run(int count){
            //让渐变色动起来
            if(callback.getSectionUnit().shaderCount + 1 < callback.getSectionUnit().shaderR.xCount())
                callback.getSectionUnit().shaderCount += 1;
            else
                callback.getSectionUnit().shaderCount = 0;
        }
    });
}
