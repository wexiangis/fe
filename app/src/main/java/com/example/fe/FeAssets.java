package com.example.fe;

import android.graphics.Bitmap;

public class FeAssets {

    //----- map -----
    class Map{
        public Bitmap getBitmap(int section){
            return null;
        }
        public int getWidth(int section){
            return 100;
        }
        public int getHeight(int section){
            return 100;
        }
        public int getPixel(int section){
            return 100;
        }
    }
    public Map map;

    //----- unit -----

    //人物列表
    class Unit{
        public int name;
        public int head;
        public int profession;
        public int addition;
        public int level;
        public int item;
        // 链表信息
        public int line;
        public Unit next = null;
    }
    public Unit unit;

    //人物名称列表
    class UnitName{
        public String name;
        public String summary;
        // 链表信息
        public int line;
        public UnitName next = null;
    }
    public UnitName unitName;

    //人物物品列表
    class UnitItem{
        public int it1;
        public int it2;
        public int it3;
        public int it4;
        public int it5;
        public int it6;
        // 链表信息
        public int line;
        public UnitItem next = null;
    }
    public UnitItem unitItem;

    //----- unit addition -----

    //人物加成列表
    class Addition{
        public int ability;
        public int grow;
        public int skill;
        public int special;
        // 链表信息
        public int line;
        public Addition next = null;
    }
    public Addition addition;

    //人物能力加成列表
    class AdditionAbility{
        public int hp;
        public int str;
        public int mag;
        public int skill;
        public int spe;
        public int luk;
        public int def;
        public int mde;
        public int weig;
        public int mov;
        // 链表信息
        public int line;
        public AdditionAbility next = null;
    }
    public AdditionAbility additionAbility;

    //人物成长率加成列表
    class AdditionGrow{
        public int hp;
        public int str;
        public int mag;
        public int skill;
        public int spe;
        public int luk;
        public int def;
        public int mde;
        public int weig;
        public int mov;
        // 链表信息
        public int line;
        public AdditionGrow next = null;
    }
    public AdditionGrow additionGrow;

    //人物技能加成列表
    class AdditionSkill{
        public int sword;
        public int gun;
        public int axe;
        public int arrow;
        public int phy;
        public int light;
        public int dark;
        public int stick;
        // 链表信息
        public int line;
        public AdditionSkill next = null;
    }
    public AdditionSkill additionSkill;

    //人物特技列表
    class AdditionSpecial{
        public int spe1;
        public int spe2;
        public int spe3;
        public int spe4;
        // 链表信息
        public int line;
        public AdditionSpecial next = null;
    }
    public AdditionSpecial additionSpecial;

    //----- unit profession -----

    //职业列表
    class Profession{
        public int name;
        public int anim;
        public int ability;
        public int upgrade;
        public int grow;
        public int skill;
        public int special;
        public int type;
        // 链表信息
        public int line;
        public Profession next = null;
    }
    public Profession profession;

    //职业名称列表
    class ProfessionName{
        public String name;
        public String summary;
        // 链表信息
        public int line;
        public ProfessionName next = null;
    }
    public ProfessionName professionName;

    //职业能力列表
    class ProfessionAbility{
        public int hp;
        public int str;
        public int mag;
        public int skill;
        public int spe;
        public int luk;
        public int def;
        public int mde;
        public int weig;
        public int mov;
        // 链表信息
        public int line;
        public ProfessionAbility next = null;
    }
    public ProfessionAbility professionAbility;

    //职业升级加点列表
    class ProfessionUpgrade{
        public int hp;
        public int str;
        public int mag;
        public int skill;
        public int spe;
        public int luk;
        public int def;
        public int mde;
        public int weig;
        public int mov;
        // 链表信息
        public int line;
        public ProfessionUpgrade next = null;
    }
    public ProfessionUpgrade professionUpgrade;

    //职业成长率列表
    class ProfessionGrow{
        public int hp;
        public int str;
        public int mag;
        public int skill;
        public int spe;
        public int luk;
        public int def;
        public int mde;
        public int weig;
        public int mov;
        // 链表信息
        public int line;
        public ProfessionGrow next = null;
    }
    public ProfessionGrow professionGrow;

    //职业技能列表
    class ProfessionSkill{
        public int sword;
        public int gun;
        public int axe;
        public int arrow;
        public int phy;
        public int light;
        public int dark;
        public int stick;
        // 链表信息
        public int line;
        public ProfessionSpecial next = null;
    }
    public ProfessionSkill professionSkill;

    //职业特技列表
    class ProfessionSpecial{
        public int spe1;
        public int spe2;
        public int spe3;
        public int spe4;
        // 链表信息
        public int line;
        public ProfessionSpecial next = null;
    }
    public ProfessionSpecial professionSpecial;

    //职业类型列表
    class ProfessionTypes{
        public String name;
        public String summary;
        public int picture;
        // 链表信息
        public int line;
        public ProfessionTypes next = null;
    }
    public ProfessionTypes professionTypes;

    //----- unit else -----

    //物品列表
    class Items{
        public int type;
        public String name;
        public String summary;
        public int picture;
        public int level;
        public int range;
        public int weight;
        public int power;
        public int hit;
        public int critical;
        // 链表信息
        public int line;
        public Items next = null;
    }
    public Items items = null;

    //特技列表
    class Special{
        public String name;
        public String summary;
        public int picture;
        // 链表信息
        public int line;
        public Special next = null;
        public Special(String n, String s){
            name = n; summary = s;
        }
    }
    public Special special = null;
}
