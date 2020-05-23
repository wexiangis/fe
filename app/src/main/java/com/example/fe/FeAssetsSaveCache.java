package com.example.fe;

/*
    /assets/save/sX/cache 文件夹资源管理器,章节运行信息缓存
 */
public class FeAssetsSaveCache {

    private FeAssetsUnit _unit;
    private int sX;

    public FeAssetsSaveCache(FeAssetsUnit unit, int sX){
        this._unit = unit;
        this.sX = sX;
        //sX
        String folder = String.format("/save/s%d/cache/", sX);
        //file
        this.unit = new Unit(folder, "unit.txt", ";");
        this.round = new Round(folder, "round.txt", ";");
    }

    //----- api -----

    //----- all file -----

    public Unit unit;
    public Round round;

    // 有添加时初始化
    public Camp campBlue = null;
    public Camp campRed = null;
    public Camp campGreen = null;
    public Camp campDark = null;
    public Camp campOrange = null;
    public Camp campPurple = null;
    public Camp campCyan = null;

    //----- class -----

    class Unit extends FeReaderFile{

        public int getCamp(int line){ return getInt(line, 0); }
        public int getOrder(int line){ return getInt(line, 1); }
        public int getId(int line){ return getInt(line, 2); }
        public int getX(int line){ return getInt(line, 3)/1000; }
        public int getY(int line){ return getInt(line, 3)%1000; }

        public void setCamp(int line, int camp){ setValue(camp, line, 0); }
        public void setOrder(int line, int order){ setValue(order, line, 1); }
        public void setId(int line, int id){ setValue(id, line, 2); }
        public void setX(int line, int x){ setValue(x*1000+getY(line), line, 3); }
        public void setY(int line, int y){ setValue(getX(line)*1000+y, line, 3); }
        
