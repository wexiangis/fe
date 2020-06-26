package fans.develop.fe;

import android.content.Context;
import android.view.View;

/*
    地图上的系统菜单
 */
public class FeLayoutSysMenu extends FeLayoutParent {

    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutSysMenu(Context context, FeLayoutSection.Callback callback) {
        super(context);
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
