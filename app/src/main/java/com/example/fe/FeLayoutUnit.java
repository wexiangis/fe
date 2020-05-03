package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    地图中的人物动画管理
 */
public class FeLayoutUnit extends RelativeLayout {

    private FeParamMap paramMap;
    private int hitAnimOrder = -1;

    /*
        who_refresh:
            0/map refresh
            1/select unit refresh
     */
    public void refresh(int who_refresh){
        FeViewUnit tmp;
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++) {
            tmp = (FeViewUnit)getChildAt(i);
            if(hitAnimOrder == i &&
                paramMap.checkSelectType(FeParamMap.SELECT_UNIT)) {
                if(who_refresh == 1)
                    tmp.setAnimMode(tmp.getAnimMode()+1);
                paramMap.getRectByGrid(tmp._gridX, tmp._gridY, paramMap.selectUnit);
            }
            else
                tmp.setAnimMode(0);
            tmp.invalidate();
        }
    }

    public boolean checkHit(int type, float x, float y){
        if(type == MotionEvent.ACTION_UP &&
            !paramMap.checkSelectType(FeParamMap.SELECT_MOVE_END))
        {
            FeViewUnit tmp;
            //遍历所有子view
            for (int i = 0; i < getChildCount(); i++) {
                tmp = (FeViewUnit)getChildAt(i);
                if (tmp.checkHit(x, y)) {
                    hitAnimOrder = i;
                    paramMap.setSelectType(FeParamMap.SELECT_UNIT);
                    refresh(1);
                    return true;
                }
            }
            //
            hitAnimOrder = -1;
            paramMap.cleanSelectType(FeParamMap.SELECT_UNIT);
            refresh(0);
        }
        return false;
    }

    public FeLayoutUnit(Context context) {
        super(context);
        paramMap = FeData.feParamMap;

        loadView(0, 1, 0);
        loadView(1, 2, 1);
//        loadView(2, 3, 0);
//        loadView(3, 4, 1);
        loadView(4, 5, 0);
        loadView(5, 6, 1);
//        loadView(6, 7, 0);
//        loadView(7, 8, 1);
        loadView(8, 9, 0);
        loadView(9, 10, 1);
//        loadView(10, 11, 0);
//        loadView(11, 12, 1);
        loadView(12, 13, 0);
        loadView(13, 14, 1);

//        loadView(14, 1, 12);
//        loadView(15, 2, 13);
        loadView(16, 3, 12);
        loadView(17, 4, 13);
//        loadView(18, 5, 12);
//        loadView(19, 6, 13);
        loadView(20, 7, 12);
        loadView(21, 8, 13);
//        loadView(22, 9, 12);
//        loadView(23, 10, 13);
        loadView(24, 11, 12);
        loadView(25, 12, 13);
//        loadView(26, 13, 12);
//        loadView(27, 14, 13);
    }

    private void loadView(int id, int y, int x){
        addView(new FeViewUnit(FeData.context, id, x, y, 0, 0));
        addView(new FeViewUnit(FeData.context, id, x+2, y, 1, 1));
        addView(new FeViewUnit(FeData.context, id, x+4, y, 2, 2));
        addView(new FeViewUnit(FeData.context, id, x+6, y, 3, 3));
        addView(new FeViewUnit(FeData.context, id, x+8, y, 4, 4));
        addView(new FeViewUnit(FeData.context, id, x+10, y, 5, 5));
        addView(new FeViewUnit(FeData.context, id, x+12, y, 6, 6));
    }
}
