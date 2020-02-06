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

public class FeMainTheme extends RelativeLayout {

    //
    private FeSave feSave;

    private int screenWidth, screenHeight;

    //
    private boolean showMenu = false;

    private FeAnimFrame cover = null;

    private TextView tipsText = null;
    private RelativeLayout.LayoutParams tipsTextParam = null;

    private TextView tv1 = null, tv2 = null, tv3 = null;
    private LinearLayout linearLayout = null;
    private RelativeLayout.LayoutParams linearLayoutParam = null;

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
                else if(v == tv1){
                    feSave.feEvent.loadSection(0);
                }
                else{
                    ;//菜单按键
                }
            }
            return true;//不返回true的话ACTION_DOWN之后的事件都会被丢弃
        }
    };

    public FeMainTheme(Context context, FeSave save)
    {
        super(context);
        feSave = save;
        //获取屏幕参数
        DisplayMetrics dm = new DisplayMetrics();
        feSave.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        //加载背景动画
        loadCover();
        //加载按任意键提示
        loadTip();
    }

    public void loadCover(){
        if(cover != null)
            return;
        //主界面封面背景
        cover = new FeAnimFrame(feSave.activity, new int [] {
                R.drawable.cover,
                R.drawable.cover2,
                R.drawable.cover3}, 500, new Rect(0, 0, screenWidth, screenHeight));
        cover.setOnTouchListener(onTouchListener);
        this.addView(cover);
    }

    public void loadTip(){
        if(tipsText == null){
            //按任意键继续
            tipsText = new TextView(feSave.activity);
            tipsText.setText("按任意键继续");
            tipsText.setTextSize(16);
            tipsTextParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tipsTextParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tipsTextParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            tipsTextParam.setMargins(0, 0, 0, 100);
            //
            tipsText.setLayoutParams(tipsTextParam);
        }
        this.addView(tipsText);
    }

    public void removeTips()
    {
        if(tipsText == null)
            return;
        this.removeView(tipsText);
    }

    public void loadMenu(){
        if(linearLayout == null)
        {
            tv1 = new TextView(feSave.activity);tv1.setText("开 始");tv1.setTextSize(16);
            tv1.setOnTouchListener(onTouchListener);
            //
            tv2 = new TextView(feSave.activity);tv2.setText("清单2");tv2.setTextSize(16);
            tv3 = new TextView(feSave.activity);tv3.setText("清单3");tv3.setTextSize(16);
            //
            linearLayout = new LinearLayout(feSave.activity);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
//            linearLayout.setBackgroundColor(0xFF00FF00);
            //
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0, 0, 20);
            //
            linearLayout.addView(tv1, layoutParams);
            linearLayout.addView(tv2, layoutParams);
            linearLayout.addView(tv3, layoutParams);
            //
            linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            linearLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            linearLayoutParam.setMargins(0, 0, 0, 100);
            //
            linearLayout.setLayoutParams(linearLayoutParam);
        }
        this.addView(linearLayout);
    }

    public void removeMenu()
    {
        if(linearLayout == null)
            return;
        this.removeView(linearLayout);
    }

}