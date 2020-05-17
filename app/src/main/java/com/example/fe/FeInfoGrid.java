package com.example.fe;

import android.graphics.Path;
import android.graphics.Rect;

public class FeInfoGrid {
    //方格梯形
    public Path selectPath = new Path();
    //方格
    public Rect selectRect = new Rect(0,0,0,0);
    //方格横纵格数
    public int[] selectPoint = new int[2];
    //
    public void clean(){
        selectPath.reset();
    }
}
