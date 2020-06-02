package com.example.fe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/*
    存档条列表
 */
public class FeLayoutSave extends FeLayoutParent {

    private int ctrl;
    //条目列表
    private Button[] bnSaveList;
    //菜单线性布局参数
    private LinearLayout linearLayout = null;

    //主界面触屏事件回调函数
    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //遍历所有存档位置
            for(int i = 0; i < bnSaveList.length; i++)
            {
                if(v == bnSaveList[i])
                {
                    switch(FeLayoutSave.this.ctrl){
                        case 0: //新建
                            if(bnSaveList[i].getText().toString().indexOf("未 使 用") == 0){
                                //新建
                                FeData.assets.save.newSx(i);
                                //刷新
                                refresh();
                            }
                        break;
                        case 1: //加载
                            if(bnSaveList[i].getText().toString().indexOf("未 使 用") != 0)
                                FeData.flow.loadSection(0);
                        break;
                        case 2: //删除
                            if(bnSaveList[i].getText().toString().indexOf("未 使 用") != 0){
                                //删除
                                FeData.assets.save.delSx(i);
                                //刷新
                                refresh();
                            }
                        break;
                        case 3: //复制
                        break;
                        case 4: //通关保存
                        break;
                    }
                }
            }
        }
    };

    private Button buildButtonStyle(Context context, String text){
        Button button = new Button(context);
        button.setText(text);
        button.setTextColor(0xFFFFFFFF);
        button.setTextSize(24);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(onClickListener);
        button.setBackground(Drawable.createFromStream(getClass().getResourceAsStream("/assets/menu/item/item_save_g.png"), null));
        return button;
    }

    /*
        ctrl 0/新建 1/加载(或继续) 2/删除 3/复制 4/通关保存
     */
    public FeLayoutSave(Context context, int ctrl){
        super(context);
        this.ctrl = ctrl;
        //更新存档状态(FeData.save[][]的状态)
        FeData.saveReload();
        //初始化
        bnSaveList = new Button[FeData.saveNum];
        for(int i = 0; i < bnSaveList.length; i++){
            int h = FeData.save[i][2]/3600;
            int m = FeData.save[i][2]%3600/60;
            int s = FeData.save[i][2]%60;
            if(FeData.save[i][0] > 0)
                bnSaveList[i] = buildButtonStyle(
                    context, String.format("第%d章 XXX %02d:%02d:%02d", FeData.save[i][0], h, m, s));
            else
                bnSaveList[i] = buildButtonStyle(context, "未 使 用");
        }
        //创建线性布局窗体
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //创建线性布局窗体参数
        LinearLayout.LayoutParams bnLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bnLayoutParams.setMargins(0,0, 0, 30);
        //线性布局窗体相对主界面位置参数
        RelativeLayout.LayoutParams linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        linearLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL);
        //添加条目到视图
        for(int i = 0; i < bnSaveList.length; i++)
            linearLayout.addView(bnSaveList[i], bnLayoutParams);
        //显示列表
        this.addView(linearLayout, linearLayoutParam);
        this.setBackgroundColor(0x80404080);
    }

    /*
        刷新内容
     */
    public void refresh() {
        //更新存档状态(FeData.save[][]的状态)
        FeData.saveReload();
        //更新词条
        for(int i = 0; i < bnSaveList.length; i++){
            int h = FeData.save[i][2]/3600;
            int m = FeData.save[i][2]%3600/60;
            int s = FeData.save[i][2]%60;
            if(FeData.save[i][0] > 0)
                bnSaveList[i].setText(String.format("第%d章 XXX %02d:%02d:%02d", FeData.save[i][0], h, m, s));
            else
                bnSaveList[i].setText("未 使 用");
        }
    }
}
