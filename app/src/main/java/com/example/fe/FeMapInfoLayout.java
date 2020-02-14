package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    显示地图光标处的地形信息
 */
public class FeMapInfoLayout extends RelativeLayout {

    private FeSave feSave;

    public FeMapInfoLayout(Context context, FeSave save) {
        super(context);
        feSave = save;
    }
}
