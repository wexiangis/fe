package com.example.fe;

import android.graphics.Bitmap;

public class FeMapInfo {

    public int section;

    //图片: map.jpg
    public Bitmap bitmap = null;

    //基本参数: size.txt
    public int xGrid = 10;
    public int yGrid = 10;
    public int pixelPerGrid = 104;
    public int transferGrid = 0;

    //矩阵: grid.txt
    public short[][] grid;

    //方格类型信息: grid_info.txt
    public String[] name;
    public short[] defend;
    public short[] avoid;
    public short[] plus;
    public short[] mov;
    public short[] type;
    public String[] info;

    public FeMapInfo(int section){
        this.section = section;
    }

}
