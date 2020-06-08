package fans.develop.fe;

import android.content.Context;
import android.widget.RelativeLayout;
import fans.develop.fe.FeSection.Event;

/*
    加载和管理地图及地形动画
 */
public class FeLayoutMap extends FeLayoutParent {

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutMap(Context context) {
        super(context);
        addView(new FeViewMap(context));
    }

}
