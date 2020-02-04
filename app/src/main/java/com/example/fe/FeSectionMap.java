package com.example.fe;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class FeSectionMap extends RelativeLayout {

    private FeSave feSave;

    public FeMapView mapView = null;

    public int xGridPiexl = 0, yGridPiexl = 0;

    private void loadMapView(){
        mapView = new FeMapView(feSave.activity, feSave.feMapParam, R.drawable.map_000_25x15, 25, 15);
        this.addView(mapView);
    }

    private void loadView(int id, int lineY, int lineX){
        //
        xGridPiexl = (int)(feSave.feMapParam.xGridPixel*2);
        yGridPiexl = (int)(feSave.feMapParam.yGridPixel*2);
        int offsetX = -xGridPiexl/4;
        int offsetY = -yGridPiexl/2;
        //
        FeAnimFilm anim = new FeAnimFilm(feSave.activity, feSave.feMapParam, id,
                32, 32, 3, 12+3,
                offsetX, offsetY, xGridPiexl, yGridPiexl,
                150, new int[]{3, 1, 3},
                0, 0);
        this.addView(anim);
        anim.moveGridTo(lineX, lineY);
        //
        FeAnimFilm anim2 = new FeAnimFilm(feSave.activity, feSave.feMapParam, id,
                32, 32, 3, 12,
                offsetX, offsetY, xGridPiexl, yGridPiexl,
                150, new int[]{3, 1, 3},
                0, 0);
        this.addView(anim2);
        anim2.moveGridTo(lineX+2, lineY);
        //
        FeAnimFilm anim3 = new FeAnimFilm(feSave.activity, feSave.feMapParam, id,
                32, 32, 4, 0,
                offsetX, offsetY, xGridPiexl, yGridPiexl,
                150, new int[]{1},
                1, 0);
        this.addView(anim3);
        anim3.moveGridTo(lineX+4, lineY);
        //
        FeAnimFilm anim4 = new FeAnimFilm(feSave.activity, feSave.feMapParam, id,
                32, 32, 4, 4,
                offsetX, offsetY, xGridPiexl, yGridPiexl,
                150, new int[]{1},
                1, 0);
        this.addView(anim4);
        anim4.moveGridTo(lineX+6, lineY);
        //
        FeAnimFilm anim5 = new FeAnimFilm(feSave.activity, feSave.feMapParam, id,
                32, 32, 4, 8,
                offsetX, offsetY, xGridPiexl, yGridPiexl,
                150, new int[]{1},
                1, 0);
        this.addView(anim5);
        anim5.moveGridTo(lineX+8, lineY);
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
        //
        loadMapView();
        //
        loadView(R.drawable.ma_053, 1, 1);
        loadView(R.drawable.ma_054, 2, 2);
        loadView(R.drawable.ma_029, 3, 1);
        loadView(R.drawable.ma_030, 4, 2);
        loadView(R.drawable.ma_031, 5, 1);
        loadView(R.drawable.ma_032, 6, 2);
        loadView(R.drawable.ma_033, 7, 1);
        loadView(R.drawable.ma_034, 8, 2);
        loadView(R.drawable.ma_035, 9, 1);
        loadView(R.drawable.ma_036, 10, 2);
        loadView(R.drawable.ma_037, 11, 1);
        loadView(R.drawable.ma_038, 12, 2);
        loadView(R.drawable.ma_039, 13, 1);

        loadView(R.drawable.ma_040, 1, 11);
        loadView(R.drawable.ma_041, 2, 12);
        loadView(R.drawable.ma_042, 3, 11);
        loadView(R.drawable.ma_043, 4, 12);
        loadView(R.drawable.ma_044, 5, 11);
        loadView(R.drawable.ma_045, 6, 12);
        loadView(R.drawable.ma_046, 7, 11);
        loadView(R.drawable.ma_047, 8, 12);
        loadView(R.drawable.ma_048, 9, 11);
        loadView(R.drawable.ma_049, 10, 12);
        loadView(R.drawable.ma_050, 11, 11);
        loadView(R.drawable.ma_051, 12, 12);
        loadView(R.drawable.ma_052, 13, 11);
    }

}
