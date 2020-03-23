package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的对话
 */
public class FeMapChatLayout extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public boolean checkHit(int type, float x, float y, boolean isMove){
        if(!isMove)
        {
            ;
        }
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeMapChatLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
    }
}
