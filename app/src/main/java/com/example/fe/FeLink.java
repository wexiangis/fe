package com.example.fe;

/*
    通用的链表结构
 */
public class FeLink<U> {
    public U data = null;
    public FeLink<U> previous = null, next = null;
    public FeLink(U dat){
        data = dat;
    }
}
