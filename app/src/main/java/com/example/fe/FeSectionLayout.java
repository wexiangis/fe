package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    章节地图和地图人物动画等信息管理
 */
public class FeSectionLayout extends RelativeLayout {

    private FeSave feSave;

    public FeMapLayout mapLayout = null;
    public FeMapUnitLayout unitLayout = null;
    public FeMapInfoLayout mapInfoLayout = null;
    public FeMapUnitMenuLayout unitMenuLayout = null;
    public FeMapMenuLayout menuLayout = null;
    public FeMapChatLayout chatLayout = null;

    public void refresh(){
        //更新人物动画
        unitLayout.refresh();
        //更新地形信息
        mapInfoLayout.refresh();
        //更新人物菜单
        unitMenuLayout.refresh();
        //更新系统菜单
        menuLayout.refresh();
        //更新对话
        chatLayout.refresh();
    }

    public boolean onTouch(int type, float x, float y, boolean isMove){
        //正在对话?
        if(chatLayout.checkHit(type, x, y, isMove))
            return true;
        //系统菜单中?
        if(menuLayout.checkHit(type, x, y, isMove))
            return true;
        //人物菜单中?
        if(unitMenuLayout.checkHit(type, x, y, isMove))
            return true;
        //
        if(mapInfoLayout.checkHit(type, x, y, isMove))
            return true;
        //选中人物?
        if(unitLayout.checkHit(type, x, y, isMove))
            return true;
        //
        return true;
    }

    public FeSectionLayout(Context context, FeSave save, int section){
        super(context);
        feSave = save;
        //地图图层
        mapLayout = new FeMapLayout(feSave.activity, feSave, section);
        addView(mapLayout);
        //地图人物动画图层
        unitLayout = new FeMapUnitLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(unitLayout);
        //地图地形信息
        mapInfoLayout = new FeMapInfoLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(mapInfoLayout);
        //人物操作菜单图层
        unitMenuLayout = new FeMapUnitMenuLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(unitMenuLayout);
        // 系统菜单图层
        menuLayout = new FeMapMenuLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(menuLayout);
        //人物对话图层
        chatLayout = new FeMapChatLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(chatLayout);
        //其它图层
        ;
        //
//        setBackgroundColor(0xFF00FF00);
    }

}
