package com.example.fe;

import android.app.Activity;
import android.app.Application;

/*
    贯穿全局的参数管理
 */
public class FeSave extends Application {

    //核心部件
    public Activity activity = null;
    public FeHeart feHeart = null;
    //结构层
    public FeEvent feEvent = null;
    public FeLayoutSection feLayoutSection = null;
    //参数合集
    public FeMapParam feMapParam = null;
    public FeUnitParam feUnitParam = null;

    public void onCreate() {
        super.onCreate();
    }

    public void start(Activity act){
        activity = act;
        //全局动画心跳
        feHeart = new FeHeart();
        //开始事件
        feEvent = new FeEvent(this);
    }

}