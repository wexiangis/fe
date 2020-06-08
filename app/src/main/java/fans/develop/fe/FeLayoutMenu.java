package fans.develop.fe;

import android.content.Context;

/*
    地图上的系统菜单
 */
public class FeLayoutMenu extends FeLayoutParent {

    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMenu(Context context, FeSection.Callback callback) {
        super(context);
    }
}
