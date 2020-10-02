package fans.develop.fe;

/*
    camps.txt + campC_XXX.txt 数据组合,C为阵营,XXX为camps中的人物序号(不足3位补0,从0数起)
    用在以下文件管理:
        /save/sX/cache/camps.txt、campC_XXX.txt
        /save/sX/campC/camps.txt、campC_XXX.txt
 */
class FeAssetsCamps {

    private FeAssetsUnit assetsUnit;
    private String folder;

    /*
        从 /save/sX/cache/* 加载
     */
    public FeAssetsCamps(FeAssetsUnit assetsUnit, int sX) {
        folder = String.format("/save/s%d/cache/", sX);
        _FeAssetsCamps(assetsUnit, sX);
    }

    /*
        从 /save/sX/campC/* 加载
     */
    public FeAssetsCamps(FeAssetsUnit assetsUnit, int sX, int camp) {
        folder = String.format("/save/s%d/camp%d/", sX, camp);
        _FeAssetsCamps(assetsUnit, sX);
    }

    private void _FeAssetsCamps(FeAssetsUnit assetsUnit, int sX) {
        this.assetsUnit = assetsUnit;
        //file
        camps = new Camps(folder, "camps.txt", ";");
        //每个阵营都有一个链表头
        campUnitBlue = new CampUnit(folder, FeTypeCamp.BLUE, 0, ";");
        campUnitGreen = new CampUnit(folder, FeTypeCamp.GREEN, 0, ";");
        campUnitRed = new CampUnit(folder, FeTypeCamp.RED, 0, ";");
        campUnitDark = new CampUnit(folder, FeTypeCamp.DARK, 0, ";");
        campUnitOrange = new CampUnit(folder, FeTypeCamp.ORANGE, 0, ";");
        campUnitPurple = new CampUnit(folder, FeTypeCamp.PURPLE, 0, ";");
        campUnitBlueGreen = new CampUnit(folder, FeTypeCamp.BLUE_GREEN, 0, ";");
        //根据camps.txt信息加载各阵营人物信息campC_XXX.txt到内存
        for (int i = 0; i < camps.total(); i++) {
            //获取order和camp
            int order = camps.getOrder(i);
            int camp = camps.getCamp(i);
            //添加到特定阵营的链表
            CampUnit campUnit = getCampUnits(camp);
            if (campUnit != null)
                campUnit.add(new CampUnit(folder, camp, order, ";"));
        }
    }

    // ----- flie -----

    public Camps camps;

    // 各阵营链表
    public CampUnit campUnitBlue;
    public CampUnit campUnitGreen;
    public CampUnit campUnitRed;
    public CampUnit campUnitDark;
    public CampUnit campUnitOrange;
    public CampUnit campUnitPurple;
    public CampUnit campUnitBlueGreen;

    // ----- api -----

    /*
        获得某个阵营链表
     */
    public CampUnit getCampUnits(int camp) {
        switch (camp) {
            case FeTypeCamp.BLUE: return campUnitBlue;
            case FeTypeCamp.GREEN: return campUnitGreen;
            case FeTypeCamp.RED: return campUnitRed;
            case FeTypeCamp.DARK: return campUnitDark;
            case FeTypeCamp.ORANGE: return campUnitOrange;
            case FeTypeCamp.PURPLE: return campUnitPurple;
            case FeTypeCamp.BLUE_GREEN: return campUnitBlueGreen;
        }
        return null;
    }

    /*
        获得特定order人物
     */
    public CampUnit getCampUnit(int order) {
        if (order >= camps.line())
            return null;
        int camp = camps.getCamp(order);
        CampUnit campUnit = getCampUnits(camp);
        if (campUnit == null)
            return null;
        return campUnit.find(order);
    }

