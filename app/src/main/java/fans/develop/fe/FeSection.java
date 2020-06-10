package fans.develop.fe;

import android.content.Context;

/*
    章节运行关键参数之数据部分
 */
public class FeSection{

    //sX: 存档位置 mode: 0/重新加载 1/中断继续
    public FeSection(Context context, int sX, int mode){
        //从文件加载章节存档数据
        if(mode == 1)
            data = FeData.assets.save.recoverSx(sX);
        else
            data = FeData.assets.save.loadSx(sX);
        //章节
        if(data != null)
            section = data.info.getSection();
        //初始化参数集
        sectionMap = new FeSectionMap(section);
        sectionUnit = new FeSectionUnit();
    }

    /* ---------- 章节数据 ---------- */

    private FeAssetsSX data;

    /* ---------- 参数合集 ---------- */

    public int section = 0;
    public FeLayoutSection layoutSection;
    public FeSectionMap sectionMap;
    public FeSectionUnit sectionUnit;

    /* ---------- 触屏产生的各种状态 ---------- */

    //选中事件状态[9]
    private boolean[] click_type = new boolean[9];
    //select type
    public static short ON_MOVE = 0;//移动中
    public static short ON_HIT_MAP = 1;//选中地图
    public static short ON_HIT_UNIT = 2;//选中人物
    public static short ON_HIT_MAPINFO = 3;//选中地图信息
    public static short ON_HIT_UNIT_MENU = 4;//选中人物菜单
    public static short ON_HIT_SYS_MENU = 5;//选中系统菜单

    /* ---------- 控件事件回调 ---------- */

    public FeSection.Callback callback = new FeSection.Callback()
    {
        public void addHeartUnit(FeHeartUnit heartUnit){
            FeData.addHeartUnit(heartUnit);
        }
        public void removeHeartUnit(FeHeartUnit heartUnit){
            FeData.removeHeartUnit(heartUnit);
        }
        public FeSectionMap getSectionMap(){
            return sectionMap;
        }
        public FeSectionUnit getSectionUnit(){
            return sectionUnit;
        }
        public FeAssets getAssets(){
            return FeData.assets;
        }
        public void refresh(){
            if(layoutSection == null)
                return;
            //更新标记格
            layoutSection.layoutMark.refresh();
            //更新人物动画
            layoutSection.layoutUnit.refresh(0);
            //更新地形信息
            layoutSection.layoutMapInfo.refresh();
            //更新人物菜单
            layoutSection.layoutUnitMenu.refresh();
            //更新系统菜单
            layoutSection.layoutMenu.refresh();
            //更新对话
            layoutSection.layoutChat.refresh();
        }
        public boolean checkHit(float x, float y){
            if(layoutSection == null)
                return false;
            //点击:正在对话?
            layoutSection.layoutChat.checkHit(x, y);
            //点击:系统菜单中?
            layoutSection.layoutMenu.checkHit(x, y);
            //点击:人物菜单中?
            layoutSection.layoutUnitMenu.checkHit(x, y);
            //点击:标记格
            layoutSection.layoutMark.checkHit(x, y);
            //点击:选中人物?
            layoutSection.layoutUnit.checkHit(x, y);
            //点击:地图信息?
            layoutSection.layoutMapInfo.checkHit(x, y);
            return false;
        }
        public void cleanClickState(short type){
            click_type[type] = false;
        }
        public void cleanClickStateAll(short type){
            for(int i = 0; i < click_type.length; i++)
                click_type[i] = false;
        }
        public void setClickState(short type){
            click_type[type] = true;
        }
        public boolean checkClickState(short type){
            return click_type[type];
        }
        public void setLayoutSection(FeLayoutSection layout){
            layoutSection = layout;
        }
        //界面就绪,开始程序
        public void start(){
            //人物加载
            for(int i = 0; i < data.saveCache.unit.total(); i++){
                layoutSection.layoutUnit.loadView(
                    data.saveCache.unit.getId(i),
                    data.saveCache.unit.getX(i),
                    data.saveCache.unit.getY(i),
                    data.saveCache.unit.getCamp(i));
            }
        }
    };

    public interface Callback{
        void addHeartUnit(FeHeartUnit heartUnit);
        void removeHeartUnit(FeHeartUnit heartUnit);
        FeSectionMap getSectionMap();
        FeSectionUnit getSectionUnit();
        FeAssets getAssets();
        void refresh();
        boolean checkHit(float x, float y);
        void cleanClickState(short type);
        void cleanClickStateAll(short type);
        void setClickState(short type);
        boolean checkClickState(short type);
        void setLayoutSection(FeLayoutSection layout);
        //界面就绪,开始程序
        void start();
    }
}
