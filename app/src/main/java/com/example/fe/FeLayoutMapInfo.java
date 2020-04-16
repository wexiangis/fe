package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    显示地图光标处的地形信息
 */
public class FeLayoutMapInfo extends RelativeLayout {

    private FeSave feSave;
    private FeParamMap paramMap;

    public boolean checkHit(int type, float x, float y){
        if(type == MotionEvent.ACTION_UP){
            refresh();
        }
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMapInfo(Context context, FeSave save) {
        super(context);
        feSave = save;
        paramMap = feSave.feParamMap;
        //
        addView(new FeViewMapInfo(context, paramMap));
        addView(new FeViewUnitInfo(context, paramMap));
    }
}