    /*
        往特定阵营添加特定人物
     */
    public void addUnit(int camp, int id, int xy, int fromCamp) {
        //添加到链表
        CampUnit campHead = getCampUnits(camp);
        if(campHead == null)
            return;
        //获取order
        int order = camps.add(camp, xy);
        //创建文件
        CampUnit campUnit = new CampUnit(folder, camp, order, ";");
        //添加到特定阵营链表
        campHead.add(campUnit);
        //特定阵营人物,从 campC 中拷贝人物数据
        if (fromCamp >= 0) {
            ;
        }
        //全新人物,从 assetsUnit 数据库获得数据
        else {
            //行0
            campUnit.setCamp(camp);
            campUnit.setOrder(order);
            campUnit.setId(id);
            //行1
            campUnit.setStandby(0);
            campUnit.setState(0);
            campUnit.setLevel(assetsUnit.getLevel(id));
            campUnit.setExp(0);
            campUnit.setHpRes(assetsUnit.getProfessionAbilityHp(id));
            //行2 (整行拷贝)
            campUnit.setLine(2, assetsUnit.getProfessionAbility(id));
            //行3 (默认为0)
            //行4 (整行拷贝)
            campUnit.setLine(4, assetsUnit.getProfessionSkill(id));
            //行5 (整行拷贝)
            campUnit.setLine(5, assetsUnit.getItem(id));
            //行6 (整行拷贝)
            campUnit.setLine(6, assetsUnit.getAdditionSpecial(id));
            //行7
            campUnit.setRescue(0);
            campUnit.setRescueOrder(0);
            //行8
            campUnit.setRescue(0);
            campUnit.setWin(0);
            campUnit.setDie(0);
            //行9
            campUnit.setView(3);
            campUnit.setViewAdd(0);
            //行10
        }
        //保存文件
        campUnit.save();
        camps.save();
    }

    // ----- class -----

    public class Camps extends FeReaderFile {

        //注意xy格式如003004,代表x=3,y=4
        //返回序号,可用于创建camp_c_xxx.txt
        public int add(int camp, int xxxyyy) {
            return addLine(new String[]{
                    String.valueOf(camp),
                    String.valueOf(line()),
                    String.valueOf(xxxyyy)});
        }

        public int total() {
            return line();
        }

        public int getCamp(int line) {
            return getInt(line, 0);
        }

        public int getOrder(int line) {
            return getInt(line, 1);
        }

        public int getXY(int line) {
            return getInt(line, 2);
        }

        public int getX(int line) {
            return getInt(line, 2) / 1000;
        }

        public int getY(int line) {
            return getInt(line, 2) % 1000;
        }

        public void setCamp(int line, int camp) {
            setValue(camp, line, 0);
        }

        public void setOrder(int line, int order) {
            setValue(order, line, 1);
        }

        public void setXY(int line, int xy) {
            setValue(xy, line, 2);
        }

        public void setX(int line, int x) {
            setValue(x * 1000 + getY(line), line, 2);
        }

        public void setY(int line, int y) {
            setValue(getX(line) * 1000 + y, line, 2);
        }

        public Camps(String folder, String name, String split) {
            super(folder, name, split);
        }
    }

    public class CampUnit extends FeReaderFile {

        //链表信息
        private CampUnit next = null, last = null;
        private int total = 0;//链表元素个数,不包括自己(指该阵营人数)

        public int total() {
            return total;
        }

        //链表操作
        public CampUnit find(int order) {
            CampUnit campUnit = next;
            if (campUnit != null) {
                while (campUnit.getOrder() != order && campUnit.next != null)
                    campUnit = campUnit.next;
                if (campUnit.getOrder() == order)
                    return campUnit;
            }
            return null;
        }

        public void add(CampUnit campUnit) {
            if (next == null) {
                next = campUnit;
                campUnit.last = this;
                total = 1;
            } else {
                CampUnit tmp = next;
                while (tmp.next != null)
                    tmp = tmp.next;
                tmp.next = campUnit;
                campUnit.last = tmp;
            }
        }

        public void remove(int order) {
            CampUnit campUnit = next;
            if (campUnit != null) {
                while (campUnit.getOrder() != order && campUnit.next != null)
                    campUnit = campUnit.next;
                if (campUnit.getOrder() == order) {
                    if (campUnit.next != null)
                        campUnit.next.last = campUnit.last;
                    if (campUnit.last != null)
                        campUnit.last.next = campUnit.next;
                    if (campUnit == next)
                        next = campUnit.next;
                    total -= 1;
                }
            }
        }

        // line 0

        public int getCamp() {
            return getInt(0, 0);
        }

        public int getOrder() {
            return getInt(0, 1);
        }

        public int getId() {
            return getInt(0, 2);
        }

        public int getDisable() {
            return getInt(0, 3);
        }

        public void setCamp(int camp) {
            setValue(camp, 0, 0);
        }

        public void setOrder(int order) {
            setValue(order, 0, 1);
        }

        public void setId(int id) {
            setValue(id, 0, 2);
        }

