package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的系统菜单
 */
public class FeMapMenuLayout extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public FeMapMenuLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
    }
}
