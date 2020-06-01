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
    主界面菜单选项
 */
public class FeLayoutMainMenu extends LinearLayout {

    //菜单信息
    private Button tvLoad = null, tvNew = null, tvCopy = null, tvDel = null, tvElse = null;

    //菜单线性布局参数
    private RelativeLayout.LayoutParams linearLayoutParam = null;
    private LinearLayout.LayoutParams tvLayoutParams = null;

    //主界面触屏事件回调函数
    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //点击:加载存档
            if(v == tvLoad){
                FeData.flow.loadSection(0);
            }
            //点击:创建存档
            else if(v == tvNew){
                FeData.flow.loadSection(0);
            }
            //点击:复制存档
            else if(v == tvCopy){
                ;
            }
            //点击:删除存档
            else if(v == tvDel){
                ;
            }
            //点击:附加内容
            else if(v == tvElse){
                ;
            }
        }
    };

    private void _setTxetView(Button button, String text){
        button.setText(text);
        button.setTextColor(0xFFFFFFFF);
        button.setTextSize(24);
//        button.setShadowLayer(5, 5, 5, 0x40404040);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(onClickListener);
        button.setBackground(Drawable.createFromStream(getClass().getResourceAsStream("/assets/menu/item/item.png"), null));
    }

    public FeLayoutMainMenu(Context context){
        super(context);
        //菜单各项TXT
        tvLoad = new Button(FeData.context);_setTxetView(tvLoad, "继 续");
        tvNew = new Button(FeData.context);_setTxetView(tvNew, "新游戏");
        tvCopy = new Button(FeData.context);_setTxetView(tvCopy, "复 制");
        tvDel = new Button(FeData.context);_setTxetView(tvDel, "删 除");
        tvElse = new Button(FeData.context);_setTxetView(tvElse, "附加内容");
        //创建线性布局窗体
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(0x80808080);
        //创建线性布局窗体参数
        tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.setMargins(0,0, 0, 10);
        //线性布局窗体相对主界面位置参数
        linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        linearLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL);
        //添加布局参数
        this.setLayoutParams(linearLayoutParam);
    }

    public void reload(){
        //removeall
        this.removeAllViews();
        //检查是否存在记录
        boolean findRecord = false;
        for(int i = 0; i < FeData.saveNum; i++) {
            if (FeData.save[i][0] > 0) {
                findRecord = true;
                break;
            }
        }
        //按特定参数添加各项到线性布局
        if(findRecord)
            this.addView(tvLoad, tvLayoutParams);
        this.addView(tvNew, tvLayoutParams);
        if(findRecord)
            this.addView(tvCopy, tvLayoutParams);
        if(findRecord)
            this.addView(tvDel, tvLayoutParams);
        this.addView(tvElse, tvLayoutParams);
    }
}
