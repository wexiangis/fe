package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    加载和管理地图及地形动画
 */
public class FeLayoutMap extends FeLayoutParent {

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMap(Context context, int section) {
        super(context);
        //初始化和地图相关的所有参数
        FeData.paramMap = new FeParamMap(section);
        //
        loadView();
    }

    private void loadView(){
        addView(new FeViewMap(FeData.context));
    }

}
