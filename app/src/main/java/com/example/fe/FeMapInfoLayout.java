package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    显示地图光标处的地形信息
 */
public class FeMapInfoLayout extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public FeMapInfoLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
    }
}
