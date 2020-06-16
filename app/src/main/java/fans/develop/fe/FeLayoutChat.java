package fans.develop.fe;

import android.content.Context;

/*
    地图上的对话
 */
public class FeLayoutChat extends FeLayoutParent {

    public boolean checkHit(float x, float y){
        return false;
    }

    public void refresh(){
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++)
            getChildAt(i).invalidate();
    }

    public FeLayoutChat(Context context, FeLayoutSection.Callback callback) {
        super(context);
    }
}
