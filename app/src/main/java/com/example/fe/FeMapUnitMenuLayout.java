package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    地图上的人物菜单
 */
public class FeMapUnitMenuLayout extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public boolean checkHit(int type, float x, float y){
        if(type == MotionEvent.ACTION_UP){
            ;
        }
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeMapUnitMenuLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
    }
}
