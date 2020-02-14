package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的系统菜单
 */
public class FeMapMenuLayout extends RelativeLayout {

    private FeSave feSave;

    public FeMapMenuLayout(Context context, FeSave save) {
        super(context);
        feSave = save;
    }
}
