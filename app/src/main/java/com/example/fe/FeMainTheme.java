package com.example.fe;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FeMainTheme {

    private FeEvent feEvent;
    private int screenX, screenY;

    //
    private boolean showMenu = false;

    //主界面涉及的View
    private FeAnimView cover = null;
    private TextView tipsText = null;
    RelativeLayout.LayoutParams tipsTextParam = null;
    private LinearLayout linearLayout = null;
    RelativeLayout.LayoutParams linearLayoutParam = null;

    //主界面涉及的View的触屏事件回掉函数
    private View.OnTouchListener onTouchListener  = new View.OnTouchListener (){
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                if(v == cover) {
                    //
                    if(showMenu) {
                        removeMenu();
                        loadTip();
                    }
                    else {
                        removeTips();
                        loadMenu();
                    }
                    //
                    showMenu = !showMenu;
                }
                else{
                    ;//菜单按键
                }
            }
            return true;//不返回true的话ACTION_DOWN之后的事件都会被丢弃
        }
    };

    public FeMainTheme(FeEvent event)
    {
        feEvent = event;
        //获取屏幕参数
        DisplayMetrics dm = new DisplayMetrics();
        feEvent.act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenX = dm.widthPixels;
        screenY = dm.heightPixels;
        //
        loadCover();
        loadTip();
    }

    public void loadCover(){
        if(cover != null)
            return;
        //主界面封面背景
        cover = new FeAnimView(feEvent.act, new int [] {
                R.drawable.cover,
                R.drawable.cover2,
                R.drawable.cover3}, 500, new Rect(0, 0, screenX, screenY));
        cover.setOnTouchListener(onTouchListener);
        feEvent.layout.addView(cover);
    }

    public void loadTip(){
        if(tipsText == null){
            //按任意键继续
            tipsText = new TextView(feEvent.act);
            tipsText.setText("按任意键继续");
            tipsText.setTextSize(16);
            tipsTextParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tipsTextParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tipsTextParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            tipsTextParam.setMargins(0, 0, 0, 100);
        }
        feEvent.layout.addView(tipsText, tipsTextParam);
    }

    public void removeTips()
    {
        if(tipsText == null)
            return;
        feEvent.layout.removeView(tipsText);
    }

    public void loadMenu(){
        if(linearLayout == null)
        {
            linearLayout = new LinearLayout(feEvent.act);
            linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            linearLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            linearLayoutParam.setMargins(0, 0, 0, 100);
        }
        feEvent.layout.addView(linearLayout, linearLayoutParam);
    }

    public void removeMenu()
    {
        if(linearLayout == null)
            return;
        feEvent.layout.removeView(linearLayout);
    }

}
