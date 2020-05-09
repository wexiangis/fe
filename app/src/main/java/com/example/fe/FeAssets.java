package com.example.fe;

import android.graphics.Bitmap;

/*
    assets资源管理器
 */
public class FeAssets {

    //----- api -----

    public String getUnitName(int count){
        return name.getName(unit.getName(count));
    }
    public String getUnitNameSummary(int count){
        return name.getSummary(unit.getName(count));
    }

    //----- all -----

    // unit
    public Unit unit = new Unit();
    public Name name = new Name();
    public Item item = new Item();
    // unit addition
    public Addition addition = new Addition();
    public A_Ability a_ability = new A_Ability();
    public A_Grow a_grow = new A_Grow();
    public A_Skill a_skill = new A_Skill();
    public A_Special a_special = new A_Special();
    // unit profession
    public Profession profession = new Profession();
    public P_Name p_name = new P_Name();
    public P_Ability p_ability = new P_Ability();
    public P_Upgrade p_upgrade = new P_Upgrade();
    public P_Grow p_grow = new P_Grow();
    public P_Skill p_skill = new P_Skill();
    public P_Special p_special = new P_Special();
    public P_Types p_types = new P_Types();
    // unit else
    public Items items = new Items();
    public Special special = new Special();

    //----- unit -----

    //人物列表
    class Unit extends AssetsFileStruct{
        public int getName(int line){ return getInt(line, 0); }
        public int getHead(int line){ return getInt(line, 1); }
        public int getProfession(int line){ return getInt(line, 2); }
        public int getAddition(int line){ return getInt(line, 3); }
        public int getLevel(int line){ return getInt(line, 4); }
        public int getItem(int line){ return getInt(line, 5); }

        public void setName(int line, int name){ setValue(name, line, 0); }
        public void setHead(int line, int head){ setValue(head, line, 1); }
        public void setProfession(int line, int profession){ setValue(profession, line, 2); }
        public void setAddition(int line, int addition){ setValue(addition, line, 3); }
        public void setLevel(int line, int level){ setValue(level, line, 4); }
        public void setItem(int line, int item){ setValue(item, line, 5); }

        public Unit(){
            folderAndName = new String[]{"/unit/", "unit.txt"};
            split = ";";
            loadFile();
        }
    }

    //人物名称列表
    class Name extends AssetsFileStruct{
        public String getName(int line){ return getString(line, 0); }
        public String getSummary(int line){ return getString(line, 1); }

        public void setName(int line, String name){ setValue(name, line, 0); }
        public void setSummary(int line, String summary){ setValue(summary, line, 1); }

        public Name(){
            folderAndName = new String[]{"/unit/", "name.txt"};
            split = ";";
            loadFile();
        }
    }

    //人物物品列表
    class Item extends AssetsFileStruct{
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

        public Item(){
            folderAndName = new String[]{"/unit/", "item.txt"};
            split = ";";
            loadFile();
        }
    }

    //----- unit addition -----

    //人物加成列表
    class Addition extends AssetsFileStruct{
        public int getAbility(int line){ return getInt(line, 0); }
        public int getGrow(int line){ return getInt(line, 1); }
        public int getSkill(int line){ return getInt(line, 2); }
        public int getSpecial(int line){ return getInt(line, 3); }

        public void setAbility(int line, int ability){ setValue(ability, line, 0); }
        public void setGrow(int line, int grow){ setValue(grow, line, 1); }
        public void setSkill(int line, int skill){ setValue(skill, line, 2); }
        public void setSpecial(int line, int special){ setValue(special, line, 3); }
        
        public Addition(){
            folderAndName = new String[]{"/unit/", "addition.txt"};
            split = ";";
            loadFile();
        }
    }

    //人物能力加成列表
    class A_Ability extends AssetsFileStruct{
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
        
        public A_Ability(){
            folderAndName = new String[]{"/unit/", "a_ability.txt"};
            split = ";";
            loadFile();
        }
    }

    //人物成长率加成列表
    class A_Grow extends AssetsFileStruct{
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

        public A_Grow(){
            folderAndName = new String[]{"/unit/", "a_grow.txt"};
            split = ";";
            loadFile();
        }
    }

