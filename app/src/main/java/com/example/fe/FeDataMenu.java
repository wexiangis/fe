package com.example.fe;

import android.graphics.Bitmap;

public class FeDataMenu {

    private FeAssetsBitmapReader bitmapStruct;

    void FeDataMenu(){
        bitmapStruct = new FeAssetsBitmapReader();
    }

    public Bitmap getMapCompare(){
        return bitmapStruct.load_bitmap("/menu/map/", "compare.png");
    }

    public Bitmap getMapHeader(){
        return bitmapStruct.load_bitmap("/menu/map/", "header.png");
    }

    public Bitmap getMapInfo(){
        return bitmapStruct.load_bitmap("/menu/map/", "mapinfo.png");
    }

    public Bitmap getMapSelect(){
        return bitmapStruct.load_bitmap("/menu/map/", "select.png");
    }

    public Bitmap getMapTarget(){
        return bitmapStruct.load_bitmap("/menu/map/", "target.png");
    }

}
