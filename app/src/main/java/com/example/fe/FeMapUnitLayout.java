package com.example.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    地图中的人物动画管理
 */
public class FeMapUnitLayout extends RelativeLayout {

    private FeSave feSave;

    public FeMapUnitLayout(Context context, FeSave save) {
        super(context);
        feSave = save;

//        loadView(R.drawable.ma_001, 5, 5);

//        loadView(R.drawable.ma_001, 1, 0);
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

    private void loadView(int id, int y, int x){
//        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, feSave.feMapParam, id, x, y, 0, 0));
//        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, feSave.feMapParam, id, x+2, y, 1, 0));
//        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, feSave.feMapParam, id, x+4, y, 2, 0));
//        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, feSave.feMapParam, id, x+6, y, 3, 0));
//        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, feSave.feMapParam, id, x+8, y, 4, 0));
//        addView(new FeAnimFilm(feSave.activity, feSave.feHeart, feSave.feMapParam, id, x+10, y, 5, 0));
    }
}
