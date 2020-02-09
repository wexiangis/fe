package com.example.fe;

import android.content.Context;
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
        FeMap map = new FeMap(feSave.activity, feSave.feHeart, feMapParam, R.drawable.map_002_15x10, 15, 10);
//        FeMap map = new FeMap(feSave.activity, feSave.feHeart, feMapParam, R.drawable.map_000_25x15, 25, 15);
        mapLayout.addView(map);
    }

    private void loadView(int id, int y, int x){
        FeAnimFilm anim = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x, y, 0, 0);
        animLayout.addView(anim);
        FeAnimFilm anim2 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+2, y, 1, 0);
        animLayout.addView(anim2);
        FeAnimFilm anim3 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+4, y, 2, 0);
        animLayout.addView(anim3);
        FeAnimFilm anim4 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+6, y, 3, 0);
        animLayout.addView(anim4);
        FeAnimFilm anim5 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+8, y, 4, 0);
        animLayout.addView(anim5);
        FeAnimFilm anim6 = new FeAnimFilm(feSave.activity, feSave.feHeart, feMapParam, id, x+10, y, 5, 0);
        animLayout.addView(anim6);
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < animLayout.getChildCount(); i++)
            animLayout.getChildAt(i).invalidate();
    }

    public FeSectionMap(Context context, FeSave save){
        super(context);
        feSave = save;
        feSave.setFeSectionMap(this);
        //
        mapLayout = new ComLayout(feSave.activity);
        this.addView(mapLayout);
        animLayout = new ComLayout(feSave.activity);
        this.addView(animLayout);
        //
        loadMapView();
        //
        loadView(R.drawable.ma_077, 1, 0);
        loadView(R.drawable.ma_078, 2, 1);
        loadView(R.drawable.ma_079, 3, 0);
        loadView(R.drawable.ma_080, 4, 1);
        loadView(R.drawable.ma_081, 5, 0);
        loadView(R.drawable.ma_082, 6, 1);
        loadView(R.drawable.ma_083, 7, 0);
        loadView(R.drawable.ma_084, 8, 1);
        loadView(R.drawable.ma_085, 9, 0);
        loadView(R.drawable.ma_086, 10, 1);
        loadView(R.drawable.ma_087, 11, 0);
        loadView(R.drawable.ma_088, 12, 1);
        loadView(R.drawable.ma_089, 13, 0);

//        loadView(R.drawable.ma_090, 1, 12);
//        loadView(R.drawable.ma_091, 2, 13);
//        loadView(R.drawable.ma_092, 3, 12);
//        loadView(R.drawable.ma_093, 4, 13);
//        loadView(R.drawable.ma_094, 5, 12);
//        loadView(R.drawable.ma_095, 6, 13);
//        loadView(R.drawable.ma_096, 7, 12);
//        loadView(R.drawable.ma_097, 8, 13);
//        loadView(R.drawable.ma_098, 9, 12);
//        loadView(R.drawable.ma_099, 10, 13);
//        loadView(R.drawable.ma_100, 11, 12);
//        loadView(R.drawable.ma_101, 12, 13);
//        loadView(R.drawable.ma_102, 13, 12);
    }

}
