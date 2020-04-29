package com.example.fe;

import android.app.Activity;

/*
    事件管理层: 用于切换 主界面,章节内容,存档界面,等等等
 */
public class FeEvent {

    public FeEvent()
    {
        //加载主界面
//        loadMainTheme();
        //加载地图
        loadSection(99);

    }

    //加载主界面
    public void loadMainTheme(){
        FeData.getActivity().setContentView(new FeLayoutMainTheme(FeData.getContext()));
    }

    //加载章节片头
    public void loadSectionTitles(int count) {
    }

    //加载章节大地图
    public void loadSection(int section) {
        FeData.setFeLayoutSection(new FeLayoutSection(FeData.getContext(), section));
        FeData.getActivity().setContentView(FeData.getFeLayoutSection());
    }

    //加载章节对话
    public void loadSectionConversation(int count) {
        ;
    }


}
