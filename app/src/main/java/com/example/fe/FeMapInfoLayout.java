package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    显示地图光标处的地形信息
 */
public class FeMapInfoLayout extends RelativeLayout {

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

    public FeMapInfoLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
        //
        addView(new FeMapInfoView(context, mapParam));
    }
}
