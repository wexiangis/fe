package com.example.fe;

/*
    /assets/map 文件夹资源管理器
 */
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
