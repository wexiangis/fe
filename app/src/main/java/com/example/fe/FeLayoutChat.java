package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的对话
 */
public class FeLayoutChat extends FeLayoutParent {

    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutChat(Context context) {
        super(context);
    }
}
