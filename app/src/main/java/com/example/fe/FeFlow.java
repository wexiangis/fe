package com.example.fe;

import android.view.ViewGroup;

/*
    事件管理层: 用于切换 主界面,章节内容,存档界面,等等等
 */
public class FeFlow {

    private void loadLayout(FeLayoutParent layout){
        //销毁旧layout
        if(FeData.layoutCurrent != null && FeData.layoutCurrent instanceof FeLayoutParent.Callback)
            FeData.layoutCurrent.callback.destory();
        //记录当前
        FeData.layoutCurrent = layout;
        //显示
        FeData.activity.setContentView(FeData.layoutCurrent);
    }

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
        loadLayout(new FeLayoutMainTheme(FeData.context));
    }

    //加载职业动画
    public void loadProfessionAnim(){
        ;
    }

    //加载主界面菜单
    public void loadMainMenu(){
        loadLayout(new FeLayoutMainMenu(FeData.context));
    }

    //加载存档界面: ctrl 0/新建 1/加载(或继续) 2/删除 3/复制 4/通关存档
    public void loadSave(int ctrl){
        loadLayout(new FeLayoutSave(FeData.context, ctrl));
    }

    //加载额外内容
    public void loadExtra(){
        loadLayout(new FeLayoutExtra(FeData.context));
    }

    //加载章节大地图
    public void loadSection(int section) {
        //只创建一个sectionLayout
        if(FeData.layoutSection == null)
            FeData.layoutSection = new FeLayoutSection(FeData.context, section);
        loadLayout(FeData.layoutSection);
    }

}
