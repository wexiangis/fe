package com.example.fe;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class FeSectionMap extends RelativeLayout {

    //
    private FeEvent feEvent;

    public FeSectionMap(Context context, FeEvent event){
        super(context);
        feEvent = event;
        //
//        this.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        this.addView(new FeMapView(context, R.drawable.map_000_25x15, 25, 15));
    }

}
