package fans.develop.fe;

import android.content.Context;

/*
    显示地图光标处的地形信息
 */
public class FeLayoutMapInfo extends FeLayoutParent {

    private Context context;
    private FeSection.Callback callback;
    private Boolean onFlag = false;

    public boolean checkHit(float x, float y){
        refresh();
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMapInfo(Context context, FeSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;

    }

    /*
        启动 地图光标、地图信息、地图人物头像信息 显示
     */
    public void on(){
        if(onFlag)
            return;
        addView(new FeViewSelect(context, callback));
        addView(new FeViewMapInfo(context, callback));
        addView(new FeViewUnitInfo(context, callback));
        onFlag = true;
    }

    /*
        关闭 地图光标、地图信息、地图人物头像信息 显示
     */
    public void off(){
        removeAllViews();
        onFlag = false;
    }
}
