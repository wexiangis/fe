package com.example.fe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/*
    通用的根据文件夹和id获取图片方法
 */
class FeAssetsBitmapStruct{

    //关键路径
    private String feSdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FE";

    private Bitmap _load_bitmap(String path){
        Bitmap ret = null;
        if(path.indexOf("/assets") == 0) {
            try{
                InputStream is = getClass().getResourceAsStream(path);
                if(is != null){
                    ret = BitmapFactory.decodeStream(is);
                    is.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("AssetsPng._load_bitmap", "not found : " + path);
            } catch (IOException e) {
                Log.d("AssetsPng._load_bitmap", "IOException : " + path);
            }
        }
        else
            ret = BitmapFactory.decodeFile(path);
        return ret;
    }

    private Bitmap load_png_or_jpg(String folder, String realName) {
        File assetsFilePath = new File("/assets" + folder + realName);
        File sdFileFolderPath = new File(feSdRootPath + folder);
        File sdFilePath = new File(feSdRootPath + folder + realName);
        //sd卡(内置存储)路径准备
        if(!sdFileFolderPath.exists())
            sdFileFolderPath.mkdirs();
        //存在sd卡(内置存储)配置则优先使用该配置
        if(sdFilePath.exists())
            return _load_bitmap(sdFilePath.getPath());
        else
            return _load_bitmap(assetsFilePath.getPath());
    }

    /*
        folder: 示例 "/unit/head/" 前后都带斜杠
        id: 从0数起
     */
    public Bitmap load_png(String folder, int id) {
        String realName = String.format("%03d.png",id);
        return load_png_or_jpg(folder, realName);
    }

    public Bitmap load_jpg(String folder, int id) {
        String realName = String.format("%03d.jpg",id);
        return load_png_or_jpg(folder, realName);
    }
}
