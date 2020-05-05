package com.example.fe;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/*
    按行写文件工具
 */
public class FeFileWrite {
    //关键路径
    private String feSdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FE";
    private String filePath;
    //各种玄学句柄
    private FileOutputStream fos;
    private OutputStreamWriter osw;

    public void write(String value){
        try {
            if (osw != null){
                osw.write(value);
                osw.flush();
            }
        } catch (java.io.IOException e) {
            Log.d("FeFileWrite: write " + filePath, e.getMessage());
        }
    }

    public void writeLine(String spl, String ... argv){
        try {
            if (osw != null){
                for(int i = 0; i < argv.length; i++){
                    osw.write(argv[i]);
                    osw.write(spl);
                }
                osw.write("\r\n");
                osw.flush();
            }
        } catch (java.io.IOException e) {
            Log.d("FeFileWrite: writeLine " + filePath, e.getMessage());
        }
    }

    /*
        folder: 示例 "/unit/" 前后都带斜杠
        path: 示例 "test.txt" 没有斜杠
     */
    public FeFileWrite(String folder, String path) {
        //文件夹检查
        File sdFileFolderPath = new File(feSdRootPath + folder);
        if(!sdFileFolderPath.exists())
            sdFileFolderPath.mkdirs();
        //文件写到sd卡(内置)存储
        filePath = feSdRootPath + folder + path;
        try {
            fos = new FileOutputStream(filePath);
            if(fos != null)
                osw = new OutputStreamWriter(fos, "UTF-8");
        }catch (java.io.FileNotFoundException e) {
            Log.d("FeFileWrite: open " + filePath, "not found");
        }catch (java.io.UnsupportedEncodingException e) {
            Log.d("FeFileWrite: open " + filePath, e.getMessage());
        }
    }

    public void exit(){
        try {
            if(fos != null)
                fos.close();
            if (fos != null)
                fos.close();
        } catch (java.io.IOException e) {
            Log.d("FeFileWrite: exit " + filePath, e.getMessage());
        }
    }
}
