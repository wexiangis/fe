package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    加载和管理地图及地形动画
 */
public class FeMapLayout extends RelativeLayout {

    private FeSave feSave;
    public FeMapParam mapParam;

    public FeMapLayout(Context context, FeSave save, int section) {
        super(context);
        feSave = save;
        mapParam = new FeMapParam(feSave.activity, section);
        //
        loadView();
    }

    private void loadView(){
        addView(new FeMap(feSave.activity, feSave.feHeart, mapParam));
    }

}
