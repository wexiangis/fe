package com.example.fe;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
    按行读文件工具
 */
public class FeFileRead {
    //关键路径
    private String feSdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FE";
    private String filePath;
    //各种玄学句柄
    private FileInputStream fis;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;
    //每次调用 readLine() 后得到的数据
    private String lineContent;//行原始数据
    private String[] content;//按分隔符处理后的行数据
    private int line = -1;//当前行数

    public String[] getContent(){
        return content;
    }

    public String getLineContent(){
        return lineContent;
    }

    public int getLine(){
        if(line < 0)
            return 0;
        return line;
    }

    public int getInt(int count){
        if(content != null && content.length > count)
            return Integer.valueOf(content[count]);
        return -1;
    }

    public String getString(int count){
        if(content != null && content.length > count)
            return content[count];
        return "";
    }

    public boolean readLine(String spl){
        try{
            if(br == null)
                return false;
            lineContent = br.readLine();
            content = lineContent.split(spl);
            line += 1;
            return true;
        } catch (IOException e) {
            Log.d("FeFileRead: readLine " + filePath, e.getMessage());
        }
        return false;
    }

    /*
        folder: 示例 "/unit/" 前后都带斜杠
        path: 示例 "test.txt" 没有斜杠
     */
    public FeFileRead(String folder, String path) {
        File assetsFilePath = new File("/assets" + folder + path);
        File sdFilePath = new File(feSdRootPath + folder + path);
        File sdFileFolderPath = new File(feSdRootPath + folder);
        //sd卡(内置存储)路径准备
        if(!sdFileFolderPath.exists())
            sdFileFolderPath.mkdirs();
        //存在sd卡(内置存储)配置则优先使用该配置
        if(sdFilePath.exists())
            filePath = sdFilePath.getPath();
        else
            filePath = assetsFilePath.getPath();
        //inputStream
        if (filePath.indexOf("/assets") == 0)
            is = getClass().getResourceAsStream(filePath);
        else {
            try {
                fis = new FileInputStream(filePath);
            } catch (java.io.FileNotFoundException e) {
                Log.d("FeFileRead: open " + filePath, "not found");
            }
        }
        //reader
        if (is != null) {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
        } else if (is != null) {
            try {
                isr = new InputStreamReader(fis, "UTF-8");
                if(isr != null)
                    br = new BufferedReader(isr);
            }catch (java.io.UnsupportedEncodingException e) {
                Log.d("FeFileRead: open " + filePath, e.getMessage());
            }
        }
    }

    public Boolean ready(){
        if(br != null)
            return true;
        return false;
    }

    public void exit(){
        try {
            if (br != null)
                br.close();
            if (isr != null)
                isr.close();
            if (is != null)
                is.close();
            if(fis != null)
                fis.close();
        } catch (IOException e) {
            Log.d("FeFileRead: exit " + filePath, e.getMessage());
        }
    }
}