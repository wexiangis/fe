package com.example.fe;

import android.app.Activity;
import android.widget.RelativeLayout;

public class FeEvent {

    public Activity act;
    public RelativeLayout layout;
    public FeSave fedata;

    public FeEvent(Activity activity, FeSave feSave)
    {
        act = activity;
        fedata = feSave;
        //布局准备
        layout = new RelativeLayout(activity);
        //开始显示
        act.setContentView(layout);
        //加载主界面
        loadMainTheme();
    }

    //加载主界面
    public void loadMainTheme(){
        FeMainTheme mainTheme = new FeMainTheme(this);
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
