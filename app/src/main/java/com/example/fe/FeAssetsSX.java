package com.example.fe;

import android.graphics.Bitmap;

/*
    /assets/save/sX 文件夹资源管理器
 */
public class FeAssetsSX {

    public FeAssetsUnit _unit;
    public int sX;
    public FeAssetsSaveSection section;
    public FeAssetsSaveUnit unit;

    public FeAssetsSX(FeAssetsUnit unit, int sX){
        this._unit = unit;
        this.sX = sX;
    }
}

/*
    /assets/save/sX/section 文件夹资源管理器
 */
class FeAssetsSaveSection {

    private FeAssetsUnit _unit;
    private int sX = 0;

    public FeAssetsSaveSection(FeAssetsUnit unit, int sX){
        this._unit = unit;
        this.sX = sX;
        //sX
        String sectionFolder = String.format("/save/s%d/section/", sX);
        //section
        this.unit = new Unit(sectionFolder, "unit.txt", ";");
        this.round = new Round(sectionFolder, "round.txt", ";");
        //
        // this.campBlue = new Camp(sectionFolder, "b_000.txt", ";");
    }

    //----- api -----

    public void addCamp(int camp, int id){
    }

    public void addCamp2(int id){
    }

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

/*
    /assets/save/sX/unit 文件夹资源管理器
 */
class FeAssetsSaveUnit {

    private FeAssetsUnit _unit;
    private int sX = 0;

    public FeAssetsSaveUnit(FeAssetsUnit unit, int sX){
        this._unit = unit;
        this.sX = sX;
        //sX
        String unitFolder = String.format("/save/s%d/unit/", sX);
        // unit
        this.unit = new Unit(unitFolder, "unit.txt", ";");
        this.ability = new Ability(unitFolder, "ability.txt", ";");
        this.grow = new Grow(unitFolder, "grow.txt", ";");
        this.skill = new Skill(unitFolder, "skill.txt", ";");
        this.special = new Special(unitFolder, "special.txt", ";");
        this.item = new Item(unitFolder, "item.txt", ";");
        this.record = new Record(unitFolder, "record.txt", ";");
    }

    //----- api -----

    public int addUnit(int count){
        return 0;
    }

