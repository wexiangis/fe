package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的系统菜单
 */
public class FeLayoutMenu extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public boolean checkHit(int type, float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMenu(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
    }
}
