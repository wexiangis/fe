package com.example.fe;

/*
    事件管理层
 */
public class FeEvent {

    public FeSave feSave;
    public FeSectionLayout feSectionLayout = null;

    public FeEvent(FeSave save)
    {
        feSave = save;
        //加载主界面
//        loadMainTheme();
        //加载地图
        loadSection(1);
    }

    //加载主界面
    public void loadMainTheme(){
        feSave.activity.setContentView(new FeMainTheme(feSave.activity, feSave));
    }

    //加载章节片头
    public void loadSectionTitles(int count) {
    }

    //加载章节大地图
    public void loadSection(int section) {
        feSectionLayout = new FeSectionLayout(feSave.activity, feSave, section);
        feSave.activity.setContentView(feSectionLayout);
    }

    //加载章节对话
    public void loadSectionConversation(int count) {
        ;
    }


}
