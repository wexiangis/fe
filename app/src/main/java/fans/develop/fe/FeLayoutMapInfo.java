package fans.develop.fe;

import android.content.Context;
import android.widget.RelativeLayout;

/*
    显示地图光标处的地形信息
 */
public class FeLayoutMapInfo extends FeLayoutParent {

    public boolean checkHit(float x, float y){
        refresh();
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMapInfo(Context context) {
        super(context);
        //
        addView(new FeViewSelect(context));
        addView(new FeViewMapInfo(context));
        addView(new FeViewUnitInfo(context));
    }
}