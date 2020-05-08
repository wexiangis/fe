package com.example.fe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FeMapRead {
    //关键路径
    private String feSdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FE";
    private String assetsFilePath, sdFilePath;
    //基本参数
    private int xGrid = 10;
    private int yGrid = 10;
    private int pixelPerGrid = 104;
    private int transferGrid = 0;

    public int getXGrid() {return xGrid;}
    public int getYGrid() {return yGrid;}
    public int getPixelPerGrid() {return pixelPerGrid;}
    public int getTransferGrid() {return transferGrid;}

    public byte[][] getGrid(){
        return null;
    }

    public Bitmap getBitmap(){
        File sdBitmapPath = new File(sdFilePath + "map.jpg");
        if(sdBitmapPath.exists())
            return BitmapFactory.decodeFile(sdBitmapPath.getPath());
        else{
            Bitmap ret = null;
            try{
                InputStream is = getClass().getResourceAsStream(assetsFilePath + "map.jpg");
                if(is != null){
                    ret = BitmapFactory.decodeStream(is);
                    is.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("FeMapRead", "open bitmap not found");
            } catch (IOException e) {
                Log.d("FeMapRead", "read bitmap IOException");
            }
            return ret;
        }
    }

    private void load_size_txt(){
        File sdSizePath = new File(sdFilePath + "size.txt");
        if(sdSizePath.exists()){
            try {
                FileInputStream fis = new FileInputStream(sdSizePath.getPath());
                byte[] line = new byte[100];
                if(fis.read(line) > 0){
                    String[] dat = line.toString().split(";");
                    if(dat.length > 0) xGrid = Integer.parseInt(dat[0]);
                    if(dat.length > 1) yGrid = Integer.parseInt(dat[1]);
                    if(dat.length > 2) pixelPerGrid = Integer.parseInt(dat[2]);
                    if(dat.length > 3) transferGrid = Integer.parseInt(dat[3]);
                }
                fis.close();
            } catch (java.io.FileNotFoundException e) {
                Log.d("FeMapRead", "open" + sdSizePath.getPath() + " not found");
            } catch (java.io.IOException e) {
                Log.d("FeMapRead", "read" + sdSizePath.getPath() + " IOException");
            }
        }
        else {
            try {
                InputStream is = getClass().getResourceAsStream(assetsFilePath + "size.txt");
                if (is != null) {
                    byte[] line = new byte[100];
                    if (is.read(line) >= 4) {
                        String[] dat = new String(line).split(";");
                        if (dat.length > 0) xGrid = Integer.parseInt(dat[0]);
                        if (dat.length > 1) yGrid = Integer.parseInt(dat[1]);
                        if (dat.length > 2) pixelPerGrid = Integer.parseInt(dat[2]);
                        if (dat.length > 3) transferGrid = Integer.parseInt(dat[3]);
                    }
                    is.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("FeMapRead", "open" + assetsFilePath + "size.txt" + " not found");
            } catch (java.io.IOException e) {
                Log.d("FeMapRead", "read" + assetsFilePath + "size.txt" + " IOException");
            }
        }
    }

    public FeMapRead(int section) {
        String mapPath = "/map"+String.format("%02d/",section);

        assetsFilePath = "/assets" + mapPath;
        sdFilePath = feSdRootPath + mapPath;

        load_size_txt();
    }

}
