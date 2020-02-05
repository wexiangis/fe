package com.example.fe;

public class FeHeartUnit {

    public interface TimeOutTask{
        public void run(int count);
    }

    public int type = 0;
    public boolean useless = false;
    public FeHeartUnit.TimeOutTask task = null;
    public FeHeartUnit(int t, FeHeartUnit.TimeOutTask ta){
        type = t;
        task = ta;
    }
}
