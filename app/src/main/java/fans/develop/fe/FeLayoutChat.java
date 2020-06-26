package fans.develop.fe;

import android.content.Context;
import android.view.View;

/*
    地图上的对话
 */
public class FeLayoutChat extends FeLayoutParent {

	private FeLayoutSection.Callback callback;
	
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
		this.callback = callback;
    }

    /* ---------- abstract interface ---------- */
    public boolean onKeyBack(){
        return false;
    }
    public boolean onDestory(){
        //释放子view
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof FeViewParent)
                ((FeViewParent)v).onDestory();
        }
        return true;
    }
    public void onReload(){
        ;
    }
}
