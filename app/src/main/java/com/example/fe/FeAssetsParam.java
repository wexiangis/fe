package com.example.fe;

import android.graphics.Bitmap;

/*
    /assets/param 文件夹资源管理器
 */
public class FeAssetsParam {

    //----- api -----

    // special.txt
    public String getSpecialsName(int id){
        return specials.getName(id);
    }
    public String getSpecialsSummary(int id){
        return specials.getSummary(id);
    }
    public Bitmap getSpecialsPicture(int id){
        return getSpecialsBitmap(specials.getPicture(id));
    }
    // items.txt
    public int getItemsType(int id){
        return items.getType(id);
    }
    public String getItemsName(int id){
        return items.getName(id);
    }
    public String getItemsSummary(int id){
        return items.getSummary(id);
    }
    public Bitmap getItemsPicture(int id){
        return getItemsBitmap(items.getPicture(id));
    }
    public int getItemsLevel(int id){
        return items.getLevel(id);
    }
    public int getItemsRange(int id){
        return items.getRange(id);
    }
    public int getItemsWeight(int id){
        return items.getWeight(id);
    }
    public int getItemsPower(int id){
        return items.getPower(id);
    }
    public int getItemsHit(int id){
        return items.getHit(id);
    }
    public int getItemsCritical(int id){
        return items.getCritical(id);
    }
    // skill_level.txt
    public int getSkillLevelE(){
        return skillLevel.getE(0);
    }
    public int getSkillLevelD(){
        return skillLevel.getD(0);
    }
    public int getSkillLevelC(){
        return skillLevel.getC(0);
    }
    public int getSkillLevelB(){
        return skillLevel.getB(0);
    }
    public int getSkillLevelA(){
        return skillLevel.getA(0);
    }
    public int getSkillLevelS(){
        return skillLevel.getS(0);
    }
    public int getSkillLevelSS(){
        return skillLevel.getSS(0);
    }
    // ability_limit.txt
    public int getAbilityLimitHp(int id){
        return abilityLimit.getHp(id);
    }
    public int getAbilityLimitStr(int id){
        return abilityLimit.getStr(id);
    }
    public int getAbilityLimitMag(int id){
        return abilityLimit.getMag(id);
    }
    public int getAbilityLimitSkill(int id){
        return abilityLimit.getSkill(id);
    }
    public int getAbilityLimitSpe(int id){
        return abilityLimit.getSpe(id);
    }
    public int getAbilityLimitLuk(int id){
        return abilityLimit.getLuk(id);
    }
    public int getAbilityLimitDef(int id){
        return abilityLimit.getDef(id);
    }
    public int getAbilityLimitMde(int id){
        return abilityLimit.getMde(id);
    }
    public int getAbilityLimitWeig(int id){
        return abilityLimit.getWeig(id);
    }
    public int getAbilityLimitMov(int id){
        return abilityLimit.getMov(id);
    }
    // types.txt
    public String getTypesName(int id){
        return types.getName(id);
    }
    public String getTypesSummary(int id){
        return types.getSummary(id);
    }
    public Bitmap getTypesPicture(int id){
        return getTypesBitmap(types.getPicture(id));
    }

    //----- all file -----

    private Types types = new Types("/param/", "types.txt", ";");
    private Items items = new Items("/param/", "items.txt", ";");
    private Specials specials = new Specials("/param/", "specials.txt", ";");
    private SkillLevel skillLevel = new SkillLevel("/param/", "skill_level.txt", ";");
    private AbilityLimit abilityLimit = new AbilityLimit("/param/", "ability_limit.txt", ";");

    //----- unit 文件夹 -----

    private FeReaderBitmap bitmapReader = new FeReaderBitmap();

    private Bitmap getSpecialsBitmap(int id){
        return bitmapReader.load_png_byId("/param/special/", id);
    }
    private Bitmap getTypesBitmap(int id){
        return bitmapReader.load_png_byId("/param/type/", id);
    }
    private Bitmap getItemsBitmap(int id){
        return bitmapReader.load_png_byId("/param/item/", id);
    }

    //----- xxx -----

    //职业类型列表
    class Types extends FeReaderFile {
        public String getName(int line){ return getString(line, 0); }
        public String getSummary(int line){ return getString(line, 1); }
        public int getPicture(int line){ return getInt(line, 2); }

        public void setName(int line, String name){ setValue(name, line, 0); }
        public void setSummary(int line, String summary){ setValue(summary, line, 1); }
        public void setPicture(int line, int picture){ setValue(picture, line, 2); }

        public Types(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //物品列表
    class Items extends FeReaderFile {
        public int getType(int line){ return getInt(line, 0); }
        public String getName(int line){ return getString(line, 1); }
        public String getSummary(int line){ return getString(line, 2); }
        public int getPicture(int line){ return getInt(line, 3); }
        public int getLevel(int line){ return getInt(line, 4); }
        public int getRange(int line){ return getInt(line, 5); }
        public int getWeight(int line){ return getInt(line, 6); }
        public int getPower(int line){ return getInt(line, 7); }
        public int getHit(int line){ return getInt(line, 8); }
        public int getCritical(int line){ return getInt(line, 9); }

        public void setType(int line, int type){ setValue(type, line, 0); }
        public void setName(int line, String name){ setValue(name, line, 1); }
        public void setSummary(int line, String summary){ setValue(summary, line, 2); }
        public void setPicture(int line, int picture){ setValue(picture, line, 3); }
        public void setLevel(int line, int level){ setValue(level, line, 4); }
        public void setRange(int line, int range){ setValue(range, line, 5); }
        public void setWeight(int line, int weight){ setValue(weight, line, 6); }
        public void setPower(int line, int power){ setValue(power, line, 7); }
        public void setHit(int line, int hit){ setValue(hit, line, 8); }
        public void setCritical(int line, int critical){ setValue(critical, line, 9); }

        public Items(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //特技列表
    class Specials extends FeReaderFile {
        public String getName(int line){ return getString(line, 0); }
        public String getSummary(int line){ return getString(line, 1); }
        public int getPicture(int line){ return getInt(line, 2); }

        public void setName(int line, String name){ setValue(name, line, 0); }
        public void setSummary(int line, String summary){ setValue(summary, line, 1); }
        public void setPicture(int line, int picture){ setValue(picture, line, 2); }

        public Specials(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //技能等级
    class SkillLevel extends FeReaderFile {
        public int getE(int line){ return getInt(line, 0); }
        public int getD(int line){ return getInt(line, 1); }
        public int getC(int line){ return getInt(line, 2); }
        public int getB(int line){ return getInt(line, 3); }
        public int getA(int line){ return getInt(line, 4); }
        public int getS(int line){ return getInt(line, 5); }
        public int getSS(int line){ return getInt(line, 6); }

        public SkillLevel(String folder, String name, String split){
            super(folder, name, split);
        }
    }

    //能力上限
    class AbilityLimit extends FeReaderFile {
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

        public AbilityLimit(String folder, String name, String split){
            super(folder, name, split);
        }
    }
}