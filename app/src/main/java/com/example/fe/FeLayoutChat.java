package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的对话
 */
public class FeLayoutChat extends RelativeLayout {

    private FeSave feSave;
    private FeParamMap paramMap;

    public boolean checkHit(int type, float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutChat(Context context, FeSave save) {
        super(context);
        feSave = save;
        paramMap = feSave.feParamMap;
    }
}
