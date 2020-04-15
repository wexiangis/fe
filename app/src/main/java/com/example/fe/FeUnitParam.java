package com.example.fe;

import android.app.Activity;
import android.graphics.Bitmap;

public class FeUnitParam {

    private Activity activity;

    public FeUnitParam(Activity act){
        activity = act;
        //初始化assets/unit文件夹中的信息
    }

    //用链表管理所有需要的人物信息(新增,重复的都从这里取)

    //获取单元名称
    public String get_u_name(int order){
        return null;
    }

    //获取单元名称说明
    public String get_u_nameSummary(int order){
        return null;
    }

    //获取单元头像
    public Bitmap get_head(int order){
        return null;
    }

    //获取单元地图动画
    public Bitmap get_anim(int order){
        return null;
    }

    //获取单元等级
    public int get_level(int order){
        return 0;
    }

    //获取单元职业名称
    public String get_p_name(int order){
        return null;
    }

    //获取单元职业名称说明
    public String get_p_nameSummary(int order){
        return null;
    }
}
