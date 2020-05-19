package com.example.fe;

/*
    assets资源管理器, 所有assets下的资源都汇总到这里
 */
public class FeAssets {

    public FeAssetsMap map;
    public FeAssetsMenu menu;
    public FeAssetsParam param;
    public FeAssetsUnit unit;
    public FeAssetsSave[] save;
    public FeAssetsSection section;

    public FeAssets(){
        map = new FeAssetsMap();
        menu = new FeAssetsMenu();
        param = new FeAssetsParam();
        unit = new FeAssetsUnit();
        save = new FeAssetsSave[3];
        save[0] = new FeAssetsSave(unit, 0);
        save[1] = new FeAssetsSave(unit, 1);
        save[2] = new FeAssetsSave(unit, 2);
        section = new FeAssetsSection();
    }

}




