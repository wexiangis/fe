package com.example.fe;

import android.view.ViewGroup;

/*
    事件管理层: 用于切换 主界面,章节内容,存档界面,等等等
 */
public class FeFlow {

    public void stop(){
        if(FeData.feLayoutSection != null &&
            FeData.feLayoutSection.getParent() != null)
            ((ViewGroup)FeData.feLayoutSection.getParent()).removeAllViews();
    }

    public void start(){

        //恢复
        if(FeData.feLayoutSection != null){
            FeData.activity.setContentView(FeData.feLayoutSection);
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
        //更新存档状态
        FeData.save = FeData.feAssets.save.getSx(FeData.saveNum);
        //加载主界面
        FeData.activity.setContentView(new FeLayoutMainTheme(FeData.context));
    }

    //加载章节大地图
    public void loadSection(int section) {
        //只创建一个sectionLayout
        if(FeData.feLayoutSection == null)
            FeData.feLayoutSection = new FeLayoutSection(FeData.context, section);
        FeData.activity.setContentView(FeData.feLayoutSection);
    }

}
