package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    地图中的人物动画管理
 */
public class FeMapUnitLayout extends RelativeLayout {

    private FeSave feSave;
    private FeMapParam mapParam;

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    private int hitOrder = -1;
    public boolean checkHit(int type, float x, float y, boolean isMove){
        if(!isMove)
        {
            //遍历所有子view
            for (int i = 0; i < getChildCount(); i++) {
                FeMapUnitView anim = (FeMapUnitView) getChildAt(i);
                if (anim.checkHit(x, y)) {
                    if (type == MotionEvent.ACTION_UP) {
                        //设置人物动画
                        if (hitOrder == i)
                            anim.setAnimMode(1);
                            //
                        else
                            hitOrder = -1;
                    } else {
                        //释放上一个人物动画
                        if (hitOrder > -1) {
                            FeMapUnitView anim2 = (FeMapUnitView) getChildAt(hitOrder);
                            anim2.setAnimMode(0);
                        }
                        //
                        hitOrder = i;
                    }
                    //
                    mapParam.isSelectUnit = true;
                    return true;
                }
            }
        }
        //释放上一个人物动画
        if(hitOrder > -1){
            FeMapUnitView anim2 = (FeMapUnitView)getChildAt(hitOrder);
            anim2.setAnimMode(0);
        }
        //
        hitOrder = -1;
        return false;
    }

    public FeMapUnitLayout(Context context, FeSave save, FeMapParam feMapParam) {
        super(context);
        feSave = save;
        mapParam = feMapParam;

        loadView(R.drawable.ma_001, 1, 0);
//        loadView(R.drawable.ma_002, 2, 1);
//        loadView(R.drawable.ma_003, 3, 0);
//        loadView(R.drawable.ma_004, 4, 1);
//        loadView(R.drawable.ma_005, 5, 0);
//        loadView(R.drawable.ma_006, 6, 1);
//        loadView(R.drawable.ma_007, 7, 0);
//        loadView(R.drawable.ma_008, 8, 1);
//        loadView(R.drawable.ma_009, 9, 0);
//        loadView(R.drawable.ma_010, 10, 1);
//        loadView(R.drawable.ma_011, 11, 0);
//        loadView(R.drawable.ma_012, 12, 1);
//        loadView(R.drawable.ma_013, 13, 0);
//        loadView(R.drawable.ma_014, 14, 1);
//
//        loadView(R.drawable.ma_015, 1, 12);
//        loadView(R.drawable.ma_016, 2, 13);
        loadView(R.drawable.ma_017, 3, 12);
//        loadView(R.drawable.ma_018, 4, 13);
//        loadView(R.drawable.ma_019, 5, 12);
//        loadView(R.drawable.ma_020, 6, 13);
//        loadView(R.drawable.ma_021, 7, 12);
//        loadView(R.drawable.ma_022, 8, 13);
//        loadView(R.drawable.ma_023, 9, 12);
//        loadView(R.drawable.ma_024, 10, 13);
//        loadView(R.drawable.ma_025, 11, 12);
//        loadView(R.drawable.ma_026, 12, 13);
//        loadView(R.drawable.ma_027, 13, 12);
//        loadView(R.drawable.ma_028, 14, 13);
    }

    private void loadView(int id, int y, int x){
        addView(new FeMapUnitView(feSave.activity, feSave.feHeart, mapParam, id, x, y, 0, 0));
        addView(new FeMapUnitView(feSave.activity, feSave.feHeart, mapParam, id, x+2, y, 1, 0));
        addView(new FeMapUnitView(feSave.activity, feSave.feHeart, mapParam, id, x+4, y, 2, 0));
        addView(new FeMapUnitView(feSave.activity, feSave.feHeart, mapParam, id, x+6, y, 3, 0));
        addView(new FeMapUnitView(feSave.activity, feSave.feHeart, mapParam, id, x+8, y, 4, 0));
        addView(new FeMapUnitView(feSave.activity, feSave.feHeart, mapParam, id, x+10, y, 5, 0));
    }
}
