package com.example.fe;

import android.graphics.Bitmap;

/*
    /assets/save/sX 文件夹资源管理器
 */
public class FeAssetsSave {

    public FeAssetsSaveUnit unit;
    public FeAssetsSaveSection section;

    public FeAssetsSave(FeAssetsUnit unit, int sX){
        this.unit = new FeAssetsSaveUnit(unit, sX);
        this.section = new FeAssetsSaveSection(unit, sX);
    }
}

/*
    /assets/save/sX/section 文件夹资源管理器
 */
class FeAssetsSaveSection {

    public FeAssetsSaveSection(FeAssetsUnit unit, int sX){
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
        String sectionFolder = String.format("/save/s%d/section/", sX);
        // unit
        this.unit = new Unit(unitFolder, "unit.txt", ";");
        this.ability = new Ability(unitFolder, "ability.txt", ";");
        this.grow = new Grow(unitFolder, "grow.txt", ";");
        this.skill = new Skill(unitFolder, "skill.txt", ";");
        this.special = new Special(unitFolder, "special.txt", ";");
        this.item = new Item(unitFolder, "item.txt", ";");
    }

    //----- api -----

    public int addUnit(int count){
        return 0;
    }

    //----- all file -----

    public Unit unit;
    public Ability ability;
    public Grow grow;
    public Skill skill;
    public Special special;
    public Item item;

    //----- unit -----

    //人物列表
    class Unit extends FeReaderFile {
        public int getName(int line){ return getInt(line, 0); }
        public int getHead(int line){ return getInt(line, 1); }
        public int getProfession(int line){ return getInt(line, 2); }
        public int getAbility(int line){ return getInt(line, 3); }
        public int getGrow(int line){ return getInt(line, 4); }
        public int getSkill(int line){ return getInt(line, 5); }
        public int getSpecial(int line){ return getInt(line, 6); }
        public int getLevel(int line){ return getInt(line, 7); }
        public int getItem(int line){ return getInt(line, 8); }
        public int getCamp(int line){ return getInt(line, 9); }
        public int getState(int line){ return getInt(line, 10); }

        public void setName(int line, int name){ setValue(name, line, 0); }
        public void setHead(int line, int head){ setValue(head, line, 1); }
        public void setProfession(int line, int profession){ setValue(profession, line, 2); }
        public void setAbility(int line, int ability){ setValue(ability, line, 3); }
        public void setGrow(int line, int grow){ setValue(grow, line, 4); }
        public void setSkill(int line, int skill){ setValue(skill, line, 5); }
        public void setSpecial(int line, int special){ setValue(special, line, 6); }
        public void setLevel(int line, int level){ setValue(level, line, 7); }
        public void setItem(int line, int item){ setValue(item, line, 8); }
        public void setCamp(int line, int camp){ setValue(camp, line, 9); }
        public void setState(int line, int state){ setValue(state, line, 10); }

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

}