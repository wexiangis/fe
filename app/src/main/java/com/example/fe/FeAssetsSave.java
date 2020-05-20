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

    private FeAssetsUnit _unit;
    private int sX = 0;

    public FeAssetsSaveSection(FeAssetsUnit unit, int sX){
        this._unit = unit;
        this.sX = sX;
        //sX
        String sectionFolder = String.format("/save/s%d/section/", sX);
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
    }

    //----- api -----

    public int addUnit(int count){
        return 0;
    }

    // _unit.name.txt
    public String getName(int count){
        return _unit.name.getName(unit.getName(count));
    }
    public String getSummary(int count){
        return _unit.name.getSummary(unit.getName(count));
    }
    // /head/xx.png
    public Bitmap getHead(int count){
        return _unit.getHeadBitmap(unit.getHead(count));
    }
    // /anim/xxx.png
    public Bitmap getProfessionAnim(int count){
        return _unit.getAnimBitmap(_unit.profession.getAnim(unit.getProfession(count)));
    }
    // _unit.p_name.txt
    public String getProfessionName(int count){
        return _unit.p_name.getName(_unit.profession.getName(unit.getProfession(count)));
    }
    public String getProfessionSummary(int count){
        return _unit.p_name.getSummary(_unit.profession.getName(unit.getProfession(count)));
    }
    // _unit.p_upgrade.txt
    public int getProfessionUpgradeHp(int count){
        return _unit.p_upgrade.getHp(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeStr(int count){
        return _unit.p_upgrade.getStr(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeMag(int count){
        return _unit.p_upgrade.getMag(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeSkill(int count){
        return _unit.p_upgrade.getSkill(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeSpe(int count){
        return _unit.p_upgrade.getSpe(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeLuk(int count){
        return _unit.p_upgrade.getLuk(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeDef(int count){
        return _unit.p_upgrade.getDef(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeMde(int count){
        return _unit.p_upgrade.getMde(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeWeig(int count){
        return _unit.p_upgrade.getWeig(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    public int getProfessionUpgradeMov(int count){
        return _unit.p_upgrade.getMov(_unit.profession.getUpgrade(unit.getProfession(count)));
    }
    // _unit.p_special.txt
    public int getProfessionSpecial1(int count){
        return _unit.p_special.getSpe1(_unit.profession.getSpecial(unit.getProfession(count)));
    }
    public int getProfessionSpecial2(int count){
        return _unit.p_special.getSpe2(_unit.profession.getSpecial(unit.getProfession(count)));
    }
    public int getProfessionSpecial3(int count){
        return _unit.p_special.getSpe3(_unit.profession.getSpecial(unit.getProfession(count)));
    }
    public int getProfessionSpecial4(int count){
        return _unit.p_special.getSpe4(_unit.profession.getSpecial(unit.getProfession(count)));
    }
    // ability.txt
    public int getProfessionAbilityHp(int count){
        return ability.getHp(unit.getAbility(count));
    }
    public int getProfessionAbilityStr(int count){
        return ability.getStr(unit.getAbility(count));
    }
    public int getProfessionAbilityMag(int count){
        return ability.getMag(unit.getAbility(count));
    }
    public int getProfessionAbilitySkill(int count){
        return ability.getSkill(unit.getAbility(count));
    }
    public int getProfessionAbilitySpe(int count){
        return ability.getSpe(unit.getAbility(count));
    }
    public int getProfessionAbilityLuk(int count){
        return ability.getLuk(unit.getAbility(count));
    }
    public int getProfessionAbilityDef(int count){
        return ability.getDef(unit.getAbility(count));
    }
    public int getProfessionAbilityMde(int count){
        return ability.getMde(unit.getAbility(count));
    }
    public int getProfessionAbilityWeig(int count){
        return ability.getWeig(unit.getAbility(count));
    }
    public int getProfessionAbilityMov(int count){
        return ability.getMov(unit.getAbility(count));
    }
    // grow.txt
    public int getProfessionGrowHp(int count){
        return grow.getHp(unit.getGrow(count));
    }
    public int getProfessionGrowStr(int count){
        return grow.getStr(unit.getGrow(count));
    }
    public int getProfessionGrowMag(int count){
        return grow.getMag(unit.getGrow(count));
    }
    public int getProfessionGrowSkill(int count){
        return grow.getSkill(unit.getGrow(count));
    }
    public int getProfessionGrowSpe(int count){
        return grow.getSpe(unit.getGrow(count));
    }
    public int getProfessionGrowLuk(int count){
        return grow.getLuk(unit.getGrow(count));
    }
    public int getProfessionGrowDef(int count){
        return grow.getDef(unit.getGrow(count));
    }
    public int getProfessionGrowMde(int count){
        return grow.getMde(unit.getGrow(count));
    }
    public int getProfessionGrowWeig(int count){
        return grow.getWeig(unit.getGrow(count));
    }
    public int getProfessionGrowMov(int count){
        return grow.getMov(unit.getGrow(count));
    }
    // skill.txt
    public int getProfessionSkillSword(int count){
        return skill.getSword(unit.getSkill(count));
    }
    public int getProfessionSkillGun(int count){
        return skill.getGun(unit.getSkill(count));
    }
    public int getProfessionSkillAxe(int count){
        return skill.getAxe(unit.getSkill(count));
    }
    public int getProfessionSkillArrow(int count){
        return skill.getArrow(unit.getSkill(count));
    }
    public int getProfessionSkillPhy(int count){
        return skill.getPhy(unit.getSkill(count));
    }
    public int getProfessionSkillLight(int count){
        return skill.getLight(unit.getSkill(count));
    }
    public int getProfessionSkillDark(int count){
        return skill.getDark(unit.getSkill(count));
    }
    public int getProfessionSkillStick(int count){
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