package fans.develop.fe;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.TextView;

/*
    章节运行关键参数之数据部分
 */
public class FeLayoutSection extends FeLayoutParent{

    private FeData feData;
    private int sX;
    private int mode;

    //sX: 存档位置 mode: 0/重新加载 1/中断继续
    public FeLayoutSection(FeData feData, int sX, int mode){
        super(feData.context);
        this.feData = feData;
        this.sX = sX;
        this.mode = mode;

        callback = new FeLayoutParent.Callback() {
            @Override
            public boolean keyBack() {
                return false;
            }

            @Override
            public boolean destory() {
                return true;
            }

            @Override
            public void reload() {
                FeLayoutSection.this.reload();
            }
        };
    }

    public void reload(){

        this.removeAllViews();

        //显示loading界面
        this.addView(new FeLayoutLoading(feData.context, 0, this,
                new FeLayoutLoading.DoInBackground<FeLayoutSection>() {
                    @Override
                    public String run(FeLayoutSection obj, FeLayoutLoading layoutLoading) {
                        try {
                            Thread.sleep(100);

                            //从文件加载章节存档数据
                            if(mode == 1)
                                data = feData.assets.save.recoverSx(sX);
                            else
                                data = feData.assets.save.loadSx(sX);

                            layoutLoading.setPercent(10);//百分比进度

                            //章节解析
                            if(data != null)
                                section = data.info.getSection();

                            layoutLoading.setPercent(15);//百分比进度

                            //初始化参数集
                            sectionUnit = new FeSectionUnit();

                            layoutLoading.setPercent(20);//百分比进度

                            //地图图层
                            layoutMap = new FeLayoutMap(feData.context, sectionCallback);

                            layoutLoading.setPercent(25);//百分比进度

                            //标记格图层
                            layoutMark = new FeLayoutMark(feData.context, sectionCallback);

                            layoutLoading.setPercent(30);//百分比进度

                            //人物动画图层
                            layoutUnit = new FeLayoutUnit(feData.context, sectionCallback);

                            layoutLoading.setPercent(35);//百分比进度

                            //地图地形信息
                            layoutMapInfo = new FeLayoutMapInfo(feData.context, sectionCallback);

                            layoutLoading.setPercent(40);//百分比进度

                            //人物操作菜单图层
                            layoutUnitMenu = new FeLayoutUnitMenu(feData.context, sectionCallback);

                            layoutLoading.setPercent(45);//百分比进度

                            // 系统菜单图层
                            layoutMenu = new FeLayoutMenu(feData.context, sectionCallback);

                            layoutLoading.setPercent(50);//百分比进度

                            //人物对话图层
                            layoutChat = new FeLayoutChat(feData.context, sectionCallback);

                            layoutLoading.setPercent(55);//百分比进度

                            //debug图层
                            layoutDebug = new FeLayoutDebug(feData.context, sectionCallback);

                            layoutLoading.setPercent(60);//百分比进度

                            //加载地图
                            layoutMap.loadMap(section);

                            layoutLoading.setPercent(65);//百分比进度

                            //人物加载
                            for(int i = 0; i < data.saveCache.unit.total(); i++){
                                layoutUnit.loadView(
                                        data.saveCache.unit.getId(i),
                                        data.saveCache.unit.getX(i),
                                        data.saveCache.unit.getY(i),
                                        data.saveCache.unit.getCamp(i));
                            }

                            layoutLoading.setPercent(70);//百分比进度

                            //启动地图信息显示
                            layoutMapInfo.on();

                            layoutLoading.setPercent(75);//百分比进度

                            //debug
                            dbTouchXY = layoutDebug.addInfo(0x80800000, false);
                            dbTouchGridXY = layoutDebug.addInfo(0x80000080, false);

                            layoutLoading.setPercent(100);//百分比进度

                            Thread.sleep(100);

                        } catch (java.lang.InterruptedException e) { }
                        return null;
                    }
                },
                new FeLayoutLoading.DoInFinal<FeLayoutSection>() {
                    @Override
                    public void run(FeLayoutSection obj, String result) {

                        //移除loading界面
                        obj.removeAllViews();

                        /* ----- 数据就绪,加载图层 ----- */

                        //地图图层
                        obj.addView(layoutMap);
                        //标记格图层
                        obj.addView(layoutMark);
                        //人物动画图层
                        obj.addView(layoutUnit);
                        //地图地形信息
                        obj.addView(layoutMapInfo);
                        //人物操作菜单图层
                        obj.addView(layoutUnitMenu);
                        // 系统菜单图层
                        obj.addView(layoutMenu);
                        //人物对话图层
                        obj.addView(layoutChat);
                        //debug图层
                        obj.addView(layoutDebug);

                        /* ----- 启动后台线程 ----- */

                    }
                }
        ));
    }