        public void setDisable(int disable) {
            setValue(disable, 0, 3);
        }

        // line 1
        public int getStandby() {
            return getInt(1, 0);
        }

        public int getState() {
            return getInt(1, 1);
        }

        public int getLevel() {
            return getInt(1, 2);
        }

        public int getExp() {
            return getInt(1, 3);
        }

        public int getHpRes() {
            return getInt(1, 4);
        }

        public void setStandby(int standby) {
            setValue(standby, 1, 0);
        }

        public void setState(int state) {
            setValue(state, 1, 1);
        }

        public void setLevel(int level) {
            setValue(level, 1, 2);
        }

        public void setExp(int exp) {
            setValue(exp, 1, 3);
        }

        public void setHpRes(int hpRes) {
            setValue(hpRes, 1, 4);
        }

        // line 2
        public int getHp() {
            return getInt(2, 0);
        }

        public int getStr() {
            return getInt(2, 1);
        }

        public int getMag() {
            return getInt(2, 2);
        }

        public int getSkill() {
            return getInt(2, 3);
        }

        public int getSpe() {
            return getInt(2, 4);
        }

        public int getLuk() {
            return getInt(2, 5);
        }

        public int getDef() {
            return getInt(2, 6);
        }

        public int getMde() {
            return getInt(2, 7);
        }

        public int getBody() {
            return getInt(2, 8);
        }

        public int getMov() {
            return getInt(2, 9);
        }

        public void setHp(int hp) {
            setValue(hp, 2, 0);
        }

        public void setStr(int str) {
            setValue(str, 2, 1);
        }

        public void setMag(int mag) {
            setValue(mag, 2, 2);
        }

        public void setSkill(int skill) {
            setValue(skill, 2, 3);
        }

        public void setSpe(int spe) {
            setValue(spe, 2, 4);
        }

        public void setLuk(int luk) {
            setValue(luk, 2, 5);
        }

        public void setDef(int def) {
            setValue(def, 2, 6);
        }

        public void setMde(int mde) {
            setValue(mde, 2, 7);
        }

        public void setBody(int body) {
            setValue(body, 2, 8);
        }

        public void setMov(int mov) {
            setValue(mov, 2, 9);
        }

        // line 3
        public int getAddHp() {
            return getInt(3, 0);
        }

        public int getAddStr() {
            return getInt(2, 1);
        }

        public int getAddMag() {
            return getInt(3, 2);
        }

        public int getAddSkill() {
            return getInt(3, 3);
        }

        public int getAddSpe() {
            return getInt(3, 4);
        }

        public int getAddLuk() {
            return getInt(3, 5);
        }

        public int getAddDef() {
            return getInt(3, 6);
        }

        public int getAddMde() {
            return getInt(3, 7);
        }

        public int getAddBody() {
            return getInt(3, 8);
        }

        public int getAddMov() {
            return getInt(3, 9);
        }

        public void setAddHp(int hp) {
            setValue(hp, 3, 0);
        }

        public void setAddStr(int str) {
            setValue(str, 3, 1);
        }

        public void setAddMag(int mag) {
            setValue(mag, 3, 2);
        }

        public void setAddSkill(int skill) {
            setValue(skill, 3, 3);
        }

        public void setAddSpe(int spe) {
            setValue(spe, 3, 4);
        }

        public void setAddLuk(int luk) {
            setValue(luk, 3, 5);
        }

        public void setAddDef(int def) {
            setValue(def, 3, 6);
        }

        public void setAddMde(int mde) {
            setValue(mde, 3, 7);
        }

        public void setAddBody(int body) {
            setValue(body, 3, 8);
        }

        public void setAddMov(int mov) {
            setValue(mov, 3, 9);
        }

        // line 4
        public int getSward() {
            return getInt(4, 0);
        }

        public int getGun() {
            return getInt(4, 1);
        }

        public int getAxe() {
            return getInt(4, 2);
        }

        public int getArrow() {
            return getInt(4, 3);
        }

        public int getPhy() {
            return getInt(4, 4);
        }

        public int getLight() {
            return getInt(4, 5);
        }

        public int getDark() {
            return getInt(4, 6);
        }

        public int getStick() {
            return getInt(4, 7);
        }

        public void setSward(int sward) {
            setValue(sward, 4, 0);
        }

        public void setGun(int gun) {
            setValue(gun, 4, 1);
        }

