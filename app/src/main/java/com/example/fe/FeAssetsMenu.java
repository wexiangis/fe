package com.example.fe;

import android.graphics.Bitmap;

/*
    /assets/menu 文件夹资源管理器
 */
public class FeAssetsMenu {

    private FeReaderBitmap bitmapReader = new FeReaderBitmap();

    // /menu/map/xxx.png
    void FeDataMenu(){
        bitmapReader = new FeReaderBitmap();
    }
    public Bitmap getMapCompare(){
        return bitmapReader.load_bitmap("/menu/map/", "compare.png");
    }
    public Bitmap getMapHeader(){
        return bitmapReader.load_bitmap("/menu/map/", "header.png");
    }
    public Bitmap getMapInfo(){
        return bitmapReader.load_bitmap("/menu/map/", "mapinfo.png");
    }
    public Bitmap getMapSelect(){
        return bitmapReader.load_bitmap("/menu/map/", "select.png");
    }
    public Bitmap getMapTarget(){
        return bitmapReader.load_bitmap("/menu/map/", "target.png");
    }

}
