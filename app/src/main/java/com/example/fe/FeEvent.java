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
        loadMainTheme();
    }

    //加载主界面
    public void loadMainTheme(){
        act.setContentView(new FeMainTheme(act, this));
    }

    //加载章节片头
    public void loadSectionTitles(int count) {
        ;
    }

    //加载章节大地图
    public void loadSection(int count) {
        ;
    }

    //加载章节对话
    public void loadSectionConversation(int count) {
        ;
    }


}
