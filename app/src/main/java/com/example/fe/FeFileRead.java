package com.example.fe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
    按行读文件工具
 */
public class FeFileRead {
    private String _path;

    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;

    private String lineContent;
    private String[] content;
    private int line = -1;

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
            Log.d("FeFileRead: readLine " + _path, e.getMessage());
        }
        return false;
    }

    public FeFileRead(String path){
        _path = path;
        is = getClass().getResourceAsStream(_path);
        if(is != null) {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
        }
    }

    public void exit(){
        try {
            if (br != null)
                br.close();
            if (isr != null)
                isr.close();
            if (is != null)
                is.close();
        } catch (IOException e) {
            Log.d("FeFileRead: exit " + _path, e.getMessage());
        }
    }
}