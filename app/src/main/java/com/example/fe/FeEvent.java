package com.example.fe;

import android.app.Activity;

public class FeEvent {

    public FeSave feSave;

    public FeEvent(FeSave save)
    {
        feSave = save;
        //加载主界面
        loadMainTheme();
        //加载地图
//        loadSection(0);
    }

    //加载主界面
    public void loadMainTheme(){
        feSave.activity.setContentView(new FeMainTheme(feSave.activity, feSave));
    }

    //加载章节片头
    public void loadSectionTitles(int count) {
    }

    //加载章节大地图
    public void loadSection(int count) {
        feSave.activity.setContentView(new FeSectionMap(feSave.activity, feSave));
    }

    //加载章节对话
    public void loadSectionConversation(int count) {
        ;
    }


}
