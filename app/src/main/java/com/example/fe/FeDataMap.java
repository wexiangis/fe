package com.example.fe;

public class FeDataMap {

    private FeAssetsMapReader mapReader;

    public FeDataMap(){
        mapReader = new FeAssetsMapReader();
    }

    public FeMapInfo getMap(int section){
        FeMapInfo mapInfo = new FeMapInfo(section);
        mapReader.getFeMapInfo(mapInfo, section);
        return mapInfo;
    }
}
