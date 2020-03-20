package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    章节地图和地图人物动画等信息管理
 */
public class FeSectionLayout extends RelativeLayout {

    private FeSave feSave;

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < unitLayout.getChildCount(); i++)
            unitLayout.getChildAt(i).invalidate();
    }

    private int hitOrder = -1;
    public boolean checkHit(int type, float x, float y){
        //遍历所有子view
        for (int i = 0; i < unitLayout.getChildCount(); i++) {
            FeAnimFilm anim = (FeAnimFilm)unitLayout.getChildAt(i);
            if(anim.checkHit(x, y)){
                if(type == MotionEvent.ACTION_UP){
                    //设置人物动画
                    if(hitOrder == i)
                        anim.setAnimMode(1);
                    //
                    else
                        hitOrder = -1;
                }else {
                    //释放上一个人物动画
                    if(hitOrder > -1){
                        FeAnimFilm anim2 = (FeAnimFilm)unitLayout.getChildAt(hitOrder);
                        anim2.setAnimMode(0);
                    }
                    //
                    hitOrder = i;
                }
                return true;
            }
        }
        //释放上一个人物动画
        if(hitOrder > -1){
            FeAnimFilm anim2 = (FeAnimFilm)unitLayout.getChildAt(hitOrder);
            anim2.setAnimMode(0);
        }
        //
        hitOrder = -1;
        return false;
    }

    public FeMapLayout mapLayout = null;
    public FeMapUnitLayout unitLayout = null;
    public FeMapInfoLayout mapInfoLayout = null;
    public FeMapMenuLayout menuLayout = null;
    public FeMapUnitMenuLayout unitMenuLayout = null;
    public FeMapChatLayout chatLayout = null;

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
        // 系统菜单图层
        menuLayout = new FeMapMenuLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(menuLayout);
        //人物操作菜单图层
        unitMenuLayout = new FeMapUnitMenuLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(unitMenuLayout);
        //人物对话图层
        chatLayout = new FeMapChatLayout(feSave.activity, feSave, mapLayout.mapParam);
        addView(chatLayout);
        //其它图层
        ;
        //
//        setBackgroundColor(0xFF00FF00);
    }

}
