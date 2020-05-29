package com.example.fe;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
    主界面
 */
public class FeLayoutMainTheme extends RelativeLayout {

    //当前显示状态: 0/tips 1/menu 2/存档列表
    private int showStatus = 0;

    //“按任意键继续”提示及其layout参数
    private TextView tvPressAnyKey = null;
    private RelativeLayout.LayoutParams tvPressAnyKeyParam = null;

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

    //主界面触屏事件回调函数
    private View.OnTouchListener onTouchListener  = new View.OnTouchListener (){
        public boolean onTouch(View v, MotionEvent event) {
            //触屏UP时
            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                //点击:空白处
                //菜单显示 -> 移除菜单
                if(showStatus == 0) {
                    removeTips();
                    loadMenu();
                    showStatus = 1;
                }
                //没有菜单 -> 显示菜单
                else if(showStatus == 1){
                    removeMenu();
                    loadTip();
                    showStatus = 0;
                }
            }
            return true;//不返回true的话ACTION_DOWN之后的事件都会被丢弃
        }
    };

    public FeLayoutMainTheme(Context context)
    {
        super(context);

        // //获取屏幕参数
        // DisplayMetrics dm = new DisplayMetrics();
        // FeData.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // screenWidth = dm.widthPixels;
        // screenHeight = dm.heightPixels;

        //加载背景动画
        // loadTitle();

        //加载按任意键提示
        loadTip();

        this.setBackgroundColor(0xFF408040);
        this.setOnTouchListener(onTouchListener);
    }

    public void loadItem(){
        ;
    }

    public void loadTip(){
        //为空则创建
        if(tvPressAnyKey == null){
            //按任意键继续
            tvPressAnyKey = new TextView(FeData.context);
            tvPressAnyKey.setText("按任意键继续");
            tvPressAnyKey.setTextColor(0x80FFFFFF);
            tvPressAnyKey.setTextSize(24);
            //相对主界面的位置
            tvPressAnyKeyParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvPressAnyKeyParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tvPressAnyKeyParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            tvPressAnyKeyParam.setMargins(0, 0, 0, 100);
            //添加布局参数
            tvPressAnyKey.setLayoutParams(tvPressAnyKeyParam);
        }
        //添加到主界面
        this.addView(tvPressAnyKey);
    }

    public void removeTips()
    {
        if(tvPressAnyKey == null)
            return;
        this.removeView(tvPressAnyKey);
    }

    private void _setTxetView(Button button, String text){
        button.setText(text);
        button.setTextColor(0xFFFFFFFF);
        button.setTextSize(24);
//        button.setShadowLayer(5, 5, 5, 0x40404040);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(onClickListener);
        button.setBackground(Drawable.createFromStream(getClass().getResourceAsStream("/assets/menu/item/item.png"), null));
    }

    public void loadMenu(){
        //为空则创建
        if(linearLayout == null)
        {
            //菜单各项TXT
            tvLoad = new Button(FeData.context);_setTxetView(tvLoad, "继 续");
            tvNew = new Button(FeData.context);_setTxetView(tvNew, "新游戏");
            tvCopy = new Button(FeData.context);_setTxetView(tvCopy, "复 制");
            tvDel = new Button(FeData.context);_setTxetView(tvDel, "删 除");
            tvElse = new Button(FeData.context);_setTxetView(tvElse, "附加内容");
            //创建线性布局窗体
            linearLayout = new LinearLayout(FeData.context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
//            linearLayout.setBackgroundColor(0x80808080);
            //创建线性布局窗体参数
            tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvLayoutParams.setMargins(0,0, 0, 10);
            //线性布局窗体相对主界面位置参数
            linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            linearLayoutParam.addRule(RelativeLayout.CENTER_VERTICAL);
            //添加布局参数
            linearLayout.setLayoutParams(linearLayoutParam);
        }
        //检查是否存在记录
        boolean findRecord = false;
        for(int i = 0; i < FeData.saveNum; i++){
            if(FeData.save[i][0] > 0){
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
        //添加到主界面
        this.addView(linearLayout);
    }

    public void removeMenu()
    {
        if(linearLayout == null)
            return;
        this.removeView(linearLayout);
        linearLayout.removeView(tvLoad);
        linearLayout.removeView(tvNew);
        linearLayout.removeView(tvCopy);
        linearLayout.removeView(tvDel);
        linearLayout.removeView(tvElse);
    }

}