        public void setAxe(int axe) {
            setValue(axe, 4, 2);
        }

        public void setArrow(int arrow) {
            setValue(arrow, 4, 3);
        }

        public void setPhy(int phy) {
            setValue(phy, 4, 4);
        }

        public void setLight(int light) {
            setValue(light, 4, 5);
        }

        public void setDark(int dark) {
            setValue(dark, 4, 6);
        }

        public void setStick(int stick) {
            setValue(stick, 4, 7);
        }

        // line 5
        public int getIt1() {
            return getInt(5, 0);
        }

        public int getIt2() {
            return getInt(5, 1);
        }

        public int getIt3() {
            return getInt(5, 2);
        }

        public int getIt4() {
            return getInt(5, 3);
        }

        public int getIt5() {
            return getInt(5, 4);
        }

        public int getIt6() {
            return getInt(5, 5);
        }

        public int getEquip() {
            return getInt(5, 6);
        }

        public void setIt1(int it1) {
            setValue(it1, 5, 0);
        }

        public void setIt2(int it2) {
            setValue(it2, 5, 1);
        }

        public void setIt3(int it3) {
            setValue(it3, 5, 2);
        }

        public void setIt4(int it4) {
            setValue(it4, 5, 3);
        }

        public void setIt5(int it5) {
            setValue(it5, 5, 4);
        }

        public void setIt6(int it6) {
            setValue(it6, 5, 5);
        }

        public void setEquip(int equip) {
            setValue(equip, 5, 6);
        }

        // line 6
        public int getSpe1() {
            return getInt(6, 0);
        }

        public int getSpe2() {
            return getInt(6, 1);
        }

        public int getSpe3() {
            return getInt(6, 2);
        }

        public int getSpe4() {
            return getInt(6, 3);
        }

        public void setSpe1(int spe1) {
            setValue(spe1, 6, 0);
        }

        public void setSpe2(int spe2) {
            setValue(spe2, 6, 1);
        }

        public void setSpe3(int spe3) {
            setValue(spe3, 6, 2);
        }

        public void setSpe4(int spe4) {
            setValue(spe4, 6, 3);
        }

        // line 7
        public int getRescue() {
            return getInt(7, 0);
        }

        public int getRescueOrder() {
            return getInt(7, 1);
        }

        public void setRescue(int rescue) {
            setValue(rescue, 7, 0);
        }

        public void setRescueOrder(int rescueOrder) {
            setValue(rescueOrder, 7, 1);
        }

        // line 8
        public int getFight() {
            return getInt(8, 0);
        }

        public int getWin() {
            return getInt(8, 1);
        }

        public int getDie() {
            return getInt(8, 3);
        }

        public void setFight(int fight) {
            setValue(fight, 8, 0);
        }

        public void setWin(int win) {
            setValue(win, 8, 1);
        }

        public void setDie(int die) {
            setValue(die, 8, 2);
        }

        // line 9
        public int getView() {
            return getInt(9, 0);
        }

        public int getViewAdd() {
            return getInt(9, 1);
        }

        public void setView(int view) {
            setValue(view, 9, 0);
        }

        public void setViewAdd(int viewAdd) {
            setValue(viewAdd, 9, 1);
        }

        // line 10
        public int getTrend() {
            return getInt(10, 0);
        }

        public int getLeader() {
            return getInt(10, 1);
        }

        public void setTrend(int trend) {
            setValue(trend, 10, 0);
        }

        public void setLeader(int leader) {
            setValue(leader, 10, 1);
        }

        public CampUnit(String folder, int camp, int order, String split) {
            super(folder, String.format("camp%d_%03d.txt", camp, order), split);
            //文件没有加载,创建文件(一般为某一阵营的链表头文件)
            if (line() == 0) {
                addLine(new String[]{"0", "0", "0", "0"});
                addLine(new String[]{"0", "0", "0"});
                addLine(new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
                addLine(new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"});
                addLine(new String[]{"0", "0", "0", "0", "0", "0", "0", "0"});
                addLine(new String[]{"0", "0", "0", "0", "0", "0", "0"});
                addLine(new String[]{"0", "0", "0", "0"});
                addLine(new String[]{"0", "0"});
                addLine(new String[]{"0", "0", "0", "0"});
                addLine(new String[]{"0", "0"});
                addLine(new String[]{"0", "0"});
                // save();
            }
        }
    }
}