        public Unit(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Round extends FeReaderFile{

        public int getTurn(int line){ return getInt(line, 0); }
        public int getCamp(int line){ return getInt(line, 1); }
        public int getOrder(int line){ return getInt(line, 2); }
        public int getTime(int line){ return getInt(line, 3); }

        public void setTurn(int line, int turn){ setValue(turn, line, 0); }
        public void setCamp(int line, int camp){ setValue(camp, line, 1); }
        public void setOrder(int line, int order){ setValue(order, line, 2); }
        
        public Round(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    class Camp extends FeReaderFile{

        //链表信息
        public int order = 0;
        public int camp = 0;
        public FeReaderFile next = null;

        // line 0
        public int getStandby(){ return getInt(0, 0); }
        public int getState(){ return getInt(0, 1); }
        public int getLevel(){ return getInt(0, 2); }
        public void setStandby(int standby){ setValue(standby, 0, 0); }
        public void setState(int state){ setValue(state, 0, 1); }
        public void setLevel(int level){ setValue(level, 0, 2); }
        // line 1
        public int getHp(){ return getInt(1, 0); }
        public int getStr(){ return getInt(1, 1); }
        public int getMag(){ return getInt(1, 2); }
        public int getSkill(){ return getInt(1, 3); }
        public int getSpe(){ return getInt(1, 4); }
        public int getLuk(){ return getInt(1, 5); }
        public int getDef(){ return getInt(1, 6); }
        public int getMde(){ return getInt(1, 7); }
        public int getWeig(){ return getInt(1, 8); }
        public int getMov(){ return getInt(1, 9); }
        public void setHp(int hp){ setValue(hp, 1, 0); }
        public void setStr(int str){ setValue(str, 1, 1); }
        public void setMag(int mag){ setValue(mag, 1, 2); }
        public void setSkill(int skill){ setValue(skill, 1, 3); }
        public void setSpe(int spe){ setValue(spe, 1, 4); }
        public void setLuk(int luk){ setValue(luk, 1, 5); }
        public void setDef(int def){ setValue(def, 1, 6); }
        public void setMde(int mde){ setValue(mde, 1, 7); }
        public void setWeig(int weig){ setValue(weig, 1, 8); }
        public void setMov(int mov){ setValue(mov, 1, 9); }
        // line 2
        public int getAddHp(){ return getInt(2, 0); }
        public int getAddStr(){ return getInt(2, 1); }
        public int getAddMag(){ return getInt(2, 2); }
        public int getAddSkill(){ return getInt(2, 3); }
        public int getAddSpe(){ return getInt(2, 4); }
        public int getAddLuk(){ return getInt(2, 5); }
        public int getAddDef(){ return getInt(2, 6); }
        public int getAddMde(){ return getInt(2, 7); }
        public int getAddWeig(){ return getInt(2, 8); }
        public int getAddMov(){ return getInt(2, 9); }
        public void setAddHp(int hp){ setValue(hp, 2, 0); }
        public void setAddStr(int str){ setValue(str, 2, 1); }
        public void setAddMag(int mag){ setValue(mag, 2, 2); }
        public void setAddSkill(int skill){ setValue(skill, 2, 3); }
        public void setAddSpe(int spe){ setValue(spe, 2, 4); }
        public void setAddLuk(int luk){ setValue(luk, 2, 5); }
        public void setAddDef(int def){ setValue(def, 2, 6); }
        public void setAddMde(int mde){ setValue(mde, 2, 7); }
        public void setAddWeig(int weig){ setValue(weig, 2, 8); }
        public void setAddMov(int mov){ setValue(mov, 2, 9); }
        // line 3
        public int getSward(){ return getInt(3, 0); }
        public int getGun(){ return getInt(3, 1); }
        public int getAxe(){ return getInt(3, 2); }
        public int getArrow(){ return getInt(3, 3); }
        public int getPhy(){ return getInt(3, 4); }
        public int getLight(){ return getInt(3, 5); }
        public int getDark(){ return getInt(3, 6); }
        public int getStick(){ return getInt(3, 7); }
        public void setSward(int sward){ setValue(sward, 3, 0); }
        public void setGun(int gun){ setValue(gun, 3, 1); }
        public void setAxe(int axe){ setValue(axe, 3, 2); }
        public void setArrow(int arrow){ setValue(arrow, 3, 3); }
        public void setPhy(int phy){ setValue(phy, 3, 4); }
        public void setLight(int light){ setValue(light, 3, 5); }
        public void setDark(int dark){ setValue(dark, 3, 6); }
        public void setStick(int stick){ setValue(stick, 3, 7); }
        // line 4
        public int getIt1(){ return getInt(4, 0); }
        public int getIt2(){ return getInt(4, 1); }
        public int getIt3(){ return getInt(4, 2); }
        public int getIt4(){ return getInt(4, 3); }
        public int getIt5(){ return getInt(4, 4); }
        public int getIt6(){ return getInt(4, 5); }
        public int getEquip(){ return getInt(4, 6); }
        public void setIt1(int it1){ setValue(it1, 4, 0); }
        public void setIt2(int it2){ setValue(it2, 4, 1); }
        public void setIt3(int it3){ setValue(it3, 4, 2); }
        public void setIt4(int it4){ setValue(it4, 4, 3); }
        public void setIt5(int it5){ setValue(it5, 4, 4); }
        public void setIt6(int it6){ setValue(it6, 4, 5); }
        public void setEquip(int equip){ setValue(equip, 4, 6); }
        // line 5
        public int getSpe1(){ return getInt(5, 0); }
        public int getSpe2(){ return getInt(5, 1); }
        public int getSpe3(){ return getInt(5, 2); }
        public int getSpe4(){ return getInt(5, 3); }
        public void setSpe1(int spe1){ setValue(spe1, 5, 0); }
        public void setSpe2(int spe2){ setValue(spe2, 5, 1); }
        public void setSpe3(int spe3){ setValue(spe3, 5, 2); }
        public void setSpe4(int spe4){ setValue(spe4, 5, 3); }
        // line 6
        public int getRescue(){ return getInt(6, 0); }
        public int getRescueOrder(){ return getInt(6, 1); }
        public void setRescue(int rescue){ setValue(rescue, 6, 0); }
        public void setRescueOrder(int rescueOrder){ setValue(rescueOrder, 6, 1); }
        // line 7
        public int getFight(){ return getInt(7, 0); }
        public int getWin(){ return getInt(7, 1); }
        public int getDie(){ return getInt(7, 3); }
        public void setFight(int fight){ setValue(fight, 7, 0); }
        public void setWin(int win){ setValue(win, 7, 1); }
        public void setDie(int die){ setValue(die, 7, 2); }
        // line 8
        public int getView(){ return getInt(8, 0); }
        public int getViewAdd(){ return getInt(8, 1); }
        public void setView(int view){ setValue(view, 8, 0); }
        public void setViewAdd(int viewAdd){ setValue(viewAdd, 8, 1); }
        
        public Camp(String folder, String name, String split){
            super(folder, name, split);
        }
    }
}