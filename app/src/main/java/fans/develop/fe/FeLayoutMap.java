package fans.develop.fe;

import android.content.Context;
import android.view.View;

/*
    加载和管理地图及地形动画
 */
public class FeLayoutMap extends FeLayout {

    private Context context;
    private FeLayoutSection.Callback callback;

    // 同时只显示一张地图
    private FeViewMap viewMap = null;
    // 同时只显示一张背景
    private View viewBackground = null;
    // 同时只显示一张地图背景
    // private FeViewMap viewMapBackground = null;

    public FeLayoutMap(Context context, FeLayoutSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    /* ---------- function ---------- */
    
    public boolean checkHit(float x, float y){
        return false;
    }
    
    /*
        刷新,一般在移动地图之后
     */
    public void refresh(){
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
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

    /*
        捕捉,保持指定人物或地点在地图显示的中心
        id = -1 释放
     */
    public void catchUnit(int id){
        ;
    }
    public void catchPoint(int xGrid, int yGrid){
        ;
    }

    /* ---------- abstract interface ---------- */

    public boolean onKeyBack(){
        return false;
    }
    public boolean onDestory(){
        //释放子view
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof FeView)
                ((FeView)v).onDestory();
        }
        return true;
    }
    public void onReload(){
        ;
    }
}
