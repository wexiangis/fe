package com.example.fe;

/*
    /assets/save/sX 文件夹资源管理器,存档X管理
 */
public class FeAssetsSX {

    public FeAssetsUnit _unit;
    public int sX;

    public FeAssetsSaveCache saveCache;
    public FeAssetsSaveUnit saveUnit;
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
        this.section = new FeAssetsSection(unit, info.getSection());
        this.saveUnit = new FeAssetsSaveUnit(unit, sX);
    }

    //----- api -----

    /*
        从中断中回复
     */
    public void recover(){
        ;
    }

    /*
        初始人物加载
     */
    public void init(){
        //删空缓存
        new FeFile().delete(String.format("/save/s%d/cache/", sX));
        //建立缓存
        this.saveCache = new FeAssetsSaveCache(_unit, sX);
        //根据 section 的site加载 saveUnit 人物到 saveCache
        for(int secUnit = 0, savUnit = 0; 
            secUnit < section.site.total() && savUnit < saveUnit.unit.total(); 
            secUnit++)
        {
            //触发方式为回合触发,且回合为0
            if(section.site.getTrigger(secUnit) == 0 && 
                section.site.getTurn(secUnit) == 0)
            {
                //添加人物
            }
        }
        //根据 section 的unit加载人物到 saveCache
        for(int i = 0; i < section.unit.total(); i++){
            ;
        }
    }

    /*
        回合触发事件
     */
    public void eventTurn(int turn, int camp){
        ;
    }

    /*
        到达
     */
    
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
            //文件没有加载,创建文件
            if(line() == 0)
            {
                addLine(new String[]{"1","0","0","章节/是否中断/总用时"});
                save();
            }
        }
    }

    class Setting extends FeReaderFile{
        
        public Setting(String folder, String name, String split){
            super(folder, name, split);
        }
    }
}