    // unit.txt
    public int getId(int count){
        return unit.getId(count);
    }
    // _unit.name.txt
    public String getName(int count){
        return _unit.getName(unit.getId(count));
    }
    public String getSummary(int count){
        return _unit.getSummary(unit.getId(count));
    }
    // /head/xx.png
    public Bitmap getHead(int count){
        return _unit.getHead(unit.getId(count));
    }
    // /anim/xxx.png
    public Bitmap getProfessionAnim(int count){
        return _unit.getProfessionAnim(unit.getId(count));
    }
    // _unit.p_name.txt
    public String getProfessionName(int count){
        return _unit.getProfessionName(unit.getId(count));
    }
    public String getProfessionSummary(int count){
        return _unit.getProfessionSummary(unit.getId(count));
    }
    // _unit.p_upgrade.txt
    public int getProfessionUpgradeHp(int count){
        return _unit.getProfessionUpgradeHp(unit.getId(count));
    }
    public int getProfessionUpgradeStr(int count){
        return _unit.getProfessionUpgradeStr(unit.getId(count));
    }
    public int getProfessionUpgradeMag(int count){
        return _unit.getProfessionUpgradeMag(unit.getId(count));
    }
    public int getProfessionUpgradeSkill(int count){
        return _unit.getProfessionUpgradeSkill(unit.getId(count));
    }
    public int getProfessionUpgradeSpe(int count){
        return _unit.getProfessionUpgradeSpe(unit.getId(count));
    }
    public int getProfessionUpgradeLuk(int count){
        return _unit.getProfessionUpgradeLuk(unit.getId(count));
    }
    public int getProfessionUpgradeDef(int count){
        return _unit.getProfessionUpgradeDef(unit.getId(count));
    }
    public int getProfessionUpgradeMde(int count){
        return _unit.getProfessionUpgradeMde(unit.getId(count));
    }
    public int getProfessionUpgradeWeig(int count){
        return _unit.getProfessionUpgradeWeig(unit.getId(count));
    }
    public int getProfessionUpgradeMov(int count){
        return _unit.getProfessionUpgradeMov(unit.getId(count));
    }
    // _unit.p_special.txt
    public int getProfessionSpecial1(int count){
        return _unit.getProfessionSpecial1(unit.getId(count));
    }
    public int getProfessionSpecial2(int count){
        return _unit.getProfessionSpecial2(unit.getId(count));
    }
    public int getProfessionSpecial3(int count){
        return _unit.getProfessionSpecial3(unit.getId(count));
    }
    public int getProfessionSpecial4(int count){
        return _unit.getProfessionSpecial4(unit.getId(count));
    }
    // ability.txt
    public int getAbilityHp(int count){
        return ability.getHp(unit.getAbility(count));
    }
    public int getAbilityStr(int count){
        return ability.getStr(unit.getAbility(count));
    }
    public int getAbilityMag(int count){
        return ability.getMag(unit.getAbility(count));
    }
    public int getAbilitySkill(int count){
        return ability.getSkill(unit.getAbility(count));
    }
    public int getAbilitySpe(int count){
        return ability.getSpe(unit.getAbility(count));
    }
    public int getAbilityLuk(int count){
        return ability.getLuk(unit.getAbility(count));
    }
    public int getAbilityDef(int count){
        return ability.getDef(unit.getAbility(count));
    }
    public int getAbilityMde(int count){
        return ability.getMde(unit.getAbility(count));
    }
    public int getAbilityWeig(int count){
        return ability.getWeig(unit.getAbility(count));
    }
    public int getAbilityMov(int count){
        return ability.getMov(unit.getAbility(count));
    }
    // grow.txt
    public int getGrowHp(int count){
        return grow.getHp(unit.getGrow(count));
    }
    public int getGrowStr(int count){
        return grow.getStr(unit.getGrow(count));
    }
    public int getGrowMag(int count){
        return grow.getMag(unit.getGrow(count));
    }
    public int getGrowSkill(int count){
        return grow.getSkill(unit.getGrow(count));
    }
    public int getGrowSpe(int count){
        return grow.getSpe(unit.getGrow(count));
    }
    public int getGrowLuk(int count){
        return grow.getLuk(unit.getGrow(count));
    }
    public int getGrowDef(int count){
        return grow.getDef(unit.getGrow(count));
    }
    public int getGrowMde(int count){
        return grow.getMde(unit.getGrow(count));
    }
    public int getGrowWeig(int count){
        return grow.getWeig(unit.getGrow(count));
    }
    public int getGrowMov(int count){
        return grow.getMov(unit.getGrow(count));
    }
    // skill.txt
    public int getSkillSword(int count){
        return skill.getSword(unit.getSkill(count));
    }
    public int getSkillGun(int count){
        return skill.getGun(unit.getSkill(count));
    }
    public int getSkillAxe(int count){
        return skill.getAxe(unit.getSkill(count));
    }
    public int getSkillArrow(int count){
        return skill.getArrow(unit.getSkill(count));
    }
    public int getSkillPhy(int count){
        return skill.getPhy(unit.getSkill(count));
    }
    public int getSkillLight(int count){
        return skill.getLight(unit.getSkill(count));
    }
    public int getSkillDark(int count){
        return skill.getDark(unit.getSkill(count));
    }
    public int getSkillStick(int count){
        return skill.getStick(unit.getSkill(count));
    }
    // special.txt
    public int getSpecial1(int count){
        return special.getSpe1(unit.getSpecial(count));
    }
    public int getSpecial2(int count){
        return special.getSpe2(unit.getSpecial(count));
    }
    public int getSpecial3(int count){
        return special.getSpe3(unit.getSpecial(count));
    }
    public int getSpecial4(int count){
        return special.getSpe4(unit.getSpecial(count));
    }
    // unit.txt
    public int getLevel(int count){
        return unit.getLevel(count);
    }
    // item.txt
    public int getItem1(int count){
        return item.getIt1(unit.getItem(count));
    }
    public int getItem2(int count){
        return item.getIt2(unit.getItem(count));
    }
    public int getItem3(int count){
        return item.getIt3(unit.getItem(count));
    }
    public int getItem4(int count){
        return item.getIt4(unit.getItem(count));
    }
    public int getItem5(int count){
        return item.getIt5(unit.getItem(count));
    }
    public int getItem6(int count){
        return item.getIt6(unit.getItem(count));
    }
    // unit.txt
    public int getCamp(int count){
        return unit.getCamp(count);
    }
    // unit.txt
    public int getState(int count){
        return unit.getState(count);
    }
    // record.txt
    public int getRecord(int count){
        return record.getRecord(unit.getRecord(count));
    }
    public int getWin(int count){
        return record.getWin(unit.getRecord(count));
    }
    public int getDie(int count){
        return record.getDie(unit.getRecord(count));
    }
    
