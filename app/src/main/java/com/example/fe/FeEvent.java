package com.example.fe;

import android.app.Activity;

public class FeEvent {

    public Activity act;
    public FeSave fedata;

    public FeEvent(Activity activity, FeSave feSave)
    {
        act = activity;
        fedata = feSave;
        //加载主界面
//        loadMainTheme();
        //
        loadSection(0);
    }

    //加载主界面
    public void loadMainTheme(){
        act.setContentView(new FeMainTheme(act, this));
    }

    //加载章节片头
    public void loadSectionTitles(int count) {
    }

    //加载章节大地图
    public void loadSection(int count) {
        act.setContentView(new FeSectionMap(act, this));
//        act.setContentView(new FeMapView(act, R.drawable.map_000_25x15, 25, 15));
    }

    //加载章节对话
    public void loadSectionConversation(int count) {
        ;
    }


}
