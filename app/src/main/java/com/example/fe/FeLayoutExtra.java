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
    额外列表
 */
public class FeLayoutExtra extends LinearLayout {

    //条目列表
    private Button[] bnSaveList;

    //主界面触屏事件回调函数
    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            for(int i = 0; i < bnSaveList.lenght; i++){
                if(v == bnSaveList[i])
                    ;
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
        button.setBackground(Drawable.createFromStream(getClass().getResourceAsStream("/assets/menu/item/item_r.png"), null));
        return button;
    }

    public FeLayoutExtra(Context context){
        super(context);
        //更新存档状态(FeData.save[][]的状态)
        FeData.saveReload();
        //初始化
        bnSaveList = new Button[2];
        bnSaveList[0] = buildButtonStyle(context, "音 乐");
        bnSaveList[1] = buildButtonStyle(context, "战 绩");
        //创建线性布局窗体
        this.setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(0x80000080);
        //创建线性布局窗体参数
        LinearLayout.LayoutParams bnLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bnLayoutParams.setMargins(0,0, 0, 30);
        //线性布局窗体相对主界面位置参数
        RelativeLayout.LayoutParams linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        linearLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL);
        //添加布局参数
        this.setLayoutParams(linearLayoutParam);
        //添加条目到视图
        for(int i = 0; i < bnSaveList.lenght; i++)
            this.addView(bnSaveList[i], bnLayoutParams);
    }
}