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
    主界面菜单选项,不从属于主界面,它有自己的背景墙
 */
public class FeLayoutMainMenu extends FeLayoutParent {

    //菜单信息
    private Button tvLoad = null, tvNew = null, tvCopy = null, tvDel = null, tvElse = null;

    //菜单线性布局参数
    private LinearLayout linearLayout = null;
    private RelativeLayout.LayoutParams linearLayoutParam = null;
    private LinearLayout.LayoutParams tvLayoutParams = null;

    //主界面触屏事件回调函数
    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //点击:创建存档
            if(v == tvNew)
                FeData.flow.loadSave(0);
            //点击:加载存档
            else if(v == tvLoad)
                FeData.flow.loadSave(1);
            //点击:删除存档
            else if(v == tvDel)
                FeData.flow.loadSave(2);
            //点击:复制存档
            else if(v == tvCopy)
                FeData.flow.loadSave(3);
            //点击:附加内容
            else if(v == tvElse)
                FeData.flow.loadExtra();
        }
    };

    private Button buildButtonStyle(Context context, String text){
        Button button = new Button(context);
        button.setText(text);
        button.setTextColor(0xFFFFFFFF);
        button.setTextSize(24);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(onClickListener);
        button.setBackground(Drawable.createFromStream(getClass().getResourceAsStream("/assets/menu/item/item_b.png"), null));
        return button;
    }

    public FeLayoutMainMenu(Context context){
        super(context);
        //菜单各项TXT
        tvLoad = buildButtonStyle(context, "继 续");
        tvNew = buildButtonStyle(context, "新游戏");
        tvCopy = buildButtonStyle(context, "复 制");
        tvDel = buildButtonStyle(context, "删 除");
        tvElse = buildButtonStyle(context, "附加内容");
        //创建线性布局窗体
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //创建线性布局窗体参数
        tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.setMargins(0,0, 0, 20);
        //线性布局窗体相对主界面位置参数
        linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        linearLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL);
        //根据存档状态加载条目
        loadMenu();
        //显示列表
        this.addView(linearLayout, linearLayoutParam);
        this.setBackgroundColor(0x80408040);
    }

    private void loadMenu(){
        //更新存档状态(FeData.save[][]的状态)
        FeData.saveReload();
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
            linearLayout.addView(tvLoad, tvLayoutParams);
        linearLayout.addView(tvNew, tvLayoutParams);
        if(findRecord)
            linearLayout.addView(tvCopy, tvLayoutParams);
        if(findRecord)
            linearLayout.addView(tvDel, tvLayoutParams);
        linearLayout.addView(tvElse, tvLayoutParams);
    }
}
