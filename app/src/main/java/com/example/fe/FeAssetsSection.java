package com.example.fe;

import com.example.fe.FeAssetsSection.Setting;

/*
    /assets/section 文件夹资源管理器
 */
public class FeAssetsSection {

    private FeAssetsUnit _unit;
    private int sX = 0;

    public FeAssetsSection(FeAssetsUnit unit, int sX){
        this._unit = unit;
        this.sX = sX;
        //sX
        String sectionFolder = String.format("/section/section%02d/", sX);
        //section
        this.unit = new Unit(sectionFolder, "unit.txt", ";");
        this.site = new Site(sectionFolder, "site.txt", ";");
        this.target = new Target(sectionFolder, "target.txt", ";");
        this.setting = new Setting(sectionFolder, "setting.txt", ";");
        this.talk = new Talk(sectionFolder, "talk.txt", ";");
        this.bgm = new Bgm(sectionFolder, "bgm.txt", ";");
    }

    //----- api -----

    //----- all file -----

    public Unit unit;
    public Site site;
    public Target target;
    public Setting setting;
    public Talk talk;
    public Bgm bgm;

    //----- class -----

    class Unit extends FeReaderFile{

        public int getTrigger(int line){ return getInt(line, 0); }
        public int getTurn(int line){ return getInt(line, 1); }
        public int getX(int line){ return getInt(line, 2)/1000; }
        public int getY(int line){ return getInt(line, 2)%1000; }
        public int getId(int line){ return getInt(line, 3); }
        public int getTrend(int line){ return getInt(line, 4); }
        public int getLeader(int line){ return getInt(line, 5); }
        public int getCamp(int line){ return getInt(line, 6); }
        
        public Unit(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Site extends FeReaderFile{

        public int getTrigger(int line){ return getInt(line, 0); }
        public int getTurn(int line){ return getInt(line, 1); }
        public int getX(int line){ return getInt(line, 2)/1000; }
        public int getY(int line){ return getInt(line, 2)%1000; }
        public int getFix(int line){ return getInt(line, 3); }
        public int getId(int line){ return getInt(line, 4); }
        
        public Site(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Target extends FeReaderFile{

        public int getCondition(int line){ return getInt(line, 0); }
        public int getId(int line){ return getInt(line, 1); }
        public int getTurn(int line){ return getInt(line, 2); }
        public int getXYNum(int line){ return getInt(line, 3); }
        public int getX(int line, int num){ return getInt(line, num + 4)/1000; }
        public int getY(int line, int num){ return getInt(line, num + 4)%1000; }
        
        public Target(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Setting extends FeReaderFile{
        
        public Setting(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Talk extends FeReaderFile{
        
        public Talk(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Bgm extends FeReaderFile{
        
        public Bgm(String folder, String name, String split){
            super(folder, name, split);
        }
    }
}