package com.example.fe;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
    主界面
 */
public class FeLayoutMainTheme extends RelativeLayout {

    private boolean showMenu = false;

    //“按任意键继续”提示及其layout参数
    private TextView tvPressAnyKey = null;
    private RelativeLayout.LayoutParams tvPressAnyKeyParam = null;

    //菜单信息
    private TextView tvLoad = null, tvNew = null, tvCopy = null, tvDel = null, tvElse = null;

    //菜单线性布局参数
    private LinearLayout linearLayout = null;
    private RelativeLayout.LayoutParams linearLayoutParam = null;

    //主界面触屏事件回调函数
    private View.OnTouchListener onTouchListener  = new View.OnTouchListener (){
        public boolean onTouch(View v, MotionEvent event) {
            //触屏UP时
            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                //点击:加载存档
                if(v == tvLoad){
                    FeData.feFlow.loadSection(0);
                }
                //点击:创建存档
                else if(v == tvNew){
                    ;
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
                //点击:空白处
                else{
                    //菜单显示 -> 移除菜单
                    if(showMenu) {
                        removeMenu();
                        loadTip();
                        showMenu = false;
                    }
                    //没有菜单 -> 显示菜单
                    else {
                        removeTips();
                        loadMenu();
                        showMenu = true;
                    }
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
    }

    public void loadTip(){
        //为空则创建
        if(tvPressAnyKey == null){
            //按任意键继续
            tvPressAnyKey = new TextView(FeData.context);
            tvPressAnyKey.setText("按任意键继续");
            tvPressAnyKey.setTextSize(16);
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

    public void loadMenu(){
        //为空则创建
        if(linearLayout == null)
        {
            //菜单各项TXT
            tvLoad = new TextView(FeData.context);tvLoad.setText("继 续");tvLoad.setTextSize(16);
            tvLoad.setOnTouchListener(onTouchListener);
            tvNew = new TextView(FeData.context);tvNew.setText("新游戏");tvNew.setTextSize(16);
            tvNew.setOnTouchListener(onTouchListener);
            tvCopy = new TextView(FeData.context);tvCopy.setText("复 制");tvCopy.setTextSize(16);
            tvCopy.setOnTouchListener(onTouchListener);
            tvDel = new TextView(FeData.context);tvCopy.setText("删 除");tvCopy.setTextSize(16);
            tvDel.setOnTouchListener(onTouchListener);
            tvElse = new TextView(FeData.context);tvCopy.setText("附加内容");tvCopy.setTextSize(16);
            tvElse.setOnTouchListener(onTouchListener);
            //创建线性布局窗体
            linearLayout = new LinearLayout(FeData.context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            //创建线性布局窗体参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0, 0, 20);
            //线性布局窗体相对主界面位置参数
            linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            linearLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            linearLayoutParam.setMargins(0, 0, 0, 100);
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
            linearLayout.addView(tvLoad, layoutParams);
        linearLayout.addView(tvNew, layoutParams);
        if(findRecord)
            linearLayout.addView(tvCopy, layoutParams);
        if(findRecord)
            linearLayout.addView(tvDel, layoutParams);
        linearLayout.addView(tvElse, layoutParams);
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