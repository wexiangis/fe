package fans.develop.fe;

import android.view.ViewGroup;

/*
    流程管理: 用于来回切换各个界面
 */
public class FeFlow {

    private FeData feData;

    public FeFlow(FeData feData){
        this.feData = feData;
    }

    private void loadLayout(FeLayoutParent layout){
        //销毁旧layout
        if(feData.layoutCurrent != null && feData.layoutCurrent.callback != null)
            feData.layoutCurrent.callback.destory();
        //入栈
        if(feData.layoutCurrent != null){
            if(feData.layoutChain == null)
                feData.layoutChain = new FeChain<FeLayoutParent>(feData.layoutCurrent);
            else {
                feData.layoutChain.next = new FeChain<FeLayoutParent>(feData.layoutCurrent);
                feData.layoutChain.next.previous = feData.layoutChain;
                feData.layoutChain = feData.layoutChain.next;
            }
        }
        //记录当前
        feData.layoutCurrent = layout;
        //需要的reload
        if(feData.layoutCurrent.callback != null)
            feData.layoutCurrent.callback.reload();
        //显示
        feData.activity.setContentView(feData.layoutCurrent);
    }

    public void stop(){
        if(feData.layoutCurrent != null &&
            feData.layoutCurrent.getParent() != null)
            ((ViewGroup)feData.layoutCurrent.getParent()).removeAllViews();
    }

    public void start(){

        //恢复
        if(feData.layoutCurrent != null){
            feData.activity.setContentView(feData.layoutCurrent);
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
        loadTheme();
    }

    //加载主界面
    public void loadTheme(){
        loadLayout(new FeLayoutTheme(feData));
    }

    //加载职业动画
    public void loadProfessionAnim(){
        ;
    }

    //加载主界面菜单
    public void loadMainMenu(){
        loadLayout(new FeLayoutMainMenu(feData));
    }

    //加载存档界面: ctrl 0/新建 1/继续 2/加载(或继续) 3/删除 4/复制 5/通关存档
    public void loadSaveMenu(int ctrl){
        loadLayout(new FeLayoutSave(feData, ctrl));
    }

    //加载额外内容
    public void loadExtraMenu(){
        loadLayout(new FeLayoutExtra(feData));
    }

    //加载章节
    //sX: 存档位置 mode: 0/重新加载 1/中断继续
    public void loadSection(int sX, int mode) {
        //初始化章节数据
        feData.section = new FeSection(feData.context, sX, mode);
        //显示界面
        loadLayout(feData.section.layoutSection);
        //开始流程
        feData.section.start();
    }

    //系统的界面返回, 返回false表示没有上一级界面了
    public boolean loadLast(){
        //链表已空
        if(feData.layoutChain == null)
            return false;
        //销毁旧layout
        if(feData.layoutCurrent != null && feData.layoutCurrent.callback != null)
            feData.layoutCurrent.callback.destory();
        //记录当前
        feData.layoutCurrent = feData.layoutChain.data;
        //出栈
        feData.layoutChain = feData.layoutChain.previous;
        //有需要reload的
        if(feData.layoutCurrent.callback != null)
            feData.layoutCurrent.callback.reload();
        //显示
        feData.activity.setContentView(feData.layoutCurrent);
        
        return true;
    }
}
