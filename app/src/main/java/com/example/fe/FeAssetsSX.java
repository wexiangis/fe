package com.example.fe;

/*
    /assets/save/sX 文件夹资源管理器,存档X管理
 */
public class FeAssetsSX {

    public FeAssetsUnit _unit;
    public int sX;

    public FeAssetsSaveCache saveSection;
    public FeAssetsSaveUnit unit;
    public FeAssetsSection section;

    public FeAssetsSX(FeAssetsUnit unit, int sX){
        this._unit = unit;
        this.sX = sX;
        //sX
        String folder = String.format("/save/s%d/", sX);
        //file
        this.info = new Info(folder, "info.txt", ";");
        this.setting = new Setting(folder, "setting.txt", ";");
        //子文件夹
        this.section = new FeAssetsSection(unit, sX);
        this.unit = new FeAssetsSaveUnit(unit, sX);
        this.saveSection = new FeAssetsSaveCache(unit, sX);
        //
        loadSectionToSaveCache();
    }

    //----- api -----

    public void load(){
    }

    public void recover(){
    }

    // 根据FeAssetsSection的unit加载人物到FeSaveCache
    private void loadSectionToSaveCache(){
        ;
    }

    // 根据FeAssetsSection的site加载人物到FeSaveCache
    private void loadSaveUnitToSaveCache(){
        ;
    }
    
    //----- all file -----

    public Info info;
    public Setting setting;

    //----- class -----

    class Info extends FeReaderFile{

        public int getSection(){ return getInt(0, 0); }
        public int getIrq(){ return getInt(0, 1); }
        public int getTime(){ return getInt(0, 2); }

        public void setSection(int section){ setValue(section, 0, 0); }
        public void setIrq(int irq){ setValue(irq, 0, 1); }
        public void setTime(int time){ setValue(time, 0, 2); }
        
        public Info(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Setting extends FeReaderFile{
        
        public Setting(String folder, String name, String split){
            super(folder, name, split);
        }
    }
}