    /* ---------- 参数合集 ---------- */

    private FeAssetsSX data;
    private int section = 0;
    private FeSectionMap sectionMap;
    private FeSectionUnit sectionUnit;

    public FeLayoutMap layoutMap = null;
    public FeLayoutMark layoutMark = null;
    public FeLayoutUnit layoutUnit = null;
    public FeLayoutMapInfo layoutMapInfo = null;
    public FeLayoutUnitMenu layoutUnitMenu = null;
    public FeLayoutMenu layoutMenu = null;
    public FeLayoutChat layoutChat = null;
    public FeLayoutDebug layoutDebug = null;

    //debug
    public TextView dbTouchXY;
    public TextView dbTouchGridXY;

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

    public FeLayoutSection.Callback sectionCallback = new FeLayoutSection.Callback()
    {
        public void addHeartUnit(FeHeartUnit heartUnit){
            feData.addHeartUnit(heartUnit);
        }
        public void removeHeartUnit(FeHeartUnit heartUnit){
            feData.removeHeartUnit(heartUnit);
        }
        public FeSectionMap getSectionMap(){
            return sectionMap;
        }
        public FeSectionUnit getSectionUnit(){
            return sectionUnit;
        }
        public void refreshSectionMap(int section){
            //获取屏幕宽高信息
            DisplayMetrics dm = new DisplayMetrics();
            feData.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            //重新初始化map
            sectionMap = new FeSectionMap(section, feData.assets.map.getMap(section), dm.widthPixels, dm.heightPixels);
        }
        public FeAssets getAssets(){
            return feData.assets;
        }
        public Context getContext(){
            return feData.context;
        }
        public void refresh(){
            //更新标记格
            layoutMark.refresh();
            //更新人物动画
            layoutUnit.refresh(0);
            //更新地形信息
            layoutMapInfo.refresh();
            //更新人物菜单
            layoutUnitMenu.refresh();
            //更新系统菜单
            layoutMenu.refresh();
            //更新对话
            layoutChat.refresh();
            //debug图层
            layoutDebug.refresh();
        }
        public boolean checkHit(float x, float y){
            //点击:正在对话?
            layoutChat.checkHit(x, y);
            //点击:系统菜单中?
            layoutMenu.checkHit(x, y);
            //点击:人物菜单中?
            layoutUnitMenu.checkHit(x, y);
            //点击:标记格
            layoutMark.checkHit(x, y);
            //点击:选中人物?
            layoutUnit.checkHit(x, y);
            //点击:地图信息?
            layoutMapInfo.checkHit(x, y);

            //debug
            dbTouchXY.setText(String.format("Touch XY: %.2f, %.2f", x, y));
            dbTouchGridXY.setText(String.format("Touch Grid XY: %d, %d", sectionMap.selectSite.point[0], sectionMap.selectSite.point[1]));

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
    };

    public interface Callback{
        void addHeartUnit(FeHeartUnit heartUnit);
        void removeHeartUnit(FeHeartUnit heartUnit);
        FeSectionMap getSectionMap();
        FeSectionUnit getSectionUnit();
        void refreshSectionMap(int section);
        FeAssets getAssets();
        Context getContext();
        void refresh();
        boolean checkHit(float x, float y);
        void cleanClickState(short type);
        void cleanClickStateAll(short type);
        void setClickState(short type);
        boolean checkClickState(short type);
    }
}
