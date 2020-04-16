package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    加载和管理地图及地形动画
 */
public class FeLayoutMap extends RelativeLayout {

    private FeSave feSave;

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMap(Context context, FeSave save, int section) {
        super(context);
        feSave = save;
        //初始化和地图相关的所有参数
        feSave.feParamMap = new FeParamMap(feSave.activity, section);
        //
        loadView();
    }

    private void loadView(){
        addView(new FeViewMap(feSave.activity, feSave.feHeart, feSave.feParamMap));
    }

}