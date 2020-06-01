package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

    private FeLayoutMainMenu linearLayout = null;

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

    public void loadMenu(){
        //为空则创建
        if(linearLayout == null)
            linearLayout = new FeLayoutMainMenu(FeData.context);
        linearLayout.reload();
        //添加到主界面
        this.addView(linearLayout);
    }

    public void removeMenu()
    {
        if(linearLayout == null)
            return;
        this.removeView(linearLayout);
    }

}