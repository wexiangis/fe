package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    地图上的人物菜单
 */
public class FeLayoutUnitMenu extends RelativeLayout {

    private FeSave feSave;
    private FeParamMap paramMap;

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

    public FeLayoutUnitMenu(Context context, FeSave save) {
        super(context);
        feSave = save;
        paramMap = feSave.feParamMap;
    }
}
