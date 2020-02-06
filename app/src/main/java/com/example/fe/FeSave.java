package com.example.fe;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;

public class FeSave extends Application {

    public Activity activity = null;
    public FeEvent feEvent = null;
    public FeHeart feHeart = null;

    //
    public FeSectionMap feSectionMap = null;
    public void setFeSectionMap(FeSectionMap sectionMap){
        feSectionMap = sectionMap;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void start(Activity act){
        activity = act;
        feHeart = new FeHeart();
        feEvent = new FeEvent(this);
    }

}