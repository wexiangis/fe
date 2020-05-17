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
    public static FeHeart feHeart = null;//全局动画心跳
    //结构层
    public static FeFlow feFlow = null;//界面流程管理
    public static FeLayoutMainTheme feLayoutMainTheme = null;
    public static FeLayoutSection feLayoutSection = null;
    //参数合集
    public static FeAssets feAssets = null;//assets文件资源管理
    public static FeEvent feEvent = null;//用户操作事件管理
    public static FeParamMap feParamMap = null;
//    public static FeParamUnit feParamUnit = null;

    public static void start(Activity act){
        //保留activity
        activity = act;
        //获取application的context
        context = activity.getApplicationContext();
        //全局动画心跳
        feHeart.start();
        //开始事件
        feFlow.start();
    }

    public static void stop(){
        //关闭timer定时器
        feHeart.stop();
        //从当前activity中移除界面控件
        feFlow.stop();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        feHeart = new FeHeart();
        feFlow = new FeFlow();
        feAssets = new FeAssets();
        feEvent = new FeEvent();
    }
}