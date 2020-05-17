package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    章节地图和地图人物动画等信息管理
 */
public class FeLayoutSection extends RelativeLayout {

    public FeLayoutMap mapLayout = null;
    public FeLayoutMark markLayout = null;
    public FeLayoutUnit unitLayout = null;
    public FeLayoutMapInfo mapInfoLayout = null;
    public FeLayoutUnitMenu unitMenuLayout = null;
    public FeLayoutMenu menuLayout = null;
    public FeLayoutChat chatLayout = null;

    public void refresh(){
        //更新标记格
        markLayout.refresh();
        //更新人物动画
        unitLayout.refresh(0);
        //更新地形信息
        mapInfoLayout.refresh();
        //更新人物菜单
        unitMenuLayout.refresh();
        //更新系统菜单
        menuLayout.refresh();
        //更新对话
        chatLayout.refresh();
    }

    public boolean click(float x, float y){
        //点击:正在对话?
        chatLayout.checkHit(x, y);
        //点击:系统菜单中?
        menuLayout.checkHit(x, y);
        //点击:人物菜单中?
        unitMenuLayout.checkHit(x, y);
        //点击:标记格
        markLayout.checkHit(x, y);
        //点击:选中人物?
        unitLayout.checkHit(x, y);
        //点击:地图信息?
        mapInfoLayout.checkHit(x, y);
        return false;
    }

    public FeLayoutSection(Context context, int section){
        super(context);
        //地图图层
        mapLayout = new FeLayoutMap(FeData.context, section);
        addView(mapLayout);
        //标记格图层
        markLayout = new FeLayoutMark(FeData.context);
        addView(markLayout);
        //人物动画图层
        unitLayout = new FeLayoutUnit(FeData.context);
        addView(unitLayout);
        //地图地形信息
        mapInfoLayout = new FeLayoutMapInfo(FeData.context);
        addView(mapInfoLayout);
        //人物操作菜单图层
        unitMenuLayout = new FeLayoutUnitMenu(FeData.context);
        addView(unitMenuLayout);
        // 系统菜单图层
        menuLayout = new FeLayoutMenu(FeData.context);
        addView(menuLayout);
        //人物对话图层
        chatLayout = new FeLayoutChat(FeData.context);
        addView(chatLayout);
        //其它图层
        ;
        //
//        setBackgroundColor(0xFF00FF00);
    }

}