    //----- all file -----

    public Unit unit;
    public Ability ability;
    public Grow grow;
    public Skill skill;
    public Special special;
    public Item item;
    public Record record;

    //----- unit -----

    //人物列表
    class Unit extends FeReaderFile {
        public int getId(int line){ return getInt(line, 0); }
        public int getAbility(int line){ return getInt(line, 1); }
        public int getGrow(int line){ return getInt(line, 2); }
        public int getSkill(int line){ return getInt(line, 3); }
        public int getSpecial(int line){ return getInt(line, 4); }
        public int getLevel(int line){ return getInt(line, 5); }
        public int getItem(int line){ return getInt(line, 6); }
        public int getCamp(int line){ return getInt(line, 7); }
        public int getState(int line){ return getInt(line, 8); }
        public int getRecord(int line){ return getInt(line, 9); }

        public void setId(int line, int id){ setValue(id, line, 0); }
        public void setAbility(int line, int ability){ setValue(ability, line, 1); }
        public void setGrow(int line, int grow){ setValue(grow, line, 2); }
        public void setSkill(int line, int skill){ setValue(skill, line, 3); }
        public void setSpecial(int line, int special){ setValue(special, line, 4); }
        public void setLevel(int line, int level){ setValue(level, line, 5); }
        public void setItem(int line, int item){ setValue(item, line, 6); }
        public void setCamp(int line, int camp){ setValue(camp, line, 7); }
        public void setState(int line, int state){ setValue(state, line, 8); }
        public void setRecord(int line, int record){ setValue(record, line, 9); }

