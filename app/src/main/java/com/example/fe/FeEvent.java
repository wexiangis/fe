package com.example.fe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

public class FeEvent {

    private Activity act;
    private RelativeLayout relativeLayout;
    private FeSave fedata;

    int screenX, screenY;

    public FeEvent(Activity activity, FeSave feSave)
    {
        act = activity;
        fedata = feSave;
        //
        DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenX = dm.widthPixels;
        screenY = dm.heightPixels;
        //加载封面,等待触屏事件
        relativeLayout = new RelativeLayout(activity);
        //
        FeAnimView cover = new FeAnimView(act, new int [] {
                R.drawable.cover,
                R.drawable.cover2,
                R.drawable.cover3}, 500, new Rect(0, 0, screenX, screenY));
        //
        FeMapView femap = new FeMapView(act);
        //
        relativeLayout.addView(cover);
//        relativeLayout.addView(femap);
        //
        act.setContentView(relativeLayout);
    }

}
