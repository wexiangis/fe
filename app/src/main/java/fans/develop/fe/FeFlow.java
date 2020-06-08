package fans.develop.fe;

import android.view.ViewGroup;

/*
    流程管理: 用于来回切换各个界面
 */
public class FeFlow {

    private void loadLayout(FeLayoutParent layout){
        //销毁旧layout
        if(FeData.layoutCurrent != null && FeData.layoutCurrent.callback != null)
            FeData.layoutCurrent.callback.destory();
        //入栈
        if(FeData.layoutCurrent != null){
            if(FeData.layoutChain == null)
                FeData.layoutChain = new FeChain<FeLayoutParent>(FeData.layoutCurrent);
            else
                FeData.layoutChain = FeData.layoutChain.next = new FeChain<FeLayoutParent>(FeData.layoutCurrent);
        }
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
        ;
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

    //加载章节
    //sX: 存档位置 mode: 0/重新加载 1/中断继续
    public void loadSection(int sX, int mode) {
        //初始化章节数据
        FeData.section = new FeSection(FeData.context, sX, mode);
        FeData.layoutSection = new FeLayoutSection(FeData.context, FeData.section.callback);
        //显示章节
        loadLayout(FeData.layoutSection);
    }

    //系统的界面返回, 返回false表示没有上一级界面了
    public boolean loadLast(){
        //链表已空
        if(FeData.layoutChain == null)
            return false;
        //销毁旧layout
        if(FeData.layoutCurrent != null && FeData.layoutCurrent.callback != null)
            FeData.layoutCurrent.callback.destory();
        //记录当前
        FeData.layoutCurrent = FeData.layoutChain.data;
        //出栈
        FeData.layoutChain = FeData.layoutChain.previous;
        //有需要reload的
        if(FeData.layoutCurrent.callback != null)
            FeData.layoutCurrent.callback.reload();
        //显示
        FeData.activity.setContentView(FeData.layoutCurrent);
        
        return true;
    }
}
