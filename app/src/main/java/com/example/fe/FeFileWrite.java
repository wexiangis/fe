package com.example.fe;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/*
    按行写文件工具
 */
public class FeFileWrite {
    private String _path;

    private FileOutputStream fos;
    private OutputStreamWriter osw;

    public void write(String value){
        try {
            if (osw != null){
                osw.write(value);
                osw.flush();
            }
        } catch (java.io.IOException e) {
            Log.d("FeFileWrite: write " + _path, e.getMessage());
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
            Log.d("FeFileWrite: writeLine " + _path, e.getMessage());
        }
    }

    public FeFileWrite(String path){
        _path = path;
        try {
            fos = new FileOutputStream(_path);
            if(fos != null)
                osw = new OutputStreamWriter(fos, "UTF-8");
        }catch (java.io.FileNotFoundException e) {
            Log.d("FeFileWrite: open " + _path, "not found");
        }catch (java.io.UnsupportedEncodingException e) {
            Log.d("FeFileWrite: open " + _path, e.getMessage());
        }
    }

    public void exit(){
        try {
            if(fos != null)
                fos.close();
            if (fos != null)
                fos.close();
        } catch (java.io.IOException e) {
            Log.d("FeFileWrite: exit " + _path, e.getMessage());
        }
    }
}
