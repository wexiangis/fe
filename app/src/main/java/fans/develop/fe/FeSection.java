package fans.develop.fe;

/*
    章节运行关键参数之数据部分
 */
public class FeSection {

    /* ---------- 章节数据 ---------- */

    public FeAssetsSX data;

    //sX: 存档位置 mode: 0/重新加载 1/中断继续
    public FeSection(int sX, int mode){
        if(mode == 1)
            data = FeData.assets.save.recoverSx(sX);
        else
            data = FeData.assets.save.loadSx(sX);
    }

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

}
