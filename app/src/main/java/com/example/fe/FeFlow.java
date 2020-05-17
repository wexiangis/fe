package com.example.fe;

import android.view.ViewGroup;

/*
    事件管理层: 用于切换 主界面,章节内容,存档界面,等等等
 */
public class FeFlow {

    public void stop(){
        if(FeData.feLayoutMainTheme != null &&
            FeData.feLayoutMainTheme.getParent() != null)
            ((ViewGroup)FeData.feLayoutMainTheme.getParent()).removeAllViews();
        else if(FeData.feLayoutSection != null &&
            FeData.feLayoutSection.getParent() != null)
            ((ViewGroup)FeData.feLayoutSection.getParent()).removeAllViews();
    }

    public void start(){
        //加载主界面
//        loadMainTheme();
        //加载地图
        loadSection(0);
    }

    //加载主界面
    public void loadMainTheme(){
        if(FeData.feLayoutMainTheme == null)
            FeData.feLayoutMainTheme = new FeLayoutMainTheme(FeData.context);
        FeData.activity.setContentView(FeData.feLayoutMainTheme);
    }

    //加载章节片头
    public void loadSectionTitles(int count) {
    }

    //加载章节大地图
    public void loadSection(int section) {
        if(FeData.feLayoutSection == null)
            FeData.feLayoutSection = new FeLayoutSection(FeData.context, section);
        FeData.activity.setContentView(FeData.feLayoutSection);
    }

    //加载章节对话
    public void loadSectionConversation(int count) {
        ;
    }


}