    //人物技能加成列表
    class A_Skill extends AssetsFileStruct{
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
        
        public A_Skill(){
            folderAndName = new String[]{"/unit/", "a_skill.txt"};
            split = ";";
            loadFile();
        }
    }

    //人物特技列表
    class A_Special extends AssetsFileStruct{
        public int getSpe1(int line){ return getInt(line, 0); }
        public int getSpe2(int line){ return getInt(line, 1); }
        public int getSpe3(int line){ return getInt(line, 2); }
        public int getSpe4(int line){ return getInt(line, 3); }

        public void setSpe1(int line, int spe1){ setValue(spe1, line, 0); }
        public void setSpe2(int line, int spe2){ setValue(spe2, line, 1); }
        public void setSpe3(int line, int spe3){ setValue(spe3, line, 2); }
        public void setSpe4(int line, int spe4){ setValue(spe4, line, 3); }
        
        public A_Special(){
            folderAndName = new String[]{"/unit/", "a_special.txt"};
            split = ";";
            loadFile();
        }
    }

    //----- unit profession -----

    //职业列表
    class Profession extends AssetsFileStruct{
        public int getName(int line){ return getInt(line, 0); }
        public int getAnim(int line){ return getInt(line, 1); }
        public int getAbility(int line){ return getInt(line, 2); }
        public int getUpgrade(int line){ return getInt(line, 3); }
        public int getGrow(int line){ return getInt(line, 4); }
        public int getSkill(int line){ return getInt(line, 5); }
        public int getSpecial(int line){ return getInt(line, 6); }
        public int getType(int line){ return getInt(line, 7); }

        public void setName(int line, int name){ setValue(name, line, 0); }
        public void setAnim(int line, int anim){ setValue(anim, line, 1); }
        public void setAbility(int line, int ability){ setValue(ability, line, 2); }
        public void setUpgrade(int line, int upgrade){ setValue(upgrade, line, 3); }
        public void setGrow(int line, int grow){ setValue(grow, line, 4); }
        public void setSkill(int line, int skill){ setValue(skill, line, 5); }
        public void setSpecial(int line, int special){ setValue(special, line, 6); }
        public void setType(int line, int type){ setValue(type, line, 7); }

        public Profession(){
            folderAndName = new String[]{"/unit/", "profession.txt"};
            split = ";";
            loadFile();
        }
    }

    //职业名称列表
    class P_Name extends AssetsFileStruct{
        public String getName(int line){ return getString(line, 0); }
        public String getSummary(int line){ return getString(line, 1); }

        public void setName(int line, String name){ setValue(name, line, 0); }
        public void setSummary(int line, String summary){ setValue(summary, line, 1); }

        public P_Name(){
            folderAndName = new String[]{"/unit/", "p_name.txt"};
            split = ";";
            loadFile();
        }
    }

    //职业能力列表
    class P_Ability extends AssetsFileStruct{
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
        
        public P_Ability(){
            folderAndName = new String[]{"/unit/", "p_ability.txt"};
            split = ";";
            loadFile();
        }
    }

    //职业升级加点列表
    class P_Upgrade extends AssetsFileStruct{
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

        public P_Upgrade(){
            folderAndName = new String[]{"/unit/", "p_upgrade.txt"};
            split = ";";
            loadFile();
        }
    }

    //职业成长率列表
    class P_Grow extends AssetsFileStruct{
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

        public P_Grow(){
            folderAndName = new String[]{"/unit/", "p_grow.txt"};
            split = ";";
            loadFile();
        }
    }

    //职业技能列表
    class P_Skill extends AssetsFileStruct{
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

        public P_Skill(){
            folderAndName = new String[]{"/unit/", "p_skill.txt"};
            split = ";";
            loadFile();
        }
    }

    //职业特技列表
    class P_Special extends AssetsFileStruct{
        public int getSpe1(int line){ return getInt(line, 0); }
        public int getSpe2(int line){ return getInt(line, 1); }
        public int getSpe3(int line){ return getInt(line, 2); }
        public int getSpe4(int line){ return getInt(line, 3); }

