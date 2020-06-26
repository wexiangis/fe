package fans.develop.fe;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.Shader;

public class FeLayoutMark extends FeLayoutParent {

    private Context context;
    private FeLayoutSection.Callback callback;
    //着色器心跳启动标志
    private Boolean shaderHeartStartFlag = false;
    
    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMark(Context context, FeLayoutSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    /*
        显示特定人物的mark范围
     */
    public void showUnit(int id){
        if(!shaderHeartStartFlag)
            shaderHeartStart();
        //计算范围
        ;
        //显示范围
        addView(new FeViewMark(context, 1, 10, 10, callback));
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
        this.removeAllViews();
    }

    private void shaderHeartStart(){
        //只启动一次
        if(shaderHeartStartFlag)
            return;
        //初始化着色器列表
        callback.getSectionUnit().shaderR = new FeShader(
                new RectF(0, 0, callback.getSectionMap().xGridPixel, callback.getSectionMap().yGridPixel),
                (int)(callback.getSectionMap().xGridPixel/10), 1,
                20,
                new int[]{0x80FF8080, 0x80FF2020, 0x80FF8080},
                new float[] {0.25F, 0.5F, 7.5F },
                Shader.TileMode.REPEAT
        );
        callback.getSectionUnit().shaderG = new FeShader(
                new RectF(0, 0, callback.getSectionMap().xGridPixel, callback.getSectionMap().yGridPixel),
                (int)(callback.getSectionMap().xGridPixel/10), 1,
                20,
                new int[]{0x8060FF60, 0x8020FF20, 0x8060F60F},
                new float[] {0.25F, 0.5F, 7.5F },
                Shader.TileMode.REPEAT
        );
        callback.getSectionUnit().shaderB = new FeShader(
                new RectF(0, 0, callback.getSectionMap().xGridPixel, callback.getSectionMap().yGridPixel),
                (int)(callback.getSectionMap().xGridPixel/10), 1,
                20,
                new int[]{0x808080FF, 0x802020FF, 0x808080FF},
                new float[] {0.1F, 0.5F, 0.9F },
                Shader.TileMode.REPEAT
        );
        //引入心跳,让渐变色动起来
        callback.addHeartUnit(heartUnit);
        //只启动一次
        shaderHeartStartFlag = true;
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

    /* ---------- abstract interface ---------- */
    public boolean onKeyBack(){
        return false;
    }
    public boolean onDestory(){
        //没有启动就没有关闭
        if(!shaderHeartStartFlag)
            return true;
        //解除心跳注册
        callback.removeHeartUnit(heartUnit);
        return true;
    }
    public void onReload(){
        ;
    }
}
