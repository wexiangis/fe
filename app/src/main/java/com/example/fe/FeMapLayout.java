package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    加载和管理地图及地形动画
 */
public class FeMapLayout extends RelativeLayout {

    private FeSave feSave;

    public FeMapLayout(Context context, FeSave save) {
        super(context);
        feSave = save;
        //
        loadView();
    }

    private void loadView(){
//        addView(new FeMap(feSave.activity, feSave.feHeart, feSave.feMapParam, R.drawable.map_000_25x15, 25, 15));
        addView(new FeMap(feSave.activity, feSave.feHeart, feSave.feMapParam, R.drawable.map_001_26x25, 26, 25));
//        addView(new FeMap(feSave.activity, feSave.feHeart, feSave.feMapParam, R.drawable.map_002_15x10, 15, 10));
//        addView(new FeMap(feSave.activity, feSave.feHeart, feSave.feMapParam, R.drawable.map_003_30x30, 30, 30));
    }

}
