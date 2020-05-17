package com.example.fe;

/*
    /assets/map 文件夹资源管理器
 */
public class FeAssetsMap {

    private FeReaderMap mapReader = new FeReaderMap();

    public FeMapInfo getMap(int section){
        FeMapInfo mapInfo = new FeMapInfo(section);
        mapReader.getFeMapInfo(mapInfo, section);
        return mapInfo;
    }
}
