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
        this.item = new Item(unitFolder, "item.txt", ";");
        // unit addition
        addition = new Addition(unitFolder, "addition.txt", ";");
        a_ability = new A_Ability(unitFolder, "a_ability.txt", ";");
        a_grow = new A_Grow(unitFolder, "a_grow.txt", ";");
        a_skill = new A_Skill(unitFolder, "a_skill.txt", ";");
        a_special = new A_Special(unitFolder, "a_special.txt", ";");
    }

    //----- api -----

    public int addUnit(int count){
        ;
    }

    // name.txt
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
    // _unit.p_name.txt
    public String getProfessionName(int count){
        return _unit.p_name.getName(_unit.profession.getName(unit.getProfession(count)));
    }
    public String getProfessionSummary(int count){
        return _unit.p_name.getSummary(_unit.profession.getName(unit.getProfession(count)));
    }
    // /anim/xxx.png
    public Bitmap getProfessionAnim(int count){
        return _unit.getAnimBitmap(_unit.profession.getAnim(unit.getProfession(count)));
    }
    // _unit.p_ability.txt
    public int getProfessionAbilityHp(int count){
        return _unit.p_ability.getHp(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilityStr(int count){
        return _unit.p_ability.getStr(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilityMag(int count){
        return _unit.p_ability.getMag(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilitySkill(int count){
        return _unit.p_ability.getSkill(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilitySpe(int count){
        return _unit.p_ability.getSpe(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilityLuk(int count){
        return _unit.p_ability.getLuk(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilityDef(int count){
        return _unit.p_ability.getDef(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilityMde(int count){
        return _unit.p_ability.getMde(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilityWeig(int count){
        return _unit.p_ability.getWeig(_unit.profession.getAbility(unit.getProfession(count)));
    }
    public int getProfessionAbilityMov(int count){
        return _unit.p_ability.getMov(_unit.profession.getAbility(unit.getProfession(count)));
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
    // _unit.p_grow.txt
    public int getProfessionGrowHp(int count){
        return _unit.p_grow.getHp(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowStr(int count){
        return _unit.p_grow.getStr(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowMag(int count){
        return _unit.p_grow.getMag(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowSkill(int count){
        return _unit.p_grow.getSkill(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowSpe(int count){
        return _unit.p_grow.getSpe(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowLuk(int count){
        return _unit.p_grow.getLuk(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowDef(int count){
        return _unit.p_grow.getDef(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowMde(int count){
        return _unit.p_grow.getMde(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowWeig(int count){
        return _unit.p_grow.getWeig(_unit.profession.getGrow(unit.getProfession(count)));
    }
    public int getProfessionGrowMov(int count){
        return _unit.p_grow.getMov(_unit.profession.getGrow(unit.getProfession(count)));
    }
    // _unit.p_skill.txt
    public int getProfessionSkillSword(int count){
        return _unit.p_skill.getSword(_unit.profession.getSkill(unit.getProfession(count)));
    }
    public int getProfessionSkillGun(int count){
        return _unit.p_skill.getGun(_unit.profession.getSkill(unit.getProfession(count)));
    }
    public int getProfessionSkillAxe(int count){
        return _unit.p_skill.getAxe(_unit.profession.getSkill(unit.getProfession(count)));
    }
    public int getProfessionSkillArrow(int count){
        return _unit.p_skill.getArrow(_unit.profession.getSkill(unit.getProfession(count)));
    }
    public int getProfessionSkillPhy(int count){
        return _unit.p_skill.getPhy(_unit.profession.getSkill(unit.getProfession(count)));
    }
    public int getProfessionSkillLight(int count){
        return _unit.p_skill.getLight(_unit.profession.getSkill(unit.getProfession(count)));
    }
    public int getProfessionSkillDark(int count){
        return _unit.p_skill.getDark(_unit.profession.getSkill(unit.getProfession(count)));
    }
    public int getProfessionSkillStick(int count){
        return _unit.p_skill.getStick(_unit.profession.getSkill(unit.getProfession(count)));
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
    // _unit.profession.txt
    public int getProfessionType(int count){
        return _unit.profession.getType(unit.getProfession(count));
    }
    // a_ability.txt
    public int getAdditionAbilityHp(int count){
        return a_ability.getHp(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilityStr(int count){
        return a_ability.getStr(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilityMag(int count){
        return a_ability.getMag(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilitySkill(int count){
        return a_ability.getSkill(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilitySpe(int count){
        return a_ability.getSpe(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilityLuk(int count){
        return a_ability.getLuk(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilityDef(int count){
        return a_ability.getDef(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilityMde(int count){
        return a_ability.getMde(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilityWeig(int count){
        return a_ability.getWeig(addition.getAbility(unit.getAddition(count)));
    }
    public int getAdditionAbilityMov(int count){
        return a_ability.getMov(addition.getAbility(unit.getAddition(count)));
    }
    // a_grow.txt
    public int getAdditionGrowHp(int count){
        return a_grow.getHp(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowStr(int count){
        return a_grow.getStr(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowMag(int count){
        return a_grow.getMag(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowSkill(int count){
        return a_grow.getSkill(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowSpe(int count){
        return a_grow.getSpe(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowLuk(int count){
        return a_grow.getLuk(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowDef(int count){
        return a_grow.getDef(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowMde(int count){
        return a_grow.getMde(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowWeig(int count){
        return a_grow.getWeig(addition.getGrow(unit.getAddition(count)));
    }
    public int getAdditionGrowMov(int count){
        return a_grow.getMov(addition.getGrow(unit.getAddition(count)));
    }
    // a_skill.txt
    public int getAdditionSkillSword(int count){
        return a_skill.getSword(addition.getSkill(unit.getAddition(count)));
    }
    public int getAdditionSkillGun(int count){
        return a_skill.getGun(addition.getSkill(unit.getAddition(count)));
    }
    public int getAdditionSkillAxe(int count){
        return a_skill.getAxe(addition.getSkill(unit.getAddition(count)));
    }
    public int getAdditionSkillArrow(int count){
        return a_skill.getArrow(addition.getSkill(unit.getAddition(count)));
    }
    public int getAdditionSkillPhy(int count){
        return a_skill.getPhy(addition.getSkill(unit.getAddition(count)));
    }
    public int getAdditionSkillLight(int count){
        return a_skill.getLight(addition.getSkill(unit.getAddition(count)));
    }
    public int getAdditionSkillDark(int count){
        return a_skill.getDark(addition.getSkill(unit.getAddition(count)));
    }
    // a_special.txt
    public int getAdditionSpecial1(int count){
        return a_special.getSpe1(addition.getSpecial(unit.getAddition(count)));
    }
    public int getAdditionSpecial2(int count){
        return a_special.getSpe2(addition.getSpecial(unit.getAddition(count)));
    }
    public int getAdditionSpecial3(int count){
        return a_special.getSpe3(addition.getSpecial(unit.getAddition(count)));
    }
    public int getAdditionSpecial4(int count){
        return a_special.getSpe4(addition.getSpecial(unit.getAddition(count)));
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
    public int getState(int count){
        return unit.getState(count);
    }

    //----- all file -----

    // unit
    public Unit unit;
    public Item item;
    // unit addition
    public Addition addition;
    public A_Ability a_ability;
    public A_Grow a_grow;
    public A_Skill a_skill;
    public A_Special a_special;

    //----- unit -----

    //人物列表
    class Unit extends FeReaderFile {
        public int getName(int line){ return getInt(line, 0); }
        public int getHead(int line){ return getInt(line, 1); }
        public int getProfession(int line){ return getInt(line, 2); }
        public int getAddition(int line){ return getInt(line, 3); }
        public int getLevel(int line){ return getInt(line, 4); }
        public int getItem(int line){ return getInt(line, 5); }
        public int getCamp(int line){ return getInt(line, 6); }
        public int getState(int line){ return getInt(line, 7); }

        public void setName(int line, int name){ setValue(name, line, 0); }
        public void setHead(int line, int head){ setValue(head, line, 1); }
        public void setProfession(int line, int profession){ setValue(profession, line, 2); }
        public void setAddition(int line, int addition){ setValue(addition, line, 3); }
        public void setLevel(int line, int level){ setValue(level, line, 4); }
        public void setItem(int line, int item){ setValue(item, line, 5); }
        public void setCamp(int line, int camp){ setValue(camp, line, 6); }
        public void setState(int line, int state){ setValue(state, line, 7); }

        public Unit(String folder, String name, String split){
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

        public void setIt1(int line, int it1){ setValue(it1, line, 0); }
        public void setIt2(int line, int it2){ setValue(it2, line, 1); }
        public void setIt3(int line, int it3){ setValue(it3, line, 2); }
        public void setIt4(int line, int it4){ setValue(it4, line, 3); }
        public void setIt5(int line, int it5){ setValue(it5, line, 4); }
        public void setIt6(int line, int it6){ setValue(it6, line, 5); }

        public Item(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //----- unit addition -----

    //人物加成列表
    class Addition extends FeReaderFile {
        public int getAbility(int line){ return getInt(line, 0); }
        public int getGrow(int line){ return getInt(line, 1); }
        public int getSkill(int line){ return getInt(line, 2); }
        public int getSpecial(int line){ return getInt(line, 3); }

        public void setAbility(int line, int ability){ setValue(ability, line, 0); }
        public void setGrow(int line, int grow){ setValue(grow, line, 1); }
        public void setSkill(int line, int skill){ setValue(skill, line, 2); }
        public void setSpecial(int line, int special){ setValue(special, line, 3); }

        public Addition(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //人物能力加成列表
    class A_Ability extends FeReaderFile {
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

        public A_Ability(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //人物成长率加成列表
    class A_Grow extends FeReaderFile {
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

        public A_Grow(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //人物技能加成列表
    class A_Skill extends FeReaderFile {
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

        public A_Skill(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //人物特技列表
    class A_Special extends FeReaderFile {
        public int getSpe1(int line){ return getInt(line, 0); }
        public int getSpe2(int line){ return getInt(line, 1); }
        public int getSpe3(int line){ return getInt(line, 2); }
        public int getSpe4(int line){ return getInt(line, 3); }

        public void setSpe1(int line, int spe1){ setValue(spe1, line, 0); }
        public void setSpe2(int line, int spe2){ setValue(spe2, line, 1); }
        public void setSpe3(int line, int spe3){ setValue(spe3, line, 2); }
        public void setSpe4(int line, int spe4){ setValue(spe4, line, 3); }

        public A_Special(String folder, String name, String split){
            super(folder, name, split);
        }
    }
}