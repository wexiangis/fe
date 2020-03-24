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
                FeAnimFilm anim = (FeAnimFilm) getChildAt(i);
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
                            FeAnimFilm anim2 = (FeAnimFilm) getChildAt(hitOrder);
                            anim2.setAnimMode(0);
                        }
                        //
                        hitOrder = i;
                    }
                    return true;
                }
            }
        }
        //释放上一个人物动画
        if(hitOrder > -1){
            FeAnimFilm anim2 = (FeAnimFilm)getChildAt(hitOrder);
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

        loadView(R.drawable.ma_076, 1, 0);
        loadView(R.drawable.ma_077, 2, 1);
        loadView(R.drawable.ma_078, 3, 0);
        loadView(R.drawable.ma_079, 4, 1);
        loadView(R.drawable.ma_080, 5, 0);
        loadView(R.drawable.ma_081, 6, 1);
        loadView(R.drawable.ma_082, 7, 0);
        loadView(R.drawable.ma_083, 8, 1);
        loadView(R.drawable.ma_084, 9, 0);
        loadView(R.drawable.ma_085, 10, 1);
        loadView(R.drawable.ma_086, 11, 0);
        loadView(R.drawable.ma_087, 12, 1);
        loadView(R.drawable.ma_088, 13, 0);
        loadView(R.drawable.ma_088, 14, 1);

        loadView(R.drawable.ma_089, 1, 12);
        loadView(R.drawable.ma_090, 2, 13);
        loadView(R.drawable.ma_091, 3, 12);
        loadView(R.drawable.ma_092, 4, 13);
        loadView(R.drawable.ma_093, 5, 12);
        loadView(R.drawable.ma_094, 6, 13);
        loadView(R.drawable.ma_095, 7, 12);
        loadView(R.drawable.ma_096, 8, 13);
        loadView(R.drawable.ma_097, 9, 12);
        loadView(R.drawable.ma_098, 10, 13);
        loadView(R.drawable.ma_099, 11, 12);
        loadView(R.drawable.ma_100, 12, 13);
        loadView(R.drawable.ma_101, 13, 12);
        loadView(R.drawable.ma_102, 14, 13);
    }

    private void loadView(int id, int y, int x){
        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, mapParam, id, x, y, 0, 0));
        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, mapParam, id, x+2, y, 1, 0));
        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, mapParam, id, x+4, y, 2, 0));
        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, mapParam, id, x+6, y, 3, 0));
        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, mapParam, id, x+8, y, 4, 0));
        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, mapParam, id, x+10, y, 5, 0));
    }
}
