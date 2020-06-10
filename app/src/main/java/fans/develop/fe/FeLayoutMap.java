package fans.develop.fe;

import android.content.Context;

/*
    加载和管理地图及地形动画
 */
public class FeLayoutMap extends FeLayoutParent {

    private Context context;
    private FeSection.Callback callback;

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMap(Context context, FeSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
        //加载地图
        addView(new FeViewMap(context, callback));
    }

    /*
        加载背景,将遮挡住地图,用于战斗、对话剧情等
     */
    public void loadBackground(){
        ;
    }

}
