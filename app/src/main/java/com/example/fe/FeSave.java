package com.example.fe;

import android.app.Activity;

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
    public FeParamMap feParamMap = null;
    public FeParamUnit feParamUnit = null;

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