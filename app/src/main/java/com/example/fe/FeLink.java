package com.example.fe;

public class FeLink<U> {
    public U data = null;
    public FeLink<U> previous = null, next = null;
    public FeLink(U dat){
        data = dat;
    }
}
