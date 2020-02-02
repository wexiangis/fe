package com.example.fe;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;

public class FeSave extends Application {

    public Activity activity = null;
    public FeEvent feEvent = null;

    //
    public FeSectionMap feSectionMap = null;
    public FeMapParam feMapParam = new FeMapParam(0,0,1,1);
    public void setFeSectionMap(FeSectionMap sectionMap){
        feSectionMap = sectionMap;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void start(Activity act){
        activity = act;
        feEvent = new FeEvent(this);
    }

}