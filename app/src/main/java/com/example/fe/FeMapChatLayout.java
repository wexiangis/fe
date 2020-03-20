package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的对话
 */
public class FeMapChatLayout extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public FeMapChatLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;
    }
}
