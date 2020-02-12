package com.example.fe;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/*
    地图和地图人物动画管理
 */
public class FeSectionMap extends RelativeLayout {

    public class ComLayout extends RelativeLayout{
        public ComLayout(Context context){
            super(context);
        }
    }

    private FeSave feSave;
    private FeMapParam feMapParam= new FeMapParam();
    public ComLayout mapLayout = null, animLayout = null;

    private void loadMapView(){
        FeMap map = new FeMap(feSave.activity, feSave.feHeart, feMapParam, R.drawable.map_000_25x15, 25, 15);
//        FeMap map = new FeMap(feSave.activity, feSave.feHeart, feMapParam, R.drawable.map_000_25x15, 25, 15);
        mapLayout.addView(map);
    }

    private void loadView(int id, int y, int x){
        FeAnimFilm anim = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x, y, 0, 0);
        animLayout.addView(anim);
//        FeAnimFilm anim2 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+2, y, 1, 0);
//        animLayout.addView(anim2);
//        FeAnimFilm anim3 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+4, y, 2, 0);
//        animLayout.addView(anim3);
//        FeAnimFilm anim4 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+6, y, 3, 0);
//        animLayout.addView(anim4);
//        FeAnimFilm anim5 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+8, y, 4, 0);
//        animLayout.addView(anim5);
//        FeAnimFilm anim6 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+10, y, 5, 0);
//        animLayout.addView(anim6);
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < animLayout.getChildCount(); i++)
            animLayout.getChildAt(i).invalidate();
    }

    private int hitOrder = -1;
    public boolean checkHit(int type, float x, float y){
        //遍历所有子view
        for (int i = 0; i < animLayout.getChildCount(); i++) {
            FeAnimFilm anim = (FeAnimFilm)animLayout.getChildAt(i);
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
                        FeAnimFilm anim2 = (FeAnimFilm)animLayout.getChildAt(hitOrder);
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
            FeAnimFilm anim2 = (FeAnimFilm)animLayout.getChildAt(hitOrder);
            anim2.setAnimMode(0);
        }
        //
        hitOrder = -1;
        return false;
    }

    public FeSectionMap(Context context, FeSave save){
        super(context);
        feSave = save;
        feSave.setFeSectionMap(this);
        //地图图层
        mapLayout = new ComLayout(feSave.activity);
        this.addView(mapLayout);
        //地图人物动画图层
        animLayout = new ComLayout(feSave.activity);
        this.addView(animLayout);
        //地图提示和菜单图层
        ;
        //人物操作菜单图层
        ;
        //人物对话图层
        ;
        //其它图层
        ;
        //
        loadMapView();
        //
        loadView(R.drawable.ma_001, 1, 0);
        loadView(R.drawable.ma_002, 2, 1);
        loadView(R.drawable.ma_003, 3, 0);
        loadView(R.drawable.ma_004, 4, 1);
        loadView(R.drawable.ma_005, 5, 0);
        loadView(R.drawable.ma_006, 6, 1);
        loadView(R.drawable.ma_007, 7, 0);
        loadView(R.drawable.ma_008, 8, 1);
        loadView(R.drawable.ma_009, 9, 0);
        loadView(R.drawable.ma_010, 10, 1);
        loadView(R.drawable.ma_011, 11, 0);
        loadView(R.drawable.ma_012, 12, 1);
        loadView(R.drawable.ma_013, 13, 0);

        loadView(R.drawable.ma_014, 1, 12);
        loadView(R.drawable.ma_015, 2, 13);
        loadView(R.drawable.ma_016, 3, 12);
        loadView(R.drawable.ma_017, 4, 13);
        loadView(R.drawable.ma_018, 5, 12);
        loadView(R.drawable.ma_019, 6, 13);
        loadView(R.drawable.ma_020, 7, 12);
        loadView(R.drawable.ma_021, 8, 13);
        loadView(R.drawable.ma_022, 9, 12);
        loadView(R.drawable.ma_023, 10, 13);
        loadView(R.drawable.ma_024, 11, 12);
        loadView(R.drawable.ma_025, 12, 13);
        loadView(R.drawable.ma_026, 13, 12);
    }

}
