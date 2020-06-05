package com.example.fe;

public class FeFormat {

    public static int StringToInt(String string){
        try{
            return Integer.valueOf(string);
        }catch(java.lang.NumberFormatException e){
            return 0;
        }
    }
}