        public void setSpe1(int line, int spe1){ setValue(spe1, line, 0); }
        public void setSpe2(int line, int spe2){ setValue(spe2, line, 1); }
        public void setSpe3(int line, int spe3){ setValue(spe3, line, 2); }
        public void setSpe4(int line, int spe4){ setValue(spe4, line, 3); }

        public P_Special(){
            folderAndName = new String[]{"/unit/", "p_special.txt"};
            split = ";";
            loadFile();
        }
    }

    //职业类型列表
    class P_Types extends AssetsFileStruct{
        public String getName(int line){ return getString(line, 0); }
        public String getSummary(int line){ return getString(line, 1); }
        public int getPicture(int line){ return getInt(line, 2); }

        public void setName(int line, String name){ setValue(name, line, 0); }
        public void setSummary(int line, String summary){ setValue(summary, line, 1); }
        public void setPicture(int line, int picture){ setValue(picture, line, 2); }
        
        public P_Types(){
            folderAndName = new String[]{"/unit/", "p_types.txt"};
            split = ";";
            loadFile();
        }
    }

    //----- unit else -----

    //物品列表
    class Items extends AssetsFileStruct{
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
        
        public Items(){
            folderAndName = new String[]{"/unit/", "items.txt"};
            split = ";";
            loadFile();
        }
    }

    //特技列表
    class Special extends AssetsFileStruct{
        public String getName(int line){ return getString(line, 0); }
        public String getSummary(int line){ return getString(line, 1); }
        public int getPicture(int line){ return getInt(line, 2); }

        public void setName(int line, String name){ setValue(name, line, 0); }
        public void setSummary(int line, String summary){ setValue(summary, line, 1); }
        public void setPicture(int line, int picture){ setValue(picture, line, 2); }

        public Special(){
            folderAndName = new String[]{"/unit/", "special.txt"};
            split = ";";
            loadFile();
        }
    }
}

//通用文件管理
class AssetsFileStruct{
    //文件路径和分隔符
    public String[] folderAndName;
    public String split;
    //链表数据结构,一行数据占用一个Data
    public Data data;
    public class Data{
        public String[] content;//原汁原味保留行数据,用split分隔而来
        public Data next = null;
        public Data(String[] content){
            this.content = content;
        }
    }
    //获取某一行数据格式: 数据 = 对象.getData(line).数据名称;
    public Data getData(int line){
        int count = 0;
        Data dat = data;
        while (dat != null && count++ != line)
            dat = dat.next;
        return dat;
    }
    //获取/设置指定行,指定序号的数据
    public String getString(int line, int count){
        Data d = getData(line);
        if(d != null || count < d.content.length)
            return d.content[count];
        return "";
    }
    public void setValue(String val, int line, int count){
        Data d = getData(line);
        if(d != null && count < d.content.length)
            d.content[count] = val;
    }
    //获取/设置指定行,指定序号的数据
    public int getInt(int line, int count){
        Data d = getData(line);
        if(d != null || count < d.content.length)
            return Integer.valueOf(d.content[count]);
        return -1;
    }
    public void setValue(int val, int line, int count){
        Data d = getData(line);
        if(d != null && count < d.content.length)
            d.content[count] = String.valueOf(val);
    }
    //从文件加载数据到data链表
    public void loadFile(){
        FeFileRead ffr = new FeFileRead(folderAndName[0], folderAndName[1]);
        Data datNow = null;
        while(ffr.readLine(split)){
            //获取数据
            if(datNow == null) datNow = data = new Data(ffr.getContent());
            else datNow = datNow.next = new Data(ffr.getContent());
        }
        ffr.exit();
    }
    //保存data链表的数据到文件
    public void saveFile(){
        FeFileWrite ffw = new FeFileWrite(folderAndName[0], folderAndName[1]);
        if(ffw.ready())
        {
            Data dat = data;
            while (dat != null)
            {
                //重组一行数并写入, "+split"意思是结尾保留分隔符
                String line = dat.content[0] + split;
                for(int i = 1; i < dat.content.length; i++)
                    line += dat.content[i] + split;
                ffw.write(line, true);

                //避免String.join()的使用,其要求API24以上
                // ffw.write(String.join(split, dat.content) + split, true);

                dat = dat.next;
            }
        }
        ffw.exit();
    }
}


