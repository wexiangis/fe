package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的人物菜单
 */
public class FeMapUnitMenuLayout extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public FeMapUnitMenuLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
    }
}
