package com.example.fe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class FeTools {

    public Bitmap getAssetsBitmap(String path)
    {
        Bitmap ret = null;
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if(is != null){
                ret = BitmapFactory.decodeStream(is);
                is.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("getAssetsBitmap: ", "not found");
        } catch (IOException e) {
            Log.d("getAssetsBitmap: ", e.getMessage());
        }
        return ret;
    }

    public Bitmap getAssetsUnitAnim(int id)
    {
        return getAssetsBitmap("/assets/unit/anim/"+String.format("%03d.png",id));
    }
}
