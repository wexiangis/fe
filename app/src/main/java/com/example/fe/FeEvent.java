package com.example.fe;

/*
    管理由用户操作产生的各种事件
 */
public class FeEvent {

    //选中事件状态[9]
    private boolean[] click_type = new boolean[9];

    //select type
    public static short EVENT_MOVE = 0;//移动中
    public static short EVENT_HIT_MAP = 1;//选中地图
    public static short EVENT_HIT_UNIT = 2;//选中人物
    public static short EVENT_HIT_MAPINFO = 3;//选中地图信息
    public static short EVENT_HIT_UNIT_MENU = 4;//选中人物菜单
    public static short EVENT_HIT_SYS_MENU = 5;//选中系统菜单
    public static short EVENT_CHAT = 6;//对讲中

    public void cleanSelectType(short type){
        click_type[type] = false;
    }

    public void cleanSelectTypeAll(short type){
        for(int i = 0; i < click_type.length; i++)
            click_type[i] = false;
    }

    public void setSelectType(short type){
        click_type[type] = true;
    }

    public boolean checkSelectType(short type){
        return click_type[type];
    }

}
