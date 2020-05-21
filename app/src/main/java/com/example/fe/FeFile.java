package com.example.fe;

import android.os.Environment;

import java.io.File;

/*
    用来创建、查询手机内存FE文件夹中的文件
 */
public class FeFile{
    //关键路径
    private String feSdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FE";

    /*
        folder: 目标文件夹, 示例 "/unit/" 前后都带斜杠
        name: 目标名称, 示例 "test.txt" 没有斜杠
     */
    public Boolean isExists(String folder, String name) {
        return new File(feSdRootPath + folder + name).exists();
    }
    
}