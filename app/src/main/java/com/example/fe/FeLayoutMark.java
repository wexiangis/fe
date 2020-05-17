package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

public class FeLayoutMark extends RelativeLayout {

    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMark(Context context) {
        super(context);
    }
}
