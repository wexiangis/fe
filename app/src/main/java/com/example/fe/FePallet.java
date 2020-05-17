package com.example.fe;

import android.graphics.Bitmap;

/*
    调色板
 */
public class FePallet {

    //type: 0/原色 1/绿色 2/红色 3/灰色 4/橙色 5/紫色 6/不蓝不绿
    public static Bitmap replace(Bitmap oldBitmap, int type){
        if(type == 0)   //使用原色
            return oldBitmap;
        //相关说明可参考 http://xys289187120.blog.51cto.com/3361352/657590/
        Bitmap mBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //循环获得bitmap所有像素点
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        int r, g, b, color, tcolor, tmp;

        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                color = mBitmap.getPixel(j, i);
                tcolor = color&0xff000000;
                r = (color&0x00ff0000)>>16;
                g = (color&0x0000ff00)>>8;
                b = color&0x000000ff;
                if(b > g && b > r) {
                    if (type == 1) {         //蓝色 换 绿色
                        tcolor |= (int) (r * 0.5) << 16;
                        tcolor |= (int) (b * 0.9) << 8;
                        tcolor |= (int) (g * 0.5) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 2) {    //蓝色 换 红色
                        tcolor |= (int) (b * 1.0) << 16;
                        tcolor |= (int) (g * 0.5) << 8;
                        tcolor |= (int) (r * 0.5) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 3) {    //蓝色 换 黑色
                        tmp = r + g + b;
                        r = g = b = tmp/4;
                        tcolor |= (int) (r * 1.0) << 16;
                        tcolor |= (int) (g * 1.0) << 8;
                        tcolor |= (int) (b * 1.0) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 4) {    //蓝色 换 橙色
                        tcolor |= (int) (b * 1.0) << 16;
                        tcolor |= (int) (g * 1.0) << 8;
                        tcolor |= (int) (r * 0.5) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 5) {    //蓝色 换 红紫色
                        tcolor |= (int) (b * 0.8) << 16;
                        tcolor |= (int) (r * 0.5) << 8;
                        tcolor |= (int) (g * 1.0) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }else if (type == 6) {    //蓝色 换 红紫色
                        tcolor |= (int) (r * 0.8) << 16;
                        tcolor |= (int) (b * 0.8) << 8;
                        tcolor |= (int) (b * 0.8) << 0;
                        mBitmap.setPixel(j, i, tcolor);
                    }
                }
            }
        }
        return mBitmap;
    }
}