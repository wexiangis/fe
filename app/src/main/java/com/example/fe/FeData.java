package com.example.fe;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/*
    贯穿全局的参数管理
 */
public class FeData extends Application {

    //核心部件
    public static Activity activity = null;
    public static Context context = null;
    public static FeHeart feHeart = null;
    //结构层
    public static FeEvent feEvent = null;
    public static FeLayoutMainTheme feLayoutMainTheme = null;
    public static FeLayoutSection feLayoutSection = null;
    //参数合集
    public static FeParamMap feParamMap = null;
    public static FeParamUnit feParamUnit = null;
    public static FeAssets feAssets = null;

    public static void start(Activity act){
        //保留activity
        activity = act;
        //获取application的context
        context = activity.getApplicationContext();
        //全局动画心跳
        if(feHeart == null)
            feHeart = new FeHeart();
        feHeart.start();
        //开始事件
        if(feEvent == null)
            feEvent = new FeEvent();
        feEvent.start();
    }

    public static void stop(){
        if(feHeart != null)
            feHeart.stop();
        if(feEvent != null)
            feEvent.stop();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //
        feAssets = new FeAssets();
//        feAssets.p_skill.saveFile();
    }
}