        public Unit(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //职业能力列表
    class Ability extends FeReaderFile {
        public int getHp(int line){ return getInt(line, 0); }
        public int getStr(int line){ return getInt(line, 1); }
        public int getMag(int line){ return getInt(line, 2); }
        public int getSkill(int line){ return getInt(line, 3); }
        public int getSpe(int line){ return getInt(line, 4); }
        public int getLuk(int line){ return getInt(line, 5); }
        public int getDef(int line){ return getInt(line, 6); }
        public int getMde(int line){ return getInt(line, 7); }
        public int getWeig(int line){ return getInt(line, 8); }
        public int getMov(int line){ return getInt(line, 9); }

        public void setHp(int line, int hp){ setValue(hp, line, 0); }
        public void setStr(int line, int str){ setValue(str, line, 1); }
        public void setMag(int line, int mag){ setValue(mag, line, 2); }
        public void setSkill(int line, int skill){ setValue(skill, line, 3); }
        public void setSpe(int line, int spe){ setValue(spe, line, 4); }
        public void setLuk(int line, int luk){ setValue(luk, line, 5); }
        public void setDef(int line, int def){ setValue(def, line, 6); }
        public void setMde(int line, int mde){ setValue(mde, line, 7); }
        public void setWeig(int line, int weig){ setValue(weig, line, 8); }
        public void setMov(int line, int mov){ setValue(mov, line, 9); }

        public Ability(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //成长率列表
    class Grow extends FeReaderFile {
        public int getHp(int line){ return getInt(line, 0); }
        public int getStr(int line){ return getInt(line, 1); }
        public int getMag(int line){ return getInt(line, 2); }
        public int getSkill(int line){ return getInt(line, 3); }
        public int getSpe(int line){ return getInt(line, 4); }
        public int getLuk(int line){ return getInt(line, 5); }
        public int getDef(int line){ return getInt(line, 6); }
        public int getMde(int line){ return getInt(line, 7); }
        public int getWeig(int line){ return getInt(line, 8); }
        public int getMov(int line){ return getInt(line, 9); }

        public void setHp(int line, int hp){ setValue(hp, line, 0); }
        public void setStr(int line, int str){ setValue(str, line, 1); }
        public void setMag(int line, int mag){ setValue(mag, line, 2); }
        public void setSkill(int line, int skill){ setValue(skill, line, 3); }
        public void setSpe(int line, int spe){ setValue(spe, line, 4); }
        public void setLuk(int line, int luk){ setValue(luk, line, 5); }
        public void setDef(int line, int def){ setValue(def, line, 6); }
        public void setMde(int line, int mde){ setValue(mde, line, 7); }
        public void setWeig(int line, int weig){ setValue(weig, line, 8); }
        public void setMov(int line, int mov){ setValue(mov, line, 9); }

        public Grow(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //技能列表
    class Skill extends FeReaderFile {
        public int getSword(int line){ return getInt(line, 0); }
        public int getGun(int line){ return getInt(line, 1); }
        public int getAxe(int line){ return getInt(line, 2); }
        public int getArrow(int line){ return getInt(line, 3); }
        public int getPhy(int line){ return getInt(line, 4); }
        public int getLight(int line){ return getInt(line, 5); }
        public int getDark(int line){ return getInt(line, 6); }
        public int getStick(int line){ return getInt(line, 7); }

        public void setSword(int line, int sword){ setValue(sword, line, 0); }
        public void setGun(int line, int gun){ setValue(gun, line, 1); }
        public void setAxe(int line, int axe){ setValue(axe, line, 2); }
        public void setArrow(int line, int arrow){ setValue(arrow, line, 3); }
        public void setPhy(int line, int phy){ setValue(phy, line, 4); }
        public void setLight(int line, int light){ setValue(light, line, 5); }
        public void setDark(int line, int dark){ setValue(dark, line, 6); }
        public void setStick(int line, int stick){ setValue(stick, line, 7); }

        public Skill(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //个人特技列表
    class Special extends FeReaderFile {
        public int getSpe1(int line){ return getInt(line, 0); }
        public int getSpe2(int line){ return getInt(line, 1); }
        public int getSpe3(int line){ return getInt(line, 2); }
        public int getSpe4(int line){ return getInt(line, 3); }

        public void setSpe1(int line, int spe1){ setValue(spe1, line, 0); }
        public void setSpe2(int line, int spe2){ setValue(spe2, line, 1); }
        public void setSpe3(int line, int spe3){ setValue(spe3, line, 2); }
        public void setSpe4(int line, int spe4){ setValue(spe4, line, 3); }

        public Special(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //人物物品列表
    class Item extends FeReaderFile {
        public int getIt1(int line){ return getInt(line, 0); }
        public int getIt2(int line){ return getInt(line, 1); }
        public int getIt3(int line){ return getInt(line, 2); }
        public int getIt4(int line){ return getInt(line, 3); }
        public int getIt5(int line){ return getInt(line, 4); }
        public int getIt6(int line){ return getInt(line, 5); }
        public int getEquip(int line){ return getInt(line, 6); }

        public void setIt1(int line, int it1){ setValue(it1, line, 0); }
        public void setIt2(int line, int it2){ setValue(it2, line, 1); }
        public void setIt3(int line, int it3){ setValue(it3, line, 2); }
        public void setIt4(int line, int it4){ setValue(it4, line, 3); }
        public void setIt5(int line, int it5){ setValue(it5, line, 4); }
        public void setIt6(int line, int it6){ setValue(it6, line, 5); }
        public void setEquip(int line, int equip){ setValue(equip, line, 6); }

        public Item(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //人物战绩
    class Record extends FeReaderFile {
        public int getRecord(int line){ return getInt(line, 0); }
        public int getWin(int line){ return getInt(line, 1); }
        public int getDie(int line){ return getInt(line, 2); }

        public void setRecord(int line, int record){ setValue(record, line, 0); }
        public void setWin(int line, int win){ setValue(win, line, 1); }
        public void setDie(int line, int die){ setValue(die, line, 2); }

        public Record(String folder, String name, String split){
            super(folder, name, split);
        }
    }

}