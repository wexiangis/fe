package fans.develop.fe;

/*
    /assets/save/sX 文件夹资源管理器, 档位X的存档信息管理
 */
public class FeAssetsSX {

    private FeAssetsUnit assetsUnit;
    private int sX;

    /*
        注意区别 sX 和 当前章节 不是一个东西
     */
    public FeAssetsSX(FeAssetsUnit assetsUnit, int sX) {
        this.assetsUnit = assetsUnit;
        this.sX = sX;
        //sX
        String folder = String.format("/save/s%d/", sX);
        //file
        info = new Info(folder, "info.txt", ";");
        setting = new Setting(folder, "setting.txt", ";");
        site = new Site(folder, "site.txt", ";");
        //布局文件不存在,则从section拷贝过来
        if (site.line() < 1) {
            site.reLoad(String.format("/section/section%02d/", info.getSection()), "site.txt");
            site.rename(folder, "site.txt");
        }
        // 子文件夹
        section = new FeAssetsSection(assetsUnit, info.getSection());
        // 各阵营存档情况
        campBlue = new FeAssetsCamps(assetsUnit, sX, FeTypeCamp.BLUE);
        campGreen = new FeAssetsCamps(assetsUnit, sX, FeTypeCamp.GREEN);
        campRed = new FeAssetsCamps(assetsUnit, sX, FeTypeCamp.RED);
        campDark = new FeAssetsCamps(assetsUnit, sX, FeTypeCamp.DARK);
        campOrange = new FeAssetsCamps(assetsUnit, sX, FeTypeCamp.ORANGE);
        campPurple = new FeAssetsCamps(assetsUnit, sX, FeTypeCamp.PURPLE);
        campBlueGreen = new FeAssetsCamps(assetsUnit, sX, FeTypeCamp.BLUE_GREEN);
    }

    //----- file -----

    public Info info;
    public Setting setting;
    public Site site;

    //----- folder -----

    public FeAssetsSaveCache saveCache;
    public FeAssetsSection section;

    // 各阵营存档情况
    public FeAssetsCamps campBlue;
    public FeAssetsCamps campGreen;
    public FeAssetsCamps campRed;
    public FeAssetsCamps campDark;
    public FeAssetsCamps campOrange;
    public FeAssetsCamps campPurple;
    public FeAssetsCamps campBlueGreen;

    //----- api -----

    /*
        从中断中恢复, 与 init() 互斥使用
     */
    public void recover() {
        //加载缓存
        saveCache = new FeAssetsSaveCache(assetsUnit, sX);
    }

    /*
        初始人物加载, 与 recover() 互斥使用
     */
    public void init() {
        //删空缓存
        new FeFile().delete(String.format("/save/s%d/cache", sX));
        //建立缓存
        saveCache = new FeAssetsSaveCache(assetsUnit, sX);
        //根据 section 的site加载 saveUnit 人物到 saveCache
        // for (int siteCount = 0, saveUnitCount = 0;
        //      siteCount < section.site.total() && saveUnitCount < saveUnit.camps.total();
        //      siteCount++) {
        //     //触发方式为回合触发,且回合为0
        //     if (section.site.getTrigger(siteCount) == 0 &&
        //             section.site.getTurn(siteCount) == 0) {
        //         //添加己方人物
        //         saveCache.addUnit(0, saveUnitCount++, section.site.getXY(siteCount), 0);
        //     }
        // }
        //根据 section 的assetsUnit加载人物到 saveCache
        for (int i = 0; i < section.layout.total(); i++) {
            //触发方式为回合触发,且回合为0
            if (section.layout.getTrigger(i) == 0 &&
                    section.layout.getTurn(i) == 0) {
                //添加assetsUnit人物
                saveCache.addUnit(section.layout.getCamp(i), section.layout.getId(i), section.layout.getXY(i), -1);
            }
        }
    }

    /*
        回合触发事件(回合turn>0)
     */
    public void eventTurn(int turn, int camp) {

        // ----- 触发人物增加 -----

        //根据 section 的site加载 saveUnit 人物到 saveCache
        //为己方阵营
        // if (camp == 0) {
        //     for (int siteCount = 0, saveUnitCount = 0;
        //          siteCount < section.site.total() &&
        //                  saveUnitCount < saveUnit.camps.total();
        //          siteCount++) {
        //         //触发方式为回合触发,且回合为turn
        //         if (section.site.getTrigger(siteCount) == 0 &&
        //                 section.site.getTurn(siteCount) == turn) {
        //             //添加己方人物
        //             saveCache.addUnit(0, saveUnit.capmps.getId(saveUnitCount), section.site.getXY(siteCount), true);
        //             //计数,防止当前出击人数超过全部人数
        //             saveUnitCount++;
        //         }
        //     }
        // }
        //根据 section 的assetsUnit加载人物到 saveCache
        for (int i = 0; i < section.layout.total(); i++) {
            //触发方式为回合触发,且回合为turn,且为目标阵营
            if (section.layout.getTrigger(i) == 0 &&
                    section.layout.getTurn(i) == turn &&
                    section.layout.getCamp(i) == camp) {
                //添加assetsUnit人物
                saveCache.addUnit(camp, section.layout.getId(i), section.layout.getXY(i), -1);
            }
        }

        // ----- 触发人物trend变动 -----


        // ----- 触发对话 -----
    }

    /*
        其他事件
        camp, order: 触发人物的阵营和序号
     */
    public void eventXXX(int camp, int order) {
        ;
    }

    //----- class -----

    public class Info extends FeReaderFile {

        public int getSection() {
            return getInt(0, 0);
        }

        public int getIrq() {
            return getInt(0, 1);
        }

        public int getTime() {
            return getInt(0, 2);
        }

        public void setSection(int section) {
            setValue(section, 0, 0);
        }

        public void setIrq(int irq) {
            setValue(irq, 0, 1);
        }

        public void setTime(int time) {
            setValue(time, 0, 2);
        }

        public Info(String folder, String name, String split) {
            super(folder, name, split);
            //文件没有加载,创建文件
            if (line() == 0) {
                addLine(new String[]{"1", "0", "0", "章节/是否中断/总用时"});
                save();
            }
        }
    }

    public class Setting extends FeReaderFile {

        public Setting(String folder, String name, String split) {
            super(folder, name, split);
        }
    }

    public class Site extends FeReaderFile {

        public int getTrigger(int line) {
            return getInt(line, 0);
        }

        public int getTurn(int line) {
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

        public int getFix(int line) {
            return getInt(line, 3);
        }

        public int getId(int line) {
            return getInt(line, 4);
        }

        public int total() {
            return line();
        }

        public Site(String folder, String name, String split) {
            super(folder, name, split);
        }
    }
}

