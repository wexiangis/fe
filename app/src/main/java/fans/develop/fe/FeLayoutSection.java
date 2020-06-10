package fans.develop.fe;

import android.content.Context;

public class FeLayoutSection extends FeLayoutParent {

    public FeLayoutMap layoutMap = null;
    public FeLayoutMark layoutMark = null;
    public FeLayoutUnit layoutUnit = null;
    public FeLayoutMapInfo layoutMapInfo = null;
    public FeLayoutUnitMenu layoutUnitMenu = null;
    public FeLayoutMenu layoutMenu = null;
    public FeLayoutChat layoutChat = null;

    public FeLayoutSection(Context context, FeSection.Callback callback){
        super(context);
        callback.setLayoutSection(this);
        //地图图层
        layoutMap = new FeLayoutMap(context, callback);
        addView(layoutMap);
        //标记格图层
        layoutMark = new FeLayoutMark(context, callback);
        addView(layoutMark);
        //人物动画图层
        layoutUnit = new FeLayoutUnit(context, callback);
        addView(layoutUnit);
        //地图地形信息
        layoutMapInfo = new FeLayoutMapInfo(context, callback);
        addView(layoutMapInfo);
        //人物操作菜单图层
        layoutUnitMenu = new FeLayoutUnitMenu(context, callback);
        addView(layoutUnitMenu);
        // 系统菜单图层
        layoutMenu = new FeLayoutMenu(context, callback);
        addView(layoutMenu);
        //人物对话图层
        layoutChat = new FeLayoutChat(context, callback);
        addView(layoutChat);
        //其它图层
        ;
    }
}
