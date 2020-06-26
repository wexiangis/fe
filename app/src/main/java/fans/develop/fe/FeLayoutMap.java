package fans.develop.fe;

import android.content.Context;
import android.view.View;

/*
    加载和管理地图及地形动画
 */
public class FeLayoutMap extends FeLayoutParent {

    private Context context;
    private FeLayoutSection.Callback callback;
    private View viewMap = null, viewBackground = null;

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMap(Context context, FeLayoutSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    /*
        加载地图
     */
    public void loadMap(int section){
        //只容存在一张地图
        if(viewMap != null)
            removeView(viewMap);
        //更换了地图,重新初始化参数
        callback.refreshSectionMap(section);
        //添加地图view
        viewMap = new FeViewMap(context, callback);
        addView(viewMap);
    }

    /*
        加载背景,将遮挡住地图,用于战斗、对话剧情等
     */
    public void loadBackground(){
        //只容存在一张背景
        if(viewBackground != null)
            removeView(viewBackground);
        //添加view
        viewBackground = new View(context);
        addView(viewBackground);
    }

    /* ---------- abstract interface ---------- */
    public boolean onKeyBack(){
        return false;
    }
    public boolean onDestory(){
        //释放子view
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof FeViewParent)
                ((FeViewParent)v).onDestory();
        }
        return true;
    }
    public void onReload(){
        ;
    }
}
