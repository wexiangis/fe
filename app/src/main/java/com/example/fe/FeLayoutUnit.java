package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    地图中的人物动画管理
 */
public class FeLayoutUnit extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;
    private int hitAnimOrder = -1;

    public void refresh(){
        FeViewUnit tmp;
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++) {
            tmp = (FeViewUnit)getChildAt(i);
            if(hitAnimOrder == i &&
                mapParam.checkSelectType(FeMapParam.SELECT_UNIT)) {
                tmp.setAnimMode(1);
                mapParam.getRectByGrid(tmp._gridX, tmp._gridY, mapParam.selectUnit);
            }
            else
                tmp.setAnimMode(0);
            tmp.invalidate();
        }
    }

    public boolean checkHit(int type, float x, float y){
        if(type == MotionEvent.ACTION_UP &&
            !mapParam.checkSelectType(FeMapParam.SELECT_MOVE_END))
        {
            FeViewUnit tmp;
            //遍历所有子view
            for (int i = 0; i < getChildCount(); i++) {
                tmp = (FeViewUnit)getChildAt(i);
                if (tmp.checkHit(x, y)) {
                    hitAnimOrder = i;
                    mapParam.setSelectType(FeMapParam.SELECT_UNIT);
                    refresh();
                    return true;
                }
            }
            //
            hitAnimOrder = -1;
            mapParam.cleanSelectType(FeMapParam.SELECT_UNIT);
            refresh();
        }
        return false;
    }

    public FeLayoutUnit(Context context, FeSave save) {
        super(context);
        feSave = save;
        mapParam = feSave.feMapParam;

        loadView(0, 1, 0);
        loadView(1, 2, 1);
//        loadView(3, 3, 0);
//        loadView(4, 4, 1);
//        loadView(5, 5, 0);
//        loadView(6, 6, 1);
//        loadView(7, 7, 0);
        loadView(8, 8, 1);
        loadView(9, 9, 0);
//        loadView(10, 10, 1);
//        loadView(11, 11, 0);
//        loadView(12, 12, 1);
//        loadView(13, 13, 0);
//        loadView(14, 14, 1);

//        loadView(15, 1, 12);
//        loadView(16, 2, 13);
//        loadView(17, 3, 12);
//        loadView(18, 4, 13);
//        loadView(19, 5, 12);
        loadView(20, 6, 13);
        loadView(21, 7, 12);
//        loadView(22, 8, 13);
//        loadView(23, 9, 12);
//        loadView(24, 10, 13);
//        loadView(25, 11, 12);
//        loadView(26, 12, 13);
        loadView(27, 13, 12);
        loadView(28, 14, 13);
    }

    private void loadView(int id, int y, int x){
        addView(new FeViewUnit(feSave.activity, feSave.feHeart, mapParam, id, x, y, 0, 0));
        addView(new FeViewUnit(feSave.activity, feSave.feHeart, mapParam, id, x+2, y, 1, 0));
        addView(new FeViewUnit(feSave.activity, feSave.feHeart, mapParam, id, x+4, y, 2, 0));
        addView(new FeViewUnit(feSave.activity, feSave.feHeart, mapParam, id, x+6, y, 3, 0));
        addView(new FeViewUnit(feSave.activity, feSave.feHeart, mapParam, id, x+8, y, 4, 0));
        addView(new FeViewUnit(feSave.activity, feSave.feHeart, mapParam, id, x+10, y, 5, 0));
    }
}
