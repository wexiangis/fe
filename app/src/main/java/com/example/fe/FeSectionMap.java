package com.example.fe;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class FeSectionMap extends RelativeLayout {

    private FeSave feSave;

    public FeMapView mapView = null;

    public int xGridPiexl = 0, yGridPiexl = 0;

    private void loadView(){
        //
        mapView = new FeMapView(feSave.activity, feSave.feMapParam, R.drawable.map_000_25x15, 25, 15);
        this.addView(mapView);
        //
        xGridPiexl = (int)feSave.feMapParam.xGridPixel;
        yGridPiexl = (int)feSave.feMapParam.yGridPixel;
        //
        FeAnimFilm anim = new FeAnimFilm(feSave.activity, feSave.feMapParam, R.drawable.mas_001,
                16, 16, 0, 0,
                xGridPiexl, yGridPiexl,
                150, new int[]{3, 1, 3},
                0, 0);
        this.addView(anim);
        anim.move(xGridPiexl*6, yGridPiexl*5);
        //
        FeAnimFilm anim2 = new FeAnimFilm(feSave.activity, feSave.feMapParam, R.drawable.mas_003,
                16, 16, 0, 0,
                (int)feSave.feMapParam.xGridPixel, (int)feSave.feMapParam.yGridPixel,
                150, new int[]{3, 1, 3},
                0, 1);
        this.addView(anim2);
        anim2.move(xGridPiexl*8, yGridPiexl*5);
        //
        FeAnimFilm anim3 = new FeAnimFilm(feSave.activity, feSave.feMapParam, R.drawable.mas_004,
                16, 16, 0, 0,
                (int)feSave.feMapParam.xGridPixel, (int)feSave.feMapParam.yGridPixel,
                150, new int[]{3, 1, 3},
                0, 2);
        this.addView(anim3);
        anim3.move(xGridPiexl*6, yGridPiexl*7);
        //
        FeAnimFilm anim4 = new FeAnimFilm(feSave.activity, feSave.feMapParam, R.drawable.mas_005,
                16, 16, 0, 0,
                (int)feSave.feMapParam.xGridPixel, (int)feSave.feMapParam.yGridPixel,
                150, new int[]{3, 1, 3},
                0, 0);
        this.addView(anim4);
        anim4.move(xGridPiexl*8, yGridPiexl*7);
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 1; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if(child != null && child != mapView){
                //调用一次onDraw
                child.invalidate();
            }
        }
    }

    public FeSectionMap(Context context, FeSave save){
        super(context);
        feSave = save;
        feSave.setFeSectionMap(this);
        loadView();
    }

}
