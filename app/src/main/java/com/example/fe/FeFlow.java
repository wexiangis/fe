package com.example.fe;

import android.view.ViewGroup;

/*
    事件管理层: 用于切换 主界面,章节内容,存档界面,等等等
 */
public class FeFlow {

    public void stop(){
        if(FeData.layoutSection != null &&
            FeData.layoutSection.getParent() != null)
            ((ViewGroup)FeData.layoutSection.getParent()).removeAllViews();
    }

    public void start(){

        //恢复
        if(FeData.layoutSection != null){
            FeData.activity.setContentView(FeData.layoutSection);
            return;
        }

        //加载OP
        loadOpening();
    }

    //加载章节片头
    public void loadOpening() {
        //片头播完...
        
        //加载主界面
        loadMainTheme();
    }

    //加载主界面
    public void loadMainTheme(){
        //加载主界面
        FeData.activity.setContentView(new FeLayoutMainTheme(FeData.context));
    }

    //加载职业动画
    public void loadProfessionAnim(){
        ;
    }

    //加载主界面菜单
    public void loadMainMenu(){
        //加载主界面
        FeData.activity.setContentView(new FeLayoutMainMenu(FeData.context));
    }

    //加载存档界面: ctrl 0/新建 1/加载(或继续) 2/删除 3/复制 4/通关存档
    public void loadSave(int ctrl){
        //加载主界面
        FeData.activity.setContentView(new FeLayoutSave(FeData.context, ctrl));
    }

    //加载额外内容
    public void loadExtra(){
        ;
    }

    //加载章节大地图
    public void loadSection(int section) {
        //只创建一个sectionLayout
        if(FeData.layoutSection == null)
            FeData.layoutSection = new FeLayoutSection(FeData.context, section);
        FeData.activity.setContentView(FeData.layoutSection);
    }

}
