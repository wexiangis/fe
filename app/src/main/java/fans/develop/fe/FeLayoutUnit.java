package fans.develop.fe;

import android.content.Context;
import android.view.View;

/*
    地图中的人物动画管理
 */
public class FeLayoutUnit extends FeLayoutParent {

    private Context context;
    private FeLayoutSection.Callback callback;
    private int hitAnimOrder = -1;

    /*
        who_refresh:
            0/map refresh
            1/select unit refresh
     */
    public void refresh(int who_refresh){
        FeViewUnit tmp;
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++) {
            tmp = (FeViewUnit)getChildAt(i);
            if(hitAnimOrder == i &&
                callback.checkClickState(FeLayoutSection.ON_HIT_UNIT)) {
                if(who_refresh == 1)
                    tmp.setAnimMode(tmp.getAnimMode()+1);
                callback.getSectionMap().getRectByGrid(tmp.gridX, tmp.gridY, callback.getSectionUnit().selectSite);
            }
            else
                tmp.setAnimMode(0);
            tmp.invalidate();
        }
    }

    public boolean checkHit(float x, float y){
        FeViewUnit fvu;
        //遍历所有子view
        for (int i = 0; i < getChildCount(); i++) {
            fvu = (FeViewUnit)getChildAt(i);
            if (fvu.checkHit(x, y)) {
                hitAnimOrder = i;
                callback.setClickState(FeLayoutSection.ON_HIT_UNIT);
                callback.getSectionUnit().selectView = fvu;
                refresh(1);
                return true;
            }
        }
        //
        hitAnimOrder = -1;
        callback.cleanClickState(FeLayoutSection.ON_HIT_UNIT);
        refresh(0);
        return false;
    }

    public FeLayoutUnit(Context context, FeLayoutSection.Callback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    public void loadView(int id, int y, int x, int camp){
        addView(new FeViewUnit(context, id, x, y, 0, camp, callback));
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
