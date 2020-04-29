package com.example.fe;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/*
    贯穿全局的参数管理
 */
public class FeData extends Application {

    //核心部件
    private static Activity activity = null;
    public static Activity getActivity() { return activity; }
    public static void setActivity(Activity act) { activity = act; }

    private static Context _context = null;
    public static Context getContext() { return _context; }
    public static void setContext(Context context) { _context = context; }

    private static FeHeart feHeart = null;
    public static FeHeart getFeHeart() { return feHeart; }
    public static void setFeHeart(FeHeart heart) { feHeart = heart; }
    public static void feHeartStart() { if(feHeart != null) feHeart.start(); }
    public static void feHeartStop() { if(feHeart != null) feHeart.stop(); }

    //结构层
    private static FeEvent feEvent = null;
    public static FeEvent getFeEvent() { return feEvent; }
    public static void setFeEvent(FeEvent event) { feEvent = event; }

    private static FeLayoutSection feLayoutSection = null;
    public static FeLayoutSection getFeLayoutSection() { return feLayoutSection; }
    public static void setFeLayoutSection(FeLayoutSection layoutSection) { feLayoutSection = layoutSection; }

    //参数合集
    private static FeParamMap feParamMap = null;
    public static FeParamMap getFeParamMap() { return feParamMap; }
    public static void setFeParamMap(FeParamMap paramMap) { feParamMap = paramMap; }

    private static FeParamUnit feParamUnit = null;
    public static FeParamUnit getFeParamUnit() { return feParamUnit; }
    public static void setFeParamUnit(FeParamUnit paramUnit) { feParamUnit = paramUnit; }

    public void onCreate() {
        super.onCreate();
    }

    public static void start(Activity act){
        //保留activity
        setActivity(act);
        //获取application的context
        setContext(activity.getApplicationContext());
        //全局动画心跳
        setFeHeart(new FeHeart());
        //开始事件
        setFeEvent(new FeEvent());
    }
}