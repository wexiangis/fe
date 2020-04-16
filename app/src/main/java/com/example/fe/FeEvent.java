package com.example.fe;

/*
    事件管理层: 用于切换 主界面,章节内容,存档界面,等等等
 */
public class FeEvent {

    public FeSave feSave;

    public FeEvent(FeSave save)
    {
        feSave = save;
        //加载主界面
//        loadMainTheme();
        //加载地图
        loadSection(99);
    }

    //加载主界面
    public void loadMainTheme(){
        feSave.activity.setContentView(new FeLayoutMainTheme(feSave.activity, feSave));
    }

    //加载章节片头
    public void loadSectionTitles(int count) {
    }

    //加载章节大地图
    public void loadSection(int section) {
        feSave.feLayoutSection = new FeLayoutSection(feSave.activity, feSave, section);
        feSave.activity.setContentView(feSave.feLayoutSection);
    }

    //加载章节对话
    public void loadSectionConversation(int count) {
        ;
    }


}
