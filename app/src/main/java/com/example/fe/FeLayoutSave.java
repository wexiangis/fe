package com.example.fe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/*
    存档条列表
 */
public class FeLayoutSave extends FeLayoutParent {

    public static final String default_name = "未 使 用";

    private int ctrl;
    //条目列表
    private Button[] bnSaveList;
    //菜单线性布局参数
    private LinearLayout linearLayout = null;

    //复制模式时,记录第一个选中的存档
    private int currnt_select = -1;

    //触屏事件回调函数
    private View.OnTouchListener onTouchListener  = new View.OnTouchListener (){
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //按下反馈
                v.setAlpha(0.5f);
            }
            else if(event.getAction() == MotionEvent.ACTION_UP) {
                //松开反馈
                v.setAlpha(1.0f);
                //遍历所有存档位置
                for(int i = 0; i < bnSaveList.length; i++)
                {
                    if(v == bnSaveList[i])
                    {
                        switch(FeLayoutSave.this.ctrl){
                            //新建
                            case 0:
                                //确认为空存档
                                if(bnSaveList[i].getText().toString().indexOf(default_name) == 0){
                                    //新建
                                    FeData.assets.save.newSx(i);
                                    //刷新
                                    refresh();
                                }
                                break;
                            //加载
                            case 1:
                                //确认为非空存档
                                if(bnSaveList[i].getText().toString().indexOf(default_name) != 0)
                                    FeData.flow.loadSection(i, 0);
                                break;
                            //删除
                            case 2:
                                //确认为非空存档
                                if(bnSaveList[i].getText().toString().indexOf(default_name) != 0){
                                    //删除
                                    FeData.assets.save.delSx(i);
                                    //刷新
                                    refresh();
                                }
                                break;
                            //复制
                            case 3:
                                //点击被复制项
                                if(currnt_select < 0){
                                    //确认为非空存档
                                    if(bnSaveList[i].getText().toString().indexOf(default_name) != 0){
                                        //标记
                                        currnt_select = i;
                                        v.setAlpha(0.5f);
                                    }
                                }
                                //点击复制到位置
                                else{
                                    //不是选中那条
                                    if(i != currnt_select){
                                        //复制
                                        FeData.assets.save.copySx(i, currnt_select);
                                        //解除标记
                                        bnSaveList[currnt_select].setAlpha(1.0f);
                                        currnt_select = -1;
                                        //刷新
                                        refresh();
                                    }
                                    //点击了选中那条,解除选中状态
                                    else
                                        currnt_select = -1;
                                }
                                break;
                            //通关保存
                            case 4:
                                break;
                        }
                    }
                }
            }
            //不返回true的话ACTION_DOWN之后的事件都会被丢弃
            return true;
        }
    };

    private Button buildButtonStyle(Context context, String text){
        Button button = new Button(context);
        button.setText(text);
        button.setTextColor(0xFFFFFFFF);
        button.setTextSize(24);
        button.setGravity(Gravity.CENTER);
        button.setOnTouchListener(onTouchListener);
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
                bnSaveList[i] = buildButtonStyle(context, default_name);
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
                bnSaveList[i].setText(default_name);
        }
    }
}
