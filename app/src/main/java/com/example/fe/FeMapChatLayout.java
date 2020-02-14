package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图上的对话
 */
public class FeMapChatLayout extends RelativeLayout {

    private FeSave feSave;

    public FeMapChatLayout(Context context, FeSave save) {
        super(context);
        feSave = save;
    }
}
